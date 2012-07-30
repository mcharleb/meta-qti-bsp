DESCRIPTION = "Rebooter daemon"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://reboot-daemon.c;startline=1;endline=28;md5=1b281a0dce72d4076361d50e798a5f05"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/reboot-daemon"

inherit autotools

S = ${WORKDIR}/reboot-daemon

EXTRA_OEMAKE_append = " CROSS=${HOST_PREFIX}"

do_install() {
    install -m 0755 ${S}/reboot-daemon -D ${D}/sbin/reboot-daemon
}
