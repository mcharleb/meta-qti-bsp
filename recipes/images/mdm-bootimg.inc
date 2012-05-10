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
		"

# Image output types
IMAGE_FSTYPES = "tar.gz yaffs2 ${INITRAMFS_FSTYPES}"

# Make the bootimg image file using the information available in the sysroot...
do_rootfs_append() {
	gencpio="${STAGING_KERNEL_DIR}/usr/gen_init_cpio"
	geninit="${COREBASE}/meta-msm/recipes/busybox/files/geninit"
	$gencpio $geninit | gzip -n -9 > ${DEPLOY_DIR_IMAGE}/${PN}-initrd.gz

	ver=`sed -r 's/#define UTS_RELEASE "(.*)"/\1/' ${STAGING_DIR_TARGET}/usr/src/linux/include/generated/utsrelease.h`
	kernelsize=`awk --non-decimal-data '/ _end/ {end="0x" $1} /_stext/ {beg="0x" $1} END {size1=end-beg+4096; size=and(size1,compl(4095)); printf("%#x",size)}' ${STAGING_DIR_TARGET}/boot/System.map-${ver}`

	${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${STAGING_DIR_TARGET}/boot/Image-${ver} \
		--ramdisk ${DEPLOY_DIR_IMAGE}/${PN}-${MACHINE}.cpio.gz \
		--cmdline "noinitrd root=/dev/mtdblock14 rw rootfstype=yaffs2 console=ttyHSL0,115200,n8 androidboot.hardware=qcom" \
		--base 0x40800000 \
		--ramdisk_offset $kernelsize \
		--output ${DEPLOY_DIR_IMAGE}/${PN}-boot-${MACHINE}.img
}
