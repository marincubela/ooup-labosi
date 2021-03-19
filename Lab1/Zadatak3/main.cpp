#include <stdio.h>

class CoolClass {
   public:
    virtual void set(int x) { x_ = x; };
    virtual int get() { return x_; };

   private:
    int x_;
};
class PlainOldClass {
   public:
    void set(int x) { x_ = x; };
    int get() { return x_; };

   private:
    int x_;
};

int main(void) {
    printf("Size of plain old class: %d\nSize of cool class: %d", sizeof(PlainOldClass), sizeof(CoolClass));

    getchar();
    return 0;
}