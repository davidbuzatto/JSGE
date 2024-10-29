/*
 * Copyright (C) 2024 Prof. Dr. David Buzatto
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

/**
 * Uma matriz.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Matrix {
    
    public double m0, m4, m8, m12;  // primeira linha
    public double m1, m5, m9, m13;  // segunda linha
    public double m2, m6, m10, m14; // terceira linha
    public double m3, m7, m11, m15; // quarta linha

    /**
     * Cria uma nova matriz com valores padrão.
     */
    public Matrix() {
    }

    /**
     * Cria uma nova matriz.
     * 
     * @param m0 valor da linha 1, coluna 1
     * @param m4 valor da linha 1, coluna 2
     * @param m8 valor da linha 1, coluna 3
     * @param m12 valor da linha 1, coluna 4
     * @param m1 valor da linha 2, coluna 1
     * @param m5 valor da linha 2, coluna 2
     * @param m9 valor da linha 2, coluna 3
     * @param m13 valor da linha 2, coluna 4
     * @param m2 valor da linha 3, coluna 1
     * @param m6 valor da linha 3, coluna 2
     * @param m10 valor da linha 3, coluna 3
     * @param m14 valor da linha 3, coluna 4
     * @param m3 valor da linha 4, coluna 1
     * @param m7 valor da linha 4, coluna 2
     * @param m11 valor da linha 4, coluna 3
     * @param m15 valor da linha 4, coluna 4
     */
    public Matrix( double m0, double m4, double m8, double m12, 
                   double m1, double m5, double m9, double m13, 
                   double m2, double m6, double m10, double m14, 
                   double m3, double m7, double m11, double m15 ) {
        this.m0 = m0;
        this.m4 = m4;
        this.m8 = m8;
        this.m12 = m12;
        this.m1 = m1;
        this.m5 = m5;
        this.m9 = m9;
        this.m13 = m13;
        this.m2 = m2;
        this.m6 = m6;
        this.m10 = m10;
        this.m14 = m14;
        this.m3 = m3;
        this.m7 = m7;
        this.m11 = m11;
        this.m15 = m15;
    }
    
    public double determinant() {
        return 0;
    }
/*
// Compute matrix determinant
RMAPI float MatrixDeterminant(Matrix mat)
{
    float result = 0.0f;

    // Cache the matrix values (speed optimization)
    float a00 = mat.m0, a01 = mat.m1, a02 = mat.m2, a03 = mat.m3;
    float a10 = mat.m4, a11 = mat.m5, a12 = mat.m6, a13 = mat.m7;
    float a20 = mat.m8, a21 = mat.m9, a22 = mat.m10, a23 = mat.m11;
    float a30 = mat.m12, a31 = mat.m13, a32 = mat.m14, a33 = mat.m15;

    result = a30*a21*a12*a03 - a20*a31*a12*a03 - a30*a11*a22*a03 + a10*a31*a22*a03 +
             a20*a11*a32*a03 - a10*a21*a32*a03 - a30*a21*a02*a13 + a20*a31*a02*a13 +
             a30*a01*a22*a13 - a00*a31*a22*a13 - a20*a01*a32*a13 + a00*a21*a32*a13 +
             a30*a11*a02*a23 - a10*a31*a02*a23 - a30*a01*a12*a23 + a00*a31*a12*a23 +
             a10*a01*a32*a23 - a00*a11*a32*a23 - a20*a11*a02*a33 + a10*a21*a02*a33 +
             a20*a01*a12*a33 - a00*a21*a12*a33 - a10*a01*a22*a33 + a00*a11*a22*a33;

    return result;
}*/
    
    
    
    
    public double trace() {
        return 0;
    }
    
/*

// Get the trace of the matrix (sum of the values along the diagonal)
RMAPI float MatrixTrace(Matrix mat)
{
    float result = (mat.m0 + mat.m5 + mat.m10 + mat.m15);

    return result;
}*/

    
    
    
    public Matrix transpose() {
        return null;
    }
    
