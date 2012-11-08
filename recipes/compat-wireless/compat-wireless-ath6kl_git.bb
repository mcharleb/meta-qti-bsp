include compat-wireless.inc

PR = "${INC_PR}.0"

SRC_URI += "file://ath6kl_ctrl_wlan"

do_configure() {
    ./scripts/driver-select ath6kl
}

do_install_append() {
    install -m 0755 ${WORKDIR}/ath6kl_ctrl_wlan -D ${D}${sysconfdir}/init.d/wlan
}

pkg_postinst () {
    [ -n "$D" ] && OPT="-r $D" || OPT="-s"
    update-rc.d $OPT -f wlan remove
    update-rc.d $OPT wlan start 15 2 3 4 5 . stop 85 0 6 .
}
