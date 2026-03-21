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
package br.com.davidbuzatto.jsge.core;

import br.com.davidbuzatto.jsge.math.Matrix;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.Vector3;

/**
 * Representation of a camera for controlling the drawing process.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Camera2D {
    
    /**
     * The camera target.
     */
    public Vector2 target;

    /**
     * The camera offset.
     */
    public Vector2 offset;

    /**
     * Clockwise rotation in degrees.
     */
    public double rotation;

    /**
     * Zoom factor.
     */
    public double zoom;

    /**
     * Creates a new camera pointing to coordinate 0.0; 0.0, with zero offset
     * both vertically and horizontally, no rotation and a zoom factor of 1.0.
     */
    public Camera2D() {
        target = new Vector2();
        offset = new Vector2();
        rotation = 0.0;
        zoom = 1.0;
    }
    
    /**
     * Creates a new camera.
     *
     * @param target the target.
     * @param offset the offset.
     * @param rotation the rotation.
     * @param zoom the zoom.
     */
    public Camera2D( Vector2 target, Vector2 offset, double rotation, double zoom ) {
        this.target = target;
        this.offset = offset;
        this.rotation = rotation;
        this.zoom = zoom;
    }
    
    /**
     * Converts a screen coordinate to a 2D world coordinate
     * according to the camera.
     *
     * @param x The x coordinate of the screen position.
     * @param y The y coordinate of the screen position.
     * @return The corresponding point in the 2D world.
     */
    public Vector2 getScreenToWorld( double x, double y ) {
        
        Matrix invMatCamera = getCameraMatrix().invert();
        Vector3 transform = new Vector3( x, y, 0 ).transform( invMatCamera );

        return new Vector2( transform.x, transform.y );
        
    }
    
    /**
     * Converts a screen coordinate to a 2D world coordinate
     * according to the camera.
     *
     * @param point The screen position.
     * @return The corresponding point in the 2D world.
     */
    public Vector2 getScreenToWorld( Vector2 point ) {
        return getScreenToWorld( point.x, point.y );
    }
    
    /**
     * Converts a 2D world coordinate to a screen coordinate
     * according to the camera.
     *
     * @param x The x coordinate of the 2D world position.
     * @param y The y coordinate of the 2D world position.
     * @return The corresponding screen point.
     */
    public Vector2 getWorldToScreen( double x, double y ) {
        
        Matrix matCamera = getCameraMatrix();
        Vector3 transform = new Vector3( x, y, 0 ).transform( matCamera );

        return new Vector2( transform.x, transform.y );
        
    }
    
    /**
     * Converts a 2D world coordinate to a screen coordinate
     * according to the camera.
     *
     * @param point The 2D world position.
     * @return The corresponding screen point.
     */
    public Vector2 getWorldToScreen( Vector2 point ) {
        return getWorldToScreen( point.x, point.y );
    }
    
    /**
     * Gets the 2D camera matrix.
     *
     * @return The 2D camera matrix.
     */
    public Matrix getCameraMatrix() {
        
        // source: https://github.com/raysan5/raylib/blob/master/src/rcore.c
        // The camera in world-space is set by
        //   1. Move it to target
        //   2. Rotate by -rotation and scaling by (1/zoom)
        //      When setting higher scaling, it's more intuitive for the world to become bigger (= camera become smaller),
        //      not for the camera getting bigger, hence the invert. Same deal with rotation
        //   3. Move it by (-offset);
        //      Offset defines target transform relative to screen, but since we're effectively "moving" screen (camera)
        //      we need to do it into opposite direction (inverse transform)

        // Having camera transform in world-space, inverse of it gives the modelview transform
        // Since (A*B*C)' = C'*B'*A', the modelview is
        //   1. Move to offset
        //   2. Rotate and Scale
        //   3. Move by -target
        Matrix matOrigin = Matrix.translate( -target.x, -target.y, 0.0 );
        Matrix matRotation = Matrix.rotate( new Vector3( 0.0, 0.0, 1.0 ), Math.toRadians( rotation ) );
        Matrix matScale = Matrix.scale( zoom, zoom, 1.0 );
        Matrix matTranslation = Matrix.translate( offset.x, offset.y, 0.0 );

        return matOrigin.multiply( matScale.multiply( matRotation ) ).multiply( matTranslation );
        
    }
    
    @Override
    public String toString() {
        return "Camera{" + "target=" + target + ", offset=" + offset + ", rotation=" + rotation + ", zoom=" + zoom + '}';
    }
    
}
