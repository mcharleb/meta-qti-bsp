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
