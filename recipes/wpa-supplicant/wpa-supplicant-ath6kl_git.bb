include wpa-supplicant.inc

PR = "${INC_PR}.0"

SRC_URI += "file://defconfig-ath6kl \
            "

do_configure() {
    install -m 0644 ${WORKDIR}/defconfig-ath6kl .config
}
