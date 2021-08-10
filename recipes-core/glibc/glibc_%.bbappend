FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

CFLAGS:remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"
CPPFLAGS:remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"
CXXFLAGS:remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"

# GLIBC_GIT_URI = "git://sourceware.org/git/glibc.git"
# GLIBC_GIT_URI = "git@github.com:lmajewski/y2038_glibc.git"

SRCBRANCH = "master"
SRCREV_glibc = "c87fcacc50505d550f1bb038382bcc7ea73a5926"

SRC_URI:remove = "file://0016-timezone-re-written-tzselect-as-posix-sh.patch"
SRC_URI:remove = "file://mte-backports.patch"

RDEPENDS:ldd = "bash"
RDEPENDS:tzcode = "bash"
