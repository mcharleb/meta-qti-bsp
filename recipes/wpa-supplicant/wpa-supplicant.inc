inherit autotools linux-kernel-base

DESCRIPTION = "Wi-Fi Protected Access(WPA) Supplicant"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://../COPYING;md5=ab87f20cd7e8c0d0a6539b34d3791d0e"

FILES_${PN} += "\
        /usr/sbin \
        "
INC_PR = "r1"

DEPENDS = "openssl libnl"

SRC_URI = "file://${WORKSPACE}/external/hostap"

S = "${WORKDIR}/hostap/wpa_supplicant"

do_install() {
    make install DESTDIR=${D}
}
