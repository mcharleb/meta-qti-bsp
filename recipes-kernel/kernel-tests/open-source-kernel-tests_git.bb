inherit autotools-brokensep pkgconfig linux-kernel-base

DESCRIPTION = "Open Source kernel tests"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI    = "file://qcom-opensource/kernel/kernel-tests/"
S          = "${WORKDIR}/qcom-opensource/kernel/kernel-tests"

DEPENDS = "virtual/kernel libxml2 glib-2.0"
# This DEPENDS is to serialize kernel module builds
DEPENDS_append_mdm9635 = " qcacld-ll rtsp-alg"
DEPENDS_append_mdm9640 = " qcacld-ll rtsp-alg"
#DEPENDS_append_mdmcalifornium = " qcacld-ll rtsp-alg"

PR = "r6-${MACHINE}"

CFLAGS_pn-${PN} = ""
CPPFLAGS_pn-${PN} = ""
CXXFLAGS_pn-${PN} = ""
LDFLAGS_pn-${PN} = ""
PACKAGE_STRIP = "no"

EXTRA_OEMAKE += "ARCH=${TARGET_ARCH} CROSS_COMPILE=${TARGET_PREFIX}"

EXTRA_OECONF = "--prefix=/kernel-tests \
                --with-kernel=${STAGING_KERNEL_BUILDDIR} \
                --disable-sps \
                --with-glib \
                --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

EXTRA_OECONF_append_apq8053 = "	--with-kflags="ARCH=arm64""

EXTRA_OECONF_append_mdmcalifornium = " --disable-memory_prof"
EXTRA_OECONF_append_mdmcalifornium += " --disable-bam_dmux_loopback"
EXTRA_OECONF_append_mdmcalifornium += " --disable-iommu"
EXTRA_OECONF_append_mdmcalifornium += " --disable-ion"
EXTRA_OECONF_append_mdmcalifornium += " --disable-ocmem"
EXTRA_OECONF_append_mdm9607  = " --disable-ion"
EXTRA_OECONF_append_mdm9607 += " --disable-ocmem"
EXTRA_OECONF_append_mdm9607 += " --disable-ip_accelerator"
EXTRA_OECONF_append_apq8053 = " --disable-glink"
EXTRA_OECONF_append_apq8053 += " --disable-ip_accelerator"
EXTRA_OECONF_append_apq8053 += " --disable-swp"
EXTRA_OECONF_append_apq8053 += " --disable-coresight"

FILES_${PN}-dbg = "/kernel-tests/*/.debug/*"
FILES_${PN} = "/kernel-tests/*"
FILES_${PN}-dev = "${includedir}/*"

do_module_signing() {
   if [ -f ${STAGING_KERNEL_BUILDDIR}/signing_key.priv ]; then
         for KernelTestModules in ${PKGDEST}/${PN}/kernel-tests/modules/lib/modules/${KERNEL_VERSION}/extra/*
  	 do
             ${STAGING_KERNEL_DIR}/scripts/sign-file sha512 ${STAGING_KERNEL_BUILDDIR}/signing_key.priv ${STAGING_KERNEL_BUILDDIR}/signing_key.x509 ${KernelTestModules}
         done
   fi
}

addtask module_signing after do_package before do_package_write_ipk
