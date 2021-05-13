#include <stdlib.h>

typedef char const* (*PTRFUN)();

struct Parrot {
    PTRFUN* vtable;
    char const* name;
};

char const* name(void* this) {
    return ((struct Parrot*)this)->name;
}

char const* greet() {
    return "Pozdrav! Ja sam papiga";
}

char const* menu() {
    return "Pticja hrana";
}

PTRFUN parrotVTable[3] = {
    (PTRFUN)name,
    (PTRFUN)greet,
    (PTRFUN)menu};

void constructParrot(struct Parrot* this, char const* name) {
    this->name = name;
    this->vtable = parrotVTable;
}

void* create(char const* name) {
    struct Parrot* this = malloc(sizeof(struct Parrot));
    constructParrot(this, name);
    return this;
}