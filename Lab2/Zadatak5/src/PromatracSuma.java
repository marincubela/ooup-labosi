import java.util.List;

public class PromatracSuma implements Promatrac {
    @Override
    public void azuriran(List<Integer> kolekcija) {
        System.out.println("Suma: " + kolekcija.stream().reduce(0, Integer::sum));
    }
}
