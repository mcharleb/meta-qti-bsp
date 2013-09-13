DESCRIPTION = "Miscellaneous files for the base system."
PR = "r7"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://host.conf;md5=a61b9f6548d337c1cc1e5a4de39f7b7f"

#re-use non-perf settings
BASEMACHINE = "${@d.getVar('MACHINE', True).replace('-perf', '')}"

SRC_URI = " \
           file://nsswitch.conf \
           file://motd \
           file://inputrc \
           file://host.conf \
           file://profile \
           file://profile.d \
           file://${BASEMACHINE}/fstab \
           file://filesystems \
           file://issue.net \
           file://issue \
           file://usbd \
           file://share/dot.bashrc \
           file://share/dot.profile \
           file://ls \
           file://set-console \
           "
S = "${WORKDIR}"

docdir_append = "/${P}"
dirs1777 = "/tmp ${localstatedir}/volatile/lock ${localstatedir}/volatile/tmp"
dirs2775 = "/home ${prefix}/src ${localstatedir}/local"
dirs755 = "/bin /boot /dev ${sysconfdir} ${sysconfdir}/default \
           ${sysconfdir}/skel /lib /mnt /proc /home/root /sbin \
           ${prefix} ${bindir} ${docdir} /usr/games ${includedir} \
           ${libdir} ${sbindir} ${datadir} \
           ${datadir}/common-licenses ${datadir}/dict ${infodir} \
           ${mandir} ${datadir}/misc ${localstatedir} \
           ${localstatedir}/backups ${localstatedir}/lib \
           /sys ${localstatedir}/lib/misc ${localstatedir}/spool \
           ${localstatedir}/volatile ${localstatedir}/volatile/cache \
           ${localstatedir}/volatile/lock/subsys \
           ${localstatedir}/volatile/log \
           ${localstatedir}/volatile/run \
           /mnt /media /media/card /media/cf /media/net /media/ram \
           /media/union /media/realroot /media/hdd \
           /media/mmc1"

dirs755_micro = "/dev /proc /sys ${sysconfdir}"
dirs2775_micro = ""
dirs1777_micro = "/tmp"

media = "card cf net ram"
media_micro = ""

volatiles = "cache run log lock tmp"
conffiles = "${sysconfdir}/debian_version ${sysconfdir}/host.conf \
             ${sysconfdir}/inputrc ${sysconfdir}/issue /${sysconfdir}/issue.net \
             ${sysconfdir}/nsswitch.conf ${sysconfdir}/profile \
             ${sysconfdir}/default"

hostname = "openembedded"

do_install () {
    for d in ${dirs755}; do
        install -m 0755 -d ${D}$d
    done
    for d in ${dirs1777}; do
        install -m 1777 -d ${D}$d
    done
    for d in ${dirs2775}; do
        install -m 2755 -d ${D}$d
    done
    for d in ${volatiles}; do
        if [ -d ${D}${localstatedir}/volatile/$d ]; then
            ln -sf volatile/$d ${D}/${localstatedir}/$d
        fi
    done
    for d in ${media}; do
        ln -sf /media/$d ${D}/mnt/$d
    done

    if [ -n "${MACHINE}" -a "${hostname}" = "openembedded" ]; then
        echo ${MACHINE} > ${D}${sysconfdir}/hostname
    else
        echo ${hostname} > ${D}${sysconfdir}/hostname
    fi

    if [ "${DISTRO}" != "micro" -a "${DISTRO}" != "micro-uclibc" ]; then
        install -m 644 ${WORKDIR}/issue*  ${D}${sysconfdir}

        if [ -n "${DISTRO_NAME}" ]; then
            echo -n "${DISTRO_NAME} " >> ${D}${sysconfdir}/issue
            echo -n "${DISTRO_NAME} " >> ${D}${sysconfdir}/issue.net
            if [ -n "${DISTRO_VERSION}" ]; then
                echo -n "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue
                echo -n "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue.net
            fi
            echo "\n \l" >> ${D}${sysconfdir}/issue
            echo >> ${D}${sysconfdir}/issue
            echo "%h"    >> ${D}${sysconfdir}/issue.net
            echo >> ${D}${sysconfdir}/issue.net
        else
            install -m 0644 ${WORKDIR}/issue ${D}${sysconfdir}/issue
            install -m 0644 ${WORKDIR}/issue.net ${D}${sysconfdir}/issue.net
        fi

        install -m 0644 ${WORKDIR}/${BASEMACHINE}/fstab ${D}${sysconfdir}/fstab
        install -m 0644 ${WORKDIR}/filesystems ${D}${sysconfdir}/filesystems
        install -m 0644 ${WORKDIR}/usbd ${D}${sysconfdir}/default/usbd
        install -m 0644 ${WORKDIR}/profile ${D}${sysconfdir}/profile
        install -d 0755 ${D}${sysconfdir}/profile.d
        install -m 0755 ${WORKDIR}/profile.d/* ${D}${sysconfdir}/profile.d/
        install -m 0755 ${WORKDIR}/share/dot.profile ${D}${sysconfdir}/skel/.profile
        install -m 0755 ${WORKDIR}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
        install -m 0644 ${WORKDIR}/inputrc ${D}${sysconfdir}/inputrc
        install -m 0644 ${WORKDIR}/motd ${D}${sysconfdir}/motd

        ln -sf /proc/mounts ${D}${sysconfdir}/mtab
        install -m 0644 ${WORKDIR}/host.conf ${D}${sysconfdir}/host.conf
    fi

    install -m 0644 ${WORKDIR}/nsswitch.conf ${D}${sysconfdir}/nsswitch.conf
    install -m 0755 ${WORKDIR}/ls ${D}${sysconfdir}/../sbin/ls
    install -m 0755 ${WORKDIR}/set-console ${D}${sysconfdir}/../sbin/set-console

    ln -s /media/card ${D}/sdcard
}

FILES_${PN} = "/*"

CONFFILES_${PN} = "${sysconfdir}/fstab ${sysconfdir}/hostname"
