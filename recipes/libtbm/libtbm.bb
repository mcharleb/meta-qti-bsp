inherit autotools

DESCRIPTION = "Tizen buffer management library"
HOMEPAGE = "http://support.cdmatech.com"
LICENSE = "QUALCOMM-Proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qcom/files/qcom-licenses/\
QUALCOMM-Proprietary;md5=92b1d0ceea78229551577d4284669bb8"

PR = "r0"

DEPENDS = "libdrm"

SRC_URI = "file://${WORKSPACE}/libtbm"

S = "${WORKDIR}/libtbm"
