#include <assert.h>
#include <stdlib.h>

#include <iostream>

struct Point {
    int x;
    int y;
};

class Shape {
   public:
    virtual void draw() = 0;
    virtual void move(int x, int y) = 0;
};

class Circle : public Shape {
    virtual void draw() {
        std::cerr << "in draw in Circle\n";
    }

    virtual void move(int x, int y) {
        this->center_.x += x;
        this->center_.y += y;
        std::cerr << "in move in Circle\n";
    }
    double radius_;
    Point center_;
};

class Square {
    virtual void draw() {
        std::cerr << "in draw in Square\n";
    }

    virtual void move(int x, int y) {
        this->center_.x += x;
        this->center_.y += y;
        std::cerr << "in move in Square\n";
    }
    double side_;
    Point center_;
};

class Rhomb {
    virtual void draw() {
        std::cerr << "in draw in Rhomb\n";
    }

    virtual void move(int x, int y) {
        this->center_.x += x;
        this->center_.y += y;
        std::cerr << "in move in Rhomb\n";
    }
    double side_;
    Point center_;
};

void drawShapes(Shape** shapes, int n) {
    for (int i = 0; i < n; ++i) {
        struct Shape* s = shapes[i];
        s->draw();
    }
}

void moveShapes(Shape** shapes, int n, int x, int y) {
    for (int i = 0; i < n; ++i) {
        struct Shape* s = shapes[i];
        s->move(x, y);
    }
}

int main() {
    Shape* shapes[4];
    shapes[0] = (Shape*)new Circle;
    shapes[1] = (Shape*)new Square;
    shapes[2] = (Shape*)new Square;
    shapes[3] = (Shape*)new Circle;
    shapes[4] = (Shape*)new Rhomb;

    drawShapes(shapes, 5);
    moveShapes(shapes, 5, 1, 2);
}