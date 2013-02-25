inherit autotools gettext
SUMMARY = "MiniDLNA (aka ReadyDLNA) is server software with the aim of being fully \
compliant with DLNA/UPnP-AV clients."
HOMEPAGE = "http://sourceforge.net/projects/minidlna/"
BUGTRACKER = "http://sourceforge.net/tracker/?group_id=243163&atid=1121516&source=navbar"
LICENSE = "GPLv2"
PRIORITY = "optional"
DEPENDS = "miniupnpd libvorbis sqlite-autoconf libexif libjpeg-turbo libid3tag ffmpeg flac zlib"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

# Package Revision (update whenever recipe is changed)
PR = "r0"

SRC_URI = "\
    https://www.codeaurora.org/mirrored_source/quic/le/${PN}_${PV}_src.tar.gz \
    file://0001-patch.patch \
"

SRC_URI[md5sum] = "d966256baf2f9b068b9de871ab5dade5"
SRC_URI[sha256sum] = "170560fbe042c2bbcba78c5f15b54f4fac321ff770490b23b55789be463f2851"

do_compile () {
    cd ${S} && make INCDIR=${STAGING_INCDIR} USRLIBDIR=${STAGING_LIBDIR} LIBDIR=${STAGING_DIR_HOST}${base_libdir}
}

do_install () {
    cd ${S} && make DESTDIR=${D} USRLIBDIR=${STAGING_LIBDIR} LIBDIR=${STAGING_DIR_HOST}${base_libdir} install
    install -d ${D}${sysconfdir}
    install --mode=0644 -b ${WORKDIR}/${PN}-${PV}/${PN}.conf ${D}${sysconfdir}
}

do_configure_append() {
    # Use the STAGING area instead of the host machine
    sed -i s:/usr/include:${STAGING_INCDIR}:g genconfig.sh
}
