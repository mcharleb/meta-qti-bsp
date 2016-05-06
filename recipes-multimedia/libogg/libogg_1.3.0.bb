inherit autotools
SUMMARY = "Multimedia container format."
HOMEPAGE = "http://www.xiph.org/ogg/"
BUGTRACKER = "https://trac.xiph.org/"
LICENSE = "BSD"
PRIORITY = "optional"
LIC_FILES_CHKSUM = "file://COPYING;md5=db1b7a668b2a6f47b2af88fb008ad555"

# Package Revision (update whenever recipe is changed)
PR = "r2"

SRC_URI = "\
    http://downloads.xiph.org/releases/ogg/${PN}-${PV}.tar.gz \
"

SRC_URI[md5sum] = "0a7eb40b86ac050db3a789ab65fe21c2"
SRC_URI[sha256sum] = "a8de807631014615549d2356fd36641833b8288221cea214f8a72750efe93780"

EXTRA_OECONF = "--libdir=${base_libdir}"



PACKAGE_SNAP_LIB_SYMLINKS = "0"

INSANE_SKIP_${PN} += "dev-so"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
FILES_${PN} += "${base_libdir}/*"
FILES_${PN}-dev = ""
