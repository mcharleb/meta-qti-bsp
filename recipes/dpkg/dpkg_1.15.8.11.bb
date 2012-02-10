require dpkg.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI += "file://noman.patch \
            file://check_snprintf.patch \
            file://check_version.patch \
            file://perllibdir.patch \
            file://preinst.patch"

SRC_URI[md5sum] = "58a1a3ab86ab3220e469cb75f6fb6d7c"
SRC_URI[sha256sum] = "194e5395fa6642e1810c6629a9e7b78d5e6f1e0d13636e72eec91aa5a988a1fc"

PR = "${INC_PR}.3"

