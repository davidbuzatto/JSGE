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
package br.com.davidbuzatto.jsge.geom;

import br.com.davidbuzatto.jsge.core.Drawable;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.awt.Paint;
import java.io.Serializable;

/**
 * Classe para representação de um círculo em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Circle implements Drawable, Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * Coordenada x do centro.
     */
    public double x;
    
    /**
     * Coordenada y do centro.
     */
    public double y;
    
    /**
     * Raio.
     */
    public double radius;

    /**
     * Cria um círculo com valores padrão.
     */
    public Circle() {
    }

    /**
     * Cria um círculo.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radius Raio.
     */
    public Circle( double x, double y, double radius ) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawCircle( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillCircle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Circle[%.2f, %.2f, %.2f]", x, y, radius );
    }

}