/*// Transposes provided matrix
RMAPI Matrix MatrixTranspose(Matrix mat)
{
    Matrix result = { 0 };

    result.m0 = mat.m0;
    result.m1 = mat.m4;
    result.m2 = mat.m8;
    result.m3 = mat.m12;
    result.m4 = mat.m1;
    result.m5 = mat.m5;
    result.m6 = mat.m9;
    result.m7 = mat.m13;
    result.m8 = mat.m2;
    result.m9 = mat.m6;
    result.m10 = mat.m10;
    result.m11 = mat.m14;
    result.m12 = mat.m3;
    result.m13 = mat.m7;
    result.m14 = mat.m11;
    result.m15 = mat.m15;

    return result;
}*/

    
    /**
     * Inverte a matriz corrente.
     */
    public Matrix invert() {
        
        Matrix result = new Matrix();

        // Cache the matrix values (speed optimization)
        double a00 = m0, a01 = m1, a02 = m2, a03 = m3;
        double a10 = m4, a11 = m5, a12 = m6, a13 = m7;
        double a20 = m8, a21 = m9, a22 = m10, a23 = m11;
        double a30 = m12, a31 = m13, a32 = m14, a33 = m15;

        double b00 = a00*a11 - a01*a10;
        double b01 = a00*a12 - a02*a10;
        double b02 = a00*a13 - a03*a10;
        double b03 = a01*a12 - a02*a11;
        double b04 = a01*a13 - a03*a11;
        double b05 = a02*a13 - a03*a12;
        double b06 = a20*a31 - a21*a30;
        double b07 = a20*a32 - a22*a30;
        double b08 = a20*a33 - a23*a30;
        double b09 = a21*a32 - a22*a31;
        double b10 = a21*a33 - a23*a31;
        double b11 = a22*a33 - a23*a32;

        // Calculate the invert determinant (inlined to avoid double-caching)
        double invDet = 1.0f/(b00*b11 - b01*b10 + b02*b09 + b03*b08 - b04*b07 + b05*b06);

        result.m0 = (a11*b11 - a12*b10 + a13*b09)*invDet;
        result.m1 = (-a01*b11 + a02*b10 - a03*b09)*invDet;
        result.m2 = (a31*b05 - a32*b04 + a33*b03)*invDet;
        result.m3 = (-a21*b05 + a22*b04 - a23*b03)*invDet;
        result.m4 = (-a10*b11 + a12*b08 - a13*b07)*invDet;
        result.m5 = (a00*b11 - a02*b08 + a03*b07)*invDet;
        result.m6 = (-a30*b05 + a32*b02 - a33*b01)*invDet;
        result.m7 = (a20*b05 - a22*b02 + a23*b01)*invDet;
        result.m8 = (a10*b10 - a11*b08 + a13*b06)*invDet;
        result.m9 = (-a00*b10 + a01*b08 - a03*b06)*invDet;
        result.m10 = (a30*b04 - a31*b02 + a33*b00)*invDet;
        result.m11 = (-a20*b04 + a21*b02 - a23*b00)*invDet;
        result.m12 = (-a10*b09 + a11*b07 - a12*b06)*invDet;
        result.m13 = (a00*b09 - a01*b07 + a02*b06)*invDet;
        result.m14 = (-a30*b03 + a31*b01 - a32*b00)*invDet;
        result.m15 = (a20*b03 - a21*b01 + a22*b00)*invDet;

        return result;
        
    }

    public static Matrix identity() {
        return null;
    }
    
/*// Get identity matrix
RMAPI Matrix MatrixIdentity(void)
{
    Matrix result = { 1.0f, 0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f, 0.0f,
                      0.0f, 0.0f, 1.0f, 0.0f,
                      0.0f, 0.0f, 0.0f, 1.0f };

    return result;
}*/

    
    public Matrix add( Matrix right ) {
        return null;
    }
    
