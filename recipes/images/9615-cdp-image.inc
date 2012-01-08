# The mkbootimg production process..
require 9615-cdp-bootimg.inc
IMAGE_INSTALL += "base-files"
IMAGE_INSTALL += "base-passwd"

IMAGE_INSTALL += "adbd"
IMAGE_INSTALL += "bridge-utils"
IMAGE_INSTALL += "dnsmasq"
IMAGE_INSTALL += "glib-2.0"
IMAGE_INSTALL += "initscripts-msm"
IMAGE_INSTALL += "libstdc++"
IMAGE_INSTALL += "openssl"
IMAGE_INSTALL += "powerapp"
IMAGE_INSTALL += "powerapp-reboot"
IMAGE_INSTALL += "powerapp-shutdown"
IMAGE_INSTALL += "wireless-tools"
IMAGE_INSTALL += "loc-api"
