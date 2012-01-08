inherit autotools

DESCRIPTION = "GPS Location API"
LICENSE = "BSD"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/hardware/qcom/gps"
DEPENDS = "qmi-framework glib-2.0 libhardware"
EXTRA_OECONF = "--with-qmi-includes=${STAGING_INCDIR}/qmi-framework \
                --with-qmi-libraries=${STAGING_LIBDIR} \
                --with-glib"

S = "${WORKDIR}/gps"
