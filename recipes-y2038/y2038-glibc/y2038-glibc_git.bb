LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSES;md5=cfc0ed77a9f62fa62eded042ebe31d72 \
		    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
		    file://posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
		    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "linux-y2038 linux-libc-headers bison-native"

BRANCH = "Y2038-2.29-glibc-08-02-2019"
SRC_URI = "git://github.com/lmajewski/y2038_glibc.git;protocol=https;branch=${BRANCH}"

# Modify these as desired
PV = "2.29+git${SRCPV}"
SRCREV = "c0f0d3aeacf01075dde674256f049aa0124842c7"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# In /usr/include we shall have headers installed from linux-y2038
EXTRA_OECONF = "\
		--with-headers=${STAGING_INCDIR} \
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

do_make_check () {
	cd ${B}
	# Remove -Wformat-security checking as it causes 'make check' to fail
	# due to manual addition of -Wno-format to disable 'format-security'
	# checking for some tests (and defined earlier -Wformat-security causes
	# error
	oe_runmake check CC="${@'${CC}'.replace('-Wformat-security -Werror=format-security', '')}" \
		   CXX="${@'${CXX}'.replace('-Wformat-security -Werror=format-security', '')}"
}

do_make_check[doc] = "Run glibc's tests"
addtask do_make_check after do_compile

do_install[noexec] = "1"