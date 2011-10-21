require linux-quic.inc

PR = "r0"

SRC_URI = "file://${WORKSPACE}/kernel"

S = ${WORKDIR}/kernel

KERNEL_IMAGETYPE = "Image"
