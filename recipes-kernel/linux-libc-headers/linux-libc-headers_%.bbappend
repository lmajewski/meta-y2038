DEPENDS += "bison-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
S = "${WORKDIR}/git"

KERNEL_VERSION_SANITY_SKIP="1"
PV = "${LINUX_VERSION}+git${SRCPV}"

#BRANCH = "y2038-kernel-07-02-2019"
#SRC_URI = "https://github.com/lmajewski/y2038_kernel.git;branch=${BRANCH}"

BRANCH = "y2038"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/arnd/playground.git;branch=${BRANCH}"

LINUX_VERSION = "5.0"
LINUX_VERSION_EXTENSION = "-y2038-${SRCREV}"

SRCREV ="48166e6ea47d23984f0b481ca199250e1ce0730a"
