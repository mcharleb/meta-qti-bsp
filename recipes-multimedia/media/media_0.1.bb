inherit androidmk deploy

SUMMARY = "Multimedia libraries and SDK"
SECTION = "multimedia"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://hardware/qcom/media/"
SRC_URI += "file://0001-media-build-omx-encoder-component-for-linux.patch"

SRCREV = "${AUTOREV}"
S      = "${WORKDIR}/hardware/qcom/media"

DEPENDS += "adreno200"
DEPENDS += "display-hal"
DEPENDS += "system-media"
DEPENDS += "av-frameworks"

PACKAGES = "${PN}"

EXTRA_OEMAKE += " BOARD_USES_ADRENO=false"
EXTRA_OEMAKE += " VDEC_ENABLE=false"

CFLAGS += "-Dstrlcpy=g_strlcpy"
CFLAGS += "-Dstrlcat=g_strlcat"
CFLAGS += "-I${STAGING_INCDIR}/cutils/"
CFLAGS += "-I${STAGING_INCDIR}/adreno/"
CFLAGS += "-I${STAGING_INCDIR}/adreno/"
CFLAGS += "-I${STAGING_INCDIR}/ui/"
CFLAGS += "-I${STAGING_INCDIR}"
CFLAGS += "-I${STAGING_INCDIR}/glib-2.0"
CFLAGS += "-I${STAGING_LIBDIR}/glib-2.0/include"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include"
CFLAGS += "-I${STAGING_INCDIR}/libgralloc/"
CFLAGS += "-I${STAGING_INCDIR}/utils/"
CFLAGS += "-I${STAGING_INCDIR}/binder/"

CFLAGS += "-include stdint.h"
CFLAGS += "-Dstrlcpy=g_strlcpy"
CFLAGS += "-Dstrlcat=g_strlcat"
CFLAGS += "-std=c++11"

CFLAGS += "-include glib.h"
CFLAGS += "-include glibconfig.h"
CFLAGS += "-include sys/ioctl.h"

CFLAGS += "-DPTHREAD_RECURSIVE_MUTEX_INITIALIZER_NP"
CFLAGS += "-DVENC_PERF_NO_SUPPORT"

LDFLAGS += "-lcutils"
LDFLAGS += "-lglib-2.0"
LDFLAGS += "-llog"
LDFLAGS += "-lbase"
LDFLAGS += "-lutils"
LDFLAGS += "-lbinder"

export TARGET_LIBRARY_SUPPRESS_LIST="libgui libbinder"

do_fixup_before_compile () {
    #
    # replace "$(TOP)/hardware/qcom/media/" in mk files
    find ${S}/ -type f -name "*.mk" -exec sed -i 's/\$(TOP)\/hardware\/qcom\/media\//\$(QCOM_MEDIA_ROOT)\//g' {} +

    #
    # replace "hardware/qcom/media/" in mk files
    find ${S}/ -type f -name "*.mk" -exec sed -i 's/hardware\/qcom\/media\//\$(QCOM_MEDIA_ROOT)\//g' {} +

    #
    # comment out all the occurrences of "-D_VQZIP_" in mk files
    find ${S}/ -type f -name "*.mk" -exec sed -i 's/libmm-venc-def\ +=\ -D_VQZIP_/\#libmm-venc-def\ +=\ -D_VQZIP_/g' {} +

    # Donot build the libstagefrighthw OMXPlugin
    rm -f ${S}/libstagefrighthw/Android.mk

    #
    # replace $(TARGET_OUT_HEADERS)/adreno with $(TARGET_SYSROOT)/usr/include/adreno
    find ${S}/ -type f -name "*.mk" -exec sed -i 's/$(TARGET_OUT_HEADERS)\/adreno/$(TARGET_SYSROOT)\/usr\/include\/adreno/g' {} +
}
addtask fixup_before_compile after do_patch before do_configure

do_compile() {
    # Current support is limited to 32-bit build
    #
    if [ "${MLPREFIX}" == "lib32-" ]; then
        androidmk_setenv
        oe_runmake -f ${LA_COMPAT_DIR}/build/core/main.mk BUILD_MODULES_IN_PATHS=${S} \
            all_modules SHOW_COMMANDS=true || die "make failed"
    else
        die "not supported"
    fi
}

do_install_append() {
        install -d ${D}${includedir}
        install -m 0644 ${S}/mm-core/inc/*.h -D ${D}${includedir}/mm-core/
        install -m 0644 ${S}/mm-video-v4l2/vidc/venc/inc/omx_video_common.h -D ${D}${includedir}/mm-video-v4l2/vidc/venc/inc/omx_video_common.h
        install -m 0644 ${S}/libstagefrighthw/QComOMXMetadata.h	-D ${D}${includedir}/libstagefrighthw/QComOMXMetadata.h
}
