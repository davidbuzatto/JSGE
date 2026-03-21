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
 * An integer selector component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiSpinner extends GuiComponent {
    
    private int value;
    private int min;
    private int max;
    
    protected GuiChangeValueButton leftButton;
    protected GuiChangeValueButton rightButton;
    
    /**
     * Creates the component.
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param value The initial value of the component.
     * @param min The minimum value of the component.
     * @param max The maximum value of the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiSpinner( double x, double y, double width, double height, int value, int min, int max, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value, min, max );
        initComponents( engine, 24 );
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
     * @param value The initial value of the component.
     * @param min The minimum value of the component.
     * @param max The maximum value of the component.
     */
    public GuiSpinner( double x, double y, double width, double height, int value, int min, int max ) {
        super( x, y, width, height );
        initData( value, min, max );
        initComponents( null, 24 );
    }
    
    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param value The initial value of the component.
     * @param min The minimum value of the component.
     * @param max The maximum value of the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiSpinner( Rectangle bounds, int value, int min, int max, EngineFrame engine ) {
        super( bounds, engine );
        initData( value, min, max );
        initComponents( engine, 24 );
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param value The initial value of the component.
     * @param min The minimum value of the component.
     * @param max The maximum value of the component.
     */
    public GuiSpinner( Rectangle bounds, int value, int min, int max ) {
        super( bounds );
        initData( value, min, max );
        initComponents( null, 24 );
    }
    
    private void initData( int value, int min, int max ) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.backgroundColor = CONTAINER_BACKGROUND_COLOR;
    }
    
    private void initComponents( EngineFrame engine, double buttonSize ) {
        if ( engine == null ) {
            leftButton = new GuiChangeValueButton( bounds.x, bounds.y, buttonSize, bounds.height, 180 );
            rightButton = new GuiChangeValueButton( bounds.x + bounds.width - buttonSize, bounds.y, buttonSize, bounds.height, 0 );
        } else {
            leftButton = new GuiChangeValueButton( bounds.x, bounds.y, buttonSize, bounds.height, 180, engine );
            rightButton = new GuiChangeValueButton( bounds.x + bounds.width - buttonSize, bounds.y, buttonSize, bounds.height, 0, engine );
        }
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            leftButton.update( delta );
            rightButton.update( delta );
            
            if ( leftButton.isMousePressed() ) {
                decrementValue();
            }
            
            if ( rightButton.isMousePressed() ) {
                incrementValue();
            }
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                if ( mouseState == GuiComponentMouseState.MOUSE_OVER ) {
                    drawSpinner( MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_BORDER_COLOR, MOUSE_OVER_TEXT_COLOR );
                } else {
                    drawSpinner( backgroundColor, borderColor, textColor );
                }
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
    
    /**
     * Gets the current value of the component.
     *
     * @return The current value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the component.
     *
     * @param value The value of the component.
     */
    public void setValue( int value ) {
        this.value = MathUtils.clamp( value, min, max );
    }

    /**
     * Returns the minimum value of the component.
     *
     * @return The minimum value.
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum value of the component.
     *
     * @param min The minimum value of the component.
     */
    public void setMin( int min ) {
        this.min = min;
    }

    /**
     * Returns the maximum value of the component.
     *
     * @return The maximum value.
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum value of the component.
     *
     * @param max The maximum value of the component.
     */
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

    /**
     * Sets the background color of the buttons.
     *
     * @param backgroundColor The background color of the buttons.
     */
    public void setButtonsBackgroundColor( Color backgroundColor ) {
        leftButton.setBackgroundColor( backgroundColor );
        rightButton.setBackgroundColor( backgroundColor );
    }
    
    @Override
    public void setBackgroundColor( Color backgroundColor ) {
        super.setBackgroundColor( backgroundColor );
    }

    @Override
    public void setBorderColor( Color borderColor ) {
        super.setBorderColor( borderColor );
        leftButton.setBorderColor( borderColor );
        rightButton.setBorderColor( borderColor );
    }

    @Override
    public void setTextColor( Color textColor ) {
        super.setTextColor( textColor );
        leftButton.setTextColor( textColor );
        rightButton.setTextColor( textColor );
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        leftButton.move( xAmount, yAmount );
        rightButton.move( xAmount, yAmount );
    }
    
    private class GuiChangeValueButton extends GuiButton {
        
        private final double arrowAngle;
        
        public GuiChangeValueButton( double x, double y, double width, double height, double arrowAngle, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
            this.arrowAngle = arrowAngle;
        }
        
        public GuiChangeValueButton( double x, double y, double width, double height, double arrowAngle ) {
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
                            drawChangeValueButton( textColor );
                            break;
                        case MOUSE_OVER:
                            drawChangeValueButton( MOUSE_OVER_TEXT_COLOR );
                            break;
                        case MOUSE_PRESSED:
                            drawChangeValueButton( MOUSE_DOWN_TEXT_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawChangeValueButton( MOUSE_DOWN_TEXT_COLOR );
                            break;
                    }

                } else {
                    drawChangeValueButton( DISABLED_TEXT_COLOR );
                }

            }
            
        }
        
        private void drawChangeValueButton( Color color ) {
            int pad = 5;
            engine.fillPolygon( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, 3, 6, arrowAngle, color );
        }
        
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        setBackgroundColor( theme.containerBackgroundColor );
        leftButton.apply( theme );
        rightButton.apply( theme );
    }
    
}
