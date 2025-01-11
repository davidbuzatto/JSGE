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
package br.com.davidbuzatto.jsge.core.engine.imgui;

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
    
    private GuiButtonGroup buttonGroup;
    
    public GuiToggleButton( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    public GuiToggleButton( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    public GuiToggleButton( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    public GuiToggleButton( Rectangle bounds, String text ) {
        super( bounds, text );
    }
    
    @Override
    public void update( double delta ) {
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                mouseState = GuiComponentState.MOUSE_OVER;
                if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentState.MOUSE_PRESSED;
                    if ( buttonGroup != null ) {
                        buttonGroup.toggle( this );
                    } else {
                        selected = !selected;
                    }
                } else if ( engine.isMouseButtonDown( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentState.MOUSE_DOWN;
                }
            } else {
                mouseState = GuiComponentState.MOUSE_OUT;
            }
            
        } else {
            mouseState = GuiComponentState.MOUSE_OUT;
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
                            drawButton( MOUSE_OUT_BACKGROUND_COLOR, MOUSE_OUT_BORDER_COLOR );
                            drawCenteredText( MOUSE_OUT_TEXT_COLOR );
                        } else {
                            drawButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            drawCenteredText( MOUSE_DOWN_TEXT_COLOR );
                        }
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
    
    public void setSelected( boolean selected ) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public boolean isPressed() {
        return mouseState == GuiComponentState.MOUSE_PRESSED;
    }

    public void setButtonGroup( GuiButtonGroup buttonGroup ) {
        this.buttonGroup = buttonGroup;
        this.buttonGroup.addToggleButton( this );
    }
    
}
