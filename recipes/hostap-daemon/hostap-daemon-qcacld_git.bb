include hostap-daemon.inc

PR = "${INC_PR}.0"

SRC_URI = "file://${WORKSPACE}/external/wpa_supplicant_8"
SRC_URI += "file://defconfig-qcacld"

S = "${WORKDIR}/wpa_supplicant_8/hostapd"

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig-qcacld .config
}

