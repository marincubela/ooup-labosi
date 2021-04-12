import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sheet {
    private Cell[][] sheet;

    public Sheet(int rows, int columns) {
        sheet = new Cell[rows][columns];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                sheet[i][j] = new Cell();
            }
        }
    }

    public Cell cell(String ref) {
        int row = ref.charAt(0) - 'A';
        int column = -1 + Integer.parseInt(ref.substring(1));

        return sheet[row][column];
    }

    public void set(String ref, String content) {
        Cell cell = cell(ref);
        getrefs(cell).forEach(c -> c.removeListener(cell));
        cell.setContent(content);
        getrefs(cell).forEach(c -> c.addListener(cell));
        if(isCircular(cell)) {
            throw new RuntimeException("Fatal error");
        }

        evaluate(cell);
    }

    private boolean isCircular(Cell cell) {
        for(Cell c : getrefs(cell)) {
            if(isDependent(c, cell)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDependent(Cell c1, Cell c2) {
        if(getrefs(c1).contains(c2)) return true;

        for(Cell c : getrefs(c1)) {
            if(isDependent(c, c2)) {
                return true;
            }
        }
        return false;
    }

    public List<Cell> getrefs(Cell cell) {
        String[] split = cell.getContent().split("\\+");
        List<Cell> cells = Arrays.stream(split).filter(s -> Character.isLetter(s.charAt(0))).map(s -> cell(s)).collect(Collectors.toList());
        return cells;
    }

    public void evaluate(Cell cell) {
        String[] split = cell.getContent().split("\\+");

        double value = 0;
        for(String s : split) {
            if(Character.isLetter(s.charAt(0))) {
                value += cell(s).getValue();
            } else {
                value += Double.parseDouble(s);
            }
        }

        cell.setValue(value);
        cell.notifyAllListeners(this);
    }

    public void print() {
        for(int i = 0; i < this.sheet.length; i++) {
            for(int j = 0; j < this.sheet[i].length; j++) {
                System.out.format("%15s", sheet[i][j]);
            }
            System.out.format("\n");
        }
    }

    public static void main(String[] args) {
        Sheet s = new Sheet(5, 5);

        s.set("A1","2");
        s.set("A2","5");
        s.set("A3","A1+A2");
        s.print();

        s.set("A1","4");
        s.set("A4","A1+A3");
        s.print();

        try {
            s.set("A1", "A3");
        } catch (RuntimeException e) {
            System.out.println("Circular dependency not allowed");
        }
        s.print();
    }
}
