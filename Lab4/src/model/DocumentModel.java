package model;

import graphics.GraphicalObject;
import graphics.GraphicalObjectListener;
import utils.GeometryUtil;
import utils.Point;

import java.util.*;

public class DocumentModel {
    public final static double SELECTION_PROXIMITY = 5;

    /**
     * Collection of all graphical objects
     */
    private final List<GraphicalObject> objects = new ArrayList<>();

    /**
     * Read-only proxy of collection of graphical objects
     */
    private final List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);

    private final List<DocumentModelListener> listeners = new ArrayList<>();

    /**
     * Collection of selected objects
     */
    private final List<GraphicalObject> selectedObjects = new ArrayList<>();

    /**
     * Read-only proxy of collection of selected objects
     */
    private final List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

    /**
     * Listener that will be registered in all objects
     */
    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            notifyListeners();
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            if (go.isSelected() && !selectedObjects.contains(go)) {
                selectedObjects.add(go);
            } else if (!go.isSelected()) {
                selectedObjects.remove(go);
            }
            notifyListeners();
        }
    };

    public DocumentModel() {

    }

    /**
     * Remove all objects from model and notify listeners
     */
    public void clear() {
        for (int i = 0; i < objects.size(); i++) {
            this.removeGraphicalObject(objects.get(0));
        }
        notifyListeners();
    }

    /**
     * Add given {@link GraphicalObject} to this model.
     *
     * @param obj object to be appended to this model.
     */
    public void addGraphicalObject(GraphicalObject obj) {
        objects.add(obj);
        if (obj.isSelected()) {
            selectedObjects.add(obj);
        }
        obj.addGraphicalObjectListener(goListener);
        notifyListeners();
    }

    /**
     * Remove given {@link GraphicalObject} from this model.
     *
     * @param obj object to be removed from this model.
     */
    public void removeGraphicalObject(GraphicalObject obj) {
        obj.removeGraphicalObjectListener(goListener);
        if (obj.isSelected()) {
            selectedObjects.remove(obj);
        }
        objects.remove(obj);
        notifyListeners();
    }

    /**
     * Returns unmodifiable list of objects from model
     *
     * @return unmodifiable list of objects from model
     */
    public List<GraphicalObject> list() {
        return roObjects;
    }

    public void addDocumentModelListener(DocumentModelListener l) {
        listeners.add(l);
    }

    public void removeDocumentModelListener(DocumentModelListener l) {
        listeners.remove(l);
    }

    public void notifyListeners() {
        listeners.forEach(DocumentModelListener::documentChange);
    }

    /**
     * Returns unmodifiable list of selected objects in this model
     *
     * @return unmodifiable list of selected objects in this model
     */
    public List<GraphicalObject> getSelectedObjects() {
        return roSelectedObjects;
    }

    /**
     * Method that deselects all selected objects
     */
    public void deselectAll() {
        objects.forEach(o -> o.setSelected(false));
        notifyListeners();
    }

    public void removeSelected() {
        objects.removeIf(GraphicalObject::isSelected);
        selectedObjects.clear();
    }

    /**
     * Move given {@link GraphicalObject} one place higher so it is closer to the front.
     *
     * @param go object to be moved
     */
    public void increaseZ(GraphicalObject go) {
        int index = objects.indexOf(go);
        if (index >= 0 && index < objects.size() - 1) {
            GraphicalObject go2 = objects.remove(index + 1);
            objects.add(index, go2);
        }
    }

    /**
     * Move given {@link GraphicalObject} one place lower so it is further from the front.
     *
     * @param go object to be moved
     */
    public void decreaseZ(GraphicalObject go) {
        int index = objects.indexOf(go);
        if (index > 0 && index < objects.size()) {
            GraphicalObject go2 = objects.remove(index - 1);
            objects.add(index, go2);
        }
    }

    /**
     * Returns closest object to given point if its distance is smaller than SELECTION_PROXIMITY, otherwise null
     *
     * @param mousePoint given point
     * @return object closest to given point or null
     */
    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        Optional<GraphicalObject> opt = objects.stream().min(Comparator.comparingDouble(g -> g.selectionDistance(mousePoint)));

        if (opt.isPresent() && opt.get().selectionDistance(mousePoint) < SELECTION_PROXIMITY) {
            return opt.get();
        }

        return null;
    }

    /**
     * Returns index of closest hotpoint of a given object if it is in selection proximity, else -1
     *
     * @param object object whose selected hotpoints are testet
     * @param mousePoint given point
     * @return index of closest hotpoint to given point or -1 if there is no such one
     */
    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        int index = -1;
        double d;
        double mind = 0;
        for (int i = 0; i < object.getNumberOfHotPoints(); i++) {
            d = GeometryUtil.distanceFromPoint(object.getHotPoint(i), mousePoint);
            if (d < SELECTION_PROXIMITY) {
                if (index == -1) {
                    mind = d;
                    index = i;
                    continue;
                }
                if (d < mind) {
                    mind = d;
                }
            }
        }

        return index;
    }
}
