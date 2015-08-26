inherit autotools-brokensep pkgconfig

DESCRIPTION = "Open Source kernel tests"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI    = "file://qcom-opensource/kernel/kernel-tests/"
S          = "${WORKDIR}/qcom-opensource/kernel/kernel-tests"

DEPENDS = "virtual/kernel libxml2 glib-2.0"
# This DEPENDS is to serialize kernel module builds
DEPENDS_append_mdm9635 = " qcacld-ll rtsp-alg"
DEPENDS_append_mdm9640 = " qcacld-ll rtsp-alg"
#DEPENDS_append_mdmcalifornium = " qcacld-ll rtsp-alg"

PR = "r6"

CFLAGS_pn-${PN} = ""
CPPFLAGS_pn-${PN} = ""
CXXFLAGS_pn-${PN} = ""
LDFLAGS_pn-${PN} = ""
PACKAGE_STRIP = "no"

EXTRA_OEMAKE += "ARCH=${TARGET_ARCH} CROSS_COMPILE=${TARGET_PREFIX}"

EXTRA_OECONF = "--prefix=/usr/kernel-tests \
                --with-kernel=${STAGING_KERNEL_BUILDDIR} \
                --disable-sps \
                --with-glib \
                --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

EXTRA_OECONF_append_msm7627a = " --disable-ion"
EXTRA_OECONF_append_msm7627a = " --disable-ocmem"
EXTRA_OECONF_append_msm7627a = " --enable-v4l2apps"
EXTRA_OECONF_append_mdmcalifornium = " --disable-memory_prof"
EXTRA_OECONF_append_mdmcalifornium += " --disable-bam_dmux_loopback"
EXTRA_OECONF_append_mdmcalifornium += " --disable-iommu"
EXTRA_OECONF_append_mdmcalifornium += " --disable-ion"
EXTRA_OECONF_append_mdmcalifornium += " --disable-ip_accelerator"
EXTRA_OECONF_append_mdmcalifornium += " --disable-ocmem"

FILES_${PN}-dbg = "${prefix}/kernel-tests/*/.debug/* ${prefix}/src/debug/*"
FILES_${PN}-dbg += "${libdir}/*.so ${libdir}/.debug/*"
FILES_${PN} = "${prefix}/kernel-tests/* ${prefix}/src/*"
FILES_${PN} += "${datadir}/pixmaps/* ${libdir}/*"
