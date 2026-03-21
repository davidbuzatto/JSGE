/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.animation.frame;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.image.Image;
import java.awt.Color;

/**
 * An animation frame based on an image.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ImageAnimationFrame implements AnimationFrame {

    /**
     * The image of the animation frame.
     */
    public final Image baseImage;

    /**
     * Creates an image animation frame.
     *
     * @param baseImage The frame image.
     */
    public ImageAnimationFrame( Image baseImage ) {
        this.baseImage = baseImage;
    }

    /**
     * Draws the animation frame.
     *
     * @param engine The engine.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public void draw( EngineFrame engine, double x, double y ) {
        engine.drawImage( baseImage, x, y );
    }

    /**
     * Draws the animation frame.
     *
     * @param engine The engine.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param rotation Rotation in degrees for drawing the image (clockwise).
     */
    public void draw( EngineFrame engine, double x, double y, double rotation ) {
        engine.drawImage( baseImage, x, y, rotation );
    }

    /**
     * Draws the animation frame.
     *
     * @param engine The engine.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees for drawing the image (clockwise).
     */
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation ) {
        engine.drawImage( baseImage, x, y, originX, originY, rotation );
    }

    /**
     * Draws the animation frame.
     *
     * @param engine The engine.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param bgColor Background color.
     */
    public void draw( EngineFrame engine, double x, double y, Color bgColor ) {
        engine.drawImage( baseImage, x, y, bgColor );
    }

    /**
     * Draws the animation frame.
     *
     * @param engine The engine.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param rotation Rotation in degrees for drawing the image (clockwise).
     * @param bgColor Background color.
     */
    public void draw( EngineFrame engine, double x, double y, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, x, y, rotation, bgColor );
    }

    /**
     * Draws the animation frame.
     *
     * @param engine The engine.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees for drawing the image (clockwise).
     * @param bgColor Background color.
     */
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, x, y, originX, originY, rotation, bgColor );
    }
    
}
