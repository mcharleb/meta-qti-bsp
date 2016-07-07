# List of FOSS packages installed onto the root file system as specified by the user.
include ${MACHINE}-${PRODUCT}-image.inc

include machine-image.bb

# Call function makesystem to generate sparse ext4 image
addtask makesystem after do_rootfs before do_build
