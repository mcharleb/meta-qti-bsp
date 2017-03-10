# Rootfs creation.
require ../../../recipes-ss-mgr/packagegroup/ss-mgr.inc

DEPENDS_append += "lk"
IMAGE_INSTALL += "packagegroup-core-boot"

IMAGE_INSTALL += "${ROOTFS_PKGMANAGE_BOOTSTRAP}"
IMAGE_INSTALL += "${CORE_IMAGE_EXTRA_INSTALL}"

# utils and scripts needed for SELinux
IMAGE_INSTALL += "packagegroup-core-selinux"
IMAGE_INSTALL += "selinux-config"
IMAGE_INSTALL += "refpolicy-mls"
IMAGE_INSTALL += "start-scripts-misc-daemon"
IMAGE_INSTALL += "selinux-labelall"

# The MSM Linux minimal boot image files.

IMAGE_INSTALL += "alsa-lib"
IMAGE_INSTALL += "alsa-utils"
IMAGE_INSTALL += "tinycompress"

# AUDIO HAL
IMAGE_INSTALL += "audiohal"
IMAGE_INSTALL += "encoders"
IMAGE_INSTALL += "media"
IMAGE_INSTALL += "usbaudio"

IMAGE_INSTALL += "base-files"
IMAGE_INSTALL += "base-passwd"
IMAGE_INSTALL += "bluez5"

IMAGE_INSTALL += "dhcpcd"
IMAGE_INSTALL += "dnsmasq"

IMAGE_INSTALL += "ebtables"
IMAGE_INSTALL += "ethtool"
IMAGE_INSTALL += "native-frameworks"
DEPENDS += "gdb-cross-arm"

IMAGE_INSTALL += "gdb"
IMAGE_INSTALL += "glib-2.0"

IMAGE_INSTALL += "init-audio"
IMAGE_INSTALL += "iproute2"
IMAGE_INSTALL += "iptables"

IMAGE_INSTALL += "libnl"
IMAGE_INSTALL += "libcutils"
IMAGE_INSTALL += "liblog"

IMAGE_INSTALL += "libxml2"

IMAGE_INSTALL += "start-scripts-find-partitions"
IMAGE_INSTALL += "start-scripts-firmware-links"
IMAGE_INSTALL += "system-core-adbd"
IMAGE_INSTALL += "system-core-logd"
IMAGE_INSTALL += "system-core-leprop"
IMAGE_INSTALL += "system-core-usb"
IMAGE_INSTALL += "system-core-debuggerd"
IMAGE_INSTALL += "system-core-post-boot"
IMAGE_INSTALL += "system-conf"
IMAGE_INSTALL += "system-prop"
IMAGE_INSTALL += "tcpdump"
IMAGE_INSTALL += "wireless-tools"

IMAGE_INSTALL += "wcnss"
IMAGE_INSTALL += "wlan-opensource"
IMAGE_INSTALL += "qcacld-utils"
IMAGE_INSTALL += "hostap-daemon-qcacld"
IMAGE_INSTALL += "wpa-supplicant-qcacld"
IMAGE_INSTALL += "bt-app"
IMAGE_INSTALL += "bt-property"
IMAGE_INSTALL += "fluoride"
IMAGE_INSTALL += "libbt-vendor"
IMAGE_INSTALL += "display-hal-linux"

IMAGE_INSTALL += "gstreamer1.0"
IMAGE_INSTALL += "gstreamer1.0-plugins-base"
IMAGE_INSTALL += "gstreamer1.0-plugins-good"
IMAGE_INSTALL += "gstreamer1.0-rtsp-server"
#IMAGE_PREPROCESS_COMMAND += "echo ${MACHINE} >> ${IMAGE_ROOTFS}/target;"


# Below is to generate sparse ext4 system image (OE by default supports raw ext4 images)
SELINUX_FILE_CONTEXTS = "${IMAGE_ROOTFS}/etc/selinux/mls/contexts/files/file_contexts"
do_makesystem() {

    make_ext4fs -s -a / -S ${SELINUX_FILE_CONTEXTS} -l ${SYSTEM_SIZE_EXT4} ${DEPLOY_DIR_IMAGE}/${MACHINE}-sysfs.ext4 ${IMAGE_ROOTFS}
#    make_ext4fs -s  -l ${SYSTEM_SIZE_EXT4} ${DEPLOY_DIR_IMAGE}/${MACHINE}-sysfs.ext4 ${IMAGE_ROOTFS}
}
