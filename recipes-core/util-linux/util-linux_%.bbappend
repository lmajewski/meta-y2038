FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append = " \
	file://0001-fix-Change-the-signature-of-close_range-to-match-one.patch"
