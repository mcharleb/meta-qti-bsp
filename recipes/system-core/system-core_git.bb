DESCRIPTION = "Android system/core components"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://adb/NOTICE;md5=2ddb23e63b1f9c3c46aaa4195f819a6d"

SRC_URI = "file://${WORKSPACE}/system/core"
SRC_URI += "file://files/50-log.rules"

PR = "r4"

inherit autotools

S = "${WORKDIR}/core"

INITSCRIPT_NAME = "adbd"
INITSCRIPT_PARAMS = "start 42 S 2 3 4 5 S . stop 80 0 1 6 ."

inherit update-rc.d

do_install_append() {
   install -m 0755 -d ${D}${includedir}/cutils
   install -m 0644  ${S}/include/cutils/* ${D}${includedir}/cutils
   install -m 0644 -D ${S}/include/android/log.h ${D}${includedir}/android/log.h
   install -m 0644 -D ${S}/include/pixelflinger/format.h ${D}${includedir}/pixelflinger/format.h
   install -m 0644 -D ${S}/include/pixelflinger/pixelflinger.h ${D}${includedir}/pixelflinger/pixelflinger.h

   install -m 0644 -D ${S}/../files/50-log.rules ${D}${sysconfdir}/udev/rules.d/50-log.rules

   # Prefer adbd to be located in /sbin for historical reasons
   rm ${D}${bindir}/adbd
   install -m 0755 ${S}/adb/adbd -D ${D}/sbin/adbd
   install -m 0755 ${S}/adb/start_adbd -D ${D}${sysconfdir}/init.d/adbd
   install -m 0755 ${S}/usb/start_usb -D ${D}${sysconfdir}/init.d/usb
   install -m 0755 ${S}/usb/usb_composition -D ${D}${bindir}/
   install -d ${D}${bindir}/usb/compositions/
   install -m 0755 ${S}/usb/compositions/* -D ${D}${bindir}/usb/compositions/
   ln -s /usr/bin/usb/compositions/9025 ${D}${bindir}/usb/boot_hsusb_composition
   ln -s /usr/bin/usb/compositions/empty ${D}${bindir}/usb/boot_hsic_composition
}

pkg_postinst () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f ${INITSCRIPT_NAME} remove
        update-rc.d $OPT ${INITSCRIPT_NAME} ${INITSCRIPT_PARAMS}
        update-rc.d $OPT -f usb remove
        update-rc.d $OPT usb start 41 S .
}


PACKAGES =+ "${PN}-libcutils-dbg ${PN}-libcutils ${PN}-libcutils-dev ${PN}-libcutils-static"
FILES_${PN}-libcutils-dbg    = "${libdir}/.debug/libcutils.*"
FILES_${PN}-libcutils        = "${libdir}/libcutils.so.*"
FILES_${PN}-libcutils-dev    = "${libdir}/libcutils.so ${libdir}/libcutils.la ${includedir}"
FILES_${PN}-libcutils-static = "${libdir}/libcutils.a"

PACKAGES =+ "${PN}-adbd-dbg ${PN}-adbd"
FILES_${PN}-adbd-dbg = "/sbin/.debug/adbd"
FILES_${PN}-adbd     = "/sbin/adbd ${sysconfdir}/init.d/adbd"

PACKAGES =+ "${PN}-usb"
FILES_${PN}-usb     = "${sysconfdir}/init.d/usb ${bindir}/usb_composition ${bindir}/usb/compositions/* ${bindir}/usb/*"

PACKAGES =+ "${PN}-liblog-dbg ${PN}-liblog ${PN}-liblog-dev ${PN}-liblog-static"
FILES_${PN}-liblog-dbg    = "${libdir}/.debug/liblog.* ${bindir}/.debug/logcat"
FILES_${PN}-liblog        = "${libdir}/liblog.so.* ${bindir}/logcat ${sysconfdir}/udev/rules.d/50-log.rules"
FILES_${PN}-liblog-dev    = "${libdir}/liblog.so ${libdir}/liblog.la"
FILES_${PN}-liblog-static = "${libdir}/liblog.a"

