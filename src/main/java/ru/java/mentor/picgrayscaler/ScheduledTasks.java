package ru.java.mentor.picgrayscaler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.java.mentor.picgrayscaler.exceptions.NotFoundImageWithId;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class ScheduledTasks {
   private ArrayList<Path> processedFiles = new ArrayList<>();
   private ArrayList<Long> savedAsBW = new ArrayList<>();

   @Autowired
   private AppConfig appConfig;

   @Autowired
   private Directory directory;

   @Autowired
   private ProcessImage processImage;

   @PostConstruct
   public void init() {
      directory.setPath(appConfig.getDirToMonitor());
   }

   @Scheduled(fixedRate = 3_000)
   public void refresh() throws IOException {
      List<Path> notProcessed = directory.getNotProcessedPathsAsList(processedFiles);
      processedFiles.addAll(notProcessed);
      notProcessed.forEach(System.out::println);
      for (Path path: notProcessed) {
         processImage.convertToGrayAndSave(path);
      }
   }

   @Scheduled(fixedRate = 7000)
   public void saveProcessedPictures() throws IOException, NotFoundImageWithId {
      processImage.saveNewImages(savedAsBW, appConfig.getOutputDir());
   }


}