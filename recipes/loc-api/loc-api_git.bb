inherit autotools

DESCRIPTION = "GPS Location API"
LICENSE = "BSD"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/hardware/qcom/gps"
DEPENDS = "qmi-framework glib-2.0 libhardware"
EXTRA_OECONF = "--with-libhardware-includes=${STAGING_INCDIR} \
                --with-core-includes=${WORKSPACE}/system/core/include \
                --with-glib"

S = "${WORKDIR}/gps"
