DESCRIPTION = "util library"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PV = "1.0.0"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/base/libs/utils \
           file://string_fix_patch.txt \
 "

S = "${WORKDIR}/utils"

inherit autotools

DEPENDS += "system-core"

ARM_INSTRUCTION_SET = "arm"

EXTRA_OECONF_append = " --with-additional-include-directives="${WORKSPACE}/base/include""

CPPFLAGS += "-I${STAGING_INCDIR}/c++"
CPPFLAGS += "-I${STAGING_INCDIR}/c++/${TARGET_SYS}"

FILES_${PN} += "/usr/lib/*"

# package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"

do_configure_prepend() {
    # Apply patch to string class
    pushd ${S}/..
    patch -p0 < string_fix_patch.txt
    popd
}