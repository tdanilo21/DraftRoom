package raf.draft.dsw.controller;

import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class PixelSpaceConverter implements ISubscriber {
    private final RoomTab roomTab;
    private AffineTransform transform, itransform;

    public PixelSpaceConverter(RoomTab roomTab){
        this.roomTab = roomTab;
        updateTransform();
        updateITransform();
    }

    private record Parameters(double scaleFactor, Point2D location){}

    private Parameters getParameters(){
        double screenW = roomTab.getScreenDimension().width, screenH = roomTab.getScreenDimension().height;
        Wall room = ApplicationFramework.getInstance().getRepository().getRoom(roomTab.getRoom().id());
        double roomW = room.getW(), roomH = room.getH();
        double scaleFactor = Math.min(screenW / roomW, screenH / roomH);
        Point2D location = new Point2D.Double((screenW / scaleFactor - roomW) / 2, (screenH / scaleFactor - roomH) / 2);
        return new Parameters(scaleFactor, location);
    }

    private void updateTransform(){
        Parameters params = getParameters();
        transform = new AffineTransform(1, 0, 0, -1, 0, 0);
        transform.preConcatenate(AffineTransform.getTranslateInstance(params.location.getX(), params.location.getY()));
        transform.preConcatenate(AffineTransform.getScaleInstance(params.scaleFactor, params.scaleFactor));
    }

    private void updateITransform(){
        Parameters params = getParameters();
        itransform = AffineTransform.getScaleInstance(1 / params.scaleFactor, 1 / params.scaleFactor);
        itransform.preConcatenate(AffineTransform.getTranslateInstance(-params.location.getX(), -params.location.getY()));
        itransform.preConcatenate(new AffineTransform(1, 0, 0, -1, 0, 0));
    }

    public double angleToPixelSpace(double alpha){
        return 2*Math.PI - alpha;
    }

    public double angleFromPixelSpace(double alpha){
        return 2*Math.PI - alpha;
    }

    public double lengthToPixelSpace(double x){
        return x * getParameters().scaleFactor;
    }

    public double lengthFromPixelSpace(double x){
        return x / getParameters().scaleFactor;
    }

    public Point2D pointToPixelSpace(Point2D p){
        return transform.transform(p, null);
    }

    public Point2D pointFromPixelSpace(Point2D p){
        return itransform.transform(p, null);
    }

    public AffineTransform transformToPixelSpace(AffineTransform f){
        AffineTransform f1 = (AffineTransform)f.clone();
        f1.concatenate(itransform);
        f1.preConcatenate(transform);
        return f1;
    }

    public AffineTransform transformFromPixelSpace(AffineTransform f){
        AffineTransform f1 = (AffineTransform)f.clone();
        f1.concatenate(transform);
        f1.preConcatenate(itransform);
        return f1;
    }

    public void updateTransforms(){
        updateTransform();
        updateITransform();
    }

    @Override
    public void notify(EventTypes type, Object state) {
        if (type == EventTypes.VISUAL_ELEMENT_EDITED && state instanceof Integer roomId && roomId.equals(roomTab.getRoom().id()))
            updateTransforms();
    }
}
