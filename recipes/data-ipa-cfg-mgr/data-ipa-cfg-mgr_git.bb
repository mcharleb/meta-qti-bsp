inherit autotools

DESCRIPTION = "Qualcomm IPA"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

PR = "r3"

DEPENDS  = "glib-2.0"
DEPENDS += "libxml2"
DEPENDS += "libnetfilter-conntrack"
DEPENDS += "virtual/kernel"

EXTRA_OECONF = "--with-kernel=${STAGING_KERNEL_DIR} \
                --with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include --with-glib"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://data-ipa-cfg-mgr"

S = "${WORKDIR}/data-ipa-cfg-mgr"

pkg_postinst_${PN} () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f start_ipacm_le remove
        update-rc.d $OPT start_ipacm_le start 38 S . stop 62 0 1 6 .

}




