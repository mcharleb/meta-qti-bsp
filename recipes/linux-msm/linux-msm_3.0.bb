require recipes/linux-msm/linux-msm.inc

PR = "r14"

SRC_URI = "git://codeaurora.org/kernel/msm;protocol=git"
SRCREV = "c6c98b112b695a0df202881afc51ee307433d736"

SRC_URI += "file://msm7630-msm7627a-perf_defconfig.patch"
SRC_URI += "file://msm_fb-display-MSM-V4l2-video-overlay-driver.patch"

S = ${WORKDIR}/git

KERNEL_IMAGETYPE = "Image"

