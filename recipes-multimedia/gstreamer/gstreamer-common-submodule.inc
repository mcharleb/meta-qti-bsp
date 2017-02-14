#common project used in plugin bb.

do_patch_append() {
    import os
    os.system("cd ${S} && git rm common")
    cmd = "cd ${S} && git submodule add -f git://anongit.freedesktop.org/gstreamer/common && git submodule init"
    os.system(cmd)
}
