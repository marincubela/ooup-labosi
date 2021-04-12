import java.util.ArrayList;
import java.util.List;

public class SlijedBrojeva {
    private List<Integer> brojevi;
    private Izvor izvor;
    private List<Promatrac> promatraci;

    public SlijedBrojeva(Izvor izvor) {
        this.izvor = izvor;
        brojevi = new ArrayList<>();
        promatraci = new ArrayList<>();
    }

    public void kreni() {
        int n;
        while((n = izvor.getNextNumber()) != -1) {
            brojevi.add(n);
            System.out.println(n);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obavijestiPromatrace();
        }
    }

    public void dodajPromatraca(Promatrac p) {
        promatraci.add(p);
    }

    public void ukloniPromatraca(Promatrac p) {
        promatraci.remove(p);
    }

    public void obavijestiPromatrace() {
        promatraci.forEach(p -> p.azuriran(brojevi));
    }
}
