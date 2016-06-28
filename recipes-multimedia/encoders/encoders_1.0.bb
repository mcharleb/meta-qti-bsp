inherit autotools qcommon

DESCRIPTION = "encoders"
PR = "r0"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_DIR = "${WORKSPACE}/hardware/qcom/audio/mm-audio/"

S = "${WORKDIR}/hardware/qcom/audio/mm-audio/"
EXTRA_OECONF_append += "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"
EXTRA_OECONF_append += "--with-glib"

DEPENDS = "media"
