package raf.draft.dsw.model.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import java.awt.*;
import java.util.Random;

@Getter @Setter
public class Building extends DraftNodeComposite {
    private String name;
    private Color color;

    public Building(String name, DraftNode parent){
        super(parent);
        this.name = name;
        color = new Color(new Random().nextInt(0xFFFFFF));
    }
}
