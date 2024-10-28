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
 * Classe para representação de uma curva Bézier quadrática.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class QuadCurve implements Drawable {
    
    /**
     * Coordenada x do ponto inicial.
     */
    public double x1;
    
    /**
     * Coordenada y do ponto inicial.
     */
    public double y1;
    
    /**
     * Coordenada x do ponto de controle.
     */
    public double cx;
    
    /**
     * Coordenada y do ponto de controle.
     */
    public double cy;
    
    /**
     * Coordenada x do ponto final.
     */
    public double x2;
    
    /**
     * Coordenada y do ponto final.
     */
    public double y2;

    /**
     * Uma uma nova curva Bézier quadrática com valores padrão.
     */
    public QuadCurve() {
    }

    /**
     * Cria uma nova curva Bézier quadrática.
     * 
     * @param x1 Coordenada x inicial.
     * @param y1 Coordenada y inicial.
     * @param cx Coordenada x do ponto de controle.
     * @param cy Coordenada y do ponto de controle.
     * @param x2 Coordenada x final.
     * @param y2 Coordenada y final.
     */
    public QuadCurve( double x1, double y1, double cx, double cy, double x2, double y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.cx = cx;
        this.cy = cy;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawQuadCurve( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillQuadCurve( this, color );
    }

    @Override
    public String toString() {
        return String.format( "QuadCurve[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, cx, cy, x2, x2 );
    }

}
