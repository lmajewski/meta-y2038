require y2038-image.inc

DESCRIPTION = "y2038 image for development"

PV = "0.1"

# Devel and debug tools and packages
EXTRA_IMAGE_FEATURES += "debug-tweaks dev-pkgs tools-sdk"

# This is needed to allow glibc building with SDK toolchain
TOOLCHAIN_HOST_TASK_append = " nativesdk-perl-module-locale"

IMAGE_INSTALL += "packagegroup-y2038-devel"

# Set the generated image size (for SD card) to be rounded
# to 2 GiB (rootfs size with alignment to 1 GiB)
IMAGE_ROOTFS_ALIGNMENT_y2038arm = "1048576"
