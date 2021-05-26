package graphicals;

public interface GraphicalObjectListener {
    /**
     * Call when anything on object updates
     *
     * @param go updated object
     */
    void graphicalObjectChanged(GraphicalObject go);

    /**
     * Call when selection status of object changes (not his hotpoints)
     *
     * @param go updated object
     */
    void graphicalObjectSelectionChanged(GraphicalObject go);

}