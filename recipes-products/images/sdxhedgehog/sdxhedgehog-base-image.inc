# Rootfs creation.
require ../../../recipes-ss-mgr/packagegroup/ss-mgr.inc
DEPENDS_append_aarch64 += "lib32-lk"
DEPENDS_append_arm += "lk"

IMAGE_INSTALL += "packagegroup-core-boot"
IMAGE_INSTALL += "${ROOTFS_PKGMANAGE_BOOTSTRAP}"
IMAGE_INSTALL += "${CORE_IMAGE_EXTRA_INSTALL}"

# The MSM Linux minimal boot image files.
IMAGE_INSTALL += "alsa-intf"

IMAGE_INSTALL += "avahi-daemon"
IMAGE_INSTALL += "avahi-dnsconfd"
IMAGE_INSTALL += "avahi-autoipd"
IMAGE_INSTALL += "avahi-utils"
IMAGE_INSTALL += "alx"
IMAGE_INSTALL += "base-files"
IMAGE_INSTALL += "base-passwd"

IMAGE_INSTALL += "conntrack-tools"
IMAGE_INSTALL += "data-ipa-cfg-mgr"
IMAGE_INSTALL += "data-oss"
IMAGE_INSTALL += "dhcpcd"
IMAGE_INSTALL += "dnsmasq"
IMAGE_INSTALL += "ddclient"
IMAGE_INSTALL += "ebtables"
IMAGE_INSTALL += "ethtool"

IMAGE_INSTALL += "gdbserver"
IMAGE_INSTALL += "gdb"
IMAGE_INSTALL += "glib-2.0"
IMAGE_INSTALL += "hostap-daemon-qcacld"

IMAGE_INSTALL += "iproute2"
IMAGE_INSTALL += "iptables"
IMAGE_INSTALL += "iw"
IMAGE_INSTALL += "jquery"
IMAGE_INSTALL += "jquery-ui"
IMAGE_INSTALL += "kernel-modules"
IMAGE_INSTALL += "libnl"
IMAGE_INSTALL += "libavahi-common"
IMAGE_INSTALL += "libavahi-core"
IMAGE_INSTALL += "libavahi-client"
IMAGE_INSTALL += "libavahi-glib"
IMAGE_INSTALL += "libcutils"
IMAGE_INSTALL += "liblog"
IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "libxml2"
IMAGE_INSTALL += "lighttpd"
IMAGE_INSTALL += "loc-stub"
IMAGE_INSTALL += "loc-pla"
IMAGE_INSTALL += "loc-hal"
IMAGE_INSTALL += "minidlna"
IMAGE_INSTALL += "miniupnpd"
IMAGE_INSTALL += "mtd-utils-ubifs"
IMAGE_INSTALL += "openssl"
IMAGE_INSTALL += "pimd"
IMAGE_INSTALL += "powerapp"
IMAGE_INSTALL += "powerapp-powerconfig"
IMAGE_INSTALL += "powerapp-reboot"
IMAGE_INSTALL += "powerapp-shutdown"
IMAGE_INSTALL += "ppp"
IMAGE_INSTALL += "perl"
IMAGE_INSTALL += "qcacld-ll"
IMAGE_INSTALL += "qcacld-hl"

IMAGE_INSTALL += "rtsp-alg"
IMAGE_INSTALL += "sfe"
IMAGE_INSTALL += "sigma-dut"
IMAGE_INSTALL += "start-scripts-find-partitions"
IMAGE_INSTALL += "start-scripts-firmware-links"
IMAGE_INSTALL += "system-core-adbd"
IMAGE_INSTALL += "system-core-logd"
IMAGE_INSTALL += "system-core-usb"
IMAGE_INSTALL += "system-conf"
IMAGE_INSTALL += "tcpdump"
IMAGE_INSTALL += "wireless-tools"
IMAGE_INSTALL += "wpa-supplicant-qcacld"
IMAGE_INSTALL += "tinyproxy"

# BT packages
IMAGE_INSTALL += "bt-property"
IMAGE_INSTALL += "bt-app"
IMAGE_INSTALL += "fluoride"
IMAGE_INSTALL += "libbt-vendor"

IMAGE_PREPROCESS_COMMAND += "echo ${MACHINE} >> ${IMAGE_ROOTFS}/target;"

# Below is to generate sparse ext4 system image (OE by default supports raw ext4 images)

do_makesystem() {
    make_ext4fs -s -l ${SYSTEM_SIZE_EXT4} ${DEPLOY_DIR_IMAGE}/${MACHINE}-${PRODUCT}-sysfs.ext4 ${IMAGE_ROOTFS}
}
