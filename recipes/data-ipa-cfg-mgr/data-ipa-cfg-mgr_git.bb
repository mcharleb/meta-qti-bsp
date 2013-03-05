inherit autotools

DESCRIPTION = "Qualcomm IPA"
LICENSE = "BSD License"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"

PR = "r1"

DEPENDS = "glib-2.0 libxml2 libnetfilter-conntrack"

EXTRA_OECONF = "--with-kernel=${STAGING_KERNEL_DIR} \
                --with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"

SRC_URI = "file://${WORKSPACE}/data-ipa-cfg-mgr"

S = "${WORKDIR}/data-ipa-cfg-mgr"

pkg_postinst () {
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f start_ipacm_le remove
        update-rc.d $OPT start_ipacm_le start 90 2 3 4 5 . stop 10 0 1 6 .

}




