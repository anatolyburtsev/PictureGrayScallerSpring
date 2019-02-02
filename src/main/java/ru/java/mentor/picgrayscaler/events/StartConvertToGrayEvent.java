package ru.java.mentor.picgrayscaler.events;

import org.springframework.context.ApplicationEvent;

public class StartConvertToGrayEvent extends AbstractImageProcessingEvent{
    public StartConvertToGrayEvent(Object source, String filename) {
        super(source, filename);
    }
}
