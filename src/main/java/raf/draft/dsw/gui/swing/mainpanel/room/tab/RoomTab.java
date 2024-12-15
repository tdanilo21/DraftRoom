package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import lombok.Getter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.AbstractPainter;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.PainterFactory;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Vector;

public class RoomTab extends JPanel {

    private static final int padding = 10;
    @Getter
    private final DraftNodeDTO room;
    private final Vector<AbstractPainter> painters;
    @Getter
    private Vector<VisualElement> selection;
    @Getter
    private SimpleRectangle selectionRectangle;
    private final AffineTransform f;

    public RoomTab(DraftNodeDTO room){
        this.room = room;
        painters = new Vector<>();
        selection = new Vector<>();
        f = AffineTransform.getTranslateInstance(padding, padding);
        updateElements();
        setBackground(Color.WHITE);
    }

    public void updateElements(){
        Vector<VisualElement> elements = ApplicationFramework.getInstance().getRepository().getVisualElements(room.id());
        painters.clear();
        for (VisualElement e : elements)
            painters.add(PainterFactory.createPainter(e));
        repaint();
    }

    public VisualElement getElementAt(int x, int y){
        for (AbstractPainter p : painters)
            if (p.getElement().contains(new Point(x, y)))
                return p.getElement();
        return null;
    }

    public Dimension getScreenDimension(){
        return new Dimension(getWidth() - 2*padding, getHeight() - 2*padding);
    }

    public void preConcatenateTransform(AffineTransform g){
        f.preConcatenate(g);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for (AbstractPainter p : painters)
            p.paint(g, f);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return ((RoomTab)o).getRoom().equals(room);
    }
}
