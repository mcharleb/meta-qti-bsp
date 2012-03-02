DESCRIPTION = "Start up script for MM Camera Daemon"
HOMEPAGE = "http://codeaurora.org"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/start_mm-qcamera-daemon;startline=3;endline=28;md5=69b02bf5dd424a4c5bb80becc3368f11"

SRC_URI +="file://start_mm-qcamera-daemon"

PR = "r1"

inherit update-rc.d

INITSCRIPT_NAME = "mm-qcamera-daemon"

do_install() {
        install -m 0755 ${WORKDIR}/start_mm-qcamera-daemon -D ${D}${sysconfdir}/init.d/mm-qcamera-daemon
}

pkg_postinst-${PN} () {
        update-alternatives --install ${sysconfdir}/init.d/mm-qcamera-daemon start-mm-camera-daemon mm-qcamera-daemon 60
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f mm-qcamera-daemon remove
        update-rd.d $OPT mm-qcamera-daemon start 25 S 2 3 4 5 S . stop 75 0 1 6 .
}
