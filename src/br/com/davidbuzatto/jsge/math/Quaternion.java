/*
 * Copyright (C) 2025 Prof. Dr. David Buzatto
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
 * Classe para representação de um quatérnio.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Quaternion implements Cloneable, Serializable {
    
    private static final long serialVersionUID = 1L;
    
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
     * Cria um novo quatérnio com valores padrão.
     */
    public Quaternion() {
    }

    /**
     * Cria um novo quatérnio.
     * 
     * @param x coordenada x.
     * @param y coordenada y.
     * @param z coordenada z.
     * @param w coordenada w.
     */
    public Quaternion( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Soma o quatérnio corrente com outro quatérnio.
     * 
     * @param q Outro quatérnio.
     * @return Um novo quatérnio resultado da soma.
     */
    public Quaternion add( Quaternion q ) {
        return new Quaternion( x + q.x, y + q.y, z + q.z, w + q.w );
    }
    
    /**
     * Soma um valor ao quatérnio corrente.
     * 
     * @param value O valor a somar.
     * @return Um novo quatérnio com os componentes somados ao valor passado.
     */
    public Quaternion addValue( double value ) {
        return new Quaternion( x + value, y + value, z + value, w + value );
    }
    
    /**
     * Subtrai um quatérnio do quatérnio corrente.
     * 
     * @param q Outro quatérnio.
     * @return Um novo quatérnio resultado da subtração.
     */
    public Quaternion subtract( Quaternion q ) {
        return new Quaternion( x - q.x, y - q.y, z - q.z, w - q.w );
    }
    
    /**
     * Subtrai um valor do quatérnio corrente
     * 
     * @param value O valor a subtrair.
     * @return Um novo quatérnio com os componentes subtraídos do valor passado.
     */
    public Quaternion subtractValue( double value ) {
        return new Quaternion( x - value, y - value, z - value, w - value );
    }
    
    /**
     * Calcula o comprimento do quatérnio.
     * 
     * @return O comprimento.
     */
    public double length() {
        return Math.sqrt( x * x + y * y + z * z + w * w );
    }
    
    /**
     * Normaliza o vetor quatérnio.
     * 
     * @return Um novo quatérnio normalizado.
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
     * Inverte o quatérnio corrente.
     * 
     * @return Um novo quatérnio invertido.
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
     * Multiplica o quatérnio corrente por outro quatérnio.
     * 
     * @param q Outro quatérnio.
     * @return Um novo quatérnio com o resultado da multiplicação.
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
     * Escalona o quatérnio corrente, análogo à multiplicação por escalar.
     * 
     * @param scale A escala.
     * @return Um novo quatérnio escalonado.
     */
    public Quaternion scale( double scale ) {
        return new Quaternion( x * scale, y * scale, z * scale, w * scale );
    }
    
    /**
     * Divide o quatérnio corrente por outro quatérnio.
     * 
     * @param q Outro quatérnio.
     * @return Um novo vetor com o resultado da divisão.
     */
    public Quaternion divide( Quaternion q ) {
        return new Quaternion( x / q.x, y / q.y, z / q.z, w / q.w );
    }
    
    /**
     * Realiza a interpolação linear entre o quatérnio corrente (início) e outro quatérnio (fim).
     * 
     * @param end Quatérnio final.
     * @param amount Quantidade (0 a 1)
     * @return Um quatérnio que representa a interpolação linear entre dois quatérnios.
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
     * Realiza a interpolação linear normalizada entre o quatérnio corrente (início) e outro quatérnio (fim).
     * 
     * @param end Quatérnio final.
     * @param amount Quantidade (0 a 1)
     * @return Um quatérnio que representa a interpolação linear normalizada entre dois quatérnios.
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
     * Realiza a interpolação linear esférica entre o quatérnio corrente (início) e outro quatérnio (fim).
     * 
     * @param end Quatérnio final.
     * @param amount Quantidade (0 a 1)
     * @return Um quatérnio que representa a interpolação linear esférica entre dois quatérnios.
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
     * Calcula a interpolação cúbica Hermitiana do quatérnio corrente e de outro quatérnio e suas tangentes.
     * 
     * @param outTangent Primeira tangente
     * @param q O quatérnio.
     * @param inTangent Segunda tangente.
     * @param amount A quantidade
     * @return Um novo vetor com a interpolação cúbica Hermitiana.
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
     * Obtém a matrix do quatérnio corrente.
     * 
     * @return Uma nova matriz do quatérnio corrente.
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
     * Obtém o ângulo de rotação e o eixo do quatérnio corrente.
     * @param outAxis Vetor que receberá os dados do eixo.
     * @return O ângulo.
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
     * Obtém os ângulos de Euler em radianos (rolagem (roll), passo (pitch) e guinada (yaw))
     * equivalentes ao quatérnio corrente.
     * @return Um vetor contendo os ângulos de Euler. x = roll (rotação em x), y = pitch (rotação em y) e z = yaw (rotação em z).
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
     * Transforma o quatérnio corrente dada uma matriz.
     * 
     * @param mat A matriz.
     * @return Um novo quatérnio transformado.
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
     * Cria uma quatérnio identidade.
     * 
     * @return Uma novo quatérnio identidade.
     */
    public static Quaternion identity() {
        return new Quaternion( 0.0, 0.0, 0.0, 1.0 );
    }
    
    /**
     * Cria um quatérnio baseado na rotação de um vetor para outro.
     * 
     * @param from Um vetor.
     * @param to Outro vetor.
     * @return Um novo quatérnio.
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
     * Obtém um quatérnio dada uma matriz de rotação.
     * 
     * @param mat A matriz.
     * @return Um novo quatérnio.
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
     * Obtém um quatérnio de rotação dado um ângulo e um eixo.
     * 
     * @param axis O eixo.
     * @param angle O ângulo em radianos.
     * @return Um novo quatérnio de rotação.
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
     * Obtém o quatérnio equivalente aos ângulos de Euler.
     * A ordem de rotação é ZYX.
     * 
     * @param pitch Ângulo de passo.
     * @param yaw Ângulo de guinada.
     * @param roll Ângulo de rolagem.
     * @return Um novo quatérnio equivalente aos ângulos de Euler.
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
