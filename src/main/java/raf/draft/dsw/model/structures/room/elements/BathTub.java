package raf.draft.dsw.model.structures.room.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNodeSubType;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

@DraftNodeSubType("BathTub")
public class BathTub extends RectangularElement {
    @JsonCreator
    public BathTub(@JsonProperty("transform") AffineTransform transform, @JsonProperty("name") String name){
        super(transform, name);
    }

    public BathTub(double w, double h, Point2D location, Integer id){
        super(w, h, location, id);
    }

    public BathTub(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.BATH_TUB;
    }

    @Override
    public RoomElement clone(Integer id) {
        return new BathTub(transform, id);
    }
}
