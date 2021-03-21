#include <iostream>

class B {
   public:
    virtual int __cdecl prva() = 0;
    virtual int __cdecl druga(int) = 0;
};

class D : public B {
   public:
    virtual int __cdecl prva() { return 42; }
    virtual int __cdecl druga(int x) { return prva() + x; }
};

typedef void (*PTRFUN)();

void funkcija(B *pb) {
}

int main(void) {
    return 0;
}