inherit autotools pkgconfig

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
DEPENDS += "cairo"
DEPENDS += "mm-parser"
DEPENDS += "mm-osal"
DEPENDS += "audiohal"
DEPENDS += "fastcv-noship"

CFLAGS += "-I${STAGING_INCDIR}"
CFLAGS += "-I${STAGING_INCDIR}/mm-parser/include"
CFLAGS += "-I${STAGING_INCDIR}/mm-osal/include"
CFLAGS += "-I${STAGING_INCDIR}/fastcv"
TARGET_CFLAGS += "-I${STAGING_INCDIR}/qcom/display"

EXTRA_OECONF += " --with-gralloc-library=${WORKSPACE}/display/display-hal"
EXTRA_OECONF += " --with-mm-core=${WORKSPACE}/hardware/qcom/media/mm-core/inc"
EXTRA_OECONF += " --with-camerahal=${WORKSPACE}/camera/lib/QCamera2/HAL3"
EXTRA_OECONF_append = " --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-sdk"
SRC_URI  += "file://qmmf-server.service"

S = "${WORKDIR}/qmmf-sdk"

do_install_append () {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}/etc/systemd/system/
        install -m 0644 ${WORKDIR}/qmmf-server.service -D ${D}/etc/systemd/system/qmmf-server.service
        install -d ${D}/etc/systemd/system/multi-user.target.wants/
        # enable the service for multi-user.target
        ln -sf /etc/systemd/qmmf-server.service \
           ${D}/etc/systemd/system/multi-user.target.wants/qmmf-server.service
    fi
}

FILES_${PN}-qmmf-server-dbg = "${bindir}/.debug/qmmf-server"
FILES_${PN}-qmmf-server     = "${bindir}/qmmf-server"
FILES_${PN}-qmmf-server    += "/etc/systemd/system/"

FILES_${PN}-libqmmf_recorder_client-dbg    = "${libdir}/.debug/libqmmf_recorder_client.*"
FILES_${PN}-libqmmf_recorder_client        = "${libdir}/libqmmf_recorder_client.so.*"
FILES_${PN}-libqmmf_recorder_client-dev    = "${libdir}/libqmmf_recorder_client.so ${libdir}/libqmmf_recorder_client.la ${includedir}"

FILES_${PN}-libqmmf_recorder_service-dbg    = "${libdir}/.debug/libqmmf_recorder_service.*"
FILES_${PN}-libqmmf_recorder_service        = "${libdir}/libqmmf_recorder_service.so.*"
FILES_${PN}-libqmmf_recorder_service-dev    = "${libdir}/libqmmf_recorder_service.so ${libdir}/libqmmf_recorder_service.la ${includedir}"

FILES_${PN}-libqmmf_display_client-dbg    = "${libdir}/.debug/libqmmf_display_client.*"
FILES_${PN}-libqmmf_display_client        = "${libdir}/libqmmf_display_client.so.*"
FILES_${PN}-libqmmf_display_client-dev    = "${libdir}/libqmmf_display_client.so ${libdir}/libqmmf_display_client.la ${includedir}"

FILES_${PN}-libqmmf_display_service-dbg    = "${libdir}/.debug/libqmmf_display_service.*"
FILES_${PN}-libqmmf_display_service        = "${libdir}/libqmmf_display_service.so.*"
FILES_${PN}-libqmmf_display_service-dev    = "${libdir}/libqmmf_display_service.so ${libdir}/libqmmf_display_service.la ${includedir}"

FILES_${PN}-libcamera_adaptor-dbg    = "${libdir}/.debug/libcamera_adaptor.*"
FILES_${PN}-libcamera_adaptor        = "${libdir}/libcamera_adaptor.so.*"
FILES_${PN}-libcamera_adaptor-dev    = "${libdir}/libcamera_adaptor.so ${libdir}/libcamera_adaptor.la ${includedir}"

FILES_${PN}-libcodec_adaptor-dbg    = "${libdir}/.debug/libcodec_adaptor.*"
FILES_${PN}-libcodec_adaptor        = "${libdir}/libcodec_adaptor.so.*"
FILES_${PN}-libcodec_adaptor-dev    = "${libdir}/libcodec_adaptor.so ${libdir}/libcodec_adaptor.la ${includedir}"

FILES_${PN}-libav_codec-dbg    = "${libdir}/.debug/libav_codec.*"
FILES_${PN}-libav_codec        = "${libdir}/libav_codec.so.*"
FILES_${PN}-libav_codec-dev    = "${libdir}/libav_codec.so ${libdir}/libav_codec.la ${includedir}"

FILES_${PN}-libqmmf_audio_client-dbg    = "${libdir}/.debug/libqmmf_audio_client.*"
FILES_${PN}-libqmmf_audio_client        = "${libdir}/libqmmf_audio_client.so.*"
FILES_${PN}-libqmmf_audio_client-dev    = "${libdir}/libqmmf_audio_client.so ${libdir}/libqmmf_audio_client.la ${includedir}"

FILES_${PN}-libqmmf_audio_service-dbg    = "${libdir}/.debug/libqmmf_audio_service.*"
FILES_${PN}-libqmmf_audio_service        = "${libdir}/libqmmf_audio_service.so.*"
FILES_${PN}-libqmmf_audio_service-dev    = "${libdir}/libqmmf_audio_service.so ${libdir}/libqmmf_audio_service.la ${includedir}"

FILES_${PN}-libqmmf_player_client-dbg    = "${libdir}/.debug/libqmmf_player_client.*"
FILES_${PN}-libqmmf_player_client        = "${libdir}/libqmmf_player_client.so.*"
FILES_${PN}-libqmmf_player_client-dev    = "${libdir}/libqmmf_player_client.so ${libdir}/libqmmf_player_client.la ${includedir}"

FILES_${PN}-libqmmf_player_service-dbg    = "${libdir}/.debug/libqmmf_player_service.*"
FILES_${PN}-libqmmf_player_service        = "${libdir}/libqmmf_player_service.so.*"
FILES_${PN}-libqmmf_player_service-dev    = "${libdir}/libqmmf_player_service.so ${libdir}/libqmmf_player_service.la ${includedir}"

INSANE_SKIP_${PN} += "build-deps dev-deps file-rdeps"
