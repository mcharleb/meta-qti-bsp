inherit autotools update-rc.d

DESCRIPTION = "Device specific config"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"
PR = "r3"

FILESPATH =+ "${WORKSPACE}:"
# Provide a baseline
SRC_URI = "file://mdm-init/"

# Update for each machine
S = "${WORKDIR}/mdm-init/"

FILES_${PN} += "${base_libdir}/firmware/wlan/qca_cld/*"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'mdm9607', '--enable-target-mdm9607=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'mdmcalifornium', '--enable-target-mdmcalifornium=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'apq8009', '--enable-pronto-wlan=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'apq8053', '--enable-pronto-wlan=yes', '', d)}"

INITSCRIPT_NAME   = "wlan"
INITSCRIPT_PARAMS = "remove"
INITSCRIPT_PARAMS_apq8009 = "start 98 5 . stop 2 0 1 6 ."
INITSCRIPT_PARAMS_apq8053 = "start 98 5 . stop 2 0 1 6 ."

