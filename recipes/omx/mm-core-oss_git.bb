DESCRIPTION = "OpenMAX core for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://omxcore/src/default/qc_registry_table.c;startline=1;endline=27;md5=a1a011a0f0b9d7f1d7c95a5fdd6e7a7e"

SRC_URI = "file://${WORKSPACE}/mm-core-oss"


PR = "r4"

S = "${WORKDIR}/mm-core-oss"

LV = "1.0.0"

do_compile() {
    oe_runmake LIBVER="${LV}" LDFLAGS_SO="${LDFLAGS}"
}

do_install() {
    oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
