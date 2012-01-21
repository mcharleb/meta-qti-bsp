DESCRIPTION = "USB network scripts"
HOMEPAGE = "http://codeaurora.org"
LICENSE = "BSD"

SRC_URI ="file://usbnet"

PR = "r1"

inherit update-rc.d

INITSCRIPT_NAME = "usbnet"
INITSCRIPT_PARAMS = "start 26 S 2 3 4 5 S . stop 80 0 1 6 ."


do_install() {
        install -m 0755 ${WORKDIR}/usbnet -D ${D}${sysconfdir}/init.d/usbnet
}

pkg_postinst-${PN} () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f usbnet remove
        update-rd.d $OPT usbnet start 26 S 2 3 4 5 S . stop 80 0 1 6 .
}