import java.util.ArrayList;
import java.util.List;

public class Cell implements CellListener {
    private String exp;
    private double value;
    private CellListener listener;
    private List<CellListener> listeners;

    public Cell(String exp, double value) {
        this.exp = exp;
        this.value = value;
        listeners = new ArrayList<>();
    }

    public Cell() {
        this("0", 0);
    }

    public void setContent(String content) {
        this.exp = content;
    }

    public String getContent() {
        return exp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void addListener(CellListener l) {
        listeners.add(l);
    }

    public void removeListener(CellListener l) {
        listeners.add(l);
    }

    public void notifyAllListeners(Sheet s) {
        listeners.forEach(l -> l.update(s));
    }

    public void update(Sheet sheet) {
        sheet.evaluate(this);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
