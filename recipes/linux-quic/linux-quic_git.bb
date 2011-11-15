inherit linux-kernel-base srctree gitver

DESCRIPTION = "QuIC Linux Kernel"
LICENSE = "GPLv2"

PV = "${GITVER}"
PR = "r2"
PACKAGE_ARCH = ${BASE_PACKAGE_ARCH}

S = "${WORKSPACE}/kernel"
O = "${WORKDIR}/obj"
KDIR = "/usr/src/linux"

PROVIDES += "virtual/kernel"
DEPENDS = "virtual/${TARGET_PREFIX}gcc"
INHIBIT_DEFAULT_DEPS = "1"
# Until usr/src/linux/scripts can be correctly processed
PACKAGE_STRIP = "no"

PACKAGES = "kernel kernel-base kernel-module-bridge"

PACKAGES =+ "kernel-image"
FILES_kernel-image = "/boot/${KERNEL_IMAGETYPE}*"

PACKAGES =+ "kernel-dev"
FILES_kernel-dev = "/boot/System.map* /boot/Module.symvers* /boot/config*"

PACKAGES =+ "kernel-vmlinux"
FILES_kernel-vmlinux = "/boot/vmlinux*"

PACKAGES =+ "kernel-headers"
FILES_kernel-headers = "${KDIR}/usr/include"

PACKAGES =+ "kernel-modbuild"
FILES_kernel-modbuild = "${KDIR}"

PACKAGES =+ "kernel-modules"
FILES_kernel-modules = "/lib/modules"

# The kernel makefiles do not like extra flags being given to make.
EXTRA_OEMAKE_pn-${PN} = ""
CFLAGS_pn-${PN} = ""
CPPFLAGS_pn-${PN} = ""
CXXFLAGS_pn-${PN} = ""
LDFLAGS_pn-${PN} = ""
MACHINE_pn-${PN} = ""

export ARCH = "${TARGET_ARCH}"
export CROSS_COMPILE = "${TARGET_PREFIX}"

uses_modules () {
    grep -q -i -e '^CONFIG_MODULES=y$' "${O}/.config"
}

do_configure () {
    mkdir -p ${O}
    oe_runmake ${KERNEL_DEFCONFIG} O=${O}
    oe_runmake savedefconfig O=${O}
}

do_compile () {
    oe_runmake ${KERNEL_IMAGETYPE} O=${O}
    uses_modules && oe_runmake modules O=${O}
}

__do_clean_make () {
    [ -d ${O} ] && oe_runmake mrproper O=${O}
    oe_runmake mrproper
}

KERNEL_VERSION = "${@get_kernelversion('${O}')}"
do_install () {
# Files destined for the target
    install -d ${D}/boot
    for f in System.map Module.symvers vmlinux; do
        install -m 0644 ${O}/${f} ${D}/boot/${f}-${KERNEL_VERSION}
    done
    install -m 0644 ${O}/arch/${TARGET_ARCH}/boot/${KERNEL_IMAGETYPE} \
        ${D}/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    install -m 0644 ${O}/.config ${D}/boot/config-${KERNEL_VERSION}
    uses_modules && oe_runmake modules_install O=${O} INSTALL_MOD_PATH=${D}

    # Files needed for staging
    install -d ${D}${KDIR}/usr
    oe_runmake headers_install O=${D}${KDIR}
    oe_runmake ${KERNEL_DEFCONFIG} O=${D}${KDIR}
    uses_modules && oe_runmake modules_prepare O=${D}${KDIR}
}

sysroot_stage_all_append() {
    sysroot_stage_dir ${D}/boot ${SYSROOT_DESTDIR}${STAGING_DIR_HOST}/boot
    sysroot_stage_dir ${D}${KDIR} ${SYSROOT_DESTDIR}${STAGING_DIR_HOST}${KDIR}
}
