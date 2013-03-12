inherit autotools
SUMMARY = "Library for interacting with ID3 tags"
HOMEPAGE = "http://www.underbit.com/products/mad/"
BUGTRACKER = "http://sourceforge.net/tracker/?atid=112349&group_id=12349&func=browse"
LICENSE = "GPLv2"
PRIORITY = "optional"
DEPENDS = "zlib"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

# Package Revision (update whenever recipe is changed)
PR = "r0"

SRC_URI = "\
    http://downloads.sourceforge.net/project/mad/${PN}/0.15.1b/${PN}-0.15.1b.tar.gz \
"

SRC_URI[md5sum] = "e5808ad997ba32c498803822078748c3"
SRC_URI[sha256sum] = "63da4f6e7997278f8a3fef4c6a372d342f705051d1eeb6a46a86b03610e26151"