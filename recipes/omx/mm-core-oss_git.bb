DESCRIPTION = "OpenMAX core for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

SRC_URI = "file://${WORKSPACE}/mm-video-oss/mm-core"

inherit autotools

PR = "r11"

S = "${WORKDIR}/mm-core"

LV = "1.0.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

EXTRA_OECONF_append = "--with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include "
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8655', ' --enable-target-msm7630=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm7627a', ' --enable-target-msm7627a=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8960', ' --enable-target-msm8960=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8974', ' --enable-target-msm8974=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8610', ' --enable-target-msm8610=yes', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8226', ' --enable-target-msm8226=yes', '', d)}"

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/*"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

do_install() {
	oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