/*// Add two matrices
RMAPI Matrix MatrixAdd(Matrix left, Matrix right)
{
    Matrix result = { 0 };

    result.m0 = left.m0 + right.m0;
    result.m1 = left.m1 + right.m1;
    result.m2 = left.m2 + right.m2;
    result.m3 = left.m3 + right.m3;
    result.m4 = left.m4 + right.m4;
    result.m5 = left.m5 + right.m5;
    result.m6 = left.m6 + right.m6;
    result.m7 = left.m7 + right.m7;
    result.m8 = left.m8 + right.m8;
    result.m9 = left.m9 + right.m9;
    result.m10 = left.m10 + right.m10;
    result.m11 = left.m11 + right.m11;
    result.m12 = left.m12 + right.m12;
    result.m13 = left.m13 + right.m13;
    result.m14 = left.m14 + right.m14;
    result.m15 = left.m15 + right.m15;

    return result;
}*/

    
    public Matrix subtract( Matrix right ) {
        return null;
    }
    
/*// Subtract two matrices (left - right)
RMAPI Matrix MatrixSubtract(Matrix left, Matrix right)
{
    Matrix result = { 0 };

    result.m0 = left.m0 - right.m0;
    result.m1 = left.m1 - right.m1;
    result.m2 = left.m2 - right.m2;
    result.m3 = left.m3 - right.m3;
    result.m4 = left.m4 - right.m4;
    result.m5 = left.m5 - right.m5;
    result.m6 = left.m6 - right.m6;
    result.m7 = left.m7 - right.m7;
    result.m8 = left.m8 - right.m8;
    result.m9 = left.m9 - right.m9;
    result.m10 = left.m10 - right.m10;
    result.m11 = left.m11 - right.m11;
    result.m12 = left.m12 - right.m12;
    result.m13 = left.m13 - right.m13;
    result.m14 = left.m14 - right.m14;
    result.m15 = left.m15 - right.m15;

    return result;
}*/

    
    /**
     * Multiplica a matriz corrente pela passada.
     */
    public Matrix multiply( Matrix right ) {
        
        Matrix result = new Matrix();

        result.m0 = m0*right.m0 + m1*right.m4 + m2*right.m8 + m3*right.m12;
        result.m1 = m0*right.m1 + m1*right.m5 + m2*right.m9 + m3*right.m13;
        result.m2 = m0*right.m2 + m1*right.m6 + m2*right.m10 + m3*right.m14;
        result.m3 = m0*right.m3 + m1*right.m7 + m2*right.m11 + m3*right.m15;
        result.m4 = m4*right.m0 + m5*right.m4 + m6*right.m8 + m7*right.m12;
        result.m5 = m4*right.m1 + m5*right.m5 + m6*right.m9 + m7*right.m13;
        result.m6 = m4*right.m2 + m5*right.m6 + m6*right.m10 + m7*right.m14;
        result.m7 = m4*right.m3 + m5*right.m7 + m6*right.m11 + m7*right.m15;
        result.m8 = m8*right.m0 + m9*right.m4 + m10*right.m8 + m11*right.m12;
        result.m9 = m8*right.m1 + m9*right.m5 + m10*right.m9 + m11*right.m13;
        result.m10 = m8*right.m2 + m9*right.m6 + m10*right.m10 + m11*right.m14;
        result.m11 = m8*right.m3 + m9*right.m7 + m10*right.m11 + m11*right.m15;
        result.m12 = m12*right.m0 + m13*right.m4 + m14*right.m8 + m15*right.m12;
        result.m13 = m12*right.m1 + m13*right.m5 + m14*right.m9 + m15*right.m13;
        result.m14 = m12*right.m2 + m13*right.m6 + m14*right.m10 + m15*right.m14;
        result.m15 = m12*right.m3 + m13*right.m7 + m14*right.m11 + m15*right.m15;

        return result;
        
    }

    /**
     * Cria uma matriz de translação.
     */
    public static Matrix translation( double x, double y, double z ) {
        return new Matrix( 
                1.0, 0.0, 0.0, x,
                0.0, 1.0, 0.0, y,
                0.0, 0.0, 1.0, z,
                0.0, 0.0, 0.0, 1.0
        );
    }
    
    /**
     * Cria uma matriz de rotação.
     */
    public static Matrix rotation( Vector3 axis, double angle ) {
        
        Matrix result = new Matrix();

        double x = axis.x, y = axis.y, z = axis.z;

        double lengthSquared = x*x + y*y + z*z;

        if ((lengthSquared != 1.0f) && (lengthSquared != 0.0f)) {
            double ilength = 1.0f/Math.sqrt(lengthSquared);
            x *= ilength;
            y *= ilength;
            z *= ilength;
        }

        double sinres = Math.sin(angle);
        double cosres = Math.cos(angle);
        double t = 1.0f - cosres;

        result.m0 = x*x*t + cosres;
        result.m1 = y*x*t + z*sinres;
        result.m2 = z*x*t - y*sinres;
        result.m3 = 0.0;

        result.m4 = x*y*t - z*sinres;
        result.m5 = y*y*t + cosres;
        result.m6 = z*y*t + x*sinres;
        result.m7 = 0.0;

        result.m8 = x*z*t + y*sinres;
        result.m9 = y*z*t - x*sinres;
        result.m10 = z*z*t + cosres;
        result.m11 = 0.0;

        result.m12 = 0.0;
        result.m13 = 0.0;
        result.m14 = 0.0;
        result.m15 = 1.0;

        return result;
        
    }

