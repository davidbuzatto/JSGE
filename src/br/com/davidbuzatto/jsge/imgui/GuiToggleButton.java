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

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Um componente botão de alternância.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiToggleButton extends GuiCheckBox {
    
    protected GuiButtonGroup buttonGroup;
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param engine 
     */
    public GuiToggleButton( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text 
     */
    public GuiToggleButton( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    /**
     * 
     * @param bounds
     * @param text
     * @param engine 
     */
    public GuiToggleButton( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    /**
     * 
     * @param bounds
     * @param text 
     */
    public GuiToggleButton( Rectangle bounds, String text ) {
        super( bounds, text );
    }
    
    @Override
    public void update( double delta ) {
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                mouseState = GuiComponentMouseState.MOUSE_OVER;
                if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentMouseState.MOUSE_PRESSED;
                    if ( buttonGroup != null ) {
                        buttonGroup.toggle( this );
                    } else {
                        selected = !selected;
                    }
                } else if ( engine.isMouseButtonDown( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentMouseState.MOUSE_DOWN;
                }
            } else {
                mouseState = GuiComponentMouseState.MOUSE_OUT;
            }
            
        } else {
            mouseState = GuiComponentMouseState.MOUSE_OUT;
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                
                switch ( mouseState ) {
                    case MOUSE_OUT:
                        if ( !selected ) {
                            drawToggleButton( BACKGROUND_COLOR, BORDER_COLOR );
                            drawCenteredText( TEXT_COLOR );
                        } else {
                            drawToggleButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            drawCenteredText( MOUSE_DOWN_TEXT_COLOR );
                        }
                        break;
                    case MOUSE_OVER:
                        drawToggleButton( MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_BORDER_COLOR );
                        drawCenteredText( MOUSE_OVER_TEXT_COLOR );
                        break;
                    case MOUSE_PRESSED:
                        drawToggleButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                        drawCenteredText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                    case MOUSE_DOWN:
                        drawToggleButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                        drawCenteredText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                }

            } else {
                drawToggleButton( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR );
                drawCenteredText( DISABLED_TEXT_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawToggleButton( Color backgroundColor, Color borderColor ) {
        engine.fillRectangle( bounds, backgroundColor );
        engine.drawRectangle( bounds, borderColor );
    }

    /**
     * 
     * @param buttonGroup 
     */
    public void setButtonGroup( GuiButtonGroup buttonGroup ) {
        this.buttonGroup = buttonGroup;
        this.buttonGroup.addToggleButton( this );
    }
    
}
