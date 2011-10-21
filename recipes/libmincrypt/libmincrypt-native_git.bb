inherit native

PR = "r0"

MY_PN = "mincrypt"
MY_LPN = "libmincrypt"

DESCRIPTION = "Minimalistic encryption library from Android"
LICENSE = "BSD"
HOMEPAGE = "http://android.git.kernel.org/?p=platform/system/core.git"

SRC_URI = "file://${WORKSPACE}/system/core/${MY_LPN} \
	   file://${WORKSPACE}/system/core/include/${MY_PN} \
	   file://makefile"

S = "${WORKDIR}"

EXTRA_OEMAKE = "INCLUDES='-I.'"

do_install() {
	install -d ${D}${includedir}/${MY_PN} ${D}${libdir}/${MY_PN}
	install ${MY_PN}/*.h ${D}${includedir}/${MY_PN}
	install ${MY_LPN}.a ${D}${libdir}/${MY_PN}
}

NATIVE_INSTALL_WORKS = "1"
