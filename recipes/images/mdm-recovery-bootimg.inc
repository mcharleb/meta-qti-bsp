# Force a few additional dependencies in the mix so that we get the needed
# recipes to build in the right order so we can make the bootimg file and
# our yaffs2 image...
DEPENDS = " \
    virtual/kernel \
    virtual/bootloader \
    pkgconfig-native \
    gtk-doc-native \
    gettext-native \
    yaffs2-utils-native \
    mkbootimg-native \
    dtbtool-native \
"

# Image output types
IMAGE_FSTYPES = "tar.gz yaffs2"

# Make the bootimg image file using the information available in the sysroot...
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

    # Updated base address according to new memory map.
    ${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${STAGING_DIR_TARGET}/boot/zImage-${ver} \
        --dt ${STAGING_DIR_TARGET}/boot/masterDTB \
        --ramdisk /dev/null \
        --cmdline "noinitrd rw rootfstype=yaffs2 console=ttyHSL0,115200,n8 androidboot.hardware=qcom ehci-hcd.park=3" \
        --base ${kernelbase} \
        --tags-addr 0x00f00000 \
        --ramdisk_offset 0x0 \
        --output ${DEPLOY_DIR_IMAGE}/${PN}-boot-${MACHINE}.img
}
