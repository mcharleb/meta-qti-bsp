DESCRIPTION = "GLES headers"
HOMEPAGE = "http://www.khronos.org/"
LICENSE = "SGI Free Software B License Version 2.0"
LIC_FILES_CHKSUM = "file://include/GLES/gl.h;startline=12;endline=15;md5=4353e0b39204009be8445dc056b20d96"

DEPENDS = "libx11"
PR = "r3"

SRC_URI = "file://${WORKSPACE}/base/opengl/include"
S = "${WORKDIR}"

ALLOW_EMPTY_${PN} = "1"

do_install() {
   for i in  EGL KHR GLES GLES2; do
      install -d ${D}/${includedir}/$i
      cp -pPr ${S}/include/$i/* ${D}/${includedir}/$i
   done
}

