inherit autotools linux-kernel-base module

DESCRIPTION = "Qualcomm Atheros WLAN CLD driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

FILES_${PN} += "${base_libdir}/firmware/wlan/*"

PR = "r0"

SRC_URI = "file://${WORKSPACE}/wlan/qcacld-2.0"

S = "${WORKDIR}/qcacld-2.0"

EXTRA_OEMAKE = "KERN_DIR=${STAGING_KERNEL_DIR}"

do_configure_append() {
    rm -f Kbuild
}

do_compile_prepend() {
    unset LDFLAGS
}

do_install() {
    oe_runmake  INSTALL_MOD_PATH="${D}" LDFLAGS="" INSTALL_MOD_STRIP=1 \
    -C ${STAGING_KERNEL_DIR} M=${S} modules_install

    install -d ${D}${base_libdir}/firmware/wlan
    install -m 0644 ${S}/firmware_bin/WCNSS_qcom_cfg.ini ${D}${base_libdir}/firmware/wlan/qcom_cfg.ini
    install -m 0644 ${S}/firmware_bin/WCNSS_cfg.dat ${D}${base_libdir}/firmware/wlan/cfg.dat
    install -m 0644 ${S}/firmware_bin/WCNSS_qcom_wlan_nv.bin ${D}${base_libdir}/firmware/wlan/qcom_wlan_nv.bin
}
