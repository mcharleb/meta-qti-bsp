DESCRIPTION = "HAL libraries for camera"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PV = "1.0.0"
PR = "r6"

SRC_URI = "file://${WORKSPACE}/camera-hal"

S = "${WORKDIR}/camera-hal"

inherit autotools

# Need the kernel headers
DEPENDS += "virtual/kernel"
DEPENDS += "glib-2.0"
DEPENDS += "mm-camera"
DEPENDS += "mm-still"
DEPENDS_appemd_msm8974 += "mm-video-oss"
DEPENDS_append_msm8974 += "mm-image-codec"
DEPENDS_append_msm8610 += "mm-image-codec"
DEPENDS += "dlog"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += "-I${STAGING_INCDIR}/jpeg/inc"
CFLAGS += "-I${STAGING_INCDIR}/cameracommon"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include/media"

EXTRA_OECONF_append = " --enable-debug=no --with-dlog"

EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm7627a', ' --enable-target=msm7627a', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8960', ' --enable-target=msm8960', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8974', ' --enable-target=msm8974', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8610', ' --enable-target=msm8610', '', d)}"

EXTRA_OECONF_append = " --with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"
EXTRA_OECONF_append_msm8960 = " --with-additional-include-directives="-I${WORKSPACE}/mm-video-oss/mm-core/inc/ -I${WORKSPACE}/mm-still/omx/inc/""
EXTRA_OECONF_append_msm8974 = " --with-additional-include-directives="${WORKSPACE}/mm-video-oss/mm-core/inc/ ""
EXTRA_OECONF_append_msm8610 = " --with-additional-include-directives="${WORKSPACE}/mm-video-oss/mm-core/inc/ ""

FILES_${PN}_append_msm8960 += "/usr/lib/hw/*"
FILES_${PN}_append_msm8974 += "/usr/lib/*"
FILES_${PN}_append_msm8610 += "/usr/lib/*"

# The camera-hal package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"

do_install_append_msm8960() {
   mkdir -p ${D}/usr/lib/hw

   # Move and rename libcamera.so files to hw/machine-specific names.
   cp ${D}/usr/lib/libcamera.so.0.0.0 ${D}/usr/lib/hw/libcamera.so

   pushd ${D}/usr/lib/hw
   ln -s libcamera.so ./camera.msm8960.so
   popd
}
