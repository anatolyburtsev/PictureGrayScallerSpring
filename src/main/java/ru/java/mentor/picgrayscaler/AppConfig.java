package ru.java.mentor.picgrayscaler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${dir_to_monitor}")
    private String dirToMonitor;

    public String getOutputDir() {
        return outputDir;
    }

    @Value("${output_dir}")
    private String outputDir;

    public String getDirToMonitor() {
        return dirToMonitor;
    }

}
