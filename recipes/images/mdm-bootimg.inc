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
IMAGE_FSTYPES = "tar.gz yaffs2 ${INITRAMFS_FSTYPES}"

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

    # Updated base address according to new memory map.
    ${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${STAGING_DIR_TARGET}/boot/zImage-${ver} \
        --dt ${STAGING_DIR_TARGET}/boot/masterDTB \
        --ramdisk /dev/null \
        --cmdline "noinitrd root=/dev/mtdblock17 rw rootfstype=yaffs2 console=ttyHSL0,115200,n8 androidboot.hardware=qcom ehci-hcd.park=3" \
        --base 0x00300000 \
        --tags-addr 0x06800000 \
        --ramdisk_offset 0x0 \
        --output ${DEPLOY_DIR_IMAGE}/${PN}-boot-${MACHINE}.img
}
