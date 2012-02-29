inherit autotools

DESCRIPTION = "GPS Location API"
PR = "r2"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://loc_api/NOTICE;md5=678c1eaab719b8ad3b690fe9755bc525"

SRC_URI = "file://${WORKSPACE}/hardware/qcom/gps"
DEPENDS = "qmi-framework glib-2.0 libhardware"
EXTRA_OECONF = "--with-libhardware-includes=${STAGING_INCDIR} \
                --with-core-includes=${WORKSPACE}/system/core/include \
                --with-glib"

S = "${WORKDIR}/gps"
