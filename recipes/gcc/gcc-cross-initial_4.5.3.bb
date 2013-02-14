require gcc-cross_${PV}.bb
#EXTRA_OECONF_INITIAL = "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', '--with-ld=${STAGING_BINDIR_TOOLCHAIN}/${TARGET_PREFIX}ld.bfd', '', d)}"
require gcc-cross-initial.inc
