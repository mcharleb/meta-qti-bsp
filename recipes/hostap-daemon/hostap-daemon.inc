inherit autotools linux-kernel-base

DESCRIPTION = "Hostap Daemon"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://../COPYING;md5=ab87f20cd7e8c0d0a6539b34d3791d0e"

FILES_${PN} += "\
        /usr/bin \
        "
FILES_${PN}-dbg += "\
        /usr/bin/.debug \
        "

INC_PR = "r0"

DEPENDS = "openssl libnl"

SRC_URI = "file://${WORKSPACE}/external/hostap"

S = "${WORKDIR}/hostap/hostapd"

do_configure_append() {
    echo "CFLAGS += -I${STAGING_INCDIR}" >> .config
    echo "LDFLAGS += -L${STAGING_LIBDIR}" >> .config
}

do_install() {
    make install DESTDIR=${D}
}
