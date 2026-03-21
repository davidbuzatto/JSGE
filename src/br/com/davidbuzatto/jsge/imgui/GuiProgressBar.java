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
 * A progress bar component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiProgressBar extends GuiComponent {
    
    private double value;
    private double min;
    private double max;
    
    private Color progressFillColor;
    
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
    public GuiProgressBar( double x, double y, double width, double height, double value, double min, double max, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value, min, max );
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
    public GuiProgressBar( double x, double y, double width, double height, double value, double min, double max ) {
        super( x, y, width, height );
        initData( value, min, max );
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
    public GuiProgressBar( Rectangle bounds, double value, double min, double max, EngineFrame engine ) {
        super( bounds, engine );
        initData( value, min, max );
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
    public GuiProgressBar( Rectangle bounds, double value, double min, double max ) {
        super( bounds );
        initData( value, min, max );
    }
    
    private void initData( double value, double min, double max ) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.progressFillColor = PROGRESS_BAR_PROGRESS_FILL_COLOR;
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                drawProgressBar( backgroundColor, borderColor, progressFillColor );
            } else {
                drawProgressBar( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR, progressFillColor );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawProgressBar( Color backgroundColor, Color borderColor, Color percentageColor ) {
        double width = bounds.width * getPercentage();
        engine.fillRectangle( bounds, backgroundColor );
        if ( enabled ) {
            engine.fillRectangle( bounds.x, bounds.y, width, bounds.height, percentageColor );
        }
        engine.drawRectangle( bounds, borderColor );
    }

    /**
     * Gets the current value of the component.
     *
     * @return The current value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the component.
     *
     * @param value The value of the component.
     */
    public void setValue( double value ) {
        this.value = MathUtils.clamp( value, min, max );
    }

    /**
     * Returns the minimum value of the component.
     *
     * @return The minimum value.
     */
    public double getMin() {
        return min;
    }

    /**
     * Sets the minimum value of the component.
     *
     * @param min The minimum value of the component.
     */
    public void setMin( double min ) {
        this.min = min;
    }

    /**
     * Returns the maximum value of the component.
     *
     * @return The maximum value.
     */
    public double getMax() {
        return max;
    }

    /**
     * Sets the maximum value of the component.
     *
     * @param max The maximum value of the component.
     */
    public void setMax( double max ) {
        this.max = max;
    }

    /**
     * Returns the progress percentage of the progress bar
     * based on the current value and the minimum and maximum values.
     *
     * @return The progress bar percentage.
     */
    public double getPercentage() {
        return MathUtils.clamp( MathUtils.inverseLerp( min, max, value ), 0, 1 );
    }

    /**
     * Gets the fill color of the progress bar progress.
     *
     * @return The fill color of the progress.
     */
    public Color getProgressFillColor() {
        return progressFillColor;
    }

    /**
     * Sets the fill color of the progress bar progress.
     *
     * @param progressColor The fill color of the progress.
     */
    public void setProgressFillColor( Color progressColor ) {
        this.progressFillColor = progressColor;
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        setProgressFillColor( theme.progressBarProgressFillColor );
    }
    
}
