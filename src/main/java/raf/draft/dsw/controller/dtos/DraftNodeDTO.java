package raf.draft.dsw.controller.dtos;

import java.awt.*;

public record DraftNodeDTO(Integer id, DraftNodeTypes type, String name, Color color){}
