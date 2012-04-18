DESCRIPTION = "HAL libraries for camera"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://MODULE_LICENSE_BSD;md5=d41d8cd98f00b204e9800998ecf8427e"
PV = "1.0.0"
PR = "r2"

SRC_URI = "file://${WORKSPACE}/camera-hal"

S = "${WORKDIR}/camera-hal"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install_append() {
   mkdir -p ${STAGING_INCDIR}
   mkdir -p ${STAGING_INCDIR}/libcamera2
   cp -a ${S}/*.h ${STAGING_INCDIR}/libcamera2/
}

