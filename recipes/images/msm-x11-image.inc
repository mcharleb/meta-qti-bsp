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

IMAGE_INSTALL += "xeyes"
IMAGE_INSTALL += "xset"

IMAGE_FEATURES += "x11-mini ssh-server-dropbear"

export IMAGE_BASENAME = "msm-x11-image"
