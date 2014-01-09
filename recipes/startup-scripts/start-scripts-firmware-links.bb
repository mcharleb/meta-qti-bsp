DESCRIPTION = "Start up script for firmware links"
HOMEPAGE = "http://codeaurora.org"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
LICENSE = "BSD"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI +="file://${BASEMACHINE}/firmware-links.sh"

PR = "r2"

inherit update-rc.d

INITSCRIPT_NAME = "firmware-links.sh"
INITSCRIPT_PARAMS_mdmkrypton = "start 01 2 3 4 5 . stop 99 0 1 6 ."

do_install() {
    install -m 0755 ${WORKDIR}/${BASEMACHINE}/firmware-links.sh -D ${D}${sysconfdir}/init.d/firmware-links.sh
}

pkg_postinst-${PN} () {
        update-alternatives --install ${sysconfdir}/init.d/firmware-links.sh firmware-links.sh firmware-links.sh 60
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f firmware-links.sh remove
        update-rd.d $OPT firmware-links.sh multiuser
}
