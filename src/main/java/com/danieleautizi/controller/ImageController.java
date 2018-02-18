package com.danieleautizi.controller;

import com.danieleautizi.manager.ImageManager;
import com.danieleautizi.model.presentation.Image;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageManager imageManager;

    @GetMapping(value = "/{imageId}")
    public Image getImage(@PathVariable("imageId") final String imageId) {

        LOG.debug("Get image by id {} ", imageId);
        return imageManager.getImageById(imageId);
    }

    @GetMapping("/")
    public List<Image> getImages() {

        LOG.debug("Get all images. ");
        return imageManager.getImages();
    }

    @GetMapping("/active")
    public List<Image> getActiveImages() {

        LOG.debug("Get all active images. ");
        return imageManager.getActiveImages();
    }

    @PostMapping(value = "/")
    public Image create(@RequestBody final Image image) {

        LOG.debug("Create Image {} ", image);
        return imageManager.create(image);
    }

    @PostMapping(value = "/bulk")
    public List<Image> create(@RequestBody final List<Image> images) {

        LOG.debug("Create Images: {} ", images);
        return imageManager.create(images);
    }

    @PutMapping(value = "/")
    public void update(@RequestBody final Image image) {

        LOG.debug("Update Image: {} ", image);
        imageManager.update(image);
    }

    @DeleteMapping(value = "/{imageId}")
    public void delete(@PathVariable("imageId") final String imageId) {

        LOG.debug("Delete Image by id {} ", imageId);
        imageManager.delete(imageId);
    }

}
