# The mkbootimg production process.
require msm-x11-image.inc

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
IMAGE_FSTYPES = "ext4"

# The OE-Core minimal boot image files.
IMAGE_INSTALL += "task-core-boot"
IMAGE_INSTALL += "${ROOTFS_PKGMANAGE_BOOTSTRAP}"
IMAGE_INSTALL += "${POKY_EXTRA_INSTALL}"

# The MSM Linux minimal boot image files.
IMAGE_INSTALL += "base-files"
IMAGE_INSTALL += "base-passwd"

IMAGE_INSTALL += "system-core-adbd"
IMAGE_INSTALL += "system-core-usb"
IMAGE_INSTALL += "system-core-liblog"
IMAGE_INSTALL += "system-core-libcutils"
#IMAGE_INSTALL += "start-scripts-backlight"
IMAGE_INSTALL += "start-scripts-firmware-links"
IMAGE_INSTALL += "alsa-utils-amixer"
IMAGE_INSTALL += "alsa-utils-aplay"
IMAGE_INSTALL += "alsa-utils-speakertest"
IMAGE_INSTALL += "alsa-utils-midi"
IMAGE_INSTALL += "udev"
IMAGE_INSTALL += "libstdc++"
#IMAGE_INSTALL += "mm-core-oss"

# Convenience: build a compatible GDB and install gdbserver on the image
DEPENDS += "gdb-cross"
IMAGE_INSTALL += "gdbserver"
IMAGE_INSTALL += "strace"
#IMAGE_INSTALL += "msm7k"

IMAGE_PREPROCESS_COMMAND += "mkdir -p ${IMAGE_ROOTFS}/cache;"
IMAGE_PREPROCESS_COMMAND += "echo ro.build.version.release=`cat ${IMAGE_ROOTFS}/etc/version ` >> ${IMAGE_ROOTFS}/build.prop;"

# Make the bootimg image file using the information available in the sysroot...
do_rootfs_append() {
	# Make bootimage
	ver=`sed -r 's/#define UTS_RELEASE "(.*)"/\1/' ${STAGING_KERNEL_DIR}/include/generated/utsrelease.h`

	# Updated base address according to new memory map.
	${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${STAGING_DIR_TARGET}/boot/zImage-${ver} \
		--ramdisk /dev/null \
		--cmdline "noinitrd root=/dev/mmcblk0p12 rw init=/sbin/init --verbose loglevel=7 console=ttyHSL0,115200,n8 androidboot.hardware=qcom" \
		--base 0x80200000 \
		--output ${DEPLOY_DIR_IMAGE}/${PN}-boot-${MACHINE}.img
}

