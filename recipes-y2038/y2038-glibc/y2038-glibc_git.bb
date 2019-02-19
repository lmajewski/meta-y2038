LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSES;md5=cfc0ed77a9f62fa62eded042ebe31d72 \
		    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
		    file://posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
		    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "linux-y2038 bison-native"

BRANCH = "Y2038-2.29-glibc-08-02-2019"
SRC_URI = "git://github.com/lmajewski/y2038_glibc.git;protocol=https;branch=${BRANCH}"

# Modify these as desired
PV = "2.29+git${SRCPV}"
SRCREV = "c0f0d3aeacf01075dde674256f049aa0124842c7"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# In /usr/include we shall have headers installed from linux-y2038
EXTRA_OECONF = "\
	--with-headers=${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}/include \
	--prefix=/usr \
	--host=${TARGET_SYS} \
	--build=${BUILD_SYS} \
"
inherit autotools deploy

do_configure () {
	${S}/configure ${EXTRA_OECONF}
}

do_compile () {
	oe_runmake
}

# Remove -Wformat-security checking as it causes 'make check' to fail
# due to manual addition of -Wno-format to disable 'format-security'
# checking for some tests (and defined earlier -Wformat-security causes
# error

MAKE_CHECK_EXCLUDE = "\
'CC=${@'${CC}'.replace('-Wformat-security -Werror=format-security', '')}' \
'CXX=${@'${CXX}'.replace('-Wformat-security -Werror=format-security', '')}' \
"
do_make_check () {
	cd ${B} && oe_runmake check ${MAKE_CHECK_EXCLUDE}
}

do_make_check[doc] = "Run glibc's tests"
addtask do_make_check after do_compile

SYSROOT_DIRS_append = "${Y2038_GLIBC_DEPLOY_DIR}"
do_install () {
	oe_runmake install DESTDIR=${D}${Y2038_GLIBC_DEPLOY_DIR}

	# Remove /opt/usr/bin as we do not need glibc scripts to be installed
	# on the target rootfs (for glibc development)
	rm -rf ${D}${Y2038_GLIBC_DEPLOY_DIR}${bindir}/*
}

FILES_${PN} += "${Y2038_GLIBC_DEPLOY_DIR}/*"

# As we strive to have y2038-glibc build as a standalone package,
# some QA checks need to be disabled to allow smooth installation
# in the /opt directory (to avoid clash with original glibc)
INSANE_SKIP_${PN} += "dev-so staticdev"

# Do not strip the resultings libc.*.so
INHIBIT_PACKAGE_STRIP = "1"
