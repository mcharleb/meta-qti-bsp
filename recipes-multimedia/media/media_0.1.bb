inherit autotools pkgconfig qlicense
DESCRIPTION = "media"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://hardware/qcom/media/"

S = "${WORKDIR}/hardware/qcom/media"

DEPENDS = "adreno200"
DEPENDS += "system-media"
DEPENDS += "av-frameworks"
DEPENDS += "glib-2.0"

DEPENDS += "${@ '' if d.getVar('IS_APQ8009') else 'display-hal mm-video-noship'}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

# setting video flags
IS_ION = "yes"
MEDIA_EXTN_SUPPORTED = "no"

IS_MSM8610 = '${@bb.utils.contains_any('BASEMACHINE', ["apq8010", "msm8610", "msm8010"], "yes", "", d)}'
IS_MSM8953 = '${@bb.utils.contains_any('BASEMACHINE', ["apq8053", "msm8953", "msm8053"], "yes", "", d)}'
IS_MSM8996 = '${@bb.utils.contains_any('BASEMACHINE', ["apq8096", "msm8996", "msm8096"], "yes", "", d)}'
IS_MASTER_SIDE_CP = '${@bb.utils.contains_any('BASEMACHINE', ["apq8096", "msm8996"], "yes", "", d)}'
UBWC_SUPPORTED = '${@bb.utils.contains_any('BASEMACHINE', ["msm8996", "msmcobalt", "apq8096"], "yes", "", d)}'
PQ_SUPPORTED = '${@bb.utils.contains_any('BASEMACHINE', ["msm8996", "msm8953", "apq8096", "apq8053"], "yes", "", d)}'
IS_MSM8226 = '${@bb.utils.contains_any('BASEMACHINE', ["msm8226", "msm8916", "msm8909", "apq8026", "apq8016", "apq8009"], "yes", "", d)}'
MM-VIDEO = '${@bb.utils.contains_any('BASEMACHINE', ["apq8096", "apq8053", "apq8010"], "yes", "", d)}'
IS_APQ8009 = '${@bb.utils.contains_any('BASEMACHINE', ["msm8909", "apq8009"], "yes", "", d)}'

# configure features
EXTRA_OECONF_append =" --enable-use-glib="yes""
EXTRA_OECONF_append =" --enable-target-uses-ion=${IS_ION}"
EXTRA_OECONF_append =" --enable-build-mm-video=${MM-VIDEO}"
EXTRA_OECONF_append =" --enable-target-msm8953=${IS_MSM8953}"
EXTRA_OECONF_append =" --enable-target-msm8996=${IS_MSM8996}"
EXTRA_OECONF_append =" --enable-target-msm8909=${IS_APQ8009}"
EXTRA_OECONF_append =" --enable-target-msm8610=${IS_MSM8610}"
EXTRA_OECONF_append =" --enable-is-ubwc-supported=${UBWC_SUPPORTED}"
EXTRA_OECONF_append =" --enable-targets-that-support-pq=${PQ_SUPPORTED}"
EXTRA_OECONF_append =" --enable-targets-that-use-flag-msm8226=${IS_MSM8226}"
EXTRA_OECONF_append =" --enable-master-side-cp-target-list=${IS_MASTER_SIDE_CP}"
EXTRA_OECONF_append =" --enable-target-uses-media-extensions=${MEDIA_EXTN_SUPPORTED}"

# configure headers
EXTRA_OECONF_append ="--with-glib"
EXTRA_OECONF_append =" --with-ui-headers=${STAGING_INCDIR}/ui/"
EXTRA_OECONF_append =" --with-android-headers=${STAGING_INCDIR}/"
#EXTRA_OECONF_append =" --with-utils-headers=${STAGING_INCDIR}/utils/"
#EXTRA_OECONF_append =" --with-cutils-headers=${STAGING_INCDIR}/cutils/"
EXTRA_OECONF_append =" --with-glib-headers=${STAGING_INCDIR}/glib-2.0/"
EXTRA_OECONF_append =" --with-binder-headers=${STAGING_INCDIR}/binder/"
EXTRA_OECONF_append =" --with-adreno-headers=${STAGING_INCDIR}/adreno/"
EXTRA_OECONF_append =" --with-glib-lib-dir=${STAGING_LIBDIR}/glib-2.0/include"
EXTRA_OECONF_append =" --with-gralloc-headers=${STAGING_INCDIR}/libgralloc/"
EXTRA_OECONF_append =" --with-qdutils-headers=${STAGING_INCDIR}/libqdutils/"
EXTRA_OECONF_append =" --with-libgpustats-headers=${STAGING_INCDIR}/libgpustats/"
EXTRA_OECONF_append =" --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"
EXTRA_OECONF_append =" --with-display-headers=${STAGING_INCDIR}/qcom/display"

FILES_${PN}-dbg  = "${libdir}/.debug/*"
FILES_${PN}      = "${libdir}/*.so ${libdir}/*.so.* ${libdir}/*.so.*.*.* ${sysconfdir}/* ${bindir}/* ${libdir}/pkgconfig/*"
FILES_${PN}-dev  = "${libdir}/*.la ${includedir}"

do_install_append() {
    oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
    mkdir -p ${STAGING_INCDIR}/mm-core
	mkdir -p ${STAGING_INCDIR}/libstagefrighthw
	install -m 0644 ${S}/mm-core/inc/*.h ${STAGING_INCDIR}/mm-core
    install -m 0644 ${S}/libstagefrighthw/*.h ${STAGING_INCDIR}/libstagefrighthw
}

INSANE_SKIP_${PN} += "dev-so"
EXCLUDE_FROM_SHLIBS = "1"
