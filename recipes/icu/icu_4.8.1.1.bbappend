CXXFLAGS     += "-I${STAGING_INCDIR}/c++"
CXXFLAGS     += "-I${STAGING_INCDIR}/c++/${TARGET_SYS}"

EXTRA_OECONF += "--disable-renaming"
