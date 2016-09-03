inherit go update-rc.d

DESCRIPTION = "QMMF Webserver"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS := "go-cross"
DEPENDS += "github.com-gorilla-muxer"
DEPENDS += "qmmf-support"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/opensource/:"
SRC_URI  := "file://qmmf-webserver"
SRC_URI  += "file://0001-qmmf-webserver-update-http-library-path.patch"
SRC_URI  += "file://qmmf-webserver.sh"
S = "${WORKDIR}/qmmf-webserver"

INITSCRIPT_NAME = "qmmf-webserver.sh"
INITSCRIPT_PARAMS = "start 25 S 2 3 4 5 S . stop 75 0 1 6 ."

export CGO_ENABLED = "1"
export GOPATH="${S}:${STAGING_LIBDIR}/${TARGET_SYS}/go"

do_compile() {
  go build qmmf-webserver.go
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${S}/qmmf-webserver" "${D}/${bindir}"
  install -m 0755 ${WORKDIR}/qmmf-webserver.sh -D ${D}/${sysconfdir}/init.d/qmmf-webserver.sh
}

FILES_${PN} = "${bindir}/qmmf-webserver ${sysconfdir}/init.d/qmmf-webserver.sh"
