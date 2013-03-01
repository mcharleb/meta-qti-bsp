DESCRIPTION = "HAL libraries for camera"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PV = "1.0.0"
PR = "r3"

SRC_URI = "file://${WORKSPACE}/camera-hal"

S = "${WORKDIR}/camera-hal"

inherit autotools

# Need the kernel headers
DEPENDS += "virtual/kernel"
DEPENDS += "glib-2.0"
DEPENDS += "mm-camera"
DEPENDS += "mm-still"
DEPENDS += "mm-video-oss"

PACKAGE_ARCH = "${MACHINE_ARCH}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += "-I${STAGING_INCDIR}/jpeg/inc"
CFLAGS += "-I${STAGING_INCDIR}/cameracommon"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include/media"

EXTRA_OECONF_append = " --enable-debug=no"

EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm7627a', ' --enable-target=msm7627a', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm8960', ' --enable-target=msm8960', '', d)}"

EXTRA_OECONF_append = " --with-additional-include-directives="-I${WORKSPACE}/mm-video-oss/mm-core/inc/ -I${WORKSPACE}/mm-still/omx/inc/""

FILES_${PN} += "/usr/lib/hw/*"

# The camera-hal package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"

do_install_append() {
   mkdir -p ${D}/usr/lib/hw

   # Move and rename libcamera.so files to hw/machine-specific names.
   cp ${D}/usr/lib/libcamera.so.0.0.0 ${D}/usr/lib/hw/libcamera.so

   pushd ${D}/usr/lib/hw
   ln -s libcamera.so ./camera.msm8960.so
   popd
}

