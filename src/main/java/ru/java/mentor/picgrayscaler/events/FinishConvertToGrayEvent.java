package ru.java.mentor.picgrayscaler.events;

import org.springframework.context.ApplicationEvent;

public class FinishConvertToGrayEvent extends AbstractImageProcessingEvent {
    public FinishConvertToGrayEvent(Object source, String filename) {
        super(source, filename);
    }
}
