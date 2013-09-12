require recipes-graphics/xorg-driver/xorg-driver-common.inc

DESCRIPTION = "X.Org X server -- MSM display driver"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=3775480a712fc46a69647678acb234cb"

PR = "r6"

SRC_URI = "file://${WORKSPACE}/xf86-video-msm"
S = "${WORKDIR}/xf86-video-msm"

EXTRA_OECONF_append += "--with-kernel-headers=${STAGING_KERNEL_DIR}/usr/include"

RDEPENDS += "xserver-xorg"

DEPENDS = "${RDEPENDS} \
           fontsproto \
           randrproto \
           videoproto \
           renderproto \
           dri2proto \
           xf86driproto \
           glproto \
           xproto \
           libtbm"

DEPENDS += "virtual/kernel xdbg"

#TODO: remove this once adreno200 symlinks are fixed
INSANE_SKIP_${PN} = "dev-deps"

PACKAGE_ARCH = "${MACHINE_ARCH}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += " -Wno-error "
