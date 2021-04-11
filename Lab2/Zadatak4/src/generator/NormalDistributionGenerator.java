package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NormalDistributionGenerator implements Generator {
    private double mean;
    private double variance;
    private int size;

    public NormalDistributionGenerator(double mean, double variance, int size) {
        this.mean = mean;
        this.variance = variance;
        this.size = size;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < size; i++) {
            numbers.add((int) (random.nextGaussian() * variance + mean));
        }

        return numbers;
    }
}
