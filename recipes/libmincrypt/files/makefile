libmincrypt.a: rsa.o sha.o
	$(AR) $(ARFLAGS) $@ $^

# This relies on $(INCLUDES) for an appropriate header path. The path in the
# Android tree is system/core/include.
rsa.o: libmincrypt/rsa.c mincrypt/rsa.h
	$(CC) $(INCLUDES) $(CFLAGS) -c $<

sha.o: libmincrypt/sha.c mincrypt/sha.h
	$(CC) $(INCLUDES) $(CFLAGS) -c $<
