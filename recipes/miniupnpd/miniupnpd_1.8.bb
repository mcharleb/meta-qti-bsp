inherit autotools
SUMMARY = "Lightweight implementation of a UPnP IGD daemon."
DESCRIPTION = "MiniUPnPd is a low memory daemon which acts as a\
UPnP device, enabling seamless detection of other UPnP devices/control points."
HOMEPAGE = "http://miniupnp.free.fr/"
BUGTRACKER = "http://miniupnp.tuxfamily.org/forum/viewforum.php?f=2"
LICENSE = "BSD"
PRIORITY = "optional"
DEPENDS = "data"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"

# Package Revision (update whenever recipe is changed)
PR = "r1"

SRC_URI = "\
    https://www.codeaurora.org/mirrored_source/quic/le/miniupnpd-1.8.tar.gz \
    file://0001-qcmap-enabled.patch \
"

SRC_URI[md5sum] = "0d8a8e936d5a0012cb260a3b972acbf3"
SRC_URI[sha256sum] = "e453a9225a2883e759d09e15c51bb0265b8a019d1132434131e6929fef0076bb"

do_compile () {
    cd ${S} && make -f Makefile.linux LIBDIR=${STAGING_LIBDIR}
}

do_install () {
    make -f Makefile.linux PREFIX=${D} LIBDIR=${STAGING_LIBDIR} install
}
