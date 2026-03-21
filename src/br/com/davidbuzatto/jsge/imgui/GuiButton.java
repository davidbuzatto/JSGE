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
package br.com.davidbuzatto.jsge.imgui;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import java.awt.Color;

/**
 * A button component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiButton extends GuiTextComponent {
    
    /**
     * Creates the component.
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiButton( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     */
    public GuiButton( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiButton( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     */
    public GuiButton( Rectangle bounds, String text ) {
        super( bounds, text );
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {

                switch ( mouseState ) {
                    case MOUSE_OUT:
                        drawButton( backgroundColor, borderColor );
                        drawCenteredText( textColor );
                        break;
                    case MOUSE_OVER:
                        drawButton( MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_BORDER_COLOR );
                        drawCenteredText( MOUSE_OVER_TEXT_COLOR );
                        break;
                    case MOUSE_PRESSED:
                        drawButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                        drawCenteredText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                    case MOUSE_DOWN:
                        drawButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                        drawCenteredText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                }

            } else {
                drawButton( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR );
                drawCenteredText( DISABLED_TEXT_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawButton( Color backgroundColor, Color borderColor ) {
        engine.fillRectangle( bounds, backgroundColor );
        engine.drawRectangle( bounds, borderColor );
    }

    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
}
