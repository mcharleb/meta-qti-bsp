DESCRIPTION = "Installing audio init script"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
PR = "r1"

SRC_URI = "file://files/init_qcom_audio"

inherit autotools

S = ${WORKDIR}/init-audio

do_install() {
    install -m 0755 ${S}/../files/init_qcom_audio -D ${D}${sysconfdir}/init.d/init_qcom_audio
}

pkg_postinst() {
    [ -n "$$D" ] && OPT="-r $D" || OPT="-s"
    update-rc.d $OPT -f init_qcom_audio
    update-rc.d $OPT init_qcom_audio start 60 2 3 4 5 . stop 40 0 1 6 .
}