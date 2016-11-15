BASEMACHINE ?= "apq8009"
require conf/machine/include/basemachine.inc

# Uncomment the following line to enable the hard floating point abi. Note that
# this breaks some binary libraries and 3D (neither of which ship with
# meta-yocto). For maximum compatibility, leave this disabled.
#DEFAULTTUNE ?= "cortexa8hf-neon"

require conf/machine/include/apq8009.inc

MACHINE_DISPLAY_WIDTH_PIXELS ?= "320"
MACHINE_DISPLAY_HEIGHT_PIXELS ?= "320"

MACHINE_SOC_NAME ?= "msm8909w"

EXTRA_IMAGECMD ?= "-N 2048"

# Sizes for EXT4 (in bytes)
SYSTEM_SIZE_EXT4 ?= "536870912"
USERDATA_SIZE_EXT4 ?= "200000000"
CACHE_SIZE_EXT4 ?= "65536000"
