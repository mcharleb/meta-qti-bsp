inherit autotools pkgconfig

DESCRIPTION = "audiohal"
SECTION = "multimedia"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

TARGET = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

FILESPATH =+ "${WORKSPACE}/:"
SRC_URI  = "file://hardware/qcom/audio/"
SRC_URI += "file://${TARGET}/"

S = "${WORKDIR}/hardware/qcom/audio/"
PR = "r0"

DEPENDS = "glib-2.0 tinycompress tinyalsa expat system-media libhardware acdbloader surround-sound-3mic"

EXTRA_OEMAKE = "DEFAULT_INCLUDES= CPPFLAGS="-I. -I${STAGING_KERNEL_BUILDDIR}/usr/include -I${STAGING_INCDIR}/surround_sound_3mic -I${STAGING_INCDIR}/sound_trigger""
EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"
EXTRA_OECONF += "--with-glib"

EXTRA_OECONF += "BOARD_SUPPORTS_QAHW=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_HDMI_EDID=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_FM_POWER_OPT=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_USBAUDIO=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_HFP=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_SSR=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_MULTI_VOICE_SESSIONS=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_COMPRESS_VOIP=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_SPKR_PROTECTION=false"
EXTRA_OECONF += "MULTIPLE_HW_VARIANTS_ENABLED=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_COMPRESS_CAPTURE=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_DTS_EAGLE=false"
EXTRA_OECONF += "DOLBY_DDP=false"
EXTRA_OECONF += "DS1_DOLBY_DAP=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_DEV_ARBI=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_SOURCE_TRACKING=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_LISTEN=false"
EXTRA_OECONF += "BOARD_SUPPORTS_SOUND_TRIGGER=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_PM_SUPPORT=false"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_EXTN_FLAC_DECODER=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_EXTN_ALAC_DECODER=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_EXTN_VORBIS_DECODER=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_EXTN_WMA_DECODER=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_MULTI_RECORD=true"
EXTRA_OECONF += "AUDIO_FEATURE_ENABLED_PROXY_DEVICE=true"

do_install_append() {
   if [ -d "${WORKDIR}/${TARGET}" ] && [ $(ls -1  ${WORKDIR}/${TARGET} | wc -l) -ne 0 ]; then
      install -d ${D}${sysconfdir}
      install -m 0755 ${WORKDIR}/${TARGET}/* ${D}${sysconfdir}/
   fi

   #Userspace expects hal name to be audio.primary.default
   cd  ${D}/${libdir}/ && ln -s audio_primary_default.so audio.primary.default.so
}

FILES_${PN}-dbg  = "${libdir}/.debug/*"
FILES_${PN}      = "${libdir}/*.so ${libdir}/*.so.* ${sysconfdir}/* ${libdir}/pkgconfig/* ${bindir}/*"
FILES_${PN}-dev  = "${libdir}/*.la ${includedir}"
INSANE_SKIP_${PN} = "dev-so"
