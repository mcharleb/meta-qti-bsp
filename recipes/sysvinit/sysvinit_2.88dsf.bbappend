FILESEXTRAPATHS = "${THISDIR}/${PN}-${PV}"

SRC_URI += "\
           file://rcS-default \
"
do_install_append() {
  install -d ${D}/firmware
}

FILES_${PN} += "/firmware"
