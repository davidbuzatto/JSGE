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

import br.com.davidbuzatto.jsge.core.Engine;
import java.awt.Color;

/**
 * Classe para representação de uma curva Bézier cúbica.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CubicCurve implements Drawable {
    
    public double x1;
    public double y1;
    public double c1x;
    public double c1y;
    public double c2x;
    public double c2y;
    public double x2;
    public double y2;

    /**
     * Uma uma nova curva Bézier cúbica com valores padrão.
     */
    public CubicCurve() {
    }

    /**
     * Cria uma nova curva Bézier cúbica.
     * 
     * @param x1 coordenada x inicial.
     * @param y1 coordenada y inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param x2 coordenada x final.
     * @param y2 coordenada y final.
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
    public void draw( Engine engine, Color color ) {
        engine.drawCubicCurve( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillCubicCurve( this, color );
    }

    @Override
    public String toString() {
        return String.format( "CubicCurve[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, c1x, c1y, c2x, c2y, x2, x2 );
    }

}
