PRINC = "6"

FILESEXTRAPATHS := "${THISDIR}/${PN}-${PV}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI += "file://do-not-install-unnecessary-udev-rules.patch"
SRC_URI_append_msm8960 += " file://${BASEMACHINE}/local.rules"
