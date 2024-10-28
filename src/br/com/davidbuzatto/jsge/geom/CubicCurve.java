/*
 * Copyright (C) 2024 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.geom;

import br.com.davidbuzatto.jsge.core.Drawable;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.awt.Paint;

/**
 * Classe para representação de uma curva Bézier cúbica.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CubicCurve implements Drawable {
    
    /**
     * Coordenada x do ponto inicial.
     */
    public double x1;
    
    /**
     * Coordenada y do ponto inicial.
     */
    public double y1;
    
    /**
     * Coordenada x do primeiro ponto de controle.
     */
    public double c1x;
    
    /**
     * Coordenada y do primeiro ponto de controle.
     */
    public double c1y;
    
    /**
     * Coordenada x do segundo ponto de controle.
     */
    public double c2x;
    
    /**
     * Coordenada y do segundo ponto de controle.
     */
    public double c2y;
    
    /**
     * Coordenada x do ponto final.
     */
    public double x2;
    
    /**
     * Coordenada y do ponto final.
     */
    public double y2;

    /**
     * Uma uma nova curva Bézier cúbica com valores padrão.
     */
    public CubicCurve() {
    }

    /**
     * Cria uma nova curva Bézier cúbica.
     * 
     * @param x1 Coordenada x inicial.
     * @param y1 Coordenada y inicial.
     * @param c1x Coordenada x do primeiro ponto de controle.
     * @param c1y Coordenada y do primeiro ponto de controle.
     * @param c2x Coordenada x do segundo ponto de controle.
     * @param c2y Coordenada y do segundo ponto de controle.
     * @param x2 Coordenada x final.
     * @param y2 Coordenada y final.
     */
    public CubicCurve( double x1, double y1, double c1x, double c1y, double c2x, double c2y, double x2, double y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.c1x = c1x;
        this.c1y = c1y;
        this.c2x = c2x;
        this.c2y = c2y;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawCubicCurve( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillCubicCurve( this, color );
    }

    @Override
    public String toString() {
        return String.format( "CubicCurve[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, c1x, c1y, c2x, c2y, x2, x2 );
    }

}
