FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

CFLAGS:remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"
CPPFLAGS:remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"
CXXFLAGS:remove = "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"

# GLIBC_GIT_URI = "git://sourceware.org/git/glibc.git"
GLIBC_GIT_URI = "git://github.com/lmajewski/y2038_glibc.git"

SRCBRANCH = "y2038_edge"
SRCREV_glibc = "8b1a43e675ea2ba7615784241ecfcc9f2b5a0c4f"

SRC_URI:remove = "file://0016-timezone-re-written-tzselect-as-posix-sh.patch"
SRC_URI:remove = "file://mte-backports.patch"

RDEPENDS:ldd = "bash"
RDEPENDS:tzcode = "bash"
