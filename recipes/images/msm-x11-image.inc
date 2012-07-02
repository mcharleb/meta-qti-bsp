# List of packages to be installed by mxm-x11-image

XSERVER = "xserver-xorg \
           xserver-xorg-extension-dri \
           xserver-xorg-extension-dri2 \
           xf86-input-evdev \
           xf86-video-msm \
           xcb-util \
           libxcb-dri2 \
           libxcb-xfixes \
           libxcb-shape \
           libx11-xcb \
           xserver-xf86-config "

IMAGE_INSTALL += "${XSERVER}"
IMAGE_INSTALL += "${POKY_BASE_INSTALL}"

IMAGE_INSTALL += "system-core-adbd"
IMAGE_INSTALL += "system-core-usb"
IMAGE_INSTALL += "system-core-liblog"
IMAGE_INSTALL += "start-scripts-backlight"
IMAGE_INSTALL += "alsa-utils-amixer"
IMAGE_INSTALL += "alsa-utils-aplay"
IMAGE_INSTALL += "alsa-utils-speakertest"
IMAGE_INSTALL += "alsa-utils-midi"
IMAGE_INSTALL += "udev"
IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "xeyes"
IMAGE_INSTALL += "xset"
IMAGE_INSTALL += "${@base_contains('MACHINE_FEATURES', 'mm-video-oss', 'mm-video-oss', '', d)}"
IMAGE_INSTALL += "mm-core-oss"

# Convenience: build a compatible GDB and install gdbserver on the image
DEPENDS += "gdb-cross"
IMAGE_INSTALL += "gdbserver"
IMAGE_INSTALL += "strace"

IMAGE_FEATURES += "x11-mini ssh-server-dropbear"

export IMAGE_BASENAME = "msm-x11-image"

# Image output types
IMAGE_FSTYPES = "ext4"
IMAGE_FSTYPES += "fastboot"

# User space partition size
IMAGE_ROOTFS_SIZE = "204800"
