DESCRIPTION = "OpenMAX video for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
SRC_URI = "file://${WORKSPACE}/mm-video-oss"

PR = "r8"

DEPENDS = "virtual/kernel mm-core-oss"
DEPENDS += "glib-2.0"
RDEPENDS = "mm-core-oss mm-video"

# Need the kernel headers
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/mm-video-oss"

LV = "1.0.0"

inherit autotools

EXTRA_OECONF_append = "--with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"
EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm8655', ' --enable-target-msm7630=yes', '', d)}"

CPPFLAGS += "-I${STAGING_INCDIR}/glib-2.0"
CPPFLAGS += "-I${STAGING_LIBDIR}/glib-2.0/include"

LDFLAGS += "-lglib-2.0"

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/share/*"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

do_install() {
	oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
