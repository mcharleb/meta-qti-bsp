inherit autotools pkgconfig update-rc.d

DESCRIPTION = "Android IPC utilities"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

DEPENDS = "liblog libcutils libhardware libselinux system-core glib-2.0"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI   = "file://frameworks/native"
SRC_URI   += "file://servicemanager.sh"

S = "${WORKDIR}/native"

INITSCRIPT_NAME = "servicemanager.sh"

EXTRA_OECONF = " --with-core-includes=${WORKSPACE}/system/core/include --with-glib"

CFLAGS += "-I${STAGING_INCDIR}/libselinux"

do_install_append() {
    install -m 0755 ${WORKDIR}/servicemanager.sh -D ${D}/${sysconfdir}/init.d/servicemanager.sh
}

pkg_postinst_${PN} () {
  update-alternatives --install ${sysconfdir}/init.d/servicemanager.sh servicemanager.sh servicemanager 60
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f servicemanager remove
        update-rc.d $OPT servicemanager start 25 S 2 3 4 5 S . stop 75 0 1 6 .
}

FILES_${PN}-servicemanager-dbg = "${bindir}/.debug/servicemanager"
FILES_${PN}-servicemanager     = "${bindir}/servicemanager ${sysconfdir}/init.d/servicemanager.sh"

FILES_${PN}-libbinder-dbg    = "${libdir}/.debug/libbinder.*"
FILES_${PN}-libbinder        = "${libdir}/libbinder.so.*"
FILES_${PN}-libbinder-dev    = "${libdir}/libbinder.so ${libdir}/libbinder.la ${includedir}"
FILES_${PN}-libbinder-static = "${libdir}/libbinder.a"

FILES_${PN}-libui-dbg    = "${libdir}/.debug/libui.*"
FILES_${PN}-libui        = "${libdir}/libui.so.*"
FILES_${PN}-libbui-dev    = "${libdir}/libui.so ${libdir}/libui.la ${includedir}"

