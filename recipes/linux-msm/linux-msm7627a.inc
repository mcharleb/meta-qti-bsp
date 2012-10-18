do_defconfig_patch () {
	cat >> ${S}/arch/arm/configs/${KERNEL_DEFCONFIG} <<MSM_X11_EXTRACONFIGS
CONFIG_DEVTMPFS=y
CONFIG_DEVTMPFS_MOUNT=y
CONFIG_UNIX98_PTYS=y
MSM_X11_EXTRACONFIGS
}

do_pmem_patch () {
	perl -p -i.bak -e \
		's/PMEM_KERNEL_EBI1_SIZE\s+0x3A000/PMEM_KERNEL_EBI1_SIZE 0x1200000/g' \
		${S}/arch/arm/mach-msm/board-msm7627a.h ${S}/arch/arm/mach-msm/board-msm7x27a.c
}

do_patch_append () {
	bb.build.exec_func('do_defconfig_patch',d)
	bb.build.exec_func('do_pmem_patch',d)
}

