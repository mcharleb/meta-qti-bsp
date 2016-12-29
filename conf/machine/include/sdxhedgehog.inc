BASEMACHINE ?= "sdxhedgehog"
require conf/machine/include/basemachine.inc

SOC_FAMILY ?= "mdm9x50"
include conf/machine/include/soc-family.inc

# Uncomment the following line to enable the hard floating point abi. Note that
# this breaks some binary libraries and 3D (neither of which ship with
# meta-yocto). For maximum compatibility, leave this disabled.
#DEFAULTTUNE ?= "cortexa8hf-neon"

include conf/machine/include/tune-cortexa8.inc

BBMASK = ".*/*/recipes-kernel/linux-msm-4.4"

MACHINE_DISPLAY_WIDTH_PIXELS ?= "480"
MACHINE_DISPLAY_HEIGHT_PIXELS ?= "640"

SERIAL_CONSOLE ?= "115200 ttyHSL0"

DISTRO ?= "mdm"

MACHINE_FEATURES += "kernel26 keyboard"

PAGE_SIZE ?= '2048'

# Max supported inodes
EXTRA_IMAGECMD ?= "-N 2048"

#UBINIZE Args
UBINIZE_ARGS ?= "-m 2048 -p 128KiB -s 2048"
#MKUBIFS Args
MKUBIFS_ARGS ?= "-m 2048 -e 126976 -c 1073 -F"
#Volume sizes
SYSTEM_VOLUME_SIZE ?= "90MiB"
CACHE_VOLUME_SIZE ?= "5MiB"

# Sizes for EXT4 (in bytes)
SYSTEM_SIZE_EXT4 ?= "90000000"
USERDATA_SIZE_EXT4 ?= "2000000000"
CACHE_SIZE_EXT4 ?= "80000000"
