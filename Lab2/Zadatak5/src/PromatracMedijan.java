import java.util.ArrayList;
import java.util.List;

public class PromatracMedijan implements Promatrac {
    @Override
    public void azuriran(List<Integer> kolekcija) {
        List<Integer> sorted = new ArrayList<>(kolekcija);
        sorted.sort(Integer::compareTo);
        double median;
        int n = sorted.size();
        if (n == 0) {
            System.out.println("NaN");
            return;
        }

        if (n % 2 == 0) {
            median = (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.;
        } else {
            median = sorted.get(n / 2);
        }
        System.out.println("Medijan: " + median);
    }
}
