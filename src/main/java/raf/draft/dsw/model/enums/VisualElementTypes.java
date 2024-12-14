package raf.draft.dsw.model.enums;

public enum VisualElementTypes {
    BATH_TUB, BED, CLOSET, TABLE, WASHING_MACHINE, BOILER, DOOR, TOILET, SINK, WALL;

    @Override
    public String toString(){
        String[] split = this.name().split("_");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            s.append(split[i].toLowerCase());
            if (i < split.length - 1) s.append(" ");
        }
        return s.toString();
    }
}
