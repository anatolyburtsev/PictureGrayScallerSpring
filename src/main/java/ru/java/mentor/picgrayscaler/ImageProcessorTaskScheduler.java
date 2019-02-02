package ru.java.mentor.picgrayscaler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.java.mentor.picgrayscaler.exceptions.NotFoundImageWithId;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class ImageProcessorTaskScheduler {
    private ArrayList<Path> processedFiles = new ArrayList<>();
    private ArrayList<Long> savedAsBW = new ArrayList<>();

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private PictureProcessor pictureProcessor;

    @Scheduled(fixedRate = 3_000)
    public void refresh() throws IOException {
        List<Path> notProcessed = getNotProcessedPaths();
        processedFiles.addAll(notProcessed);
        notProcessed.forEach(System.out::println);
        for (Path path : notProcessed) {
            pictureProcessor.convertToGrayAndSave(path);
        }
    }

    @Scheduled(fixedRate = 7000)
    public void saveProcessedPictures() throws IOException, NotFoundImageWithId {
        pictureProcessor.saveNewImages(savedAsBW, appConfig.getOutputDir());
    }

    private List<Path> getNotProcessedPaths() throws IOException {
        return Files.walk(Paths.get(appConfig.getDirToMonitor()))
                .filter(Files::isRegularFile)
                .filter(p -> !processedFiles.contains(p))
                .collect(Collectors.toList());
    }


}
