do_ramdisk_create() {
        CURRENT_DIR=`pwd`
        rm -rf ${TMPDIR}/ramdisk
        mkdir -p ${TMPDIR}/ramdisk
        mkdir -p ${TMPDIR}/ramdisk/bin
        mkdir -p ${TMPDIR}/ramdisk/etc
        mkdir -p ${TMPDIR}/ramdisk/etc/init.d
        mkdir -p ${TMPDIR}/ramdisk/lib
        mkdir -p ${TMPDIR}/ramdisk/usr
        mkdir -p ${TMPDIR}/ramdisk/usr/bin
        mkdir -p ${TMPDIR}/ramdisk/usr/sbin
        mkdir -p ${TMPDIR}/ramdisk/dev
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/console c 5 1
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/tty c 5 0
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/tty0 c 4 0
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/tty1 c 4 1
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/tty2 c 4 2
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/tty3 c 4 3
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/tty4 c 4 4
        mknod -m 0600 ${TMPDIR}/ramdisk/dev/zero c 1 5
        mkdir -p ${TMPDIR}/ramdisk/dev/pts
        mkdir -p ${TMPDIR}/ramdisk/root
        mkdir -p ${TMPDIR}/ramdisk/proc
        mkdir -p ${TMPDIR}/ramdisk/sys
        cd ${TMPDIR}/ramdisk
        ln -s bin sbin
        cp ${IMAGE_ROOTFS}/bin/busybox bin/
        cp ${IMAGE_ROOTFS}/sbin/adbd sbin/
        cp ${IMAGE_ROOTFS}/lib/libc.so.6 lib/libc.so.6
        cp ${IMAGE_ROOTFS}/lib/libm.so.6 lib/libm.so.6
        cp ${IMAGE_ROOTFS}/lib/librt.so.1 lib/librt.so.1
        cp ${IMAGE_ROOTFS}/lib/libpthread.so.0 lib/libpthread.so.0
        cp ${IMAGE_ROOTFS}/lib/ld-linux.so.3 lib/ld-linux.so.3
        cp ${COREBASE}/meta-msm/recipes/busybox/files/fstab etc/
        cp ${COREBASE}/meta-msm/recipes/busybox/files/inittab etc/
        cp ${COREBASE}/meta-msm/recipes/busybox/files/profile etc/
        cp ${COREBASE}/meta-msm/recipes/busybox/files/rcS etc/init.d

        ln -s bin/busybox init
        ln -s busybox bin/sh
        ln -s busybox bin/echo
        ln -s busybox bin/mount
        ln -s busybox bin/umount

        #gen_initramfs_list.sh expects to be run from kernel directory
        cd ${STAGING_KERNEL_DIR}

        bash ${WORKSPACE}/kernel/scripts/gen_initramfs_list.sh -o ${DEPLOY_DIR_IMAGE}/${PN}-initrd.gz -u 0 -g 0 ${TMPDIR}/ramdisk

        cd ${CURRENT_DIR}
}
