PRINC = "1"

FILESEXTRAPATHS := "${THISDIR}/${PN}-${PV}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI += "file://umountfs"

do_install_append() {
	update-rc.d -f -r ${D} reboot remove

	rm -rf ${D}${sysconfdir}/init.d/reboot
}
