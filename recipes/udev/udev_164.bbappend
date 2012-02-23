PRINC = "1"

FILESEXTRAPATHS := "${THISDIR}/${PN}-${PV}"

SRC_URI += "file://remove-60-persistent-storage-rules.patch"
