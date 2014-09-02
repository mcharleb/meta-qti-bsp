FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
            file://find-touchscreen.sh \
            file://automountsdcard.sh \
            file://usb.sh \
            file://mdev.conf \
            file://profile \
            file://fstab \
            file://inittab \
            file://rcS \
            file://no-console.cfg \
            file://login.cfg \
            file://mdev.cfg \
            file://base.cfg \
"

prefix = ""

do_install_append() {
    install -d ${D}${sysconfdir}/mdev
    install -m 0755 ${WORKDIR}/automountsdcard.sh ${D}${sysconfdir}/mdev/
    install -m 0755 ${WORKDIR}/find-touchscreen.sh ${D}${sysconfdir}/mdev/
    install -m 0755 ${WORKDIR}/usb.sh ${D}${sysconfdir}/mdev/
}

FILES_${PN}-mdev += "${sysconfdir}/mdev/* "
