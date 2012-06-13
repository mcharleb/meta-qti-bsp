DEPENDS = 	" \
		virtual/kernel \
		virtual/bootloader \
		pkgconfig-native \
		gtk-doc-native \
		gettext-native \
		yaffs2-utils-native \
		mkbootimg-native \
		"

do_ramdisk_create() {
        CURRENT_DIR=`pwd`
        mkdir -p ${TMPDIR}/ramdisk
        mkdir -p ${TMPDIR}/ramdisk/bin
        mkdir -p ${TMPDIR}/ramdisk/etc
        mkdir -p ${TMPDIR}/ramdisk/etc/init.d
        mkdir -p ${TMPDIR}/ramdisk/sbin
        cd ${TMPDIR}/ramdisk
        cp ${IMAGE_ROOTFS}/bin/busybox bin/
        cp ${IMAGE_ROOTFS}/sbin/adbd sbin/
        cp ${COREBASE}/meta-msm/recipes/busybox/files/fstab etc/
        cp ${COREBASE}/meta-msm/recipes/busybox/files/inittab etc/
        cp ${COREBASE}/meta-msm/recipes/busybox/files/profile etc/
        cp ${COREBASE}/meta-msm/recipes/busybox/files/rcS etc/init.d

        gencpio="${STAGING_KERNEL_DIR}/usr/gen_init_cpio"
        geninit="${COREBASE}/meta-msm/recipes/busybox/files/geninit"
        $gencpio $geninit | gzip -n -9 > ${DEPLOY_DIR_IMAGE}/${PN}-initrd.gz

        cd ${CURRENT_DIR}
}
