inherit kernel

DESCRIPTION = "QuIC Linux Kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
COMPATIBLE_MACHINE = "(mdm9640|mdm9640-perf|mdmfermium)"
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

# Default image type is zImage, change here if needed.
#KERNEL_IMAGETYPE = "zImage"
# Where built kernel lies in the kernel tree
#KERNEL_OUTPUT ?= "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
#KERNEL_IMAGEDEST = "boot"
KERNEL_IMAGETYPE_FOR_MAKE = ""

# Provide a config baseline for things so the kernel will build...
KERNEL_DEFCONFIG_mdm9640         = "mdm9640_defconfig"
KERNEL_DEFCONFIG_mdm9640-perf    = "mdm9640-perf_defconfig"
KERNEL_DEFCONFIG_mdmfermium      = "mdmfermium_defconfig"
KERNEL_DEFCONFIG_mdmfermium-perf = "mdmfermium-perf_defconfig"
KERNEL_DEFCONFIG                ?= "${KERNEL_DEFCONFIG_mdm9640}"

#PACKAGE_ARCH = "${MACHINE_ARCH}"
FILESPATH =+ "${WORKSPACE}:"
SRC_URI   =  "file://kernel"
SRC_URI   =+ "file://kernel/arch/${ARCH}/configs/${KERNEL_DEFCONFIG}"
SRC_DIR   =  "${WORKSPACE}/kernel"
S         =  "${WORKDIR}/kernel"
GITVER    =  "${@base_get_metadata_git_revision('${SRC_DIR}',d)}"
PV = "git-${GITVER}"
PR = "r0"

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
}

do_deploy () {
# Make bootimage

    dtb_files=`find ${B}/arch/arm/boot/dts -iname *${MACHINE_DTS_NAME}*.dtb | awk -F/ '{print $NF}' | awk -F[.][d] '{print $1}'`

    # Create separate images with dtb appended to zImage for all targets.
    for d in ${dtb_files}; do
       targets=`echo ${d#${MACHINE_DTS_NAME}-}`
       cat ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ${B}/arch/arm/boot/dts/${d}.dtb > ${B}/arch/arm/boot/dts/dtb-${KERNEL_IMAGETYPE}-${KERNEL_VERSION}-${targets}
    done

    ${STAGING_BINDIR_NATIVE}/dtbtool ${B}/arch/arm/boot/dts/ -s ${PAGE_SIZE} -o ${D}/${KERNEL_IMAGEDEST}/masterDTB -p ${B}/scripts/dtc/ -v

    mkdir -p ${DEPLOY_DIR_IMAGE}
    machine=`echo ${MACHINE}`
     __cmdparams='noinitrd  rw console=ttyHSL0,115200,n8 androidboot.hardware=qcom ehci-hcd.park=3 msm_rtb.filter=0x37'

    if [ "${BASEMACHINE}" != "mdm9640" || "${BASEMACHINE}" != "mdmfermium" ]; then
        __cmdparams+=' rootfstype=yaffs2'
    fi

    cmdparams=`echo ${__cmdparams}`

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
