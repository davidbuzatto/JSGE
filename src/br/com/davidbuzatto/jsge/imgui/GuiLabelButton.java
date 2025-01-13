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

/**
 * Um componente label (etiqueta) que se comporta como um botão.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiLabelButton extends GuiTextComponent {
    
    public GuiLabelButton( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    public GuiLabelButton( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    public GuiLabelButton( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    public GuiLabelButton( Rectangle bounds, String text ) {
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
                } else if ( engine.isMouseButtonDown( engine.MOUSE_BUTTON_LEFT ) ) {
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
            
            if ( enabled ) {

                switch ( mouseState ) {
                    case MOUSE_OUT:
                        drawText( MOUSE_OUT_TEXT_COLOR );
                        break;
                    case MOUSE_OVER:
                        drawText( MOUSE_OVER_TEXT_COLOR );
                        break;
                    case MOUSE_PRESSED:
                        drawText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                    case MOUSE_DOWN:
                        drawText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                }

            } else {
                drawText( DISABLED_TEXT_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    public boolean isPressed() {
        return mouseState == GuiComponentState.MOUSE_PRESSED;
    }
    
}
