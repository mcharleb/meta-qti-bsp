# Force a few additional dependencies in the mix so that we get the needed
# recipes to build in the right order so we can make the bootimg file and
# our images...
DEPENDS = " \
             virtual/kernel \
             pkgconfig-native \
             gtk-doc-native \
             gettext-native \
             yaffs2-utils-native \
             e2fsprogs-native \
             ext4-utils-native \
"

DEPENDS_append_mdm9640 += "virtual/bootloader"

# Image output types
IMAGE_FSTYPES = "tar.gz ${INITRAMFS_FSTYPES}"

IMAGE_PREPROCESS_COMMAND_prepend = " gen_buildprop;"
IMAGE_PREPROCESS_COMMAND_mdm9640 += "gen_target;"

gen_buildprop() {
   mkdir -p ${IMAGE_ROOTFS}/cache
   echo ro.build.version.release=`cat ${IMAGE_ROOTFS}/etc/version ` >> ${IMAGE_ROOTFS}/build.prop
}

gen_target() {
   echo ${MACHINE} >> ${IMAGE_ROOTFS}/target
}
