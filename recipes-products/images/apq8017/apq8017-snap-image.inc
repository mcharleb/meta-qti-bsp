require apq8017-base-image.inc

IMAGE_INSTALL += "qcacld-hl"
IMAGE_INSTALL_remove = "wcnss"
IMAGE_INSTALL_remove = "wlan-opensource"
