package ru.java.mentor.picgrayscaler.events;

import org.springframework.context.ApplicationEvent;

public abstract class AbstractImageProcessingEvent extends ApplicationEvent {
    private final String filename;

    public String getFilename() {
        return filename;
    }

    public AbstractImageProcessingEvent(Object source, String filename) {
        super(source);
        this.filename = filename;
    }
}
