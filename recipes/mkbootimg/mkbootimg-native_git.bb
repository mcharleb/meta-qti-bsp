inherit native

PR = "r3"

MY_PN = "mkbootimg"

DESCRIPTION = "Boot image creation tool from Android"
LICENSE = "Apache-2.0"
# The license declaration's in the source for the command...
LIC_FILES_CHKSUM = "file://bootimg.h;startline=1;endline=3;md5=f59c7a8f3dd40789f94737fe8c36dad0"
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
