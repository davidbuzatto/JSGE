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
 * Um componente de painel.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiPanel extends GuiTextComponent {
    
    private Rectangle titleBarBounds;
    
    public GuiPanel( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
        createChildremComponents();
    }
    
    public GuiPanel( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
        createChildremComponents();
    }
    
    public GuiPanel( double x, double y, double width, double height, EngineFrame engine ) {
        super( x, y, width, height, null, engine );
        createChildremComponents();
    }
    
    public GuiPanel( double x, double y, double width, double height ) {
        super( x, y, width, height, null );
        createChildremComponents();
    }
    
    public GuiPanel( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
        createChildremComponents();
    }
    
    public GuiPanel( Rectangle bounds, String text ) {
        super( bounds, text );
        createChildremComponents();
    }
    
    public GuiPanel( Rectangle bounds, EngineFrame engine ) {
        super( bounds, null, engine );
        createChildremComponents();
    }
    
    public GuiPanel( Rectangle bounds ) {
        super( bounds, null );
        createChildremComponents();
    }
    
    private void createChildremComponents() {
        titleBarBounds = new Rectangle( bounds.x, bounds.y, bounds.width, 25 );
    }
    
    @Override
    public void update( double delta ) {
        
        if ( text != null && visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, titleBarBounds ) ) {
                mouseState = GuiComponentState.MOUSE_OVER;
                if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentState.MOUSE_PRESSED;
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
                drawPanel( CONTAINER_BORDER_COLOR, CONTAINER_BACKGROUNG_COLOR, CONTAINER_TITLE_BAR_BORDER_COLOR, CONTAINER_TITLE_BAR_BACKGROUND_COLOR, CONTAINER_TITLE_BAR_TEXT_COLOR );
            } else {
                drawPanel( DISABLED_CONTAINER_BORDER_COLOR, DISABLED_CONTAINER_BACKGROUND_COLOR, DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR, DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR, DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    private void drawPanel( Color borderColor, Color backgroundColor, Color titleBarBorderColor, Color titleBarBackgroundColor, Color titleBarTextColor ) {
        
        if ( textWidth == -1 && text != null ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        
        if ( text != null ) {
            engine.fillRectangle( bounds, backgroundColor );
            engine.drawRectangle( bounds, borderColor );
            engine.fillRectangle( titleBarBounds, titleBarBackgroundColor );
            engine.drawRectangle( titleBarBounds, titleBarBorderColor );
            engine.drawText( text, bounds.x + 10, bounds.y + 10, FONT_SIZE, titleBarTextColor );
        } else {
            engine.fillRectangle( bounds, backgroundColor );
            engine.drawRectangle( bounds, borderColor );
        }
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        titleBarBounds.x += xAmount;
        titleBarBounds.y += yAmount;
    }
    
    public boolean isTitleBarPressed() {
        return mouseState == GuiComponentState.MOUSE_PRESSED;
    }
    
}
