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
 * Class for representing a ring in two dimensions.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Ring implements Drawable, Serializable {
    
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
     * Inner radius.
     */
    public double innerRadius;

    /**
     * Outer radius.
     */
    public double outerRadius;

    /**
     * Start angle, in degrees, clockwise.
     */
    public double startAngle;

    /**
     * End angle, in degrees, clockwise.
     */
    public double endAngle;

    /**
     * Creates a new ring with default values.
     */
    public Ring() {
    }

    /**
     * Creates a new ring.
     *
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     */
    public Ring( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle ) {
        this.x = x;
        this.y = y;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawRing( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillRing( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Ring[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x, y, innerRadius, outerRadius, startAngle, endAngle );
    }

}
