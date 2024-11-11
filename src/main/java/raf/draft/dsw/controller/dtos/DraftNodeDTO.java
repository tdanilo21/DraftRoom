package raf.draft.dsw.controller.dtos;

import java.awt.*;

public record DraftNodeDTO(
        Integer id,
        DraftNodeTypes type,
        String name,
        String author,
        Color color,
        Integer parent
){
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return id.equals(((DraftNodeDTO)o).id);
    }
}
