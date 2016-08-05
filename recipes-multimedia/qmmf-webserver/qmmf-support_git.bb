inherit autotools pkgconfig

DESCRIPTION = "QMMF Webserver support libraries"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

DEPENDS = "liblog"
DEPENDS += "libcutils"
DEPENDS += "native-frameworks"
DEPENDS += "system-core"
DEPENDS += "glib-2.0"
DEPENDS += "av-frameworks"
DEPENDS += "live555-qti"
DEPENDS += "qmmf-sdk"

TARGET_CFLAGS += "-I${STAGING_INCDIR} -I${STAGING_INCDIR}/recorder/"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-webserver"

S = "${WORKDIR}/qmmf-webserver"

do_package_qa () {
}

FILES_${PN} = "${libdir}/lib*.so.* ${libdir}/pkgconfig/*"
FILES_${PN}-dev = "${libdir}/lib*.so* ${includedir} ${libdir}/*.la ${libdir}/*.a"
FILES_${PN}-dbg = "${libdir}/.debug"
PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"
