package com.danieleautizi.manager;

import com.danieleautizi.model.presentation.Image;

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
    public Image getImageById(final String imageId);

    /**
     * Find Images by a bunch of ids
     * @param imageIds
     * @return List<Image>
     */
    public List<Image> getImageByIds(final List<String> imageIds);

    /**
     * Find all Images
     * @return List<Image>
     */
    public List<Image> getImages();

    /**
     * Find all the active Images
     * @return List<Image>
     */
    public List<Image> getActiveImages();

    /**
     * Create a new Image
     * @param image
     * @return Image created
     */
    public Image create(final Image image);

    /**
     * Create new Images
     * @param images
     * @return List<Image> created
     */
    public List<Image> create(final List<Image> images);

    /**
     * Update an Image
     * @param image
     * @return Image updated
     */
    public Image update(final Image image);

    /**
     * Delete an existing Image by its id
     * @param imageId
     */
    public void delete(final String imageId);

}
