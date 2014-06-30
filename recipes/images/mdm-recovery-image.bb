# List of FOSS packages installed onto the root file system as specified by the user.
include ${MACHINE}-recovery-image.inc

IMAGE_LINGUAS = ""

# Use busybox as login manager
IMAGE_LOGIN_MANAGER = "busybox-static"

# Include minimum init and init scripts
IMAGE_DEV_MANAGER = "busybox-static-mdev"
IMAGE_INIT_MANAGER = "sysvinit sysvinit-pidof"
IMAGE_INITSCRIPTS = ""

require mdm-ota-target-image.inc

inherit core-image
