DESCRIPTION = "OpenMAX core for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

SRC_URI = "file://${WORKSPACE}/mm-core-oss"

inherit autotools

PR = "r7"

S = "${WORKDIR}/mm-core-oss"

LV = "1.0.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm8655', ' --enable-target=msm7630', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm7627a', ' --enable-target=msm7627A', '', d)}"

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/*"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

do_install() {
	oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
