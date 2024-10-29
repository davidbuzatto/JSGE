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
public class Matrix implements Cloneable {
    
    /** Linha 1, coluna 1 */
    public double m0;
    /** Linha 1, coluna 2 */
    public double m4;
    /** Linha 1, coluna 3 */
    public double m8;
    /** Linha 1, coluna 4 */
    public double m12;
    
    /** Linha 2, coluna 1 */
    public double m1;
    /** Linha 2, coluna 2 */
    public double m5;
    /** Linha 2, coluna 3 */
    public double m9;
    /** Linha 2, coluna 4 */
    public double m13;
    
    /** Linha 3, coluna 1 */
    public double m2;
    /** Linha 3, coluna 2 */
    public double m6;
    /** Linha 3, coluna 3 */
    public double m10;
    /** Linha 3, coluna 4 */
    public double m14;
    
    /** Linha 4, coluna 1 */
    public double m3;
    /** Linha 4, coluna 2 */
    public double m7;
    /** Linha 4, coluna 3 */
    public double m11;
    /** Linha 4, coluna 4 */
    public double m15;

    /**
     * Cria uma nova matriz com valores padrão.
     */
    public Matrix() {
    }

    /**
     * Cria uma nova matriz.
     * 
     * @param m0 Valor da linha 1, coluna 1
     * @param m4 Valor da linha 1, coluna 2
     * @param m8 Valor da linha 1, coluna 3
     * @param m12 Valor da linha 1, coluna 4
     * @param m1 Valor da linha 2, coluna 1
     * @param m5 Valor da linha 2, coluna 2
     * @param m9 Valor da linha 2, coluna 3
     * @param m13 Valor da linha 2, coluna 4
     * @param m2 Valor da linha 3, coluna 1
     * @param m6 Valor da linha 3, coluna 2
     * @param m10 Valor da linha 3, coluna 3
     * @param m14 Valor da linha 3, coluna 4
     * @param m3 Valor da linha 4, coluna 1
     * @param m7 Valor da linha 4, coluna 2
     * @param m11 Valor da linha 4, coluna 3
     * @param m15 Valor da linha 4, coluna 4
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
    
    /**
     * Calcula o determinante da matriz.
     * 
     * @return O determinante da matriz.
     */
    public double determinant() {
        
        double result = 0.0f;

        double a00 = m0, a01 = m1, a02 = m2, a03 = m3;
        double a10 = m4, a11 = m5, a12 = m6, a13 = m7;
        double a20 = m8, a21 = m9, a22 = m10, a23 = m11;
        double a30 = m12, a31 = m13, a32 = m14, a33 = m15;

        result = a30 * a21 * a12 * a03 - a20 * a31 * a12 * a03 - a30 * a11 * a22 * a03 + a10 * a31 * a22 * a03 +
                 a20 * a11 * a32 * a03 - a10 * a21 * a32 * a03 - a30 * a21 * a02 * a13 + a20 * a31 * a02 * a13 +
                 a30 * a01 * a22 * a13 - a00 * a31 * a22 * a13 - a20 * a01 * a32 * a13 + a00 * a21 * a32 * a13 +
                 a30 * a11 * a02 * a23 - a10 * a31 * a02 * a23 - a30 * a01 * a12 * a23 + a00 * a31 * a12 * a23 +
                 a10 * a01 * a32 * a23 - a00 * a11 * a32 * a23 - a20 * a11 * a02 * a33 + a10 * a21 * a02 * a33 +
                 a20 * a01 * a12 * a33 - a00 * a21 * a12 * a33 - a10 * a01 * a22 * a33 + a00 * a11 * a22 * a33;

        return result;
    
    }
    
    /**
     * Calcila o traço da matriz (soma dos valores da diagonal).
     * 
     * @return O traço de matriz.
     */
    public double trace() {
        return m0 + m5 + m10 + m15;
    }
    
    /**
     * Transpõe a matriz corrente.
     * 
     * @return Uma nova matriz transposta.
     */
    public Matrix transpose() {
        
        Matrix result = new Matrix();

        result.m0 = m0;
        result.m1 = m4;
        result.m2 = m8;
        result.m3 = m12;
        result.m4 = m1;
        result.m5 = m5;
        result.m6 = m9;
        result.m7 = m13;
        result.m8 = m2;
        result.m9 = m6;
        result.m10 = m10;
        result.m11 = m14;
        result.m12 = m3;
        result.m13 = m7;
        result.m14 = m11;
        result.m15 = m15;

        return result;
    
    }
    
