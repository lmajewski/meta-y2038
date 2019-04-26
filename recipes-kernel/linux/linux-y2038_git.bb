# (C) Copyright 2019
# Lukasz Majewski, DENX Software Engineering, lukma@denx.de.
#

# Linux kernel recipe to use vexpress-v2p-ca15-tc1 as a QEMU test
# board.

require kernel-common.inc

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;branch=${BRANCH}"

LINUX_VERSION = "5.1-rc6"
LINUX_VERSION_EXTENSION = "-y2038-${SRCREV}"

SRCREV ="085b7755808aa11f78ab9377257e1dad2e6fa4bb"

KMETA_BRANCH = "yocto-5.0"
SRCREV_meta = "f0b575cda6d03540ff8da2f70421f13a08f34a5e"
