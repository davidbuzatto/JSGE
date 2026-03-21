/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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
 * Class for representing a triangle in two dimensions.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Triangle implements Drawable, Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * X coordinate of the first vertex.
     */
    public double x1;

    /**
     * Y coordinate of the first vertex.
     */
    public double y1;

    /**
     * X coordinate of the second vertex.
     */
    public double x2;

    /**
     * Y coordinate of the second vertex.
     */
    public double y2;

    /**
     * X coordinate of the third vertex.
     */
    public double x3;

    /**
     * Y coordinate of the third vertex.
     */
    public double y3;

    /**
     * Creates a new triangle with default values.
     */
    public Triangle() {
    }

    /**
     * Creates a new triangle. Provide the vertices in clockwise order.
     *
     * @param x1 X coordinate of the first vertex.
     * @param y1 Y coordinate of the first vertex.
     * @param x2 X coordinate of the second vertex.
     * @param y2 Y coordinate of the second vertex.
     * @param x3 X coordinate of the third vertex.
     * @param y3 Y coordinate of the third vertex.
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
