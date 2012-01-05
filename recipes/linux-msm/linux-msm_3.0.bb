require recipes/linux-msm/linux-msm.inc

PR = "r9"

SRC_URI = "git://codeaurora.org/kernel/msm;protocol=git"
SRCREV = "79c0d25bac3a0a4e99576bd1e639444e243cab20"

# TODO: Upstream all these patches...
SRC_URI += "file://msm7630-msm7627a-defconfig-Add-devtmpfs-and-devpts.patch;apply=no"
SRC_URI += "file://kgsl_drm_patch.patch;apply=no"
SRC_URI += "file://camera_v4l2_kernel_8x55.patch;apply=no"
SRC_URI += "file://msm-diag-writes-to-dev-diag-now-works.patch;apply=no"
SRC_URI += "file://MDP-v4l2-video-overlay-driver.patch;apply=no"

S = ${WORKDIR}/git

KERNEL_IMAGETYPE = "Image"

do_patch() {
   patch -d ${S} -p1 -i ../msm7630-msm7627a-defconfig-Add-devtmpfs-and-devpts.patch
   patch -d ${S} -p1 -i ../kgsl_drm_patch.patch
   if [ "x${MACHINE}" == "xmsm8655" ]; then
      patch -d ${S} -p1 -i ../camera_v4l2_kernel_8x55.patch
   fi
   patch -d ${S} -p1 -i ../msm-diag-writes-to-dev-diag-now-works.patch
   if [ "x${MACHINE}" == "xmsm8655" ]; then
      patch -d ${S} -p1 -i ../MDP-v4l2-video-overlay-driver.patch
   fi
}

