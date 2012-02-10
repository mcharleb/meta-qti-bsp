PR = "r2"

DESCRIPTION = "Little Kernel bootloader"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5a1abdab641eec675725c843f43f03af"
HOMEPAGE = "https://www.codeaurora.org/gitweb/quic/la?p=kernel/lk.git"
PROVIDES = "virtual/bootloader"

SRC_URI = "file://${WORKSPACE}/bootable/bootloader/lk \
           file://mdm9615-ld.patch"

S = "${WORKDIR}/${PN}"

MY_TARGET = "mdm9615"

EXTRA_OEMAKE = "TOOLCHAIN_PREFIX='${TARGET_PREFIX}' ${MY_TARGET}"

do_install() {
	install	-d ${D}/boot
	install build-${MY_TARGET}/appsboot.{mbn,raw} ${D}/boot
}

FILES_${PN} = "/boot"

do_deploy () {
        install -d ${DEPLOY_DIR_IMAGE}
        install build-${MY_TARGET}/appsboot.{mbn,raw} ${DEPLOY_DIR_IMAGE}

	# FIXME !!  Not sure what we're doing here- and this is a PSTAGE type function...
        #package_stagefile_shell ${DEPLOY_DIR_IMAGE}/appsboot.mbn
}
do_deploy[dirs] = "${S}"
addtask deploy before do_package_stage after do_compile

PACKAGE_STRIP = "no"
