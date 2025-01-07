package raf.draft.dsw.model.structures.room.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNodeSubType;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

@DraftNodeSubType("Table")
public class Table extends RectangularElement {
    @JsonCreator
    public Table(@JsonProperty("transform") AffineTransform transform, @JsonProperty("name") String name){
        super(transform, name);
    }

    public Table(double w, double h, Point2D location, Integer id){
        super(Math.min(w, h), Math.max(w, h), location, id);
        if (w > h) pRotate(-Math.PI/2, new Point2D.Double(location.getX()+h/2, location.getY()+h/2));
    }

    public Table(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.TABLE;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Table(transform, id);
    }


}
