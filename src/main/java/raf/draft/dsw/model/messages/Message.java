package raf.draft.dsw.model.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @AllArgsConstructor
public class Message {
    private String text;
    private MessageTypes type;
    private LocalDateTime timestamp;
}
