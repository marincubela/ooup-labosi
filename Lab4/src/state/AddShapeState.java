package state;

import graphics.GraphicalObject;
import model.DocumentModel;
import utils.Point;

public class AddShapeState extends IdleState {
    private final GraphicalObject prototype;
    private final DocumentModel model;

    public AddShapeState(DocumentModel model, GraphicalObject prototype) {
        this.model = model;
        this.prototype = prototype;
    }
    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject copy = prototype.duplicate();
        copy.translate(mousePoint);
        model.addGraphicalObject(copy);
    }
}
