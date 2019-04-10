# (C) Copyright 2019
# Lukasz Majewski, DENX Software Engineering, lukma@denx.de.
#

# Linux kernel recipe to use vexpress-v2p-ca15-tc1 as a QEMU test
# board.

require kernel-common.inc

BRANCH = "y2038"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/arnd/playground.git;branch=${BRANCH}"

LINUX_VERSION = "5.0"
LINUX_VERSION_EXTENSION = "-y2038-${SRCREV}"

SRCREV ="48166e6ea47d23984f0b481ca199250e1ce0730a"