/*// Get x-rotation matrix
// NOTE: Angle must be provided in radians
RMAPI Matrix MatrixRotateX(float angle)
{
    Matrix result = { 1.0f, 0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f, 0.0f,
                      0.0f, 0.0f, 1.0f, 0.0f,
                      0.0f, 0.0f, 0.0f, 1.0f }; // MatrixIdentity()

    float cosres = cosf(angle);
    float sinres = sinf(angle);

    result.m5 = cosres;
    result.m6 = sinres;
    result.m9 = -sinres;
    result.m10 = cosres;

    return result;
}

// Get y-rotation matrix
// NOTE: Angle must be provided in radians
RMAPI Matrix MatrixRotateY(float angle)
{
    Matrix result = { 1.0f, 0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f, 0.0f,
                      0.0f, 0.0f, 1.0f, 0.0f,
                      0.0f, 0.0f, 0.0f, 1.0f }; // MatrixIdentity()

    float cosres = cosf(angle);
    float sinres = sinf(angle);

    result.m0 = cosres;
    result.m2 = -sinres;
    result.m8 = sinres;
    result.m10 = cosres;

    return result;
}

// Get z-rotation matrix
// NOTE: Angle must be provided in radians
RMAPI Matrix MatrixRotateZ(float angle)
{
    Matrix result = { 1.0f, 0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f, 0.0f,
                      0.0f, 0.0f, 1.0f, 0.0f,
                      0.0f, 0.0f, 0.0f, 1.0f }; // MatrixIdentity()

    float cosres = cosf(angle);
    float sinres = sinf(angle);

    result.m0 = cosres;
    result.m1 = sinres;
    result.m4 = -sinres;
    result.m5 = cosres;

    return result;
}


// Get xyz-rotation matrix
// NOTE: Angle must be provided in radians
RMAPI Matrix MatrixRotateXYZ(Vector3 angle)
{
    Matrix result = { 1.0f, 0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f, 0.0f,
                      0.0f, 0.0f, 1.0f, 0.0f,
                      0.0f, 0.0f, 0.0f, 1.0f }; // MatrixIdentity()

    float cosz = cosf(-angle.z);
    float sinz = sinf(-angle.z);
    float cosy = cosf(-angle.y);
    float siny = sinf(-angle.y);
    float cosx = cosf(-angle.x);
    float sinx = sinf(-angle.x);

    result.m0 = cosz*cosy;
    result.m1 = (cosz*siny*sinx) - (sinz*cosx);
    result.m2 = (cosz*siny*cosx) + (sinz*sinx);

    result.m4 = sinz*cosy;
    result.m5 = (sinz*siny*sinx) + (cosz*cosx);
    result.m6 = (sinz*siny*cosx) - (cosz*sinx);

    result.m8 = -siny;
    result.m9 = cosy*sinx;
    result.m10= cosy*cosx;

    return result;
}

// Get zyx-rotation matrix
// NOTE: Angle must be provided in radians
RMAPI Matrix MatrixRotateZYX(Vector3 angle)
{
    Matrix result = { 0 };

    float cz = cosf(angle.z);
    float sz = sinf(angle.z);
    float cy = cosf(angle.y);
    float sy = sinf(angle.y);
    float cx = cosf(angle.x);
    float sx = sinf(angle.x);

    result.m0 = cz*cy;
    result.m4 = cz*sy*sx - cx*sz;
    result.m8 = sz*sx + cz*cx*sy;
    result.m12 = 0;

    result.m1 = cy*sz;
    result.m5 = cz*cx + sz*sy*sx;
    result.m9 = cx*sz*sy - cz*sx;
    result.m13 = 0;

    result.m2 = -sy;
    result.m6 = cy*sx;
    result.m10 = cy*cx;
    result.m14 = 0;

    result.m3 = 0;
    result.m7 = 0;
    result.m11 = 0;
    result.m15 = 1;

    return result;
}*/

    /**
     * Cria uma matriz de escalonamento.
     */
    public static Matrix scaling( double x, double y, double z ) {
        return new Matrix( 
                x, 0.0, 0.0, 0.0,
                0.0, y, 0.0, 0.0,
                0.0, 0.0, z, 0.0,
                0.0, 0.0, 0.0, 1.0
        );
    }

