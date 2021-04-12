package percentile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClosestPercentile implements Percentile {
    @Override
    public int getPercentile(List<Integer> numbers, int p) {
        List<Integer> sorted = new ArrayList<>(numbers);
        sorted.sort(Integer::compare);

        int index = -1 + (int) Math.ceil(p * sorted.size() / 100.0);
        return sorted.get(index);
    }
}
