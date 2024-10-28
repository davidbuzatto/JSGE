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
package br.com.davidbuzatto.jsge.examples.basic;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.image.ImageUtils;
import java.awt.Font;

/**
 * Exemplos de carga, desenho e processamento de imagens.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ImageLoadingProcessingExample extends EngineFrame {

    private Image duke;
    private Image dukeStroke;
    private Image ladybug;
    private Image rotatingImage;
    
    private int rotationAngle;
    
    /**
     * Cria o exemplo.
     */
    public ImageLoadingProcessingExample() {
        super( 900, 650, "Image Loading and Processing", 60, true );
    }
    
    @Override
    public void create() {
        
        duke = loadImage( "resources/images/duke.png" );
        dukeStroke = loadImage( "resources/images/dukeCont.png" );
        ladybug = loadImage( "resources/images/ladybug.png" );
        
        setWindowIcon( duke );
        
        Image.enableAntialiasing();
        Image.setFontStyle( FONT_BOLD );
        
        dukeStroke.fillRing( 50, 50, 10, 30, 0, 290, ColorUtils.colorAlpha( GOLD, 0.8 ) );
        dukeStroke.drawRing( 50, 50, 10, 30, 0, 290, BLACK );
        dukeStroke.drawText( "hello!", 50, 30, 20, BLUE );
        
        dukeStroke.drawImage( 
                ladybug, 
                dukeStroke.getWidth() - ladybug.getWidth(), 
                dukeStroke.getHeight() - ladybug.getHeight()
        );
        
        /*dukeStroke.drawImage( 
                ladybug, 
                new Rectangle( 0, 0, 30, 30 ),
                dukeStroke.getWidth() - ladybug.getWidth(), 
                dukeStroke.getHeight() - ladybug.getHeight()
        );*/
        
        /*dukeStroke.drawImage( 
                ladybug, 
                new Rectangle( 0, 0, 30, 30 ),
                new Rectangle( 
                        dukeStroke.getWidth() - ladybug.getWidth(),
                        dukeStroke.getHeight() - ladybug.getHeight(),
                        60, 30
                )
        );*/
        
    }

    @Override
    public void update() {
        rotatingImage = ImageUtils.imageRotate( dukeStroke, rotationAngle++ );
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        setFontStyle( Font.BOLD );
        
        drawImage( duke, 10, 10 );
        drawImage( duke, 410, 10, 45, ColorUtils.colorAlpha( BLUE, 0.5 ) );
        drawImage( duke, 780, 10, 10, 10, 90, ColorUtils.colorAlpha( GREEN, 0.5 ) );
        
        drawImage( duke, new Rectangle( 0, 0, 100, 100 ), 10, 250, ColorUtils.colorAlpha( BLACK, 0.5 ) );
        drawImage( duke, new Rectangle( 0, 0, 100, 100 ), 170, 250, 45, ColorUtils.colorAlpha( PINK, 0.5 ) );
        drawImage( duke, new Rectangle( 0, 0, 100, 100 ), 320, 250, 10, 10, 90, ColorUtils.colorAlpha( ORANGE, 0.5 ) );
        
        drawImage( duke, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 10, 400, 150, 150 ), ColorUtils.colorAlpha( VIOLET, 0.5 ) );
        drawImage( duke, new Rectangle( 20, 20, 100, 100 ), new Rectangle( 250, 400, 150, 150 ), 45, ColorUtils.colorAlpha( LIME, 0.5 ) );
        drawImage( duke, new Rectangle( 40, 40, 100, 100 ), new Rectangle( 490, 400, 150, 150 ), 10, 10, 90, ColorUtils.colorAlpha( DARKBLUE, 0.5 ) );
        
        drawImage( rotatingImage, 550, 300, ColorUtils.colorAlpha( GOLD, 0.5 ) );
        
        drawFPS( 10, 10 );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new ImageLoadingProcessingExample();
    }
    
}
