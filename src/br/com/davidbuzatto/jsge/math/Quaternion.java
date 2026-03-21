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
 * Class for representing a quaternion.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Quaternion implements Cloneable, Serializable {
    
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
     * Creates a new quaternion with default values.
     */
    public Quaternion() {
    }

    /**
     * Creates a new quaternion.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param z z coordinate.
     * @param w w coordinate.
     */
    public Quaternion( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Adds the current quaternion to another quaternion.
     *
     * @param q Another quaternion.
     * @return A new quaternion resulting from the addition.
     */
    public Quaternion add( Quaternion q ) {
        return new Quaternion( x + q.x, y + q.y, z + q.z, w + q.w );
    }
    
    /**
     * Adds a value to the current quaternion.
     *
     * @param value The value to add.
     * @return A new quaternion with the components added to the given value.
     */
    public Quaternion addValue( double value ) {
        return new Quaternion( x + value, y + value, z + value, w + value );
    }
    
    /**
     * Subtracts a quaternion from the current quaternion.
     *
     * @param q Another quaternion.
     * @return A new quaternion resulting from the subtraction.
     */
    public Quaternion subtract( Quaternion q ) {
        return new Quaternion( x - q.x, y - q.y, z - q.z, w - q.w );
    }
    
    /**
     * Subtracts a value from the current quaternion.
     *
     * @param value The value to subtract.
     * @return A new quaternion with the components subtracted by the given value.
     */
    public Quaternion subtractValue( double value ) {
        return new Quaternion( x - value, y - value, z - value, w - value );
    }
    
    /**
     * Calculates the length of the quaternion.
     *
     * @return The length.
     */
    public double length() {
        return Math.sqrt( x * x + y * y + z * z + w * w );
    }
    
    /**
     * Normalizes the quaternion vector.
     *
     * @return A new normalized quaternion.
     */
    public Quaternion normalize() {

        Quaternion result = new Quaternion();
        double length = Math.sqrt( x * x + y * y + z * z + w * w );

        if ( length == 0.0 ) {
            length = 1.0;
        }
        
        double ilength = 1.0 / length;
        result.x = x * ilength;
        result.y = y * ilength;
        result.z = z * ilength;
        result.w = w * ilength;

        return result;

    }
    
    /**
     * Inverts the current quaternion.
     *
     * @return A new inverted quaternion.
     */
    public Quaternion invert() {
        
        Quaternion result = new Quaternion( x, y, z, w );

        double lengthSq = x * x + y * y + z * z + w * w;

        if ( lengthSq != 0.0 ) {
            double invLength = 1.0/lengthSq;
            result.x *= -invLength;
            result.y *= -invLength;
            result.z *= -invLength;
            result.w *= invLength;
        }

        return result;
    
    }
    
    /**
     * Multiplies the current quaternion by another quaternion.
     *
     * @param q Another quaternion.
     * @return A new quaternion with the result of the multiplication.
     */
    public Quaternion multiply( Quaternion q ) {
        
        Quaternion result = new Quaternion();

        double qax = x, qay = y, qaz = z, qaw = w;
        double qbx = q.x, qby = q.y, qbz = q.z, qbw = q.w;

        result.x = qax * qbw + qaw * qbx + qay * qbz - qaz * qby;
        result.y = qay * qbw + qaw * qby + qaz * qbx - qax * qbz;
        result.z = qaz * qbw + qaw * qbz + qax * qby - qay * qbx;
        result.w = qaw * qbw - qax * qbx - qay * qby - qaz * qbz;

        return result;
    
    }
    
    /**
     * Scales the current quaternion, analogous to scalar multiplication.
     *
     * @param scale The scale.
     * @return A new scaled quaternion.
     */
    public Quaternion scale( double scale ) {
        return new Quaternion( x * scale, y * scale, z * scale, w * scale );
    }
    
    /**
     * Divides the current quaternion by another quaternion.
     *
     * @param q Another quaternion.
     * @return A new quaternion with the result of the division.
     */
    public Quaternion divide( Quaternion q ) {
        return new Quaternion( x / q.x, y / q.y, z / q.z, w / q.w );
    }
    
    /**
     * Performs linear interpolation between the current quaternion (start) and another quaternion (end).
     *
     * @param end End quaternion.
     * @param amount Amount (0 to 1)
     * @return A quaternion representing the linear interpolation between two quaternions.
     */
    public Quaternion lerp( Quaternion end, double amount ) {
        return new Quaternion( 
            this.x + ( end.x - this.x ) * amount,
            this.y + ( end.y - this.y ) * amount,
            this.z + ( end.z - this.z ) * amount,
            this.w + ( end.w - this.w ) * amount
        );
    }
    
    /**
     * Performs normalized linear interpolation between the current quaternion (start) and another quaternion (end).
     *
     * @param end End quaternion.
     * @param amount Amount (0 to 1)
     * @return A quaternion representing the normalized linear interpolation between two quaternions.
     */
    public Quaternion normalizedLerp( Quaternion end, double amount ) {
        
        Quaternion result = new Quaternion( 
            this.x + ( end.x - this.x ) * amount,
            this.y + ( end.y - this.y ) * amount,
            this.z + ( end.z - this.z ) * amount,
            this.w + ( end.w - this.w ) * amount
        );
        
        Quaternion q = new Quaternion( result.x, result.y, result.z, result.w );
        double length = Math.sqrt( q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w );
        if ( length == 0.0 ) {
            length = 1.0;
        }
        double ilength = 1.0 / length;

        result.x = q.x * ilength;
        result.y = q.y * ilength;
        result.z = q.z * ilength;
        result.w = q.w * ilength;
        
        return result;
        
    }
    
    /**
     * Performs spherical linear interpolation between the current quaternion (start) and another quaternion (end).
     *
     * @param end End quaternion.
     * @param amount Amount (0 to 1)
     * @return A quaternion representing the spherical linear interpolation between two quaternions.
     */
    public Quaternion sphericalLerp( Quaternion end, double amount ) {
        
        Quaternion result = new Quaternion();

        double cosHalfTheta = x * end.x + y * end.y + z * end.z + w * end.w;

        if ( cosHalfTheta < 0.0 ) {
            end.x = -end.x; 
            end.y = -end.y; 
            end.z = -end.z; 
            end.w = -end.w;
            cosHalfTheta = -cosHalfTheta;
        }

        if ( Math.abs( cosHalfTheta ) >= 1.0 ) {
            result.x = x;
            result.y = y;
            result.z = z;
            result.w = w;
        } else if ( cosHalfTheta > 0.95 ) {
            result = normalizedLerp( end, amount );
        } else {
            
            double halfTheta = Math.cos( cosHalfTheta );
            double sinHalfTheta = Math.sqrt( 1.0 - cosHalfTheta * cosHalfTheta );

            if ( Math.abs( sinHalfTheta ) < 0.000001 ) {
                result.x = (x * 0.5f + end.x * 0.5f);
                result.y = (y * 0.5f + end.y * 0.5f);
                result.z = (z * 0.5f + end.z * 0.5f);
                result.w = (w * 0.5f + end.w * 0.5f);
            } else {
                
                double ratioA = Math.sin( ( 1 - amount ) * halfTheta ) / sinHalfTheta;
                double ratioB = Math.sin( amount * halfTheta ) / sinHalfTheta;

                result.x = (x * ratioA + end.x * ratioB);
                result.y = (y * ratioA + end.y * ratioB);
                result.z = (z * ratioA + end.z * ratioB);
                result.w = (w * ratioA + end.w * ratioB);
                
            }
            
        }

        return result;
    
    }
    
    /**
     * Calculates the cubic Hermite interpolation of the current quaternion and another quaternion and their tangents.
     *
     * @param outTangent First tangent.
     * @param q The quaternion.
     * @param inTangent Second tangent.
     * @param amount The amount.
     * @return A new quaternion with the cubic Hermite interpolation.
     */
    public Quaternion cubicHermite( Quaternion outTangent, Quaternion q, Quaternion inTangent, double amount ) {
        
        double t2 = amount*amount;
        double t3 = t2*amount;
        double h00 = 2*t3 - 3*t2 + 1;
        double h10 = t3 - 2*t2 + amount;
        double h01 = -2*t3 + 3*t2;
        double h11 = t3 - t2;

        Quaternion p0 = scale( h00);
        Quaternion m0 = outTangent.scale( h10 );
        Quaternion p1 = q.scale( h01 );
        Quaternion m1 = inTangent.scale( h11 );

        Quaternion result = p0.add( m0 );
        result = result.add( p1 );
        result = result.add( m1 );
        result = result.normalize();

        return result;
    
    }
    
    /**
     * Gets the matrix of the current quaternion.
     *
     * @return A new matrix of the current quaternion.
     */
    public Matrix toMatrix() {
        
        Matrix result = Matrix.identity();

        double a2 = x * x;
        double b2 = y * y;
        double c2 = z * z;
        double ac = x * z;
        double ab = x * y;
        double bc = y * z;
        double ad = w * x;
        double bd = w * y;
        double cd = w * z;

        result.m0 = 1 - 2 * (b2 + c2);
        result.m1 = 2 * (ab + cd);
        result.m2 = 2 * (ac - bd);

        result.m4 = 2 * (ab - cd);
        result.m5 = 1 - 2 * (a2 + c2);
        result.m6 = 2 * (bc + ad);

        result.m8 = 2 * (ac + bd);
        result.m9 = 2 * (bc - ad);
        result.m10 = 1 - 2 * (a2 + b2);

        return result;
    
    }
    
    /**
     * Gets the rotation angle and axis of the current quaternion.
     * @param outAxis Vector that will receive the axis data.
     * @return The angle.
     */
    public double toAxisAngle( Vector3 outAxis ) {
        
        Quaternion q = new Quaternion( x, y, z, w );
        
        if ( Math.abs( q.w ) > 1.0 ) {
            
            double length = Math.sqrt( q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w );
            if (length == 0.0) length = 1.0;
            double ilength = 1.0 / length;

            q.x = q.x * ilength;
            q.y = q.y * ilength;
            q.z = q.z * ilength;
            q.w = q.w * ilength;
            
        }

        outAxis.x = 0;
        outAxis.y = 0;
        outAxis.z = 0;
        
        double resAngle = 2.0 * Math.acos( q.w );
        double den = Math.sqrt( 1.0 - q.w*q.w );

        if ( den > 0.000001 ) {
            outAxis.x = q.x/den;
            outAxis.y = q.y/den;
            outAxis.z = q.z/den;
        } else {
            outAxis.x = 1.0;
        }

        return resAngle;
    
    }
    
    /**
     * Gets the Euler angles in radians (roll, pitch, and yaw)
     * equivalent to the current quaternion.
     * @return A vector containing the Euler angles. x = roll (rotation in x), y = pitch (rotation in y) and z = yaw (rotation in z).
     */
    public Vector3 toEuler() {
        
        Vector3 result = new Vector3();

        double x0 = 2.0 * (w * x + y * z);
        double x1 = 1.0 - 2.0 * (x * x + y * y);
        result.x = Math.atan2( x0, x1 );

        double y0 = 2.0 * (w * y - z * x);
        y0 = y0 > 1.0 ? 1.0 : y0;
        y0 = y0 < -1.0 ? -1.0 : y0;
        result.y = Math.asin( y0 );

        double z0 = 2.0 * (w * z + x * y);
        double z1 = 1.0 - 2.0 * (y * y + z * z);
        result.z = Math.atan2( z0, z1 );

        return result;
    
    }
    
    /**
     * Transforms the current quaternion by a given matrix.
     *
     * @param mat The matrix.
     * @return A new transformed quaternion.
     */
    public Quaternion transform( Matrix mat ) {
        
        Quaternion result = new Quaternion();

        result.x = mat.m0 * x + mat.m4 * y + mat.m8 * z + mat.m12 * w;
        result.y = mat.m1 * x + mat.m5 * y + mat.m9 * z + mat.m13 * w;
        result.z = mat.m2 * x + mat.m6 * y + mat.m10 * z + mat.m14 * w;
        result.w = mat.m3 * x + mat.m7 * y + mat.m11 * z + mat.m15 * w;

        return result;
        
    }
    
    /**
     * Creates an identity quaternion.
     *
     * @return A new identity quaternion.
     */
    public static Quaternion identity() {
        return new Quaternion( 0.0, 0.0, 0.0, 1.0 );
    }
    
    /**
     * Creates a quaternion based on the rotation from one vector to another.
     *
     * @param from A vector.
     * @param to Another vector.
     * @return A new quaternion.
     */
    public static Quaternion fromVectorToVetor( Vector3 from, Vector3 to ) {
        
        Quaternion result = new Quaternion();

        double cos2Theta = (from.x * to.x + from.y * to.y + from.z * to.z);
        Vector3 cross = new Vector3( from.y * to.z - from.z * to.y, from.z * to.x - from.x * to.z, from.x * to.y - from.y * to.x );

        result.x = cross.x;
        result.y = cross.y;
        result.z = cross.z;
        result.w = 1.0 + cos2Theta;

        Quaternion q = new Quaternion( result.x, result.y, result.z, result.w );
        double length = Math.sqrt( q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w );
        if ( length == 0.0 ) {
            length = 1.0;
        }
        double ilength = 1.0 / length;

        result.x = q.x * ilength;
        result.y = q.y * ilength;
        result.z = q.z * ilength;
        result.w = q.w * ilength;
        
        return result;
    
    }
    
    /**
     * Gets a quaternion from a rotation matrix.
     *
     * @param mat The matrix.
     * @return A new quaternion.
     */
    public static Quaternion fromMatrix( Matrix mat ) {
        
        Quaternion result = new Quaternion();

        double fourWSquaredMinus1 = mat.m0  + mat.m5 + mat.m10;
        double fourXSquaredMinus1 = mat.m0  - mat.m5 - mat.m10;
        double fourYSquaredMinus1 = mat.m5  - mat.m0 - mat.m10;
        double fourZSquaredMinus1 = mat.m10 - mat.m0 - mat.m5;

        int biggestIndex = 0;
        double fourBiggestSquaredMinus1 = fourWSquaredMinus1;
        if ( fourXSquaredMinus1 > fourBiggestSquaredMinus1 ) {
            fourBiggestSquaredMinus1 = fourXSquaredMinus1;
            biggestIndex = 1;
        }

        if ( fourYSquaredMinus1 > fourBiggestSquaredMinus1 ) {
            fourBiggestSquaredMinus1 = fourYSquaredMinus1;
            biggestIndex = 2;
        }

        if ( fourZSquaredMinus1 > fourBiggestSquaredMinus1 ) {
            fourBiggestSquaredMinus1 = fourZSquaredMinus1;
            biggestIndex = 3;
        }

        double biggestVal = Math.sqrt( fourBiggestSquaredMinus1 + 1.0 ) * 0.5;
        double mult = 0.25 / biggestVal;

        switch ( biggestIndex ) {
            case 0:
                result.w = biggestVal;
                result.x = (mat.m6 - mat.m9) * mult;
                result.y = (mat.m8 - mat.m2) * mult;
                result.z = (mat.m1 - mat.m4) * mult;
                break;
            case 1:
                result.x = biggestVal;
                result.w = (mat.m6 - mat.m9) * mult;
                result.y = (mat.m1 + mat.m4) * mult;
                result.z = (mat.m8 + mat.m2) * mult;
                break;
            case 2:
                result.y = biggestVal;
                result.w = (mat.m8 - mat.m2) * mult;
                result.x = (mat.m1 + mat.m4) * mult;
                result.z = (mat.m6 + mat.m9) * mult;
                break;
            case 3:
                result.z = biggestVal;
                result.w = (mat.m1 - mat.m4) * mult;
                result.x = (mat.m8 + mat.m2) * mult;
                result.y = (mat.m6 + mat.m9) * mult;
                break;
        }

        return result;
    
    }
    
    /**
     * Gets a rotation quaternion given an angle and an axis.
     *
     * @param axis The axis.
     * @param angle The angle in radians.
     * @return A new rotation quaternion.
     */
    public static Quaternion fromAxisAngle( Vector3 axis, double angle ) {
        
        Quaternion result = identity();

        double axisLength = Math.sqrt( axis.x * axis.x + axis.y * axis.y + axis.z * axis.z );

        if ( axisLength != 0.0 ) {
            
            angle *= 0.5;

            double length = 0.0;
            double ilength = 0.0;

            length = axisLength;
            if ( length == 0.0 ) {
                length = 1.0;
            }
            ilength = 1.0 / length;
            axis.x *= ilength;
            axis.y *= ilength;
            axis.z *= ilength;

            double sinres = Math.sin( angle );
            double cosres = Math.cos( angle );

            result.x = axis.x * sinres;
            result.y = axis.y * sinres;
            result.z = axis.z * sinres;
            result.w = cosres;

            Quaternion q = new Quaternion( result.x, result.y, result.z, result.w );
            length = Math.sqrt( q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w );
            if ( length == 0.0 ) {
                length = 1.0;
            }
            ilength = 1.0/length;
            
            result.x = q.x * ilength;
            result.y = q.y * ilength;
            result.z = q.z * ilength;
            result.w = q.w * ilength;
            
        }

        return result;
    
    }
    
    /**
     * Gets the quaternion equivalent to the Euler angles.
     * The rotation order is ZYX.
     *
     * @param pitch Pitch angle.
     * @param yaw Yaw angle.
     * @param roll Roll angle.
     * @return A new quaternion equivalent to the Euler angles.
     */
    public static Quaternion fromEuler( double pitch, double yaw, double roll ) {
        
        Quaternion result = new Quaternion();

        double x0 = Math.cos( pitch * 0.5 );
        double x1 = Math.sin( pitch * 0.5 );
        double y0 = Math.cos( yaw * 0.5 );
        double y1 = Math.sin( yaw * 0.5 );
        double z0 = Math.cos( roll * 0.5 );
        double z1 = Math.sin( roll * 0.5 );

        result.x = x1 * y0 * z0 - x0 * y1 * z1;
        result.y = x0 * y1 * z0 + x1 * y0 * z1;
        result.z = x0 * y0 * z1 - x1 * y1 * z0;
        result.w = x0 * y0 * z0 + x1 * y1 * z1;

        return result;
        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Quaternion clone = (Quaternion) super.clone();
        clone.x = x;
        clone.y = y;
        clone.z = z;
        clone.w = w;
        return clone;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) ( Double.doubleToLongBits( this.x ) ^ ( Double.doubleToLongBits( this.x ) >>> 32 ) );
        hash = 89 * hash + (int) ( Double.doubleToLongBits( this.y ) ^ ( Double.doubleToLongBits( this.y ) >>> 32 ) );
        hash = 89 * hash + (int) ( Double.doubleToLongBits( this.z ) ^ ( Double.doubleToLongBits( this.z ) >>> 32 ) );
        hash = 89 * hash + (int) ( Double.doubleToLongBits( this.w ) ^ ( Double.doubleToLongBits( this.w ) >>> 32 ) );
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
        final Quaternion other = (Quaternion) obj;
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
        return String.format( "Quaternion[%.2f, %.2f, %.2f, %.2f]", x, y, z, w );
    }
    
}
