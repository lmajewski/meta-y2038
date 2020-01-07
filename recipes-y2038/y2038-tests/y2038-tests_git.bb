LICENSE = "CLOSED"

# We explicitly depend on new glibc - and do not provide the new
# one to recipe-sysroot
DEPENDS = "y2038-glibc"
DEPENDS_remove = "virtual/libc"
RDEPENDS_${PN} = "y2038-glibc"

SRC_URI = "git://github.com/lmajewski/y2038tests.git;protocol=https"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "80d6a49a31d5e6673c3739f316544b2ddc9cba43"

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
CFLAGS_append = " -ggdb"

LDFLAGS_append = "\
	-Wl,-rpath=${Y2038_GLIBC_DEPLOY_DIR}${base_libdir} \
	-Wl,--dynamic-linker=${Y2038_GLIBC_DEPLOY_DIR}${base_libdir}/ld-linux-armhf.so.3"

LDFLAGS_append_y2038arm = "\
	--sysroot=${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}"

LDFLAGS_append_qemux86 = "\
	--sysroot=${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}"

LDFLAGS_append_qemux86-64 = "\
	-Wl,--sysroot=${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR} \
	-Wl,-rpath=${Y2038_GLIBC_DEPLOY_DIR}/lib64 \
	-L${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}/lib64/ \
	-L${STAGING_DIR_HOST}${Y2038_GLIBC_DEPLOY_DIR}/usr/lib64/"

do_compile () {
	oe_runmake
}

inherit autotools-brokensep package

FILES_${PN} += "${bindir}"
FILES_${PN}-dbg += "${exec_prefix}/src/debug"

do_install () {
	install -d ${D}${bindir}
	install -m 0755 test_y2038 ${D}${bindir}
	install -m 0755 test_n2038 ${D}${bindir}

	install -d ${D}${exec_prefix}/src/debug/${PN}/${PV}
	install -m 0644 ${S}/*.c ${D}${exec_prefix}/src/debug/${PN}/${PV}
	install -m 0644 ${S}/*.h ${D}${exec_prefix}/src/debug/${PN}/${PV}
}

def do_prepare_recipe_sysroot_32bit_fix(d):
    # Copy toolchain files - like crtbeginS.o - to allow cross compilation
    cp_from = d.getVar('STAGING_DIR_HOST') + d.getVar('libdir') + "/" + d.getVar('TARGET_SYS')
    cp_to = d.getVar('STAGING_DIR_HOST') + d.getVar('Y2038_GLIBC_DEPLOY_DIR') + d.getVar('libdir')
    os.system("cp -a " + cp_from + " " + cp_to)
    # Copy the libgcc* as suggested in:
    # https://sourceware.org/glibc/wiki/Testing/Builds
    # 'Building glibc with intent to install'
    cp_from = d.getVar('STAGING_DIR_HOST') + d.getVar('base_libdir') + "/libgcc*"
    cp_to = d.getVar('STAGING_DIR_HOST') + d.getVar('Y2038_GLIBC_DEPLOY_DIR') + d.getVar('base_libdir')
    os.system("cp -a " + cp_from + " " + cp_to)

def do_prepare_recipe_sysroot_64bit_fix(d):
    # As we pass -Wl,--sysroot=${STAGING_DIR_HOST}/opt as linker root, we need
    # to provide symbolic links to some compiler specific libs (*.so, *.a)
    #
    os.chdir(d.getVar('STAGING_DIR_HOST'))
    os.symlink("."+ d.getVar('Y2038_GLIBC_DEPLOY_DIR') + "/lib64", "./lib64")
    os.symlink(".."+ d.getVar('Y2038_GLIBC_DEPLOY_DIR') + "/usr/lib64", "./usr/lib64")

python do_prepare_recipe_sysroot_append_y2038arm () {
    bb.note("32 bit ARM")
    return do_prepare_recipe_sysroot_32bit_fix(d)
}

python do_prepare_recipe_sysroot_append_qemux86 () {
    bb.note("32 bit x86")
    return do_prepare_recipe_sysroot_32bit_fix(d)
}

python do_prepare_recipe_sysroot_append_qemux86-64 () {
    bb.note("64 bit x86-64")
    return do_prepare_recipe_sysroot_64bit_fix(d)
}
