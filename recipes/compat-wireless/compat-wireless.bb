inherit autotools linux-kernel-base module

DESCRIPTION = "Linux Wireless compatibility package"
HOMEPAGE = "http://wireless.kernel.org/en/users/Download";
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEPENDS = "virtual/kernel"
RDEPENDS = "wireless-tools"

PR = "r0"

COMPAT_WIRELESS_VERSION = "3.0"

SRC_URI = "file://${WORKSPACE}/external/compat-wireless"

PV = "${COMPAT_WIRELESS_VERSION}"

S = "${WORKDIR}/compat-wireless"

EXTRA_OEMAKE = "KLIB_BUILD=${STAGING_KERNEL_DIR} KLIB=${D}"

do_configure() {
    cd ${S}
    ./scripts/driver-select ath6kl
}

do_install() {
    oe_runmake DEPMOD=echo DESTDIR="${D}" INSTALL_MOD_PATH="${D}" \
                      LDFLAGS="" install-modules
}
