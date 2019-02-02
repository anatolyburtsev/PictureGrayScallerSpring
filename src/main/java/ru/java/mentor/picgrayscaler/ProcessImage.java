package ru.java.mentor.picgrayscaler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.java.mentor.picgrayscaler.dao.GrayImagesDAO;
import ru.java.mentor.picgrayscaler.entity.GrayImage;
import ru.java.mentor.picgrayscaler.events.FinishConvertToGrayEvent;
import ru.java.mentor.picgrayscaler.events.SavedToDbEvent;
import ru.java.mentor.picgrayscaler.events.StartConvertToGrayEvent;
import ru.java.mentor.picgrayscaler.exceptions.NotFoundImageWithId;
import ru.java.mentor.picgrayscaler.exceptions.NotImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class ProcessImage {

    @Autowired
    private GrayImagesDAO grayImagesDAO;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Async
    void convertToGrayAndSave(Path path) throws IOException {
//        System.out.println("Start processing " + path.getFileName());
        Picture pic = new Picture(path);
        try {
            applicationEventPublisher.publishEvent(
                    new StartConvertToGrayEvent(this, path.getFileName().toString()));
            BufferedImage grayImage = pic.getGrayScaledImage();
            applicationEventPublisher.publishEvent(
                    new FinishConvertToGrayEvent(this, path.getFileName().toString())
            );
            saveImageToDB(grayImage, path);
            applicationEventPublisher.publishEvent(
                    new SavedToDbEvent(this, path.getFileName().toString())
            );
        } catch (NotImageException e) {
            System.out.println("Found not picture! " + path.getFileName());
        }
//        System.out.println("Picture processed: " + path.getFileName());
    }

    private byte[] imageToByte(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "bmp", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }

    private void saveImageToDB(BufferedImage image, Path path) throws IOException {
        GrayImage grayImage = new GrayImage(
                path.getFileName().toString(),
                imageToByte(image)
        );

        grayImagesDAO.save(grayImage);
    }

    public void saveNewImages(Collection<Long> processedImages, String dstPath) throws IOException, NotFoundImageWithId {
        List<GrayImage> allImages = grayImagesDAO.findAll();
        for (GrayImage image: allImages) {
            if (! processedImages.contains(image.getId())) {
                saveImageById(image.getId(), dstPath);
                processedImages.add(image.getId());
            }
        }
    }

    public void saveImageById(Long id, String dstPath) throws NotFoundImageWithId, IOException {
        Optional<GrayImage> grayImageOpt = grayImagesDAO.findById(id);
        if (!grayImageOpt.isPresent()) {
            throw new NotFoundImageWithId();
        }
        GrayImage grayImage = grayImageOpt.get();
        Path newFilePath = Paths.get(String.valueOf(dstPath), grayImage.getFileName());
        saveImage(
                bytesToImage(grayImageOpt.get().getContent()),
                newFilePath.toFile()
        );
    }

    private BufferedImage bytesToImage(byte[] imageInByte) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageInByte));
        return img;
    }

    private void saveImage(BufferedImage image, File path) throws IOException {
        ImageIO.write(image, "jpg", path);
    }


}
