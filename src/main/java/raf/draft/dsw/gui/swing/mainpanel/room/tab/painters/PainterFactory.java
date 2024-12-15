package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

public class PainterFactory {

    public static AbstractPainter createPainter(VisualElement element){
        return switch (element.getVisualElementType()){
            case VisualElementTypes.WALL -> new WallPainter((Wall)element);
            case VisualElementTypes.BATH_TUB -> new BathTubPainter((RectangularVisualElement)element);
            case VisualElementTypes.BOILER -> new BoilerPainter((CircularVisualElement)element);
            default -> null;
        };
    }
}
