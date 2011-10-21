DESCRIPTION = "Andriod Debug Bridge. It provides connectivity over USB for running a shell, transferring files etc."
HOMEPAGE = "http://developer.android.com/guide/developing/tools/adb.html"
LICENSE = "Apache-2.0"

SRC_URI = "file://${WORKSPACE}/system/core"

DEPENDS = "virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}binutils"

inherit autotools

PR = "r1"

S = "${WORKDIR}/core"

do_install() {
	install -m 0755 ${WORKDIR}/core/adb/adbd -D ${D}/sbin/adbd
}
