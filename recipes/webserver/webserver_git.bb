DESCRIPTION = "Reference Webserver"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://webserver.c;startline=2;endline=27;md5=a92553d6d84b89f8abc82e7e8ee5cac5"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/webserver"

inherit autotools

S = "${WORKDIR}/webserver"

EXTRA_OEMAKE_append = " CROSS=${HOST_PREFIX}"

INITSCRIPT_NAME = "webserver"
INITSCRIPT_PARAMS = "start 98 5 . stop 15 0 1 6 ."

inherit update-rc.d

do_install() {
    install -m 0755 ${S}/webserver -D ${D}/bin/webserver
    install -m 0755 ${S}/start_webserver -D ${D}${sysconfdir}/init.d/webserver
    install -m 0644 ${S}/default_config.conf -D ${D}${sysconfdir}/webserver.conf
}
