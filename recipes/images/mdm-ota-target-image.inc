# This include forces the generation of a /usr userdata partition
# yaffs2 image file and propagates the /usr part of the filesystem
# over to it.

OTA_TARGET_BASENAME = "${MACHINE}-ota-target-image"
OTA_TARGET_IMAGE_ROOTFS = "${IMAGE_ROOTFS}/../${OTA_TARGET_BASENAME}"
OUTPUT_FILE = "${DEPLOY_DIR_IMAGE}/${OTA_TARGET_BASENAME}.zip"
#OUTPUT_FILE = "${OTA_TARGET_BASENAME}.zip"

do_rootfs_append(){
rm -rf ${OTA_TARGET_IMAGE_ROOTFS}
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}

#Create directory structure for targetfiles.zip
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}/BOOTABLE_IMAGES
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}/DATA
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}/META
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}/OTA
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}/SYSTEM

# copy the boot\recovery images
cp ${DEPLOY_DIR_IMAGE}/${MACHINE}-boot.img ${OTA_TARGET_IMAGE_ROOTFS}/BOOTABLE_IMAGES/boot.img

cp ${DEPLOY_DIR_IMAGE}/${MACHINE}-boot.img ${OTA_TARGET_IMAGE_ROOTFS}/BOOTABLE_IMAGES/recovery.img

# copy the contents of system rootfs
cp -r ${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/mdm-image-1.0-r0/rootfs/. ${OTA_TARGET_IMAGE_ROOTFS}/SYSTEM/.

#copy contents of recovery rootfs
cp -r ${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/mdm-recovery-image-1.0-r0/rootfs/. ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/.

#generate recovery.fstab which is used by the updater-script
#echo #mount point fstype device [device2] >> ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/recovery.fstab
echo /boot     mtd     boot >> ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/recovery.fstab
echo /cache    yaffs2  cache >> ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/recovery.fstab
echo /data     yaffs2  userdata >> ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/recovery.fstab
echo /recovery mtd     recovery >> ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/recovery.fstab
echo /system   yaffs2  system >> ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/recovery.fstab

#Copy contents of userdata rootfs
cp -r ${TMPDIR}/rootfs/${MACHINE}-usrfs/. ${OTA_TARGET_IMAGE_ROOTFS}/DATA/.
#Remove fota dir and ipth_dme binary from userdata. We dont want to update them
rm -rf ${OTA_TARGET_IMAGE_ROOTFS}/DATA/fota
rm ${OTA_TARGET_IMAGE_ROOTFS}/DATA/bin/ipth_dme

#Getting content for OTA folder
mkdir -p ${OTA_TARGET_IMAGE_ROOTFS}/OTA/bin
cp   ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/usr/bin/applypatch ${OTA_TARGET_IMAGE_ROOTFS}/OTA/bin/.

cp   ${OTA_TARGET_IMAGE_ROOTFS}/RECOVERY/usr/bin/updater ${OTA_TARGET_IMAGE_ROOTFS}/OTA/bin/.

cp ${WORKSPACE}/Innopath/FOTA/ipth_dua/ipth_dua ${OTA_TARGET_IMAGE_ROOTFS}/OTA/bin/.

# copy contents of META folder
#recovery_api_version is from recovery module
echo recovery_api_version=3 >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#blocksize = BOARD_FLASH_BLOCK_SIZE
echo blocksize=131072 >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#boot_size: Size of boot partition from partition.xml
echo boot_size=0x00A00000 >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#recovery_size : Size of recovery partition from partition.xml
echo recovery_size=0x00A00000 >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#system_size : Size of system partition from partition.xml
echo system_size=0x00A00000 >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#userdate_size : Size of data partition from partition.xml
echo userdata_size=0x00A00000 >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#mkyaffs2_extra_flags : -c $(BOARD_KERNEL_PAGESIZE) -s $(BOARD_KERNEL_SPARESIZE)
echo mkyaffs2_extra_flags=-c 4096 -s 16 >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#extfs_sparse_flag : definition in build
echo extfs_sparse_flags=-s >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#default_system_dev_certificate : Dummy location
echo default_system_dev_certificate=build/abcd >> ${OTA_TARGET_IMAGE_ROOTFS}/META/misc_info.txt

#delete old files
rm -rf ${DEPLOY_DIR_IMAGE}/${OTA_TARGET_BASENAME}
# Pack the files into the deploy dir
cp -r ${OTA_TARGET_IMAGE_ROOTFS} ${DEPLOY_DIR_IMAGE}
}
