FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "\
           file://Enable-ffbm.patch \
           file://rcS-default \
           file://init-setrlimit-to-enable-coredump.patch \
           file://call_restorecon_from_init.patch \
"
SRC_URI_append_mdm9607 +=" file://${BASEMACHINE}/rcS-default"
do_install_append() {
  install -d ${D}/firmware
}

FILES_${PN} += "/firmware"
