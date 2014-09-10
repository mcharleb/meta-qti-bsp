inherit autotools 

DESCRIPTION = "GPS Location API"
PR = "r4"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://hardware/qcom/gps/"
S = "${WORKDIR}/hardware/qcom/gps"
DEPENDS = "qmi-framework glib-2.0 libhardware"
EXTRA_OECONF = "--with-libhardware-includes=${STAGING_INCDIR} \
                --with-core-includes=${WORKSPACE}/system/core/include \
                --with-glib"


CPPFLAGS += "-I${WORKSPACE}/base/include"

do_install_append() {
   mkdir -p ${D}/usr/include/loc-api/libloc_api_50001
   cp -pPr ${S}/loc_api/ulp/inc/ulp.h ${D}/usr/include/loc-api/libloc_api_50001
}
