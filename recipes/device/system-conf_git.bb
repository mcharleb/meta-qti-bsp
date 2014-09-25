inherit autotools

DESCRIPTION = "Device specific config"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"
PR = "r2"

FILESPATH =+ "${WORKSPACE}:"
# Provide a baseline
SRC_URI_mdm9635 = "file://device/qcom/krypton/"
SRC_URI_mdmzirc = "file://mdm-init/"

# Update for each machine
S_mdm9635 = "${WORKDIR}/device/qcom/krypton/"
S_mdmzirc = "${WORKDIR}/mdm-init/"