/*// Get perspective projection matrix
    RMAPI Matrix MatrixFrustum(double left, double right, double bottom, double top, double nearPlane, double farPlane)
    {
    Matrix result = { 0 };
    float rl = (float)(right - left);
    float tb = (float)(top - bottom);
    float fn = (float)(farPlane - nearPlane);
    result.m0 = ((float)nearPlane*2.0f)/rl;
    result.m1 = 0.0f;
    result.m2 = 0.0f;
    result.m3 = 0.0f;
    result.m4 = 0.0f;
    result.m5 = ((float)nearPlane*2.0f)/tb;
    result.m6 = 0.0f;
    result.m7 = 0.0f;
    result.m8 = ((float)right + (float)left)/rl;
    result.m9 = ((float)top + (float)bottom)/tb;
    result.m10 = -((float)farPlane + (float)nearPlane)/fn;
    result.m11 = -1.0f;
    result.m12 = 0.0f;
    result.m13 = 0.0f;
    result.m14 = -((float)farPlane*(float)nearPlane*2.0f)/fn;
    result.m15 = 0.0f;
    return result;
    }
    // Get perspective projection matrix
    // NOTE: Fovy angle must be provided in radians
    RMAPI Matrix MatrixPerspective(double fovY, double aspect, double nearPlane, double farPlane)
    {
    Matrix result = { 0 };
    double top = nearPlane*tan(fovY*0.5);
    double bottom = -top;
    double right = top*aspect;
    double left = -right;
    // MatrixFrustum(-right, right, -top, top, near, far);
    float rl = (float)(right - left);
    float tb = (float)(top - bottom);
    float fn = (float)(farPlane - nearPlane);
    result.m0 = ((float)nearPlane*2.0f)/rl;
    result.m5 = ((float)nearPlane*2.0f)/tb;
    result.m8 = ((float)right + (float)left)/rl;
    result.m9 = ((float)top + (float)bottom)/tb;
    result.m10 = -((float)farPlane + (float)nearPlane)/fn;
    result.m11 = -1.0f;
    result.m14 = -((float)farPlane*(float)nearPlane*2.0f)/fn;
    return result;
    }
    // Get orthographic projection matrix
    RMAPI Matrix MatrixOrtho(double left, double right, double bottom, double top, double nearPlane, double farPlane)
    {
    Matrix result = { 0 };
    float rl = (float)(right - left);
    float tb = (float)(top - bottom);
    float fn = (float)(farPlane - nearPlane);
    result.m0 = 2.0f/rl;
    result.m1 = 0.0f;
    result.m2 = 0.0f;
    result.m3 = 0.0f;
    result.m4 = 0.0f;
    result.m5 = 2.0f/tb;
    result.m6 = 0.0f;
    result.m7 = 0.0f;
    result.m8 = 0.0f;
    result.m9 = 0.0f;
    result.m10 = -2.0f/fn;
    result.m11 = 0.0f;
    result.m12 = -((float)left + (float)right)/rl;
    result.m13 = -((float)top + (float)bottom)/tb;
    result.m14 = -((float)farPlane + (float)nearPlane)/fn;
    result.m15 = 1.0f;
    return result;
    }
    // Get camera look-at matrix (view matrix)
    RMAPI Matrix MatrixLookAt(Vector3 eye, Vector3 target, Vector3 up)
    {
    Matrix result = { 0 };
    float length = 0.0f;
    float ilength = 0.0f;
    // Vector3Subtract(eye, target)
    Vector3 vz = { eye.x - target.x, eye.y - target.y, eye.z - target.z };
    // Vector3Normalize(vz)
    Vector3 v = vz;
    length = sqrtf(v.x*v.x + v.y*v.y + v.z*v.z);
    if (length == 0.0f) length = 1.0f;
    ilength = 1.0f/length;
    vz.x *= ilength;
    vz.y *= ilength;
    vz.z *= ilength;
    // Vector3CrossProduct(up, vz)
    Vector3 vx = { up.y*vz.z - up.z*vz.y, up.z*vz.x - up.x*vz.z, up.x*vz.y - up.y*vz.x };
    // Vector3Normalize(x)
    v = vx;
    length = sqrtf(v.x*v.x + v.y*v.y + v.z*v.z);
    if (length == 0.0f) length = 1.0f;
    ilength = 1.0f/length;
    vx.x *= ilength;
    vx.y *= ilength;
    vx.z *= ilength;
    // Vector3CrossProduct(vz, vx)
    Vector3 vy = { vz.y*vx.z - vz.z*vx.y, vz.z*vx.x - vz.x*vx.z, vz.x*vx.y - vz.y*vx.x };
    result.m0 = vx.x;
    result.m1 = vy.x;
    result.m2 = vz.x;
    result.m3 = 0.0f;
    result.m4 = vx.y;
    result.m5 = vy.y;
    result.m6 = vz.y;
    result.m7 = 0.0f;
    result.m8 = vx.z;
    result.m9 = vy.z;
    result.m10 = vz.z;
    result.m11 = 0.0f;
    result.m12 = -(vx.x*eye.x + vx.y*eye.y + vx.z*eye.z);   // Vector3DotProduct(vx, eye)
    result.m13 = -(vy.x*eye.x + vy.y*eye.y + vy.z*eye.z);   // Vector3DotProduct(vy, eye)
    result.m14 = -(vz.x*eye.x + vz.y*eye.y + vz.z*eye.z);   // Vector3DotProduct(vz, eye)
    result.m15 = 1.0f;
    return result;
    }
    // Get float array of matrix data
    RMAPI float16 MatrixToFloatV(Matrix mat)
    {
    float16 result = { 0 };
    result.v[0] = mat.m0;
    result.v[1] = mat.m1;
    result.v[2] = mat.m2;
    result.v[3] = mat.m3;
    result.v[4] = mat.m4;
    result.v[5] = mat.m5;
    result.v[6] = mat.m6;
    result.v[7] = mat.m7;
    result.v[8] = mat.m8;
    result.v[9] = mat.m9;
    result.v[10] = mat.m10;
    result.v[11] = mat.m11;
    result.v[12] = mat.m12;
    result.v[13] = mat.m13;
    result.v[14] = mat.m14;
    result.v[15] = mat.m15;
    return result;
    }
    // Decompose a transformation matrix into its rotational, translational and scaling components
    RMAPI void MatrixDecompose(Matrix mat, Vector3 *translation, Quaternion *rotation, Vector3 *scaling)
    {
    // Extract translation.
    translation->x = mat.m12;
    translation->y = mat.m13;
    translation->z = mat.m14;
    // Extract upper-left for determinant computation
    const float a = mat.m0;
    const float b = mat.m4;
    const float c = mat.m8;
    const float d = mat.m1;
    const float e = mat.m5;
    const float f = mat.m9;
    const float g = mat.m2;
    const float h = mat.m6;
    const float i = mat.m10;
    const float A = e*i - f*h;
    const float B = f*g - d*i;
    const float C = d*h - e*g;
    // Extract scaling
    const float det = a*A + b*B + c*C;
    Vector3 abc = { a, b, c };
    Vector3 def = { d, e, f };
    Vector3 ghi = { g, h, i };
    float scalex = Vector3Length(abc);
    float scaley = Vector3Length(def);
    float scalez = Vector3Length(ghi);
    Vector3 s = { scalex, scaley, scalez };
    if (det < 0) s = Vector3Negate(s);
     *scaling = s;
    // Remove scaling from the matrix if it is not close to zero
    Matrix clone = mat;
    if (!FloatEquals(det, 0))
    {
    clone.m0 /= s.x;
    clone.m5 /= s.y;
    clone.m10 /= s.z;
    // Extract rotation
     *rotation = QuaternionFromMatrix(clone);
    }
    else
    {
    // Set to identity if close to zero
     *rotation = QuaternionIdentity();
    }
    }
     */
    
    @Override
    public int hashCode(  ) {
        int hash = 7;
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m0 ) ^ ( Double.doubleToLongBits( this.m0 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m4 ) ^ ( Double.doubleToLongBits( this.m4 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m8 ) ^ ( Double.doubleToLongBits( this.m8 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m12 ) ^ ( Double.doubleToLongBits( this.m12 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m1 ) ^ ( Double.doubleToLongBits( this.m1 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m5 ) ^ ( Double.doubleToLongBits( this.m5 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m9 ) ^ ( Double.doubleToLongBits( this.m9 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m13 ) ^ ( Double.doubleToLongBits( this.m13 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m2 ) ^ ( Double.doubleToLongBits( this.m2 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m6 ) ^ ( Double.doubleToLongBits( this.m6 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m10 ) ^ ( Double.doubleToLongBits( this.m10 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m14 ) ^ ( Double.doubleToLongBits( this.m14 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m3 ) ^ ( Double.doubleToLongBits( this.m3 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m7 ) ^ ( Double.doubleToLongBits( this.m7 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m11 ) ^ ( Double.doubleToLongBits( this.m11 ) >>> 32 ) );
        hash = 97 * hash + (int) ( Double.doubleToLongBits( this.m15 ) ^ ( Double.doubleToLongBits( this.m15 ) >>> 32 ) );
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
        final Matrix other = (Matrix) obj;
        if ( Double.doubleToLongBits( this.m0 ) != Double.doubleToLongBits( other.m0 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m4 ) != Double.doubleToLongBits( other.m4 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m8 ) != Double.doubleToLongBits( other.m8 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m12 ) != Double.doubleToLongBits( other.m12 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m1 ) != Double.doubleToLongBits( other.m1 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m5 ) != Double.doubleToLongBits( other.m5 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m9 ) != Double.doubleToLongBits( other.m9 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m13 ) != Double.doubleToLongBits( other.m13 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m2 ) != Double.doubleToLongBits( other.m2 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m6 ) != Double.doubleToLongBits( other.m6 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m10 ) != Double.doubleToLongBits( other.m10 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m14 ) != Double.doubleToLongBits( other.m14 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m3 ) != Double.doubleToLongBits( other.m3 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m7 ) != Double.doubleToLongBits( other.m7 ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.m11 ) != Double.doubleToLongBits( other.m11 ) ) {
            return false;
        }
        return Double.doubleToLongBits( this.m15 ) == Double.doubleToLongBits( other.m15 );
    }

    @Override
    public String toString() {
        return String.format(
            """
                            Matrix[
                                %.2f, %.2f, %.2f, %.2f
                                %.2f, %.2f, %.2f, %.2f
                                %.2f, %.2f, %.2f, %.2f
                                %.2f, %.2f, %.2f, %.2f
                            ]
                            """,
            m0, m4, m8, m12,
            m1, m5, m9, m13,
            m2, m6, m10, m14,
            m3, m7, m11, m15
        );
    }
    
}
