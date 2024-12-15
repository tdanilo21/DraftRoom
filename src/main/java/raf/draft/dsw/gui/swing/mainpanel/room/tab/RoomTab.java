package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.states.DeleteState;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.AbstractPainter;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.PainterFactory;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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
    @Getter
    private final AffineTransform f;
    @Getter @Setter
    private double zoomFactor;

    public RoomTab(DraftNodeDTO room){
        this.room = room;
        painters = new Vector<>();
        selection = new Vector<>();
        f = AffineTransform.getTranslateInstance(padding, padding);
        zoomFactor = 1;
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

    public VisualElement getElementAt(Point2D p){
        for (int i = 0; i < painters.size(); i++)
            if (painters.get(i).getElement().containsInPixelSpace(p))
                return painters.get(i).getElement();
        return null;
    }

    public Dimension getScreenDimension(){
        return new Dimension(getWidth() - 2*padding, getHeight() - 2*padding);
    }

    public void select(VisualElement element){
        selection = new Vector<>(){{add(element);}};
    }

    private void updateSelection(){
        selection.clear();
        if (selectionRectangle == null) return;
        for (int i = 0; i < painters.size(); i++)
            if (selectionRectangle.contains(painters.get(i).getElement()))
                selection.add(painters.get(i).getElement());
        System.out.println(selection);
    }

    public void scaleSelectionRectangle(double dx, double dy){
        selectionRectangle.setW(selectionRectangle.getWInPixelSpace() + dx);
        selectionRectangle.setH(selectionRectangle.getHInPixelSpace() + dy);
        updateSelection();
        repaint();
    }

    public void setSelectionRectangle(SimpleRectangle selectionRectangle){
        this.selectionRectangle = selectionRectangle;
        updateSelection();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for (AbstractPainter p : painters)
            p.paint(g, (AffineTransform) f.clone());
        if (selectionRectangle != null) paintSelection(g);
    }

    private void paintSelection(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Point2D a = selectionRectangle.getLocationInPixelSpace();
        double w = selectionRectangle.getWInPixelSpace();
        double h = selectionRectangle.getHInPixelSpace();
        Point2D b = new Point2D.Double(a.getX()+w, a.getY()+h);
        f.transform(a, a);
        f.transform(b, b);
        int x = (int)Math.round(Math.min(a.getX(), b.getX()));
        int y = (int)Math.round(Math.min(a.getY(), b.getY()));
        int w1 = (int)Math.round(Math.abs(b.getX() - a.getX()));
        int h1 = (int)Math.round(Math.abs(b.getY() - a.getY()));
        g2.setStroke(new BasicStroke());
        g2.setColor(Color.blue);
        g2.drawRect(x, y, w1, h1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return ((RoomTab)o).getRoom().equals(room);
    }
}
