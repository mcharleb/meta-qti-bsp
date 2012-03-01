# The mkbootimg production process.
require 9615-cdp-bootimg.inc

# The OE-Core minimal boot image files.
IMAGE_INSTALL += task-core-boot 
IMAGE_INSTALL += ${ROOTFS_PKGMANAGE_BOOTSTRAP} 
IMAGE_INSTALL += ${POKY_EXTRA_INSTALL}

# The MSM Linux minimal boot image files.
IMAGE_INSTALL += "alsa-intf"
IMAGE_INSTALL += "base-files"
IMAGE_INSTALL += "base-passwd"
IMAGE_INSTALL += "system-core-adbd"
#IMAGE_INSTALL += "bridge-utils"
IMAGE_INSTALL += "dhcpcd"
IMAGE_INSTALL += "dnsmasq"
DEPENDS += "gdb-cross"
IMAGE_INSTALL += "gdbserver"
IMAGE_INSTALL += "glib-2.0"
# FIXME - This has a conflict with the task-core-boot.  You'll probably want to specify
# bbappend-s to things there or come up with a new task set that involves this recipe's 
# work.
#IMAGE_INSTALL += "initscripts-msm"
IMAGE_INSTALL += "iptables"
IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "loc-api"
IMAGE_INSTALL += "openssl"
IMAGE_INSTALL += "powerapp"
IMAGE_INSTALL += "powerapp-powerconfig"
IMAGE_INSTALL += "powerapp-reboot"
IMAGE_INSTALL += "powerapp-shutdown"
IMAGE_INSTALL += "tcpdump"
IMAGE_INSTALL += "wireless-tools"
