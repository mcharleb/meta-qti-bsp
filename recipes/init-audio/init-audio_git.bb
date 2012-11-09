DESCRIPTION = "Installing audio init script"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://../files/init_qcom_audio;md5=c94d9416e5527b4ab02ef2ac32cffd94"
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
