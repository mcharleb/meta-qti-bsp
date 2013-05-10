inherit autotools
PR = "r2"

DESCRIPTION = "Recovery bootloader"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"
HOMEPAGE = "https://www.codeaurora.org/gitweb/quic/la?p=platform/bootable/recovery.git"
DEPENDS = "libmincrypt-native system-core"
RDEPENDS = "zlib bzip2"

SRC_URI = "file://${WORKSPACE}/bootable/recovery"

S = "${WORKDIR}/${PN}"

EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include \
                --with-core-headers=${STAGING_INCDIR_NATIVE}"

INITSCRIPT_NAME = "recovery"
INITSCRIPT_PARAMS = "start 27 5 . stop 80 0 1 6 ."
inherit update-rc.d

FILES_${PN} += "/cache"
FILES_${PN} += "/system"
FILES_${PN} += "/tmp"
FILES_${PN} += "/res"
FILES_${PN} += "/data"
do_install_append() {
        install -m  0755 ${WORKSPACE}/oe-core/meta-msm/recipes/base-files-recovery/fstab -D ${D}${sysconfdir}/fstab
        install -d ${D}/cache/
        install -d ${D}/tmp/
        install -d ${D}/res/
        install -d ${D}/data/
        install -d ${D}/system/
        install -m 0755 ${WORKSPACE}/oe-core/meta-msm/recipes/base-files-recovery/fstab -D ${D}/res/recovery_volume_config
        echo /dev/mtdblock16       /system     yaffs2     defaults    0   0 >> ${D}/res/recovery_volume_config
        echo /dev/mtdblock17       /data       yaffs2     defaults    0   0 >> ${D}/res/recovery_volume_config
        echo /dev/mtdblock12       /misc       mtd        defaults    0   0 >> ${D}/res/recovery_volume_config
        install -m 0755 ${WORKDIR}/${PN}/start_recovery -D ${D}${sysconfdir}/init.d/recovery
}

oe_runmake() {
        if [ x"$MAKE" = x ]; then MAKE=make; fi
        bbnote make -j 1 "$@"
        make -j 1 "$@" || die "oe_runmake failed"
}

