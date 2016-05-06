inherit autotools-brokensep update-rc.d
DESCRIPTION = "Modem init"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
PR = "r6"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://init_mss/"

S = "${WORKDIR}/init_mss/"

INITSCRIPT_NAME = "init_sys_mss"
INITSCRIPT_PARAMS = "start 38 S ."

do_install() {
    install -m 0755 ${S}/init_mss -D ${D}/sbin/init_mss
    install -m 0755 ${S}/start_mss -D ${D}${sysconfdir}/init.d/init_sys_mss
}
