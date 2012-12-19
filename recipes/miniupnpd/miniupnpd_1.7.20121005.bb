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
PR = "r0"

S = "${WORKDIR}/miniupnpd-${PV}"

SRC_URI = "\
    http://miniupnp.free.fr/files/download.php?file=miniupnpd-${PV}.tar.gz \
    file://0001-qcmap-enabled.patch \
"

SRC_URI[md5sum] = "d676ef9516ca3fbdd811aee62f2ca724"
SRC_URI[sha256sum] = "5b8b8163fc578f6acb923b62002b38f071ff68fc178922dc0ff2410490ec6a0e"

do_compile () {
    cd ${S} && make -f Makefile.linux LIBDIR=${STAGING_LIBDIR}
}

do_install () {
    make -f Makefile.linux PREFIX=${D} LIBDIR=${STAGING_LIBDIR} install
}
