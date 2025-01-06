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

@DraftNodeSubType("Closet")
public class Closet extends RectangularElement {
    @JsonCreator
    public Closet(@JsonProperty("transform") AffineTransform transform, @JsonProperty("name") String name){
        super(transform, name);
    }

    public Closet(double w, double h, Point2D location, Integer id){
        super(w, h, location, id);
    }

    public Closet(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.CLOSET;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Closet(transform, id);
    }
}
