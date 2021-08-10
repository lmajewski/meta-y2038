LICENSE = "CLOSED"

SRC_URI = "git://github.com/lmajewski/y2038tests.git;protocol=https"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "129ff665aaa457b36f314d3fe8d216238278fc41"

S = "${WORKDIR}/git"

# Provide setup similar to the original y2038 sandbox
# - build without GCC optimization (-O0)

PARALLEL_MAKE = "-j 1"
CC:remove = "-D_FORTIFY_SOURCE=2"
CFLAGS:remove = "-O2"
CFLAGS:append = " -ggdb"
CFLAGS:append = " -O0"

do_compile () {
	oe_runmake
}

inherit autotools-brokensep package

FILES:${PN} += "${bindir}"
FILES:${PN}-dbg += "${exec_prefix}/src/debug"

do_install () {
	install -d ${D}${bindir}
	install -m 0755 test_y2038 ${D}${bindir}
	install -m 0755 test_n2038 ${D}${bindir}

	install -d ${D}${exec_prefix}/src/debug/${PN}/${PV}
	install -m 0644 ${S}/*.c ${D}${exec_prefix}/src/debug/${PN}/${PV}
	install -m 0644 ${S}/*.h ${D}${exec_prefix}/src/debug/${PN}/${PV}
}
