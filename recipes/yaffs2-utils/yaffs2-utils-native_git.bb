inherit native autotools

MY_PN = "mkyaffs2image"

DESCRIPTION = "YAFFS (Yet Another Flash File System) provides a fast robust file system for NAND and NOR Flash. "
HOMEPAGE = "http://www.yaffs.net/"
LICENSE = "Apache-2.0"

SRC_URI = "file://${WORKSPACE}/external/yaffs2"

PR = "r1"

S = "${WORKDIR}/yaffs2"
