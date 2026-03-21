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
package br.com.davidbuzatto.jsge.collision.aabb;

import br.com.davidbuzatto.jsge.core.Drawable;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.awt.Paint;

/**
 * Representation of an Axis-aligned Bounding Box (AABB).
 *
 * Holds some properties to guide the execution of AABBQuadtree, such as
 * the AABB type (static or dynamic), whether it is an active AABB, and which
 * object it references.
 *
 * @author Prof. Dr. David Buzatto
 */
public class AABB implements Drawable {

    /**
     * AABB type.
     */
    public static enum Type {

        /**
         * Static type. Indicates that in the quadtree, static AABBs do not interact
         * with each other, only with dynamic AABBs.
         */
        STATIC,

        /**
         * Dynamic type. Indicates that in the quadtree, dynamic AABBs interact
         * with both static and dynamic AABBs.
         */
        DYNAMIC;

    }
    
    /**
     * X coordinate of the upper-left vertex.
     */
    public double x1;

    /**
     * Y coordinate of the upper-left vertex.
     */
    public double y1;

    /**
     * X coordinate of the lower-right vertex.
     */
    public double x2;

    /**
     * Y coordinate of the lower-right vertex.
     */
    public double y2;

    /**
     * Width.
     */
    public double width;

    /**
     * Height.
     */
    public double height;

    /**
     * AABB type.
     */
    public Type type;

    /**
     * Indicates whether the AABB is active.
     */
    public boolean active;

    /**
     * Object referenced by this AABB, i.e., the object that originated
     * this AABB.
     */
    public Object referencedObject;

    /**
     * A nearby AABB.
     */
    public AABB nearby;

    /**
     * Constructs a static AABB with the upper-left vertex at 0, 0 and
     * lower-right vertex at 50, 50 that does not reference any object.
     */
    public AABB() {
        this( 0, 0, 50, 50, Type.STATIC, null );
    }

    /**
     * Constructs a static AABB.
     *
     * @param x1 X coordinate of the upper-left vertex.
     * @param y1 Y coordinate of the upper-left vertex.
     * @param x2 X coordinate of the lower-right vertex.
     * @param y2 Y coordinate of the lower-right vertex.
     * @param referencedObject Object referenced by this AABB, i.e., the
     * object that originated it.
     */
    public AABB( double x1, double y1, double x2, double y2, Object referencedObject ) {
        this( x1, y1, x2, y2, Type.STATIC, referencedObject );
    }
    
    /**
     * Constructs an active AABB.
     *
     * @param x1 X coordinate of the upper-left vertex.
     * @param y1 Y coordinate of the upper-left vertex.
     * @param x2 X coordinate of the lower-right vertex.
     * @param y2 Y coordinate of the lower-right vertex.
     * @param type The AABB type.
     * @param referencedObject Object referenced by this AABB, i.e., the
     * object that originated it.
     */
    public AABB( double x1, double y1, double x2, double y2, Type type, Object referencedObject ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
        this.type = type;
        this.active = true;
        this.referencedObject = referencedObject;
        this.nearby = null;
    }

    /**
     * Checks whether the current AABB collides with another AABB.
     *
     * @param aabb An AABB.
     * @return True if the AABBs collide, false otherwise.
     */
    public boolean collidesWith( AABB aabb ) {

        if ( x1 > aabb.x2 || aabb.x1 > x2 ) {
            return false;
        }

        if ( y2 < aabb.y1 || aabb.y2 < y1 ) {
            return false;
        }

        return true;

    }

    /**
     * Moves the AABB.
     *
     * @param deltaX Displacement along x.
     * @param deltaY Displacement along y.
     */
    public void move( double deltaX, double deltaY ) {
        x1 += deltaX;
        y1 += deltaY;
        x2 += deltaX;
        y2 += deltaY;
    }
    
    /**
     * Repositions the AABB.
     *
     * @param x1 X coordinate of the upper-left vertex.
     * @param y1 Y coordinate of the upper-left vertex.
     */
    public void moveTo( double x1, double y1 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + this.width;
        this.y2 = y1 + this.height;
    }

    /**
     * Sets the size of the AABB, recalculating x2 and y2.
     *
     * @param width Width.
     * @param height Height.
     */
    public void setSize( double width, double height ) {
        x2 = x1 + width;
        y2 = y1 + height;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }
    
    @Override
    public void draw( EngineFrame engine, Paint paint ) {
        engine.drawAABB( this, paint );
    }

    @Override
    public void fill( EngineFrame engine, Paint paint ) {
        engine.fillAABB( this, paint );
    }

}
