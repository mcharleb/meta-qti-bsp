inherit native

PR = "r0"

MY_PN = "dtbtool"

DESCRIPTION = "Boot image creation tool from Android"
LICENSE = "Apache-2.0"
# The license declaration's in the source for the command...
LIC_FILES_CHKSUM = "file://dtbtool.c;md5=56fa4b5679b2e8e544882c7f5a469e93"
PROVIDES = "dtbtool-native"

# Handle do_fetch ourselves...  The automated tools don't work nicely with this...
do_fetch () {
	install -d ${S}
	cp -rf ${WORKSPACE}/system/core/${MY_PN}/* ${S}
	cp -f ${THISDIR}/files/makefile ${S}
}

do_install() {
	install -d ${D}${bindir}
	install ${MY_PN} ${D}${bindir}
}

NATIVE_INSTALL_WORKS = "1"
