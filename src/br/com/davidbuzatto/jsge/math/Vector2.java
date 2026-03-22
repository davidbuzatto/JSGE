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
package br.com.davidbuzatto.jsge.math;

import java.io.Serializable;

/**
 * Class for representing a two-dimensional vector.
 * Also used to represent 2D points in various parts of the Engine API.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Vector2 implements Cloneable, Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * X coordinate.
     */
    public double x;

    /**
     * Y coordinate.
     */
    public double y;

    /**
     * Creates a new two-dimensional vector with default values.
     */
    public Vector2() {
    }
    
    /**
     * Creates a new two-dimensional vector.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public Vector2( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the current vector to another vector.
     *
     * @param v Another vector.
     * @return A new vector resulting from the addition.
     */
    public Vector2 add( Vector2 v ) {
        return new Vector2( x + v.x, y + v.y );
    }

    /**
     * Adds the given components to the current vector.
     *
     * @param x The x component to add.
     * @param y The y component to add.
     * @return A new vector resulting from the addition.
     */
    public Vector2 add( double x, double y ) {
        return new Vector2( this.x + x, this.y + y );
    }

    /**
     * Adds a value to the current vector.
     *
     * @param value The value to add.
     * @return A new vector with the components added to the given value.
     */
    public Vector2 addValue( double value ) {
        return new Vector2( x + value, y + value );
    }

    /**
     * Subtracts a vector from the current vector.
     *
     * @param v Another vector.
     * @return A new vector resulting from the subtraction.
     */
    public Vector2 subtract( Vector2 v ) {
        return new Vector2( x - v.x, y - v.y );
    }

    /**
     * Subtracts the given components from the current vector.
     *
     * @param x The x component to subtract.
     * @param y The y component to subtract.
     * @return A new vector resulting from the subtraction.
     */
    public Vector2 subtract( double x, double y ) {
        return new Vector2( this.x - x, this.y - y );
    }

    /**
     * Subtracts a value from the current vector.
     *
     * @param value The value to subtract.
     * @return A new vector with the components subtracted by the given value.
     */
    public Vector2 subtractValue( double value ) {
        return new Vector2( x - value, y - value );
    }
    
    /**
     * Scales the current vector, analogous to scalar multiplication.
     *
     * @param scale The scale.
     * @return A new scaled vector.
     */
    public Vector2 scale( double scale ) {
        return new Vector2( x * scale, y * scale );
    }

    /**
     * Scales the current vector independently per component (non-uniform scaling).
     *
     * @param x The scale factor for the x component.
     * @param y The scale factor for the y component.
     * @return A new scaled vector.
     */
    public Vector2 scale( double x, double y ) {
        return new Vector2( this.x * x, this.y * y );
    }

    /**
     * Multiplies the current vector by another vector.
     *
     * @param v Another vector.
     * @return A new vector with the result of the multiplication.
     */
    public Vector2 multiply( Vector2 v ) {
        return new Vector2( x * v.x, y * v.y );
    }

    /**
     * Multiplies the current vector by the given components.
     *
     * @param x The x component to multiply by.
     * @param y The y component to multiply by.
     * @return A new vector with the result of the multiplication.
     */
    public Vector2 multiply( double x, double y ) {
        return new Vector2( this.x * x, this.y * y );
    }

    /**
     * Calculates the length of the vector.
     *
     * @return The length.
     */
    public double length() {
        return Math.sqrt( x * x + y * y );
    }
    
    /**
     * Calculates the squared length of the vector.
     *
     * @return The length.
     */
    public double lengthSquare() {
        return x * x + y * y;
    }
    
    /**
     * Calculates the dot product of the current vector with the given vector.
     *
     * @param v Another vector.
     * @return The dot product.
     */
    public double dotProduct( Vector2 v ) {
        return x * v.x + y * v.y;
    }

    /**
     * Calculates the distance between the current vector and another vector.
     *
     * @param v Another vector.
     * @return The distance.
     */
    public double distance( Vector2 v ) {
        return Math.sqrt( ( v.x - x ) * ( v.x - x ) +
                          ( v.y - y ) * ( v.y - y ) );
    }

    /**
     * Calculates the distance between the current vector and a point defined by its coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return The distance.
     */
    public double distance( double x, double y ) {
        return Math.sqrt( ( x - this.x ) * ( x - this.x ) +
                          ( y - this.y ) * ( y - this.y ) );
    }

    /**
     * Calculates the squared distance between the current vector and another vector.
     *
     * @param v Another vector.
     * @return The squared distance.
     */
    public double distanceSquare( Vector2 v ) {
        return ( v.x - x ) * ( v.x - x ) +
               ( v.y - y ) * ( v.y - y );
    }

    /**
     * Calculates the squared distance between the current vector and a point defined by its coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return The squared distance.
     */
    public double distanceSquare( double x, double y ) {
        return ( x - this.x ) * ( x - this.x ) +
               ( y - this.y ) * ( y - this.y );
    }

    /**
     * Calculates the angle between the current vector and another vector, where
     * the angle is calculated from the origin point (0, 0).
     *
     * @param v Another vector.
     * @return The angle between the vectors.
     */
    public double angle( Vector2 v ) {

        double dot = x * v.x + y * v.y;
        double det = x * v.y - y * v.x;

        return Math.atan2( det, dot );

    }

    /**
     * Negates the current vector.
     *
     * @return A new vector with the negation of the current vector.
     */
    public Vector2 negate() {
        return new Vector2( -x, -y );
    }

    /**
     * Divides the current vector by another vector.
     *
     * @param v Another vector.
     * @return A new vector with the result of the division.
     */
    public Vector2 divide( Vector2 v ) {
        return new Vector2( x / v.x, y / v.y );
    }

    /**
     * Divides the current vector by the given components.
     *
     * @param x The x component to divide by.
     * @param y The y component to divide by.
     * @return A new vector with the result of the division.
     */
    public Vector2 divide( double x, double y ) {
        return new Vector2( this.x / x, this.y / y );
    }

    /**
     * Normalizes the current vector.
     *
     * @return A new normalized vector.
     */
    public Vector2 normalize() {

        Vector2 result = new Vector2();
        double length = Math.sqrt( x * x + y * y );

        if ( length > 0.0 ) {
            double ilength = 1.0 / length;
            result.x = x * ilength;
            result.y = y * ilength;
        }

        return result;

    }

    /**
     * Transforms the current vector by a given matrix.
     *
     * @param mat The matrix.
     * @return A new transformed vector.
     */
    public Vector2 transform( Matrix mat ) {
        
        Vector2 result = new Vector2();

        double z = 0;

        result.x = mat.m0*x + mat.m4*y + mat.m8*z + mat.m12;
        result.y = mat.m1*x + mat.m5*y + mat.m9*z + mat.m13;

        return result;
        
    }

    /**
     * Performs linear interpolation between the current vector (start) and another vector (end).
     *
     * @param end End vector.
     * @param amount Amount (0 to 1)
     * @return A vector representing the linear interpolation between two vectors.
     */
    public Vector2 lerp( Vector2 end, double amount ) {
        return new Vector2(
            this.x + ( end.x - this.x ) * amount,
            this.y + ( end.y - this.y ) * amount
        );
    }

    /**
     * Calculates the reflection of the current vector by a normal vector.
     *
     * @param normal Normal vector.
     * @return A new reflected vector.
     */
    public Vector2 reflect( Vector2 normal ) {

        Vector2 result = new Vector2();

        double dotProduct = ( x * normal.x + y * normal.y ); // dot product

        result.x = x - ( 2.0 * normal.x ) * dotProduct;
        result.y = y - ( 2.0 * normal.y ) * dotProduct;

        return result;

    }

    /**
     * Gets a new vector with the minimum of each component.
     *
     * @param v Another vector.
     * @return A new vector with the minimum of each component.
     */
    public Vector2 min( Vector2 v ) {

        Vector2 result = new Vector2();

        result.x = Math.min( x, v.x );
        result.y = Math.min( y, v.y );

        return result;

    }

    /**
     * Gets a new vector with the maximum of each component.
     *
     * @param v Another vector.
     * @return A new vector with the maximum of each component.
     */
    public Vector2 max( Vector2 v ) {

        Vector2 result = new Vector2();

        result.x = Math.max( x, v.x );
        result.y = Math.max( y, v.y );

        return result;

    }

    /**
     * Rotates the current vector by an angle (in radians).
     *
     * @param angle The angle.
     * @return A new rotated vector.
     */
    public Vector2 rotate( double angle ) {

        Vector2 result = new Vector2();

        double cos = Math.cos( angle );
        double sin = Math.sin( angle );

        result.x = x * cos - y * sin;
        result.y = x * sin + y * cos;

        return result;

    }

    /**
     * Creates a new vector moved towards a target.
     *
     * @param target The target.
     * @param maxDistance The maximum distance.
     * @return A new vector moved towards the target.
     */
    public Vector2 moveTowards( Vector2 target, double maxDistance ) {

        Vector2 result = new Vector2();

        double dx = target.x - x;
        double dy = target.y - y;
        double value = dx * dx + dy * dy;

        if ( ( value == 0 ) || ( ( maxDistance >= 0 ) && ( value <= maxDistance * maxDistance ) ) ) {
            return new Vector2( target.x, target.y );
        }

        double dist = Math.sqrt( value );

        result.x = x + dx / dist * maxDistance;
        result.y = y + dy / dist * maxDistance;

        return result;

    }

    /**
     * Inverts the current vector.
     *
     * @return A new inverted vector.
     */
    public Vector2 invert() {
        return new Vector2( 1.0 / x, 1.0 / y );
    }

    /**
     * Clamps the current vector between two vectors.
     *
     * @param min The minimum vector.
     * @param max The maximum vector.
     * @return A new vector clamped between the minimum and maximum vectors.
     */
    public Vector2 clamp( Vector2 min, Vector2 max ) {

        Vector2 result = new Vector2();

        result.x = Math.min( max.x, Math.max( min.x, x ) );
        result.y = Math.min( max.y, Math.max( min.y, y ) );

        return result;

    }

    /**
     * Clamps the magnitude of the current vector between minimum and maximum.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A new vector with the clamped magnitude.
     */
    public Vector2 clampValue( double min, double max ) {

        Vector2 result = new Vector2( x, y );

        double length = x * x + y * y;

        if ( length > 0.0 ) {

            length = Math.sqrt( length );

            double scale = 1;
            if ( length < min ) {
                scale = min / length;
            } else if ( length > max ) {
                scale = max / length;
            }

            result.x = x * scale;
            result.y = y * scale;

        }

        return result;

    }
    
    /**
     * Creates a vector with both components equal to 1.0.
     *
     * @return A vector with both components equal to 1.0.
     */
    public static Vector2 one() {
        return new Vector2( 1.0, 1.0 );
    }
    
    /**
     * Computes the direction of a refracted ray.
     *
     * @param v The normalized direction of the incoming ray.
     * @param n The normalized normal vector of the interface between the two optical media.
     * @param r The ratio of the refractive index of the medium where the ray arrives to
     * the refractive index of the other medium at the surface.
     * @return The direction of the refracted ray.
     */
    public static Vector2 refract( Vector2 v, Vector2 n, double r ) {

        Vector2 result = new Vector2();

        double dot = v.x * n.x + v.y * n.y;
        double d = 1.0 - r * r * (1.0 - dot * dot);

        if ( d >= 0.0 ) {
            d = Math.sqrt( d );
            result.x = r * v.x - (r * dot + d) * n.x;
            result.y = r * v.y - (r * dot + d) * n.y;
        }

        return result;
            
    }
    
    /**
     * Calculates the relative angle between two lines defined by the vectors.
     *
     * @param v1 A vector.
     * @param v2 Another vector.
     * @return The relative angle of the two lines defined by the two vectors.
     */
    public static double lineAngle( Vector2 v1, Vector2 v2 ) {
        return Math.atan2( v2.y - v1.y, v2.x - v1.x );
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Vector2 clone = (Vector2) super.clone();
        clone.x = x;
        clone.y = y;
        return clone;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) ( Double.doubleToLongBits( this.x ) ^ ( Double.doubleToLongBits( this.x ) >>> 32 ) );
        hash = 83 * hash + (int) ( Double.doubleToLongBits( this.y ) ^ ( Double.doubleToLongBits( this.y ) >>> 32 ) );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Vector2 other = (Vector2) obj;
        if ( Double.doubleToLongBits( this.x ) != Double.doubleToLongBits( other.x ) ) {
            return false;
        }
        return Double.doubleToLongBits( this.y ) == Double.doubleToLongBits( other.y );
    }
    
    @Override
    public String toString() {
        return String.format( "Vector2[%.2f, %.2f]", x, y );
    }

}
