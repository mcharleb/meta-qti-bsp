PR .= ".micro1"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}-${PV}", "${THISDIR}/files"], d)}:"

SRC_URI += "file://ldso-no-hwcaps.patch"
