inherit kernel

DESCRIPTION = "QuIC Linux Kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
COMPATIBLE_MACHINE = "(mdm9640|mdm9640-perf|mdmfermium|mdmcalifornium|apq8009)"
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

# Default image type is zImage, change here if needed.
#KERNEL_IMAGETYPE = "zImage"
# Where built kernel lies in the kernel tree
#KERNEL_OUTPUT ?= "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
#KERNEL_IMAGEDEST = "boot"
KERNEL_IMAGETYPE_FOR_MAKE = ""

# Provide a config baseline for things so the kernel will build...
KERNEL_DEFCONFIG                = "mdm_defconfig"
KERNEL_DEFCONFIG_apq8009         = "msm8909_defconfig"

#PACKAGE_ARCH = "${MACHINE_ARCH}"
FILESPATH =+ "${WORKSPACE}:"
SRC_URI   =  "file://kernel"
SRC_URI   =+ "file://kernel/arch/${ARCH}/configs/${KERNEL_DEFCONFIG}"
SRC_DIR   =  "${WORKSPACE}/kernel"
S         =  "${WORKDIR}/kernel"
GITVER    =  "${@base_get_metadata_git_revision('${SRC_DIR}',d)}"
PV = "git-${GITVER}"
PR = "r3"

DEPENDS += "dtbtool-native mkbootimg-native"
KERNEL_PRIORITY = "9001"
PACKAGES = "kernel kernel-base kernel-vmlinux kernel-dev kernel-modules"
RDEPENDS_kernel-base = ""

do_configure () {
	oe_runmake_call -C ${S} ARCH=${ARCH} ${KERNEL_DEFCONFIG} O=${B}
}

# Make vmlinux available as soon as possible
do_install_prepend () {

    install -d ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}
    for f in System.map Module.symvers vmlinux; do
        install -m 0644 ${B}/${f} ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/${f}-${KERNEL_VERSION}
    done
    install -m 0644 ${B}/${KERNEL_OUTPUT} ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

do_install_append() {
    oe_runmake_call -C ${STAGING_KERNEL_DIR} ARCH=${ARCH} CC="${KERNEL_CC}" LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS} headers_install
    install -d ${STAGING_KERNEL_DIR}/usr
    for f in ${B}/usr/*; do
        install -m 0777 ${f} ${STAGING_KERNEL_DIR}/${f#${B}}
    done
    install -m 0777 ${B}/vmlinux ${STAGING_KERNEL_DIR}/vmlinux
    install -m 0777 ${B}/arch/arm/boot/Image ${STAGING_KERNEL_DIR}/arch/arm/boot/Image
}

do_deploy () {
# Make bootimage

    dtb_files=`find ${B}/arch/arm/boot/dts -iname *${MACHINE_DTS_NAME}*.dtb | awk -Fdts/ '{print $NF}' | awk -F[.][d] '{print $1}'`

    # Create separate images with dtb appended to zImage for all targets.
    for d in ${dtb_files}; do
	 #Strip qcom from the result if its present.
       targets=`echo ${d#${MACHINE_DTS_NAME}-}| awk '{split($0,a, "/");print a[2]}'`
	 #If dtb are stored inside qcom then we need to search for them inside qcom, else inside dts.
       qcom_check=`echo ${d}| awk '{split($0,a, "/");print a[1]}'`
	   if [ ${qcom_check} == "qcom" ]; then
		cat ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ${B}/arch/arm/boot/dts/${d}.dtb > ${B}/arch/arm/boot/dts/qcom/dtb-${KERNEL_IMAGETYPE}-${KERNEL_VERSION}-${targets}
	    ${STAGING_BINDIR_NATIVE}/dtbtool ${B}/arch/arm/boot/dts/qcom/ -s ${PAGE_SIZE} -o ${D}/${KERNEL_IMAGEDEST}/masterDTB -p ${B}/scripts/dtc/ -v
	   else
        cat ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ${B}/arch/arm/boot/dts/${d}.dtb > ${B}/arch/arm/boot/dts/dtb-${KERNEL_IMAGETYPE}-${KERNEL_VERSION}-${targets}
	    ${STAGING_BINDIR_NATIVE}/dtbtool ${B}/arch/arm/boot/dts/ -s ${PAGE_SIZE} -o ${D}/${KERNEL_IMAGEDEST}/masterDTB -p ${B}/scripts/dtc/ -v
	   fi
    done

    mkdir -p ${DEPLOY_DIR_IMAGE}
    cmdparams='noinitrd  rw console=ttyHSL0,115200,n8 androidboot.hardware=qcom ehci-hcd.park=3 msm_rtb.filter=0x37 lpm_levels.sleep_disabled=1 ${EXTRA_KERNEL_CMD_PARAMS}'

    # Updated base address according to new memory map.
    ${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} \
        --dt ${D}/${KERNEL_IMAGEDEST}/masterDTB \
        --ramdisk /dev/null \
        --cmdline "${cmdparams}" \
        --pagesize ${PAGE_SIZE} \
        --base ${MACHINE_KERNEL_BASE} \
        --tags-addr ${MACHINE_KERNEL_TAGS_OFFSET} \
        --ramdisk_offset 0x0 \
        --output ${DEPLOY_DIR_IMAGE}/${MACHINE}-boot.img

}
