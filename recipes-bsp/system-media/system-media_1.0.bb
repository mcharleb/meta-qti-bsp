inherit autotools-brokensep

DESCRIPTION = "system media headers"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI   = "file://system/media/"
S = "${WORKDIR}/system/media"

PR = "r1"

do_install () {
        install -d ${D}${includedir}
        install -m 0644 ${S}/camera/include/system/camera_metadata.h -D ${D}${includedir}/system/camera_metadata.h
        install -m 0644 ${S}/camera/include/system/camera_metadata_tags.h -D ${D}${includedir}/system/camera_metadata_tags.h
        install -m 0644 ${S}/camera/include/system/camera_vendor_tags.h -D ${D}${includedir}/system/camera_vendor_tags.h
}
