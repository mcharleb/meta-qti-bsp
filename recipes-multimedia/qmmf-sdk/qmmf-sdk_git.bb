inherit autotools pkgconfig update-rc.d

DESCRIPTION = "QMMF SDK"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

PR = "r0"

DEPENDS = "liblog"
DEPENDS += "libcutils"
DEPENDS += "native-frameworks"
DEPENDS += "system-core"
DEPENDS += "glib-2.0"
DEPENDS += "av-frameworks"
DEPENDS += "gtest"
DEPENDS += "media"

CFLAGS += "-I${STAGING_INCDIR}"
EXTRA_OECONF += " --with-gralloc-library=${WORKSPACE}/display/display-hal"
EXTRA_OECONF += " --with-mm-core=${WORKSPACE}/hardware/qcom/media/mm-core/inc"
EXTRA_OECONF += " --with-camerahal=${WORKSPACE}/camera/lib/QCamera2/HAL3"
EXTRA_OECONF_append = " --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-sdk"
SRC_URI  += "file://qmmf-server.sh"

S = "${WORKDIR}/qmmf-sdk"

INITSCRIPT_NAME = "qmmf-server.sh"
INITSCRIPT_PARAMS = "start 25 S 2 3 4 5 S . stop 75 0 1 6 ."

do_install_append() {
    install -m 0755 ${WORKDIR}/qmmf-server.sh -D ${D}/${sysconfdir}/init.d/qmmf-server.sh
}

pkg_postinst_${PN} () {
  update-alternatives --install ${sysconfdir}/init.d/qmmf-server.sh qmmf-server.sh qmmf-server 60
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
}

do_package_qa () {
}

FILES_${PN}-qmmf-server-dbg = "${bindir}/.debug/qmmf-server"
FILES_${PN}-qmmf-server     = "${bindir}/qmmf-server ${sysconfdir}/init.d/qmmf-server.sh"

FILES_${PN}-libqmmf_recorder_client-dbg    = "${libdir}/.debug/libqmmf_recorder_client.*"
FILES_${PN}-libqmmf_recorder_client        = "${libdir}/libqmmf_recorder_client.so.*"
FILES_${PN}-libqmmf_recorder_client-dev    = "${libdir}/libqmmf_recorder_client.so ${libdir}/libqmmf_recorder_client.la ${includedir}"

FILES_${PN}-libqmmf_recorder_service-dbg    = "${libdir}/.debug/libqmmf_recorder_service.*"
FILES_${PN}-libqmmf_recorder_service        = "${libdir}/libqmmf_recorder_service.so.*"
FILES_${PN}-libqmmf_recorder_service-dev    = "${libdir}/libqmmf_recorder_service.so ${libdir}/libqmmf_recorder_service.la ${includedir}"

FILES_${PN}-libcamera_adaptor-dbg    = "${libdir}/.debug/libcamera_adaptor.*"
FILES_${PN}-libcamera_adaptor        = "${libdir}/libcamera_adaptor.so.*"
FILES_${PN}-libcamera_adaptor-dev    = "${libdir}/libcamera_adaptor.so ${libdir}/libcamera_adaptor.la ${includedir}"

FILES_${PN}-libcodec_adaptor-dbg    = "${libdir}/.debug/libcodec_adaptor.*"
FILES_${PN}-libcodec_adaptor        = "${libdir}/libcodec_adaptor.so.*"
FILES_${PN}-libcodec_adaptor-dev    = "${libdir}/libcodec_adaptor.so ${libdir}/libcodec_adaptor.la ${includedir}"

FILES_${PN}-libqmmf_audio_client-dbg    = "${libdir}/.debug/libqmmf_audio_client.*"
FILES_${PN}-libqmmf_audio_client        = "${libdir}/libqmmf_audio_client.so.*"
FILES_${PN}-libqmmf_audio_client-dev    = "${libdir}/libqmmf_audio_client.so ${libdir}/libqmmf_audio_client.la ${includedir}"

FILES_${PN}-libqmmf_audio_service-dbg    = "${libdir}/.debug/libqmmf_audio_service.*"
FILES_${PN}-libqmmf_audio_service        = "${libdir}/libqmmf_audio_service.so.*"
FILES_${PN}-libqmmf_audio_service-dev    = "${libdir}/libqmmf_audio_service.so ${libdir}/libqmmf_audio_service.la ${includedir}"
