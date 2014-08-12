# Force a few additional dependencies in the mix so that we get the needed
# recipes to build in the right order so we can make the bootimg file and
# our images...
DEPENDS = " \
             virtual/kernel \
             virtual/bootloader \
             pkgconfig-native \
             gtk-doc-native \
             gettext-native \
             yaffs2-utils-native \
             genext2fs-native \
             e2fsprogs-native \
             kernel-tests \
"
# Image output types
IMAGE_FSTYPES = "tar.gz ${INITRAMFS_FSTYPES}"

IMAGE_PREPROCESS_COMMAND += "mkdir -p ${IMAGE_ROOTFS}/cache;"
IMAGE_PREPROCESS_COMMAND += "echo ro.build.version.release=`cat ${IMAGE_ROOTFS}/etc/version ` >> ${IMAGE_ROOTFS}/build.prop;"
