DESCRIPTION = "HAL libraries for camera"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PV = "1.0.0"
PR = "r3"

SRC_URI = "file://${WORKSPACE}/camera-hal"

S = "${WORKDIR}/camera-hal"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install_append() {
   mkdir -p ${STAGING_INCDIR}
   mkdir -p ${STAGING_INCDIR}/libcamera2
   cp -a ${S}/*.h ${STAGING_INCDIR}/libcamera2/
}

