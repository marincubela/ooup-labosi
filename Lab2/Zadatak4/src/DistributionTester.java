import generator.FibonacciGenerator;
import generator.Generator;
import generator.NormalDistributionGenerator;
import generator.SequentialGenerator;
import percentile.ClosestPercentile;
import percentile.InterpolatedPercentile;
import percentile.Percentile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DistributionTester {
    private List<Integer> numbers;
    private Generator generator;
    private Percentile percentile;

    public List<Integer> getNumbers() {
        return numbers;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public Percentile getPercentile() {
        return percentile;
    }

    public void setPercentile(Percentile percentile) {
        this.percentile = percentile;
    }

    public DistributionTester(Generator generator, Percentile percentile) {
        this.generator = generator;
        this.percentile = percentile;
    }

    public void generateNewNumbers() {
        this.numbers = this.generator.generate();
    }

    public List<Integer> getPercentiles() {
        if(numbers == null) {
            throw new IllegalStateException("Numbers not generated yet!");
        }

        List<Integer> percentiles = new ArrayList<>();

        for(int i = 10; i < 100; i += 10) {
            percentiles.add(this.percentile.getPercentile(numbers, i));
        }


        return percentiles;
    }

    public static void main(String[] args) {
        Generator gen = new SequentialGenerator(1, 15, 1);
        Generator gen2 = new NormalDistributionGenerator(50, 10, 20);
        Generator gen3 = new FibonacciGenerator(20);
        Percentile perc = new ClosestPercentile();
        Percentile perc2 = new InterpolatedPercentile();

        DistributionTester dt = new DistributionTester(gen, perc);
        dt.generateNewNumbers();
        printResult(dt);

        dt.setPercentile(perc2);
        printResult(dt);

        dt.setGenerator(gen2);
        dt.generateNewNumbers();
        dt.setPercentile(perc);
        printResult(dt);

        dt.setPercentile(perc2);
        printResult(dt);

        dt.setGenerator(gen3);
        dt.generateNewNumbers();
        dt.setPercentile(perc);
        printResult(dt);

        dt.setPercentile(perc2);
        printResult(dt);
    }

    private static void printResult(DistributionTester dt) {
        System.out.println(dt.getNumbers().stream().map(i -> i.toString()).collect(Collectors.joining(", ")));
        System.out.println(dt.getPercentiles().stream().map(i -> i.toString()).collect(Collectors.joining(", ")));
    }


}
