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
 * Class for representing a four-dimensional vector.
 *
 * Can also be used to return values with four components.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Vector4 implements Cloneable, Serializable {
    
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
     * Z coordinate.
     */
    public double z;

    /**
     * W coordinate.
     */
    public double w;

    /**
     * Creates a new four-dimensional vector with default values.
     */
    public Vector4() {
    }

    /**
     * Creates a new four-dimensional vector.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param z z coordinate.
     * @param w w coordinate.
     */
    public Vector4( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Creates a vector with all components equal to 1.0.
     *
     * @return A vector with all components equal to 1.0.
     */
    public static Vector4 one() {
        return new Vector4( 1.0, 1.0, 1.0, 1.0 );
    }
    
    /**
     * Adds the current vector to another vector.
     *
     * @param v Another vector.
     * @return A new vector resulting from the addition.
     */
    public Vector4 add( Vector4 v ) {
        return new Vector4( x + v.x, y + v.y, z + v.z, w + v.w );
    }

    /**
     * Adds the given components to the current vector.
     *
     * @param x The x component to add.
     * @param y The y component to add.
     * @param z The z component to add.
     * @param w The w component to add.
     * @return A new vector resulting from the addition.
     */
    public Vector4 add( double x, double y, double z, double w ) {
        return new Vector4( this.x + x, this.y + y, this.z + z, this.w + w );
    }

    /**
     * Adds a value to the current vector.
     *
     * @param value The value to add.
     * @return A new vector with the components added to the given value.
     */
    public Vector4 addValue( double value ) {
        return new Vector4( x + value, y + value, z + value, w + value );
    }
    
    /**
     * Subtracts a vector from the current vector.
     *
     * @param v Another vector.
     * @return A new vector resulting from the subtraction.
     */
    public Vector4 subtract( Vector4 v ) {
        return new Vector4( x - v.x, y - v.y, z - v.z, w - v.w );
    }

    /**
     * Subtracts the given components from the current vector.
     *
     * @param x The x component to subtract.
     * @param y The y component to subtract.
     * @param z The z component to subtract.
     * @param w The w component to subtract.
     * @return A new vector resulting from the subtraction.
     */
    public Vector4 subtract( double x, double y, double z, double w ) {
        return new Vector4( this.x - x, this.y - y, this.z - z, this.w - w );
    }

    /**
     * Subtracts a value from the current vector.
     *
     * @param value The value to subtract.
     * @return A new vector with the components subtracted by the given value.
     */
    public Vector4 subtractValue( double value ) {
        return new Vector4( x - value, y - value, z - value, w - value );
    }
    
    /**
     * Calculates the length of the vector.
     *
     * @return The length.
     */
    public double length() {
        return Math.sqrt( x * x + y * y + z * z + w * w );
    }
    
    /**
     * Calculates the squared length of the vector.
     *
     * @return The length.
     */
    public double lengthSquare() {
        return x * x + y * y + z * z + w * w;
    }
    
    /**
     * Calculates the dot product of the current vector with the given vector.
     *
     * @param v Another vector.
     * @return The dot product.
     */
    public double dotProduct( Vector4 v ) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    /**
     * Calculates the distance between the current vector and another vector.
     *
     * @param v Another vector.
     * @return The distance.
     */
    public double distance( Vector4 v ) {
        return Math.sqrt( ( v.x - x ) * ( v.x - x ) +
                          ( v.y - y ) * ( v.y - y ) +
                          ( v.z - z ) * ( v.z - z ) +
                          ( v.w - w ) * ( v.w - w ) );
    }

    /**
     * Calculates the distance between the current vector and a point defined by its coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @param w The w coordinate of the point.
     * @return The distance.
     */
    public double distance( double x, double y, double z, double w ) {
        return Math.sqrt( ( x - this.x ) * ( x - this.x ) +
                          ( y - this.y ) * ( y - this.y ) +
                          ( z - this.z ) * ( z - this.z ) +
                          ( w - this.w ) * ( w - this.w ) );
    }

    /**
     * Calculates the squared distance between the current vector and another vector.
     *
     * @param v Another vector.
     * @return The squared distance.
     */
    public double distanceSquare( Vector4 v ) {
        return ( v.x - x ) * ( v.x - x ) +
               ( v.y - y ) * ( v.y - y ) +
               ( v.z - z ) * ( v.z - z ) +
               ( v.w - w ) * ( v.w - w );
    }

    /**
     * Calculates the squared distance between the current vector and a point defined by its coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     * @param w The w coordinate of the point.
     * @return The squared distance.
     */
    public double distanceSquare( double x, double y, double z, double w ) {
        return ( x - this.x ) * ( x - this.x ) +
               ( y - this.y ) * ( y - this.y ) +
               ( z - this.z ) * ( z - this.z ) +
               ( w - this.w ) * ( w - this.w );
    }

    /**
     * Scales the current vector, analogous to scalar multiplication.
     *
     * @param scale The scale.
     * @return A new scaled vector.
     */
    public Vector4 scale( double scale ) {
        return new Vector4( x * scale, y * scale, z * scale, w * scale );
    }

    /**
     * Scales the current vector independently per component (non-uniform scaling).
     *
     * @param x The scale factor for the x component.
     * @param y The scale factor for the y component.
     * @param z The scale factor for the z component.
     * @param w The scale factor for the w component.
     * @return A new scaled vector.
     */
    public Vector4 scale( double x, double y, double z, double w ) {
        return new Vector4( this.x * x, this.y * y, this.z * z, this.w * w );
    }

    /**
     * Multiplies the current vector by another vector.
     *
     * @param v Another vector.
     * @return A new vector with the result of the multiplication.
     */
    public Vector4 multiply( Vector4 v ) {
        return new Vector4( x * v.x, y * v.y, z * v.z, w * v.w );
    }

    /**
     * Multiplies the current vector by the given components.
     *
     * @param x The x component to multiply by.
     * @param y The y component to multiply by.
     * @param z The z component to multiply by.
     * @param w The w component to multiply by.
     * @return A new vector with the result of the multiplication.
     */
    public Vector4 multiply( double x, double y, double z, double w ) {
        return new Vector4( this.x * x, this.y * y, this.z * z, this.w * w );
    }

    /**
     * Negates the current vector.
     *
     * @return A new vector with the negation of the current vector.
     */
    public Vector4 negate() {
        return new Vector4( -x, -y, -z, -w );
    }
    
    /**
     * Divides the current vector by another vector.
     *
     * @param v Another vector.
     * @return A new vector with the result of the division.
     */
    public Vector4 divide( Vector4 v ) {
        return new Vector4( x / v.x, y / v.y, z / v.z, w / v.w );
    }

    /**
     * Divides the current vector by the given components.
     *
     * @param x The x component to divide by.
     * @param y The y component to divide by.
     * @param z The z component to divide by.
     * @param w The w component to divide by.
     * @return A new vector with the result of the division.
     */
    public Vector4 divide( double x, double y, double z, double w ) {
        return new Vector4( this.x / x, this.y / y, this.z / z, this.w / w );
    }

    /**
     * Normalizes the current vector.
     *
     * @return A new normalized vector.
     */
    public Vector4 normalize() {

        Vector4 result = new Vector4();
        double length = Math.sqrt( x * x + y * y + z * z + w * w );

        if ( length > 0.0 ) {
            double ilength = 1.0 / length;
            result.x = x * ilength;
            result.y = y * ilength;
            result.z = z * ilength;
            result.w = w * ilength;
        }

        return result;

    }
    
    /**
     * Gets a new vector with the minimum of each component.
     *
     * @param v Another vector.
     * @return A new vector with the minimum of each component.
     */
    public Vector4 min( Vector4 v ) {

        Vector4 result = new Vector4();

        result.x = Math.min( x, v.x );
        result.y = Math.min( y, v.y );
        result.z = Math.min( z, v.z );
        result.w = Math.min( w, v.w );

        return result;

    }

    /**
     * Gets a new vector with the maximum of each component.
     *
     * @param v Another vector.
     * @return A new vector with the maximum of each component.
     */
    public Vector4 max( Vector4 v ) {

        Vector4 result = new Vector4();

        result.x = Math.max( x, v.x );
        result.y = Math.max( y, v.y );
        result.z = Math.max( z, v.z );
        result.w = Math.max( w, v.w );

        return result;

    }
    
    /**
     * Performs linear interpolation between the current vector (start) and another vector (end).
     *
     * @param end End vector.
     * @param amount Amount (0 to 1)
     * @return A vector representing the linear interpolation between two vectors.
     */
    public Vector4 lerp( Vector4 end, double amount ) {
        return new Vector4(
            this.x + ( end.x - this.x ) * amount,
            this.y + ( end.y - this.y ) * amount,
            this.z + ( end.z - this.z ) * amount,
            this.w + ( end.w - this.w ) * amount
        );
    }
    
    /**
     * Creates a new vector moved towards a target.
     *
     * @param target The target.
     * @param maxDistance The maximum distance.
     * @return A new vector moved towards the target.
     */
    public Vector4 moveTowards( Vector4 target, double maxDistance ) {
        
        Vector4 result = new Vector4();
        
        double dx = target.x - x;
        double dy = target.y - y;
        double dz = target.z - z;
        double dw = target.w - w;
        double value = ( dx * dx ) + ( dy * dy ) + ( dz * dz ) + ( dw * dw );
        
        if ( ( value == 0.0 ) || ( ( maxDistance >= 0.0 ) && ( value <= maxDistance * maxDistance ) ) ) {
            return new Vector4( target.x, target.y, target.z, target.w );
        }
        
        double dist = Math.sqrt( value );
        result.x = x + dx / dist * maxDistance;
        result.y = y + dy / dist * maxDistance;
        result.z = z + dz / dist * maxDistance;
        result.w = w + dw / dist * maxDistance;
        
        return result;
        
    }
    
    /**
     * Inverts the current vector.
     *
     * @return A new inverted vector.
     */
    public Vector4 invert() {
        return new Vector4( 1.0 / x, 1.0 / y, 1.0 / z, 1.0 / w );
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Vector4 clone = (Vector4) super.clone();
        clone.x = x;
        clone.y = y;
        clone.z = z;
        clone.w = w;
        return clone;
    }
    
    @Override
    public int hashCode(  ) {
        int hash = 7;
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.x ) ^ ( Double.doubleToLongBits( this.x ) >>> 32 ) );
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.y ) ^ ( Double.doubleToLongBits( this.y ) >>> 32 ) );
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.z ) ^ ( Double.doubleToLongBits( this.z ) >>> 32 ) );
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.w ) ^ ( Double.doubleToLongBits( this.w ) >>> 32 ) );
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
        final Vector4 other = (Vector4) obj;
        if ( Double.doubleToLongBits( this.x ) != Double.doubleToLongBits( other.x ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.y ) != Double.doubleToLongBits( other.y ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.z ) != Double.doubleToLongBits( other.z ) ) {
            return false;
        }
        return Double.doubleToLongBits( this.w ) == Double.doubleToLongBits( other.w );
    }

    @Override
    public String toString() {
        return String.format( "Vector4[%.2f, %.2f, %.2f, %.2f]", x, y, z, w );
    }
        
}
