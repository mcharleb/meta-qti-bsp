#Include the drone settings

# Get base product settings
require ${MACHINE}-base-image.inc

IMAGE_INSTALL += "q6-admin-adsp"
IMAGE_INSTALL += "q6-admin-slpi"
IMAGE_INSTALL += "lib32-live555-qti"
IMAGE_INSTALL += "lib32-media"
IMAGE_INSTALL += "iw"
IMAGE_INSTALL += "av-frameworks"
IMAGE_INSTALL += "sigma-dut"
