require mdm-ramdisk.inc
# Force a few additional dependencies in the mix so that we get the needed
# recipes to build in the right order so we can make the bootimg file and
# our yaffs2 image...
DEPENDS = " \
    virtual/kernel \
    pkgconfig-native \
    gtk-doc-native \
    gettext-native \
    yaffs2-utils-native \
    mkbootimg-native \
    kernel-tests \
    dtbtool-native \
"
DEPENDS += ${@base_conditional('MACHINE', 'mdmkrypton', '', 'virtual/bootloader', d)}

# Image output types
IMAGE_FSTYPES = "tar.gz ext4 yaffs2 ${INITRAMFS_FSTYPES}"

# Make the bootimg image file using the information available in the sysroot...
do_rootfs_append_mdmkrypton() {
    # Make ramdisk
    do_ramdisk_create
}

do_rootfs_append() {
    # Make bootimage
    ver=`sed -r 's/#define UTS_RELEASE "(.*)"/\1/' ${STAGING_KERNEL_DIR}/include/generated/utsrelease.h`

    dtb_files=`find ${STAGING_KERNEL_DIR}/arch/arm/boot/dts -iname *${MACHINE_DTS_NAME}*.dtb | awk -F/ '{print $NF}' | awk -F[.][d] '{print $1}'`

    # Create separate images with dtb appended to zImage for all targets.
    for d in ${dtb_files}; do
       targets=`echo ${d#${MACHINE_DTS_NAME}-}`
       cat ${STAGING_DIR_TARGET}/boot/zImage-${ver} ${STAGING_KERNEL_DIR}/arch/arm/boot/dts/${d}.dtb > ${STAGING_KERNEL_DIR}/arch/arm/boot/dts/dtb-zImage-${ver}-${targets}
    done

    ${STAGING_BINDIR_NATIVE}/dtbtool ${STAGING_KERNEL_DIR}/arch/arm/boot/dts/ -o ${STAGING_DIR_TARGET}/boot/masterDTB -p ${STAGING_KERNEL_DIR}/scripts/dtc/ -v

    kernelbase=0x00000000
    kernelsize=`awk --non-decimal-data '/ _end/ {end="0x" $1} /_stext/ {beg="0x" $1} END {size1=end-beg+4096; size=and(size1,compl(4095)); printf("%#x",size)}' ${STAGING_DIR_TARGET}/boot/System.map-${ver}`
    __kernelbase=`echo ${kernelbase} | sed 's/0x//'`
    __kernelsize=`echo ${kernelsize} | sed 's/0x//'`
    __ramdiskbase=`echo "ibase=16; ${__kernelsize}+${__kernelbase}" | bc`
    ramdiskbase="0x"`echo "obase=16;${__ramdiskbase}" | bc`

    # Updated base address according to new memory map.
    ${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${STAGING_DIR_TARGET}/boot/zImage-${ver} \
        --dt ${STAGING_DIR_TARGET}/boot/masterDTB \
        --ramdisk ${DEPLOY_DIR_IMAGE}/${PN}-initrd.gz \
        --cmdline "noinitrd root=/dev/mtdblock15 rw rootfstype=yaffs2 console=ttyHSL0,115200,n8 androidboot.hardware=qcom ehci-hcd.park=3 mem=16M@0x00000000 mem=17M@0x06D00000 mem=128M@0x08000000" \
        --base ${kernelbase} \
        --tags-addr 0x00900000 \
        --ramdisk_offset ${ramdiskbase} \
        --output ${DEPLOY_DIR_IMAGE}/${PN}-boot-${MACHINE}.img
}
