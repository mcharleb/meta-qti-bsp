require recipes/linux-msm/linux-msm.inc

PR = "r19"

S = "${WORKDIR}/kernel"
SRC_URI = "file://${WORKSPACE}/kernel"

SRC_URI += "file://msm7630-msm7627a-perf_defconfig.patch"

KERNEL_IMAGETYPE = "Image"

