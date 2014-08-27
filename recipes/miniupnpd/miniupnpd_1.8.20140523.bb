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
PR = "r5"

SRC_URI = "\
    https://www.codeaurora.org/mirrored_source/quic/le/${PN}-${PV}.tar.gz \
    file://0001-certification-fixes.patch \
    file://0001-presentation-page.patch \
    file://0001-port-desc.patch \
"

SRC_URI[md5sum] = "fcac89e11da091b1cc61f43d83c9e4f5"
SRC_URI[sha256sum] = "e72cd1e515aa9e6281e519e1814fe74e7689f5da71ce463a996a17e787ffe490"

do_compile () {
    cd ${S} && make -f Makefile.linux LIBDIR=${STAGING_LIBDIR} INCDIR=${STAGING_INCDIR}
}

do_install () {
    make -f Makefile.linux DESTDIR=${D} LIBDIR=${STAGING_LIBDIR} install
}
