#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

PTRFUN* dogVtable = NULL;
PTRFUN* catVtable = NULL;

char const* dogGreet(void) {
    return "vau!";
}

char const* dogMenu(void) {
    return "kuhanu govedinu";
}

char const* catGreet(void) {
    return "mijau!";
}

char const* catMenu(void) {
    return "konzerviranu tunjevinu";
}

void initializeDogVtable() {
    dogVtable = (PTRFUN*)malloc(2 * sizeof(PTRFUN));
    dogVtable[0] = dogGreet;
    dogVtable[1] = dogMenu;
}

void initializeCatVtable() {
    catVtable = (PTRFUN*)malloc(2 * sizeof(PTRFUN));
    catVtable[0] = catGreet;
    catVtable[1] = catMenu;
}

void initialize() {
    initializeDogVtable();
    initializeCatVtable();
}

struct Animal {
    PTRFUN* vptr;
    char const* ime;
};

void animalPrintGreeting(struct Animal* p) {
    printf("%s\n", p->vptr[0]());
}

void animalPrintMenu(struct Animal* p) {
    printf("%s\n", p->vptr[1]());
}

void constructDog(struct Animal* p, char const* ime) {
    p->ime = ime;
    p->vptr = dogVtable;
}

struct Animal* createDog(char const* ime) {
    struct Animal* p = (struct Animal*)malloc(sizeof(struct Animal));
    constructDog(p, ime);
    return p;
}

void constructCat(struct Animal* p, char const* ime) {
    p->ime = ime;
    p->vptr = dogVtable;
}

struct Animal* createCat(char const* ime) {
    struct Animal* p = (struct Animal*)malloc(sizeof(struct Animal));
    constructCat(p, ime);
    return p;
}

struct Animal* createNDogs(int n) {
    if (n < 1) {
        return NULL;
    }

    struct Animal* p = (struct Animal*)malloc(n * sizeof(struct Animal));

    for (int i = 0; i < n; i++) {
        constructDog(p + i, "A");
    }

    return p;
}

void testAnimals(void) {
    struct Animal* p1 = createDog("Hamlet");
    struct Animal* p2 = createCat("Ofelija");
    struct Animal* p3 = createDog("Polonije");

    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);

    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);

    free(p1);
    free(p2);
    free(p3);
}

int main(void) {
    initialize();
    testAnimals();
    return 0;
}