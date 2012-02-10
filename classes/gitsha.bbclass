# 
# A sort-of replacement of GITVER for the purposes of handling git
# checked out repositories that are pretty much pre-cloned into 
# a directory structure that OE just simply links into the workspace.
#


# Dogsbody function that grabs the SHA hash from GIT...
def get_git_hash(path, d):
    if (path == "") :
	bb.fatal("\n\nlocalgit.bbclass ERROR:\nYou *MUST* set 'SRC_DIR' to a usable path!\n")
    else :
    	return oe_run(d, ["git", "rev-parse", "HEAD"], cwd=path).rstrip()

# This grabs the SHA of tip in the directory specified, presumes that SRC_DIR is properly set.
SRC_DIR ?= ""
GITSHA = "${@get_git_hash('${SRC_DIR}', d)}"


