package generator;

import generator.Generator;

import java.util.ArrayList;
import java.util.List;

public class SequentialGenerator implements Generator {
    private int start;
    private int end;
    private int step;

    public SequentialGenerator(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> numbers = new ArrayList<>();

        for(int i = start; i <= end; i += step)
            numbers.add(i);

        return numbers;
    }
}
