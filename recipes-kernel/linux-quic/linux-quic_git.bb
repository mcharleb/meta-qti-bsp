inherit kernel

DESCRIPTION = "QuIC Linux Kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
COMPATIBLE_MACHINE = "(mdm9607|mdmcalifornium|apq8009|apq8096|apq8053)"
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"
EXTRA_KERNEL_CMD_PARAMS ?= ""

# Default image type is zImage, change here if needed.
KERNEL_IMAGETYPE = ""
# Where built kernel lies in the kernel tree
zImage_VAR="zImage"
zImage_VAR_apq8053="Image.gz-dtb"
zImage_VAR_apq8096="Image.gz-dtb"

KERNEL_OUTPUT = "arch/${ARCH}/boot/${zImage_VAR}"
#KERNEL_IMAGEDEST = "boot"
KERNEL_IMAGEDEST_apq8096 = "boot"
KERNEL_IMAGETYPE_FOR_MAKE = ""

DEPENDS_append_aarch64 = " libgcc"
KERNEL_CC_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"

# To be moved to machine specific conf
# Provide a config baseline for things so the kernel will build...
KERNEL_DEFCONFIG          = "mdm_defconfig"
KERNEL_DEFCONFIG_mdm9607  = "mdm9607_defconfig"
KERNEL_DEFCONFIG_apq8096  = "msm_defconfig"
KERNEL_DEFCONFIG_apq8053  = "msmcortex_defconfig"
KERNEL_DEFCONFIG_apq8053-perf  = "msmcortex-perf_defconfig"

KERNEL_PRIORITY           = "9001"
# Add V=1 to KERNEL_EXTRA_ARGS for verbose
KERNEL_EXTRA_ARGS        += "O=${B}"

#PACKAGE_ARCH = "${MACHINE_ARCH}"
FILESPATH =+ "${WORKSPACE}:"
SRC_URI   =  "file://kernel"
SRC_DIR   =  "${WORKSPACE}/kernel"
S         =  "${WORKDIR}/kernel"
GITVER    =  "${@base_get_metadata_git_revision('${SRC_DIR}',d)}"
PV = "git-${GITVER}"
PR = "r4"

DEPENDS += "dtbtool-native mkbootimg-native"
DEPENDS_apq8096 += "mkbootimg-native dtc-native"
PACKAGES = "kernel kernel-base kernel-vmlinux kernel-dev kernel-modules"
RDEPENDS_kernel-base = ""

# Put the zImage in the kernel-dev pkg
FILES_kernel-dev += "/${KERNEL_IMAGEDEST}/${zImage_VAR}-${KERNEL_VERSION}"

do_configure () {
    oe_runmake_call -C ${S} ARCH=${ARCH} ${KERNEL_EXTRA_ARGS} ${KERNEL_DEFCONFIG}
}

