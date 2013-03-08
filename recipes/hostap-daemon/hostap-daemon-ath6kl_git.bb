include hostap-daemon.inc

PR = "${INC_PR}.1"

SRC_URI += "file://defconfig-ath6kl \
            file://hostapd_wlan0_wpa_all.conf \
            file://wlan0-ar6k-all.conf"

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig-ath6kl .config
}

do_configure_append() {
    echo "CFLAGS += -I${WORKSPACE}/wlan/include" >> .config
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/include" >> .config
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/os/linux/include" >> .config
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/wlan/include" >> .config
}

do_install_append() {
    install -D -m 0644 ${WORKDIR}/hostapd_wlan0_wpa_all.conf ${D}${sysconfdir}/AR6004_hostapd.conf
    install -D -m 0644 ${WORKDIR}/wlan0-ar6k-all.conf ${D}${sysconfdir}/AR6003_hostapd.conf
    ln -s ${sysconfdir}/AR6004_hostapd.conf ${D}${sysconfdir}/hostapd.conf
}

CONFFILES_${PN} += "${sysconfdir}/AR6004_hostapd.conf \
                    ${sysconfdir}/AR6003_hostapd.conf"
