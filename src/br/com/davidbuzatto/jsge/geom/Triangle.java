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
 * Classe para representação de um triângulo em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Triangle implements Drawable {

    public double x1;
    public double y1;
    public double x2;
    public double y2;
    public double x3;
    public double y3;

    /**
     * Cria um novo triângulo com valores padrão.
     */
    public Triangle() {
    }

    /**
     * Cria um novo triângulo.
     * 
     * @param x1 coordenada x do primeiro vértice.
     * @param y1 coordenada y do primeiro vértice.
     * @param x2 coordenada x do segundo vértice.
     * @param y2 coordenada y do segundo vértice.
     * @param x3 coordenada x do terceiro vértice.
     * @param y3 coordenada y do terceiro vértice.
     */
    public Triangle( double x1, double y1, double x2, double y2, double x3, double y3 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawTriangle( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillTriangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Triangle[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, x2, y2, x3, y3 );
    }

}
