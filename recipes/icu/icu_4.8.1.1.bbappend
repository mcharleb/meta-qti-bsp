SRC_URI      += "file://disable_rename.patch"

CXXFLAGS     += "-I${STAGING_INCDIR}/c++"
CXXFLAGS     += "-I${STAGING_INCDIR}/c++/${TARGET_SYS}"
