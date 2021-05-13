#include <stdlib.h>

typedef char const* (*PTRFUN)();

struct Tiger {
    PTRFUN* vtable;
    char const* name;
};

char const* name(void* this) {
    return ((struct Tiger*)this)->name;
}

char const* greet() {
    return "Pozdrav! Ja sam tigar";
}

char const* menu() {
    return "Macja hrana";
}

PTRFUN tigerVTable[3] = {
    (PTRFUN)name,
    (PTRFUN)greet,
    (PTRFUN)menu};

void constructTiger(struct Tiger* this, char const* name) {
    this->name = name;
    this->vtable = tigerVTable;
}

void* create(char const* name) {
    struct Tiger* this = malloc(sizeof(struct Tiger));
    constructTiger(this, name);
    return this;
}