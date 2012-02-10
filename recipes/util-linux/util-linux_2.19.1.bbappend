PACKAGES =+ "util-linux-blkid"
FILES_util-linux-blkid = "${sbindir}/blkid"
PR .= "micro3"

do_install_append() {
	if [ -f ${D}${sbindir}/blkid.util-linux ]; then
	mv ${D}${sbindir}/blkid.util-linux ${D}${sbindir}/blkid
	fi
}
