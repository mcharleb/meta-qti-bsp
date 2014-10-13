require icu.inc

LIC_FILES_CHKSUM = "file://../license.html;md5=c7c2fb5f3b811a12e9621020793cf999"

PR = "r3"

BASE_SRC_URI = "http://download.icu-project.org/files/icu4c/4.8.1.1/icu4c-4_8_1_1-src.tgz"
SRC_URI = "${BASE_SRC_URI};name=icu \
           https://raw.githubusercontent.com/openembedded/oe-core/master/meta/recipes-support/icu/icu/icu-pkgdata-large-cmd.patch;name=patch \
          "

SRC_URI[patch.md5sum] = "49dbf998c0969554e98a2b8de07016d5"
SRC_URI[patch.sha256sum] = "fff399a6ba4231d3f3b103f228798be4deac7463cf2f6453de1e2a7c967174be"

SRC_URI[icu.md5sum] = "ea93970a0275be6b42f56953cd332c17"
SRC_URI[icu.sha256sum] = "0a70491c5fdfc5a0fa7429f820da73951e07d59a268b3d8ffe052eec65820ca1"
