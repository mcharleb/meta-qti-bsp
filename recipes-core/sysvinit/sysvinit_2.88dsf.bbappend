FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "\
           file://Enable-ffbm.patch \
           file://rcS-default \
           file://init-setrlimit-to-enable-coredump.patch \
"
do_install_append() {
  install -d ${D}/firmware
}

FILES_${PN} += "/firmware"
