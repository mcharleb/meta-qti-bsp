DESCRIPTION = "MSM init scripts"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r1"

RCONFLICTS_${PN} = "initscripts"

SRC_URI = "file://mountall.sh"


do_install () {
#
# Create directories and install device independent scripts
#
        install -d ${D}${sysconfdir}/init.d
        install -d ${D}${sysconfdir}/rcS.d
        install -m 0755      ${WORKDIR}/mountall.sh      ${D}${sysconfdir}/init.d

#
# Create runlevel links
#
        ln -sf      ../init.d/mountall.sh      ${D}${sysconfdir}/rcS.d/S35mountall.sh
}
