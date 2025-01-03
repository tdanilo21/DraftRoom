package raf.draft.dsw.controller;

import raf.draft.dsw.AppCore;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class PixelSpaceConverter implements ISubscriber {
    private final RoomTab roomTab;
    private AffineTransform transform, itransform;

    public PixelSpaceConverter(RoomTab roomTab){
        this.roomTab = roomTab;
        ApplicationFramework.getInstance().getRepository().addSubscriber(this, EventTypes.VISUAL_ELEMENT_EDITED);
        updateTransforms();
    }

    private record Parameters(double scaleFactor, Point2D location){}

    private Parameters getParameters(){
        double screenW = roomTab.getScreenDimension().width, screenH = roomTab.getScreenDimension().height;
        Wall room = ApplicationFramework.getInstance().getRepository().getRoom(roomTab.getRoom().id());
        double roomW = room.getW(), roomH = room.getH();
        double scaleFactor = Math.min(screenW / roomW, screenH / roomH);
        Point2D location = new Point2D.Double((screenW - roomW * scaleFactor) / 2, (screenH - roomH * scaleFactor) / 2);
        return new Parameters(scaleFactor, location);
    }

    public void updateTransforms(){
        if (ApplicationFramework.getInstance().getRepository().isRoomInitialized(roomTab.getRoom().id())) {
            Parameters params = getParameters();
            transform = AffineTransform.getScaleInstance(params.scaleFactor, params.scaleFactor);
            transform.preConcatenate(AffineTransform.getTranslateInstance(params.location.getX(), params.location.getY()));
            itransform = AffineTransform.getTranslateInstance(-params.location.getX(), -params.location.getY());
            itransform.preConcatenate(AffineTransform.getScaleInstance(1 / params.scaleFactor, 1 / params.scaleFactor));
        }
    }

    public double angleToPixelSpace(double alpha){
        return 2*Math.PI - alpha;
    }

    public double angleFromPixelSpace(double alpha){
        return 2*Math.PI - alpha;
    }

    public double lengthToPixelSpace(double x){
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(x, 0));
        s.transform(transform);
        return (new Vec(s.getA(), s.getB())).abs() * (x < 0 ? -1 : 1);
    }

    public double lengthFromPixelSpace(double x){
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(x, 0));
        s.transform(itransform);
        return (new Vec(s.getA(), s.getB())).abs() * (x < 0 ? -1 : 1);
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

    @Override
    public void notify(EventTypes type, Object state) {
        if (type == EventTypes.VISUAL_ELEMENT_EDITED && state instanceof Integer roomId && roomId.equals(roomTab.getRoom().id()))
            updateTransforms();
    }
}
