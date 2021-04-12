import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DatotecniIzvor implements Izvor {
    List<String> allNumbers;
    int index;

    public DatotecniIzvor(String filename) {
        index = 0;
        try {
            allNumbers = Files.readAllLines(Path.of(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNextNumber() {
        return Integer.parseInt(allNumbers.get(index++));
    }
}
