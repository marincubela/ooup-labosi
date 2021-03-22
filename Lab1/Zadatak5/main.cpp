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

typedef int (*fun1)(B *);
typedef int (*fun2)(B *, int);

void funkcija(B *pb) {
    fun1 *vt = *(fun1 **)pb;

    std::cout << "Povratna vrijednost prve funkcije: " << vt[0](pb) << std::endl;

    fun2 *vt2 = *(fun2 **)pb;
    std::cout << "Povratna vrijednost prve funkcije: " << vt2[1](pb, 1) << std::endl;
}

int main(void) {
    B *pb = new D();
    funkcija(pb);
    return 0;
}