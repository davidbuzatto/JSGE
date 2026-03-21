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
 * Class for representing a rectangle in two dimensions.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Rectangle implements Drawable, Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * X coordinate of the top-left vertex.
     */
    public double x;

    /**
     * Y coordinate of the top-left vertex.
     */
    public double y;

    /**
     * Width.
     */
    public double width;

    /**
     * Height.
     */
    public double height;

    /**
     * Creates a new rectangle with default values.
     */
    public Rectangle() {
    }

    /**
     * Creates a new rectangle.
     *
     * @param x X coordinate of the top-left vertex.
     * @param y Y coordinate of the top-left vertex.
     * @param width Width.
     * @param height Height.
     */
    public Rectangle( double x, double y, double width, double height ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawRectangle( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillRectangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Rectangle[%.2f, %.2f, %.2f, %.2f]", x, y, width, height );
    }

}
