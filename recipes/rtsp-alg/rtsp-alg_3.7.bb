inherit autotools linux-kernel-base module
DESCRIPTION = "RSTP ALG"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://nf_nat_rtsp.c;md5=36ca2796518e389a90260ef560cdab1f"

PR = "r1"

KERNEL_VERSION = "${@get_kernelversion('${STAGING_KERNEL_DIR}')}"


FILES_${PN} += "\
    ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/netfilter/nf_nat_rtsp.ko \
    "
FILES_${PN} += "\
    ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/netfilter/nf_conntrack_rtsp.ko \
    "

SRC_URI = "http://github.com/maru-sama/rtsp-linux/archive/${PV}.tar.gz \
		file://Makefile.patch \
"
SRC_URI[md5sum]    = "5cc2be642a0d6ff8817d72d459e76606"
SRC_URI[sha256sum] = "bd14b5f8f0bc8db3db93735b2a7eca2790454c2dc200d95becd283b043b8b94d"


S = "${WORKDIR}/rtsp-linux-${PV}"
do_configure() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	oe_runmake 'MODPATH="${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net"' \
		'KSOURCE="${STAGING_KERNEL_DIR}"' \
		'KDIR="${STAGING_KERNEL_DIR}"' \
		'KERNEL_VERSION="${KERNEL_VERSION}"' \
		'ARCH=${TARGET_ARCH}'
}

do_compile() {
        unset LDFLAGS
        oe_runmake 'KSOURCE="${STAGING_KERNEL_DIR}"'
}

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
        install -m 0644 ${S}/nf_nat_rtsp.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
        install -m 0644 ${S}/nf_conntrack_rtsp.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}
