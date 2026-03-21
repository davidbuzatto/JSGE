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
 * Class for representing a star in two dimensions.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Star implements Drawable, Serializable {
    
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
     * Number of tips.
     */
    public int tips;

    /**
     * Radius.
     */
    public double radius;

    /**
     * Rotation angle in degrees, clockwise.
     */
    public double rotation;

    /**
     * Creates a new star with default values.
     */
    public Star() {
    }

    /**
     * Creates a new star.
     *
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param tips Number of tips.
     * @param radius Radius of the circumscribed circle.
     * @param rotation Start angle in degrees (clockwise).
     */
    public Star( double x, double y, int tips, double radius, double rotation ) {
        this.x = x;
        this.y = y;
        this.tips = tips;
        this.radius = radius;
        this.rotation = rotation;
    }

    /**
     * Creates a new star with rotation equal to zero.
     *
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param tips Number of tips.
     * @param radius Radius of the circumscribed circle.
     */
    public Star( double x, double y, int tips, double radius ) {
        this( x, y, tips, radius, 0.0 );
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawStar( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillStar( this, color );
    }

    @Override
    public String toString() {
        return String.format("Star[%.2f, %.2f, %d, %.2f, %.2f]", x, y, tips, radius, rotation );
    }

}
