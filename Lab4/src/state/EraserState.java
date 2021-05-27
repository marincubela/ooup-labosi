package state;

import graphics.GraphicalObject;
import model.DocumentModel;
import renderer.Renderer;
import utils.Point;

import java.util.ArrayList;
import java.util.List;

public class EraserState implements State {
    List<Point> points = new ArrayList<>();
    DocumentModel model;

    public EraserState(DocumentModel model) {
        this.model = model;
    }


    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        points.add(mousePoint);
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        points.add(mousePoint);

        points.forEach(p -> {
            GraphicalObject o = model.findSelectedGraphicalObject(p);
            if (o != null)
                model.removeGraphicalObject(o);
        });
        points.clear();
        model.notifyListeners();
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        points.add(mousePoint);
        model.notifyListeners();
    }

    @Override
    public void keyPressed(int keyCode) {

    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        for (int i = 1; i < points.size(); i++) {
            r.drawLine(points.get(i), points.get(i - 1));
        }
    }

    @Override
    public void afterDraw(Renderer r) {

    }

    @Override
    public void onLeaving() {

    }
}
