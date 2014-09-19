require bluez5.inc

PR = "r0"

SRC_URI += "file://0001-bluetooth-Add-bluetooth-support-for-QCA6174-chip.patch \
            file://0002-bluetooth-Enable-3Mbps-baud-rate-support.patch \
            file://0003-bluetooth-Remove-unused-functions-in-the-firmware-do.patch \
            file://0004-bluetooth-Configure-BD-Address.patch \
            file://0005-bluetooth-Fix-bug-in-firmware-parsing-mechanism.patch \
            file://0006-bluetooth-Enable-bluetooth-low-power-mode-functional.patch"

SRC_URI[md5sum] = "2a575ec06aeaeadca9605b2f8173e00a"
SRC_URI[sha256sum] = "92bf4ce87d58014794ef6b22dc0a13b0b19acdf9c96870391c935d1e01a43ffa"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

do_install_append() {
        install -d ${D}${sysconfdir}/bluetooth/

        if [ -f ${S}/profiles/network/network.conf ]; then
            install -m 0644 ${S}/profiles/network/network.conf ${D}/${sysconfdir}/bluetooth/
        fi

        if [ -f ${S}/profiles/input/input.conf ]; then
            install -m 0644 ${S}/profiles/input/input.conf ${D}/${sysconfdir}/bluetooth/
        fi
        # at_console doesn't really work with the current state of OE, so punch some more holes so people can actually use BT
        install -m 0644 ${S}/src/bluetooth.conf ${D}/${sysconfdir}/dbus-1/system.d/
}

PACKAGES =+ "libasound-module-bluez"

FILES_libasound-module-bluez = "\
  ${libdir}/alsa-lib/libasound_module_ctl_bluetooth.so \
  ${libdir}/alsa-lib/libasound_module_pcm_bluetooth.so \
  ${datadir}/alsa\
"
FILES_${PN} += "\
  ${base_libdir}/udev/ \
  ${base_libdir}/systemd/ \
"
FILES_${PN}-dev += "\
  ${libdir}/alsa-lib/libasound_module_ctl_bluetooth.la \
  ${libdir}/alsa-lib/libasound_module_pcm_bluetooth.la \
"
