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
 * Classe para representação de um vetor de três dimensões.
 * 
 * Pode ser usada para retornar valores com três componentes, como pontos
 * 3D etc.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector3 {
    
    /**
     * Coordenada x.
     */
    public double x;
    
    /**
     * Coordenada y.
     */
    public double y;
    
    /**
     * Coordenada z.
     */
    public double z;

    /**
     * Cria um novo vetor de três dimensões com valores padrão.
     */
    public Vector3() {
    }

    /**
     * Cria um novo vetor de três dimensões.
     * 
     * @param x coordenada x.
     * @param y coordenada y.
     * @param z coordenada z.
     */
    public Vector3( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Cria um vetor 3D com todos os componentes iguais a 1.0.
     * 
     * @return Um vetor 3D com todos os componentes iguais a 1.0.
     */
    public static Vector3 one() {
        return new Vector3( 1.0, 1.0, 1.0 );
    }
    
    /**
     * Soma o vetor corrente com outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor resultado da soma.
     */
    public Vector3 add( Vector3 v ) {
        return new Vector3( x + v.x, y + v.y, z + v.z );
    }
    
    /**
     * Soma um valor ao vetor corrente.
     * 
     * @param value O valor a somar.
     * @return Um novo vetor com os componentes somados ao valor passado.
     */
    public Vector3 addValue( double value ) {
        return new Vector3( x + value, y + value, z + value );
    }
    
    /**
     * Subtrai um vetor do vetor corrente.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor resultado da subtração.
     */
    public Vector3 subtract( Vector3 v ) {
        return new Vector3( x - v.x, y - v.y, z - v.z );
    }
    
    /**
     * Subtrai um valor do vetor corrente
     * 
     * @param value O valor a subtrair.
     * @return Um novo vetor 2D com os componentes subtraídos do valor passado.
     */
    public Vector3 subtractValue( double value ) {
        return new Vector3( x - value, y - value, z - value );
    }
    
    /**
     * Escalona o vetor corrente, análogo à multiplicação por escalar.
     * 
     * @param scale A escala.
     * @return Um novo vetor escalonado.
     */
    public Vector3 scale( double scale ) {
        return new Vector3( x * scale, y * scale, z * scale );
    }
    
    /**
     * Multiplica o vetor corrente por outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o resultado da multiplicação.
     */
    public Vector3 multiply( Vector3 v ) {
        return new Vector3( x * v.x, y * v.y, z * v.z );
    }
    
    
    /**
     * Calcula o produto vetorial do vetor corrente com o vetor passado.
     * 
     * @param v Outro vetor.
     * @return O produto vetorial.
     */
    public Vector3 crossProduct( Vector3 v ) {
        return new Vector3( y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x );
    }
    
    /**
     * Calcula o vetor perpendicular ao vetor corrente.
     * 
     * @return O vetor perpendicular.
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
        
        // produto vetorial
        result.x = y * cardinalAxis.z - z * cardinalAxis.y;
        result.y = z * cardinalAxis.x - x * cardinalAxis.z;
        result.z = x * cardinalAxis.y - y * cardinalAxis.x;
        
        return result;
        
    }
    
    /**
     * Calcula o comprimento do vetor.
     * 
     * @return O comprimento.
     */
    public double length() {
        return Math.sqrt( x * x + y * y + z * z );
    }
    
    /**
     * Calcula o comprimento quadrado do vetor.
     * 
     * @return O comprimento.
     */
    public double lengthSquare() {
        return x * x + y * y + z * z;
    }
    
    /**
     * Calcula o produto escalar do vetor corrente com o vetor passado.
     * 
     * @param v Outro vetor.
     * @return O produto escalar.
     */
    public double dotProduct( Vector3 v ) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Calcula a distância entre o vetor corrente e outro vetor.
     * 
     * @param v Outro vetor.
     * @return A distância.
     */
    public double distance( Vector3 v ) {
        return Math.sqrt( ( x - v.x ) * ( x - v.x ) + 
                          ( y - v.y ) * ( y - v.y ) +
                          ( z - v.z ) * ( z - v.z ));
    }
    
    /**
     * Calcula a distância quadrada entre o vetor corrente e outro vetor.
     * 
     * @param v Outro vetor.
     * @return A distância quadrada.
     */
    public double distanceSquare( Vector3 v ) {
        return ( x - v.x ) * ( x - v.x ) + 
               ( y - v.y ) * ( y - v.y ) + 
               ( z - v.z ) * ( z - v.z );
    }
    
    
    /**
     * Calcula o ângulo entre o vetor corrente e outro vetor, sendo que esse
     * ângulo é calculado a partir do ponto de origem (0, 0).
     * 
     * @param v Outro vetor.
     * @return O ângulo entre os vetores.
     */
    public double angle( Vector3 v ) {
        
        Vector3 cross = new Vector3( y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x );
        double len = Math.sqrt( cross.x * cross.x + cross.y * cross.y + cross.z * cross.z );
        double dot = ( x * v.x + y * v.y + z * v.z );

        return Math.atan2( len, dot );

    }
    
    /**
     * Nega o vetor corrente.
     * 
     * @return Um novo vetor com a negação do vetor corrente.
     */
    public Vector3 negate() {
        return new Vector3( -x, -y, -z );
    }
    
    /**
     * Divide o vetor corrente por outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o resultado da divisão.
     */
    public Vector3 divide( Vector3 v ) {
        return new Vector3( x / v.x, y / v.y, z / v.z );
    }
    
    /**
     * Normaliza o vetor corrent.
     * 
     * @return Um novo vetor normalizado.
     */
    public Vector3 normalize() {

        Vector3 result = new Vector3();
        double length = Math.sqrt( x * x + y * y + z * z );

        if ( length != 0.0 ) {
            double ilength = 1.0 / length;
            result.x = x * ilength;
            result.y = y * ilength;
            result.z = z * ilength;
        }

        return result;

    }
    
    /**
     * Calcula a projeção do vetor corrente no outro vetor.
     * 
     * @param v Outro vetor.
     * @return A projeção.
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
     * Calcula a rejeição do vetor corrente no outro vetor.
     * 
     * @param v Outro vetor.
     * @return A projeção.
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
     * Ortonormaliza dois vetores (serão modificados).
     * Faz com que os vetores sejam normalizados e ortogonais entre eles.
     * implementação da funlção de Gram-Schmidt.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
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
     * Transforma o vetor corrente dada uma matriz.
     * 
     * @param mat A matriz.
     * @return Um novo vetor transformado.
     */
    public Vector3 transform( Matrix mat ) {
        
        Vector3 result = new Vector3();

        result.x = mat.m0*x + mat.m4*y + mat.m8*z + mat.m12;
        result.y = mat.m1*x + mat.m5*y + mat.m9*z + mat.m13;
        result.z = mat.m2*x + mat.m6*y + mat.m10*z + mat.m14;

        return result;
        
    }
    
    
    /*// Transform a vector by quaternion rotation
    RMAPI Vector3 Vector3RotateByQuaternion(Vector3 v, Quaternion q)
    {
    Vector3 result = { 0 };
    result.x = v.x*(q.x*q.x + q.w*q.w - q.y*q.y - q.z*q.z) + v.y*(2*q.x*q.y - 2*q.w*q.z) + v.z*(2*q.x*q.z + 2*q.w*q.y);
    result.y = v.x*(2*q.w*q.z + 2*q.x*q.y) + v.y*(q.w*q.w - q.x*q.x + q.y*q.y - q.z*q.z) + v.z*(-2*q.w*q.x + 2*q.y*q.z);
    result.z = v.x*(-2*q.w*q.y + 2*q.x*q.z) + v.y*(2*q.w*q.x + 2*q.y*q.z)+ v.z*(q.w*q.w - q.x*q.x - q.y*q.y + q.z*q.z);
    return result;
    }
    
    // Rotates a vector around an axis
    RMAPI Vector3 Vector3RotateByAxisAngle(Vector3 v, Vector3 axis, float angle)
    {
    // Using Euler-Rodrigues Formula
    // Ref.: https://en.wikipedia.org/w/index.php?title=Euler%E2%80%93Rodrigues_formula
    Vector3 result = v;
    // Vector3Normalize(axis);
    float length = sqrtf(axis.x*axis.x + axis.y*axis.y + axis.z*axis.z);
    if (length == 0.0f) length = 1.0f;
    float ilength = 1.0f/length;
    axis.x *= ilength;
    axis.y *= ilength;
    axis.z *= ilength;
    angle /= 2.0f;
    float a = sinf(angle);
    float b = axis.x*a;
    float c = axis.y*a;
    float d = axis.z*a;
    a = cosf(angle);
    Vector3 w = { b, c, d };
    // Vector3CrossProduct(w, v)
    Vector3 wv = { w.y*v.z - w.z*v.y, w.z*v.x - w.x*v.z, w.x*v.y - w.y*v.x };
    // Vector3CrossProduct(w, wv)
    Vector3 wwv = { w.y*wv.z - w.z*wv.y, w.z*wv.x - w.x*wv.z, w.x*wv.y - w.y*wv.x };
    // Vector3Scale(wv, 2*a)
    a *= 2;
    wv.x *= a;
    wv.y *= a;
    wv.z *= a;
    // Vector3Scale(wwv, 2)
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
    
    // Move Vector towards target
    RMAPI Vector3 Vector3MoveTowards(Vector3 v, Vector3 target, float maxDistance)
    {
    Vector3 result = { 0 };
    float dx = target.x - v.x;
    float dy = target.y - v.y;
    float dz = target.z - v.z;
    float value = (dx*dx) + (dy*dy) + (dz*dz);
    if ((value == 0) || ((maxDistance >= 0) && (value <= maxDistance*maxDistance))) return target;
    float dist = sqrtf(value);
    result.x = v.x + dx/dist*maxDistance;
    result.y = v.y + dy/dist*maxDistance;
    result.z = v.z + dz/dist*maxDistance;
    return result;
    }
    
    // Calculate linear interpolation between two vectors
    RMAPI Vector3 Vector3Lerp(Vector3 v1, Vector3 v2, float amount)
    {
    Vector3 result = { 0 };
    result.x = v1.x + amount*(v2.x - v1.x);
    result.y = v1.y + amount*(v2.y - v1.y);
    result.z = v1.z + amount*(v2.z - v1.z);
    return result;
    }
    
    // Calculate cubic hermite interpolation between two vectors and their tangents
    // as described in the GLTF 2.0 specification: https://registry.khronos.org/glTF/specs/2.0/glTF-2.0.html#interpolation-cubic
    RMAPI Vector3 Vector3CubicHermite(Vector3 v1, Vector3 tangent1, Vector3 v2, Vector3 tangent2, float amount)
    {
    Vector3 result = { 0 };
    float amountPow2 = amount*amount;
    float amountPow3 = amount*amount*amount;
    result.x = (2*amountPow3 - 3*amountPow2 + 1)*v1.x + (amountPow3 - 2*amountPow2 + amount)*tangent1.x + (-2*amountPow3 + 3*amountPow2)*v2.x + (amountPow3 - amountPow2)*tangent2.x;
    result.y = (2*amountPow3 - 3*amountPow2 + 1)*v1.y + (amountPow3 - 2*amountPow2 + amount)*tangent1.y + (-2*amountPow3 + 3*amountPow2)*v2.y + (amountPow3 - amountPow2)*tangent2.y;
    result.z = (2*amountPow3 - 3*amountPow2 + 1)*v1.z + (amountPow3 - 2*amountPow2 + amount)*tangent1.z + (-2*amountPow3 + 3*amountPow2)*v2.z + (amountPow3 - amountPow2)*tangent2.z;
    return result;
    }
    
    // Calculate reflected vector to normal
    RMAPI Vector3 Vector3Reflect(Vector3 v, Vector3 normal)
    {
    Vector3 result = { 0 };
    // I is the original vector
    // N is the normal of the incident plane
    // R = I - (2*N*(DotProduct[I, N]))
    float dotProduct = (v.x*normal.x + v.y*normal.y + v.z*normal.z);
    result.x = v.x - (2.0f*normal.x)*dotProduct;
    result.y = v.y - (2.0f*normal.y)*dotProduct;
    result.z = v.z - (2.0f*normal.z)*dotProduct;
    return result;
    }
    
    // Get min value for each pair of components
    RMAPI Vector3 Vector3Min(Vector3 v1, Vector3 v2)
    {
    Vector3 result = { 0 };
    result.x = fminf(v1.x, v2.x);
    result.y = fminf(v1.y, v2.y);
    result.z = fminf(v1.z, v2.z);
    return result;
    }
    
    // Get max value for each pair of components
    RMAPI Vector3 Vector3Max(Vector3 v1, Vector3 v2)
    {
    Vector3 result = { 0 };
    result.x = fmaxf(v1.x, v2.x);
    result.y = fmaxf(v1.y, v2.y);
    result.z = fmaxf(v1.z, v2.z);
    return result;
    }
    
    // Compute barycenter coordinates (u, v, w) for point p with respect to triangle (a, b, c)
    // NOTE: Assumes P is on the plane of the triangle
    RMAPI Vector3 Vector3Barycenter(Vector3 p, Vector3 a, Vector3 b, Vector3 c)
    {
    Vector3 result = { 0 };
    Vector3 v0 = { b.x - a.x, b.y - a.y, b.z - a.z };   // Vector3Subtract(b, a)
    Vector3 v1 = { c.x - a.x, c.y - a.y, c.z - a.z };   // Vector3Subtract(c, a)
    Vector3 v2 = { p.x - a.x, p.y - a.y, p.z - a.z };   // Vector3Subtract(p, a)
    float d00 = (v0.x*v0.x + v0.y*v0.y + v0.z*v0.z);    // Vector3DotProduct(v0, v0)
    float d01 = (v0.x*v1.x + v0.y*v1.y + v0.z*v1.z);    // Vector3DotProduct(v0, v1)
    float d11 = (v1.x*v1.x + v1.y*v1.y + v1.z*v1.z);    // Vector3DotProduct(v1, v1)
    float d20 = (v2.x*v0.x + v2.y*v0.y + v2.z*v0.z);    // Vector3DotProduct(v2, v0)
    float d21 = (v2.x*v1.x + v2.y*v1.y + v2.z*v1.z);    // Vector3DotProduct(v2, v1)
    float denom = d00*d11 - d01*d01;
    result.y = (d11*d20 - d01*d21)/denom;
    result.z = (d00*d21 - d01*d20)/denom;
    result.x = 1.0f - (result.z + result.y);
    return result;
    }
    
    // Projects a Vector3 from screen space into object space
    // NOTE: We are avoiding calling other raymath functions despite available
    RMAPI Vector3 Vector3Unproject(Vector3 source, Matrix projection, Matrix view)
    {
    Vector3 result = { 0 };
    // Calculate unprojected matrix (multiply view matrix by projection matrix) and invert it
    Matrix matViewProj = {      // MatrixMultiply(view, projection);
    view.m0*projection.m0 + view.m1*projection.m4 + view.m2*projection.m8 + view.m3*projection.m12,
    view.m0*projection.m1 + view.m1*projection.m5 + view.m2*projection.m9 + view.m3*projection.m13,
    view.m0*projection.m2 + view.m1*projection.m6 + view.m2*projection.m10 + view.m3*projection.m14,
    view.m0*projection.m3 + view.m1*projection.m7 + view.m2*projection.m11 + view.m3*projection.m15,
    view.m4*projection.m0 + view.m5*projection.m4 + view.m6*projection.m8 + view.m7*projection.m12,
    view.m4*projection.m1 + view.m5*projection.m5 + view.m6*projection.m9 + view.m7*projection.m13,
    view.m4*projection.m2 + view.m5*projection.m6 + view.m6*projection.m10 + view.m7*projection.m14,
    view.m4*projection.m3 + view.m5*projection.m7 + view.m6*projection.m11 + view.m7*projection.m15,
    view.m8*projection.m0 + view.m9*projection.m4 + view.m10*projection.m8 + view.m11*projection.m12,
    view.m8*projection.m1 + view.m9*projection.m5 + view.m10*projection.m9 + view.m11*projection.m13,
    view.m8*projection.m2 + view.m9*projection.m6 + view.m10*projection.m10 + view.m11*projection.m14,
    view.m8*projection.m3 + view.m9*projection.m7 + view.m10*projection.m11 + view.m11*projection.m15,
    view.m12*projection.m0 + view.m13*projection.m4 + view.m14*projection.m8 + view.m15*projection.m12,
    view.m12*projection.m1 + view.m13*projection.m5 + view.m14*projection.m9 + view.m15*projection.m13,
    view.m12*projection.m2 + view.m13*projection.m6 + view.m14*projection.m10 + view.m15*projection.m14,
    view.m12*projection.m3 + view.m13*projection.m7 + view.m14*projection.m11 + view.m15*projection.m15 };
    // Calculate inverted matrix -> MatrixInvert(matViewProj);
    // Cache the matrix values (speed optimization)
    float a00 = matViewProj.m0, a01 = matViewProj.m1, a02 = matViewProj.m2, a03 = matViewProj.m3;
    float a10 = matViewProj.m4, a11 = matViewProj.m5, a12 = matViewProj.m6, a13 = matViewProj.m7;
    float a20 = matViewProj.m8, a21 = matViewProj.m9, a22 = matViewProj.m10, a23 = matViewProj.m11;
    float a30 = matViewProj.m12, a31 = matViewProj.m13, a32 = matViewProj.m14, a33 = matViewProj.m15;
    float b00 = a00*a11 - a01*a10;
    float b01 = a00*a12 - a02*a10;
    float b02 = a00*a13 - a03*a10;
    float b03 = a01*a12 - a02*a11;
    float b04 = a01*a13 - a03*a11;
    float b05 = a02*a13 - a03*a12;
    float b06 = a20*a31 - a21*a30;
    float b07 = a20*a32 - a22*a30;
    float b08 = a20*a33 - a23*a30;
    float b09 = a21*a32 - a22*a31;
    float b10 = a21*a33 - a23*a31;
    float b11 = a22*a33 - a23*a32;
    // Calculate the invert determinant (inlined to avoid double-caching)
    float invDet = 1.0f/(b00*b11 - b01*b10 + b02*b09 + b03*b08 - b04*b07 + b05*b06);
    Matrix matViewProjInv = {
    (a11*b11 - a12*b10 + a13*b09)*invDet,
    (-a01*b11 + a02*b10 - a03*b09)*invDet,
    (a31*b05 - a32*b04 + a33*b03)*invDet,
    (-a21*b05 + a22*b04 - a23*b03)*invDet,
    (-a10*b11 + a12*b08 - a13*b07)*invDet,
    (a00*b11 - a02*b08 + a03*b07)*invDet,
    (-a30*b05 + a32*b02 - a33*b01)*invDet,
    (a20*b05 - a22*b02 + a23*b01)*invDet,
    (a10*b10 - a11*b08 + a13*b06)*invDet,
    (-a00*b10 + a01*b08 - a03*b06)*invDet,
    (a30*b04 - a31*b02 + a33*b00)*invDet,
    (-a20*b04 + a21*b02 - a23*b00)*invDet,
    (-a10*b09 + a11*b07 - a12*b06)*invDet,
    (a00*b09 - a01*b07 + a02*b06)*invDet,
    (-a30*b03 + a31*b01 - a32*b00)*invDet,
    (a20*b03 - a21*b01 + a22*b00)*invDet };
    // Create quaternion from source point
    Quaternion quat = { source.x, source.y, source.z, 1.0f };
    // Multiply quat point by unprojecte matrix
    Quaternion qtransformed = {     // QuaternionTransform(quat, matViewProjInv)
    matViewProjInv.m0*quat.x + matViewProjInv.m4*quat.y + matViewProjInv.m8*quat.z + matViewProjInv.m12*quat.w,
    matViewProjInv.m1*quat.x + matViewProjInv.m5*quat.y + matViewProjInv.m9*quat.z + matViewProjInv.m13*quat.w,
    matViewProjInv.m2*quat.x + matViewProjInv.m6*quat.y + matViewProjInv.m10*quat.z + matViewProjInv.m14*quat.w,
    matViewProjInv.m3*quat.x + matViewProjInv.m7*quat.y + matViewProjInv.m11*quat.z + matViewProjInv.m15*quat.w };
    // Normalized world points in vectors
    result.x = qtransformed.x/qtransformed.w;
    result.y = qtransformed.y/qtransformed.w;
    result.z = qtransformed.z/qtransformed.w;
    return result;
    }
    
    // Get Vector3 as float array
    RMAPI float3 Vector3ToFloatV(Vector3 v)
    {
    float3 buffer = { 0 };
    buffer.v[0] = v.x;
    buffer.v[1] = v.y;
    buffer.v[2] = v.z;
    return buffer;
    }
    
    // Invert the given vector
    RMAPI Vector3 Vector3Invert(Vector3 v)
    {
    Vector3 result = { 1.0f/v.x, 1.0f/v.y, 1.0f/v.z };
    return result;
    }
    // Clamp the components of the vector between
    // min and max values specified by the given vectors
    RMAPI Vector3 Vector3Clamp(Vector3 v, Vector3 min, Vector3 max)
    {
    Vector3 result = { 0 };
    result.x = fminf(max.x, fmaxf(min.x, v.x));
    result.y = fminf(max.y, fmaxf(min.y, v.y));
    result.z = fminf(max.z, fmaxf(min.z, v.z));
    return result;
    }
    // Clamp the magnitude of the vector between two values
    RMAPI Vector3 Vector3ClampValue(Vector3 v, float min, float max)
    {
    Vector3 result = v;
    float length = (v.x*v.x) + (v.y*v.y) + (v.z*v.z);
    if (length > 0.0f)
    {
    length = sqrtf(length);
    float scale = 1;    // By default, 1 as the neutral element.
    if (length < min)
    {
    scale = min/length;
    }
    else if (length > max)
    {
    scale = max/length;
    }
    result.x = v.x*scale;
    result.y = v.y*scale;
    result.z = v.z*scale;
    }
    return result;
    }
    
    // Compute the direction of a refracted ray
    // v: normalized direction of the incoming ray
    // n: normalized normal vector of the interface of two optical media
    // r: ratio of the refractive index of the medium from where the ray comes
    //    to the refractive index of the medium on the other side of the surface
    RMAPI Vector3 Vector3Refract(Vector3 v, Vector3 n, float r)
    {
    Vector3 result = { 0 };
    float dot = v.x*n.x + v.y*n.y + v.z*n.z;
    float d = 1.0f - r*r*(1.0f - dot*dot);
    if (d >= 0.0f)
    {
    d = sqrtf(d);
    v.x = r*v.x - (r*dot + d)*n.x;
    v.y = r*v.y - (r*dot + d)*n.y;
    v.z = r*v.z - (r*dot + d)*n.z;
    result = v;
    }
    return result;
    }*/
    
    
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
