#Include the drone settings

# Get base product settings
require ${MACHINE}-base-image.inc

IMAGE_INSTALL += "qcacld-hl"
IMAGE_INSTALL += "iw"
IMAGE_INSTALL_remove = "wlan-opensource"