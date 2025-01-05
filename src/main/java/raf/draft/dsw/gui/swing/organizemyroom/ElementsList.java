package raf.draft.dsw.gui.swing.organizemyroom;

import lombok.Getter;
import raf.draft.dsw.gui.swing.CloseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

class ElementView extends JPanel{
    @Getter
    private final Element element;
    private final CloseButton closeButton;

    public ElementView(Element element){
        this.element = element;
        setLayout(new FlowLayout());

        add(new JLabel(STR."\{element.type().toString()} (\{element.w()}x\{element.h()})"));
        closeButton = new CloseButton();
        add(closeButton);
    }

    public void addOnClose(ActionListener onClose){
        closeButton.addActionListener(onClose);
    }
}

public class ElementsList extends JPanel {
    private final Vector<ElementView> list;

    public ElementsList(){
        list = new Vector<>();
        setLayout(new FlowLayout());
    }

    public void addElement(Element element){
        ElementView elementView = new ElementView(element);
        elementView.addOnClose(e -> {
            list.remove(elementView);
            remove(elementView);
            repaint();
            revalidate();
        });
        list.add(elementView);
        add(elementView);
        repaint();
        revalidate();
    }

    public Vector<Element> getElements(){
        Vector<Element> v = new Vector<>();
        for (ElementView elementView : list)
            v.add(elementView.getElement());
        return v;
    }
}
