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
 * Um componente de janela.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiWindowBox extends GuiTextComponent {
    
    private GuiButtonClose closeButton;
    private Rectangle titleBarBounds;
    
    private class GuiButtonClose extends GuiButton {
        
        public GuiButtonClose( double x, double y, double width, double height, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
        }
        
        public GuiButtonClose( double x, double y, double width, double height ) {
            super( x, y, width, height, "" );
        }

        @Override
        public void draw() {
            
            super.draw();
            
            if ( visible ) {

                engine.setStrokeLineWidth( LINE_WIDTH );

                if ( enabled ) {

                    switch ( mouseState ) {
                        case MOUSE_OUT:
                            drawButtonLabel( MOUSE_OUT_TEXT_COLOR );
                            break;
                        case MOUSE_OVER:
                            drawButtonLabel( MOUSE_OVER_TEXT_COLOR );
                            break;
                        case MOUSE_PRESSED:
                            drawButtonLabel( MOUSE_DOWN_TEXT_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawButtonLabel( MOUSE_DOWN_TEXT_COLOR );
                            break;
                    }

                } else {
                    drawButtonLabel( DISABLED_TEXT_COLOR );
                }

            }
            
        }
        
        private void drawButtonLabel( Color color ) {
            int pad = 6;
            engine.drawLine( bounds.x + pad, bounds.y + pad, bounds.x + bounds.width - pad, bounds.y + bounds.height - pad, color );
            engine.drawLine( bounds.x + pad, bounds.y + bounds.height - pad, bounds.x + bounds.width - pad, bounds.y + pad, color );
        }
        
        
    }
    
    public GuiWindowBox( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
        createChildremComponents( engine );
    }
    
    public GuiWindowBox( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
        createChildremComponents();
    }
    
    public GuiWindowBox( double x, double y, double width, double height, EngineFrame engine ) {
        super( x, y, width, height, null, engine );
        createChildremComponents( engine );
    }
    
    public GuiWindowBox( double x, double y, double width, double height ) {
        super( x, y, width, height, null );
        createChildremComponents();
    }
    
    public GuiWindowBox( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
        createChildremComponents( engine );
    }
    
    public GuiWindowBox( Rectangle bounds, String text ) {
        super( bounds, text );
        createChildremComponents();
    }
    
    public GuiWindowBox( Rectangle bounds, EngineFrame engine ) {
        super( bounds, null, engine );
        createChildremComponents( engine );
    }
    
    public GuiWindowBox( Rectangle bounds ) {
        super( bounds, null );
        createChildremComponents();
    }
    
    private void createChildremComponents( EngineFrame engine ) {
        titleBarBounds = new Rectangle( bounds.x, bounds.y, bounds.width, 25 );
        closeButton = new GuiButtonClose( bounds.x + bounds.width - 22, bounds.y + 3, 19, 19, engine );
    }
    
    private void createChildremComponents() {
        titleBarBounds = new Rectangle( bounds.x, bounds.y, bounds.width, 25 );
        closeButton = new GuiButtonClose( bounds.x + bounds.width - 22, bounds.y + 3, 19, 19 );
    }
    
    @Override
    public void update( double delta ) {
        
        if ( visible && enabled ) {
            
            closeButton.update( delta );
            
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
                drawWindowBox( CONTAINER_BORDER_COLOR, CONTAINER_BACKGROUNG_COLOR, CONTAINER_TITLE_BAR_BORDER_COLOR, CONTAINER_TITLE_BAR_BACKGROUND_COLOR, CONTAINER_TITLE_BAR_TEXT_COLOR );
            } else {
                drawWindowBox( DISABLED_CONTAINER_BORDER_COLOR, DISABLED_CONTAINER_BACKGROUND_COLOR, DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR, DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR, DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR );
            }
            drawBounds();
        }
        closeButton.draw();
    }
    
    private void drawWindowBox( Color borderColor, Color backgroundColor, Color titleBarBorderColor, Color titleBarBackgroundColor, Color titleBarTextColor ) {
        
        if ( textWidth == -1 && text != null ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        
        engine.fillRectangle( bounds, backgroundColor );
        engine.drawRectangle( bounds, borderColor );
        engine.fillRectangle( titleBarBounds, titleBarBackgroundColor );
        engine.drawRectangle( titleBarBounds, titleBarBorderColor );
            
        if ( text != null ) {
            engine.drawText( text, bounds.x + 10, bounds.y + 10, FONT_SIZE, titleBarTextColor );
        }
        
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        closeButton.setEnabled( enabled );
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        closeButton.setVisible( visible );
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        titleBarBounds.x += xAmount;
        titleBarBounds.y += yAmount;
        closeButton.bounds.x += xAmount;
        closeButton.bounds.y += yAmount;
    }
    
    public boolean isCloseButtonPressed() {
        return closeButton.isPressed();
    }
    
    public boolean isTitleBarPressed() {
        if ( closeButton.isPressed() ) {
            return false;
        }
        return mouseState == GuiComponentState.MOUSE_PRESSED;
    }
    
}
