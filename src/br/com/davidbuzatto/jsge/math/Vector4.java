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
 * Classe para representação de um vetor de quatro dimensões.
 * 
 * Pode ser usada para retornar valores com quatro componentes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector4 implements Cloneable {
    
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
     * Coordenada w.
     */
    public double w;

    /**
     * Cria um novo vetor de quatro dimensões com valores padrão.
     */
    public Vector4() {
    }

    /**
     * Cria um novo vetor de quatro dimensões.
     * 
     * @param x coordenada x.
     * @param y coordenada y.
     * @param z coordenada z.
     * @param w coordenada w.
     */
    public Vector4( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Cria um vetor com todos os componentes iguais a 1.0.
     * 
     * @return Um vetor com todos os componentes iguais a 1.0.
     */
    public static Vector4 one() {
        return new Vector4( 1.0, 1.0, 1.0, 1.0 );
    }
    
    /**
     * Soma o vetor corrente com outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor resultado da soma.
     */
    public Vector4 add( Vector4 v ) {
        return new Vector4( x + v.x, y + v.y, z + v.z, w + v.w );
    }
    
    /**
     * Soma um valor ao vetor corrente.
     * 
     * @param value O valor a somar.
     * @return Um novo vetor com os componentes somados ao valor passado.
     */
    public Vector4 addValue( double value ) {
        return new Vector4( x + value, y + value, z + value, w + value );
    }
    
    /**
     * Subtrai um vetor do vetor corrente.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor resultado da subtração.
     */
    public Vector4 subtract( Vector4 v ) {
        return new Vector4( x - v.x, y - v.y, z - v.z, w - v.w );
    }
    
    /**
     * Subtrai um valor do vetor corrente
     * 
     * @param value O valor a subtrair.
     * @return Um novo vetor com os componentes subtraídos do valor passado.
     */
    public Vector4 subtractValue( double value ) {
        return new Vector4( x - value, y - value, z - value, w - value );
    }
    
    /**
     * Calcula o comprimento do vetor.
     * 
     * @return O comprimento.
     */
    public double length() {
        return Math.sqrt( x * x + y * y + z * z + w * w );
    }
    
    /**
     * Calcula o comprimento quadrado do vetor.
     * 
     * @return O comprimento.
     */
    public double lengthSquare() {
        return x * x + y * y + z * z + w * w;
    }
    
    /**
     * Calcula o produto escalar do vetor corrente com o vetor passado.
     * 
     * @param v Outro vetor.
     * @return O produto escalar.
     */
    public double dotProduct( Vector4 v ) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    /**
     * Calcula a distância entre o vetor corrente e outro vetor.
     * 
     * @param v Outro vetor.
     * @return A distância.
     */
    public double distance( Vector4 v ) {
        return Math.sqrt( ( v.x - x ) * ( v.x - x ) + 
                          ( v.y - y ) * ( v.y - y ) +
                          ( v.z - z ) * ( v.z - z ) +
                          ( v.w - w ) * ( v.w - w ) );
    }
    
    /**
     * Calcula a distância quadrada entre o vetor corrente e outro vetor.
     * 
     * @param v Outro vetor.
     * @return A distância quadrada.
     */
    public double distanceSquare( Vector4 v ) {
        return ( v.x - x ) * ( v.x - x ) + 
               ( v.y - y ) * ( v.y - y ) +
               ( v.z - z ) * ( v.z - z ) +
               ( v.w - w ) * ( v.w - w );
    }
    
    /**
     * Escalona o vetor corrente, análogo à multiplicação por escalar.
     * 
     * @param scale A escala.
     * @return Um novo vetor escalonado.
     */
    public Vector4 scale( double scale ) {
        return new Vector4( x * scale, y * scale, z * scale, w * scale );
    }
    
    /**
     * Multiplica o vetor corrente por outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o resultado da multiplicação.
     */
    public Vector4 multiply( Vector4 v ) {
        return new Vector4( x * v.x, y * v.y, z * v.z, w * v.w );
    }
    
    /**
     * Nega o vetor corrente.
     * 
     * @return Um novo vetor com a negação do vetor corrente.
     */
    public Vector4 negate() {
        return new Vector4( -x, -y, -z, -w );
    }
    
    /**
     * Divide o vetor corrente por outro vetor.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o resultado da divisão.
     */
    public Vector4 divide( Vector4 v ) {
        return new Vector4( x / v.x, y / v.y, z / v.z, w / v.w );
    }
    
    /**
     * Normaliza o vetor corrent.
     * 
     * @return Um novo vetor normalizado.
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
     * Obtem um novo vetor com o mínimo dos componentes.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o mínimo dos componentes.
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
     * Obtem um novo vetor com o máximo dos componentes.
     * 
     * @param v Outro vetor.
     * @return Um novo vetor com o máximo dos componentes.
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
     * Realiza a interpolação linear entre o vetor corrente (início) e outro vetor (fim).
     * 
     * @param end Vetor final.
     * @param amount Quantidade (0 a 1)
     * @return Um vetor que representa a interpolação linear entre dois vetores.
     */
    public Vector4 lerp( Vector4 end, double amount ) {
        double x = this.x + ( end.x - this.x ) * amount;
        double y = this.y + ( end.y - this.y ) * amount;
        double z = this.z + ( end.z - this.z ) * amount;
        double w = this.w + ( end.w - this.w ) * amount;
        return new Vector4( x, y, z, w );
    }
    
    /**
     * Cria um novo vetor movido em direção a um alvo.
     * 
     * @param target O alvo.
     * @param maxDistance A distância máxima.
     * @return Um novo vetor movido em direção ao alvo.
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
     * Inverte o vetor corrente.
     * 
     * @return Um novo vetor invertido.
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
