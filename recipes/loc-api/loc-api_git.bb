inherit autotools

DESCRIPTION = "GPS Location API"
PR = "r3"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

SRC_URI = "file://${WORKSPACE}/hardware/qcom/gps"
DEPENDS = "qmi-framework glib-2.0 libhardware"
EXTRA_OECONF = "--with-libhardware-includes=${STAGING_INCDIR} \
                --with-core-includes=${WORKSPACE}/system/core/include \
                --with-glib"

S = "${WORKDIR}/gps"

CPPFLAGS += "-I${WORKSPACE}/base/include"

do_install_append() {
   mkdir -p ${D}/usr/include/loc-api/libloc_api_50001
   cp -pPr ${S}/loc_api/ulp/inc/ulp.h ${D}/usr/include/loc-api/libloc_api_50001
}
