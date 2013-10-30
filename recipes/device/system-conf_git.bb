inherit autotools

DESCRIPTION = "Device specific config"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"
PR = "r0"

# Provide a baseline
SRC_URI_mdmkrypton = "file://${WORKSPACE}/device/qcom/krypton"
SRC_URI ?= "file://${WORKSPACE}/device/qcom/krypton"

# Update for each machine
S_mdmkrypton = "${WORKDIR}/krypton"
S ?= "${WORKDIR}/krypton"