    /**
     * Inverte a matriz corrente.
     * 
     * @return Uma nova matriz invertida.
     */
    public Matrix invert() {
        
        Matrix result = new Matrix();

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

        double invDet = 1.0 / (b00*b11 - b01*b10 + b02*b09 + b03*b08 - b04*b07 + b05*b06);

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

    /**
     * Soma a matriz corrente com outra matriz.
     * 
     * @param m Outra matriz.
     * @return Uma nova matriz da soma.
     */
    public Matrix add( Matrix m ) {
        
        Matrix result = new Matrix();

        result.m0 = m0 + m.m0;
        result.m1 = m1 + m.m1;
        result.m2 = m2 + m.m2;
        result.m3 = m3 + m.m3;
        result.m4 = m4 + m.m4;
        result.m5 = m5 + m.m5;
        result.m6 = m6 + m.m6;
        result.m7 = m7 + m.m7;
        result.m8 = m8 + m.m8;
        result.m9 = m9 + m.m9;
        result.m10 = m10 + m.m10;
        result.m11 = m11 + m.m11;
        result.m12 = m12 + m.m12;
        result.m13 = m13 + m.m13;
        result.m14 = m14 + m.m14;
        result.m15 = m15 + m.m15;

        return result;
    
    }

    /**
     * Subtrai da matriz corrente a outra matriz.
     * 
     * @param m Outra matriz.
     * @return Uma nova matriz da subtração.
     */
    public Matrix subtract( Matrix m ) {
        
        Matrix result = new Matrix();

        result.m0 = m0 - m.m0;
        result.m1 = m1 - m.m1;
        result.m2 = m2 - m.m2;
        result.m3 = m3 - m.m3;
        result.m4 = m4 - m.m4;
        result.m5 = m5 - m.m5;
        result.m6 = m6 - m.m6;
        result.m7 = m7 - m.m7;
        result.m8 = m8 - m.m8;
        result.m9 = m9 - m.m9;
        result.m10 = m10 - m.m10;
        result.m11 = m11 - m.m11;
        result.m12 = m12 - m.m12;
        result.m13 = m13 - m.m13;
        result.m14 = m14 - m.m14;
        result.m15 = m15 - m.m15;

        return result;
        
    }
    
    /**
     * Multiplica a matriz corrente pela passada.
     * 
     * @param m Outra matriz.
     * @return Uma nova matriz com a multiplicação.
     */
    public Matrix multiply( Matrix m ) {
        
        Matrix result = new Matrix();

        result.m0 = m0*m.m0 + m1*m.m4 + m2*m.m8 + m3*m.m12;
        result.m1 = m0*m.m1 + m1*m.m5 + m2*m.m9 + m3*m.m13;
        result.m2 = m0*m.m2 + m1*m.m6 + m2*m.m10 + m3*m.m14;
        result.m3 = m0*m.m3 + m1*m.m7 + m2*m.m11 + m3*m.m15;
        result.m4 = m4*m.m0 + m5*m.m4 + m6*m.m8 + m7*m.m12;
        result.m5 = m4*m.m1 + m5*m.m5 + m6*m.m9 + m7*m.m13;
        result.m6 = m4*m.m2 + m5*m.m6 + m6*m.m10 + m7*m.m14;
        result.m7 = m4*m.m3 + m5*m.m7 + m6*m.m11 + m7*m.m15;
        result.m8 = m8*m.m0 + m9*m.m4 + m10*m.m8 + m11*m.m12;
        result.m9 = m8*m.m1 + m9*m.m5 + m10*m.m9 + m11*m.m13;
        result.m10 = m8*m.m2 + m9*m.m6 + m10*m.m10 + m11*m.m14;
        result.m11 = m8*m.m3 + m9*m.m7 + m10*m.m11 + m11*m.m15;
        result.m12 = m12*m.m0 + m13*m.m4 + m14*m.m8 + m15*m.m12;
        result.m13 = m12*m.m1 + m13*m.m5 + m14*m.m9 + m15*m.m13;
        result.m14 = m12*m.m2 + m13*m.m6 + m14*m.m10 + m15*m.m14;
        result.m15 = m12*m.m3 + m13*m.m7 + m14*m.m11 + m15*m.m15;

        return result;
        
    }
    
    /**
     * Cria uma matriz identidade.
     * 
     * @return Uma nova matriz identidade.
     */
    public static Matrix identity() {
        return new Matrix(
                1.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0 );
    }
    
    /**
     * Cria uma matriz de translação.
     * 
     * @param x Posição em x.
     * @param y Posição em y.
     * @param z Posição em z.
     * @return Uma nova matriz de translação.
     */
    public static Matrix translate( double x, double y, double z ) {
        return new Matrix( 
                1.0, 0.0, 0.0, x,
                0.0, 1.0, 0.0, y,
                0.0, 0.0, 1.0, z,
                0.0, 0.0, 0.0, 1.0
        );
    }
    
    /**
     * Cria uma matriz de rotação.
     * 
     * @param axis O eixo de rotação.
     * @param angle O ângulo em radianos.
     * @return Uma nova matriz de rotação.
     */
    public static Matrix rotate( Vector3 axis, double angle ) {
        
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
    
    /**
     * Cria uma matriz de rotação em x.
     * 
     * @param angle O ângulo em radianos.
     * @return Uma nova matriz de rotação em x.
     */
    public static Matrix rotateX( double angle ) {
        
        Matrix result = identity();

        double cosres = Math.cos(angle);
        double sinres = Math.sin(angle);

        result.m5 = cosres;
        result.m6 = sinres;
        result.m9 = -sinres;
        result.m10 = cosres;

        return result;
        
    }
    
    /**
     * Cria uma matriz de rotação em y.
     * 
     * @param angle O ângulo em radianos.
     * @return Uma nova matriz de rotação em y.
     */
    public static Matrix rotateY( double angle ) {
        
        Matrix result = identity();

        double cosres = Math.cos(angle);
        double sinres = Math.sin(angle);

        result.m0 = cosres;
        result.m2 = -sinres;
        result.m8 = sinres;
        result.m10 = cosres;

        return result;
        
    }
    
    /**
     * Cria uma matriz de rotação em z.
     * 
     * @param angle O ângulo em radianos.
     * @return Uma nova matriz de rotação em z.
     */
    public static Matrix rotateZ( double angle ) {
        
        Matrix result = identity();

        double cosres = Math.cos(angle);
        double sinres = Math.sin(angle);

        result.m0 = cosres;
        result.m1 = sinres;
        result.m4 = -sinres;
        result.m5 = cosres;

        return result;
        
    }
    
    /**
     * Cria uma matriz de rotação em em xyz.
     * 
     * @param angle Um vetor com os ângulos em radianos.
     * @return Uma nova matriz de rotação em xyz.
     */
    public static Matrix rotateXYZ( Vector3 angle ) {
        
        Matrix result = identity();

        double cosz = Math.cos(-angle.z);
        double sinz = Math.sin(-angle.z);
        double cosy = Math.cos(-angle.y);
        double siny = Math.sin(-angle.y);
        double cosx = Math.cos(-angle.x);
        double sinx = Math.sin(-angle.x);

        result.m0 = cosz * cosy;
        result.m1 = (cosz * siny * sinx) - (sinz * cosx);
        result.m2 = (cosz * siny * cosx) + (sinz * sinx);

        result.m4 = sinz * cosy;
        result.m5 = (sinz * siny * sinx) + (cosz * cosx);
        result.m6 = (sinz * siny * cosx) - (cosz * sinx);

        result.m8 = -siny;
        result.m9 = cosy * sinx;
        result.m10= cosy * cosx;

        return result;
        
    }
    
    /**
     * Cria uma matriz de rotação em em zyx.
     * 
     * @param angle Um vetor com os ângulos em radianos.
     * @return Uma nova matriz de rotação em zyx.
     */
    public static Matrix rotateZYX( Vector3 angle ) {
        
        Matrix result = new Matrix();

        double cz = Math.cos(angle.z);
        double sz = Math.sin(angle.z);
        double cy = Math.cos(angle.y);
        double sy = Math.sin(angle.y);
        double cx = Math.cos(angle.x);
        double sx = Math.sin(angle.x);

        result.m0 = cz * cy;
        result.m4 = cz * sy * sx - cx * sz;
        result.m8 = sz * sx + cz * cx * sy;
        result.m12 = 0;

        result.m1 = cy * sz;
        result.m5 = cz * cx + sz * sy * sx;
        result.m9 = cx * sz * sy - cz * sx;
        result.m13 = 0;

        result.m2 = -sy;
        result.m6 = cy * sx;
        result.m10 = cy * cx;
        result.m14 = 0;

        result.m3 = 0;
        result.m7 = 0;
        result.m11 = 0;
        result.m15 = 1;

        return result;
        
    }
    
    /**
     * Cria uma matriz de escalonamento.
     * 
     * @param x Escala em x.
     * @param y Escala em y.
     * @param z Escala em z.
     * @return Uma nova matriz escalonada.
     */
    public static Matrix scale( double x, double y, double z ) {
        return new Matrix( 
                x, 0.0, 0.0, 0.0,
                0.0, y, 0.0, 0.0,
                0.0, 0.0, z, 0.0,
                0.0, 0.0, 0.0, 1.0
        );
    }
    
    /**
     * Cria uma matriz de projeção de perspectiva.
     * 
     * @param left Esquerda.
     * @param right Direita.
     * @param bottom Baixo.
     * @param top Cima.
     * @param nearPlane Plano próximo.
     * @param farPlane Plano distante.
     * @return Uma nova matriz de projeção.
     */
    public static Matrix frustrum( double left, double right, double bottom, double top, double nearPlane, double farPlane ) {
        
        Matrix result = new Matrix();
        
        double rl = (right - left);
        double tb = (top - bottom);
        double fn = (farPlane - nearPlane);
        
        result.m0 = (nearPlane * 2.0)/rl;
        result.m1 = 0.0;
        result.m2 = 0.0;
        result.m3 = 0.0;
        result.m4 = 0.0;
        result.m5 = (nearPlane * 2.0)/tb;
        result.m6 = 0.0;
        result.m7 = 0.0;
        result.m8 = (right + left)/rl;
        result.m9 = (top + bottom)/tb;
        result.m10 = -(farPlane + nearPlane)/fn;
        result.m11 = -1.0;
        result.m12 = 0.0;
        result.m13 = 0.0;
        result.m14 = -(farPlane * nearPlane * 2.0)/fn;
        result.m15 = 0.0;
        
        return result;
    
    }
    
    /**
     * Cria uma matriz de projeção de perspectiva.
     * 
     * @param fovY Campo de visão em radianos.
     * @param aspect Aspecto.
     * @param nearPlane Plano próximo.
     * @param farPlane Plano distante.
     * @return Uma nova matriz de projeção.
     */
    public static Matrix perspective( double fovY, double aspect, double nearPlane, double farPlane ) {
        
        Matrix result = new Matrix();
        
        double top = nearPlane * Math.tan(fovY * 0.5);
        double bottom = -top;
        double right = top * aspect;
        double left = -right;
        
        double rl = (right - left);
        double tb = (top - bottom);
        double fn = (farPlane - nearPlane);
        result.m0 = (nearPlane * 2.0)/rl;
        result.m5 = (nearPlane * 2.0)/tb;
        result.m8 = (right + left)/rl;
        result.m9 = (top + bottom)/tb;
        result.m10 = -(farPlane + nearPlane)/fn;
        result.m11 = -1.0;
        result.m14 = -(farPlane * nearPlane * 2.0)/fn;
        
        return result;
    
    }

    /**
     * Cria uma matriz de projeção ortográfica.
     * 
     * @param left Esquerda.
     * @param right Direita.
     * @param bottom Baixo.
     * @param top Cima.
     * @param nearPlane Plano próximo.
     * @param farPlane Plano distante.
     * @return Uma nova matriz de projeção.
     */
    public static Matrix ortho( double left, double right, double bottom, double top, double nearPlane, double farPlane ) {
        
        Matrix result = new Matrix();
        
        double rl = (right - left);
        double tb = (top - bottom);
        double fn = (farPlane - nearPlane);
        
        result.m0 = 2.0/rl;
        result.m1 = 0.0;
        result.m2 = 0.0;
        result.m3 = 0.0;
        result.m4 = 0.0;
        result.m5 = 2.0/tb;
        result.m6 = 0.0;
        result.m7 = 0.0;
        result.m8 = 0.0;
        result.m9 = 0.0;
        result.m10 = -2.0/fn;
        result.m11 = 0.0;
        result.m12 = -(left + right)/rl;
        result.m13 = -(top + bottom)/tb;
        result.m14 = -(farPlane + nearPlane)/fn;
        result.m15 = 1.0;
        
        return result;
    
    }
    
    /**
     * Obtém a matriz de "look-at" de uma câmera (view matriz).
     * 
     * @param eye Posição do olho.
     * @param target Posição do alvo.
     * @param up Vetor cima.
     * @return Uma nova matriz "look-at".
     */
    public static Matrix lookAt( Vector3 eye, Vector3 target, Vector3 up ) {
        
        Matrix result = new Matrix();
        double length = 0.0;
        double ilength = 0.0;
        
        Vector3 vz = new Vector3( eye.x - target.x, eye.y - target.y, eye.z - target.z );
        Vector3 v = new Vector3( vz.x, vz.y, vz.z );
        
        length = Math.sqrt( v.x * v.x + v.y * v.y + v.z * v.z );
        if ( length == 0.0 ) {
            length = 1.0;
        }
        ilength = 1.0/length;
        
        vz.x *= ilength;
        vz.y *= ilength;
        vz.z *= ilength;
        
        Vector3 vx = new Vector3( up.y * vz.z - up.z * vz.y, up.z * vz.x - up.x * vz.z, up.x * vz.y - up.y * vz.x );
        v.x = vx.x;
        v.y = vx.y;
        v.z = vx.z;
        
        length = Math.sqrt( v.x * v.x + v.y * v.y + v.z * v.z );
        if ( length == 0.0 ) {
            length = 1.0;
        }
        ilength = 1.0/length;
        
        vx.x *= ilength;
        vx.y *= ilength;
        vx.z *= ilength;
        
        Vector3 vy = new Vector3( vz.y * vx.z - vz.z * vx.y, vz.z * vx.x - vz.x * vx.z, vz.x * vx.y - vz.y * vx.x );
        result.m0 = vx.x;
        result.m1 = vy.x;
        result.m2 = vz.x;
        result.m3 = 0.0;
        result.m4 = vx.y;
        result.m5 = vy.y;
        result.m6 = vz.y;
        result.m7 = 0.0;
        result.m8 = vx.z;
        result.m9 = vy.z;
        result.m10 = vz.z;
        result.m11 = 0.0;
        result.m12 = -(vx.x * eye.x + vx.y * eye.y + vx.z * eye.z);
        result.m13 = -(vy.x * eye.x + vy.y * eye.y + vy.z * eye.z);
        result.m14 = -(vz.x * eye.x + vz.y * eye.y + vz.z * eye.z);
        result.m15 = 1.0;
        
        return result;
    
    }
    
    /**
     * Decompõe a matriz em seus componentes rotacionais, translacionais e de escalonamento.
     * @param translation Vetor que receberá os dados translacionais.
     * @param rotation Quaternion que receberá os componentes rotacionais.
     * @param scaling Vetor que receberá os componentes de escalonamento.
     */
    public void decompose( Vector3 translation, Quaternion rotation, Vector3 scaling ) {
        
        translation.x = m12;
        translation.y = m13;
        translation.z = m14;
        
        double a = m0;
        double b = m4;
        double c = m8;
        double d = m1;
        double e = m5;
        double f = m9;
        double g = m2;
        double h = m6;
        double i = m10;
        double A = e * i - f * h;
        double B = f * g - d * i;
        double C = d * h - e * g;
        
        double det = a * A + b * B + c * C;
        Vector3 abc = new Vector3( a, b, c );
        Vector3 def = new Vector3( d, e, f );
        Vector3 ghi = new Vector3( g, h, i );
        double scalex = abc.length();
        double scaley = def.length();
        double scalez = ghi.length();
        Vector3 s = new Vector3( scalex, scaley, scalez );
        if ( det < 0 ) {
            s = s.negate();
        }
        scaling.x = s.x;
        scaling.y = s.y;
        scaling.z = s.z;
        
        try {
            
            Matrix clone = (Matrix) clone();

            if ( Double.doubleToLongBits( det ) != Double.doubleToLongBits( 0 ) ) {
                clone.m0 /= s.x;
                clone.m5 /= s.y;
                clone.m10 /= s.z;
                Quaternion q = Quaternion.fromMatrix( clone );
                rotation.x = q.x;
                rotation.y = q.y;
                rotation.z = q.z;
                rotation.w = q.w;
            } else {
                Quaternion q = Quaternion.identity();
                rotation.x = q.x;
                rotation.y = q.y;
                rotation.z = q.z;
                rotation.w = q.w;
            }
            
        } catch ( CloneNotSupportedException exc ) {
        }
        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Matrix clone = (Matrix) super.clone();
        clone.m0 = m0;
        clone.m4 = m4;
        clone.m8 = m8;
        clone.m12 = m12;
        clone.m1 = m1;
        clone.m5 = m5;
        clone.m9 = m9;
        clone.m13 = m13;
        clone.m2 = m2;
        clone.m6 = m6;
        clone.m10 = m10;
        clone.m14 = m14;
        clone.m3 = m3;
        clone.m7 = m7;
        clone.m11 = m11;
        clone.m15 = m15;
        return clone;
    }
    
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
