package com.danieleautizi.manager;

import static com.danieleautizi.utility.MongoUtils.stringToObject;
import static com.danieleautizi.utility.ImageConverter.entitiesToPresentation;
import static com.danieleautizi.utility.ImageConverter.entityToPresentation;
import static com.danieleautizi.utility.ImageConverter.presentationToEntity;

import com.danieleautizi.exception.BadRequestException;
import com.danieleautizi.exception.NotFoundException;
import com.danieleautizi.model.presentation.Image;
import com.danieleautizi.repository.ImageRepository;
import com.danieleautizi.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageManagerImpl implements ImageManager {

    @NonNull
    private ImageRepository imageRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String SKILL_NOT_FOUND_MESSAGE = "image.error.notfound";
    public static final String SKILL_BAD_REQUEST_MESSAGE = "image.error.badrequest";

    /**
     * Find an Image by its id
     * @param imageId
     * @return Image
     */
    @Override
    public Image getImageById(final String imageId) {

        return entityToPresentation(imageRepository.findOne(stringToObject(imageId)));
    }

    /**
     * Find Images by a bunch of ids
     * @param imageIds
     * @return List<Image>
     */
    @Override
    public List<Image> getImageByIds(final List<String> imageIds) {

        return entitiesToPresentation(imageRepository.findImagesByIdIn(stringToObject(imageIds)));
    }

    /**
     * Find all Images
     * @return List<Image>
     */
    @Override
    public List<Image> getImages() {

        return entitiesToPresentation(imageRepository.findAll());
    }

    /**
     * Find all active Images
     * @return List<Image>
     */
    @Override
    public List<Image> getActiveImages() {

        return entitiesToPresentation(imageRepository.findImagesByActive(true));
    }

    /**
     * Create a new Image
     * @param image
     * @return Image created
     */
    @Override
    public Image create(final Image image) {

        val imageEntity = imageRepository.save(presentationToEntity(image));
        return entityToPresentation(imageEntity);
    }

    /**
     * Create new Images
     * @param images
     * @return List<Image> created
     */
    public List<Image> create(final List<Image> images) {

        return images.stream()
                         .map(image -> create(image))
                         .collect(Collectors.toList());
    }

    /**
     * Update an Image
     * @param image
     * @return Image updated
     */
    public Image update(final Image image) {

        if (image == null) {

            throw new BadRequestException(personalDataUtil.getMessage(SKILL_BAD_REQUEST_MESSAGE, image));
        }

        if (getImageById(image.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(SKILL_NOT_FOUND_MESSAGE, image.getId()));
        }

        val imageEntity = presentationToEntity(image);
        return entityToPresentation(imageRepository.save(imageEntity));
    }

    /**
     * Delete an existing Image by its id
     * @param imageId
     */
    public void delete(final String imageId) {

        if (getImageById(imageId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(SKILL_NOT_FOUND_MESSAGE, imageId));
        }

        imageRepository.delete(stringToObject(imageId));
    }

}
