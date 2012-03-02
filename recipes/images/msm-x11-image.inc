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

IMAGE_INSTALL += "system-core-adbd start-scripts-usbnet start-scripts-backlight alsa-utils-amixer alsa-utils-aplay alsa-utils-speakertest alsa-utils-midi"

IMAGE_INSTALL += "udev"
IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "xeyes"
IMAGE_INSTALL += "xset"
IMAGE_INSTALL += "mm-video-oss"
IMAGE_INSTALL += "mm-core-oss"
IMAGE_INSTALL += "${@base_contains('MACHINE_FEATURES', 'mm-camera', 'start-scripts-mm-qcamera-daemon', '', d)}"

# Convenience: build a compatible GDB and install gdbserver on the image
DEPENDS += "gdb-cross"
IMAGE_INSTALL += "gdbserver"

IMAGE_FEATURES += "x11-mini ssh-server-dropbear"

export IMAGE_BASENAME = "msm-x11-image"

# Image output types
IMAGE_FSTYPES = "ext4"
IMAGE_FSTYPES += "fastboot"
