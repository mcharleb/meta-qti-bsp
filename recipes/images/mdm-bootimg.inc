# Force a few additional dependencies in the mix so that we get the needed
# recipes to build in the right order so we can make the bootimg file and
# our yaffs2 image...
DEPENDS = 	" \
		virtual/kernel \
		virtual/bootloader \
		pkgconfig-native \
		gtk-doc-native \
		gettext-native \
		yaffs2-utils-native \
		mkbootimg-native \
		kernel-tests \
		dtbtool-native \
		"

# Image output types
IMAGE_FSTYPES = "tar.gz yaffs2 ${INITRAMFS_FSTYPES}"

# Make the bootimg image file using the information available in the sysroot...
do_rootfs_append() {
	# Make bootimage
	ver=`sed -r 's/#define UTS_RELEASE "(.*)"/\1/' ${STAGING_DIR_TARGET}/usr/src/linux/include/generated/utsrelease.h`
	kernelsize=`awk --non-decimal-data '/ _end/ {end="0x" $1} /_stext/ {beg="0x" $1} END {size1=end-beg+4096; size=and(size1,compl(4095)); printf("%#x",size)}' ${STAGING_DIR_TARGET}/boot/System.map-${ver}`

	# Make Device tree blobs for various hardware configurations
	${STAGING_KERNEL_DIR}/scripts/dtc/dtc -p 1024 -O dtb -o ${STAGING_DIR_TARGET}/boot/${MACHINE_DTS_NAME}-rumi.dtb ${WORKSPACE}/kernel/arch/arm/boot/dts/${MACHINE_DTS_NAME}-rumi.dts
	${STAGING_KERNEL_DIR}/scripts/dtc/dtc -p 1024 -O dtb -o ${STAGING_DIR_TARGET}/boot/${MACHINE_DTS_NAME}-cdp.dtb ${WORKSPACE}/kernel/arch/arm/boot/dts/${MACHINE_DTS_NAME}-cdp.dts
	${STAGING_KERNEL_DIR}/scripts/dtc/dtc -p 1024 -O dtb -o ${STAGING_DIR_TARGET}/boot/${MACHINE_DTS_NAME}-mtp.dtb ${WORKSPACE}/kernel/arch/arm/boot/dts/${MACHINE_DTS_NAME}-mtp.dts

	# Create three separate images with dtb appended to zImage for RUMI support.
	cat ${STAGING_DIR_TARGET}/boot/zImage-${ver} ${STAGING_DIR_TARGET}/boot/${MACHINE_DTS_NAME}-rumi.dtb > ${STAGING_DIR_TARGET}/boot/dtb-zImage-${ver}-rumi
	cat ${STAGING_DIR_TARGET}/boot/zImage-${ver} ${STAGING_DIR_TARGET}/boot/${MACHINE_DTS_NAME}-cdp.dtb > ${STAGING_DIR_TARGET}/boot/dtb-zImage-${ver}-cdp
	cat ${STAGING_DIR_TARGET}/boot/zImage-${ver} ${STAGING_DIR_TARGET}/boot/${MACHINE_DTS_NAME}-mtp.dtb > ${STAGING_DIR_TARGET}/boot/dtb-zImage-${ver}-mtp
	${STAGING_BINDIR_NATIVE}/dtbtool ${STAGING_DIR_TARGET}/boot/ -o ${STAGING_DIR_TARGET}/boot/masterDTB -p ${STAGING_KERNEL_DIR}/scripts/dtc/ -v
	# Updated base address according to new memory map.
	${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${STAGING_DIR_TARGET}/boot/zImage-${ver} \
		--dt ${STAGING_DIR_TARGET}/boot/masterDTB \
		--ramdisk /dev/null \
		--cmdline "noinitrd root=/dev/mtdblock16 rw rootfstype=yaffs2 console=ttyHSL0,115200,n8 androidboot.hardware=qcom" \
		--base 0x00200000 \
		--ramdisk_offset $kernelsize \
		--output ${DEPLOY_DIR_IMAGE}/${PN}-boot-${MACHINE}.img
}
