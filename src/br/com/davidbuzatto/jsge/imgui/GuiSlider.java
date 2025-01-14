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
import br.com.davidbuzatto.jsge.math.MathUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Um componente deslizante.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiSlider extends GuiComponent {
    
    private double value;
    private double min;
    private double max;
    private GuiSliderButton sliderButton;
    
    private class GuiSliderButton extends GuiButton {
        
        private final double radius;
        private boolean dragging;
        private Vector2 prevMousePos;
        
        public GuiSliderButton( double x, double y, double width, double height, double radius, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
            this.radius = radius;
        }
        
        public GuiSliderButton( double x, double y, double width, double height, double radius ) {
            super( x, y, width, height, "" );
            this.radius = radius;
        }

        public void update( double delta, Rectangle containerBounds ) {

            if ( visible && enabled ) {

                Vector2 mousePos = engine.getMousePositionPoint();

                if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                    mouseState = GuiComponentState.MOUSE_OVER;
                    if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                        mouseState = GuiComponentState.MOUSE_PRESSED;
                    } else if ( engine.isMouseButtonDown( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                        mouseState = GuiComponentState.MOUSE_DOWN;
                        dragging = true;
                    }
                } else {
                    mouseState = GuiComponentState.MOUSE_OUT;
                }
                
                if ( engine.isMouseButtonUp( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    dragging = false;
                }
                
                if ( dragging ) {
                    mouseState = GuiComponentState.MOUSE_DOWN;
                    bounds.x += mousePos.x - prevMousePos.x;
                    if ( bounds.x + radius * 2 > containerBounds.x + containerBounds.width ) {
                        bounds.x = containerBounds.x + containerBounds.width - radius * 2;
                    } else if ( bounds.x < containerBounds.x ) {
                        bounds.x = containerBounds.x;
                    }
                }
                
                prevMousePos = mousePos;

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
                            drawSliderButton( MOUSE_OUT_BACKGROUND_COLOR, MOUSE_OUT_BORDER_COLOR );
                            break;
                        case MOUSE_OVER:
                            drawSliderButton( MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_BORDER_COLOR );
                            break;
                        case MOUSE_PRESSED:
                            drawSliderButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawSliderButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            break;
                    }

                } else {
                    drawSliderButton( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR );
                }
                
                drawBounds();

            }
            
        }
        
        private void drawSliderButton( Color backgroundColor, Color borderColor ) {
            engine.fillCircle( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, radius, backgroundColor );
            engine.drawCircle( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, radius, borderColor );
        }
        
    }
    
    public GuiSlider( double x, double y, double width, double height, int value, int min, int max, EngineFrame engine ) {
        super( x, y, width, height, engine );
        this.value = value;
        this.min = min;
        this.max = max;
        createChildremComponents( 10 );
    }
    
    public GuiSlider( double x, double y, double width, double height, int value, int min, int max ) {
        super( x, y, width, height );
        this.value = value;
        this.min = min;
        this.max = max;
        createChildremComponents( 10 );
    }
    
    public GuiSlider( Rectangle bounds, int value, int min, int max, EngineFrame engine ) {
        super( bounds, engine );
        this.value = value;
        this.min = min;
        this.max = max;
        createChildremComponents( 10 );
    }
    
    public GuiSlider( Rectangle bounds, int value, int min, int max ) {
        super( bounds );
        this.value = value;
        this.min = min;
        this.max = max;
        createChildremComponents( 10 );
    }
    
    private void createChildremComponents( double sliderRadius ) {
        sliderButton = new GuiSliderButton( bounds.x, bounds.y + bounds.height / 2 - sliderRadius, sliderRadius * 2, sliderRadius * 2, sliderRadius );
        updateSliderButtonPosition();
    }
    
    @Override
    public void update( double delta ) {
        
        if ( visible && enabled ) {
            
            sliderButton.update( delta, bounds );
            
            // calcula o valor
            double minSliderX = bounds.x + sliderButton.radius;
            double maxSliderX = bounds.x + bounds.width - sliderButton.radius;
            double sliderX = sliderButton.bounds.x + sliderButton.radius;
            double percentage = MathUtils.inverseLerp( minSliderX, maxSliderX, sliderX );
            
            value = MathUtils.lerp( min, max, percentage );
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                drawSlider( CONTAINER_BACKGROUNG_COLOR, MOUSE_OUT_BORDER_COLOR, MOUSE_DOWN_BACKGROUND_COLOR, 4 );
            } else {
                drawSlider( DISABLED_CONTAINER_BACKGROUND_COLOR, DISABLED_BORDER_COLOR, MOUSE_DOWN_BACKGROUND_COLOR, 4 );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawSlider( Color backgroundColor, Color borderColor, Color valueColor, double sliderHeight ) {
        engine.fillRectangle( bounds.x, bounds.y + bounds.height / 2 - sliderHeight / 2, bounds.width, sliderHeight, backgroundColor );
        engine.fillRectangle( bounds.x, bounds.y + bounds.height / 2 - sliderHeight / 2, sliderButton.bounds.x - bounds.x, sliderHeight, valueColor );
        engine.drawRectangle( bounds.x, bounds.y + bounds.height / 2 - sliderHeight / 2, bounds.width, sliderHeight, borderColor );
        sliderButton.draw();
    }
    
    private void updateSliderButtonPosition() {
        
        double minSliderX = bounds.x + sliderButton.radius;
        double maxSliderX = bounds.x + bounds.width - sliderButton.radius;
        double percentage = MathUtils.inverseLerp( min, max, value );
        
        double sliderX = MathUtils.lerp( minSliderX, maxSliderX, percentage );
        sliderButton.bounds.x = sliderX - sliderButton.radius;
        
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        sliderButton.setEnabled( enabled );
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        sliderButton.setVisible( visible );
    }
    
    public double getValue() {
        return value;
    }

    public void setValue( double value ) {
        this.value = MathUtils.clamp( value, min, max );
        updateSliderButtonPosition();
    }

    public double getMin() {
        return min;
    }

    public void setMin( double min ) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax( double max ) {
        this.max = max;
    }
    
}
