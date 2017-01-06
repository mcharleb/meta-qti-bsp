FILESEXTRAPATHS_append := "${THISDIR}:${THISDIR}/files:"
SRC_URI += "file://Remove-MLS-constraints.patch \
            file://0001-add-monolithic-policy.patch \
            file://0001-Remove-mls-relabelto.patch \
            file://policy/ \
"

do_patch_append() {
    # Move all policies declared in the meta layer to the expected locations
    import shutil
    for dir in os.listdir("${WORKDIR}/policy"):
        shutil.move("${WORKDIR}/policy/" + dir, "${S}/policy/modules/" + dir)
    shutil.rmtree("${WORKDIR}/policy")
}
