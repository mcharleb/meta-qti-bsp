FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "\
           file://Enable-ffbm.patch \
           file://rcS-default \
"
do_install_append() {
  install -d ${D}/firmware
}

FILES_${PN} += "/firmware"
