DESCRIPTION = "Lightweight high-performance web server"
HOMEPAGE = "http://www.lighttpd.net/"
BUGTRACKER = "http://redmine.lighttpd.net/projects/lighttpd/issues"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e4dac5c6ab169aa212feb5028853a579"


SECTION = "net"
DEPENDS = "zlib libpcre openssl"
RDEPENDS_${PN} += " \
               lighttpd-module-access \
               lighttpd-module-alias \
               lighttpd-module-compress \
               lighttpd-module-accesslog \
               lighttpd-module-indexfile \
               lighttpd-module-dirlisting \
               lighttpd-module-staticfile \
               lighttpd-module-cgi \
               lighttpd-module-auth \
               lighttpd-module-redirect \
               lighttpd-module-evasive \
"

PR = "r3"

SRC_URI = "http://download.lighttpd.net/lighttpd/releases-1.4.x/lighttpd-${PV}.tar.bz2 \
        file://index.html.lighttpd \
        file://lighttpd.conf \
        file://mdm9625.com.key \
        file://mdm9625.com.pem \
        file://openssl.cnf \
        file://lighttpd.user \
        file://lighttpd \
"

SRC_URI[md5sum] = "63f9df52dcae0ab5689a95c99c54e48a"
SRC_URI[sha256sum] = "0d795597e4666dbf6ffe44b4a42f388ddb44736ddfab0b1ac091e5bb35212c2d"

EXTRA_OECONF = " \
             --without-bzip2 \
             --without-ldap \
             --without-lua \
             --without-memcache \
             --with-pcre \
             --without-webdav-props \
             --without-webdav-locks \
             --with-openssl \
             --with-openssl-libs=/usr/lib \
             --disable-static \
"

inherit autotools pkgconfig update-rc.d gettext

INITSCRIPT_NAME = "lighttpd"
INITSCRIPT_PARAMS = "defaults 70"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d ${D}/www/logs ${D}/www/pages/dav ${D}/www/var
    install -m 0755 ${WORKDIR}/lighttpd ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/lighttpd.conf ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/mdm9625.com.key ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/mdm9625.com.pem ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/openssl.cnf ${D}${sysconfdir}
    install -m 0770 ${WORKDIR}/lighttpd.user ${D}/www/lighttpd.user
    install -m 0644 ${WORKDIR}/index.html.lighttpd ${D}/www/pages/index.html
}

FILES_${PN} += "${sysconfdir} /www"

CONFFILES_${PN} = "${sysconfdir}/lighttpd.conf"

PACKAGES_DYNAMIC = "lighttpd-module-*"

python populate_packages_prepend () {
        lighttpd_libdir = bb.data.expand('${libdir}', d)
        do_split_packages(d, lighttpd_libdir, '^mod_(.*)\.so$', 'lighttpd-module-%s', 'Lighttpd module for %s', extra_depends='')
}
