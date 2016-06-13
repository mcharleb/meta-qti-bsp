inherit autotools pkgconfig

DESCRIPTION = "system media headers"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "system-core"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI   = "file://system/media/"
S = "${WORKDIR}/system/media"

PR = "r1"

FILES_${PN}-dbg    = "${libdir}/.debug/libcamera_metadata.*"
FILES_${PN}        = "${libdir}/libcamera_metadata.so.* ${libdir}/pkgconfig/*"
FILES_${PN}-dev    = "${libdir}/libcamera_metadata.so ${libdir}/libcamera_metadata.la ${includedir}"
