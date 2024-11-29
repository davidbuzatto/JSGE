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

import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface com métodos utilitários para curvas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface CurveUtils {
    
    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Vector2 getPointAtLine( double p1x, double p1y, double p2x, double p2y, double amount ) {

        double x = p1x * ( 1.0 - amount ) + p2x * amount;
        double y = p1y * ( 1.0 - amount ) + p2y * amount;

        return new Vector2( x, y );
        
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1 Ponto inicial da linha.
     * @param p2 Ponto final da linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Vector2 getPointAtLine( Vector2 p1, Vector2 p2, double amount ) {
        return getPointAtLine( p1.x, p1.y, p2.x, p2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param line Uma linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Vector2 getPointAtLine( Line line, double amount ) {
        return getPointAtLine( line.x1, line.y1, line.x2, line.y2, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param cx Coordenada x do ponto de controle.
     * @param cy Coordenada y do ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Vector2 getPointAtQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, double amount ) {

        double a = Math.pow( 1.0 - amount, 2 );
        double b = 2.0 * ( 1.0 - amount ) * amount;
        double c = Math.pow( amount, 2 );

        double x = a * p1x + b * cx + c * p2x;
        double y = a * p1y + b * cy + c * p2y;

        return new Vector2( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 Ponto inicial.
     * @param c Ponto de controle.
     * @param p2 Ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Vector2 getPointAtQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, double amount ) {
        return getPointAtQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, amount );
    }
    
    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve Uma curva Bézier quadrática.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Vector2 getPointAtQuadCurve( QuadCurve quadCurve, double amount ) {
        return getPointAtQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param c1x Coordenada x do primeiro ponto de controle.
     * @param c1y Coordenada y do primeiro ponto de controle.
     * @param c2x Coordenada x do segundo ponto de controle.
     * @param c2y Coordenada y do segundo ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Vector2 getPointAtCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, double amount ) {

        double a = Math.pow( 1.0 - amount, 3 );
        double b = 3.0 * Math.pow( 1.0 - amount, 2 ) * amount;
        double c = 3.0 * ( 1.0 - amount ) * Math.pow( amount, 2 );
        double d = Math.pow( amount, 3 );

        double x = a * p1x + b * c1x + c * c2x + d * p2x;
        double y = a * p1y + b * c1y + c * c2y + d * p2y;

        return new Vector2( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 Ponto inicial.
     * @param c1 Primeiro ponto de controle.
     * @param c2 Segundo ponto de controle.
     * @param p2 Ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Vector2 getPointAtCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, double amount ) {
        return getPointAtCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve Uma curva Bézier cúbica.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Vector2 getPointAtCubicCurve( CubicCurve cubicCurve, double amount ) {
        return getPointAtCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, amount );
    }
    
    /**
     * Aplica o algoritmo de Chaikin em uma curva representada por uma lista de 
     * pontos, retornando uma nova lista de pontos que corresponde à curva
     * suavizada.
     * 
     * Referências:
     *     https://www.codeproject.com/Articles/1093960/2D-Polyline-Vertex-Smoothing
     *     https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
     * 
     * @param points Pontos que serão processados.
     * @param tension Tensão [0..1]
     * @param iterations Quantidade iterações [1..10]
     * @return Uma lista de pontos que representa uma curva suavizada ou uma
     * lista vazia caso a lista de pontos contenha menos que 3 pontos.
     */
    public static List<Vector2> getCurveSmoothingChaikin( List<Vector2> points, double tension, int iterations ) {
        
        List<Vector2> newList = new ArrayList<>();
        
        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // checks
        if ( points == null || points.size() < 3 ) {
            return newList;
        }

        if ( iterations < 1 ) {
            iterations = 1;
        } else if ( iterations > 10 ) {
            iterations = 10;
        }

        if ( tension < 0 ) {
            tension = 0;
        } else if ( tension > 1 ) {
            tension = 1;
        }

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // the tension factor defines a scale between corner cutting distance in segment half length, i.e. between 0.05 and 0.45
        // the opposite corner will be cut by the inverse (i.e. 1-cutting distance) to keep symmetry
        // with a tension value of 0.5 this amounts to 0.25 = 1/4 and 0.75 = 3/4 the original Chaikin values
        double cutdist = 0.05 + ( tension * 0.4 );

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // make a copy of the pointlist and feed it to the iteration
        for ( Vector2 point : points ) {
            newList.add( new Vector2( point.x, point.y ) );
        }

        for ( int i = 0; i <= iterations; i++ ) {
            newList = getSmootherChaikin( newList, cutdist );
        }

        return newList;

    }
    
    private static List<Vector2> getSmootherChaikin( List<Vector2> points, double cuttingDist ) {
        
        List<Vector2> newList = new ArrayList<>();

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // always add the first point
        newList.add( new Vector2( points.get( 0 ).x, points.get( 0 ).y ) );
        
        for ( int i = 0; i < points.size() - 1; i++ ) {
            
            Vector2 p1 = points.get( i );
            Vector2 p2 = points.get( i + 1 );
            
            Vector2 q = new Vector2(
                ( 1 - cuttingDist ) * p1.x + cuttingDist * p2.x,
                ( 1 - cuttingDist ) * p1.y + cuttingDist * p2.y
            );
            
            Vector2 r = new Vector2(
                cuttingDist * p1.x + ( 1 - cuttingDist ) * p2.x,
                cuttingDist * p1.y + ( 1 - cuttingDist ) * p2.y
            );
            
            newList.add( q );
            newList.add( r );
            
        }

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // always add the last point
        newList.add( new Vector2( points.get( points.size() - 1 ).x, points.get( points.size() - 1 ).y ) );

        return newList;
        
    }
    
    /**
     * Aplica o algoritmo de Catmull-Rom em uma curva representada por uma lista de 
     * pontos, retornando uma nova lista de pontos que corresponde à curva
     * suavizada.
     * 
     * @param points A lista de pontos.
     * @param interpolationPoints A quantidade de pontos de interpolação.
     * @return Uma lista de pontos que representa uma curva suavizada ou uma
     * lista vazia caso a lista de pontos contenha menos que 3 pontos.
     */
    public static List<Vector2> getSplineInterpolationCatmullRom( List<Vector2> points, int interpolationPoints ) {
        
        List<Vector2> spline = new ArrayList<>();
        
        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // The Catmull-Rom Spline, requires at least 4 points so it is possible to extrapolate from 3 points, but not from 2.
        // you would get a straight line anyway
        if ( points.size() < 3 ) {
            return spline;
        }

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // could throw an error on the following, but it is easily fixed implicitly
        if ( interpolationPoints < 1 ) {
            interpolationPoints = 1;
        }

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // create a new pointlist to do splining on
        // if you don't do this, the original pointlist gets extended with the exptrapolated points
        List<Vector2> spoints = new ArrayList<>();
        for( Vector2 p : points ) {
            spoints.add( new Vector2( p.x, p.y ) );
        }
        
        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // always extrapolate the first and last point out
        double dx = spoints.get(1).x - spoints.get(0).x;
        double dy = spoints.get(1).y - spoints.get(0).y;
        spoints.add( 0, new Vector2( spoints.get(0).x - dx, spoints.get(0).y - dy ) );
        dx = spoints.get(spoints.size()-1).x - spoints.get(spoints.size()-2).x;
        dy = spoints.get(spoints.size()-1).y - spoints.get(spoints.size()-2).y;
        spoints.add( new Vector2( spoints.get(spoints.size()-1).x + dx, spoints.get(spoints.size()-1).y + dy ) );

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // Note the nrOfInterpolatedPoints acts as a kind of tension factor between 0 and 1 because it is normalised
        // to 1/nrOfInterpolatedPoints. It can never be 0
        double t;
        Vector2 spoint;
        
        for ( int i = 0; i <= spoints.size() - 4; i++ ) {
            
            for ( int intp = 0; intp <= interpolationPoints - 1; intp++ ) {
                
                t = 1.0 / interpolationPoints * intp;
                Vector2 i0 = spoints.get( i );
                Vector2 i1 = spoints.get( i + 1 );
                Vector2 i2 = spoints.get( i + 2 );
                Vector2 i3 = spoints.get( i + 3 );
                double t2 = t * t;
                double t3 = t2 * t;
                
                spline.add( new Vector2(
                    0.5 * ( 2 * i1.x + ( -1 * i0.x + i2.x ) * t + ( 2 * i0.x - 5 * i1.x + 4 * i2.x - i3.x ) * t2 + ( -1 * i0.x + 3 * i1.x - 3 * i2.x + i3.x ) * t3 ),
                    0.5 * ( 2 * i1.y + ( -1 * i0.y + i2.y ) * t + ( 2 * i0.y - 5 * i1.y + 4 * i2.y - i3.y ) * t2 + ( -1 * i0.y + 3 * i1.y - 3 * i2.y + i3.y ) * t3 )
                ));
                
            }
            
        }

        // comentário original: https://github.com/xstos/PolylineSmoothCSharp/blob/master/MainForm.cs
        // add the last point, but skip the interpolated last point, so second last...
        spline.add( spoints.get(spoints.size() - 2) );
        return spline;
            
    }
    
}
