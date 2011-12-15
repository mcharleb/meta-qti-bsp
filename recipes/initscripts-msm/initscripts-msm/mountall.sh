#
# mountall.sh   Mount all filesystems.
#
# Version:      @(#)mountall.sh  2.83-2  01-Nov-2001  miquels@cistron.nl
#
. /etc/default/rcS
if test -f /etc/default/mountall; then
    . /etc/default/mountall
fi

#
# Mount local filesystems in /etc/fstab.
#
test "$VERBOSE" != no && echo "Mounting local filesystems..."
mount -a $MOUNTALL
ln -fs /media/card /sdcard

: exit 0
