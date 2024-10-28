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
import br.com.davidbuzatto.jsge.core.utils.PaintUtils;
import br.com.davidbuzatto.jsge.image.Image;
import java.awt.Color;
import java.awt.Paint;

/**
 * Exemplo das funcionalidades de pintura.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PaintingCapabilitiesExample extends EngineFrame {

    
    private Paint horizontalGradient;
    private Paint verticalGradient;
    
    private Paint linearGradientNoCycle;
    private Paint linearGradientReflect;
    private Paint linearGradientRepeat;
    
    private Paint radialGradientNoCycle;
    private Paint radialGradientReflect;
    private Paint radialGradientRepeat;
    
    private Paint radialFGradientNoCycle;
    private Paint radialFGradientReflect;
    private Paint radialFGradientRepeat;
    
    private Image image;
    private Paint imagePaint;
    
    /**
     * Cria o exemplo.
     */
    public PaintingCapabilitiesExample() {
        super( 630, 420, "Paiting Capabilities", 60, true );
    }
    
    @Override
    public void create() {
        
        horizontalGradient = PaintUtils.getHorizontalGradientPaint( 20, 20, 100, 155, ColorUtils.getColor( 0xFFFF6500 ), ColorUtils.getColor( 0xFF1E3E62 ) );
        verticalGradient = PaintUtils.getVerticalGradientPaint( 20, 185, 100, 155, ColorUtils.getColor( 0xFFFF6500 ), ColorUtils.getColor( 0xFF1E3E62 ) );
        
        linearGradientNoCycle = PaintUtils.getLinearGradientPaint( 130, 20, 180, 70, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ VIOLET, WHITE, MAROON } );
        linearGradientReflect = PaintUtils.getLinearGradientPaint( 130, 130, 180, 180, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ VIOLET, WHITE, MAROON }, PaintUtils.CycleMethod.REFLECT );
        linearGradientRepeat = PaintUtils.getLinearGradientPaint( 130, 240, 180, 290, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ VIOLET, WHITE, MAROON }, PaintUtils.CycleMethod.REPEAT );
        
        radialGradientNoCycle = PaintUtils.getRadialGradientPaint( 290, 70, 25, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ BLUE, GOLD, LIME } );
        radialGradientReflect = PaintUtils.getRadialGradientPaint( 290, 180, 25, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ BLUE, GOLD, LIME }, PaintUtils.CycleMethod.REFLECT );
        radialGradientRepeat = PaintUtils.getRadialGradientPaint( 290, 290, 25, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ BLUE, GOLD, LIME }, PaintUtils.CycleMethod.REPEAT );
        
        radialFGradientNoCycle = PaintUtils.getRadialGradientPaint( 400, 70, 25, 390, 60, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ ColorUtils.getColor( 0xFFFFAD60 ), ColorUtils.getColor(  0xFF96CEB4 ), ColorUtils.getColor( 0xFFA02334 ) } );
        radialFGradientReflect = PaintUtils.getRadialGradientPaint( 400, 180, 25, 390, 170, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ ColorUtils.getColor( 0xFFFFAD60 ), ColorUtils.getColor(  0xFF96CEB4 ), ColorUtils.getColor( 0xFFA02334 ) }, PaintUtils.CycleMethod.REFLECT );
        radialFGradientRepeat = PaintUtils.getRadialGradientPaint( 400, 290, 25, 390, 280, new float[]{ 0.0f, 0.2f, 1.0f }, new Color[]{ ColorUtils.getColor( 0xFFFFAD60 ), ColorUtils.getColor(  0xFF96CEB4 ), ColorUtils.getColor( 0xFFA02334 ) }, PaintUtils.CycleMethod.REPEAT );
        
        image = loadImage( "resources/images/ladybug.png" );
        imagePaint = PaintUtils.getImagePaint( image, 460, 20, 87, 61 );
        
    }

    @Override
    public void update() {
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        fillRectangle( 20, 20, 100, 155, horizontalGradient );
        drawRectangle( 20, 20, 100, 155, BLACK );
        fillRectangle( 20, 185, 100, 155, verticalGradient );
        drawRectangle( 20, 185, 100, 155, BLACK );
        drawText( "horizontal\nand\nvertical\ngradients", 20, 350, 14, BLACK );
        
        fillRectangle( 130, 20, 100, 100, linearGradientNoCycle );
        drawRectangle( 130, 20, 100, 100, BLACK );
        fillRectangle( 130, 130, 100, 100, linearGradientReflect );
        drawRectangle( 130, 130, 100, 100, BLACK );
        fillRectangle( 130, 240, 100, 100, linearGradientRepeat );
        drawRectangle( 130, 240, 100, 100, BLACK );
        drawText( "linear\ngradients", 130, 350, 14, BLACK );
        
        fillRectangle( 240, 20, 100, 100, radialGradientNoCycle );
        drawRectangle( 240, 20, 100, 100, BLACK );
        fillRectangle( 240, 130, 100, 100, radialGradientReflect );
        drawRectangle( 240, 130, 100, 100, BLACK );
        fillRectangle( 240, 240, 100, 100, radialGradientRepeat );
        drawRectangle( 240, 240, 100, 100, BLACK );
        drawText( "radial\ngradients", 240, 350, 14, BLACK );
        
        fillRectangle( 350, 20, 100, 100, radialFGradientNoCycle );
        drawRectangle( 350, 20, 100, 100, BLACK );
        fillRectangle( 350, 130, 100, 100, radialFGradientReflect );
        drawRectangle( 350, 130, 100, 100, BLACK );
        fillRectangle( 350, 240, 100, 100, radialFGradientRepeat );
        drawRectangle( 350, 240, 100, 100, BLACK );
        drawText( "radial\ngradients\nwith\nfocus", 350, 350, 14, BLACK );
        
        fillRectangle( 460, 20, 150, 320, imagePaint );
        drawRectangle( 460, 20, 150, 320, BLACK );
        drawText( "image\npaint", 460, 350, 14, BLACK );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new PaintingCapabilitiesExample();
    }
    
}
