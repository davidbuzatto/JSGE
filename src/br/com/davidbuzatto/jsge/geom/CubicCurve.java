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
 * Class for representing a cubic Bézier curve.
 *
 * @author Prof. Dr. David Buzatto
 */
public class CubicCurve implements Drawable, Serializable {
    
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
     * X coordinate of the first control point.
     */
    public double c1x;

    /**
     * Y coordinate of the first control point.
     */
    public double c1y;

    /**
     * X coordinate of the second control point.
     */
    public double c2x;

    /**
     * Y coordinate of the second control point.
     */
    public double c2y;

    /**
     * X coordinate of the end point.
     */
    public double x2;

    /**
     * Y coordinate of the end point.
     */
    public double y2;

    /**
     * Creates a new cubic Bézier curve with default values.
     */
    public CubicCurve() {
    }

    /**
     * Creates a new cubic Bézier curve.
     *
     * @param x1 Start x coordinate.
     * @param y1 Start y coordinate.
     * @param c1x X coordinate of the first control point.
     * @param c1y Y coordinate of the first control point.
     * @param c2x X coordinate of the second control point.
     * @param c2y Y coordinate of the second control point.
     * @param x2 End x coordinate.
     * @param y2 End y coordinate.
     */
    public CubicCurve( double x1, double y1, double c1x, double c1y, double c2x, double c2y, double x2, double y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.c1x = c1x;
        this.c1y = c1y;
        this.c2x = c2x;
        this.c2y = c2y;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawCubicCurve( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillCubicCurve( this, color );
    }

    @Override
    public String toString() {
        return String.format( "CubicCurve[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, c1x, c1y, c2x, c2y, x2, y2 );
    }

}
