# Options required to provide Y2038 support

# Kernel 5.1.0+ has 64 bit time related syscalls on 32 bit ports
# e.g. ARMv7
OLDEST_KERNEL = "5.1.0"

# Specify global CFLAGS for target to support 64 bit time
TARGET_CPPFLAGS += "-D_TIME_BITS=64 -D_FILE_OFFSET_BITS=64"
