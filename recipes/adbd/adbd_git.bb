DESCRIPTION = "Andriod Debug Bridge. It provides connectivity over USB for running a shell, transferring files etc."
HOMEPAGE = "http://developer.android.com/guide/developing/tools/adb.html"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://adb/NOTICE;md5=2ddb23e63b1f9c3c46aaa4195f819a6d"

SRC_URI = "file://${WORKSPACE}/system/core"

inherit autotools

PR = "r1"

S = "${WORKDIR}/core"

INITSCRIPT_NAME = "adbd"
INITSCRIPT_PARAMS = "start 25 S 2 3 4 5 S . stop 80 0 1 6 ."

inherit update-rc.d

do_install() {
        install -m 0755 ${WORKDIR}/core/adb/adbd -D ${D}/sbin/adbd
        install -m 0755 ${WORKDIR}/core/adb/start_adbd -D ${D}${sysconfdir}/init.d/adbd
}

pkg_postinst () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f adbd remove
        update-rc.d $OPT adbd start 25 S 2 3 4 5 S . stop 80 0 1 6 .
}
