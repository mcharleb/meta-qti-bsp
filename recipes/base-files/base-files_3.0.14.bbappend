FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI += "file://fstab"

dirs755 += "/media /media/card /media/cf /media/net /media/ram \
            /media/union /media/realroot /media/hdd \
            /media/mmc1"

do_install_append(){
    ln -s /media/card ${D}/sdcard
}
