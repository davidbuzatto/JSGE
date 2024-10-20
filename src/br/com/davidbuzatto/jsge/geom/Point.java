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
 * Classe para representação de um ponto em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Point implements Drawable {

    /**
     * Coordenada x.
     */
    public double x;
    
    /**
     * Coordenada y.
     */
    public double y;

    /**
     * Cria um novo ponto com valores padrão.
     */
    public Point() {
    }

    /**
     * Cria um novo ponto.
     * 
     * @param x Coordenada x.
     * @param y Coordenada y.
     */
    public Point( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawPixel( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        throw new UnsupportedOperationException( "can'f fill a point." );
    }

    @Override
    public String toString() {
        return String.format( "Point[%.2f, %.2f]", x, y );
    }

}
