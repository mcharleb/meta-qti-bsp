inherit kernel

DESCRIPTION = "QuIC Linux Kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
COMPATIBLE_MACHINE = "(mdm9640|mdm9640-perf|mdm9607|mdm9607-perf|mdmcalifornium|apq8009)"
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"
EXTRA_KERNEL_CMD_PARAMS ?= ""

# Default image type is zImage, change here if needed.
#KERNEL_IMAGETYPE = "zImage"
# Where built kernel lies in the kernel tree
#KERNEL_OUTPUT ?= "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
#KERNEL_IMAGEDEST = "boot"
KERNEL_IMAGETYPE_FOR_MAKE = ""

# Provide a config baseline for things so the kernel will build...
KERNEL_DEFCONFIG          = "mdm_defconfig"
KERNEL_DEFCONFIG_apq8009  = "msm8909_defconfig"
KERNEL_DEFCONFIG_mdm9607  = "mdm9607_defconfig"
KERNEL_DEFCONFIG_mdm9607-perf  = "mdm9607-perf_defconfig"
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
PR = "r3"

DEPENDS += "dtbtool-native mkbootimg-native"
PACKAGES = "kernel kernel-base kernel-vmlinux kernel-dev kernel-modules"
RDEPENDS_kernel-base = ""

# Put the zImage in the kernel-dev pkg
FILES_kernel-dev += "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}"

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
        install -m 0644 ${KERNEL_OUTPUT} ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
        install -m 0644 vmlinux ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/vmlinux-${KERNEL_VERSION}
        install -m 0644 vmlinux ${STAGING_DIR_TARGET}/${KERNEL_IMAGEDEST}/vmlinux
}

do_install_append() {
    oe_runmake_call -C ${STAGING_KERNEL_DIR} ARCH=${ARCH} CC="${KERNEL_CC}" LD="${KERNEL_LD}" headers_install O=${STAGING_KERNEL_BUILDDIR}
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
