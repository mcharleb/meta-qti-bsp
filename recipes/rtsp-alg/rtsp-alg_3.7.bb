inherit autotools linux-kernel-base module
DESCRIPTION = "RSTP ALG"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://nf_nat_rtsp.c;beginline=2;endline=30;md5=1d494e4d4253d1fcdcbfe334f8a4cd0a"

PR = "r3"


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

KERNEL_VERSION = "${@get_kernelversion('${STAGING_KERNEL_DIR}')}"
EXTRA_OEMAKE = "KSOURCE=${STAGING_KERNEL_DIR}"

do_configure_prepend() {
    sleep 120
}

do_compile_prepend() {
    sleep 120
}

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
        install -m 0644 ${S}/nf_nat_rtsp.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
        install -m 0644 ${S}/nf_conntrack_rtsp.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}

# Remove dependency for wrong kernel version
python split_kernel_module_packages_append() {
    if modules:
        metapkg = d.getVar('KERNEL_MODULES_META_PACKAGE', True)
        d.delVar('RDEPENDS_' + metapkg)
}
