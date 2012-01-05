DESCRIPTION = "GLES headers"
HOMEPAGE = "http://www.khronos.org/"
LICENSE = "SGI Free Software B License Version 2.0"

DEPENDS = "libx11"
PR = "r1"

SRC_URI = "file://${WORKSPACE}/base/opengl/include"
S = "${WORKDIR}"

do_install() {
   for i in  EGL KHR GLES GLES2; do
      install -d ${D}/${includedir}/$i
      cp -pPr ${S}/include/$i/* ${D}/${includedir}/$i
   done
}
