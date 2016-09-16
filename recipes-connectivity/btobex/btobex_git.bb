inherit autotools pkgconfig

DESCRIPTION = "Bluetooth OBEX"
LICENSE = "BSD"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

DEPENDS += "glib-2.0"

CFLAGS_append = " -DUSE_ANDROID_LOGGING "
LDFLAGS_append = " -llog "

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/obex_profiles/"

S = "${WORKDIR}/qcom-opensource/bt/obex_profiles/"

EXTRA_OECONF = "--with-glib"
