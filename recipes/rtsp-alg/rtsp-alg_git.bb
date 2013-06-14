inherit autotools linux-kernel-base module
DESCRIPTION = "RSTP ALG"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://nf_nat_rtsp.c;md5=afa391a5db8f879772af6fead894dac1"

PR = "r0"

KERNEL_VERSION = "${@get_kernelversion('${STAGING_KERNEL_DIR}')}"


FILES_${PN} += "\
    ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/netfilter/nf_nat_rtsp.ko \
    "
FILES_${PN} += "\
    ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/netfilter/nf_conntrack_rtsp.ko \
    "

SRC_URI = "git://github.com/maru-sama/rtsp-linux-v2.6.git;protocol=git;tag=3.3 \
		file://Makefile.patch \
"


S = "${WORKDIR}/git"
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
