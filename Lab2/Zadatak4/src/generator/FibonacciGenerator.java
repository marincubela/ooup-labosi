package generator;

import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator implements Generator {
    private int size;

    public FibonacciGenerator(int size) {
        this.size = size;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> numbers = new ArrayList<>();

        int a = 0;
        int b = 1;
        int old;

        for(int i = 0; i < size; i++) {
            numbers.add(b);
            old = a;
            a = b;
            b = old + a;
        }

        return numbers;
    }
}
