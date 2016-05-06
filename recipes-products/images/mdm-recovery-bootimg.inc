# Force a few additional dependencies in the mix so that we get the needed
# recipes to build in the right order so we can make the bootimg file and
# our image...
DEPENDS = " \
    virtual/kernel \
    virtual/bootloader \
    pkgconfig-native \
    gtk-doc-native \
    gettext-native \
    mkbootimg-native \
    dtbtool-native \
"

# Image output types
IMAGE_FSTYPES = "tar.gz"
