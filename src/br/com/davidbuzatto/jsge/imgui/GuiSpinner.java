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
import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.Color;

/**
 * Um componente seletor de inteiros.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiSpinner extends GuiComponent {
    
    private int value;
    private int min;
    private int max;
    
    private GuiButtonChangeValue leftButton;
    private GuiButtonChangeValue rightButton;
    
    public GuiSpinner( double x, double y, double width, double height, int value, int min, int max, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value, min, max );
        initComponents( 24 );
    }
    
    public GuiSpinner( double x, double y, double width, double height, int value, int min, int max ) {
        super( x, y, width, height );
        initData( value, min, max );
        initComponents( 24 );
    }
    
    public GuiSpinner( Rectangle bounds, int value, int min, int max, EngineFrame engine ) {
        super( bounds, engine );
        initData( value, min, max );
        initComponents( 24 );
    }
    
    public GuiSpinner( Rectangle bounds, int value, int min, int max ) {
        super( bounds );
        initData( value, min, max );
        initComponents( 24 );
    }
    
    private void initData( int value, int min, int max ) {
        this.value = value;
        this.min = min;
        this.max = max;
    }
    
    private void initComponents( double buttonSize ) {
        leftButton = new GuiButtonChangeValue( bounds.x, bounds.y, buttonSize, bounds.height, 180 );
        rightButton = new GuiButtonChangeValue( bounds.x + bounds.width - buttonSize, bounds.y, buttonSize, bounds.height, 0 );
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            leftButton.update( delta );
            rightButton.update( delta );
            
            if ( leftButton.isPressed() ) {
                decrementValue();
            }
            
            if ( rightButton.isPressed() ) {
                incrementValue();
            }
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                drawSpinner(CONTAINER_BACKGROUNG_COLOR, BORDER_COLOR, TEXT_COLOR );
            } else {
                drawSpinner( DISABLED_CONTAINER_BACKGROUND_COLOR, DISABLED_BORDER_COLOR, DISABLED_TEXT_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawSpinner( Color backgroundColor, Color borderColor, Color textColor ) {
        
        engine.fillRectangle( bounds.x + leftButton.bounds.width + 3, bounds.y, bounds.width - ( 2 * rightButton.bounds.width + 6 ), bounds.height, backgroundColor );
        engine.drawRectangle( bounds.x + leftButton.bounds.width + 3, bounds.y, bounds.width - ( 2 * rightButton.bounds.width + 6 ), bounds.height, borderColor );
        
        String tValue = String.valueOf( value );
        int width = engine.measureText( tValue, FONT_SIZE );
        
        engine.drawText( tValue, bounds.x + bounds.width / 2 - width / 2, bounds.y + bounds.height / 2 - 3, FONT_SIZE, textColor );
        
        leftButton.draw();
        rightButton.draw();
        
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        leftButton.setEnabled( enabled );
        rightButton.setEnabled( enabled );
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        leftButton.setVisible( visible );
        rightButton.setVisible( visible );
    }
    
    public int getValue() {
        return value;
    }

    public void setValue( int value ) {
        this.value = MathUtils.clamp( value, min, max );
    }

    public int getMin() {
        return min;
    }

    public void setMin( int min ) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax( int max ) {
        this.max = max;
    }
    
    private void decrementValue() {
        if ( value > min ) {
            value--;
        }
    }
    
    private void incrementValue() {
        if ( value < max ) {
            value++;
        }
    }
    
    private class GuiButtonChangeValue extends GuiButton {
        
        private final double arrowAngle;
        
        public GuiButtonChangeValue( double x, double y, double width, double height, double arrowAngle, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
            this.arrowAngle = arrowAngle;
        }
        
        public GuiButtonChangeValue( double x, double y, double width, double height, double arrowAngle ) {
            super( x, y, width, height, "" );
            this.arrowAngle = arrowAngle;
        }

        @Override
        public void draw() {
            
            super.draw();
            
            if ( visible ) {

                engine.setStrokeLineWidth( LINE_WIDTH );

                if ( enabled ) {

                    switch ( mouseState ) {
                        case MOUSE_OUT:
                            drawButtonLabel( TEXT_COLOR );
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
            int pad = 5;
            engine.fillPolygon( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, 3, bounds.width / 2 - pad, arrowAngle, color );
        }
        
    }
    
}
