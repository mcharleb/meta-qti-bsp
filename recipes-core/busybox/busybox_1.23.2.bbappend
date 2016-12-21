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
            file://syslog-startup.conf \
            file://busybox_klogd.patch;patchdir=.. \
            file://iio.sh \
            file://0001-Support-MTP-function.patch \
"

prefix = ""

BUSYBOX_SPLIT_SUID = "0"

do_install_append() {
    # systemd is udev compatible.
    if ${@base_contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${sysconfdir}/udev/scripts/
        install -m 0755 ${WORKDIR}/automountsdcard.sh \
            ${D}${sysconfdir}/udev/scripts/automountsdcard.sh
    else
        install -d ${D}${sysconfdir}/mdev
        install -m 0755 ${WORKDIR}/automountsdcard.sh ${D}${sysconfdir}/mdev/
        install -m 0755 ${WORKDIR}/find-touchscreen.sh ${D}${sysconfdir}/mdev/
        install -m 0755 ${WORKDIR}/usb.sh ${D}${sysconfdir}/mdev/
        install -m 0755 ${WORKDIR}/iio.sh ${D}${sysconfdir}/mdev/
    fi
}

#FILES_${PN}-mdev += "${sysconfdir}/mdev/* "
