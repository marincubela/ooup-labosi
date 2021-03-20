#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

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

PTRFUN dogVtable[2] = {dogGreet, dogMenu};
PTRFUN catVtable[2] = {catGreet, catMenu};

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

void constructDog(struct Animal* this, char const* ime) {
    this->ime = ime;
    this->vptr = dogVtable;
}

struct Animal* createDog(char const* ime) {
    struct Animal* this = (struct Animal*)malloc(sizeof(struct Animal));
    constructDog(this, ime);
    return this;
}

void constructCat(struct Animal* this, char const* ime) {
    this->ime = ime;
    this->vptr = catVtable;
}

struct Animal* createCat(char const* ime) {
    struct Animal* this = (struct Animal*)malloc(sizeof(struct Animal));
    constructCat(this, ime);
    return this;
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
    testAnimals();
    getchar();
    return 0;
}