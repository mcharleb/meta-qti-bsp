DESCRIPTION = "OpenMAX video for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://vidc/vdec/src/omx_vdec.cpp;startline=2;endline=26;md5=142d82ce5e85d25b9a0f1d72cbd6e41d"

SRC_URI = "file://${WORKSPACE}/mm-video-oss"

SRC_URI += "file://Modify-makefile-for-8x55.patch"

PR = "r6"

DEPENDS = "glesproto virtual/kernel mm-core-oss"
RDEPENDS = "mm-core-oss mm-video"

# Need the kernel headers
PACKAGE_ARCH = "${MACHINE_ARCH}"
CFLAGS += " -I${STAGING_KERNEL_DIR}/include"
LDFLAGS += "-lstdc++"

S = "${WORKDIR}/mm-video-oss"

LV = "1.0.0"

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/share/*"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

do_compile() {
    oe_runmake LIBVER="${LV}" SYSROOT="${STAGING_DIR}/${HOST_SYS}"
}

do_install() {
    oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
