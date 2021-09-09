inherit kernel
require recipes-kernel/linux/linux-yocto.inc

LIC_FILES_CHKSUM="file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

KERNEL_VERSION_SANITY_SKIP="1"
PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
META_REPO = "git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=${KMETA_BRANCH};destsuffix=${KMETA}"
SRC_URI:append = " ${META_REPO}"

SRC_URI:append:qemuarm = " file://defconfig file://qemu_vexpress_tune.cfg"
SRC_URI:append:qemuarm = " file://virtio.cfg file://printk.cfg"

# Below features must be removed when we do not use kernel-yocto
KERNEL_FEATURES:remove:qemuall="features/debug/printk.scc \
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

KMACHINE:qemux86-64-x32 = "qemux86-64"

KTYPE = "standard"

META_FEATURES = "cfg/virtio.scc cfg/sound.scc cfg/paravirt_kvm.scc"

KERNEL_FEATURES:append:qemux86 = " ${META_FEATURES}"
KERNEL_FEATURES:append:qemux86-64 = " ${META_FEATURES}"

LINUX_VERSION_EXTENSION = "-yocto-${MACHINE}-${SRCREV}"
