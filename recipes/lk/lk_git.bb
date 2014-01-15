inherit deploy

DESCRIPTION = "Little Kernel bootloader"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=0835ade698e0bcf8506ecda2f7b4f302"
HOMEPAGE = "https://www.codeaurora.org/gitweb/quic/la?p=kernel/lk.git"
PROVIDES = "virtual/bootloader"
SRC_URI  = "file://${WORKSPACE}/bootable/bootloader/lk \
            file://mdm9615-ld.patch"
S        = "${WORKDIR}/${PN}"
PR       = "r11"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#re-use non-perf settings
BASEMACHINE        = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

LIBGCC_9615-cdp    = "${STAGING_LIBDIR}/${TARGET_SYS}/4.6.3/libgcc.a"
LIBGCC_mdm9625     = "${STAGING_LIBDIR}/${TARGET_SYS}/4.6.3/libgcc.a"
LIBGCC_mdm9635  = "${STAGING_LIBDIR}/${TARGET_SYS}/4.6.3/libgcc.a"

MY_TARGET          = "${BASEMACHINE}"
MY_TARGET_9615-cdp = "mdm9615"

BOOTLOADER_NAME         = "appsboot"
BOOTLOADER_NAME_msm8960 = "emmc_appsboot"
BOOTLOADER_NAME_msm8974 = "emmc_appsboot"
BOOTLOADER_NAME_msm8610 = "emmc_appsboot"
BOOTLOADER_NAME_msm8226 = "emmc_appsboot"

EXTRA_OEMAKE = "TOOLCHAIN_PREFIX='${TARGET_PREFIX}' ${MY_TARGET}"
EXTRA_OEMAKE_append_9615-cdp = " LIBGCC='${LIBGCC}'"
EXTRA_OEMAKE_append_mdm9625  = " LIBGCC='${LIBGCC}'"
EXTRA_OEMAKE_append_mdm9635  = " LIBGCC='${LIBGCC}'"
EXTRA_OEMAKE_append_msm8960  = " EMMC_BOOT=1 SIGNED_KERNEL=1"
EXTRA_OEMAKE_append_msm8974  = " EMMC_BOOT=1 SIGNED_KERNEL=1"
EXTRA_OEMAKE_append_msm8610  = " EMMC_BOOT=1 SIGNED_KERNEL=1"
EXTRA_OEMAKE_append_msm8226  = " EMMC_BOOT=1 SIGNED_KERNEL=1"

do_install() {
	install	-d ${D}/boot
	install build-${MY_TARGET}/${BOOTLOADER_NAME}.{mbn,raw} ${D}/boot
}

do_install_append_msm8960() {
	install build-${MY_TARGET}/EMMCBOOT.MBN ${D}/boot
}

do_install_append_msm8974() {
	install build-${MY_TARGET}/EMMCBOOT.MBN ${D}/boot
}

do_install_append_msm8610() {
	install build-${MY_TARGET}/EMMCBOOT.MBN ${D}/boot
}

do_install_append_msm8226() {
	install build-${MY_TARGET}/EMMCBOOT.MBN ${D}/boot
}

FILES_${PN} = "/boot"

do_deploy () {
        install ${S}/build-${MY_TARGET}/${BOOTLOADER_NAME}.{mbn,raw} ${DEPLOYDIR}
}
do_deploy[dirs] = "${S} ${DEPLOYDIR}"
addtask deploy before do_build after do_install

PACKAGE_STRIP = "no"
