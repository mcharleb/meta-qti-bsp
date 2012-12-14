require msm-x11-image.inc

IMAGE_INSTALL += "system-core-adbd"
IMAGE_INSTALL += "system-core-usb"
IMAGE_INSTALL += "system-core-liblog"
IMAGE_INSTALL += "system-core-libcutils"
IMAGE_INSTALL += "start-scripts-backlight"
IMAGE_INSTALL += "alsa-utils-amixer"
IMAGE_INSTALL += "alsa-utils-aplay"
IMAGE_INSTALL += "alsa-utils-speakertest"
IMAGE_INSTALL += "alsa-utils-midi"
IMAGE_INSTALL += "udev"
IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "mm-core-oss"

# Convenience: build a compatible GDB and install gdbserver on the image
DEPENDS += "gdb-cross"
IMAGE_INSTALL += "gdbserver"
IMAGE_INSTALL += "strace"
IMAGE_INSTALL += "msm7k"

# Image output types
IMAGE_FSTYPES = "ext4"
IMAGE_FSTYPES += "fastboot"
#IMAGE_FSTYPES += "yaffs2"

# User space partition size
IMAGE_ROOTFS_SIZE = "204800"

