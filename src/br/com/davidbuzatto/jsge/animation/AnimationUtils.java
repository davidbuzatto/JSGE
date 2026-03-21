/*
 * Copyright (C) 2025 Prof. Dr. David Buzatto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.jsge.animation;

import br.com.davidbuzatto.jsge.animation.frame.ImageAnimationFrame;
import br.com.davidbuzatto.jsge.animation.frame.SpriteMapAnimationFrame;
import br.com.davidbuzatto.jsge.image.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface with utility methods for animations.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface AnimationUtils {

    /**
     * Creates a list of ImageAnimationFrame from a list of images.
     *
     * @param images A list of images.
     * @return A list of ImageAnimationFrame
     */
    public static List<ImageAnimationFrame> getImageAnimationFrameList( List<Image> images ) {
        
        List<ImageAnimationFrame> frames = new ArrayList<>();
        
        for ( Image image : images ) {
            frames.add( new ImageAnimationFrame( image ) );
        }
        
        return frames;
        
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     *
     * @param spriteMapImage A sprite map image.
     * @param quantity Number of frames to be generated (crops to be made from the sprite map).
     * @param x The x coordinate of the start of the crop in the sprite map image.
     * @param y The y coordinate of the start of the crop in the sprite map image.
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @param backwards Whether the sprite map should be mapped from end to start.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, int quantity, double x, double y, double width, double height, boolean backwards ) {
        
        List<SpriteMapAnimationFrame> frames = new ArrayList<>();
        
        if ( backwards ) {
            for ( int i = quantity - 1; i >= 0; i-- ) {
                frames.add( new SpriteMapAnimationFrame( spriteMapImage, x + i * width, y, width, height ) );
            }
        } else {
            for ( int i = 0; i < quantity; i++ ) {
                frames.add( new SpriteMapAnimationFrame( spriteMapImage, x + i * width, y, width, height ) );
            }
        }
        
        return frames;
        
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     * The image is processed from left to right starting from the provided initial coordinate.
     *
     * @param spriteMapImage A sprite map image.
     * @param quantity Number of frames to be generated (crops to be made from the sprite map).
     * @param x The x coordinate of the start of the crop in the sprite map image.
     * @param y The y coordinate of the start of the crop in the sprite map image.
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, int quantity, double x, double y, double width, double height ) {
        return AnimationUtils.getSpriteMapAnimationFrameList( spriteMapImage, quantity, x, y, width, height, false );
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     *
     * @param spriteMapImage A sprite map image.
     * @param quantity Number of frames to be generated (crops to be made from the sprite map).
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @param backwards Whether the sprite map should be mapped from end to start.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, int quantity, double width, double height, boolean backwards ) {
        return AnimationUtils.getSpriteMapAnimationFrameList( spriteMapImage, quantity, 0, 0, width, height, backwards );
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     * Crops are made starting from coordinate (0, 0) of the sprite map image, from left to right.
     *
     * @param spriteMapImage A sprite map image.
     * @param quantity Number of frames to be generated (crops to be made from the sprite map).
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, int quantity, double width, double height ) {
        return AnimationUtils.getSpriteMapAnimationFrameList( spriteMapImage, quantity, 0, 0, width, height, false );
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     * The number of frames is derived from the image and the crop width.
     *
     * @param spriteMapImage A sprite map image.
     * @param x The x coordinate of the start of the crop in the sprite map image.
     * @param y The y coordinate of the start of the crop in the sprite map image.
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @param backwards Whether the sprite map should be mapped from end to start.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, double x, double y, double width, double height, boolean backwards ) {
        return AnimationUtils.getSpriteMapAnimationFrameList( spriteMapImage, (int) ( spriteMapImage.getWidth() / width ), x, y, width, height, backwards );
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     * The image is processed from left to right starting from the provided initial coordinate.
     * The number of frames is derived from the image and the crop width.
     *
     * @param spriteMapImage A sprite map image.
     * @param x The x coordinate of the start of the crop in the sprite map image.
     * @param y The y coordinate of the start of the crop in the sprite map image.
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, double x, double y, double width, double height ) {
        return AnimationUtils.getSpriteMapAnimationFrameList( spriteMapImage, x, y, width, height, false );
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     * The number of frames is derived from the image and the crop width.
     *
     * @param spriteMapImage A sprite map image.
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @param backwards Whether the sprite map should be mapped from end to start.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, double width, double height, boolean backwards ) {
        return AnimationUtils.getSpriteMapAnimationFrameList( spriteMapImage, 0, 0, width, height, backwards );
    }
    
    /**
     * Creates a list of SpriteMapAnimationFrame from a sprite map image.
     * Crops are made starting from coordinate (0, 0) of the sprite map image, from left to right.
     * The number of frames is derived from the image and the crop width.
     *
     * @param spriteMapImage A sprite map image.
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     * @return A list of SpriteMapAnimationFrame
     */
    public static List<SpriteMapAnimationFrame> getSpriteMapAnimationFrameList( Image spriteMapImage, double width, double height ) {
        return AnimationUtils.getSpriteMapAnimationFrameList( spriteMapImage, 0, 0, width, height, false );
    }
    
}
