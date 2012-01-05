require recipes/xorg-driver/xorg-driver-video.inc

DESCRIPTION = "X.Org X server -- MSM display driver"
PE = "1"
PV = "1.1.0+${PR}+gitr${SRCREV}"
PR = "${INC_PR}.2"


SRC_URI = "file://${WORKSPACE}/xf86-video-msm"

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

PACKAGE_ARCH = "${MACHINE_ARCH}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += " -I${STAGING_INCDIR}/xorg -I${STAGING_KERNEL_DIR}/include"
CFLAGS += " -Wno-error "
