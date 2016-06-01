inherit autotools-brokensep pkgconfig

DESCRIPTION = "Build Android liblog"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

FILESPATH =+ "${WORKSPACE}/system/core/:"
SRC_URI   = "file://liblog"
SRC_URI  += "file://50-log.rules"

S = "${WORKDIR}/liblog"

EXTRA_OECONF = " --with-core-includes=${WORKSPACE}/system/core/include"

do_install_append() {
   install -m 0644 -D ../50-log.rules ${D}${sysconfdir}/udev/rules.d/50-log.rules
}

FILES_${PN}-dbg    = "${libdir}/.debug/liblog.* ${bindir}/.debug/logcat"
FILES_${PN}        = "${libdir}/pkgconfig/* ${libdir}/liblog.so.* ${sysconfdir}/udev/rules.d/50-log.rules"
FILES_${PN}-dev    = "${libdir}/liblog.so ${libdir}/liblog.la ${includedir}/log ${includedir}/android"
FILES_${PN}-static = "${libdir}/liblog.a"
