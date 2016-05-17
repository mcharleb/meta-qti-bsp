# Force a few additional dependencies in the mix so that we get the needed
# recipes to build in the right order so we can make the bootimg file and
# our images...
DEPENDS = " \
             virtual/kernel \
             pkgconfig-native \
             gtk-doc-native \
             gettext-native \
             e2fsprogs-native \
             ext4-utils-native \
             mtd-utils-native \
"

DEPENDS_append_aarch64 += "lib32-lk"
DEPENDS_append_arm += "lk"

# Image output types
IMAGE_FSTYPES = "tar.gz ${INITRAMFS_FSTYPES}"

IMAGE_PREPROCESS_COMMAND_prepend = " gen_buildprop;"

gen_buildprop() {
   mkdir -p ${IMAGE_ROOTFS}/cache
   echo ro.build.version.release=`cat ${IMAGE_ROOTFS}/etc/version ` >> ${IMAGE_ROOTFS}/build.prop
   echo ${MACHINE} >> ${IMAGE_ROOTFS}/target
}
