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
 * Class for representing a line in two dimensions.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Line implements Drawable, Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * X coordinate of the start point.
     */
    public double x1;

    /**
     * Y coordinate of the start point.
     */
    public double y1;

    /**
     * X coordinate of the end point.
     */
    public double x2;

    /**
     * Y coordinate of the end point.
     */
    public double y2;

    /**
     * Creates a new line with default values.
     */
    public Line() {
    }

    /**
     * Creates a new line.
     *
     * @param x1 Start x coordinate.
     * @param y1 Start y coordinate.
     * @param x2 End x coordinate.
     * @param y2 End y coordinate.
     */
    public Line( double x1, double y1, double x2, double y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawLine( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.drawLine( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Line[%.2f, %.2f, %.2f, %.2f]", x1, y1, x2, y2 );
    }

}
