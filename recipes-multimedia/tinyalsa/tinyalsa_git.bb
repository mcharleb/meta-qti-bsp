inherit autotools

DESCRIPTION = "Tinyalsa Library"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://git/pcm.c;md5=ca62982637d9006ada72e6d35f17be19"
PR = "r0"

SRCREV = "${AUTOREV}"
SRC_URI = "git://codeaurora.org/quic/le/platform/external/tinyalsa.git;protocol=git;branch=github/master \
           file://Makefile.am \
           file://configure.ac \
           file://tinyalsa.pc.in \
           file://0001-tinyalsa-Added-avail_min-member.patch"

S = "${WORKDIR}"

EXTRA_OEMAKE = "DEFAULT_INCLUDES=-I${WORKDIR}/git/include/"

DEPENDS = "libcutils"
