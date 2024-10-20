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
 * Classe para representação de um vetor de duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector2 implements Drawable {

    /**
     * Coordenada x.
     */
    public double x;
    
    /**
     * Coordenada y.
     */
    public double y;

    /**
     * Cria um novo vetor de duas dimensões com valores padrão.
     */
    public Vector2() {
    }
    
    /**
     * Cria um novo vetor de duas dimensões.
     * 
     * @param x Coordenada x.
     * @param y Coordenada y.
     */
    public Vector2( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawLine( 0, 0, x, y, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        throw new UnsupportedOperationException( "can'f fill a 2D vector." );
    }

    @Override
    public String toString() {
        return String.format( "Vector2[%.2f, %.2f]", x, y );
    }

}
