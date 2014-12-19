require iproute2.inc

#v3.2.0 tag is "447c118f138171b260ad045ad6e1b17f9ef462e2"
#but it was not fully tested and had build error, and the next commit fixed it.
SRCREV = "95fff185193cbfcbc58a6e521eed6f16ee184a9c"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/shemminger/iproute2.git \
           file://configure-cross.patch"
S = "${WORKDIR}/git"

