inherit deploy externalsrc

DESCRIPTION = "Little Kernel bootloader"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=0835ade698e0bcf8506ecda2f7b4f302"
HOMEPAGE = "https://www.codeaurora.org/gitweb/quic/la?p=kernel/lk.git"

PROVIDES = "virtual/bootloader"

FILESPATH =+ "${WORKSPACE}:"

PV       = "1.0"
PR       = "r1"
EXTERNALSRC="${WORKSPACE}/bootable/bootloader/lk"
EXTERNALSRC_BUILD="${WORKSPACE}/bootable/bootloader/lk"

PACKAGE_ARCH = "${MACHINE_ARCH}"

BASEMACHINE        = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

MY_TARGET          = "${BASEMACHINE}"
MY_TARGET_mdmcalifornium = "mdm9640"
MY_TARGET_apq8009  = "msm8909"
MY_TARGET_apq8096  = "msm8996"
MY_TARGET_mdm9607  = "mdm9607"
MY_TARGET_mdm9607-perf  = "mdm9607"
MY_TARGET_apq8053 = "msm8953"

BOOTLOADER_NAME_mdmcalifornium = "appsboot"
BOOTLOADER_NAME_apq8096 = "emmc_appsboot"
BOOTLOADER_NAME_apq8053 = "emmc_appsboot"

LIBGCC             = "${STAGING_LIBDIR}/${TARGET_SYS}/4.9.3/libgcc.a"

EXTRA_OEMAKE = "${MY_TARGET} TOOLCHAIN_PREFIX='${TARGET_PREFIX}'  LIBGCC='${LIBGCC}'"
EXTRA_OEMAKE_append_apq8053 = " VERIFIED_BOOT=0 DEFAULT_UNLOCK=true EMMC_BOOT=1"
EXTRA_OEMAKE_append_apq8096 = " VERIFIED_BOOT=0 DEFAULT_UNLOCK=true EMMC_BOOT=1"

do_install() {
        install -d ${D}/boot
        install build-${MY_TARGET}/*.mbn ${D}/boot
}


FILES_${PN} = "/boot"
FILES_${PN}-dbg = "/boot/.debug"

do_deploy() {
        install ${S}/build-${MY_TARGET}/*.mbn ${DEPLOYDIR}
}

do_deploy[dirs] = "${S} ${DEPLOYDIR}"
addtask deploy before do_build after do_install

PACKAGE_STRIP = "no"