do_shared_workdir () {
        cd ${B}

        kerneldir=${STAGING_KERNEL_BUILDDIR}
        install -d $kerneldir

        #
        # Store the kernel version in sysroots for module-base.bbclass
        #

        echo "${KERNEL_VERSION}" > $kerneldir/kernel-abiversion

        # Copy files required for module builds
        cp System.map $kerneldir/System.map-${KERNEL_VERSION}
        cp Module.symvers $kerneldir/Module.symvers
        cp Makefile $kerneldir/
        cp .config $kerneldir/
        cp -fR usr $kerneldir/

        # Signing keys may not be present
        [ -f signing_key.priv ] && cp signing_key.priv $kerneldir/
        [ -f signing_key.x509 ] && cp signing_key.x509 $kerneldir/

        # include/config
        mkdir -p $kerneldir/include/config
        cp include/config/kernel.release $kerneldir/include/config/kernel.release
        cp include/config/auto.conf $kerneldir/include/config/auto.conf

        # We can also copy over all the generated files and avoid special cases
        # like version.h, but we've opted to keep this small until file creep starts
        # to happen
        if [ -e include/linux/version.h ]; then
                mkdir -p $kerneldir/include/linux
                cp include/linux/version.h $kerneldir/include/linux/version.h
        fi

        mkdir -p $kerneldir/include/generated/
        cp -fR include/generated/* $kerneldir/include/generated/

        if [ -d arch/${ARCH}/include ]; then
                mkdir -p $kerneldir/arch/${ARCH}/include/
                cp -fR arch/${ARCH}/include/* $kerneldir/arch/${ARCH}/include/
        fi

        if [ -d arch/${ARCH}/boot ]; then
                mkdir -p $kerneldir/arch/${ARCH}/boot/
                cp -fR arch/${ARCH}/boot/* $kerneldir/arch/${ARCH}/boot/
        fi

        if [ -d scripts ]; then
            for i in \
                scripts/basic/bin2c \
                scripts/basic/fixdep \
                scripts/conmakehash \
                scripts/dtc/dtc \
                scripts/kallsyms \
                scripts/kconfig/conf \
                scripts/mod/mk_elfconfig \
                scripts/mod/modpost \
                scripts/sign-file \
                scripts/sortextable;
            do
                if [ -e $i ]; then
                    mkdir -p $kerneldir/`dirname $i`
                    cp $i $kerneldir/$i
                fi
            done
        fi

        cp ${STAGING_KERNEL_DIR}/scripts/gen_initramfs_list.sh $kerneldir/scripts/

        # Make vmlinux available as soon as possible
        install -d ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}
        install -m 0644 ${KERNEL_OUTPUT} ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/${zImage_VAR}-${KERNEL_VERSION}
        install -m 0644 vmlinux ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/vmlinux-${KERNEL_VERSION}
        install -m 0644 vmlinux ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/vmlinux
}

do_install_append() {
    oe_runmake_call -C ${STAGING_KERNEL_DIR} ARCH=${ARCH} CC="${KERNEL_CC}" LD="${KERNEL_LD}" headers_install O=${STAGING_KERNEL_BUILDDIR}
}

nand_boot_flag = "${@base_contains('DISTRO_FEATURES', 'nand-boot', '1', '0', d)}"


do_deploy_prepend() {

    mv ${D}/${KERNEL_IMAGEDEST}/-${KERNEL_VERSION} ${D}/${KERNEL_IMAGEDEST}/${zImage_VAR}-${KERNEL_VERSION}
    if [ ${nand_boot_flag} == "1" ]; then
        dtb_files=`find ${B}/arch/${ARCH}/boot/dts -iname *${MACHINE_DTS_NAME}*.dtb | awk -Fdts/ '{print $NF}' | awk -F[.][d] '{print $1}'`

        # Create separate images with dtb appended to zImage for all targets.
        for d in ${dtb_files}; do
            #Strip qcom from the result if its present.
            targets=`echo ${d#${MACHINE_DTS_NAME}-}| awk '{split($0,a, "/");print a[2]}'`
            #If dtb are stored inside qcom then we need to search for them inside qcom, else inside dts.
            qcom_check=`echo ${d}| awk '{split($0,a, "/");print a[1]}'`
            if [ ${qcom_check} == "qcom" ]; then
                cat ${D}/${KERNEL_IMAGEDEST}/${zImage_VAR}-${KERNEL_VERSION} ${B}/arch/${ARCH}/boot/dts/${d}.dtb > ${B}/arch/${ARCH}/boot/dts/qcom/dtb-${zImage_VAR}-${KERNEL_VERSION}-${targets}
                ${STAGING_BINDIR_NATIVE}/dtbtool ${B}/arch/${ARCH}/boot/dts/qcom/ -s ${PAGE_SIZE} -o ${D}/${KERNEL_IMAGEDEST}/masterDTB -p ${B}/scripts/dtc/ -v
            else
                cat ${D}/${KERNEL_IMAGEDEST}/${zImage_VAR}-${KERNEL_VERSION} ${B}/arch/${ARCH}/boot/dts/${d}.dtb > ${B}/arch/${ARCH}/boot/dts/dtb-${zImage_VAR}-${KERNEL_VERSION}-${targets}
                ${STAGING_BINDIR_NATIVE}/dtbtool ${B}/arch/${ARCH}/boot/dts/ -s ${PAGE_SIZE} -o ${D}/${KERNEL_IMAGEDEST}/masterDTB -p ${B}/scripts/dtc/ -v
            fi
        done
    fi
}

do_deploy () {

    extra_mkbootimg_params=""
    if [ ${nand_boot_flag} == "1" ]; then
        extra_mkbootimg_params='--dt ${D}/${KERNEL_IMAGEDEST}/masterDTB --tags-addr ${MACHINE_KERNEL_TAGS_OFFSET}'
    fi

    mkdir -p ${DEPLOY_DIR_IMAGE}
    cmdparams='noinitrd  rw console=ttyHSL0,115200,n8 androidboot.hardware=qcom ehci-hcd.park=3 msm_rtb.filter=0x37 lpm_levels.sleep_disabled=1 ${EXTRA_KERNEL_CMD_PARAMS}'

    # Make bootimage
    ${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${D}/${KERNEL_IMAGEDEST}/${zImage_VAR}-${KERNEL_VERSION} \
        --ramdisk /dev/null \
        --cmdline "${cmdparams}" \
        --pagesize ${PAGE_SIZE} \
        --base ${MACHINE_KERNEL_BASE} \
        --ramdisk_offset 0x0 \
        ${extra_mkbootimg_params} --output ${DEPLOY_DIR_IMAGE}/${MACHINE}-boot.img
}

do_deploy_apq8096() {
	if test -n "${KERNEL_DEVICETREE}"; then	179
		for DTB in ${KERNEL_DEVICETREE}; do
                        if echo ${DTB} | grep -q '/dts/'; then
                                bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
			        DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
			fi
			DTB_BASE_NAME=`basename ${DTB} .dtb`
			DTB_NAME=`echo ${KERNEL_IMAGE_BASE_NAME} | sed "s/${MACHINE}/${DTB_BASE_NAME}/g"`
                        DTB_SYMLINK_NAME=`echo ${KERNEL_IMAGE_SYMLINK_NAME} | sed "s/${MACHINE}/${DTB_BASE_NAME}/g"`
		        DTB_PATH="${B}/arch/${ARCH}/boot/dts/${DTB}"
		        if [ ! -e "${DTB_PATH}" ]; then
			        DTB_PATH="${B}/arch/${ARCH}/boot/${DTB}"
			fi
			install -d ${DEPLOYDIR}
			install -m 0644 ${DTB_PATH} ${DEPLOYDIR}/${DTB_NAME}.dtb
			cd ${DEPLOYDIR}
			ln -sf ${DTB_NAME}.dtb ${DTB_SYMLINK_NAME}.dtb
			cd -
		done
        fi
    rm -f "${DEPLOYDIR}/devicetree.img" "${DEPLOYDIR}/boot.img" "{DEPLOYDIR}/initrd"
    mkbootimg --kernel "${D}/${KERNEL_IMAGEDEST}/-${KERNEL_VERSION}" --ramdisk /dev/null -o "${DEPLOY_DIR_IMAGE}/${MACHINE}-boot.img" --cmdline "${EXTRA_KERNEL_CMD_PARAMS}" --base "${KERNEL_BASE}" --pagesize "${PAGE_SIZE}"
}
