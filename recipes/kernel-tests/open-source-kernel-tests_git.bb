inherit autotools linux-kernel-base

DESCRIPTION = "Open Source kernel tests"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58"

SRC_URI = "file://${WORKSPACE}/qcom-opensource/kernel/kernel-tests"

DEPENDS = "linux-quic"

PR = "r0"

S = "${WORKDIR}/kernel-tests"
CFLAGS_pn-${PN} = ""
CPPFLAGS_pn-${PN} = ""
CXXFLAGS_pn-${PN} = ""
LDFLAGS_pn-${PN} = ""
PACKAGE_STRIP = "no"
KERNEL_VERSION = "${@get_kernelversion('${STAGING_KERNEL_DIR}')}"

EXTRA_OEMAKE += "ARCH=${TARGET_ARCH} CROSS_COMPILE=${TARGET_PREFIX}"

EXTRA_OECONF = "--prefix=/usr/kernel-tests \
                --with-kernel=${STAGING_KERNEL_DIR} \
                --disable-sps \
                --with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"

FILES_${PN}-dbg = "${prefix}/kernel-tests/*/.debug/* ${prefix}/src/debug/*"
FILES_${PN} = "${prefix}/kernel-tests/* ${prefix}/src/*"



