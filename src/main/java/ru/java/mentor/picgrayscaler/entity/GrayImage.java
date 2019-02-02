package ru.java.mentor.picgrayscaler.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

@Entity
@Table(name="PICTURES")
public class GrayImage {

    public Long getId() {
        return id;
    }

    public GrayImage() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrayImage(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Id
    @GeneratedValue
    @Column(name="Id", nullable=false)
    private Long id;

    @Column(name="file_name", length=255, nullable=false)
    private String fileName;

    @Column(name="content", length=10000000, nullable = false)
    private byte[] content;
}
