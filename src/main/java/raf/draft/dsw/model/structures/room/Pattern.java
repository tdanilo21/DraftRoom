package raf.draft.dsw.model.structures.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Room;

import java.util.Vector;

@Getter
public class Pattern {
    @JsonProperty("w")
    private final double w;
    @JsonProperty("h")
    private final double h;
    @JsonProperty("children")
    private final Vector<DraftNode> children;

    @JsonCreator
    public Pattern(@JsonProperty("w") double w, @JsonProperty("h") double h, @JsonProperty("children") Vector<DraftNode> children){
        this.w = w;
        this.h = h;
        this.children = children;
    }

    public Pattern(Room room){
        this.w = room.getW() - 2*room.getWallWidth();
        this.h = room.getH() - 2*room.getWallWidth();
        this.children = new Vector<>(room.getChildren());
    }
}
