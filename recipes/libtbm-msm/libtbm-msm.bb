inherit autotools

DESCRIPTION = "MSM TBM driver"
HOMEPAGE = "https://www.codeaurora.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

PR = "r1"

SRC_URI = "file://${WORKSPACE}/libtbm-msm"

S = "${WORKDIR}/libtbm-msm"

DEPENDS = "libtbm"
DEPENDS += "virtual/kernel"

EXTRA_OECONF_append += " --with-kernel-headers=${STAGING_KERNEL_DIR}/usr"

LEAD_SONAME="libtbm_msm.so"
FILES_${PN} += "/usr/lib/bufmgr/libtbm_msm.so*"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INSANE_SKIP_${PN} = "dev-so"

CFLAGS += " -Wno-error "
