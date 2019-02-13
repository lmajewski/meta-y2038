require y2038-image.inc

DESCRIPTION = "y2038 image for development"

PV = "0.1"

# Devel and debug tools and packages
EXTRA_IMAGE_FEATURES += "debug-tweaks dev-pkgs tools-sdk"

# This is needed to allow glibc building with SDK toolchain
TOOLCHAIN_HOST_TASK_append = " nativesdk-perl-module-locale"

IMAGE_INSTALL += "packagegroup-y2038-devel"
