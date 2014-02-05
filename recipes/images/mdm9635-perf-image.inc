#Include the non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"
require ${BASEMACHINE}-image.inc

# Set MACHINE-specific configurations
