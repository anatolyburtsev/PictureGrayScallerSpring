package ru.java.mentor.picgrayscaler.events;

import org.springframework.context.ApplicationEvent;

public class SavedToDbEvent extends AbstractImageProcessingEvent{
    public SavedToDbEvent(Object source, String filename) {
        super(source, filename);
    }
}
