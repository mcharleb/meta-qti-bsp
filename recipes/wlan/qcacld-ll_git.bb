inherit autotools module

DESCRIPTION = "Qualcomm Atheros WLAN CLD driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

FILES_${PN} += "${base_libdir}/firmware/wlan/*"

PR = "r4"

# This DEPENDS is to serialize kernel module builds
DEPENDS_mdm9635 = "rtsp-alg"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://wlan/qcacld-2.0/"

S = "${WORKDIR}/wlan/qcacld-2.0/"

FIRMWARE_PATH = "${D}${base_libdir}/firmware/wlan/qca_cld"
EXTRA_OEMAKE = "KERN_DIR=${STAGING_KERNEL_DIR} -C ${STAGING_KERNEL_DIR}"

do_configure_append() {
    rm -f Kbuild
}

do_compile_prepend() {
    unset LDFLAGS
}

do_install_append() {
    install -d ${FIRMWARE_PATH}
    install -m 0644 ${S}/firmware_bin/WCNSS_qcom_cfg.ini ${FIRMWARE_PATH}/
    install -m 0644 ${S}/firmware_bin/WCNSS_cfg.dat ${FIRMWARE_PATH}/
    touch ${FIRMWARE_PATH}/wlan_mac.bin
    install -m 0644 CORE/SVC/external/wlan_nlink_common.h -D ${D}${includedir}/qcacld/wlan_nlink_common.h

    #in-tree modules are being installed here, workaround for now.
    rm -rf ${D}${base_libdir}/modules
}
