inherit autotools linux-kernel-base

DESCRIPTION = "Hostap"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab87f20cd7e8c0d0a6539b34d3791d0e"

FILES_${PN} += "\
        /usr/bin \
        /usr/sbin \
        "
FILES_${PN}-dbg += "\
        /usr/bin/.debug \
        /usr/sbin/.debug \
        "

PR = "r2"

DEPENDS = "openssl qmi"

SRC_URI = "file://${WORKSPACE}/external/hostap"

S = "${WORKDIR}/hostap"

SUPPLICANT_CONFIG = "${S}/wpa_supplicant/.config"
HOSTAPD_CONFIG = "${S}/hostapd/.config"

do_configure() {
    echo "CFLAGS += -I${STAGING_INCDIR}" >> ${SUPPLICANT_CONFIG}
    echo "LDFLAGS += -L${STAGING_LIBDIR}" >> ${SUPPLICANT_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/include" >> ${SUPPLICANT_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/include" >> ${SUPPLICANT_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/os/linux/include" >> ${SUPPLICANT_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/wlan/include" >> ${SUPPLICANT_CONFIG}

    echo "CFLAGS += -I${STAGING_INCDIR}" >> ${HOSTAPD_CONFIG}
    echo "LDFLAGS += -L${STAGING_LIBDIR}" >> ${HOSTAPD_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/include" >> ${HOSTAPD_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/include" >> ${HOSTAPD_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/os/linux/include" >> ${HOSTAPD_CONFIG}
    echo "CFLAGS += -I${WORKSPACE}/wlan/host/wlan/include" >> ${HOSTAPD_CONFIG}
}

do_compile() {
    make -C src/crypto/
    make -C hostapd/
    make -C wpa_supplicant/
}

do_install() {
    make -C hostapd/ install DESTDIR=${D}
    make -C wpa_supplicant/ install DESTDIR=${D}
    install -D -m 0644 ${S}/hostapd/config/ar6k-all.conf ${D}${sysconfdir}/hostapd.conf
}
