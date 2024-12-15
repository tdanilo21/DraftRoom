package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;

import java.awt.*;
import java.awt.geom.Point2D;

@Getter
public abstract class CircularElement extends RoomElement implements CircularVisualElement {
    protected double r;

    public CircularElement(Room room, double r, Point2D location, double angle, Integer id){
        super(room, location, angle, id);
        this.r = r;
    }

    @Override
    public double getRInPixelSpace(){
        return getRoom().toPixelSpace(r);
    }

    @Override
    public void scaleR(double lambda) {
        r *= lambda;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setR(double r) {
        this.r = r;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }
}
