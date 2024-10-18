/*
 * Copyright (C) 2024 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.examples;

import br.com.davidbuzatto.jsge.core.Engine;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.utils.ColorUtils;
import br.com.davidbuzatto.jsge.utils.ImageUtils;
import java.awt.Font;

/**
 * Exemplos de carga, desenho e processamento de imagens.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ImageLoadingProcessingExample extends Engine {

    private Image image;
    private Image imageCont;
    private Image rotatingImage;
    
    private int rotationAngle;
    
    public ImageLoadingProcessingExample() {
        super( 900, 650, "Image Loading and Processing", 60, true );
    }
    
    @Override
    public void create() {
        
        image = loadImage( "resources/images/duke.png" );
        imageCont = loadImage( "resources/images/dukeCont.png" );
        
        Image.enableAntialiasing();
        Image.setFontStyle( FONT_BOLD );
        
        imageCont.fillRing( 50, 50, 10, 30, 0, 290, ColorUtils.colorAlpha( GOLD, 0.8 ) );
        imageCont.drawRing( 50, 50, 10, 30, 0, 290, BLACK );
        imageCont.drawText( "hello!", 50, 30, 20, BLUE );
        
    }

    @Override
    public void update() {
        rotatingImage = ImageUtils.imageRotate( imageCont, rotationAngle++ );
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        setFontStyle( Font.BOLD );
        
        drawImage( image, 10, 10 );
        drawImage( image, 410, 10, 45, ColorUtils.colorAlpha( BLUE, 0.5 ) );
        drawImage( image, 780, 10, 10, 10, 90, ColorUtils.colorAlpha( GREEN, 0.5 ) );
        
        drawImage( image, new Rectangle( 0, 0, 100, 100 ), 10, 250, ColorUtils.colorAlpha( BLACK, 0.5 ) );
        drawImage( image, new Rectangle( 0, 0, 100, 100 ), 170, 250, 45, ColorUtils.colorAlpha( PINK, 0.5 ) );
        drawImage( image, new Rectangle( 0, 0, 100, 100 ), 320, 250, 10, 10, 90, ColorUtils.colorAlpha( ORANGE, 0.5 ) );
        
        drawImage( image, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 10, 400, 150, 150 ), ColorUtils.colorAlpha( VIOLET, 0.5 ) );
        drawImage( image, new Rectangle( 20, 20, 100, 100 ), new Rectangle( 250, 400, 150, 150 ), 45, ColorUtils.colorAlpha( LIME, 0.5 ) );
        drawImage( image, new Rectangle( 40, 40, 100, 100 ), new Rectangle( 490, 400, 150, 150 ), 10, 10, 90, ColorUtils.colorAlpha( DARKBLUE, 0.5 ) );
        
        drawImage( rotatingImage, 550, 300, ColorUtils.colorAlpha( GOLD, 0.5 ) );
        
    }
    
    public static void main( String[] args ) {
        new ImageLoadingProcessingExample();
    }
    
}
