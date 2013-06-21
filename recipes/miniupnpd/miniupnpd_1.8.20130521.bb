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
PR = "r2"

SRC_URI = "\
    http://miniupnp.free.fr/files/download.php?file=${PN}-${PV}.tar.gz \
    file://0001-certification-fixes.patch \
    file://0001-upstream-fixes.patch \
    file://0001-enable-backhaul.patch \
    file://0001-enable-http-date.patch \
    file://0001-presentation-page.patch \
"

SRC_URI[md5sum] = "2b913c53fbeb562731e84b66b3ce704f"
SRC_URI[sha256sum] = "2fc003b0d53a9209389843d32d574f7f0b2886c9609840640ca5e2aa1fd73d4e"

do_compile () {
    cd ${S} && make -f Makefile.linux LIBDIR=${STAGING_LIBDIR} INCDIR=${STAGING_INCDIR}
}

do_install () {
    make -f Makefile.linux DESTDIR=${D} LIBDIR=${STAGING_LIBDIR} install
}
