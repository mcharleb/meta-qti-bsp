DESCRIPTION = "Start up script for finding partitions used in recovery"
HOMEPAGE = "http://codeaurora.org"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
LICENSE = "BSD"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI +="file://${BASEMACHINE}/find_recovery_partitions.sh"
S = "${WORKDIR}/${BASEMACHINE}"

PR = "r3"

inherit update-rc.d

INITSCRIPT_NAME = "find_recovery_partitions.sh"
INITSCRIPT_PARAMS_mdm9635 = "start 01 5 ."

do_install() {
    install -m 0755 ${WORKDIR}/${BASEMACHINE}/find_recovery_partitions.sh -D ${D}${sysconfdir}/init.d/find_recovery_partitions.sh
}
