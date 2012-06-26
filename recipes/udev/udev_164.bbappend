PRINC = "3"

FILESEXTRAPATHS := "${THISDIR}/${PN}-${PV}"

SRC_URI += "file://do-not-install-unnecessary-udev-rules.patch"
