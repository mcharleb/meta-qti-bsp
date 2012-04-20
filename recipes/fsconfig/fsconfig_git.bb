inherit native
inherit autotools

PR = "r1"

DESCRIPTION = "fs_config tool from Android"
LICENSE = "Apache-2.0"
# The license declaration's in the source for the command...
LIC_FILES_CHKSUM = "file://fs_config.c;beginline=1;endline=15;md5=1082077ee391fd55418f9a4a01f62668"
HOMEPAGE = "http://android.git.kernel.org/?p=platform/system/core.git"

SRC_URI = "file://${WORKSPACE}/bootable/scripts/tools/fs_config"

S = "${WORKDIR}/fs_config"

BBCLASSEXTEND = "native"
