public class Main {

    public static void main(String[] args) {
        SlijedBrojeva sb = new SlijedBrojeva(new DatotecniIzvor("Brojevi.txt"));
        sb.dodajPromatraca(new PromatracDatoteka());
        sb.dodajPromatraca(new PromatracMedijan());
        sb.dodajPromatraca(new PromatracProsjek());
        sb.dodajPromatraca(new PromatracSuma());
        sb.dodajPromatraca(l -> {
            System.out.println("Max: " + l.stream().max(Integer::compareTo).get());
        });

        sb.kreni();
    }
}
