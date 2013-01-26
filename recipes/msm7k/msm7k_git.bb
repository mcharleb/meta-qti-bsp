inherit autotools

DESCRIPTION = "MSM7K"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/hardware/msm7k"

DEPENDS = "glib-2.0"

EXTRA_OECONF = "--with-glib"

S = "${WORKDIR}/msm7k"
