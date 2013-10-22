DESCRIPTION = "Daemon to start QRNG"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
BSD;md5=3775480a712fc46a69647678acb234cb"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/external/qrngd"

inherit autotools

S = "${WORKDIR}/qrngd"

EXTRA_OECONF += "--with-glib --with-common-includes=${STAGING_INCDIR}"

INITSCRIPT_NAME = "qrngd"
INITSCRIPT_PARAMS = "start 27 2 3 4 5 . stop 80 0 1 6 ."

inherit update-rc.d

do_install_append() {
        install -m 0755 ${WORKDIR}/qrngd/start_qrngd -D ${D}${sysconfdir}/init.d/qrngd
        install -m 0755 ${S}/qrngd -D ${D}/bin/qrngd
        install -m 0755 ${S}/qrngtest -D ${D}/bin/qrngtest
}
