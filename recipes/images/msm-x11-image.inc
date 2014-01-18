# List of packages to be installed by msm-x11-image

BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xcb-util \
           libxcb-dri2 \
           libxcb-xfixes \
           libxcb-shape \
           libx11-xcb \
           xserver-xf86-config "

XF86_VIDEO_MSM = "${@ '' if BASEMACHINE == 'msm8226' else 'xf86-video-msm'}"

IMAGE_INSTALL += "${XF86_VIDEO_MSM}"
IMAGE_INSTALL += "${XSERVER}"
IMAGE_INSTALL += "${POKY_BASE_INSTALL}"

IMAGE_INSTALL += "xeyes"
IMAGE_INSTALL += "xset"

IMAGE_FEATURES += "x11-mini ssh-server-dropbear"

IMAGE_LINGUAS = ""

# Use busybox as login manager
IMAGE_LOGIN_MANAGER = "busybox-static"

# Include minimum init and init scripts
IMAGE_DEV_MANAGER = "busybox-static-mdev"
IMAGE_INIT_MANAGER = "sysvinit sysvinit-pidof"
IMAGE_INITSCRIPTS = ""

export IMAGE_BASENAME = "msm-x11-image"
