package raf.draft.dsw.model.messages;

public enum MessageTypes {
    ERROR, WARNING, NOTIFICATION;
    @Override
    public String toString(){
        return this.name();
    }
}
