require recipes-graphics/xorg-driver/xorg-driver-common.inc

inherit localgit

DESCRIPTION = "X.Org X server -- MSM display driver"
LIC_FILES_CHKSUM = "file://src/msm-driver.c;startline=3;endline=27;md5=a846f1e47dac625c7d4d059663c97627"
PV = "git-${GITSHA}"
PR = "r2"

SRC_DIR = "${WORKSPACE}/xf86-video-msm"

S = "${WORKDIR}/xf86-video-msm"

EXTRA_OECONF_append += "--with-c2d=${STAGING_DIR_TARGET}/usr"

RDEPENDS += "xserver-xorg"
RDEPENDS += "adreno-subdriver-x11"

DEPENDS = "${RDEPENDS} \
           fontsproto \
           randrproto \
           videoproto \
           renderproto \
           dri2proto \
           xf86driproto \
           glproto \
           xproto"

DEPENDS += "adreno200"
DEPENDS += "virtual/kernel"

#TODO: remove this once adreno200 symlinks are fixed
INSANE_SKIP_${PN} = "dev-deps"

PACKAGE_ARCH = "${MACHINE_ARCH}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += " -I${STAGING_INCDIR}/xorg -I${STAGING_KERNEL_DIR}/include"
CFLAGS += " -Wno-error "
