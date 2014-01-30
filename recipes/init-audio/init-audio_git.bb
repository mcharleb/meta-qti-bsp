DESCRIPTION = "Installing audio init script"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
PR = "r4"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI = "file://files/init_qcom_audio"
SRC_URI_msm8974 += "file://files/${BASEMACHINE}/init_qcom_audio"
SRC_URI_msm8610 += "file://files/${BASEMACHINE}/init_qcom_audio"

inherit autotools

S = ${WORKDIR}/init-audio
INITSCRIPT_NAME = "init_qcom_audio"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 . stop 1 0 1 6 ."

do_install() {
    install -m 0755 ${S}/../files/init_qcom_audio -D ${D}${sysconfdir}/init.d/init_qcom_audio
}

do_install_msm8974() {
    install -m 0755 ${S}/../files/${BASEMACHINE}/init_qcom_audio -D ${D}${sysconfdir}/init.d/init_qcom_audio
}

do_install_msm8610() {
    install -m 0755 ${S}/../files/${BASEMACHINE}/init_qcom_audio -D ${D}${sysconfdir}/init.d/init_qcom_audio
}

pkg_postinst() {
    [ -n "$$D" ] && OPT="-r $D" || OPT="-s"
    update-rc.d $OPT -f init_qcom_audio
    update-rc.d $OPT init_qcom_audio start 99 2 3 4 5 . stop 1 0 1 6 .
}
