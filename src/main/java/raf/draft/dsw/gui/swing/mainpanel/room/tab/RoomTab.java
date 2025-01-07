package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.PixelSpaceConverter;
import raf.draft.dsw.controller.commands.AbstractCommand;
import raf.draft.dsw.controller.commands.AddCommand;
import raf.draft.dsw.controller.commands.CommandManager;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.controller.states.StateManager;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.AbstractPainter;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.PainterFactory;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.SelectionPainter;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Vector;

public class RoomTab extends JPanel implements ISubscriber {

    private static final int PADDING = 10;
    @Getter
    private final DraftNodeDTO room;
    private final Vector<AbstractPainter> painters;
    @Getter
    private final Vector<VisualElement> selection;
    @Getter
    private SimpleRectangle selectionRectangle;
    @Getter
    private final AffineTransform f;
    @Getter @Setter
    private double zoomFactor;
    @Getter
    private final PixelSpaceConverter converter;
    @Getter
    private final StateManager stateManager;
    @Getter
    private final CommandManager commandManager;

    public RoomTab(DraftNodeDTO room){
        this.room = room;
        painters = new Vector<>();
        selection = new Vector<>();
        f = AffineTransform.getTranslateInstance(PADDING, PADDING);
        zoomFactor = 1;
        converter = new PixelSpaceConverter(this);
        stateManager = new StateManager(this);
        commandManager = new CommandManager();
        ApplicationFramework.getInstance().getRepository().addSubscriber(room.id(), this, EventTypes.CHILD_ADDED, EventTypes.CHILD_REMOVED, EventTypes.VISUAL_ELEMENT_EDITED);
        updateElements();
        setKeyBindings();
    }

    public void setKeyBindings(){
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;

        String ctrl_z = "ctrl+z", ctrl_y = "ctrl+y", ctrl_c = "ctrl+c", ctrl_v = "ctrl+v";
        getInputMap(condition).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), ctrl_z);
        getInputMap(condition).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), ctrl_y);
        getInputMap(condition).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), ctrl_c);
        getInputMap(condition).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), ctrl_v);
        getActionMap().put(ctrl_z, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.undo();
                setSelectionRectangle(null);
            }
        });
        getActionMap().put(ctrl_y, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.redo();
                setSelectionRectangle(null);
            }
        });
        getActionMap().put(ctrl_c, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selection != null && !selection.isEmpty())
                    MainFrame.getInstance().getRoomViewController().setClipboard(selection);
            }
        });
        getActionMap().put(ctrl_v, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paste();
            }
        });
    }

    private void paste(){
        Vector<VisualElement> clipboard = MainFrame.getInstance().getRoomViewController().getClipboard();
        if (clipboard != null && !clipboard.isEmpty()) {
            Point2D p = Geometry.getRectangleHull(clipboard).getLocation();
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(mouse, this);
            Point2D q;
            try {
                q = f.inverseTransform(mouse, null);
            } catch (NoninvertibleTransformException exception){
                return;
            }
            q = converter.pointFromPixelSpace(q);
            Vector<VisualElement> v = new Vector<>();
            for (VisualElement e : clipboard){
                VisualElement clone = (VisualElement)e.clone();
                ApplicationFramework.getInstance().getRepository().addChild(room.id(), clone.getId());
                clone.translate(q.getX() - p.getX(), q.getY() - p.getY());
                v.add(clone);
            }
            AbstractCommand command = new AddCommand(v, room.id());
            commandManager.addCommand(command);
        }
    }

    public void updateElements(){
        Vector<VisualElement> elements = ApplicationFramework.getInstance().getRepository().getVisualElements(room.id());
        painters.clear();
        if (elements != null)
            for (VisualElement e : elements)
                painters.add(PainterFactory.createPainter(e));
        repaint();
    }

    public VisualElement getElementAt(Point2D p){
        for (int i = 0; i < painters.size(); i++)
            if (Geometry.contains(painters.get(i).getElement(), converter.pointFromPixelSpace(p)))
                return painters.get(i).getElement();
        return null;
    }

    public Dimension getScreenDimension(){
        return new Dimension(getWidth() - 2* PADDING, getHeight() - 2* PADDING);
    }

    private void updateSelection(){
        selection.clear();
        if (selectionRectangle == null) return;
        for (int i = 0; i < painters.size(); i++)
            if (painters.get(i).getElement().getVisualElementType() != VisualElementTypes.WALL && Geometry.contains(selectionRectangle, painters.get(i).getElement()))
                selection.add(painters.get(i).getElement());
    }

    public void scaleSelectionRectangle(double dx, double dy){
        dx = converter.lengthFromPixelSpace(dx);
        dy = converter.lengthFromPixelSpace(dy);
        selectionRectangle.setW(selectionRectangle.getW() + dx);
        selectionRectangle.setH(selectionRectangle.getH() + dy);
        updateSelection();
        repaint();
    }

    public void setSelectionRectangle(SimpleRectangle selectionRectangle){
        this.selectionRectangle = selectionRectangle;
        updateSelection();
        repaint();
    }

    public boolean overlaps(VisualElement element){
        for (int i = 0; i < painters.size(); i++)
            if (!painters.get(i).getElement().getId().equals(element.getId()) && Geometry.overlaps(painters.get(i).getElement(), element))
                return true;
        return false;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for (AbstractPainter p : painters) {
            g.setColor(overlaps(p.getElement()) ? Color.red : Color.black);
            p.paint(g, (AffineTransform) f.clone(), converter);
        }
        if (selectionRectangle != null) (new SelectionPainter(selectionRectangle)).paint(g, (AffineTransform)f.clone(), converter);
    }

    @Override
    public void notify(EventTypes type, Object state) {
        updateElements();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return ((RoomTab)o).getRoom().equals(room);
    }
}
