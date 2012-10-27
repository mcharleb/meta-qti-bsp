include hostap-daemon.inc

PR = "${INC_PR}.0"

SRC_URI += "file://defconfig-ath6kl \
            file://hostapd_wlan0_wpa_all.conf \
            "

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig-ath6kl .config
}

do_install_append() {
    install -D -m 0644 ${WORKDIR}/hostapd_wlan0_wpa_all.conf ${D}/etc/hostapd.conf
}

CONFFILES_${PN} += "${sysconfdir}/hostapd.conf"
