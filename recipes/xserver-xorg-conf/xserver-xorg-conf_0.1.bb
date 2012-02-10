DESCRIPTION = "Machine specific xorg.conf files"
# FIXME - This needs the licensing file somewhere so we can get a checksum!!
# (Also...is this BSD or more MIT/X11 since this is an xorg file??)
#LICENSE = "BSD"
LICENSE = "CLOSED"
PR = "r1"

SRC_URI = "file://xorg.conf"

do_install() {
	install -d ${D}/${sysconfdir}/X11
	install -m 0644 ${WORKDIR}/xorg.conf ${D}/${sysconfdir}/X11/
}

CONFFILES_${PN} += "${sysconfdir}/X11/xorg.conf"
PACKAGE_ARCH = "${MACHINE_ARCH}"
