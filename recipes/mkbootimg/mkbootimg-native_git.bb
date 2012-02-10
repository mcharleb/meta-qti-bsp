inherit native

PR = "r1"

MY_PN = "mkbootimg"

DESCRIPTION = "Boot image creation tool from Android"
LICENSE = "Apache-2.0"
# The license declaration's in the source for the command...
LIC_FILES_CHKSUM = "file://mkbootimg.c;md5=ba4ed0ea30eea832c842017386fcdd67"
HOMEPAGE = "http://android.git.kernel.org/?p=platform/system/core.git"
PROVIDES = "mkbootimg-native"

DEPENDS = "libmincrypt-native"

# Handle do_fetch ourselves...  The automated tools don't work nicely with this...
do_fetch () {
	install -d ${S}
	cp -rf ${WORKSPACE}/system/core/${MY_PN}/* ${S}
	cp -f ${THISDIR}/files/makefile ${S}
}

EXTRA_OEMAKE = "INCLUDES='-Imincrypt' LIBS='${libdir}/mincrypt/libmincrypt.a'"

do_install() {
	install -d ${D}${bindir}
	install ${MY_PN} ${D}${bindir}
}

NATIVE_INSTALL_WORKS = "1"
