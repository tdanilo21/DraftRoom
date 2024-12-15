package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.room.interfaces.*;

public class PainterFactory {

    public static AbstractPainter createPainter(VisualElement element){
        return switch (element.getVisualElementType()){
            case VisualElementTypes.WALL -> new WallPainter((Wall)element);
            case VisualElementTypes.BATH_TUB -> new BathTubPainter((RectangularVisualElement)element);
            case VisualElementTypes.BOILER -> new BoilerPainter((CircularVisualElement)element);
            case VisualElementTypes.BED -> new BedPainter((RectangularVisualElement)element);
            case VisualElementTypes.WASHING_MACHINE -> new WashingMachinePainter((RectangularVisualElement)element);
            case VisualElementTypes.CLOSET -> new ClosetPainter((RectangularVisualElement)element);
            case VisualElementTypes.TABLE -> new TablePainter((RectangularVisualElement)element);
            case VisualElementTypes.DOOR -> new DoorPainter((CircularVisualElement)element);
            case VisualElementTypes.SINK -> new SinkPainter((TriangularVisualElement)element);
            case VisualElementTypes.TOILET -> new ToiletPainter((CircularVisualElement)element);
            default -> null;
        };
    }
}
