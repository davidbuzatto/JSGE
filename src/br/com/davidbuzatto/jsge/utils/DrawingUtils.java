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

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Classe com métodos utilitários internos da engine.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawingUtils {
       
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
    
    /**
     * Método estático auxiliar para separação do texto em várias linhas e seu
     * consecutivo desenho.
     * 
     * @param text texto a ser desenhado.
     * @param x coordenada x inicial.
     * @param y coordenada y inicial.
     * @param g2d contexto gráfico utilizado.
     */
    public static void drawTextMultilineHelper( String text, double x, double y, Graphics2D g2d ) {
        
        double iy = y;
        boolean first = true;
        
        for ( String t : text.split( "\n" ) ) {
            Rectangle2D r = g2d.getFontMetrics().getStringBounds( t, g2d );
            if ( first ) {
                iy += r.getHeight() / 2;
                first = false;
            } else {
                iy += r.getHeight() * 0.8;
            }
            g2d.drawString( t, (int) x, (int) iy );
        }
        
    }
    
}
