inherit autotools-brokensep

PR = "r0"

FILESPATH =+ "${WORKSPACE}/android_compat/device/qcom/:"
SRC_URI   = "file://${MACHINE_SOC_NAME}"

DESCRIPTION = "Script to populate system properties"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

do_compile() {
    # Remove empty lines and lines starting with '#'
    sed -e 's/#.*$//' -e '/^$/d' ${WORKDIR}/${MACHINE_SOC_NAME}/system.prop >> ${S}/build.prop
}

do_install() {
    install -d ${D}
    install ${S}/build.prop ${D}/build.prop
}

PACKAGES = "${PN}"
FILES_${PN} += "/build.prop"
