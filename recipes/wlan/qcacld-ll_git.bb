inherit module

DESCRIPTION = "Qualcomm Atheros WLAN CLD driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

FILES_${PN} += "${base_libdir}/firmware/wlan/*"

PR = "r3"

SRC_URI = "file://${WORKSPACE}/wlan/qcacld-2.0"

S = "${WORKDIR}/qcacld-2.0"

FIRMWARE_PATH = "${D}${base_libdir}/firmware/wlan/qca_cld"

do_install() {
    module_do_install

    install -d ${FIRMWARE_PATH}
    install -m 0644 ${S}/firmware_bin/WCNSS_qcom_cfg.ini ${FIRMWARE_PATH}/
    install -m 0644 ${S}/firmware_bin/WCNSS_cfg.dat ${FIRMWARE_PATH}/
    touch ${FIRMWARE_PATH}/wlan_mac.bin

    install -m 0644 CORE/SVC/external/wlan_nlink_common.h -D ${D}${includedir}/qcacld/wlan_nlink_common.h
}
