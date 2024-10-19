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
package br.com.davidbuzatto.jsge.utils;

import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Point;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Vector2;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.Random;

/**
 * Classe com métodos estáticos utilitários relacionados à matemática.
 * 
 * Várias implementações são baseadas na raylib e em seus módulos
 * (www.raylib.com). Alguns métodos não são expostos pois o objetivo
 * é que sejam utilizados apenas como suporte à métodos para o mundo 2D.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MathUtils {
    
    private static final Random random = new Random();

    /**
     * Realiza a interpolação linear entre dois valores.
     * 
     * @param start valor inicial.
     * @param end valor final.
     * @param amount quantidade (0 a 1)
     * @return A interpolação linear entre dois valores.
     */
    public static double lerp( double start, double end, double amount ) {
        return start + amount * ( end - start );
    }

    /**
     * Limita um valor entre dois valores.
     * 
     * @param value O valor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return O valor fixado entre mínimo e máximo.
     */
    public static int clamp( int value, int min, int max ) {
        int result = value < min ? min : value;
        if ( result > max ) result = max;
        return result;
    }    

    /**
     * Limita um valor entre dois valores.
     * 
     * @param value O valor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return O valor fixado entre mínimo e máximo.
     */
    public static double clamp( double value, double min, double max ) {
        double result = value < min ? min : value;
        if ( result > max ) result = max;
        return result;
    }    

    /**
     * Normaliza o valor dentro do intervalo fornecido.
     * 
     * @param value O valor.
     * @param start O valor inicial.
     * @param end O valor final.
     * @return O valor normalizado entre o valor inicial e final.
     */
    public static double normalize( double value, double start, double end ) {
        return ( value - start ) / ( end - start );
    }

    /**
     * Remapeia um valor entre um intervalo de entrada e um intervalo de saída.
     * 
     * @param value O valor.
     * @param inputStart O valor inicial do intervalo de entrada.
     * @param inputEnd O valor final do intervalo de entrada.
     * @param outputStart O valor inicial do intervalo de saída.
     * @param outputEnd O valor final do intervalo de saída.
     * @return O valor remapeado.
     */
    public static double remap( double value, double inputStart, double inputEnd, double outputStart, double outputEnd ) {
        return ( value - inputStart ) / ( inputEnd - inputStart ) * ( outputEnd - outputStart ) + outputStart;
    }

    /**
     * Coloca um valor entre um valor mínimo e máximo.
     * 
     * @param value O valor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return O valor ajustado.
     */
    public static double wrap( double value, double min, double max ) {
        return value - ( max - min ) * Math.floor( ( value - min ) / ( max - min ) );
    }

    /**
     * Gera um número pseudo-aleatório entre min (inclusive) e max (inclusive).
     * 
     * @param min Início do intervalo.
     * @param max Fim do intervalo.
     * @return Um número pseudo-aleatório entre min (inclusive) e max (inclusive).
     */
    public static int getRandomValue( int min, int max ) {
        return random.nextInt( min, max + 1 );
    }

    /**
     * Configura a semente aleatória do gerador de números aleatórios.
     * 
     * @param seed A semente a ser utilizada.
     */
    public static void setRandomSeed( long seed ) {
        random.setSeed( seed );
    }

    /**
     * Realiza a interpolação linear entre dois pontos.
     * 
     * @param start ponto inicial.
     * @param end ponto final.
     * @param amount quantidade (0 a 1)
     * @return Um ponto que representa a interpolação linear entre dois pontos.
     */
    public static Point lerp( Point start, Point end, double amount ) {
        double x = start.x + ( end.x - start.x ) * amount;
        double y = start.y + ( end.y - start.y ) * amount;
        return new Point( x, y );
    }

    /**
     * Cria um vetor 2D com ambos os componentes iguais a 1.0.
     * 
     * @return Um vetor 2D com ambos os componentes iguais a 1.0.
     */
    public static Vector2 vector2One() {
        return new Vector2( 1.0, 1.0 );
    }

    /**
     * Soma dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a soma dos vetores passados.
     */
    public static Vector2 add( Vector2 v1, Vector2 v2 ) {
        return new Vector2( v1.x + v2.x, v1.y + v2.y );
    }

    /**
     * Soma um valor a um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a somar.
     * @return Um novo vetor 2D com os componentes somados ao valor passado.
     */
    public static Vector2 addValue( Vector2 v, double value ) {
        return new Vector2( v.x + value, v.y + value );
    }

    /**
     * Subtrai dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a subtração dos vetores passados.
     */
    public static Vector2 subtract( Vector2 v1, Vector2 v2 ) {
        return new Vector2( v1.x - v2.x, v1.y - v2.y );
    }

    /**
     * Subtrai um valor de um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a subtrair.
     * @return Um novo vetor 2D com os componentes subtraídos do valor passado.
     */
    public static Vector2 subtractValue( Vector2 v, double value ) {
        return new Vector2( v.x - value, v.y - value );
    }

    /**
     * Calcula o comprimento de um vetor 2D.
     * 
     * @param v Um vetor.
     * @return O comprimento do vetor passado.
     */
    public static double length( Vector2 v ) {
        return Math.sqrt( v.x * v.x + v.y * v.y );
    }

    /**
     * Calcula o produto escalar entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return O produto escalar entre os vetores passados.
     */
    public static double dotProduct( Vector2 v1, Vector2 v2 ) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Calcula a distância entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return A distância entre os vetores passados.
     */
    public static double distance( Vector2 v1, Vector2 v2 ) {
        return Math.sqrt( ( v1.x - v2.x ) * ( v1.x - v2.x ) + ( v1.y - v2.y ) * ( v1.y - v2.y ) );
    }

    /**
     * Calcula o ângulo entre dois vetores 2D, sendo que esse ângulo
     * é calculado a partir do ponto de origem (0, 0).
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return O ângulo entre os dois vetores passados.
     */
    public static double angle( Vector2 v1, Vector2 v2 ) {

        double dot = v1.x * v2.x + v1.y * v2.y;
        double det = v1.x * v2.y - v1.y * v2.x;

        return Math.atan2( det, dot );

    }

    /**
     * Escalona um vetor 2D, análogo à multiplicação por escalar.
     * 
     * @param v O vetor.
     * @param scale A escala.
     * @return Um novo vetor 2D escalonado.
     */
    public static Vector2 scale( Vector2 v, double scale ) {
        return new Vector2( v.x * scale, v.y * scale );
    }

    /**
     * Multiplica dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da multiplicação dos vetores passados.
     */
    public static Vector2 multiply( Vector2 v1, Vector2 v2 ) {
        return new Vector2( v1.x * v2.x, v1.y * v2.y );
    }

    /**
     * Nega um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor com a negação do vetor passado.
     */
    public static Vector2 negate( Vector2 v ) {
        return new Vector2( -v.x, -v.y );
    }

    /**
     * Divide dois vetores.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da divisão dos vetores passados.
     */
    public static Vector2 divide( Vector2 v1, Vector2 v2 ) {
        return new Vector2( v1.x / v2.x, v1.y / v2.y );
    }

    /**
     * Normaliza um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor 2D normalizado.
     */
    public static Vector2 normalize( Vector2 v ) {

        Vector2 result = new Vector2();
        double length = Math.sqrt( v.x * v.x + v.y * v.y );

        if ( length > 0 ) {
            double ilength = 1.0 / length;
            result.x = v.x * ilength;
            result.y = v.y * ilength;
        }

        return result;

    }

    /**
     * Realiza a interpolação linear entre dois vetores.
     * 
     * @param start vetor inicial.
     * @param end vetor final.
     * @param amount quantidade (0 a 1)
     * @return Um vetor que representa a interpolação linear entre dois vetores.
     */
    public static Vector2 lerp( Vector2 start, Vector2 end, double amount ) {
        double x = start.x + ( end.x - start.x ) * amount;
        double y = start.y + ( end.y - start.y ) * amount;
        return new Vector2( x, y );
    }

    /**
     * Calcula um vetor 2D refletido pela normal.
     * 
     * @param v Um vetor.
     * @param normal Vetor normal.
     * @return Um novo vetor refletido.
     */
    public static Vector2 reflect( Vector2 v, Vector2 normal ) {

        Vector2 result = new Vector2();

        double dotProduct = ( v.x * normal.x + v.y * normal.y ); // produto escalar

        result.x = v.x - ( 2.0 * normal.x ) * dotProduct;
        result.y = v.y - ( 2.0 * normal.y ) * dotProduct;

        return result;

    }

    /**
     * Obtem um novo vetor 2D com o mínimo dos componentes.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o mínimo dos componentes dos vetores passados.
     */
    public static Vector2 min( Vector2 v1, Vector2 v2 ) {

        Vector2 result = new Vector2();

        result.x = Math.min( v1.x, v2.x );
        result.y = Math.min( v1.y, v2.y );

        return result;

    }

    /**
     * Obtem um novo vetor 2D com o máximo dos componentes.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o máximo dos componentes dos vetores passados.
     */
    public static Vector2 max( Vector2 v1, Vector2 v2 ) {

        Vector2 result = new Vector2();

        result.x = Math.max( v1.x, v2.x );
        result.y = Math.max( v1.y, v2.y );

        return result;

    }

    /**
     * Rotaciona um vetor 2D usando um ângulo (em radianos).
     * 
     * @param v Um vetor.
     * @param angle O ângulo.
     * @return Um novo vetor rotacionado.
     */
    public static Vector2 rotate( Vector2 v, double angle ) {

        Vector2 result = new Vector2();

        double cos = Math.cos( angle );
        double sin = Math.sin( angle );

        result.x = v.x * cos - v.y * sin;
        result.y = v.x * sin + v.y * cos;

        return result;

    }

    /**
     * Cria um novo vetor movido em direção a um alvo.
     * 
     * @param v Um vetor.
     * @param target O alvo.
     * @param maxDistance A distância máxima.
     * @return Um novo vetor movido em direção ao alvo.
     */
    public static Vector2 moveTowards( Vector2 v, Vector2 target, double maxDistance ) {

        Vector2 result = new Vector2();

        double dx = target.x - v.x;
        double dy = target.y - v.y;
        double value = dx * dx + dy * dy;

        if ( ( value == 0 ) || ( ( maxDistance >= 0 ) && ( value <= maxDistance * maxDistance ) ) ) {
            return target;
        }

        double dist = Math.sqrt( value );

        result.x = v.x + dx / dist * maxDistance;
        result.y = v.y + dy / dist * maxDistance;

        return result;

    }

    /**
     * Cria um vetor 2D invertido.
     * 
     * @param v Um vetor.
     * @return Um novo vetor invertido.
     */
    public static Vector2 invert( Vector2 v ) {
        return new Vector2( 1.0 / v.x, 1.0 / v.y );
    }

    /**
     * Limita um vetor entre dois vetores 2D.
     * 
     * @param v O vetor.
     * @param min O vetor mínimo.
     * @param max O vetor máximo.
     * @return Um novo vetor fixado entre o vetor mínimo e o vetor máximo.
     */
    public static Vector2 clamp( Vector2 v, Vector2 min, Vector2 max ) {

        Vector2 result = new Vector2();

        result.x = Math.min( max.x, Math.max( min.x, v.x ) );
        result.y = Math.min( max.y, Math.max( min.y, v.y ) );

        return result;

    }

    /**
     * Limita a magnitude de um vetor entre mínimo e máximo.
     * 
     * @param v O vetor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return o valor fixado da magnitude entre os valores mínimo e máximo.
     */
    public static Vector2 clampValue( Vector2 v, double min, double max ) {

        Vector2 result = new Vector2( v.x, v.y );

        double length = v.x * v.x + v.y * v.y;

        if ( length > 0.0 ) {

            length = Math.sqrt( length );

            double scale = 1;    // By default, 1 as the neutral element.
            if ( length < min ) {
                scale = min / length;
            } else if ( length > max ) {
                scale = max / length;
            }

            result.x = v.x * scale;
            result.y = v.y * scale;

        }

        return result;

    }

    /**
     * Cria um ponto 2D usando um vetor 2D.
     * 
     * @param v Um vetor 2D.
     * @return Um ponto 2D.
     */
    public static Point vector2ToPoint( Vector2 v ) {
        return new Point( v.x, v.y );
    }

    /**
     * Cria um vetor 2D usando um ponto 2D.
     * 
     * @param p Um ponto 2D.
     * @return Um vetor 2D.
     */
    public static Vector2 pointToVector2( Point p ) {
        return new Vector2( p.x, p.y );
    }
    
    /**
     * Converte uma coordenada da tela para uma coordenada do mundo 2D de 
     * acordo com o câmera.
     * 
     * @param x A coordenada x da posição da tela.
     * @param y A coordenada y da posição da tela.
     * @param camera A câmera a ser utilizada.
     * @return O ponto correspondente do mundo 2D.
     */
    public static Point getScreenToWorld2D( double x, double y, Camera2D camera ) {
        
        Matrix invMatCamera = MathUtils.matrixInvert( MathUtils.getCameraMatrix2D( camera ) );
        Vector3 transform = MathUtils.vector3Transform( new Vector3( x, y, 0 ), invMatCamera );

        return new Point( transform.x, transform.y );
        
    }
    
    /**
     * Converte uma coordenada da tela para uma coordenada do mundo 2D de 
     * acordo com o câmera.
     * 
     * @param point A posição da tela.
     * @param camera A câmera a ser utilizada.
     * @return O ponto correspondente do mundo 2D.
     */
    public static Point getScreenToWorld2D( Point point, Camera2D camera ) {
        return getScreenToWorld2D( point.x, point.y, camera );
        
    }
    
    /**
     * Converte uma coordenada da tela para uma coordenada do mundo 2D de 
     * acordo com o câmera.
     * 
     * @param vector A posição da tela.
     * @param camera A câmera a ser utilizada.
     * @return O ponto correspondente do mundo 2D.
     */
    public static Point getScreenToWorld2D( Vector2 vector, Camera2D camera ) {
        return getScreenToWorld2D( vector.x, vector.y, camera );
    }
    
    /**
     * Converte uma coordenada do mundo 2D para uma coordenada da tela de 
     * acordo com o câmera.
     * 
     * @param x A coordenada x da posição do mundo 2D.
     * @param y A coordenada y da posição do mundo 2D.
     * @param camera A câmera a ser utilizada.
     * @return O ponto correspondente da tela.
     */
    public static Point getWorldToScreen2D( double x, double y, Camera2D camera ) {
        
        Matrix matCamera = MathUtils.getCameraMatrix2D( camera );
        Vector3 transform = MathUtils.vector3Transform( new Vector3( x, y, 0 ), matCamera );

        return new Point( transform.x, transform.y );
        
    }
    
    /**
     * Converte uma coordenada do mundo 2D para uma coordenada da tela de 
     * acordo com o câmera.
     * 
     * @param point A posição do mundo 2D.
     * @param camera A câmera a ser utilizada.
     * @return O ponto correspondente da tela.
     */
    public static Point getWorldToScreen2D( Point point, Camera2D camera ) {
        return getWorldToScreen2D( point.x, point.y, camera );
        
    }
    
    /**
     * Converte uma coordenada do mundo 2D para uma coordenada da tela de 
     * acordo com o câmera.
     * 
     * @param vector A posição do mundo 2D.
     * @param camera A câmera a ser utilizada.
     * @return O ponto correspondente da tela.
     */
    public static Point getWorldToScreen2D( Vector2 vector, Camera2D camera ) {
        return getWorldToScreen2D( vector.x, vector.y, camera );
    }
    
    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( double p1x, double p1y, double p2x, double p2y, double amount ) {

        double x = p1x * ( 1.0 - amount ) + p2x * amount;
        double y = p1y * ( 1.0 - amount ) + p2y * amount;

        return new Point( x, y );
        
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1 ponto inicial da linha.
     * @param p2 ponto final da linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Point p1, Point p2, double amount ) {
        return getPointAtLine( p1.x, p1.y, p2.x, p2.y, amount )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param v1 vetor do ponto inicial da linha.
     * @param v2 vetor do ponto final da linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Vector2 v1, Vector2 v2, double amount ) {
        return getPointAtLine( v1.x, v1.y, v2.x, v2.y, amount )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param line uma linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Line line, double amount ) {
        return getPointAtLine( line.x1, line.y1, line.x2, line.y2, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, double amount ) {

        double a = Math.pow( 1.0 - amount, 2 );
        double b = 2.0 * ( 1.0 - amount ) * amount;
        double c = Math.pow( amount, 2 );

        double x = a * p1x + b * cx + c * p2x;
        double y = a * p1y + b * cy + c * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Point p1, Point c, Point p2, double amount ) {
        return getPointAtQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param v1 vetor do ponto inicial da curva.
     * @param c vetor do ponto de controle da curva.
     * @param v2 vetor do ponto final da curva.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Vector2 v1, Vector2 c, Vector2 v2, double amount ) {
        return getPointAtQuadCurve( v1.x, v1.y, c.x, c.y, v2.x, v2.y, amount );
    }
    
    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( QuadCurve quadCurve, double amount ) {
        return getPointAtQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, double amount ) {

        double a = Math.pow( 1.0 - amount, 3 );
        double b = 3.0 * Math.pow( 1.0 - amount, 2 ) * amount;
        double c = 3.0 * ( 1.0 - amount ) * Math.pow( amount, 2 );
        double d = Math.pow( amount, 3 );

        double x = a * p1x + b * c1x + c * c2x + d * p2x;
        double y = a * p1y + b * c1y + c * c2y + d * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Point p1, Point c1, Point c2, Point p2, double amount ) {
        return getPointAtCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param v1 vetor do ponto inicial da curva.
     * @param c1 vetor do primeiro ponto de controle da curva.
     * @param c2 vetor do segundo ponto de controle da curva.
     * @param v2 vetor do ponto final da curva.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Vector2 v1, Vector2 c1, Vector2 c2, Vector2 v2, double amount ) {
        return getPointAtCubicCurve( v1.x, v1.y, c1.x, c1.y, c2.x, c2.y, v2.x, v2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( CubicCurve cubicCurve, double amount ) {
        return getPointAtCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, amount );
    }
    
    
    
    /***************************************************************************
     * Métodos estáticos privados para a criação de Path2Ds auxiliares.
     **************************************************************************/
    
    /*
     * Cria um triângulo.
     */
    public static Path2D createTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y ) {

        Path2D path = new Path2D.Double();
        path.moveTo( v1x, v1y );
        path.lineTo( v2x, v2y );
        path.lineTo( v3x, v3y );
        path.closePath();

        return path;

    }
    
    /*
     * Cria um polígono regular.
     */
    public static Path2D createPolygon( double centerX, double centerY, double sides, double radius, double rotation ) {

        Path2D path = new Path2D.Double();
        double currentAngle = rotation;
        double angleIncrement = 360.0 / sides;

        for ( int i = 0; i < sides; i++ ) {

            double rad = Math.toRadians( currentAngle );
            double x = centerX + radius * Math.cos( rad );
            double y = centerY + radius * Math.sin( rad );

            if ( i == 0 ) {
                path.moveTo( x, y );
            } else {
                path.lineTo( x, y );
            }

            currentAngle += angleIncrement;

        }

        path.closePath();
        
        return path;

    }
    
    /*
     * Cria um anel.
     */
    public static Path2D createRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle ) {

        Path2D path = new Path2D.Double();
        
        double extent = endAngle - startAngle;
        path.append( new Arc2D.Double( centerX - innerRadius, centerY - innerRadius, innerRadius * 2, innerRadius * 2, -startAngle, -extent, Arc2D.OPEN ), true );
        path.append( new Arc2D.Double( centerX - outerRadius, centerY - outerRadius, outerRadius * 2, outerRadius * 2, -startAngle - extent, extent, Arc2D.OPEN ), true );
        path.closePath();
        
        return path;

    }
    
    /*
     * Cria um anel (implementação antiga, mantida como referência).
     */
    @SuppressWarnings( "unused" )
    private static Path2D createRingOld( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments ) {

        Path2D path = new Path2D.Double();
        double currentAngle = startAngle;
        double angleIncrement = Math.abs( endAngle - startAngle ) / segments;

        double rad = Math.toRadians( currentAngle );
        double x = centerX + innerRadius * Math.cos( rad );
        double y = centerY + innerRadius * Math.sin( rad );
        path.moveTo( x, y );

        for ( int i = 0; i < segments; i++ ) {

            currentAngle += angleIncrement;

            rad = Math.toRadians( currentAngle );
            x = centerX + innerRadius * Math.cos( rad );
            y = centerY + innerRadius * Math.sin( rad );

            path.lineTo( x, y );

        }

        rad = Math.toRadians( currentAngle );
        x = centerX + outerRadius * Math.cos( rad );
        y = centerY + outerRadius * Math.sin( rad );
        path.lineTo( x, y );

        for ( int i = 0; i < segments; i++ ) {

            currentAngle -= angleIncrement;

            rad = Math.toRadians( currentAngle );
            x = centerX + outerRadius * Math.cos( rad );
            y = centerY + outerRadius * Math.sin( rad );

            path.lineTo( x, y );

        }

        path.closePath();
        
        return path;

    }
    
    
    
    /***************************************************************************
     * Métodos estáticos privados de suporte.
     **************************************************************************
     * /
     * 
    /**
     * Transforma um vetor 3D com uma matrix.
     */
    private static Vector3 vector3Transform( Vector3 v, Matrix mat ) {
        
        Vector3 result = new Vector3();

        double x = v.x;
        double y = v.y;
        double z = v.z;

        result.x = mat.m0*x + mat.m4*y + mat.m8*z + mat.m12;
        result.y = mat.m1*x + mat.m5*y + mat.m9*z + mat.m13;
        result.z = mat.m2*x + mat.m6*y + mat.m10*z + mat.m14;

        return result;
        
    }
    
    /**
     * Inverte uma matriz.
     */
    private static Matrix matrixInvert( Matrix mat ) {
        
        Matrix result = new Matrix();

        // Cache the matrix values (speed optimization)
        double a00 = mat.m0, a01 = mat.m1, a02 = mat.m2, a03 = mat.m3;
        double a10 = mat.m4, a11 = mat.m5, a12 = mat.m6, a13 = mat.m7;
        double a20 = mat.m8, a21 = mat.m9, a22 = mat.m10, a23 = mat.m11;
        double a30 = mat.m12, a31 = mat.m13, a32 = mat.m14, a33 = mat.m15;

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
    
    /**
     * Multiplica duas matrizes.
     */
    private static Matrix matrixMultiply( Matrix left, Matrix right ) {
        
        Matrix result = new Matrix();

        result.m0 = left.m0*right.m0 + left.m1*right.m4 + left.m2*right.m8 + left.m3*right.m12;
        result.m1 = left.m0*right.m1 + left.m1*right.m5 + left.m2*right.m9 + left.m3*right.m13;
        result.m2 = left.m0*right.m2 + left.m1*right.m6 + left.m2*right.m10 + left.m3*right.m14;
        result.m3 = left.m0*right.m3 + left.m1*right.m7 + left.m2*right.m11 + left.m3*right.m15;
        result.m4 = left.m4*right.m0 + left.m5*right.m4 + left.m6*right.m8 + left.m7*right.m12;
        result.m5 = left.m4*right.m1 + left.m5*right.m5 + left.m6*right.m9 + left.m7*right.m13;
        result.m6 = left.m4*right.m2 + left.m5*right.m6 + left.m6*right.m10 + left.m7*right.m14;
        result.m7 = left.m4*right.m3 + left.m5*right.m7 + left.m6*right.m11 + left.m7*right.m15;
        result.m8 = left.m8*right.m0 + left.m9*right.m4 + left.m10*right.m8 + left.m11*right.m12;
        result.m9 = left.m8*right.m1 + left.m9*right.m5 + left.m10*right.m9 + left.m11*right.m13;
        result.m10 = left.m8*right.m2 + left.m9*right.m6 + left.m10*right.m10 + left.m11*right.m14;
        result.m11 = left.m8*right.m3 + left.m9*right.m7 + left.m10*right.m11 + left.m11*right.m15;
        result.m12 = left.m12*right.m0 + left.m13*right.m4 + left.m14*right.m8 + left.m15*right.m12;
        result.m13 = left.m12*right.m1 + left.m13*right.m5 + left.m14*right.m9 + left.m15*right.m13;
        result.m14 = left.m12*right.m2 + left.m13*right.m6 + left.m14*right.m10 + left.m15*right.m14;
        result.m15 = left.m12*right.m3 + left.m13*right.m7 + left.m14*right.m11 + left.m15*right.m15;

        return result;
        
    }
    
    /**
     * Traslada uma matriz.
     */
    private static Matrix matrixTranslate( double x, double y, double z ) {
        return new Matrix( 
                1.0, 0.0, 0.0, x,
                0.0, 1.0, 0.0, y,
                0.0, 0.0, 1.0, z,
                0.0, 0.0, 0.0, 1.0
        );
    }
    
    /**
     * Rotaciona uma matriz (ângulo em radianos)
     */
    private static Matrix matrixRotate( Vector3 axis, double angle ) {
        
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
     * Escalona uma matriz.
     */
    private static Matrix matrixScale( double x, double y, double z ) {
        return new Matrix( 
                x, 0.0, 0.0, 0.0,
                0.0, y, 0.0, 0.0,
                0.0, 0.0, z, 0.0,
                0.0, 0.0, 0.0, 1.0
        );
    }
    
    /**
     * Obtém a matriz da câmera 2D.
     */
    private static Matrix getCameraMatrix2D( Camera2D camera ) {
        
        // from: https://github.com/raysan5/raylib/blob/master/src/rcore.c
        // The camera in world-space is set by
        //   1. Move it to target
        //   2. Rotate by -rotation and scale by (1/zoom)
        //      When setting higher scale, it's more intuitive for the world to become bigger (= camera become smaller),
        //      not for the camera getting bigger, hence the invert. Same deal with rotation
        //   3. Move it by (-offset);
        //      Offset defines target transform relative to screen, but since we're effectively "moving" screen (camera)
        //      we need to do it into opposite direction (inverse transform)

        // Having camera transform in world-space, inverse of it gives the modelview transform
        // Since (A*B*C)' = C'*B'*A', the modelview is
        //   1. Move to offset
        //   2. Rotate and Scale
        //   3. Move by -target
        Matrix matOrigin = matrixTranslate( -camera.target.x, -camera.target.y, 0.0 );
        Matrix matRotation = matrixRotate( new Vector3( 0.0f, 0.0f, 1.0f ), Math.toRadians( camera.rotation ) );
        Matrix matScale = matrixScale( camera.zoom, camera.zoom, 1.0 );
        Matrix matTranslation = matrixTranslate( camera.offset.x, camera.offset.y, 0.0 );

        return matrixMultiply( matrixMultiply( matOrigin, matrixMultiply( matScale, matRotation ) ), matTranslation );
        
    }
    
    
    
    /***************************************************************************
     * Classes internas estáticas de suporte.
     **************************************************************************/
    
    /**
     * Classe para representação de um vetor de três dimensões.
     * 
     * @author Prof. Dr. David Buzatto
     */
    private static class Vector3 {
    
        public double x;
        public double y;
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
    
        @Override
        public String toString() {
            return String.format( "Vector3[%.2f, %.2f, %.2f]", x, y, z );
        }
    
    }
    
    /**
     * Classe para representação de um vetor de quatro dimensões.
     * 
     * @author Prof. Dr. David Buzatto
     */
    private static class Vector4 {
    
        public double x;
        public double y;
        public double z;
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
    
        @Override
        public String toString() {
            return String.format( "Vector3[%.2f, %.2f, %.2f, %.2f]", x, y, z, w );
        }
    
    }

    /**
     * Uma matriz.
     * 
     * @author Prof. Dr. David Buzatto
     */
    private static class Matrix {
 
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
        public Matrix( double m0, double m4, double m8, double m12, double m1, double m5, double m9, double m13, double m2, double m6, double m10, double m14, double m3, double m7, double m11, double m15 ) {
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

}
