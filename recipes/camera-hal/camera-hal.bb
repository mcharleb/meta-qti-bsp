DESCRIPTION = "HAL libraries for camera"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PV = "1.0.0"
PR = "r5"

SRC_URI = "file://${WORKSPACE}/camera-hal"

S = "${WORKDIR}/camera-hal"

inherit autotools

# Need the kernel headers
DEPENDS += "virtual/kernel"
DEPENDS += "glib-2.0"
DEPENDS += "mm-camera"
DEPENDS += "mm-still"
DEPENDS += "mm-image-codec"
#DEPENDS += "mm-video-oss"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += "-I${STAGING_INCDIR}/jpeg/inc"
CFLAGS += "-I${STAGING_INCDIR}/cameracommon"

EXTRA_OECONF_append = " --enable-debug=no"

EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm7627a', ' --enable-target=msm7627a', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8960', ' --enable-target=msm8960', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8974', ' --enable-target=msm8974', '', d)}"

EXTRA_OECONF_append = " --with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"
EXTRA_OECONF_append = " --with-additional-include-directives="${WORKSPACE}/mm-video-oss/mm-core/inc/ ""

FILES_${PN} += "/usr/lib/*"

# The camera-hal package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"
