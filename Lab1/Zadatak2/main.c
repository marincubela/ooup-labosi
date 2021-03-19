#include <stdio.h>
#include <stdlib.h>

typedef struct Unary_Function_s Unary_Function;
typedef double (*PTRFUN)(Unary_Function *this, double x);

PTRFUN *unaryVtable;
PTRFUN *squareVtable;
PTRFUN *linearVtable;

struct Unary_Function_s {
    PTRFUN *vptr;
    int lower_bound;
    int upper_bound;
};

double Unary_value_at(Unary_Function *this, double x) {
    return 0;
}

double Unary_negative_value_at(Unary_Function *this, double x) {
    return -this->vptr[0](this, x);
}

void Unary_tabulate(Unary_Function *this) {
    for (int x = this->lower_bound; x <= this->upper_bound; x++) {
        printf("f(%d)=%lf\n", x, this->vptr[0](this, x));
    }
}

int same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance) {
    if (f1->lower_bound != f2->lower_bound) return 0;
    if (f1->upper_bound != f2->upper_bound) return 0;

    for (int x = f1->lower_bound; x <= f1->upper_bound; x++) {
        double delta = f1->vptr[0](f1, x) - f2->vptr[0](f2, x);
        if (delta < 0) delta = -delta;
        if (delta > tolerance) return 0;
    }

    return 1;
}

void construct(Unary_Function *this, int lb, int ub) {
    this->vptr = unaryVtable;
    this->lower_bound = lb;
    this->upper_bound = ub;
}

Unary_Function *create_Unary_Function(int lb, int ub) {
    Unary_Function *p = (Unary_Function *)malloc(sizeof(Unary_Function));
    construct(p, lb, ub);
    return p;
}

typedef struct Square_s {
    Unary_Function super;
} Square;

double square_value_at(Unary_Function *this, double x) {
    return x * x;
}

void construct_Square(Square *this, int lb, int ub) {
    this->super = *create_Unary_Function(lb, ub);
    this->super.vptr = squareVtable;
}

Square *create_Square(int lb, int ub) {
    Square *p = (Square *)malloc(sizeof(Square));
    construct_Square(p, lb, ub);
    return p;
}

typedef struct Linear_s {
    Unary_Function super;
    double a;
    double b;
} Linear;

double linear_value_at(Unary_Function *this, double x) {
    return ((Linear *)this)->a * x + ((Linear *)this)->b;
}

void construct_Linear(Linear *this, int lb, int ub, double a_coef, double b_coef) {
    this->super = *create_Unary_Function(lb, ub);
    this->super.vptr = linearVtable;
    this->a = a_coef;
    this->b = b_coef;
}

Linear *create_Linear(int lb, int ub, double a_coef, double b_coef) {
    Linear *this = (Linear *)malloc(sizeof(Linear));
    construct_Linear(this, lb, ub, a_coef, b_coef);
    return this;
}

void initialize_Unary() {
    unaryVtable = (PTRFUN *)malloc(2 * sizeof(PTRFUN));
    unaryVtable[0] = Unary_value_at;
    unaryVtable[1] = Unary_negative_value_at;
}

void initialize_Square() {
    squareVtable = (PTRFUN *)malloc(2 * sizeof(PTRFUN));
    squareVtable[0] = square_value_at;
    squareVtable[1] = unaryVtable[1];
}

void initialize_Linear() {
    linearVtable = (PTRFUN *)malloc(2 * sizeof(PTRFUN));
    linearVtable[0] = linear_value_at;
    linearVtable[1] = unaryVtable[1];
}

void initialize() {
    initialize_Unary();
    initialize_Square();
    initialize_Linear();
}

void freeVtable() {
    free(unaryVtable);
    free(squareVtable);
    free(linearVtable);
}

int main(void) {
    initialize();

    Unary_Function *f1 = (Unary_Function *)create_Square(-2, 2);
    Unary_tabulate(f1);

    Unary_Function *f2 = (Unary_Function *)create_Linear(-2, 2, 5, -2);
    Unary_tabulate(f2);

    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
    printf("neg_val f2(1) = %lf\n", f2->vptr[1](f2, 1.0));

    free(f1);
    free(f2);
    freeVtable();
    getchar();
    return 0;
}