inherit autotools-brokensep pkgconfig update-rc.d
PR = "r7"

DESCRIPTION = "Recovery bootloader"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"
HOMEPAGE = "https://www.codeaurora.org/gitweb/quic/la?p=platform/bootable/recovery.git"
DEPENDS = "libmincrypt-native system-core oem-recovery"
RDEPENDS_${PN} = "zlib bzip2"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://bootable/recovery/"

S = "${WORKDIR}/bootable/${PN}/"

EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include \
                --with-core-headers=${STAGING_INCDIR}"

SYSTEMD_SUPPORT = "${@base_contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}"

PARALLEL_MAKE = ""
INITSCRIPT_NAME = "recovery"
INITSCRIPT_PARAMS = "start 27 5 . stop 80 0 1 6 ."

FILES_${PN} += "/cache"
FILES_${PN} += "/system"
FILES_${PN} += "/tmp"
FILES_${PN} += "/res"
FILES_${PN} += "/data"
do_install_append() {
        install -d ${D}/cache/
        install -d ${D}/tmp/
        install -d ${D}/res/
        install -d ${D}/data/
        install -d ${D}/system/
        install -m 0755 ${S}/start_recovery -D ${D}${sysconfdir}/init.d/recovery

        if [ "${SYSTEMD_SUPPORT}" == "systemd" ]; then
              install -m  0755 ${WORKSPACE}/poky/meta-qti-bsp/recipes-bsp/recovery/files/fstab -D ${D}${sysconfdir}/fstab
              install -m 0755 ${WORKSPACE}/poky/meta-qti-bsp/recipes-bsp/recovery/files/fstab -D ${D}/res/recovery_volume_config
        else
              install -m  0755 ${WORKSPACE}/poky/meta-qti-bsp/recipes-bsp/base-files-recovery/fstab -D ${D}${sysconfdir}/fstab
              install -m 0755 ${WORKSPACE}/poky/meta-qti-bsp/recipes-bsp/base-files-recovery/fstab -D ${D}/res/recovery_volume_config
        fi
}
