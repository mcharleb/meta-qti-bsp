inherit autotools-brokensep module
DESCRIPTION = "Shortcut Forward Engine Driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://shortcut-fe/shortcut-fe/ \
		   file://start_shortcut_fe_le "

S = "${WORKDIR}/shortcut-fe/shortcut-fe"

FILES_${PN}="/etc/init.d/start_shortcut_fe_le"

do_install() {
    module_do_install
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/start_shortcut_fe_le ${D}${sysconfdir}/init.d
}
