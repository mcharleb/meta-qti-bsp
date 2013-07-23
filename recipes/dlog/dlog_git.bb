inherit autotools

PR = "r1"

DESCRIPTION = "Userspace Logging service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://${WORKSPACE}/dlog"

S = ${WORKDIR}/dlog

inherit update-rc.d

INITSCRIPT_NAME = "dlog"
INITSCRIPT_PARAMS = "start 01 2 3 4 5 . stop 99 0 1 6 ."

FILES_${PN} += "/opt/etc/*"

do_install_append() {
	install -d ${D}/opt/etc
	echo 1 > ${D}/opt/etc/platformlog.conf
	install -m 0755 ${S}/dlog.sh -D ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

pkg_postinst () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f ${INITSCRIPT_NAME} remove
        update-rc.d $OPT ${INITSCRIPT_NAME} ${INITSCRIPT_PARAMS}
}

