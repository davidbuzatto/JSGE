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
 * Classe para representação de um triângulo em duas dimensões.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Triangle implements Drawable {

    /**
     * Coordenada x do primeiro vértice.
     */
    public double x1;

    /**
     * Coordenada y do primeiro vértice.
     */
    public double y1;

    /**
     * Coordenada x do segundo vértice.
     */
    public double x2;

    /**
     * Coordenada y do segundo vértice.
     */
    public double y2;

    /**
     * Coordenada x do terceiro vértice.
     */
    public double x3;

    /**
     * Coordenada y do terceiro vértice.
     */
    public double y3;

    /**
     * Cria um novo triângulo com valores padrão.
     */
    public Triangle() {
    }

    /**
     * Cria um novo triângulo. Forneça os vértices no sentido horário.
     *
     * @param x1 Coordenada x do primeiro vértice.
     * @param y1 Coordenada y do primeiro vértice.
     * @param x2 Coordenada x do segundo vértice.
     * @param y2 Coordenada y do segundo vértice.
     * @param x3 Coordenada x do terceiro vértice.
     * @param y3 Coordenada y do terceiro vértice.
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
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawTriangle( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillTriangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Triangle[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, x2, y2, x3, y3 );
    }

}
