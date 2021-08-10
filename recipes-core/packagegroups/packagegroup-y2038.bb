SUMMARY = "y2038 package group"
DESCRIPTION = "The set of application packages required for y2038 image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r1"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = "\
  packagegroup-y2038 packagegroup-y2038-devel \
"

RDEPENDS:${PN} = "\
	python3-pip \
	python3-setuptools \
	gawk \
	util-linux \
	zile \
"

RDEPENDS:${PN}-devel = "\
	bison \
	texinfo \
	ntpdate \
	iperf3 \
	netcat \
	y2038-tests-dbg \
	git \
	openssh-sftp-server \
	sshfs-fuse \
"
