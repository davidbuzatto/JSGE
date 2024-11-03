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
package br.com.davidbuzatto.jsge.core.utils;

import br.com.davidbuzatto.jsge.image.Image;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.DoubleFunction;

/**
 * Interface com métodos utilitários de desenho.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface DrawingUtils {
       
    /**
     * Cria Path2D de um triângulo.
     * 
     * @param v1x Coordenada x do primeiro vértice.
     * @param v1y Coordenada y do primeiro vértice.
     * @param v2x Coordenada x do segundo vértice.
     * @param v2y Coordenada y do segundo vértice.
     * @param v3x Coordenada x do terceiro vértice.
     * @param v3y Coordenada y do quarto vértice.
     * @return O Path2D do triângulo.
     */
    public static Path2D createTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y ) {

        Path2D path = new Path2D.Double();
        path.moveTo( v1x, v1y );
        path.lineTo( v2x, v2y );
        path.lineTo( v3x, v3y );
        path.closePath();

        return path;

    }
    
    /**
     * Cria um Path2D de um polígono regular.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param sides Quantidade de lados.
     * @param radius Raio do círculo circunscrito.
     * @param rotation Ângulo inicial em graus (sentido horário).
     * @return O Path2D do polígono.
     */
    public static Path2D createPolygon( double x, double y, int sides, double radius, double rotation ) {

        Path2D path = new Path2D.Double();
        double currentAngle = rotation;
        double angleIncrement = 360.0 / sides;

        for ( int i = 0; i < sides; i++ ) {

            double rad = Math.toRadians( currentAngle );
            double ix = x + radius * Math.cos( rad );
            double iy = y + radius * Math.sin( rad );

            if ( i == 0 ) {
                path.moveTo( ix, iy );
            } else {
                path.lineTo( ix, iy );
            }

            currentAngle += angleIncrement;

        }

        path.closePath();
        
        return path;

    }
    
    /**
     * Cria um Path2D de uma estrela regular.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param tips Quantidade de pontas.
     * @param radius Raio do círculo circunscrito.
     * @param rotation Ângulo inicial em graus (sentido horário).
     * @return O Path2D da estrela.
     */
    public static Path2D createStar( double x, double y, int tips, double radius, double rotation ) {

        Path2D path = new Path2D.Double();
        double currentAngle = rotation;
        double angleIncrement = 360.0 / tips;
        
        double[] xs = new double[tips];
        double[] ys = new double[tips];
        
        for ( int i = 0; i < tips; i++ ) {
            double rad = Math.toRadians( currentAngle );
            xs[i] = x + radius * Math.cos( rad );
            ys[i] = y + radius * Math.sin( rad );
            currentAngle += angleIncrement;
        }
        path.moveTo( xs[0], ys[0] );
        
        if ( tips % 2 == 0 ) {
            
            for ( int i = 2; i < tips; i += 2 ) {
                path.lineTo( xs[i], ys[i] );
            }
            path.closePath();
            
            path.moveTo( xs[1], ys[1] );
            for ( int i = 3; i < tips; i += 2 ) {
                path.lineTo( xs[i], ys[i] );
            }
            
        } else {
            
            for ( int i = 2; i < tips * 2; i += 2 ) {
                path.lineTo( xs[i%tips], ys[i%tips] );
            }
            
        }

        path.closePath();
        
        return path;

    }
    
    /**
     * Cria um Path2D de um anel.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentigo horário).
     * @param endAngle Ângulo final em graus (sentigo horário).
     * @return O Path2D do anel.
     */
    public static Path2D createRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle ) {

        Path2D path = new Path2D.Double();
        
        double extent = endAngle - startAngle;
        path.append( new Arc2D.Double( x - innerRadius, y - innerRadius, innerRadius * 2, innerRadius * 2, -startAngle, -extent, Arc2D.OPEN ), true );
        path.append( new Arc2D.Double( x - outerRadius, y - outerRadius, outerRadius * 2, outerRadius * 2, -startAngle - extent, extent, Arc2D.OPEN ), true );
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
     * @param text Texto a ser desenhado.
     * @param x Coordenada x inicial.
     * @param y Coordenada y inicial.
     * @param g2d Contexto gráfico utilizado.
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
    
    /**
     * Plota uma função f(x) em um gráfico no formato de uma imagem.
     * 
     * @param function A função.
     * @param width Largura da imagem gerada.
     * @param height Altura da imagem gerada.
     * @param marginH Margem horizontal.
     * @param marginV Margem Vertical.
     * @param axisColor Cor dos eixos.
     * @param graphColor Cor do gráfico.
     * @return Uma imagem com a plotagem do gráfico.
     */
    public static Image plot( DoubleFunction<Double> function, int width, int height, int marginH, int marginV, Color axisColor, Color graphColor ) {
        
        BufferedImage img = new BufferedImage( width, height, BufferedImage.TYPE_4BYTE_ABGR );
        Graphics2D g2d = (Graphics2D) img.createGraphics();
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
        
        g2d.setColor( axisColor );
        g2d.drawLine( marginH, marginV, marginH, height - marginV );
        g2d.drawLine( marginH, marginV, marginH - 3, marginV + 3 );
        g2d.drawLine( marginH, marginV, marginH + 3, marginV + 3 );
        g2d.drawLine( marginH, height - marginV, width - marginH, height - marginV );
        g2d.drawLine( width - marginH, height - marginV, width - marginH - 3, height - marginV - 3 );
        g2d.drawLine( width - marginH, height - marginV, width - marginH - 3, height - marginV + 3 );
        
        g2d.drawString( "y", marginH + 5, marginV + 15 );
        g2d.drawString( "x", width - marginH - 14, height - marginV - 5 );
        
        g2d.setColor( graphColor );
        g2d.setStroke( new BasicStroke( 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
        Path2D.Double path = new Path2D.Double();
        
        for ( double i = 0.0; i <= 100.0; i += 0.1 ) {
            
            double p = i / 100.0;
            
            double x = marginH + ( width - marginH * 2 ) * p;
            double y = height - marginV - ( height - marginV * 2 ) * function.apply( p );
            
            if ( i == 0 ) {
                path.moveTo( x, y );
            } else {
                path.lineTo( x, y );
            }
        }
        
        g2d.draw( path );
        g2d.dispose();
        
        return new Image( img );
        
    }
    
}
