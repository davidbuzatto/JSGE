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
 * Um componente caixa de seleção.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiCheckBox extends GuiTextComponent {
    
    protected boolean selected;
    
    public GuiCheckBox( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    public GuiCheckBox( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    public GuiCheckBox( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    public GuiCheckBox( Rectangle bounds, String text ) {
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
                    selected = !selected;
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
            
            engine.setStrokeLineWidth( 1 );
            
            if ( enabled ) {

                switch ( mouseState ) {
                    case MOUSE_OUT:
                        drawCheckBox( MOUSE_OUT_BORDER_COLOR, 20, selected );
                        drawText( MOUSE_OUT_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_OVER:
                        drawCheckBox( MOUSE_OVER_BORDER_COLOR, 20, selected );
                        drawText( MOUSE_OVER_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_PRESSED:
                        drawCheckBox( MOUSE_DOWN_BORDER_COLOR, 20, selected );
                        drawText( MOUSE_DOWN_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_DOWN:
                        drawCheckBox( MOUSE_DOWN_BORDER_COLOR, 20, selected );
                        drawText( MOUSE_DOWN_TEXT_COLOR, 25, 0 );
                        break;
                }

            } else {
                drawCheckBox( DISABLED_BORDER_COLOR, 20, selected );
                drawText( DISABLED_TEXT_COLOR, 25, 0 );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawCheckBox( Color borderColor, double checkSize, boolean fillInternal ) {
        double x = bounds.x;
        double y = bounds.y + bounds.height / 2 - checkSize / 2;
        engine.drawRectangle( x, y, checkSize, checkSize, borderColor );
        if ( fillInternal ) {
            engine.fillRectangle( x + 2, y + 2, checkSize - 3, checkSize - 3, borderColor );
        }
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
    
}
