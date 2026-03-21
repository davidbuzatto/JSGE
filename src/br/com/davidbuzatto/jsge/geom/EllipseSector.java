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
 * Class for representing an ellipse sector in two dimensions.
 *
 * @author Prof. Dr. David Buzatto
 */
public class EllipseSector implements Drawable, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * X coordinate of the center.
     */
    public double x;

    /**
     * Y coordinate of the center.
     */
    public double y;

    /**
     * Horizontal radius.
     */
    public double radiusH;

    /**
     * Vertical radius.
     */
    public double radiusV;

    /**
     * Start angle, in degrees, clockwise.
     */
    public double startAngle;

    /**
     * End angle, in degrees, clockwise.
     */
    public double endAngle;

    /**
     * Creates a new ellipse sector with default values.
     */
    public EllipseSector() {
    }

    /**
     * Creates a new ellipse sector.
     *
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     */
    public EllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle ) {
        this.x = x;
        this.y = y;
        this.radiusH = radiusH;
        this.radiusV = radiusV;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawEllipseSector( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillEllipseSector( this, color );
    }

    @Override
    public String toString() {
        return String.format( "EllipseSector[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x, y, radiusH, radiusV, startAngle, endAngle );
    }

}
