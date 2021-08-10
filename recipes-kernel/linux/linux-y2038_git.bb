# (C) Copyright 2019
# Lukasz Majewski, DENX Software Engineering, lukma@denx.de.
#

# Linux kernel recipe to use vexpress-v2p-ca15-tc1 as a QEMU test
# board.

require kernel-common.inc

BRANCH = "linux-5.1.y"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;name=linux;branch=${BRANCH} \
           file://0001-ntp-y2038-Remove-incorrect-time_t-truncation.patch \
           "

LINUX_VERSION = "5.1.21"
LINUX_VERSION_EXTENSION = "-y2038-${SRCREV_linux}"

SRCREV_linux = "4a9b1eb8bc3ba4ad8b3b1aa3317cf8d4a3aaad83"

KMETA_BRANCH = "yocto-5.0"
SRCREV_meta = "f0b575cda6d03540ff8da2f70421f13a08f34a5e"
