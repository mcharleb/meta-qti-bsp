inherit autotools qcommon

DESCRIPTION = "display Library"
PR = "r8"

PACKAGES = "${PN}"

SRC_DIR = "${WORKSPACE}/display/display-hal/"
S = "${WORKDIR}/display/display-hal/"

EXTRA_OECONF = " --with-core-includes=${WORKSPACE}/system/core/include"
EXTRA_OECONF += " --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

LDFLAGS += "-llog -lhardware -lutils -lcutils"

CPPFLAGS += "-DTARGET_HEADLESS"
CPPFLAGS += "-DVENUS_COLOR_FORMAT"
CPPFLAGS += "-DPAGE_SIZE=4096"
CPPFLAGS += "-I${WORKSPACE}/display/display-hal/libqdutils"
CPPFLAGS += "-I${WORKSPACE}/display/display-hal/libqservice"
CPPFLAGS += "-I${WORKSPACE}/display/display-hal/sdm/include"
CPPFLAGS += "-I${WORKSPACE}/display/display-hal/libgralloc"
CPPFLAGS += "-I${WORKSPACE}/system/core/include"

do_install_append () {
    if [ "${MLPREFIX}" == "lib32-" ]; then
        mv ${D}/usr/lib/libgralloc.so ${D}/usr/lib/libgralloc.${BASEMACHINE}.so
    else
        mv ${D}/usr/lib64/libgralloc.so ${D}/usr/lib64/libgralloc.${BASEMACHINE}.so
    fi
}