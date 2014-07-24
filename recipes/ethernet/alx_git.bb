inherit module

DESCRIPTION = "Qualcomm Atheros Gigabit Ethernet Driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

PR = "r1"

SRC_URI = "file://${WORKSPACE}/external/compat-wireless/drivers/net/ethernet/atheros/alx"

S = "${WORKDIR}/alx"

do_install() {
    module_do_install
}
