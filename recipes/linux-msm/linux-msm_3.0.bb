require recipes/linux-msm/linux-msm.inc

PR = "r18"

S = "${WORKDIR}/kernel"
SRC_URI = "file://${WORKSPACE}/kernel"

SRC_URI += "file://msm7630-msm7627a-perf_defconfig.patch"
SRC_URI += "file://msm7627a-increase-pmem-size.patch"


KERNEL_IMAGETYPE = "Image"

