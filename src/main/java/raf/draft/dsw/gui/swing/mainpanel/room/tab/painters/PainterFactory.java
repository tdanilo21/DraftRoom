package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

public class PainterFactory {

    public static AbstractPainter createPainter(VisualElement element){
        return switch (element.getVisualElementType()){
            case VisualElementTypes.WALL -> new WallPainter((Wall)element);
            default -> null;
        };
    }
}
