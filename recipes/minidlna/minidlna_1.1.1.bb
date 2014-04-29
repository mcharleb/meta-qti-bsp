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
PR = "r4"

SRC_URI = "\
    http://sourceforge.net/projects/minidlna/files/minidlna/${PV}/${PN}-${PV}.tar.gz \
    file://0001-certification-fixes.patch \
"

SRC_URI[md5sum] = "653405555ac3f8eb4aacc54c1be7b5fa"
SRC_URI[sha256sum] = "9b70082fd6a12e16cea1558ffff05c07e12ef0c405ee806721e75ce1ce9ad037"

do_install_append () {
    sed -i s:#network_interface=eth0:network_interface=bridge0,ppp0:g minidlna.conf
    sed -i s:"#friendly_name=My DLNA Server":"friendly_name=9x25 MobileAP DLNA":g minidlna.conf
    install -d ${D}${sysconfdir}
    install --mode=0644 -b ${WORKDIR}/${PN}-${PV}/${PN}.conf ${D}${sysconfdir}    
    install -d ${D}${sysconfdir}/init.d/
    install ${WORKDIR}/${PN}-${PV}/linux/${PN}.init.d.script ${D}${sysconfdir}/init.d/minidlna
}
