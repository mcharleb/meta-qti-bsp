inherit update-rc.d autotools

DESCRIPTION = "Daemon to start QRNG"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
BSD;md5=3775480a712fc46a69647678acb234cb"
PR = "r2"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://external/qrngd/"
S = "${WORKDIR}/external/qrngd/"

EXTRA_OECONF += "--with-glib --with-common-includes=${STAGING_INCDIR}"

INITSCRIPT_NAME = "qrngd"
INITSCRIPT_PARAMS = "start 27 2 3 4 5 . stop 80 0 1 6 ."

do_install_append() {
        install -m 0755 ${S}/start_qrngd -D ${D}${sysconfdir}/init.d/qrngd
        install -m 0755 ${S}/qrngd -D ${D}/bin/qrngd
        install -m 0755 ${S}/qrngtest -D ${D}/bin/qrngtest
}
