import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PromatracDatoteka implements Promatrac {
    @Override
    public void azuriran(List<Integer> kolekcija) {
        String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        List<String> lines = kolekcija.stream().map(i -> i + " " + time).collect(Collectors.toList());
        try {
            Files.write(Path.of("Result.txt"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
