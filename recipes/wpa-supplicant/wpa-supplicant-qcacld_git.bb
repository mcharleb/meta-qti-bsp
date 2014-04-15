include wpa-supplicant.inc

PR = "${INC_PR}.0"

SRC_URI = "file://${WORKSPACE}/external/wpa_supplicant_8"
SRC_URI += "file://defconfig-qcacld"

DEPENDS += "qmi"

S = "${WORKDIR}/wpa_supplicant_8/wpa_supplicant"

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig-qcacld .config
}
