package ru.java.mentor.picgrayscaler.events;

import org.springframework.context.ApplicationEvent;

public class FinishConvertToGrayEvent extends ApplicationEvent {
    private final String filename;

    public String getFilename() {
        return filename;
    }

    public FinishConvertToGrayEvent(Object source, String filename) {
        super(source);
        this.filename = filename;
    }
}
