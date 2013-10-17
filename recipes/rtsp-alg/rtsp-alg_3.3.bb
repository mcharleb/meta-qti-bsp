inherit autotools linux-kernel-base module
DESCRIPTION = "RSTP ALG"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://nf_nat_rtsp.c;md5=afa391a5db8f879772af6fead894dac1"

PR = "r1"

KERNEL_VERSION = "${@get_kernelversion('${STAGING_KERNEL_DIR}')}"


FILES_${PN} += "\
    ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/netfilter/nf_nat_rtsp.ko \
    "
FILES_${PN} += "\
    ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/netfilter/nf_conntrack_rtsp.ko \
    "

SRC_URI = "http://github.com/maru-sama/rtsp-linux-v2.6/archive/${PV}.tar.gz \
		file://Makefile.patch \
"
SRC_URI[md5sum]    = "e59284768c8dec69dbace22196e9dee8"
SRC_URI[sha256sum] = "2f5e516790bc6e5f10b020643fff2746fdfec3c503a70b64b21e5acdc49ccd42"


S = "${WORKDIR}/rtsp-linux-v2.6-${PV}"
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
