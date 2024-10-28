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
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.SwingUtilities;

/**
 * Exemplo de uso de alguns métodos para manipulação de cores.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ColorMethodsExample extends EngineFrame {

    private Color baseColor;
    
    /**
     * Cria o exemplo.
     */
    public ColorMethodsExample() {
        super( 800, 450, "Color Methods", 60, true, true, false, false, false );
    }
    
    @Override
    public void create() {
        baseColor = LIME;
    }

    @Override
    public void update() {
        if ( isMouseButtonPressed( MOUSE_BUTTON_RIGHT ) ) {
            SwingUtilities.invokeLater(() -> {
                Color c = JColorChooser.showDialog( null, "Choose a color!", baseColor );
                if ( c != null ) {
                    baseColor = c;
                }
            });
        }
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        int iterations = 300;
        
        double xStart = 70;
        double xEnd = getScreenWidth() - 400;
        double width = xEnd - xStart;
        
        double yStart = 30;
        double yEnd = getScreenHeight() - 10;
        double height = yEnd - yStart;
        
        for ( int i = 0; i <= iterations; i++ ) {
            fillRectangle( 
                    xStart + ( i / (double) iterations ) * width, 
                    yStart + ( i / (double) iterations ) * height, 
                    2, 2, 
                    ColorUtils.colorFromHSV( ( i / (double) iterations ) * 360.0, 1, 1 )
            );
        }
        
        fillRectangle( 10, 30, 50, 50, baseColor );
        fillRectangle( 10, 80, 50, 50, ColorUtils.colorAlpha( baseColor, 0.5 ) );
        fillRectangle( 10, 130, 50, 50, ColorUtils.colorTint( baseColor, WHITE ) );
        fillRectangle( 10, 180, 50, 50, ColorUtils.colorBrightness( baseColor, -0.5 ) );
        fillRectangle( 10, 230, 50, 50, ColorUtils.colorContrast( baseColor, -0.5 ) );
        
        String message = "right click me ;)";
        drawText( message, 10, getScreenHeight() - 30, 20, BLACK );
        
        drawHSVCircle();
        
        drawFPS( 10, 10 );
        
    }
    
    private void drawHSVCircle() {
        
        int radius = 180;
        int bQuantity = 6;
        int x = getScreenWidth() - radius - 50; 
        int y = getScreenHeight() / 2;
        
        for ( double r = radius; r > 0; r -= radius / bQuantity ) {
            for ( int i = 0; i < 360; i += 15 ) {
                fillCircleSector( x, y, r, i, i + 16, ColorUtils.colorFromHSV( i - 90, 1, r / radius ) );
            }
        }
        
        for ( int i = 0; i < 360; i += 15 ) {
            double xp = x + Math.cos( Math.toRadians( i - 90 + 7.5 ) ) * ( radius + 15 );
            double yp = y + Math.sin( Math.toRadians( i - 90 + 7.5 ) ) * ( radius + 15 );
            String s = String.valueOf( i );
            int w = measureText( s, 16 );
            drawText( String.valueOf( i ), xp - w / 2, yp - 4, 16, BLACK );
        }
        
        drawCircle( x, y, radius, BLACK );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new ColorMethodsExample();
    }
    
}
