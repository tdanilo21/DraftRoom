package raf.draft.dsw.model.enums;

public enum DraftNodeTypes {
    PROJECT_EXPLORER, PROJECT, BUILDING, ROOM, ROOM_ELEMENT;

    @Override
    public String toString(){
        String[] split = this.name().split("_");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            s.append(split[i].substring(0, 1).toUpperCase()).append(split[i].substring(1).toLowerCase());
            if (i < split.length - 1) s.append(" ");
        }
        return s.toString();
    }
}
