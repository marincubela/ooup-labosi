package percentile;

import java.util.ArrayList;
import java.util.List;

public class InterpolatedPercentile implements Percentile {
    @Override
    public int getPercentile(List<Integer> numbers, int p) {
        List<Integer> sorted = new ArrayList<>(numbers);
        sorted.sort(Integer::compare);

        int n = sorted.size();
        int pMin = (int) (100.0 * 0.5 / n + 0.5);
        int pMax = (int) (100.0 * (n - 0.5) / n + 0.5);

        if (p <= pMin) {
            return sorted.get(0);
        } else if (p > pMax) {
            return sorted.get(sorted.size() - 1);
        }

        int p1 = pMin, p2 = pMin;

        for (int i = 1; i < n; i++) {
            p1 = p2;
            p2 = (int) (100.0 * (i + 0.5) / n + 0.5);

            if (p2 == p) return sorted.get(i);

            if (p < p2) {
                return (int) (sorted.get(i - 1) + n * (p - p1) * (sorted.get(i) - sorted.get(i - 1)) / 100 + 0.5);
            }
        }

        return 0;
    }
}
