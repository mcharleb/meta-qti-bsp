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
PR       = "r15"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#LK requires BASEMACHINE to be mdm9640  to support one source multiple targets.
BASEMACHINE        = "mdm9640"

MY_TARGET          = "${BASEMACHINE}"
MY_TARGET_apq8009  = "msm8909"
MY_TARGET_mdmfermium  = "mdmfermium"
MY_TARGET_mdmfermium-perf  = "mdmfermium"

LIBGCC             = "${STAGING_LIBDIR}/${TARGET_SYS}/4.9.2/libgcc.a"

EXTRA_OEMAKE = "${MY_TARGET} TOOLCHAIN_PREFIX='${TARGET_PREFIX}'  LIBGCC='${LIBGCC}'"

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
