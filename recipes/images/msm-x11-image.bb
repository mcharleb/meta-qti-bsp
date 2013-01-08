DESCRIPTION = "Minimal X11 image for MSM devices"

# Open source packages
include recipes/images/${MACHINE}-image.inc
include recipes/images/msm-x11-image.inc

inherit core-image
