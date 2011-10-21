inherit native

PR = "r1"

MY_PN = "mkbootimg"

DESCRIPTION = "Boot image creation tool from Android"
LICENSE = "BSD"
HOMEPAGE = "http://android.git.kernel.org/?p=platform/system/core.git"

SRC_URI = "file://${WORKSPACE}/system/core/${MY_PN} \
	   file://makefile"

DEPENDS = "libmincrypt-native"

S = "${WORKDIR}"

EXTRA_OEMAKE = "INCLUDES='-Imincrypt' LIBS='${libdir}/mincrypt/libmincrypt.a'"

do_install() {
	install -d ${D}${bindir}
	install ${MY_PN}/${MY_PN} ${D}${bindir}
}

NATIVE_INSTALL_WORKS = "1"
