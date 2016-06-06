inherit autotools-brokensep

DESCRIPTION = "frameworks av headers"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI   = "file://frameworks/av/"
S = "${WORKDIR}/frameworks/av"

PR = "r1"

do_install () {
        install -d ${D}${includedir}
        install -m 0644 ${S}/include/camera/CameraMetadata.h -D ${D}${includedir}/camera/CameraMetadata.h
        install -m 0644 ${S}/include/camera/CameraParameters.h -D ${D}${includedir}/camera/CameraParameters.h
}
