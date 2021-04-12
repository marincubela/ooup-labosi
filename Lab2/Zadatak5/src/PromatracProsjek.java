import java.util.List;

public class PromatracProsjek implements Promatrac {
    @Override
    public void azuriran(List<Integer> kolekcija) {
        if (kolekcija.size() == 0) {
            System.out.println("0");
            return;
        }
        System.out.println("Prosjek: " + kolekcija.stream().reduce(0, Integer::sum) * 1. / kolekcija.size());
    }
}
