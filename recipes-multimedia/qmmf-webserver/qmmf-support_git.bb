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
DEPENDS += "mm-mux"
DEPENDS += "libav"
DEPENDS += "vam-lib"
DEPENDS += "vam-test"

TARGET_CFLAGS += "-I${STAGING_INCDIR} -I${STAGING_INCDIR}/recorder/"
TARGET_CFLAGS += "-I${STAGING_INCDIR}/mm-osal/include -I${STAGING_INCDIR}/mm-mux"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-webserver"

S = "${WORKDIR}/qmmf-webserver"

do_package_qa () {
}

FILES_${PN} = "${libdir}/lib*.so.* ${bindir}/* ${libdir}/pkgconfig/*"
FILES_${PN}-dev = "${libdir}/lib*.so* ${includedir} ${libdir}/*.la ${libdir}/*.a"
FILES_${PN}-dbg = "${libdir}/.debug ${bindir}/.debug"
PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"
