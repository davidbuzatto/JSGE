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

import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;

/**
 * Classe com métodos estáticos utilitários para detecção de colisão.
 * 
 * Várias implementações são baseadas na raylib e em seus módulos
 * (www.raylib.com).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CollisionUtils {
    
    private static final double FLT_EPSILON = 2.2204460492503131e-16;

    /**
     * Verifica se dois retângulos colidiram.
     * 
     * @param rec1 Um retângulo.
     * @param rec2 Outro retângulo.
     * @return Verdadeiro caso os retângulos tenham colidido, falso caso contrário.
     */
    public static boolean checkCollisionRectangles( Rectangle rec1, Rectangle rec2 ) {
        return ( 
            ( rec1.x < ( rec2.x + rec2.width ) &&  ( rec1.x + rec1.width ) > rec2.x ) &&
            ( rec1.y < ( rec2.y + rec2.height ) && ( rec1.y + rec1.height ) > rec2.y )
        );
    }

    /**
     * Verifica se dois círculos definidos por pontos e raios colidiram.
     * 
     * @param center1 Ponto do centro do primeiro círculo.
     * @param radius1 Raio do primeiro círculo.
     * @param center2 Ponto do centro do segundo círculo.
     * @param radius2 Raio do segundo círculo.
     * @return Verdadeiro caso os círculos tenham colidido, falso caso contrário.
     */
    public static boolean checkCollisionCircles( Vector2 center1, double radius1, Vector2 center2, double radius2 ) {
        
        double dx = center2.x - center1.x;
        double dy = center2.y - center1.y;
    
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = radius1 + radius2;
    
        return distanceSquared <= radiusSum * radiusSum;

    }

    /**
     * Verifica se dois círculos colidiram.
     * 
     * @param circle1 Um círculo.
     * @param circle2 Outro círculo.
     * @return Verdadeiro caso os círculos tenham colidido, falso caso contrário.
     */
    public static boolean checkCollisionCircles( Circle circle1, Circle circle2 ) {
        return checkCollisionCircles( 
            new Vector2( circle1.x, circle1.y ), circle1.radius,
            new Vector2( circle2.x, circle2.y ), circle2.radius
        );
    }
    
    /**
     * Verifica se um círculo, definido por um ponto e pelo raio, colidiu
     * com uma linha definida por dois pontos.
     * 
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @param p1 Ponto inicial da linha.
     * @param p2 Ponto final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Vector2 center, double radius, Vector2 p1, Vector2 p2 ) {

        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;

        if ( ( Math.abs( dx ) + Math.abs( dy ) ) <= FLT_EPSILON ) {
            return checkCollisionCircles( p1, 0, center, radius );
        }

        double lengthSQ = ( ( dx * dx ) + ( dy * dy ) );
        double dotProduct = ( ( ( center.x - p1.x ) * ( p2.x - p1.x ) ) + 
                              ( ( center.y - p1.y ) * ( p2.y - p1.y ) ) ) / ( lengthSQ );

        if ( dotProduct > 1.0 ) {
            dotProduct = 1.0;
        } else if ( dotProduct < 0.0 ) {
            dotProduct = 0.0;
        }

        double dx2 = ( p1.x - ( dotProduct * ( dx ) ) ) - center.x;
        double dy2 = ( p1.y - ( dotProduct * ( dy ) ) ) - center.y;
        double distanceSQ = ( dx2 * dx2 ) + ( dy2 * dy2 );

        return distanceSQ <= radius * radius;

    }

    /**
     * Verifica se um círculo colidiu com uma linha definida por dois pontos.
     * 
     * @param circle O círculo.
     * @param p1 Ponto inicial da linha.
     * @param p2 Ponto final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Circle circle, Vector2 p1, Vector2 p2 ) {
        return checkCollisionCircleLine( new Vector2( circle.x, circle.y ), circle.radius, p1, p2 );
    }

    /**
     * Verifica se um círculo colidiu com uma linha.
     * 
     * @param circle O círculo.
     * @param line A linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Circle circle, Line line ) {
        return checkCollisionCircleLine( 
            new Vector2( circle.x, circle.y ), circle.radius,
            new Vector2( line.x1, line.y1 ), 
            new Vector2( line.x2, line.y2 )
        );
    }

    /**
     * Verifica se um círculo, definido por um ponto e pelo raio, colidiu
     * com um retângulo.
     * 
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @param rec Um retângulo.
     * @return Verdadeiro caso o círculo tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionCircleRectangle( Vector2 center, double radius, Rectangle rec ) {

        double recCenterX = rec.x + rec.width / 2.0;
        double recCenterY = rec.y + rec.height / 2.0;

        double dx = Math.abs( center.x - recCenterX );
        double dy = Math.abs( center.y - recCenterY );

        if ( dx > ( rec.width / 2.0 + radius ) ) {
            return false;
        }
        if ( dy > ( rec.height / 2.0 + radius ) ) { 
            return false;
        }

        if ( dx <= ( rec.width / 2.0 ) ) { 
            return true;
        }
        if ( dy <= ( rec.height / 2.0 ) ) { 
            return true;
        }

        double cornerDistanceSq = ( dx - rec.width / 2.0 ) * ( dx - rec.width / 2.0 ) +
                                  ( dy - rec.height / 2.0 ) * ( dy - rec.height / 2.0 );

        return cornerDistanceSq <= ( radius * radius );

    }

    /**
     * Verifica se um círculo colidiu com um retângulo.
     * 
     * @param circle O círculo.
     * @param rec O retângulo.
     * @return Verdadeiro caso o círculo tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionCircleRectangle( Circle circle, Rectangle rec ) {
        return checkCollisionCircleRectangle( new Vector2( circle.x, circle.y ), circle.radius, rec );
    }

    /**
     * Verifica se um ponto, definido pela coordenada inicial, colidiu com um retângulo.
     * 
     * @param x Coordenada x do ponto.
     * @param y Coordenada y do ponto.
     * @param rec O retângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointRectangle( double x, double y, Rectangle rec ) {
        return ( 
            ( x >= rec.x ) && 
            ( x < ( rec.x + rec.width ) ) && 
            ( y >= rec.y ) && 
            ( y < ( rec.y + rec.height ) )
        );
    }

    /**
     * Verifica se um ponto colidiu com um retângulo.
     * 
     * @param point O ponto.
     * @param rec O retângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointRectangle( Vector2 point, Rectangle rec ) {
        return checkCollisionPointRectangle( point.x, point.y, rec );
    }

    /**
     * Verifica se um ponto colidiu com um círculo.
     * 
     * @param x Coordenada x do ponto.
     * @param y Coordenada y do ponto.
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionPointCircle( double x, double y, Vector2 center, double radius ) {
        double distanceSquared = ( x - center.x ) * ( x - center.x ) + 
                                 ( y - center.y ) * ( y - center.y );
        return distanceSquared <= radius * radius;
    }

    /**
     * Verifica se um ponto colidiu com um círculo.
     * 
     * @param point O ponto.
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionPointCircle( Vector2 point, Vector2 center, double radius ) {
        return checkCollisionPointCircle( point.x, point.y, center, radius );
    }

    /**
     * Verifica se um ponto colidiu com um círculo.
     * 
     * @param point O ponto.
     * @param circle O círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionPointCircle( Vector2 point, Circle circle ) {
        return checkCollisionPointCircle( point.x, point.y, new Vector2( circle.x, circle.y ), circle.radius );
    }

    /**
     * Verifica se um ponto colidiu com um triângulo.
     * 
     * @param point O ponto.
     * @param p1 Ponto do primeiro vértice do triângulo.
     * @param p2 Ponto do segundo vértice do triângulo.
     * @param p3 Ponto do terceiro vértice do triângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointTriangle( Vector2 point, Vector2 p1, Vector2 p2, Vector2 p3 ) {

        double alpha = ((p2.y - p3.y)*(point.x - p3.x) + (p3.x - p2.x)*(point.y - p3.y)) /
                       ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));

        double beta = ((p3.y - p1.y)*(point.x - p3.x) + (p1.x - p3.x)*(point.y - p3.y)) /
                      ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));

        double gamma = 1.0 - alpha - beta;

        return (alpha > 0 ) && ( beta > 0 ) && ( gamma > 0 );

    }

    /**
     * Verifica se um ponto colidiu com um triângulo.
     * 
     * @param point O ponto.
     * @param triangle O triângulo.
     * @return Verdadeiro caso o vetor tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointTriangle( Vector2 point, Triangle triangle ) {
        return checkCollisionPointTriangle( 
            point, 
            new Vector2( triangle.x1, triangle.y1 ),
            new Vector2( triangle.x2, triangle.y2 ),
            new Vector2( triangle.x3, triangle.y3 )
        );
    }

    /**
     * Verifica se um ponto colidiu com um polígono regular.
     * 
     * @param point O ponto.
     * @param polygon O polígono.
     * @return Verdadeiro caso o ponto tenha colidido com o polígono, falso caso contrário.
     */
    public static boolean checkCollisionPointPolygon( Vector2 point, Polygon polygon ) {

        boolean inside = false;

        if ( polygon.sides > 2 ) {

            Vector2[] points = new Vector2[polygon.sides];
            double angle = 360.0 / polygon.sides;

            for ( int i = 0; i < polygon.sides; i++ ) {
                points[i] = new Vector2(
                    polygon.x + Math.cos( Math.toRadians( polygon.rotation + angle * i ) ) * polygon.radius,
                    polygon.y + Math.sin( Math.toRadians( polygon.rotation + angle * i ) ) * polygon.radius
                );
            }

            for ( int i = 0, j = polygon.sides - 1; i < polygon.sides; j = i++ ) {
                if ( ( points[i].y > point.y ) != ( points[j].y > point.y ) &&
                     ( point.x < ( points[j].x - points[i].x ) * ( point.y - points[i].y ) / ( points[j].y - points[i].y ) + points[i].x ) ) {
                    inside = !inside;
                }
            }

        }

        return inside;

    }

    /**
     * Verifica a colisão entre duas linhas.
     * 
     * @param startPos1 Ponto inicial da primeira linha.
     * @param endPos1 Ponto final da segunda linha.
     * @param startPos2 Ponto inicial da segunda linha.
     * @param endPos2 Ponto final da segunda linha.
     * @return Retorna o ponto de colisão caso as linhas tenham se interceptado ou nulo caso contrário.
     */
    public static Vector2 checkCollisionLines( Vector2 startPos1, Vector2 endPos1, Vector2 startPos2, Vector2 endPos2 ) {
        
        boolean collision = false;

        double div = (endPos2.y - startPos2.y)*(endPos1.x - startPos1.x) - (endPos2.x - startPos2.x)*(endPos1.y - startPos1.y);
        double xi = 0;
        double yi = 0;

        if ( Math.abs(div) >= FLT_EPSILON ) {

            collision = true;

            xi = ((startPos2.x - endPos2.x)*(startPos1.x*endPos1.y - startPos1.y*endPos1.x) - (startPos1.x - endPos1.x)*(startPos2.x*endPos2.y - startPos2.y*endPos2.x))/div;
            yi = ((startPos2.y - endPos2.y)*(startPos1.x*endPos1.y - startPos1.y*endPos1.x) - (startPos1.y - endPos1.y)*(startPos2.x*endPos2.y - startPos2.y*endPos2.x))/div;

            if (((Math.abs(startPos1.x - endPos1.x) > FLT_EPSILON) && (xi < Math.min(startPos1.x, endPos1.x) || (xi > Math.max(startPos1.x, endPos1.x)))) ||
                ((Math.abs(startPos2.x - endPos2.x) > FLT_EPSILON) && (xi < Math.min(startPos2.x, endPos2.x) || (xi > Math.max(startPos2.x, endPos2.x)))) ||
                ((Math.abs(startPos1.y - endPos1.y) > FLT_EPSILON) && (yi < Math.min(startPos1.y, endPos1.y) || (yi > Math.max(startPos1.y, endPos1.y)))) ||
                ((Math.abs(startPos2.y - endPos2.y) > FLT_EPSILON) && (yi < Math.min(startPos2.y, endPos2.y) || (yi > Math.max(startPos2.y, endPos2.y))))) {
                collision = false;
            }

        }

        if ( collision ) {
            return new Vector2( xi, yi );
        }

        return null;

    }

    /**
     * Verifica a colisão entre duas linhas.
     * 
     * @param line1 Uma linha.
     * @param line2 Outra linha.
     * @return Retorna o ponto de colisão caso as linhas tenham se interceptado ou nulo caso contrário.
     */
    public static Vector2 checkCollisionLines( Line line1, Line line2 ) {
        return checkCollisionLines(
            new Vector2( line1.x1, line1.y1 ),
            new Vector2( line1.x2, line1.y2 ),
            new Vector2( line2.x1, line2.y1 ),
            new Vector2( line2.x2, line2.y2 )
        );
    }

    /**
     * Verifica a colisão de um ponto com uma linha.
     * 
     * @param point O ponto.
     * @param p1 O ponto inicial da linha.
     * @param p2 O ponto final da linha.
     * @param threshold O limite de proximidade entre o ponto e a linha.
     * @return Verdadeiro caso o ponto tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionPointLine( Vector2 point, Vector2 p1, Vector2 p2, int threshold ) {

        double dxc = point.x - p1.x;
        double dyc = point.y - p1.y;
        double dxl = p2.x - p1.x;
        double dyl = p2.y - p1.y;
        double cross = dxc * dyl - dyc * dxl;

        if ( Math.abs(cross) < ( threshold * Math.max( Math.abs(dxl), Math.abs(dyl) ) ) ) {
            if ( Math.abs( dxl ) >= Math.abs( dyl ) ) {
                return ( dxl > 0 ) ? ( ( p1.x <= point.x ) && ( point.x <= p2.x ) ) : ( ( p2.x <= point.x ) && ( point.x <= p1.x ) );
            }
            return ( dyl > 0 ) ? ( ( p1.y <= point.y ) && ( point.y <= p2.y ) ) : ( ( p2.y <= point.y ) && ( point.y <= p1.y ) );
        }

        return false;

    }

    /**
     * Verifica a colisão de um ponto com uma linha.
     * 
     * @param point O ponto.
     * @param line A linha.
     * @param threshold O limite de proximidade entre o ponto e a linha.
     * @return Verdadeiro caso o ponto tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionPointLine( Vector2 point, Line line, int threshold ) {
        return checkCollisionPointLine( point, new Vector2( line.x1, line.y1 ), new Vector2( line.x2, line.y2 ), threshold );
    }

    /**
     * Calcula o retângulo da sobreposição entre dois retângulos.
     * 
     * @param rec1 Um retângulo.
     * @param rec2 Outro retângulo.
     * @return O retângulo da sobreposição entre os dois retângulos.
     */
    public static Rectangle getCollisionRectangle( Rectangle rec1, Rectangle rec2 ) {
        
        Rectangle overlap = new Rectangle();

        double left = ( rec1.x > rec2.x ) ? rec1.x : rec2.x;
        double right1 = rec1.x + rec1.width;
        double right2 = rec2.x + rec2.width;
        double right = ( right1 < right2 ) ? right1 : right2;
        double top = ( rec1.y > rec2.y ) ? rec1.y : rec2.y;
        double bottom1 = rec1.y + rec1.height;
        double bottom2 = rec2.y + rec2.height;
        double bottom = ( bottom1 < bottom2 ) ? bottom1 : bottom2;

        if ( ( left < right ) && ( top < bottom ) ) {
            overlap.x = left;
            overlap.y = top;
            overlap.width = right - left;
            overlap.height = bottom - top;
        }

        return overlap;

    }

}
