DESCRIPTION = "Start up script for 9615 platform"
HOMEPAGE = "http://codeaurora.org"
LICENSE = "BSD"

SRC_URI +="file://start_adbd"

PR = "r1"

inherit update-rc.d

INITSCRIPT_NAME = "adbd"

do_install() {
        install -m 0755 ${WORKDIR}/start_adbd -D ${D}${sysconfdir}/init.d/adbd
}

pkg_postinst-${PN} () {
        update-alternatives --install ${sysconfdir}/init.d/adbd start-adbd adbd 60
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f adbd remove
        update-rd.d $OPT adbd start 25 S 2 3 4 5 S . stop 80 0 1 6 .
}
