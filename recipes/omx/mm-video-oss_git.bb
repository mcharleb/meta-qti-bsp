DESCRIPTION = "OpenMAX video for MSM chipsets"
LICENSE = "BSD"

SRC_URI = "file://${WORKSPACE}/mm-video-oss"

SRC_URI += "file://Modify-makefile-for-8x55.patch"

PR = "r5"

DEPENDS = "glesproto virtual/kernel mm-core-oss"
RDEPENDS = "mm-core-oss mm-video"

# Need the kernel headers
PACKAGE_ARCH = "${MACHINE_ARCH}"
CFLAGS += " -I${STAGING_KERNEL_DIR}/include"

S = "${WORKDIR}/mm-video-oss"

LV = "1.0.0"

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/share/*"


do_compile() {
    oe_runmake LIBVER="${LV}" SYSROOT="${STAGING_DIR}/${HOST_SYS}"
}

do_install() {
    oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
