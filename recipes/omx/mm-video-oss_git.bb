DESCRIPTION = "OpenMAX video for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
SRC_URI = "file://${WORKSPACE}/mm-video-oss"

PR = "r17"

DEPENDS = "virtual/kernel"
DEPENDS += "glib-2.0"
DEPENDS += "mm-core-oss"
DEPENDS += "adreno200"
RDEPENDS = "mm-video-prop"
INSANE_SKIP = 1

# Need the kernel headers
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/mm-video-oss"

LV = "1.0.0"

inherit autotools

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

EXTRA_OECONF_append = "--with-libhardware-headers=${WORKSPACE}/hardware/libhardware "
EXTRA_OECONF_append = "--with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include "
EXTRA_OECONF_append = " --with-common-includes=${STAGING_INCDIR}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8655', ' --enable-target-msm7630=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8960', ' --enable-target-msm8960=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8974', ' --enable-target-msm8974=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8610', ' --enable-target-msm8610=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8226', ' --enable-target-msm8226=yes', '', d)}"

CPPFLAGS += "-I${STAGING_INCDIR}/glib-2.0"
CPPFLAGS += "-I${STAGING_LIBDIR}/glib-2.0/include"

CPPFLAGS += "-I${STAGING_INCDIR}/c++"
CPPFLAGS += "-I${STAGING_INCDIR}/c++/${TARGET_SYS}"

LDFLAGS += "-lglib-2.0"

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/include/* \
    /usr/share/*"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

do_install() {
	oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
	mkdir -p ${STAGING_INCDIR}/mm-core
	install -m 0644 ${S}/mm-core/inc/*.h ${STAGING_INCDIR}/mm-core
}
