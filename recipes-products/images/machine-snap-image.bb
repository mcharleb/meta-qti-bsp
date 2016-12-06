# List of packages installed onto the root file system as specified by the user.
include ${BASEMACHINE}/${BASEMACHINE}-snap-image.inc

require mdm-bootimg.inc

inherit core-image
