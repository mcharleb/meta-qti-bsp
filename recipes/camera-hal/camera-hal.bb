DESCRIPTION = "HAL libraries for camera"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"
PV = "1.0.0"
PR = "r8"

SRC_URI = "file://${WORKSPACE}/camera-hal \
           file://camera_parameters_header_include_patch.txt \
           file://dlog-replace-utils-patch.txt \
 "

S = "${WORKDIR}/camera-hal"

inherit autotools

# Need the kernel headers
DEPENDS += "virtual/kernel"
DEPENDS += "mm-camera"
DEPENDS += "mm-still"
DEPENDS += "utils-lib"
DEPENDS += "mm-image-codec"
DEPENDS += "dlog"
DEPENDS += "lua"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

ARM_INSTRUCTION_SET = "arm"

CFLAGS += "-I${STAGING_INCDIR}"
CFLAGS += "-I${STAGING_INCDIR}/jpeg/inc"
CFLAGS += "-I${STAGING_INCDIR}/cameracommon"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include"
CFLAGS += "-I${STAGING_KERNEL_DIR}/usr/include/media"

EXTRA_OECONF_append = " --enable-debug=no --with-dlog"

EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm7627a', ' --enable-target=msm7627a', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8960', ' --enable-target=msm8960', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8974', ' --enable-target=msm8974', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8610', ' --enable-target=msm8610', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('BASEMACHINE', 'msm8226', ' --enable-target=msm8226', '', d)}"
EXTRA_OECONF_append = " --with-additional-include-directives="${WORKSPACE}/mm-video-oss/mm-core/inc/ -I${WORKSPACE}/hardware/libhardware/include -I${WORKSPACE}/base/include -I${WORKSPACE}/system/core/include -I${WORKSPACE}/mm-video-oss/libstagefrighthw""

EXTRA_OECONF_append = " --with-sanitized-headers=${STAGING_KERNEL_DIR}/usr/include"
EXTRA_OECONF_append_msm8960 = " --with-additional-include-directives="-I${WORKSPACE}/mm-video-oss/mm-core/inc/ -I${WORKSPACE}/mm-still/omx/inc/""
EXTRA_OECONF_append_msm8974 = " --with-additional-include-directives="${WORKSPACE}/mm-video-oss/mm-core/inc/ ""
EXTRA_OECONF_append_msm8610 = " --with-additional-include-directives="${WORKSPACE}/mm-video-oss/mm-core/inc/ -I${WORKSPACE}/hardware/libhardware/include -I${WORKSPACE}/base/include -I${WORKSPACE}/system/core/include -I${WORKSPACE}/mm-video-oss/libstagefrighthw""

#TODO: append msm name.
CPPFLAGS += "-I${STAGING_INCDIR}/c++"
CPPFLAGS += "-I${STAGING_INCDIR}/c++/${TARGET_SYS}"

FILES_${PN}_append_msm8960 += "/usr/lib/hw/*"
FILES_${PN}_append_msm8974 += "/usr/lib/*"
FILES_${PN}_append_msm8610 += "/usr/lib/*"
FILES_${PN}_append_msm8226 += "/usr/lib/*"

# The camera-hal package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"


do_configure_prepend() {

    mkdir -p ${STAGING_INCDIR}/camera/

    wget --no-check-certificate -O ${S}/QCamera2/HAL/CameraParameters.h https://www.codeaurora.org/cgit/quic/la/platform/frameworks/av/plain/include/camera/CameraParameters.h?h=jb_3.2_rb5.39

    wget --no-check-certificate -O ${S}/QCamera2/HAL/CameraParameters.cpp https://www.codeaurora.org/cgit/quic/la/platform/frameworks/av/plain/camera/CameraParameters.cpp?h=jb_3.2_rb5.39

    wget --no-check-certificate -O ${S}/QCamera2/HAL/Mutex.h https://www.codeaurora.org/cgit/quic/la/platform/frameworks/native/plain/include/utils/Mutex.h?h=jb_3.2_rb5.39

    # Apply patch for newly imported cameraparameters.cpp file
    pushd ${S}/..
    patch -p0 < camera_parameters_header_include_patch.txt
    popd

    # copy additional system header files
    mkdir -p ${STAGING_INCDIR}/system
    wget --no-check-certificate -O ${STAGING_INCDIR}/system/camera.h https://www.codeaurora.org/cgit/external/gigabyte/platform/system/core/plain/include/system/camera.h?h=caf/jb_3.2_rb5.39
    wget --no-check-certificate -O ${STAGING_INCDIR}/system/window.h https://www.codeaurora.org/cgit/external/gigabyte/platform/system/core/plain/include/system/window.h?h=caf/jb_3.2_rb5.39
    wget --no-check-certificate -O ${STAGING_INCDIR}/system/graphics.h https://www.codeaurora.org/cgit/external/gigabyte/platform/system/core/plain/include/system/graphics.h?h=caf/jb_3.2_rb5.39
    mkdir -p ${STAGING_INCDIR}/sync
    wget --no-check-certificate -O ${STAGING_INCDIR}/sync/sync.h https://www.codeaurora.org/cgit/external/gigabyte/platform/system/core/plain/include/sync/sync.h?h=caf/jb_3.2_rb5.39

    # patch utils lib to replace log with dlog
    cp ${S}/../dlog-replace-utils-patch.txt ${WORKSPACE}/base/
    pushd ${WORKSPACE}/base

    git reset --hard
    git apply dlog-replace-utils-patch.txt

    popd
}

do_clean_extra () {
    #do clean up the patch
    pushd ${WORKSPACE}/base
    git reset --hard
    popd
}

addtask do_clean_extra before do_clean

do_install_append_msm8960() {
   mkdir -p ${D}/usr/lib/hw

   # Move and rename libcamera.so files to hw/machine-specific names.
   cp ${D}/usr/lib/libcamera.so.0.0.0 ${D}/usr/lib/hw/libcamera.so

   pushd ${D}/usr/lib/hw
   ln -s libcamera.so ./camera.msm8960.so
   popd
}
