DESCRIPTION = "OpenMAX core for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://omxcore/src/default/qc_registry_table.c;startline=1;endline=27;md5=a1a011a0f0b9d7f1d7c95a5fdd6e7a7e"

SRC_URI = "file://${WORKSPACE}/mm-core-oss"

inherit autotools

PR = "r6"

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
