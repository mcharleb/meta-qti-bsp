require busybox_1.1x.inc

prefix = ""
SRC_URI += "file://fix-iptunnel-location.patch \
            file://0001-Include-sysinfo.h-in-libbb.h-and-remove-custom-sysin.patch \
            file://geninit \
            file://profile \
            file://fstab \
            file://inittab \
            file://rcS"

PR = "${INC_PR}.0"

SRC_URI[md5sum] = "96dd43cc7cee4017a6bf31b7da82a1f5"
SRC_URI[sha256sum] = "10954fcd5c48d8a262a3497b16227bf983a05658bf2bf661af2fdeca773f2fc0"
