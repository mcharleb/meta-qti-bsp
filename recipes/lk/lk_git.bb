inherit deploy

DESCRIPTION = "Little Kernel bootloader"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=0835ade698e0bcf8506ecda2f7b4f302"
HOMEPAGE = "https://www.codeaurora.org/gitweb/quic/la?p=kernel/lk.git"
PROVIDES = "virtual/bootloader"
FILESPATH =+ "${WORKSPACE}:"
SRC_URI  = "file://bootable/bootloader/lk/"
S        = "${WORKDIR}/bootable/bootloader/${PN}"
PR       = "r14"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#re-use non-perf settings
BASEMACHINE        = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

MY_TARGET          = "${BASEMACHINE}"

LIBGCC_mdm9640     = "${STAGING_LIBDIR}/${TARGET_SYS}/4.9.2/libgcc.a"

BOOTLOADER_NAME         = "appsboot"
#BOOTLOADER_NAME_msm8960 = "emmc_appsboot"

EXTRA_OEMAKE = "TOOLCHAIN_PREFIX='${TARGET_PREFIX}' ${MY_TARGET}"
EXTRA_OEMAKE_append_mdm9640  = " LIBGCC='${LIBGCC}'"

do_install() {
        install -d ${D}/boot
        install build-${MY_TARGET}/${BOOTLOADER_NAME}.{mbn,raw} ${D}/boot
}

do_install_mdm9640() {
        install -d ${D}/boot
        install build-${MY_TARGET}/${BOOTLOADER_NAME}.mbn ${D}/boot
}


FILES_${PN} = "/boot"
FILES_${PN}-dbg = "/boot/.debug"

do_deploy() {
        install ${S}/build-${MY_TARGET}/${BOOTLOADER_NAME}.{mbn,raw} ${DEPLOYDIR}
}

do_deploy_mdm9640() {
        install ${S}/build-${MY_TARGET}/${BOOTLOADER_NAME}.mbn ${DEPLOYDIR}
}

do_deploy[dirs] = "${S} ${DEPLOYDIR}"
addtask deploy before do_build after do_install

PACKAGE_STRIP = "no"
