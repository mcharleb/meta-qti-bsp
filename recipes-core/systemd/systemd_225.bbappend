FILESEXTRAPATHS_prepend := "${THISDIR}/systemd:"

SRC_URI += "file://1001-systemd-Disable-unused-mount-points.patch"
SRC_URI += "file://mountpartitions.rules"

EXTRA_OECONF += " --disable-efi"
