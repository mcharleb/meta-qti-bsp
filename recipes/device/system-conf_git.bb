inherit autotools

DESCRIPTION = "Device specific config"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"
PR = "r3"

FILESPATH =+ "${WORKSPACE}:"
# Provide a baseline
SRC_URI = "file://mdm-init/"

# Update for each machine
S = "${WORKDIR}/mdm-init/"

FILES_${PN} += "${base_libdir}/firmware/wlan/qca_cld/*"
