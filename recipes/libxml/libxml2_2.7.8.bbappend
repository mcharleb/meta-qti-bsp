FILESEXTRAPATHS := "${THISDIR}/${PN}-${PV}"

SRC_URI += "file://fix-compilation-bug.patch"
EXTRA_OECONF_mdm9625 += "--with-minimum"
