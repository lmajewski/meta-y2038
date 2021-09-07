# (C) Copyright 2019
# Lukasz Majewski, DENX Software Engineering, lukma@denx.de.
#

# Linux kernel recipe to use vexpress-v2p-ca15-tc1 as a QEMU test
# board.

require kernel-common.inc

LIC_FILES_CHKSUM="file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

BRANCH = "linux-5.10.y"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;name=linux;branch=${BRANCH} \
           "

LINUX_VERSION = "5.10.62"
LINUX_VERSION_EXTENSION = "-y2038-${SRCREV_linux}"

SRCREV_linux = "f6dd002450bf7b9143aff3af42ad1e12efe9a4f8"

KMETA_BRANCH = "yocto-5.10"
SRCREV_meta = "bce2813b162bb472c137fb503951295a931c25b6"

#
# For MACHINE=beaglebone-yocto (5.10.y)
#
KBRANCH:beaglebone-yocto = "v5.10/standard/beaglebone"
KMACHINE:beaglebone-yocto ?= "beaglebone"
#SRCREV_machine:beaglebone-yocto ?= "a6df693a45f5787d4254e0998f52b4465b2a5efe"
