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
public class Vector3 implements Cloneable {
    
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
     * Cria um vetor com todos os componentes iguais a 1.0.
     * 
     * @return Um vetor com todos os componentes iguais a 1.0.
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
     * @return Um novo vetor com os componentes subtraídos do valor passado.
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
        return Math.sqrt( ( v.x - x ) * ( v.x - x ) + 
                          ( v.y - y ) * ( v.y - y ) +
                          ( v.z - z ) * ( v.z - z ) );
    }
    
    /**
     * Calcula a distância quadrada entre o vetor corrente e outro vetor.
     * 
     * @param v Outro vetor.
     * @return A distância quadrada.
     */
    public double distanceSquare( Vector3 v ) {
        return ( v.x - x ) * ( v.x - x ) + 
               ( v.y - y ) * ( v.y - y ) +
               ( v.z - z ) * ( v.z - z );
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

        if ( length > 0.0 ) {
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
    
    /**
     * Transforma o vetor corrente por uma rotação por quaternion.
     * 
     * @param q O quaternion.
     * @return Um novo vetor transformado.
     */
    public Vector3 rotateByQuaternion( Quaternion q ) {
        
        Vector3 result = new Vector3();
        
        result.x = x * ( q.x * q.x + q.w * q.w - q.y * q.y - q.z * q.z ) + y * ( 2 * q.x * q.y - 2 * q.w * q.z ) + z * ( 2 * q.x * q.z + 2 * q.w * q.y );
        result.y = x * ( 2 * q.w * q.z + 2 * q.x * q.y ) + y * ( q.w * q.w - q.x * q.x + q.y * q.y - q.z * q.z ) + z * ( -2 * q.w * q.x + 2 * q.y * q.z );
        result.z = x * ( -2 * q.w * q.y + 2 * q.x * q.z ) + y * ( 2 * q.w * q.x + 2 * q.y * q.z )+ z * ( q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z );
        
        return result;
    
    }
    
    /**
     * Rotaciona o vetor corrente em volta de um eixo.
     * 
     * Using Euler-Rodrigues Formula
     * Ref.: https://en.wikipedia.org/w/index.php?title=Euler%E2%80%93Rodrigues_formula
     * 
     * @param axis O eixo.
     * @param angle O ângulo em radianos.
     * @return 
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
     * Cria um novo vetor movido em direção a um alvo.
     * 
     * @param target O alvo.
     * @param maxDistance A distância máxima.
     * @return Um novo vetor movido em direção ao alvo.
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
     * Realiza a interpolação linear entre o vetor corrente (início) e outro vetor (fim).
     * 
     * @param end Vetor final.
     * @param amount Quantidade (0 a 1)
     * @return Um vetor que representa a interpolação linear entre dois vetores.
     */
    public Vector3 lerp( Vector3 end, double amount ) {
        double x = this.x + ( end.x - this.x ) * amount;
        double y = this.y + ( end.y - this.y ) * amount;
        double z = this.z + ( end.z - this.z ) * amount;
        return new Vector3( x, y, z );
    }
    
    /**
     * Calcula a interpolação cúbica Hermitiana do vetor corrente e outro vetor e suas tangentes.
     * 
     * @param tangent1 Primeira tangente
     * @param v O vetor.
     * @param tangent2 Segunda tangente.
     * @param amount A quantidade
     * @return Um novo vetor com a interpolação cúbica Hermitiana.
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
     * Calcula a reflexão do vetor corrente por um vetor normal.
     * 
     * @param normal Vetor normal.
     * @return Um novo vetor refletido.
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
     * Obtem um novo vetor com o mínimo dos componentes.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o mínimo dos componentes.
     */
    public Vector3 min( Vector3 v ) {

        Vector3 result = new Vector3();

        result.x = Math.min( x, v.x );
        result.y = Math.min( y, v.y );
        result.z = Math.min( z, v.z );

        return result;

    }

    /**
     * Obtem um novo vetor com o máximo dos componentes.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o máximo dos componentes.
     */
    public Vector3 max( Vector3 v ) {

        Vector3 result = new Vector3();

        result.x = Math.max( x, v.x );
        result.y = Math.max( y, v.y );
        result.z = Math.max( z, v.z );

        return result;

    }
    
    /**
     * Calcula o baricentro de um ponto p em um triângulo (a, b, c).
     * 
     * @param p O ponto.
     * @param a Primeiro vértice.
     * @param b Segundo vértice.
     * @param c Terceiro vértice.
     * @return O baricentro.
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
     * Projeta o vetor corrente (espaço da tela) para o espaço dos objetos.
     * 
     * @param projection Matrix de projeção.
     * @param view Matrix da visualização.
     * @return Um novo vetor projetado.
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
     * Inverte o vetor corrente.
     * 
     * @return Um novo vetor invertido.
     */
    public Vector3 invert() {
        return new Vector3( 1.0 / x, 1.0 / y, 1.0 / z );
    }
    
    /**
     * Limita o vetor corrente vetor entre dois vetores.
     * 
     * @param min O vetor mínimo.
     * @param max O vetor máximo.
     * @return Um novo vetor limitado entre o vetor mínimo e o vetor máximo.
     */
    public Vector3 clamp( Vector3 min, Vector3 max ) {

        Vector3 result = new Vector3();

        result.x = Math.min( max.x, Math.max( min.x, x ) );
        result.y = Math.min( max.y, Math.max( min.y, y ) );
        result.z = Math.min( max.z, Math.max( min.z, z ) );

        return result;

    }
    
    /**
     * Limita a magnitude do vetor corrente entre mínimo e máximo.
     * 
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return Um novo vetor com a magnitude limitada.
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
     * Computa a direção de um raio refratado.
     * 
     * @param v A direção normalizada do raio de chegada.
     * @param n O vetor normal normalizado da interface das dois meios óticos.
     * @param r A razão do índice de refração do meio de onde o raio chega com o
     * índice de refração do outro meio da superfície.
     * @return A direção do raio refratado.
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
