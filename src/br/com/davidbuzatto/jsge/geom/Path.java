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
import java.awt.geom.Path2D;
import java.io.Serializable;

/**
 * Class for representing a path in two dimensions.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Path implements Drawable, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * The Path2D of this path.
     */
    public Path2D.Double path;

    /**
     * Creates a new path.
     */
    public Path() {
        path = new Path2D.Double();
    }

    /**
     * Moves to the desired coordinate.
     *
     * @param x X coordinate of the desired point.
     * @param y Y coordinate of the desired point.
     */
    public void moveTo( double x, double y ) {
        path.moveTo( x, y );
    }

    /**
     * Creates a line from the current point to the desired point.
     *
     * @param x X coordinate of the desired point.
     * @param y Y coordinate of the desired point.
     */
    public void lineTo( double x, double y ) {
        path.lineTo( x, y );
    }

    /**
     * Creates a quadratic Bézier curve from the current point to the desired point.
     *
     * @param cx X coordinate of the control point.
     * @param cy Y coordinate of the control point.
     * @param x X coordinate of the desired point.
     * @param y Y coordinate of the desired point.
     */
    public void quadTo( double cx, double cy, double x, double y ) {
        path.quadTo( cx, cy, x, y );
    }

    /**
     * Creates a cubic Bézier curve from the current point to the desired point.
     *
     * @param c1x X coordinate of the first control point.
     * @param c1y Y coordinate of the first control point.
     * @param c2x X coordinate of the second control point.
     * @param c2y Y coordinate of the second control point.
     * @param x X coordinate of the desired point.
     * @param y Y coordinate of the desired point.
     */
    public void cubicTo( double c1x, double c1y, double c2x, double c2y, double x, double y ) {
        path.curveTo( c1x, c1y, c2x, c2y, x, y );
    }

    /**
     * Closes the path, connecting with a line from the start point to the last created point.
     */
    public void close() {
        path.closePath();
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawPath( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillPath( this, color );
    }

    @Override
    public String toString() {
        return path.toString();
    }

}
