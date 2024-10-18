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
 * Classe para representação de um polígono regular em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Polygon implements Drawable {

    public double x;
    public double y;
    public int sides;
    public double radius;
    public double rotation;

    /**
     * Cria um novo polígono regular com valores padrão.
     */
    public Polygon() {
    }

    /**
     * Cria um novo polígono regular.
     * 
     * @param x coordenada x do centro.
     * @param y coordenada y do centro.
     * @param sides quantidade de lados.
     * @param radius raio do círculo circunscrito.
     * @param rotation ângulo inicial em graus (sentido horário).
     */
    public Polygon( double x, double y, int sides, double radius, double rotation ) {
        this.x = x;
        this.y = y;
        this.sides = sides;
        this.radius = radius;
        this.rotation = rotation;
    }

    public Polygon( double x, double y, int sides, double radius ) {
        this( x, y, sides, radius, 0.0 );
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawPolygon( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillPolygon( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Polygon[%.2f, %.2f, %d, %.2f, %.2f]", x, y, sides, radius, rotation );
    }

}