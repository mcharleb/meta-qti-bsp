PR = "r4"

DESCRIPTION = "Little Kernel bootloader"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=0835ade698e0bcf8506ecda2f7b4f302"
HOMEPAGE = "https://www.codeaurora.org/gitweb/quic/la?p=kernel/lk.git"
PROVIDES = "virtual/bootloader"

SRC_URI = "file://${WORKSPACE}/bootable/bootloader/lk \
           file://mdm9615-ld.patch"

S = "${WORKDIR}/${PN}"

MY_TARGET = ${@base_conditional('MACHINE', '9615-cdp', 'mdm9615', '${MACHINE}', d)}

BOOTLOADER_NAME = ${@base_conditional('MACHINE', 'msm8960', 'emmc_appsboot', 'appsboot', d)}

EXTRA_OEMAKE = "TOOLCHAIN_PREFIX='${TARGET_PREFIX}' ${MY_TARGET}"
EXTRA_OEMAKE_append_msm8960 = " EMMC_BOOT=1 SIGNED_KERNEL=1"

do_install() {
	install	-d ${D}/boot
	install build-${MY_TARGET}/${BOOTLOADER_NAME}.{mbn,raw} ${D}/boot
}

do_install_append_msm8960() {
	install build-${MY_TARGET}/EMMCBOOT.MBN ${D}/boot
}

FILES_${PN} = "/boot"

do_deploy () {
        install -d ${DEPLOY_DIR_IMAGE}
        install build-${MY_TARGET}/${BOOTLOADER_NAME}.{mbn,raw} ${DEPLOY_DIR_IMAGE}

	# FIXME !!  Not sure what we're doing here- and this is a PSTAGE type function...
        #package_stagefile_shell ${DEPLOY_DIR_IMAGE}/appsboot.mbn
}
do_deploy[dirs] = "${S}"
addtask deploy before do_package_stage after do_compile

PACKAGE_STRIP = "no"
