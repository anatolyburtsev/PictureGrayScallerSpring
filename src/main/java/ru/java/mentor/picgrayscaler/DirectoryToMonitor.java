package ru.java.mentor.picgrayscaler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DirectoryToMonitor {

    @Autowired
    private AppConfig appConfig;

    @PostConstruct
    public void init() {
        this.setPath(appConfig.getDirToMonitor());
    }

    public DirectoryToMonitor(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DirectoryToMonitor() {
    }

    private String path;

    public Stream<Path> getFilePaths() throws IOException {
        return Files.walk(Paths.get(this.path)).filter(Files::isRegularFile);
    }

    public Stream<Path> getNotProcessedPaths(Collection<Path> processedPaths) throws IOException {
        return getFilePaths().filter(p -> ! processedPaths.contains(p));
    }

    public List<Path> getNotProcessedPathsAsList(Collection<Path> processedPaths) throws IOException {
        List<Path> list = getNotProcessedPaths(processedPaths).collect(Collectors.toList());
        if (list.size() < 1) {
            return Collections.emptyList();
        } else {
            return list;
        }
    }
}