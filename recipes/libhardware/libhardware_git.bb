DESCRIPTION = "hardware libhardware headers"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

PR = "r1"

SRC_URI = "file://${WORKSPACE}/hardware/libhardware/"

S = "${WORKDIR}"

do_install () {
        install -d ${D}${includedir}
        install -m 0644 ${S}/include/hardware/gps.h -D ${D}${includedir}/hardware/gps.h
}
