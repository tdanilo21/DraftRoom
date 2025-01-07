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
    private double scaleFactor;
    private Point2D location;

    public PixelSpaceConverter(RoomTab roomTab){
        this.roomTab = roomTab;
        ApplicationFramework.getInstance().getRepository().addSubscriber(roomTab.getRoom().id(), this, EventTypes.ROOM_DIMENSIONS_CHANGED);
    }

    private void updateParameters(){
        double screenW = roomTab.getScreenDimension().width, screenH = roomTab.getScreenDimension().height;
        Wall room = ApplicationFramework.getInstance().getRepository().getWall(roomTab.getRoom().id());
        double roomW = room.getW(), roomH = room.getH();
        scaleFactor = Math.min(screenW / roomW, screenH / roomH);
        location = new Point2D.Double((screenW - roomW * scaleFactor) / 2, (screenH - roomH * scaleFactor) / 2);
    }

    public void updateTransforms(){
        if (ApplicationFramework.getInstance().getRepository().isRoomInitialized(roomTab.getRoom().id())) {
            updateParameters();
            transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
            transform.preConcatenate(AffineTransform.getTranslateInstance(location.getX(), location.getY()));
            itransform = AffineTransform.getTranslateInstance(-location.getX(), -location.getY());
            itransform.preConcatenate(AffineTransform.getScaleInstance(1 / scaleFactor, 1 / scaleFactor));
        }
    }

    public double angleToPixelSpace(double alpha){
        return 2*Math.PI - alpha;
    }

    public double angleFromPixelSpace(double alpha){
        return 2*Math.PI - alpha;
    }

    public double lengthToPixelSpace(double x){ return x * scaleFactor; }

    public double lengthFromPixelSpace(double x){ return x / scaleFactor; }

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

    public AffineTransform getUnitPixelSpaceTransform(){
        return (AffineTransform)transform.clone();
    }

    @Override
    public void notify(EventTypes type, Object state) {
        if (type == EventTypes.ROOM_DIMENSIONS_CHANGED)
            updateTransforms();
    }
}
