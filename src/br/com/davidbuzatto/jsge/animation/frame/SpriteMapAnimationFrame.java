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
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import java.awt.Color;

/**
 * An animation frame based on a sprite map. It is based on an ImageAnimationFrame.
 * The sprite map image will be associated with the baseImage attribute of ImageAnimationFrame.
 *
 * @author Prof. Dr. David Buzatto
 */
public class SpriteMapAnimationFrame extends ImageAnimationFrame {

    /**
     * The source rectangle of the crop.
     */
    public final Rectangle source;

    /**
     * Creates an image animation frame.
     *
     * @param spriteMapImage The sprite map image.
     * @param x The x coordinate of the start of the sprite map crop.
     * @param y The y coordinate of the start of the sprite map crop.
     * @param width Width of the sprite map crop.
     * @param height Height of the sprite map crop.
     */
    public SpriteMapAnimationFrame( Image spriteMapImage, double x, double y, double width, double height ) {
        super( spriteMapImage );
        this.source = new Rectangle( x, y, width, height );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y ) {
        engine.drawImage( baseImage, source, x, y );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double rotation ) {
        engine.drawImage( baseImage, source, x, y, rotation );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation ) {
        engine.drawImage( baseImage, source, x, y, originX, originY, rotation );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, Color bgColor ) {
        engine.drawImage( baseImage, source, x, y, bgColor );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, source, x, y, rotation, bgColor );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, source, x, y, originX, originY, rotation, bgColor );
    }
    
}
