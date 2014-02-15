inherit autotools

DESCRIPTION = "Qualcomm Atheros WLAN CLD tools"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"
DEPENDS = "diag"

PR = "r0"

EXTRA_OECONF = "--with-glib"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://wlan/qcacld-2.0"

S = "${WORKDIR}/qcacld-2.0/tools"

