inherit autotools linux-kernel-base

DESCRIPTION = "Wi-Fi Protected Access(WPA) Supplicant"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

FILES_${PN} += "\
        /usr/sbin \
        "
INC_PR = "r2"

DEPENDS = "openssl libnl"

SRC_URI = "file://${WORKSPACE}/external/hostap"

S = "${WORKDIR}/hostap/wpa_supplicant"

do_install() {
    make install DESTDIR=${D}
}
