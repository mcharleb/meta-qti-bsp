require bluez4.inc

SRC_URI[md5sum] = "bc21551ac9a218935f2d2e7f6129dbff"
SRC_URI[sha256sum] = "54c192eb6ff31707807d86285c4c6836be36ca7b50b0696af38594aabd0e285b"

SRC_URI += "file://fix-build-races.patch"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_angstrom = "1"
DEFAULT_PREFERENCE_shr = "1"

DEPENDS += "libsndfile1"

PR = "${INC_PR}.3"

# Not all distros have a recent enough udev
BTUDEV = " --disable-udevrules"
BTUDEV_angstrom = " --enable-udevrules"
BTUDEV_shr = " --enable-udevrules"

EXTRA_OECONF += "${BTUDEV}"
