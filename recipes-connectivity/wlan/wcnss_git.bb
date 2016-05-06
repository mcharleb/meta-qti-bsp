DESCRIPTION = "WCNSS platform"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
LICENSE = "BSD"

PR = "r1"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/wlan/prima/firmware_bin \
           file://set_wcnss_mode"

S = "${WORKDIR}/qcom-opensource/wlan/firmware_bin"

inherit update-rc.d

do_install() {
    install -d ${D}/etc
    install -d ${D}/etc/init.d
    install "${WORKDIR}"/set_wcnss_mode ${D}/etc/init.d

    mkdir -p ${D}/lib/firmware/wlan/prima
    cp -pPr ${S}/* ${D}/lib/firmware/wlan/prima
}

INITSCRIPT_NAME = "set_wcnss_mode"
INITSCRIPT_PARAMS = "start 60 2 3 4 5 . stop 20 0 1 6 ."

FILES_${PN} = "/lib/firmware/*"
FILES_${PN} += "/etc/*"

