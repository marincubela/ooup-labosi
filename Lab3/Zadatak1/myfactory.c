#include "myfactory.h"

#include <stdio.h>
#include <string.h>
#include <windows.h>

typedef char const* (*PTRFUN)();

void* myfactory(char const* libname, char const* ctorarg) {
    int n = 2 + strlen(libname) + 4 + 1;
    char copy[n];
    copy[0] = '.';
    copy[1] = '/';
    strcpy(copy + 2, libname);
    copy[n - 5] = '.';
    copy[n - 4] = 'd';
    copy[n - 3] = 'l';
    copy[n - 2] = 'l';
    copy[n - 1] = '\0';

    // otvori biblioteku s imenom libname
    HINSTANCE hGetProcIDDLL = LoadLibrary(copy);

    if (!hGetProcIDDLL) {
        printf("Could not get dll module!\n");
    }

    // ucitaj funkciju create
    PTRFUN f = (PTRFUN)GetProcAddress(hGetProcIDDLL, "create");
    if (!f) {
        printf("Could not find requested function!\n");
    }

    // pozovi create s drugim argumentom ctorarg
    return f(ctorarg);
}