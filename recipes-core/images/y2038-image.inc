# Include file for y2038-image and y2038-image-devel
#
DEPENDS = "virtual/kernel"

IMAGE_FEATURES += " \
    ssh-server-openssh \
"

IMAGE_INSTALL += " \
    packagegroup-core-boot \
    packagegroup-y2038 \
"

# Provide extra 64 MiB
IMAGE_ROOTFS_EXTRA_SPACE="65536"

DISTRO_FEATURES_DEFAULT_remove = "bluetooth \
				  irda pcmcia \
				  usbhost \
				  wifi \
				  nfc x11"

inherit core-image
