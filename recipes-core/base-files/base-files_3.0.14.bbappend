FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
DEPENDS = "base-passwd"
#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI += "file://fstab"

dirs755 += "/media/cf /media/net /media/ram \
            /media/union /media/realroot /media/hdd \
            /media/mmc1"
dirs755_append_apq8009 +="/persist"

do_install_append(){
    install -m 755 -o diag -g diag -d ${D}/media
    install -m 755 -o diag -g diag -d ${D}/mnt/sdcard
    ln -s /mnt/sdcard ${D}/sdcard
    rmdir ${D}/tmp
    ln -s /var/tmp ${D}/tmp
    ln -s /var/run/resolv.conf ${D}/etc/resolv.conf
}
