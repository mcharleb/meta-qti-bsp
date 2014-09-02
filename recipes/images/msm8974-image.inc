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
		dtbtool-native \
		"

# Image output types
IMAGE_FSTYPES = "ext4"

# The OE-Core minimal boot image files.
IMAGE_INSTALL += "task-core-boot"
IMAGE_INSTALL += "${ROOTFS_PKGMANAGE_BOOTSTRAP}"
IMAGE_INSTALL += "${CORE_IMAGE_EXTRA_INSTALL}"

# The MSM Linux minimal boot image files.
IMAGE_INSTALL += "kernel-modules"
IMAGE_INSTALL += "base-files"
IMAGE_INSTALL += "base-passwd"
IMAGE_INSTALL += "powerapp"
IMAGE_INSTALL += "powerapp-powerconfig"
IMAGE_INSTALL += "powerapp-reboot"
IMAGE_INSTALL += "powerapp-shutdown"
IMAGE_INSTALL += "reboot-daemon"
IMAGE_INSTALL += "system-core-adbd"
IMAGE_INSTALL += "system-core-usb"
IMAGE_INSTALL += "system-core-liblog"
IMAGE_INSTALL += "system-core-libcutils"
IMAGE_INSTALL += "start-scripts-backlight"
IMAGE_INSTALL += "start-scripts-firmware-links"
IMAGE_INSTALL += "alsa-utils-amixer"
IMAGE_INSTALL += "alsa-utils-aplay"
IMAGE_INSTALL += "alsa-utils-speakertest"
IMAGE_INSTALL += "alsa-utils-midi"
IMAGE_INSTALL += "udev"
IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "mm-video-oss"
IMAGE_INSTALL += "camera-hal"
IMAGE_INSTALL += "open-source-kernel-tests"
IMAGE_INSTALL += "libtbm-msm"
IMAGE_INSTALL += "libgenlock"
IMAGE_INSTALL += "mm-image-codec"
IMAGE_INSTALL += "init-audio"

# Convenience: build a compatible GDB and install gdbserver on the image
DEPENDS += "gdb-cross"
IMAGE_INSTALL += "gdbserver"
IMAGE_INSTALL += "strace"
IMAGE_INSTALL += "loc-api"
#IMAGE_INSTALL += "msm7k"

IMAGE_PREPROCESS_COMMAND += "mv ${IMAGE_ROOTFS}/lib/modules ${IMAGE_ROOTFS}/usr/lib;"

IMAGE_PREPROCESS_COMMAND += "mkdir -p ${IMAGE_ROOTFS}/cache;"
IMAGE_PREPROCESS_COMMAND += "echo ro.build.version.release=`cat ${IMAGE_ROOTFS}/etc/version ` >> ${IMAGE_ROOTFS}/build.prop;"

# Make the bootimg image file using the information available in the sysroot...
do_rootfs_append() {
	# Make bootimage
	ver=`sed -r 's/#define UTS_RELEASE "(.*)"/\1/' ${STAGING_KERNEL_DIR}/include/generated/utsrelease.h`

	# Make Device tree blobs for various hardware configurations
	dts_files=`find ${WORKSPACE}/kernel/arch/arm/boot/dts -iname *${MACHINE_DTS_NAME}*.dts | awk -F/ '{print $NF}' | awk -F[.][d] '{print $1}'`

	for d in ${dts_files}; do
		${STAGING_KERNEL_DIR}/scripts/dtc/dtc -p 4096 -O dtb -o ${STAGING_DIR_TARGET}/boot/${d}.dtb ${WORKSPACE}/kernel/arch/arm/boot/dts/${d}.dts
	done

	dtb_files=`find ${STAGING_DIR_TARGET}/boot -iname *${MACHINE_DTS_NAME}*.dtb | awk -F/ '{print $NF}' | awk -F[.][d] '{print $1}'`

	# Create separate images with dtb appended to zImage for all targets.
	for d in ${dtb_files}; do
		targets=`echo ${d#${MACHINE_DTS_NAME}-}`
		cat ${STAGING_DIR_TARGET}/boot/zImage-${ver} ${STAGING_DIR_TARGET}/boot/${d}.dtb > ${STAGING_DIR_TARGET}/boot/dtb-zImage-${ver}-${targets}
	done

	${STAGING_BINDIR_NATIVE}/dtbtool ${STAGING_DIR_TARGET}/boot/ -o ${STAGING_DIR_TARGET}/boot/masterDTB -p ${STAGING_KERNEL_DIR}/scripts/dtc/ -v

	# Updated base address according to new memory map.
	${STAGING_BINDIR_NATIVE}/mkbootimg --kernel ${STAGING_DIR_TARGET}/boot/zImage-${ver} \
        --dt ${STAGING_DIR_TARGET}/boot/masterDTB \
		--ramdisk /dev/null \
		--cmdline "noinitrd root=${MACHINE_ROOTDEV} rw init=/sbin/init --verbose loglevel=7 console=${MACHINE_CONSOLE},115200,n8 androidboot.hardware=qcom" \
		--base ${MACHINE_KERNEL_BASE} \
		--output ${DEPLOY_DIR_IMAGE}/${PN}-boot-${MACHINE}.img
}

