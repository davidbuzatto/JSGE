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
 * Class for representing a three-dimensional vector.
 *
 * Can also be used to return values with three components, such as
 * 3D points, etc.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Vector3 implements Cloneable, Serializable {
    
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
     * Creates a new three-dimensional vector with default values.
     */
    public Vector3() {
    }

    /**
     * Creates a new three-dimensional vector.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param z z coordinate.
     */
    public Vector3( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Adds the current vector to another vector.
     *
     * @param v Another vector.
     * @return A new vector resulting from the addition.
     */
    public Vector3 add( Vector3 v ) {
        return new Vector3( x + v.x, y + v.y, z + v.z );
    }
    
    /**
     * Adds a value to the current vector.
     *
     * @param value The value to add.
     * @return A new vector with the components added to the given value.
     */
    public Vector3 addValue( double value ) {
        return new Vector3( x + value, y + value, z + value );
    }
    
    /**
     * Subtracts a vector from the current vector.
     *
     * @param v Another vector.
     * @return A new vector resulting from the subtraction.
     */
    public Vector3 subtract( Vector3 v ) {
        return new Vector3( x - v.x, y - v.y, z - v.z );
    }
    
    /**
     * Subtracts a value from the current vector.
     *
     * @param value The value to subtract.
     * @return A new vector with the components subtracted by the given value.
     */
    public Vector3 subtractValue( double value ) {
        return new Vector3( x - value, y - value, z - value );
    }
    
    /**
     * Scales the current vector, analogous to scalar multiplication.
     *
     * @param scale The scale.
     * @return A new scaled vector.
     */
    public Vector3 scale( double scale ) {
        return new Vector3( x * scale, y * scale, z * scale );
    }
    
    /**
     * Multiplies the current vector by another vector.
     *
     * @param v Another vector.
     * @return A new vector with the result of the multiplication.
     */
    public Vector3 multiply( Vector3 v ) {
        return new Vector3( x * v.x, y * v.y, z * v.z );
    }
    
    
    /**
     * Calculates the cross product of the current vector with the given vector.
     *
     * @param v Another vector.
     * @return The cross product.
     */
    public Vector3 crossProduct( Vector3 v ) {
        return new Vector3( y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x );
    }
    
    /**
     * Calculates the vector perpendicular to the current vector.
     *
     * @return The perpendicular vector.
     */
    public Vector3 perpendicular() {
        
        Vector3 result = new Vector3();
        
        double min = Math.abs( x );
        Vector3 cardinalAxis = new Vector3( 1.0, 0.0, 0.0 );
        
        if ( Math.abs( y ) < min ) {
            min = Math.abs( y );
            cardinalAxis.x = 0.0;
            cardinalAxis.y = 1.0;
            cardinalAxis.z = 0.0;
        }
        
        if ( Math.abs( z ) < min ) {
            cardinalAxis.x = 0.0;
            cardinalAxis.y = 0.0;
            cardinalAxis.z = 1.0;
        }
        
        // cross product
        result.x = y * cardinalAxis.z - z * cardinalAxis.y;
        result.y = z * cardinalAxis.x - x * cardinalAxis.z;
        result.z = x * cardinalAxis.y - y * cardinalAxis.x;
        
        return result;
        
    }
    
    /**
     * Calculates the length of the vector.
     *
     * @return The length.
     */
    public double length() {
        return Math.sqrt( x * x + y * y + z * z );
    }
    
    /**
     * Calculates the squared length of the vector.
     *
     * @return The length.
     */
    public double lengthSquare() {
        return x * x + y * y + z * z;
    }
    
    /**
     * Calculates the dot product of the current vector with the given vector.
     *
     * @param v Another vector.
     * @return The dot product.
     */
    public double dotProduct( Vector3 v ) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Calculates the distance between the current vector and another vector.
     *
     * @param v Another vector.
     * @return The distance.
     */
    public double distance( Vector3 v ) {
        return Math.sqrt( ( v.x - x ) * ( v.x - x ) + 
                          ( v.y - y ) * ( v.y - y ) +
                          ( v.z - z ) * ( v.z - z ) );
    }
    
    /**
     * Calculates the squared distance between the current vector and another vector.
     *
     * @param v Another vector.
     * @return The squared distance.
     */
    public double distanceSquare( Vector3 v ) {
        return ( v.x - x ) * ( v.x - x ) + 
               ( v.y - y ) * ( v.y - y ) +
               ( v.z - z ) * ( v.z - z );
    }
    
    
    /**
     * Calculates the angle between the current vector and another vector, where
     * the angle is calculated from the origin point (0, 0).
     *
     * @param v Another vector.
     * @return The angle between the vectors.
     */
    public double angle( Vector3 v ) {
        
        Vector3 cross = new Vector3( y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x );
        double len = Math.sqrt( cross.x * cross.x + cross.y * cross.y + cross.z * cross.z );
        double dot = ( x * v.x + y * v.y + z * v.z );

        return Math.atan2( len, dot );

    }
    
    /**
     * Negates the current vector.
     *
     * @return A new vector with the negation of the current vector.
     */
    public Vector3 negate() {
        return new Vector3( -x, -y, -z );
    }
    
    /**
     * Divides the current vector by another vector.
     *
     * @param v Another vector.
     * @return A new vector with the result of the division.
     */
    public Vector3 divide( Vector3 v ) {
        return new Vector3( x / v.x, y / v.y, z / v.z );
    }
    
    /**
     * Normalizes the current vector.
     *
     * @return A new normalized vector.
     */
    public Vector3 normalize() {

        Vector3 result = new Vector3();
        double length = Math.sqrt( x * x + y * y + z * z );

        if ( length > 0.0 ) {
            double ilength = 1.0 / length;
            result.x = x * ilength;
            result.y = y * ilength;
            result.z = z * ilength;
        }

        return result;

    }
    
    /**
     * Calculates the projection of the current vector onto another vector.
     *
     * @param v Another vector.
     * @return The projection.
     */
    public Vector3 project( Vector3 v ) {
        
        Vector3 result = new Vector3();
        
        double v1dv2 = ( x * v.x + y * v.y + z * v.z );
        double v2dv2 = ( v.x * v.x + v.y * v.y + v.z * v.z );
        double mag = v1dv2 / v2dv2;
        
        result.x = v.x * mag;
        result.y = v.y * mag;
        result.z = v.z * mag;
        
        return result;
    
    }
    
    /**
     * Calculates the rejection of the current vector from another vector.
     *
     * @param v Another vector.
     * @return The rejection.
     */
    public Vector3 reject( Vector3 v ) {
        
        Vector3 result = new Vector3();
        
        double v1dv2 = ( x * v.x + y * v.y + z * v.z );
        double v2dv2 = ( v.x * v.x + v.y * v.y + v.z * v.z );
        double mag = v1dv2 / v2dv2;
        
        result.x = x - ( v.x * mag );
        result.y = y - ( v.y * mag );
        result.z = z - ( v.z * mag );
        
        return result;
        
    }
    
    /**
     * Transforms the current vector by a given matrix.
     *
     * @param mat The matrix.
     * @return A new transformed vector.
     */
    public Vector3 transform( Matrix mat ) {
        
        Vector3 result = new Vector3();

        result.x = mat.m0*x + mat.m4*y + mat.m8*z + mat.m12;
        result.y = mat.m1*x + mat.m5*y + mat.m9*z + mat.m13;
        result.z = mat.m2*x + mat.m6*y + mat.m10*z + mat.m14;

        return result;
        
    }
    
    /**
     * Transforms the current vector by a quaternion rotation.
     *
     * @param q The quaternion.
     * @return A new transformed vector.
     */
    public Vector3 rotateByQuaternion( Quaternion q ) {
        
        Vector3 result = new Vector3();
        
        result.x = x * ( q.x * q.x + q.w * q.w - q.y * q.y - q.z * q.z ) + y * ( 2 * q.x * q.y - 2 * q.w * q.z ) + z * ( 2 * q.x * q.z + 2 * q.w * q.y );
        result.y = x * ( 2 * q.w * q.z + 2 * q.x * q.y ) + y * ( q.w * q.w - q.x * q.x + q.y * q.y - q.z * q.z ) + z * ( -2 * q.w * q.x + 2 * q.y * q.z );
        result.z = x * ( -2 * q.w * q.y + 2 * q.x * q.z ) + y * ( 2 * q.w * q.x + 2 * q.y * q.z )+ z * ( q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z );
        
        return result;
    
    }
    
    /**
     * Rotates the current vector around an axis.
     *
     * Using Euler-Rodrigues Formula
     * Ref.: https://en.wikipedia.org/w/index.php?title=Euler%E2%80%93Rodrigues_formula
     *
     * @param axis The axis.
     * @param angle The angle in radians.
     * @return A new rotated vector.
     */
    public Vector3 rotateByAxisAngle( Vector3 axis, double angle ) {
        
        Vector3 result = new Vector3( x, y, z );
        
        double length = Math.sqrt( axis.x * axis.x + axis.y * axis.y + axis.z * axis.z );
        
        if ( length == 0.0 ) {
            length = 1.0;
        }
        
        double ilength = 1.0 / length;
        
        axis.x *= ilength;
        axis.y *= ilength;
        axis.z *= ilength;
        
        angle /= 2.0;
        
        double a = Math.sin( angle );
        double b = axis.x * a;
        double c = axis.y * a;
        double d = axis.z * a;
        
        a = Math.cos( angle );
        Vector3 w = new Vector3( b, c, d );
        
        Vector3 wv = new Vector3( w.y * z - w.z * y, w.z * x - w.x * z, w.x * y - w.y * x );
        
        Vector3 wwv = new Vector3( w.y * wv.z - w.z * wv.y, w.z * wv.x - w.x * wv.z, w.x * wv.y - w.y * wv.x );
        
        a *= 2;
        wv.x *= a;
        wv.y *= a;
        wv.z *= a;
        
        wwv.x *= 2;
        wwv.y *= 2;
        wwv.z *= 2;
        
        result.x += wv.x;
        result.y += wv.y;
        result.z += wv.z;
        result.x += wwv.x;
        result.y += wwv.y;
        result.z += wwv.z;
        
        return result;
        
    }
    
    /**
     * Creates a new vector moved towards a target.
     *
     * @param target The target.
     * @param maxDistance The maximum distance.
     * @return A new vector moved towards the target.
     */
    public Vector3 moveTowards( Vector3 target, double maxDistance ) {
        
        Vector3 result = new Vector3();
        
        double dx = target.x - x;
        double dy = target.y - y;
        double dz = target.z - z;
        double value = ( dx * dx ) + ( dy * dy ) + ( dz * dz );
        
        if ( ( value == 0.0 ) || ( ( maxDistance >= 0.0 ) && ( value <= maxDistance * maxDistance ) ) ) {
            return new Vector3( target.x, target.y, target.z );
        }
        
        double dist = Math.sqrt( value );
        result.x = x + dx / dist * maxDistance;
        result.y = y + dy / dist * maxDistance;
        result.z = z + dz / dist * maxDistance;
        
        return result;
        
    }
    
    /**
     * Performs linear interpolation between the current vector (start) and another vector (end).
     *
     * @param end End vector.
     * @param amount Amount (0 to 1)
     * @return A vector representing the linear interpolation between two vectors.
     */
    public Vector3 lerp( Vector3 end, double amount ) {
        return new Vector3( 
            this.x + ( end.x - this.x ) * amount,
            this.y + ( end.y - this.y ) * amount,
            this.z + ( end.z - this.z ) * amount
        );
    }
    
    /**
     * Calculates the cubic Hermite interpolation of the current vector and another vector and their tangents.
     *
     * @param tangent1 First tangent.
     * @param v The vector.
     * @param tangent2 Second tangent.
     * @param amount The amount.
     * @return A new vector with the cubic Hermite interpolation.
     */
    public Vector3 cubicHermite( Vector3 tangent1, Vector3 v, Vector3 tangent2, double amount ) {
        
        Vector3 result = new Vector3();
        double amountPow2 = amount * amount;
        double amountPow3 = amount * amount * amount;
        
        result.x = (2 * amountPow3 - 3 * amountPow2 + 1) * x + (amountPow3 - 2 * amountPow2 + amount) * tangent1.x + (-2 * amountPow3 + 3 * amountPow2) * v.x + (amountPow3 - amountPow2) * tangent2.x;
        result.y = (2 * amountPow3 - 3 * amountPow2 + 1) * y + (amountPow3 - 2 * amountPow2 + amount) * tangent1.y + (-2 * amountPow3 + 3 * amountPow2) * v.y + (amountPow3 - amountPow2) * tangent2.y;
        result.z = (2 * amountPow3 - 3 * amountPow2 + 1) * z + (amountPow3 - 2 * amountPow2 + amount) * tangent1.z + (-2 * amountPow3 + 3 * amountPow2) * v.z + (amountPow3 - amountPow2) * tangent2.z;
        
        return result;
    
    }
    
    /**
     * Calculates the reflection of the current vector by a normal vector.
     *
     * @param normal Normal vector.
     * @return A new reflected vector.
     */
    public Vector3 reflect( Vector3 normal ) {
        
        Vector3 result = new Vector3();
        
        double dotProduct = (x * normal.x + y * normal.y + z * normal.z);
        result.x = x - (2.0f * normal.x) * dotProduct;
        result.y = y - (2.0f * normal.y) * dotProduct;
        result.z = z - (2.0f * normal.z) * dotProduct;
        
        return result;
    
    }
    
    /**
     * Gets a new vector with the minimum of each component.
     *
     * @param v Another vector.
     * @return A new vector with the minimum of each component.
     */
    public Vector3 min( Vector3 v ) {

        Vector3 result = new Vector3();

        result.x = Math.min( x, v.x );
        result.y = Math.min( y, v.y );
        result.z = Math.min( z, v.z );

        return result;

    }

    /**
     * Gets a new vector with the maximum of each component.
     *
     * @param v Another vector.
     * @return A new vector with the maximum of each component.
     */
    public Vector3 max( Vector3 v ) {

        Vector3 result = new Vector3();

        result.x = Math.max( x, v.x );
        result.y = Math.max( y, v.y );
        result.z = Math.max( z, v.z );

        return result;

    }
    
    /**
     * Unprojects the current vector (screen space) to object space.
     *
     * @param projection Projection matrix.
     * @param view View matrix.
     * @return A new unprojected vector.
     */
    public Vector3 unproject( Matrix projection, Matrix view ) {
        
        Vector3 result = new Vector3();
        
        Matrix matViewProj = new Matrix(
            view.m0 * projection.m0 + view.m1 * projection.m4 + view.m2 * projection.m8 + view.m3 * projection.m12,
            view.m0 * projection.m1 + view.m1 * projection.m5 + view.m2 * projection.m9 + view.m3 * projection.m13,
            view.m0 * projection.m2 + view.m1 * projection.m6 + view.m2 * projection.m10 + view.m3 * projection.m14,
            view.m0 * projection.m3 + view.m1 * projection.m7 + view.m2 * projection.m11 + view.m3 * projection.m15,
            view.m4 * projection.m0 + view.m5 * projection.m4 + view.m6 * projection.m8 + view.m7 * projection.m12,
            view.m4 * projection.m1 + view.m5 * projection.m5 + view.m6 * projection.m9 + view.m7 * projection.m13,
            view.m4 * projection.m2 + view.m5 * projection.m6 + view.m6 * projection.m10 + view.m7 * projection.m14,
            view.m4 * projection.m3 + view.m5 * projection.m7 + view.m6 * projection.m11 + view.m7 * projection.m15,
            view.m8 * projection.m0 + view.m9 * projection.m4 + view.m10 * projection.m8 + view.m11 * projection.m12,
            view.m8 * projection.m1 + view.m9 * projection.m5 + view.m10 * projection.m9 + view.m11 * projection.m13,
            view.m8 * projection.m2 + view.m9 * projection.m6 + view.m10 * projection.m10 + view.m11 * projection.m14,
            view.m8 * projection.m3 + view.m9 * projection.m7 + view.m10 * projection.m11 + view.m11 * projection.m15,
            view.m12 * projection.m0 + view.m13 * projection.m4 + view.m14 * projection.m8 + view.m15 * projection.m12,
            view.m12 * projection.m1 + view.m13 * projection.m5 + view.m14 * projection.m9 + view.m15 * projection.m13,
            view.m12 * projection.m2 + view.m13 * projection.m6 + view.m14 * projection.m10 + view.m15 * projection.m14,
            view.m12 * projection.m3 + view.m13 * projection.m7 + view.m14 * projection.m11 + view.m15 * projection.m15 );
        
        double a00 = matViewProj.m0, a01 = matViewProj.m1, a02 = matViewProj.m2, a03 = matViewProj.m3;
        double a10 = matViewProj.m4, a11 = matViewProj.m5, a12 = matViewProj.m6, a13 = matViewProj.m7;
        double a20 = matViewProj.m8, a21 = matViewProj.m9, a22 = matViewProj.m10, a23 = matViewProj.m11;
        double a30 = matViewProj.m12, a31 = matViewProj.m13, a32 = matViewProj.m14, a33 = matViewProj.m15;
        double b00 = a00 * a11 - a01 * a10;
        double b01 = a00 * a12 - a02 * a10;
        double b02 = a00 * a13 - a03 * a10;
        double b03 = a01 * a12 - a02 * a11;
        double b04 = a01 * a13 - a03 * a11;
        double b05 = a02 * a13 - a03 * a12;
        double b06 = a20 * a31 - a21 * a30;
        double b07 = a20 * a32 - a22 * a30;
        double b08 = a20 * a33 - a23 * a30;
        double b09 = a21 * a32 - a22 * a31;
        double b10 = a21 * a33 - a23 * a31;
        double b11 = a22 * a33 - a23 * a32;
        
        double invDet = 1.0 / (b00 * b11 - b01 * b10 + b02 * b09 + b03 * b08 - b04 * b07 + b05 * b06);
        Matrix matViewProjInv = new Matrix(
            (a11 * b11 - a12 * b10 + a13 * b09) * invDet,
            (-a01 * b11 + a02 * b10 - a03 * b09) * invDet,
            (a31 * b05 - a32 * b04 + a33 * b03) * invDet,
            (-a21 * b05 + a22 * b04 - a23 * b03) * invDet,
            (-a10 * b11 + a12 * b08 - a13 * b07) * invDet,
            (a00 * b11 - a02 * b08 + a03 * b07) * invDet,
            (-a30 * b05 + a32 * b02 - a33 * b01) * invDet,
            (a20 * b05 - a22 * b02 + a23 * b01) * invDet,
            (a10 * b10 - a11 * b08 + a13 * b06) * invDet,
            (-a00 * b10 + a01 * b08 - a03 * b06) * invDet,
            (a30 * b04 - a31 * b02 + a33 * b00) * invDet,
            (-a20 * b04 + a21 * b02 - a23 * b00) * invDet,
            (-a10 * b09 + a11 * b07 - a12 * b06) * invDet,
            (a00 * b09 - a01 * b07 + a02 * b06) * invDet,
            (-a30 * b03 + a31 * b01 - a32 * b00) * invDet,
            (a20 * b03 - a21 * b01 + a22 * b00) * invDet );
        
        Quaternion quat = new Quaternion( x, y, z, 1.0f );
        Quaternion qtransformed = new Quaternion(
            matViewProjInv.m0 * quat.x + matViewProjInv.m4 * quat.y + matViewProjInv.m8 * quat.z + matViewProjInv.m12 * quat.w,
            matViewProjInv.m1 * quat.x + matViewProjInv.m5 * quat.y + matViewProjInv.m9 * quat.z + matViewProjInv.m13 * quat.w,
            matViewProjInv.m2 * quat.x + matViewProjInv.m6 * quat.y + matViewProjInv.m10 * quat.z + matViewProjInv.m14 * quat.w,
            matViewProjInv.m3 * quat.x + matViewProjInv.m7 * quat.y + matViewProjInv.m11 * quat.z + matViewProjInv.m15 * quat.w );
        
        result.x = qtransformed.x / qtransformed.w;
        result.y = qtransformed.y / qtransformed.w;
        result.z = qtransformed.z / qtransformed.w;
        
        return result;
    
    }
    
    /**
     * Inverts the current vector.
     *
     * @return A new inverted vector.
     */
    public Vector3 invert() {
        return new Vector3( 1.0 / x, 1.0 / y, 1.0 / z );
    }
    
    /**
     * Clamps the current vector between two vectors.
     *
     * @param min The minimum vector.
     * @param max The maximum vector.
     * @return A new vector clamped between the minimum and maximum vectors.
     */
    public Vector3 clamp( Vector3 min, Vector3 max ) {

        Vector3 result = new Vector3();

        result.x = Math.min( max.x, Math.max( min.x, x ) );
        result.y = Math.min( max.y, Math.max( min.y, y ) );
        result.z = Math.min( max.z, Math.max( min.z, z ) );

        return result;

    }
    
    /**
     * Clamps the magnitude of the current vector between minimum and maximum.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A new vector with the clamped magnitude.
     */
    public Vector3 clampValue( double min, double max ) {

        Vector3 result = new Vector3( x, y, z );

        double length = x * x + y * y + z * z;

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
            result.z = z * scale;

        }

        return result;

    }
    
    /**
     * Creates a vector with all components equal to 1.0.
     *
     * @return A vector with all components equal to 1.0.
     */
    public static Vector3 one() {
        return new Vector3( 1.0, 1.0, 1.0 );
    }
    
    /**
     * Orthonormalizes two vectors (they will be modified).
     * Makes the vectors normalized and orthogonal to each other.
     * Implementation of the Gram-Schmidt process.
     *
     * @param v1 A vector.
     * @param v2 Another vector.
     */
    public static void orthoNormalize( Vector3 v1, Vector3 v2 ) {
        
        double length = 0.0;
        double ilength = 0.0;
        
        Vector3 v = new Vector3( v1.x, v1.y, v1.z );
        
        length = Math.sqrt( v.x * v.x + v.y * v.y + v.z * v.z );
        if ( length == 0.0 ) {
            length = 1.0;
        }
        
        ilength = 1.0 / length;
        
        v1.x *= ilength;
        v1.y *= ilength;
        v1.z *= ilength;
        
        Vector3 vn1 = new Vector3( v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x );
        
        v.x = vn1.x;
        v.y = vn1.y;
        v.z = vn1.z;
        
        length = Math.sqrt( v.x * v.x + v.y * v.y + v.z * v.z );
        if ( length == 0.0 ) {
            length = 1.0;
        }
        
        ilength = 1.0 / length;
        
        vn1.x *= ilength;
        vn1.y *= ilength;
        vn1.z *= ilength;
        
        // Vector3CrossProduct(vn1, *v1)
        Vector3 vn2 = new Vector3( vn1.y * v1.z - vn1.z * v1.y, vn1.z * v1.x - vn1.x * v1.z, vn1.x * v1.y - vn1.y * v1.x );
        v2.x = vn2.x;
        v2.y = vn2.y;
        v2.z = vn2.z;
     
    }
    
    /**
     * Calculates the barycenter of a point p in a triangle (a, b, c).
     *
     * @param p The point.
     * @param a First vertex.
     * @param b Second vertex.
     * @param c Third vertex.
     * @return The barycenter.
     */
    public static Vector3 barycenter( Vector3 p, Vector3 a, Vector3 b, Vector3 c ) {
        
        Vector3 result = new Vector3();
        Vector3 v0 = new Vector3( b.x - a.x, b.y - a.y, b.z - a.z );
        Vector3 v1 = new Vector3( c.x - a.x, c.y - a.y, c.z - a.z );
        Vector3 v2 = new Vector3( p.x - a.x, p.y - a.y, p.z - a.z );
        
        double d00 = (v0.x * v0.x + v0.y * v0.y + v0.z * v0.z);
        double d01 = (v0.x * v1.x + v0.y * v1.y + v0.z * v1.z);
        double d11 = (v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
        double d20 = (v2.x * v0.x + v2.y * v0.y + v2.z * v0.z);
        double d21 = (v2.x * v1.x + v2.y * v1.y + v2.z * v1.z);
        double denom = d00 * d11 - d01 * d01;
        
        result.y = (d11 * d20 - d01 * d21) / denom;
        result.z = (d00 * d21 - d01 * d20) / denom;
        result.x = 1.0 - (result.z + result.y);
        
        return result;
    
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
    public static Vector3 refract( Vector3 v, Vector3 n, double r ) {

        Vector3 result = new Vector3();

        double dot = v.x * n.x + v.y * n.y + v.z * n.z;
        double d = 1.0 - r * r * (1.0 - dot * dot);

        if ( d >= 0.0 ) {
            d = Math.sqrt( d );
            result.x = r * v.x - (r * dot + d) * n.x;
            result.y = r * v.y - (r * dot + d) * n.y;
            result.z = r * v.z - (r * dot + d) * n.z;
        }

        return result;
            
    }    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Vector3 clone = (Vector3) super.clone();
        clone.x = x;
        clone.y = y;
        clone.z = z;
        return clone;
    }
    
    @Override
    public int hashCode(  ) {
        int hash = 3;
        hash = 23 * hash + (int) ( Double.doubleToLongBits( this.x ) ^ ( Double.doubleToLongBits( this.x ) >>> 32 ) );
        hash = 23 * hash + (int) ( Double.doubleToLongBits( this.y ) ^ ( Double.doubleToLongBits( this.y ) >>> 32 ) );
        hash = 23 * hash + (int) ( Double.doubleToLongBits( this.z ) ^ ( Double.doubleToLongBits( this.z ) >>> 32 ) );
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
        final Vector3 other = (Vector3) obj;
        if ( Double.doubleToLongBits( this.x ) != Double.doubleToLongBits( other.x ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.y ) != Double.doubleToLongBits( other.y ) ) {
            return false;
        }
        return Double.doubleToLongBits( this.z ) == Double.doubleToLongBits( other.z );
    }

    @Override
    public String toString() {
        return String.format( "Vector3[%.2f, %.2f, %.2f]", x, y, z );
    }
    
}
