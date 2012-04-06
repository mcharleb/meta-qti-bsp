DESCRIPTION = "HAL libraries for camera"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://MODULE_LICENSE_BSD;md5=d41d8cd98f00b204e9800998ecf8427e"
PV = "1.0.0"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/camera-hal"

S = "${WORKDIR}/camera-hal"

inherit autotools

# Need the kernel headers
DEPENDS += "virtual/kernel"
DEPENDS += "glib-2.0"
DEPENDS += "mm-camera"
DEPENDS += "mm-still"

PACKAGE_ARCH = "${MACHINE_ARCH}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += "-I${STAGING_INCDIR}/jpeg/inc"
CFLAGS += "-I${STAGING_INCDIR}/cameracommon"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include/media"

EXTRA_OECONF_append = " --enable-debug=no"

EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm7627a', ' --enable-target=msm7627a', '', d)}"

FILES_${PN} += "/usr/lib/hw/*"

# The camera-hal package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"

do_install_append() {
   mkdir -p ${D}/usr/lib/hw

   # Move and rename libcamera.so files to hw/machine-specific names.
   mv ${D}/usr/lib/libcamera.so.0.0.0 ${D}/usr/lib/hw/libcamera.so
   rm ${D}/usr/lib/libcamera.so.0
   rm ${D}/usr/lib/libcamera.so

   pushd ${D}/usr/lib/hw
   ln -s libcamera.so ./camera.msm7630.so
   ln -s libcamera.so ./camera.msm7627A.so
   popd
}

