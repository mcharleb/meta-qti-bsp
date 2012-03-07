require recipes/linux-msm/linux-msm.inc

PR = "r12"

SRC_URI = "git://codeaurora.org/kernel/msm;protocol=git"
SRCREV = "d407b40f5b0fd0e0ad8184582fe6a4f71d4e91fb"

SRC_URI += "file://msm7630-msm7627a-perf_defconfig.patch"

S = ${WORKDIR}/git

KERNEL_IMAGETYPE = "Image"

