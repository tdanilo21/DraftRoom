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

@DraftNodeSubType("WashingMachine")
public class WashingMachine extends RectangularElement {
    @JsonCreator
    public WashingMachine(@JsonProperty("transform") AffineTransform transform, @JsonProperty("name") String name){
        super(transform, name);
    }

    public WashingMachine(double w, double h, Point2D location, Integer id){
        super(w, h, location, id);
    }

    public WashingMachine(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.WASHING_MACHINE;
    }

    @Override
    public Prototype clone(Integer id) {
        return new WashingMachine(transform, id);
    }
}
