DESCRIPTION = "mm-image-codec"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PV = "1.0.0"
PR = "r0"

SRC_URI = "file://${WORKSPACE}/camera-hal/mm-image-codec"

S = "${WORKDIR}/mm-image-codec"

inherit autotools

# Need the kernel headers
DEPENDS += "virtual/kernel"
#DEPENDS += "mm-video-oss"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

EXTRA_OECONF_append = " --with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"
#EXTRA_OECONF_append = " --with-omx-includes=${STAGING_INCDIR}/mm-core"
#TODO: temp changes.
EXTRA_OECONF_append = " --with-omx-includes=${WORKSPACE}/mm-video-oss/mm-core/inc"

EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8974', ' --enable-target=msm8974', '', d)}"

FILES_${PN} += "\
    /usr/lib/* "

# The mm-still package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"
