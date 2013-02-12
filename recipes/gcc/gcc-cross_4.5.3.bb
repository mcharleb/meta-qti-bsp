require gcc-${PV}.inc
require recipes-devtools/gcc/gcc-cross4.inc

EXTRA_OECONF += "--disable-libunwind-exceptions \
                 --with-mpfr=${STAGING_DIR_NATIVE}${prefix_native} \
                 --with-system-zlib"

ARCH_FLAGS_FOR_TARGET += "-isystem${STAGING_DIR_TARGET}${target_includedir}"

SRC_URI[md5sum] = "8e0b5c12212e185f3e4383106bfa9cc6"
SRC_URI[sha256sum] = "0a8847af44a9b33813b199997a73139517c96adfd519eaf24c79d4d9d09f65de"
