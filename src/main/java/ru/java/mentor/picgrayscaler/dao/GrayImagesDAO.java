package ru.java.mentor.picgrayscaler.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.java.mentor.picgrayscaler.entity.GrayImage;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface GrayImagesDAO extends CrudRepository<GrayImage, Long> {
    public Optional<GrayImage> findById(Long id);
    public List<GrayImage> findAll();

}
