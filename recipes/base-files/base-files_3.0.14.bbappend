FILESEXTRAPATHS := "${THISDIR}/${PN}-${PV}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI += "file://fstab"

do_install_append(){
    ln -s /media/card ${D}/sdcard
}
