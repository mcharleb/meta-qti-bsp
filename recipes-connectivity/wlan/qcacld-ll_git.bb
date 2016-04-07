inherit autotools-brokensep module

DESCRIPTION = "Qualcomm Atheros WLAN CLD low latency driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

FILES_${PN}     += "${base_libdir}/firmware/wlan/*"
FILES_${PN}     += "${base_libdir}/modules/${KERNEL_VERSION}/extra/wlan.ko"
RPROVIDES_${PN} += "kernel-module-wlan"

do_unpack[deptask] = "do_populate_sysroot"
PR = "r8-${KERNEL_VERSION}"

# This DEPENDS is to serialize kernel module builds
DEPENDS = "rtsp-alg"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://wlan/qcacld-2.0/"

S = "${WORKDIR}/wlan/qcacld-2.0/"

FIRMWARE_PATH = "${D}${base_libdir}/firmware/wlan/qca_cld"

# Explicitly disable HL to enable LL as current WLAN driver is not having
# simultaneous support of HL and LL.
EXTRA_OEMAKE += "CONFIG_CLD_HL_SDIO_CORE=n CONFIG_CNSS_SDIO=n"

# The common header file, 'wlan_nlink_common.h' can be installed from other
# qcacld recipes too. To suppress the duplicate detection error, add it to
# SSTATE_DUPWHITELIST.
SSTATE_DUPWHITELIST += "${STAGING_DIR}/${MACHINE}${includedir}/qcacld/wlan_nlink_common.h"

do_install () {
    module_do_install

    install -d ${FIRMWARE_PATH}
    install -m 0644 ${S}/firmware_bin/WCNSS_cfg.dat ${FIRMWARE_PATH}/

    install -d ${D}${includedir}/qcacld/
    install -m 0644 ${S}/CORE/SVC/external/wlan_nlink_common.h ${D}${includedir}/qcacld/
}
