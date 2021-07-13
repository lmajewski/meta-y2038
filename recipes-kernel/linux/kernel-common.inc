inherit kernel
require recipes-kernel/linux/linux-yocto.inc

LIC_FILES_CHKSUM="file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

KERNEL_VERSION_SANITY_SKIP="1"
PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI_append_y2038arm = " file://defconfig file://qemu_vexpress_tune.cfg"
SRC_URI_append_y2038arm = " file://virtio.cfg file://printk.cfg"

# Below features must be removed when we do not use kernel-yocto
KERNEL_FEATURES_remove_qemuall="features/debug/printk.scc \
				features/kernel-sample/kernel-sample.scc"

#############################
# QEMU x86(_64) specific code
#
#############################

# Use kernel's metadata from OE/Yoctoproject
# to get the 'defconfig' generated with  "standard" set of features.
#
# As we strive to get fully functional (similar to distro's) kernel
# this is the best way to achieve this goal.
#

KMACHINE_qemux86-64-x32 = "qemux86-64"

KMETA = "kernel-meta"
KTYPE = "standard"
META_REPO = "git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=${KMETA_BRANCH};destsuffix=${KMETA}"
META_FEATURES = "cfg/virtio.scc cfg/sound.scc cfg/paravirt_kvm.scc"

SRC_URI_append_qemux86 = " ${META_REPO}"
SRC_URI_append_qemux86-64 = " ${META_REPO}"

KERNEL_FEATURES_append_qemux86 = " ${META_FEATURES}"
KERNEL_FEATURES_append_qemux86-64 = " ${META_FEATURES}"

LINUX_VERSION_EXTENSION = "-yocto-${MACHINE}-${SRCREV}"
