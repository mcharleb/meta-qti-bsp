# List of packages to be installed by mxm-x11-image

XSERVER = "xserver-xorg \
           xserver-xorg-extension-dri \
           xserver-xorg-extension-dri2 \
           xf86-input-evdev \
           xf86-input-tslib \
           xf86-video-msm \
           libx11-xcb \
           xcb-util \
           libxcb-dri2 \
           libxcb-xfixes \
           libxcb-shape \
           xserver-xorg-conf "

IMAGE_INSTALL = "\
    ${XSERVER} \
    task-base-extended \
    angstrom-x11-base-depends \
    ${SPLASH} \
    ${ANGSTROM_EXTRA_INSTALL}"

IMAGE_INSTALL += "adbd start-scripts-usbnet start-scripts-backlight alsa-utils-amixer alsa-utils-aplay alsa-utils-speakertest alsa-utils-midi"

IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "xeyes"
IMAGE_INSTALL += "mm-core-oss mm-video-oss"
IMAGE_INSTALL += "xf86-video-v4l2"
IMAGE_INSTALL += "${@base_contains('MACHINE_FEATURES', 'mm-camera', 'start-scripts-mm-qcamera-daemon', '', d)}"

# Convenience: build a compatible GDB and install gdbserver on the image
DEPENDS += "gdb-cross"
IMAGE_INSTALL += "gdbserver"
