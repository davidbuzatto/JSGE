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
 * Um componente de barra de progresso.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiProgressBar extends GuiComponent {
    
    private double value;
    private double min;
    private double max;
    
    /**
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiProgressBar( double x, double y, double width, double height, double value, double min, double max, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value, min, max );
    }
    
    /**
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente. 
     */
    public GuiProgressBar( double x, double y, double width, double height, double value, double min, double max ) {
        super( x, y, width, height );
        initData( value, min, max );
    }
    
    /**
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiProgressBar( Rectangle bounds, double value, double min, double max, EngineFrame engine ) {
        super( bounds, engine );
        initData( value, min, max );
    }
    
    /**
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente. 
     */
    public GuiProgressBar( Rectangle bounds, double value, double min, double max ) {
        super( bounds );
        initData( value, min, max );
    }
    
    /**
     * 
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente. 
     */
    private void initData( double value, double min, double max ) {
        this.value = value;
        this.min = min;
        this.max = max;
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                drawProgressBar(BACKGROUND_COLOR, BORDER_COLOR, PROGRESS_BAR_BACKGROUND_COLOR );
            } else {
                drawProgressBar( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR, PROGRESS_BAR_BACKGROUND_COLOR );
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
     * 
     * @return 
     */
    public double getValue() {
        return value;
    }

    /**
     * 
     * @param value O valor inicial do componente. 
     */
    public void setValue( double value ) {
        this.value = MathUtils.clamp( value, min, max );
    }

    /**
     * 
     * @return 
     */
    public double getMin() {
        return min;
    }

    /**
     * 
     * @param min O valor mínimo do componente. 
     */
    public void setMin( double min ) {
        this.min = min;
    }

    /**
     * 
     * @return 
     */
    public double getMax() {
        return max;
    }

    /**
     * 
     * @param max O valor máximo do componente. 
     */
    public void setMax( double max ) {
        this.max = max;
    }
    
    /**
     * 
     * @return 
     */
    public double getPercentage() {
        return MathUtils.clamp( MathUtils.inverseLerp( min, max, value ), 0, 1 );
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
}
