# (C) Copyright 2019
# Lukasz Majewski, DENX Software Engineering, lukma@denx.de.
#

# Linux kernel recipe to use vexpress-v2p-ca15-tc1 as a QEMU test
# board.

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

LIC_FILES_CHKSUM="file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

KERNEL_VERSION_SANITY_SKIP="1"
PV = "${LINUX_VERSION}+git${SRCPV}"

#BRANCH = "y2038-kernel-07-02-2019"
#SRC_URI = "https://github.com/lmajewski/y2038_kernel.git;branch=${BRANCH}"

BRANCH = "y2038"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/arnd/playground.git;branch=${BRANCH}"
SRC_URI_append_y2038arm = " file://defconfig file://qemu_vexpress_tune.cfg"
SRC_URI_append = " file://virtio.cfg file://printk.cfg"

LINUX_VERSION = "5.0"
LINUX_VERSION_EXTENSION = "-y2038-${SRCREV}"

SRCREV ="48166e6ea47d23984f0b481ca199250e1ce0730a"

# Below features must be removed when we do not use kernel-yocto
KERNEL_FEATURES_remove_qemuall="features/debug/printk.scc \
				features/kernel-sample/kernel-sample.scc"

# Use in-tree defconfig
KBUILD_DEFCONFIG_qemux86 = "i386_defconfig"

SYSROOT_DIRS_append = " ${Y2038_GLIBC_DEPLOY_DIR}"
SYSROOT_DIRS_remove = " ${base_libdir} ${nonarch_base_libdir}"
do_install_append () {
	oe_runmake headers_install INSTALL_HDR_PATH=${D}${Y2038_GLIBC_DEPLOY_DIR}
}

# We need to re-define this function as kernel.bbclass makes it an empty one
# to avoid clash with linux-firmware
sysroot_stage_all() {
	sysroot_stage_dirs ${D} ${SYSROOT_DESTDIR}
}

FILES_${KERNEL_PACKAGE_NAME}-base += "${Y2038_GLIBC_DEPLOY_DIR}/*"
