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

do_install_append(){
   install -m 0755 ${S}/wlan_daemon -D ${D}${sysconfdir}/init.d/wlan_daemon
}

FILES_${PN} += "${base_libdir}/firmware/wlan/qca_cld/* ${sysconfdir}/init.d/* "
FILES_${PN} += "${userfsdatadir}/misc/wifi/*"

BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'mdm9607', '--enable-target-mdm9607=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'mdmcalifornium', '--enable-target-mdmcalifornium=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'apq8096', '--enable-target-apq8096=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'apq8009', '--enable-pronto-wlan=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'apq8053', '--enable-pronto-wlan=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEMACHINE', 'apq8017', '--enable-pronto-wlan=yes', '', d)}"

# Enable drone-wlan in place of pronto-wlan for Drones
EXTRA_OECONF_remove = "${@base_conditional('BASEPRODUCT', 'drone', '--enable-pronto-wlan=yes', '', d)}"
EXTRA_OECONF += "${@base_conditional('BASEPRODUCT', 'drone', '--enable-drone-wlan=yes', '', d)}"

INITSCRIPT_NAME   = "wlan_daemon"
INITSCRIPT_PARAMS_apq8009 = "start 98 5 . stop 2 0 1 6 ."
INITSCRIPT_PARAMS_apq8053 = "start 98 5 . stop 2 0 1 6 ."
INITSCRIPT_PARAMS_apq8017 = "start 98 5 . stop 2 0 1 6 ."
INITSCRIPT_PARAMS_apq8096 = "start 98 5 . stop 2 0 1 6 ."

pkg_postinst_${PN} () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f wlan_daemon remove
}
