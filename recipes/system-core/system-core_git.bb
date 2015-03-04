inherit autotools update-rc.d

DESCRIPTION = "Android system/core components"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI   = "file://system/core/"
SRC_URI  += "file://50-log.rules"

S = "${WORKDIR}/system/core"
PR = "r15"

INSANE_SKIP_${PN}-libcutils-static = "staticdev"
INSANE_SKIP_${PN}-liblog-static = "staticdev"

DEPENDS = "zlib openssl glib-2.0 libcap"

EXTRA_OECONF_append_msm8960 = " --with-host-os=${HOST_OS}"
EXTRA_OECONF_append_msm8974 = " --with-host-os=${HOST_OS}"
EXTRA_OECONF_append_msm8610 = " --with-host-os=${HOST_OS}"
EXTRA_OECONF_append_msm8226 = " --with-host-os=${HOST_OS}"
EXTRA_OECONF_append += "--with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"

do_install_append() {
   install -m 0755 -d ${D}${includedir}/cutils
   install -m 0644  ${S}/include/cutils/* ${D}${includedir}/cutils
   install -m 0644 -D ${S}/include/android/log.h ${D}${includedir}/android/log.h
   install -m 0644 -D ${S}/include/pixelflinger/format.h ${D}${includedir}/pixelflinger/format.h
   install -m 0644 -D ${S}/include/pixelflinger/pixelflinger.h ${D}${includedir}/pixelflinger/pixelflinger.h

   install -m 0644 -D ${S}/../../50-log.rules ${D}${sysconfdir}/udev/rules.d/50-log.rules

   # Prefer adbd to be located in /sbin for historical reasons
   rm ${D}${bindir}/adbd
   install -m 0755 ${S}/adb/adbd -D ${D}/sbin/adbd
   install -m 0755 ${S}/adb/start_adbd -D ${D}${sysconfdir}/init.d/adbd
   install -m 0755 ${S}/usb/start_usb -D ${D}${sysconfdir}/init.d/usb
   install -m 0755 ${S}/rootdir/etc/init.qcom.post_boot.sh -D ${D}${sysconfdir}/init.d/init_qcom_post
   install -m 0755 ${S}/usb/usb_composition -D ${D}${base_sbindir}/
   install -m 0755 ${S}/usb/debuger/usb_debug -D ${D}${base_sbindir}/
   install -m 0755 ${S}/usb/target -D ${D}${base_sbindir}/usb/target
   install -d ${D}${base_sbindir}/usb/compositions/
   install -m 0755 ${S}/usb/compositions/* -D ${D}${base_sbindir}/usb/compositions/
   install -d ${D}${base_sbindir}/usb/debuger/
   install -m 0755 ${S}/usb/debuger/debugFiles -D ${D}${base_sbindir}/usb/debuger/
   install -m 0755 ${S}/usb/debuger/help -D ${D}${base_sbindir}/usb/debuger/
   ln -s  /sbin/usb/compositions/9025 ${D}${base_sbindir}/usb/boot_hsusb_composition
   ln -s  /sbin/usb/compositions/empty ${D}${base_sbindir}/usb/boot_hsic_composition
}

do_install_append_msm8960 () {
	install -d ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/adb ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/fastboot ${DEPLOY_DIR}/host/linux/bin
}

do_install_append_msm8974 () {
	install -d ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/adb ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/fastboot ${DEPLOY_DIR}/host/linux/bin
}

do_install_append_msm8610 () {
	install -d ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/adb ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/fastboot ${DEPLOY_DIR}/host/linux/bin
}

do_install_append_msm8226 () {
	install -d ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/adb ${DEPLOY_DIR}/host/linux/bin
	install ${D}/usr/bin/fastboot ${DEPLOY_DIR}/host/linux/bin
}

INITSCRIPT_PACKAGES =+ "${PN}-usb"
INITSCRIPT_NAME_${PN}-usb = "usb"
INITSCRIPT_PARAMS_${PN}-usb = "start 30 S ."

PACKAGES =+ "${PN}-libcutils-dbg ${PN}-libcutils ${PN}-libcutils-dev ${PN}-libcutils-static"
FILES_${PN}-libcutils-dbg    = "${libdir}/.debug/libcutils.*"
FILES_${PN}-libcutils        = "${libdir}/libcutils.so.*"
FILES_${PN}-libcutils-dev    = "${libdir}/libcutils.so ${libdir}/libcutils.la ${includedir}"
FILES_${PN}-libcutils-static = "${libdir}/libcutils.a"

PACKAGES =+ "${PN}-adbd-dbg ${PN}-adbd"
FILES_${PN}-adbd-dbg = "/sbin/.debug/adbd"
FILES_${PN}-adbd     = "/sbin/adbd ${sysconfdir}/init.d/adbd"

PACKAGES =+ "${PN}-usb"
FILES_${PN}-usb     = "${sysconfdir}/init.d/usb ${base_sbindir}/usb_composition ${base_sbindir}/usb_composition_switch ${base_sbindir}/usb/compositions/* ${base_sbindir}/usb/*  ${base_sbindir}/usb_debug ${base_sbindir}/usb/debuger/*"

PACKAGES =+ "${PN}-liblog-dbg ${PN}-liblog ${PN}-liblog-dev ${PN}-liblog-static"
FILES_${PN}-liblog-dbg    = "${libdir}/.debug/liblog.* ${bindir}/.debug/logcat"
FILES_${PN}-liblog        = "${libdir}/liblog.so.* ${bindir}/logcat ${sysconfdir}/udev/rules.d/50-log.rules"
FILES_${PN}-liblog-dev    = "${libdir}/liblog.so ${libdir}/liblog.la"
FILES_${PN}-liblog-static = "${libdir}/liblog.a"

PACKAGES =+ "${PN}-init-qcom-post"
FILES_${PN}-init-qcom-post = " ${sysconfdir}/init.d/init_qcom_post"

