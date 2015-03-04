inherit native autotools

DESCRIPTION = "Libselinux"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://NOTICE;md5=84b4d2c6ef954a2d4081e775a270d0d0"

PR = "r1"

DEPENDS = "libpcre-native"

EXTRA_OECONF = " \
		--with-pcre \
		--with-core-includes=${WORKSPACE}/system/core/include \
"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://external/libselinux/"
S = "${WORKDIR}/external/libselinux"
