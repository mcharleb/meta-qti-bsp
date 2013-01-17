PRINC = "4"

FILESEXTRAPATHS := "${THISDIR}/${PN}-${PV}"

SRC_URI += "file://do-not-install-unnecessary-udev-rules.patch"
SRC_URI_append_msm8960 += " file://${MACHINE}/local.rules"
