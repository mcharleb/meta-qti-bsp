# List of packages to be installed by msm-x11-image

XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-video-msm \
           xcb-util \
           libxcb-dri2 \
           libxcb-xfixes \
           libxcb-shape \
           libx11-xcb \
           xserver-xf86-config "

IMAGE_INSTALL += "${XSERVER}"
IMAGE_INSTALL += "${CORE_IMAGE_BASE_INSTALL}"

IMAGE_INSTALL += "xeyes"
IMAGE_INSTALL += "xset"

IMAGE_FEATURES += "x11-base ssh-server-dropbear"

IMAGE_LINGUAS = ""

# Use busybox as login manager
IMAGE_LOGIN_MANAGER = "busybox-static"

# Include minimum init and init scripts
IMAGE_DEV_MANAGER = "busybox-static-mdev"
IMAGE_INIT_MANAGER = "sysvinit sysvinit-pidof"
IMAGE_INITSCRIPTS = ""

export IMAGE_BASENAME = "msm-x11-image"
