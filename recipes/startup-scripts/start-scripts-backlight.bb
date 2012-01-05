DESCRIPTION = "Backlight scripts"
HOMEPAGE = "http://codeaurora.org"
LICENSE = "BSD"

SRC_URI +="file://backlight"

PR = "r2"

inherit update-rc.d

INITSCRIPT_NAME = "backlight"

do_install() {
        install -m 0755 ${WORKDIR}/backlight -D ${D}${sysconfdir}/init.d/backlight
}

pkg_postinst-${PN} () {
        update-alternatives --install ${sysconfdir}/init.d/backlight backlight backlight 60
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f backlight remove
        update-rd.d $OPT backlight start 25 S 2 3 4 5 S . stop 80 0 1 6 .
}
