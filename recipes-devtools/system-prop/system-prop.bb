inherit autotools-brokensep

PR = "r0"

FILESPATH =+ "${WORKSPACE}/android_compat/device/qcom/:"
SRC_URI   = "file://${SOC_FAMILY}"

DESCRIPTION = "Script to populate system properties"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

do_compile() {
    # Remove empty lines and lines starting with '#'
    sed -e 's/#.*$//' -e '/^$/d' ${WORKDIR}/${SOC_FAMILY}/system.prop >> ${S}/build.prop
}

do_install() {
    install -d ${D}${userfsdatadir}
    install ${S}/build.prop ${D}${userfsdatadir}/build.prop
}

PACKAGES = "${PN}"
FILES_${PN} += "${userfsdatadir}/build.prop"
