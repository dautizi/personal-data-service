package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.Image;

import java.util.List;

/**
 * Manager for {@link Image} operations.
 */
public interface ImageManager {

    /**
     * Find an Image by its id
     * @param imageId
     * @return Image
     */
    Image getImageById(final String imageId);

    /**
     * Find Images by a bunch of ids
     * @param imageIds
     * @return List<Image>
     */
    List<Image> getImageByIds(final List<String> imageIds);

    /**
     * Find all Images
     * @return List<Image>
     */
    List<Image> getImages();

    /**
     * Find all the active Images
     * @return List<Image>
     */
    List<Image> getActiveImages();

    /**
     * Create a new Image
     * @param image
     * @return Image created
     */
    Image create(final Image image);

    /**
     * Create new Images
     * @param images
     * @return List<Image> created
     */
    List<Image> create(final List<Image> images);

    /**
     * Update an Image
     * @param image
     * @return Image updated
     */
    Image update(final Image image);

    /**
     * Delete an existing Image by its id
     * @param imageId
     */
    void delete(final String imageId);

}
