import java.util.InputMismatchException;
import java.util.Scanner;

public class TipkovnickiIzvor implements Izvor {
    private Scanner sc;

    public TipkovnickiIzvor() {
        sc = new Scanner(System.in);
    }

    @Override
    public int getNextNumber() {
        try {
            int n = sc.nextInt();
            if (n == -1) {
                sc.close();
            }
            return n;
        } catch (InputMismatchException e) {
            System.out.println("Ocekujem pozitivan cijeli broj!");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
