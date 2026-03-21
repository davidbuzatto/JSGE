/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.DoubleFunction;

/**
 * Interface with drawing utility methods.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface DrawingUtils {

    /**
     * Creates a Path2D for a triangle.
     *
     * @param v1x X coordinate of the first vertex.
     * @param v1y Y coordinate of the first vertex.
     * @param v2x X coordinate of the second vertex.
     * @param v2y Y coordinate of the second vertex.
     * @param v3x X coordinate of the third vertex.
     * @param v3y Y coordinate of the fourth vertex.
     * @return The Path2D of the triangle.
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
     * Creates a Path2D for a regular polygon.
     *
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param sides Number of sides.
     * @param radius Radius of the circumscribed circle.
     * @param rotation Initial angle in degrees (clockwise).
     * @return The Path2D of the polygon.
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
     * Creates a Path2D for a regular star.
     *
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param tips Number of points/tips.
     * @param radius Radius of the circumscribed circle.
     * @param rotation Initial angle in degrees (clockwise).
     * @return The Path2D of the star.
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
     * Creates a Path2D for a ring.
     *
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @return The Path2D of the ring.
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
     * Creates a ring (old implementation, kept as reference).
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
     * Static helper method for splitting text into multiple lines and
     * drawing them consecutively.
     *
     * @param text Text to be drawn.
     * @param x Initial x coordinate.
     * @param y Initial y coordinate.
     * @param g2d The graphics context used.
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
     * Plots a function f(x) on a graph in image format.
     *
     * @param function The function.
     * @param width Width of the generated image.
     * @param height Height of the generated image.
     * @param marginH Horizontal margin.
     * @param marginV Vertical margin.
     * @param axisColor Color of the axes.
     * @param graphColor Color of the graph.
     * @return An image with the graph plot.
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
    
    /**
     * Creates an image of the JSGE logo.
     *
     * @return An image of the JSGE logo.
     */
    public static Image createLogo() {
        
        int w = 512;
        int x = 0;
        int y = 0;
        int wi = 40;
        
        Image logo = new Image( w, w );
        
        Rectangle re = new Rectangle( x, y, w, w );
        Rectangle ri = new Rectangle( x + wi, y + wi, w - wi * 2, w - wi * 2 );
        
        double right = ri.x + ri.width;
        double down = ri.y + ri.height;
        
        Color c1 = new Color( 9, 110, 201 );
        Color c2 = new Color( 177, 130, 148 );
        Color c3 = new Color( 242, 147, 14 );        
        Paint g = PaintUtils.getLinearGradientPaint( ri.x, ri.y, right, down, new float[]{ 0.0f, 0.5f, 1.0f }, new Color[]{ c1, c2, c3 } );
        
        logo.fillRectangle( re, g );
        logo.fillRectangle( ri, EngineFrame.RAYWHITE );
        
        int sw = 18;
        int lh = 7 * sw;
        int lw = 5 * sw;
        int margin = sw;
        
        // j letter
        logo.fillRectangle( right - margin - sw * 2 - lw, down - margin - sw - lh * 2 - sw * 2, sw, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 2 - lw, down - margin - sw - lh * 2, sw, lh, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 2 - lw - sw, down - margin - sw * 2 - lh, sw * 2, sw, EngineFrame.BLACK );

        // s letter
        logo.fillRectangle( right - margin - sw * 5, down - margin - sw - lh * 2, sw * 5, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 5, down - margin - lh * 2, sw, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 5, down - margin + sw - lh * 2, sw * 5, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw, down - margin + sw * 2 - lh * 2, sw, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 5, down - margin + sw * 3 - lh * 2, sw * 5, sw, EngineFrame.BLACK );

        // g letter
        logo.fillRectangle( right - margin - sw * 6 - lw, down - margin - lh, sw * 5, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 6 - lw, down - margin + sw - lh, sw, sw * 3, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 2 - lw, down - margin + sw - lh, sw, sw * 5, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 6 - lw, down - margin + sw * 4 - lh, sw * 5, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 6 - lw, down - margin + sw * 6 - lh, sw * 5, sw, EngineFrame.BLACK );

        // e letter
        logo.fillRectangle( right - margin - sw * 5, down - margin - lh, sw * 5, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 5, down - margin + sw - lh, sw, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw, down - margin + sw - lh, sw, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 5, down - margin + sw * 2 - lh, sw * 5, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 5, down - margin + sw * 3 - lh, sw, sw, EngineFrame.BLACK );
        logo.fillRectangle( right - margin - sw * 5, down - margin + sw * 4 - lh, sw * 5, sw, EngineFrame.BLACK );
        
        return logo;
        
    }
    
}
