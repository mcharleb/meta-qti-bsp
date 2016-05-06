inherit native autotools

PR = "r4"

DESCRIPTION = "fs_config tool from Android"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"
HOMEPAGE = "http://android.git.kernel.org/?p=platform/system/core.git"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bootable/scripts/tools/fs_config/"

S = "${WORKDIR}/bootable/scripts/tools/fs_config/"

BBCLASSEXTEND = "native"
