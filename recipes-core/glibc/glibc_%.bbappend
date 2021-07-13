FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

CFLAGS_remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"
CPPFLAGS_remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"
CXXFLAGS_remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"

# GLIBC_GIT_URI = "git://sourceware.org/git/glibc.git"
# GLIBC_GIT_URIgit@github.com:lmajewski/y2038_glibc.git

SRCBRANCH = "master"
SRCREV_glibc = "8235f9311bddbe4cf8ff1fa8f72f41aa77e27e00"

SRC_URI_remove = "file://0016-timezone-re-written-tzselect-as-posix-sh.patch"
SRC_URI_remove = "file://mte-backports.patch"

RDEPENDS_ldd = "bash"
RDEPENDS_tzcode = "bash"

#EXTRA_OECONF_append = " --enable-kernel=${Y2038_GLIBC_MIN_KERNEL_VERSION}"

# This revert would not be necessary as proper fix for it is going to be added
# to gcc 11 and glibc:
# https://sourceware.org/pipermail/libc-alpha/2021-July/128963.html
SRC_URI_append = " \
	file://0001-Revert-Define-PTHREAD_STACK_MIN-to-sysconf-_SC_THREA.patch"
