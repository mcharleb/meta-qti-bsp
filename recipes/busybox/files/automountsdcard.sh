#! /bin/sh

destdir=/media/card

umount_partition()
{
        if grep -qs "^/dev/$1 " /proc/mounts ; then
                umount "${destdir}";
        fi
}

mount_partition()
{
        if ! mount -t auto "/dev/$1" "${destdir}"; then
                # failed to mount
                exit 1
        fi
}

case "${ACTION}" in
add|"")
        umount_partition ${MDEV}
        mount_partition ${MDEV}
        ;;
remove)
        umount_partition ${MDEV}
        ;;
esac

