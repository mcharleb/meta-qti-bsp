require recipes-graphics/xorg-driver/xorg-driver-common.inc

inherit localgit

DESCRIPTION = "X.Org X server -- MSM display driver"
LIC_FILES_CHKSUM = "file://src/msm-driver.c;startline=3;endline=27;md5=aba647c8d2fb6ef3d954853ee60b9f2b"
PV = "git-${GITSHA}"
PR = "r3"

SRC_DIR = "${WORKSPACE}/xf86-video-msm"

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
           xproto"

DEPENDS += "virtual/kernel"

#TODO: remove this once adreno200 symlinks are fixed
INSANE_SKIP_${PN} = "dev-deps"

PACKAGE_ARCH = "${MACHINE_ARCH}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += " -Wno-error "
