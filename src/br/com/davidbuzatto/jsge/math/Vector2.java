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
 * Classe para representação de um vetor de duas dimensões.
 * Também é usada para representar pontos 2D em várias partes da API da Engine.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector2 implements Cloneable {

    /**
     * Coordenada x.
     */
    public double x;
    
    /**
     * Coordenada y.
     */
    public double y;

    /**
     * Cria um novo vetor de duas dimensões com valores padrão.
     */
    public Vector2() {
    }
    
    /**
     * Cria um novo vetor de duas dimensões.
     * 
     * @param x Coordenada x.
     * @param y Coordenada y.
     */
    public Vector2( double x, double y ) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Cria um vetor com ambos os componentes iguais a 1.0.
     * 
     * @return Um vetor com ambos os componentes iguais a 1.0.
     */
    public static Vector2 one() {
        return new Vector2( 1.0, 1.0 );
    }

    /**
     * Soma o vetor corrente com outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor resultado da soma.
     */
    public Vector2 add( Vector2 v ) {
        return new Vector2( x + v.x, y + v.y );
    }

    /**
     * Soma um valor ao vetor corrente.
     * 
     * @param value O valor a somar.
     * @return Um novo vetor com os componentes somados ao valor passado.
     */
    public Vector2 addValue( double value ) {
        return new Vector2( x + value, y + value );
    }

    /**
     * Subtrai um vetor do vetor corrente.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor resultado da subtração.
     */
    public Vector2 subtract( Vector2 v ) {
        return new Vector2( x - v.x, y - v.y );
    }

    /**
     * Subtrai um valor do vetor corrente
     * 
     * @param value O valor a subtrair.
     * @return Um novo vetor com os componentes subtraídos do valor passado.
     */
    public Vector2 subtractValue( double value ) {
        return new Vector2( x - value, y - value );
    }
    
    /**
     * Escalona o vetor corrente, análogo à multiplicação por escalar.
     * 
     * @param scale A escala.
     * @return Um novo vetor escalonado.
     */
    public Vector2 scale( double scale ) {
        return new Vector2( x * scale, y * scale );
    }

    /**
     * Multiplica o vetor corrente por outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o resultado da multiplicação.
     */
    public Vector2 multiply( Vector2 v ) {
        return new Vector2( x * v.x, y * v.y );
    }
    
    /**
     * Calcula o comprimento do vetor.
     * 
     * @return O comprimento.
     */
    public double length() {
        return Math.sqrt( x * x + y * y );
    }
    
    /**
     * Calcula o comprimento quadrado do vetor.
     * 
     * @return O comprimento.
     */
    public double lengthSquare() {
        return x * x + y * y;
    }
    
    /**
     * Calcula o produto escalar do vetor corrente com o vetor passado.
     * 
     * @param v Outro vetor.
     * @return O produto escalar.
     */
    public double dotProduct( Vector2 v ) {
        return x * v.x + y * v.y;
    }

    /**
     * Calcula a distância entre o vetor corrente e outro vetor.
     * 
     * @param v Outro vetor.
     * @return A distância.
     */
    public double distance( Vector2 v ) {
        return Math.sqrt( ( v.x - x ) * ( v.x - x ) + 
                          ( v.y - y ) * ( v.y - y ) );
    }
    
    /**
     * Calcula a distância quadrada entre o vetor corrente e outro vetor.
     * 
     * @param v Outro vetor.
     * @return A distância quadrada.
     */
    public double distanceSquare( Vector2 v ) {
        return ( v.x - x ) * ( v.x - x ) + 
               ( v.y - y ) * ( v.y - y );
    }

    /**
     * Calcula o ângulo entre o vetor corrente e outro vetor, sendo que esse
     * ângulo é calculado a partir do ponto de origem (0, 0).
     * 
     * @param v Outro vetor.
     * @return O ângulo entre os vetores.
     */
    public double angle( Vector2 v ) {

        double dot = x * v.x + y * v.y;
        double det = x * v.y - y * v.x;

        return Math.atan2( det, dot );

    }

    /**
     * Nega o vetor corrente.
     * 
     * @return Um novo vetor com a negação do vetor corrente.
     */
    public Vector2 negate() {
        return new Vector2( -x, -y );
    }

    /**
     * Divide o vetor corrente por outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o resultado da divisão.
     */
    public Vector2 divide( Vector2 v ) {
        return new Vector2( x / v.x, y / v.y );
    }

    /**
     * Normaliza o vetor corrent.
     * 
     * @return Um novo vetor normalizado.
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
     * Transforma o vetor corrente dada uma matriz.
     * 
     * @param mat A matriz.
     * @return Um novo vetor transformado.
     */
    public Vector2 transform( Matrix mat ) {
        
        Vector2 result = new Vector2();

        double z = 0;

        result.x = mat.m0*x + mat.m4*y + mat.m8*z + mat.m12;
        result.y = mat.m1*x + mat.m5*y + mat.m9*z + mat.m13;

        return result;
        
    }

    /**
     * Realiza a interpolação linear entre o vetor corrente (início) e outro vetor (fim).
     * 
     * @param end Vetor final.
     * @param amount Quantidade (0 a 1)
     * @return Um vetor que representa a interpolação linear entre dois vetores.
     */
    public Vector2 lerp( Vector2 end, double amount ) {
        double x = this.x + ( end.x - this.x ) * amount;
        double y = this.y + ( end.y - this.y ) * amount;
        return new Vector2( x, y );
    }

    /**
     * Calcula a reflexão do vetor corrente por um vetor normal.
     * 
     * @param normal Vetor normal.
     * @return Um novo vetor refletido.
     */
    public Vector2 reflect( Vector2 normal ) {

        Vector2 result = new Vector2();

        double dotProduct = ( x * normal.x + y * normal.y ); // produto escalar

        result.x = x - ( 2.0 * normal.x ) * dotProduct;
        result.y = y - ( 2.0 * normal.y ) * dotProduct;

        return result;

    }

    /**
     * Obtem um novo vetor com o mínimo dos componentes.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o mínimo dos componentes.
     */
    public Vector2 min( Vector2 v ) {

        Vector2 result = new Vector2();

        result.x = Math.min( x, v.x );
        result.y = Math.min( y, v.y );

        return result;

    }

    /**
     * Obtem um novo vetor com o máximo dos componentes.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o máximo dos componentes.
     */
    public Vector2 max( Vector2 v ) {

        Vector2 result = new Vector2();

        result.x = Math.max( x, v.x );
        result.y = Math.max( y, v.y );

        return result;

    }

    /**
     * Rotaciona o vetor corrente usando um ângulo (em radianos).
     * 
     * @param angle O ângulo.
     * @return Um novo vetor rotacionado.
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
     * Cria um novo vetor movido em direção a um alvo.
     * 
     * @param target O alvo.
     * @param maxDistance A distância máxima.
     * @return Um novo vetor movido em direção ao alvo.
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
     * Inverte o vetor corrente.
     * 
     * @return Um novo vetor invertido.
     */
    public Vector2 invert() {
        return new Vector2( 1.0 / x, 1.0 / y );
    }

    /**
     * Limita o vetor corrente vetor entre dois vetores.
     * 
     * @param min O vetor mínimo.
     * @param max O vetor máximo.
     * @return Um novo vetor limitado entre o vetor mínimo e o vetor máximo.
     */
    public Vector2 clamp( Vector2 min, Vector2 max ) {

        Vector2 result = new Vector2();

        result.x = Math.min( max.x, Math.max( min.x, x ) );
        result.y = Math.min( max.y, Math.max( min.y, y ) );

        return result;

    }

    /**
     * Limita a magnitude do vetor corrente entre mínimo e máximo.
     * 
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return Um novo vetor com a magnitude limitada.
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
     * Computa a direção de um raio refratado.
     * 
     * @param v A direção normalizada do raio de chegada.
     * @param n O vetor normal normalizado da interface das dois meios óticos.
     * @param r A razão do índice de refração do meio de onde o raio chega com o
     * índice de refração do outro meio da superfície.
     * @return A direção do raio refratado.
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
