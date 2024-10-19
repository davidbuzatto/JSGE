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
import br.com.davidbuzatto.jsge.utils.ColorUtils;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.SwingUtilities;

/**
 * Exemplo de uso de alguns métodos para manipulação de cores.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ColorMethodsExample extends Engine {

    private Color baseColor;
    
    public ColorMethodsExample() {
        super( 400, 290, "Color Methods", 60, true, true, false, false, false );
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
        
        int iterations = 300;
        
        double xStart = 70;
        double xEnd = getScreenWidth() - 10;
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
        drawText( message, getScreenWidth() - measureText( message, 20 ) - 10, 10, 20, BLACK );
        
        drawFPS( 10, 10 );
        
    }
    
    public static void main( String[] args ) {
        new ColorMethodsExample();
    }
    
}
