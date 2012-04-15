require recipes/linux-msm/linux-msm.inc

PR = "r22"

S = "${WORKDIR}/kernel"
SRC_URI = "file://${WORKSPACE}/kernel"

KERNEL_IMAGETYPE = "Image"

do_defconfig_patch () {
	if [ ${MACHINE} = "msm7627a" ]
	then
		cat >> ${S}/arch/arm/configs/${KERNEL_DEFCONFIG} <<MSM_X11_EXTRACONFIGS
CONFIG_DEVTMPFS=y
CONFIG_DEVTMPFS_MOUNT=y
CONFIG_UNIX98_PTYS=y
CONFIG_DRM=y
CONFIG_MSM_KGSL_DRM=y
CONFIG_MSM_V4L2_VIDEO_OVERLAY_DEVICE=y
MSM_X11_EXTRACONFIGS
	elif [ ${MACHINE} = "msm8655" ]
	then
		cat >> ${S}/arch/arm/configs/${KERNEL_DEFCONFIG} <<MSM_X11_EXTRACONFIGS
CONFIG_DEVTMPFS=y
CONFIG_DEVTMPFS_MOUNT=y
CONFIG_UNIX98_PTYS=y
CONFIG_DRM=y
CONFIG_MSM_KGSL_DRM=y
CONFIG_MSM_V4L2_VIDEO_OVERLAY_DEVICE=y
CONFIG_MEDIA_CONTROLLER=y
CONFIG_MSM_CAMERA_V4L2=y
CONFIG_MSM_CAMERA_SENSOR=y
CONFIG_MSM_ACTUATOR=y
CONFIG_VX6953=y
MSM_X11_EXTRACONFIGS
	fi
}

do_pmem_patch () {
	if [ ${MACHINE} = "msm7627a" ]
	then
		perl -p -i.bak -e \
			's/PMEM_KERNEL_EBI1_SIZE\s+0x3A000/PMEM_KERNEL_EBI1_SIZE 0x1200000/g' \
			${S}/arch/arm/mach-msm/board-msm7627a.h ${S}/arch/arm/mach-msm/board-msm7x27a.c
	fi
}

do_8x55_vx6953_patch () {
	if [ ${MACHINE} = "msm8655" ]
	then
		perl -p -i.bak -e 's/\) \x26\x26 !MSM_CAMERA_V4L2/\)/' ${S}/drivers/media/video/msm/Kconfig
	fi
}

do_8x55_csiclock_patch () {
	if [ ${MACHINE} = "msm8655" ]
	then
		perl -p -i.bak -e 's/400000000/384000000/' ${S}/drivers/media/video/msm/csi/msm_csic.c
	fi
}

do_patch_append () {
	bb.build.exec_func('do_defconfig_patch',d)
	bb.build.exec_func('do_pmem_patch',d)
	bb.build.exec_func('do_8x55_vx6953_patch',d)
	bb.build.exec_func('do_8x55_csiclock_patch',d)
}

