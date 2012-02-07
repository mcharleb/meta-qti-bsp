DESCRIPTION = "Powerapp tools"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

SRC_URI = "file://${WORKSPACE}/system/core/powerapp"

PACKAGES =+ "${PN}-reboot ${PN}-shutdown ${PN}-powerconfig"
FILES_${PN}-reboot = "${sysconfdir}/init.d/reboot"
FILES_${PN}-shutdown = "${sysconfdir}/init.d/shutdown"
FILES_${PN}-powerconfig = "${sysconfdir}/init.d/power_config"

# TODO - add depedency on virtual/sh
PROVIDES =+ "${PN}-reboot ${PN}-shutdown ${PN}-powerconfig"

inherit autotools

PR = "r1"

S = "${WORKDIR}/powerapp"

do_install() {
        install -m 0755 ${WORKDIR}/powerapp/powerapp -D ${D}/sbin/powerapp
        install -m 0755 ${WORKDIR}/powerapp/reboot -D ${D}${sysconfdir}/init.d/reboot
        install -m 0755 ${WORKDIR}/powerapp/shutdown -D ${D}${sysconfdir}/init.d/shutdown
        install -m 0755 ${WORKDIR}/powerapp/start_power_config -D ${D}${sysconfdir}/init.d/power_config
        ln ${D}${base_sbindir}/powerapp ${D}${base_sbindir}/sys_reboot
        ln ${D}${base_sbindir}/powerapp ${D}${base_sbindir}/sys_shutdown
}

pkg_postinst_${PN}-reboot () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f reboot remove
        update-rc.d $OPT reboot start 99 6 .
}

pkg_postinst_${PN}-shutdown () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f shutdown remove
        update-rc.d $OPT shutdown start 99 0 .
}

pkg_postinst_${PN}-powerconfig () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f power_config remove
        update-rc.d $OPT power_config start 50 2 3 4 5 . stop 50 0 1 6 .
}
