inherit autotools

DESCRIPTION = "MSM7K"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://librpc/clnt.c;md5=dc4b2e6d754f5ca60e4947e3aef71298"
PR = "r0"

SRC_URI = "file://${WORKSPACE}/hardware/msm7k"

DEPENDS = "glib-2.0"

EXTRA_OECONF = "--with-glib"

S = "${WORKDIR}/msm7k"
