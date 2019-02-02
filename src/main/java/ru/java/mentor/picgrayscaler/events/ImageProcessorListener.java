package ru.java.mentor.picgrayscaler.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ImageProcessorListener {

    @EventListener
    public void onApplicationEvent(StartConvertToGrayEvent event) {
        System.out.println("Caught event: started processing image: " + event.getFilename());
    }

    @EventListener
    public void onApplicationEvent(FinishConvertToGrayEvent event) {
        System.out.println("Caught event: finished processing image: " + event.getFilename());
    }

    @EventListener
    public void onApplicationEvent(SavedToDbEvent event) {
        System.out.println("Caught event: image saved to db:" + event.getFilename());
    }
}
