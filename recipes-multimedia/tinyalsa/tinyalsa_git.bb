inherit autotools

DESCRIPTION = "Tinyalsa Library"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PR = "r1"

SRCREV = "${AUTOREV}"
SRC_URI = "git://codeaurora.org/quic/le/platform/external/tinyalsa.git;protocol=git;branch=github/master \
           file://Makefile.am \
           file://configure.ac \
           file://tinyalsa.pc.in \
           file://0001-tinyalsa-Added-avail_min-member.patch \
           file://0001-tinyalsa-add-24-bit-record-support.patch"

S = "${WORKDIR}"

EXTRA_OEMAKE = "DEFAULT_INCLUDES=-I${WORKDIR}/git/include/"

DEPENDS = "libcutils"
