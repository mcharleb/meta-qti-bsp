inherit autotools

DESCRIPTION = "ALSA Framework Library"
LICENSE = "Apache-2.0"
PR = "r1"
LIC_FILES_CHKSUM = "file://libalsa-intf/alsa_audio.h;startline=1;endline=16;md5=f6d8144846848bc23206803317c79e49"
DEPENDS = "acdbloader glib-2.0"

SRC_URI = "file://${WORKSPACE}/qcom-opensource/mm-audio"
prefix="/etc"

S = "${WORKDIR}/mm-audio"

EXTRA_OECONF += "--prefix=/etc \
                 --with-kernel=${STAGING_KERNEL_DIR} \
                 --with-sanitized-headers=${STAGING_KERNEL_DIR}/source/include \
                 --with-glib"

FILES_${PN} += "${prefix}/snd_soc_msm/*"
