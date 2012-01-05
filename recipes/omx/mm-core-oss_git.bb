DESCRIPTION = "OpenMAX core for MSM chipsets"
LICENSE = "BSD"

SRC_URI = "file://${WORKSPACE}/mm-core-oss"

PR = "r3"

S = "${WORKDIR}/mm-core-oss"

LV = "1.0.0"

do_compile() {
    oe_runmake LIBVER="${LV}" LDFLAGS_SO="${LDFLAGS}"
}

do_install() {
    oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
