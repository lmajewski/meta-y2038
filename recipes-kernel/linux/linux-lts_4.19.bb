# (C) Copyright 2019
# Lukasz Majewski, DENX Software Engineering, lukma@denx.de.
#

# Linux kernel recipe to use vexpress-v2p-ca15-tc1 as a QEMU test
# board.
# This is the arm kernel without 64 bit time syscalls

require kernel-common.inc

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.19.y"

LINUX_VERSION = "4.19"
LINUX_VERSION_EXTENSION = "-n2038-${SRCREV}"

SRCREV = "4d552acf337038028f7e2f63a927afb7adf65fc1"

KMETA_BRANCH = "yocto-4.19"
SRCREV_meta = "9bda6190bfc9e7858c2f7588109a0ec966f37a09"
