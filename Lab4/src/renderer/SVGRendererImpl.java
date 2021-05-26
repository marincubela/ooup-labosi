package renderer;

import utils.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SVGRendererImpl implements Renderer {
    private List<String> lines = new ArrayList<>();
    private String fileName;

    public SVGRendererImpl(String fileName) {
        this.fileName = fileName;
        lines.add("<svg xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    public void close() throws IOException {
        lines.add("</svg>");
        Files.write(Path.of(fileName), lines);
    }

    @Override
    public void drawLine(Point s, Point e) {
        String line = "<line ";
        line += " x1=\"" + s.getX();
        line += "\" y1=\"" + s.getY();
        line += "\" x2=\"" + e.getX();
        line += "\" y2=\"" + e.getY();
        line += "\" style=\"stroke:#0000ff;\"/>";

        lines.add(line);
    }

    @Override
    public void fillPolygon(Point[] points) {
        // Dodaj u lines redak koji definira popunjeni poligon:
        // <polygon points="..." style="stroke: ...; fill: ...;" />
        String line = "<polygon points=\"";

        for(Point p : points) {
            line += " " + p.getX() + "," + p.getY();
        }

        line += "\" style=\"stroke:#ff0000; fill:#0000ff;\"/>";
        lines.add(line);
    }
}