inherit autotools

DESCRIPTION = "Data Services Open Source"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

PR = "r2"

DEPENDS += "virtual/kernel glib-2.0"

EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include --with-glib"

SRC_URI = "file://${WORKSPACE}/data-oss"

S = "${WORKDIR}/data-oss"
