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
 * Classe para representação de um polígono regular em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Polygon implements Drawable {

    /**
     * Coordenada x do centro.
     */
    public double x;
    
    /**
     * Coordenada y do centro.
     */
    public double y;
    
    /**
     * Quantidade de lados.
     */
    public int sides;
    
    /**
     * Raio.
     */
    public double radius;
    
    /**
     * Ângulo de rotação em gradus, no sentido horário.
     */
    public double rotation;

    /**
     * Cria um novo polígono regular com valores padrão.
     */
    public Polygon() {
    }

    /**
     * Cria um novo polígono regular.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param sides Quantidade de lados.
     * @param radius Raio do círculo circunscrito.
     * @param rotation Ângulo inicial em graus (sentido horário).
     */
    public Polygon( double x, double y, int sides, double radius, double rotation ) {
        this.x = x;
        this.y = y;
        this.sides = sides;
        this.radius = radius;
        this.rotation = rotation;
    }

    /**
     * Cria um novo polígono regular com rotação igual a zero.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param sides Quantidade de lados.
     * @param radius Raio do círculo circunscrito.
     */
    public Polygon( double x, double y, int sides, double radius ) {
        this( x, y, sides, radius, 0.0 );
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawPolygon( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillPolygon( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Polygon[%.2f, %.2f, %d, %.2f, %.2f]", x, y, sides, radius, rotation );
    }

}
