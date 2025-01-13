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

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.Color;

/**
 * Um componente de barra de progresso.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiProgressBar extends GuiComponent {
    
    private double value;
    private double min;
    private double max;
    
    // TODO: melhorar a chamada de construtores, está horrível...
    public GuiProgressBar( double x, double y, double width, double height, double value, double min, double max, EngineFrame engine ) {
        super( x, y, width, height, engine );
        this.value = value;
        this.min = min;
        this.max = max;
    }
    
    public GuiProgressBar( double x, double y, double width, double height, double value, double min, double max ) {
        super( x, y, width, height );
        this.value = value;
        this.min = min;
        this.max = max;
    }
    
    public GuiProgressBar( Rectangle bounds, double value, double min, double max, EngineFrame engine ) {
        super( bounds, engine );
        this.value = value;
        this.min = min;
        this.max = max;
    }
    
    public GuiProgressBar( Rectangle bounds, double value, double min, double max ) {
        super( bounds );
        this.value = value;
        this.min = min;
        this.max = max;
    }
    
    @Override
    public void update( double delta ) {
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                drawProgressBar( MOUSE_OUT_BACKGROUND_COLOR, MOUSE_OUT_BORDER_COLOR, PROGRESS_BAR_BACKGROUND_COLOR );
            } else {
                drawProgressBar( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR, DISABLED_PROGRESS_BAR_BACKGROUND_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawProgressBar( Color backgroundColor, Color borderColor, Color percentageColor ) {
        double width = bounds.width * getPercentage();
        engine.fillRectangle( bounds, backgroundColor );
        engine.fillRectangle( bounds.x, bounds.y, width, bounds.height, percentageColor );
        engine.drawRectangle( bounds, borderColor );
    }

    public double getValue() {
        return value;
    }

    public void setValue( double value ) {
        this.value = MathUtils.clamp( value, min, max );
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
    
    public double getPercentage() {
        return MathUtils.clamp( MathUtils.inverseLerp( min, max, value ), 0, 1 );
    }
    
}
