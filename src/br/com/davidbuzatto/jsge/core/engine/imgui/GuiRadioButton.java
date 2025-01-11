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
package br.com.davidbuzatto.jsge.core.engine.imgui;

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Um componente botão de radio.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiRadioButton extends GuiToggleButton {
    
    private GuiButtonGroup buttonGroup;
    
    public GuiRadioButton( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    public GuiRadioButton( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    public GuiRadioButton( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    public GuiRadioButton( Rectangle bounds, String text ) {
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
            
            engine.setStrokeLineWidth( 1 );

            if ( enabled ) {

                switch ( mouseState ) {
                    case MOUSE_OUT:
                        drawRadio( MOUSE_OUT_BORDER_COLOR, 10, selected );
                        drawText( MOUSE_OUT_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_OVER:
                        drawRadio( MOUSE_OVER_BORDER_COLOR, 10, selected );
                        drawText( MOUSE_OVER_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_PRESSED:
                        drawRadio( MOUSE_DOWN_BORDER_COLOR, 10, selected );
                        drawText( MOUSE_DOWN_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_DOWN:
                        drawRadio( MOUSE_DOWN_BORDER_COLOR, 10, selected );
                        drawText( MOUSE_DOWN_TEXT_COLOR, 25, 0 );
                        break;
                }

            } else {
                drawRadio( DISABLED_BORDER_COLOR, 10, selected );
                drawText( DISABLED_TEXT_COLOR, 25, 0 );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawRadio( Color borderColor, double radioRadius, boolean fillInternal ) {
        double x = bounds.x + radioRadius;
        double y = bounds.y + bounds.height / 2;
        engine.drawCircle( x, y, radioRadius, borderColor );
        if ( fillInternal ) {
            engine.fillCircle( x + 0.5, y + 0.5, radioRadius - 2, borderColor );
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

    public void setButtonGroup( GuiButtonGroup buttonGroup ) {
        this.buttonGroup = buttonGroup;
        this.buttonGroup.addToggleButton( this );
    }
    
}
