LICENSE = "CLOSED"

# We explicitly depend on new glibc - and do not provide the new
# one to recipe-sysroot
DEPENDS = "y2038-glibc"
DEPENDS_remove = "virtual/libc"
RDEPENDS_${PN} = "y2038-glibc"

SRC_URI = "git://github.com/lmajewski/y2038tests.git;protocol=https"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "55a43c5ee4cefccacd91d8984eb90eaac3d8b714"

S = "${WORKDIR}/git"

# Provide setup similar to the original y2038 sandbox
# - build without GCC optimization (-O0)

PARALLEL_MAKE = "-j 1"
CC_remove = "-D_FORTIFY_SOURCE=2"
CFLAGS_remove = "-O2"
CFLAGS_append = " \
	--sysroot=${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR} \
	-isystem=${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}${includedir} \
	-I${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}/include"

LDFLAGS_append = "\
	--sysroot=${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR} \
	-Wl,-rpath=${Y2038_GLIBC_DEPLOY_DIR}${base_libdir}"

# It is also possible to setup the dynamic linker path during build
#-Wl,--dynamic-linker=${Y2038_GLIBC_DEPLOY_DIR}${base_libdir}/ld-linux-armhf.so.3

do_compile () {
	oe_runmake
}

FILES_${PN} += "${bindir}"
do_install () {
	install -d ${D}${bindir}
	install -m 0755 test_y2038 ${D}${bindir}
	install -m 0755 test_n2038 ${D}${bindir}
}

do_prepare_recipe_sysroot_fix[doc] = "\
sysroot adjustments necessary for building y2038 test application"

do_prepare_recipe_sysroot_fix () {
# Copy toolchain files - like crtbeginS.o - to allow cross compilation
	cp -a ${STAGING_DIR_HOST}${libdir}/${TARGET_SYS} \
		${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}${libdir}

# Copy the libgcc* as suggested in:
# https://sourceware.org/glibc/wiki/Testing/Builds
# 'Building glibc with intent to install'
	cp -a ${STAGING_DIR_HOST}${base_libdir}/libgcc* \
		${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}${base_libdir}
}

addtask do_prepare_recipe_sysroot_fix before do_configure \
	after do_prepare_recipe_sysroot
