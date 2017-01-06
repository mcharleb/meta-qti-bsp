FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://Remove-MLS-constraints.patch \
            file://selinux-Use-monolithic-policy.patch \
            file://selinux-Add-default-policy-files-for-MSM-daemons.patch"
