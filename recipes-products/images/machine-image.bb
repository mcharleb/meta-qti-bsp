# List of packages installed onto the root file system as specified by the user.
require ${BASEMACHINE}/${BASEMACHINE}-base-image.inc
IMAGE_FEATURES_append_mdm9607 = "read-only-rootfs"

require mdm-bootimg.inc

inherit core-image
