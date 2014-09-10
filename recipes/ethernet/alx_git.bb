inherit module

DESCRIPTION = "Qualcomm Atheros Gigabit Ethernet Driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

PR = "r2"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://external/compat-wireless/drivers/net/ethernet/atheros/alx/"
S = "${WORKDIR}/external/compat-wireless/drivers/net/ethernet/atheros/alx/"

do_install() {
    module_do_install
}

# Remove dependency for wrong kernel version
python split_kernel_module_packages_append() {
    if modules:
        metapkg = d.getVar('KERNEL_MODULES_META_PACKAGE', True)
        d.delVar('RDEPENDS_' + metapkg)
}
