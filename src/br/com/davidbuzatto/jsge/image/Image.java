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
package br.com.davidbuzatto.jsge.image;

import br.com.davidbuzatto.jsge.collision.aabb.AABB;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.core.utils.StrokeUtils;
import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Path;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Star;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Abstraction for BufferedImages.
 *
 * Contains most, if not all, of the engine's drawing methods;
 * however, drawing is performed on the image itself.
 *
 * Note: All configuration methods act globally, not per instance!
 *
 * @author Prof. Dr. David Buzatto
 */
public class Image {
    
    private static Font font;
    private static BasicStroke stroke;
    private static boolean antialiasing;
    
    static {
        resetFont();
        resetStroke();
        disableAntialiasing();
    }
    
    /**
     * A buffered image holding the actual image data.
     */
    public BufferedImage buffImage;
    
    /**
     * Creates an image from a buffered image.
     *
     * @param buffImage A buffered image.
     */
    public Image( BufferedImage buffImage ) {
        this.buffImage = buffImage;
    }
    
    /**
     * Creates an empty image of the specified size.
     *
     * @param width Width.
     * @param height Height.
     */
    public Image( int width, int height ) {
        this( new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB ) );
    }
    
    /**
     * Gets the width.
     *
     * @return The width.
     */
    public int getWidth() {
        return buffImage.getWidth();
    }
    
    /**
     * Gets the height.
     *
     * @return The height.
     */
    public int getHeight() {
        return buffImage.getHeight();
    }
    
    /**
     * Creates a new graphics context for the internal buffered image.
     *
     * @return A new graphics context.
     */
    public Graphics2D createGraphics() {
        
        Graphics2D g2d = (Graphics2D) buffImage.createGraphics();
        
        if ( antialiasing ) {
            g2d.setRenderingHint( 
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );
        }
        
        g2d.setFont( font );
        g2d.setStroke( stroke );
        
        return g2d;
        
    }
    
    /**
     * Gets the RGB color of a pixel.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return The RGB color encoded as an integer.
     */
    public int getRGB( int x, int y ) {
        return buffImage.getRGB( x, y );
    }
    
    /**
     * Sets the RGB color of a pixel.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param rgb The RGB color encoded as an integer.
     */
    public void setRGB( int x, int y, int rgb ) {
        buffImage.setRGB( x, y, rgb );
    }
    
    
    
    //**************************************************************************
    // Drawing methods
    //**************************************************************************

    /**
     * Draws a pixel.
     *
     * @param x X coordinate of the pixel.
     * @param y Y coordinate of the pixel.
     * @param paint Paint for drawing.
     */
    public void drawPixel( double x, double y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( x, y, x, y ) );
        g2d.dispose();
    }

    /**
     * Draws a pixel.
     *
     * @param point Point of the pixel.
     * @param paint Paint for drawing.
     */
    public void drawPixel( Vector2 point, Paint paint ) {
        drawPixel( point.x, point.y, paint );
    }

    /**
     * Draws a line.
     *
     * @param startX X coordinate of the start point.
     * @param startY Y coordinate of the start point.
     * @param endX X coordinate of the end point.
     * @param endY Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void drawLine( double startX, double startY, double endX, double endY, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( startX, startY, endX, endY ) );
        g2d.dispose();
    }

    /**
     * Draws a line.
     *
     * @param start Start point.
     * @param end End point.
     * @param paint Paint for drawing.
     */
    public void drawLine( Vector2 start, Vector2 end, Paint paint ) {
        drawLine( start.x, start.y, end.x, end.y, paint );
    }

    /**
     * Draws a line.
     *
     * @param line A line.
     * @param paint Paint for drawing.
     */
    public void drawLine( Line line, Paint paint ) {
        drawLine( line.x1, line.y1, line.x2, line.y2, paint );
    }

    /**
     * Draws a rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void drawRectangle( double x, double y, double width, double height, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Draws a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, paint );
    }

    /**
     * Draws a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, Vector2 dim, Paint paint ) {
        drawRectangle( pos.x, pos.y, dim.x, dim.y, paint );
    }
    
    /**
     * Draws a rectangle.
     *
     * @param rectangle A rectangle.
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Rectangle rectangle, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }

    /**
     * Draws a rotated rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );

        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );

        g2d.dispose();

    }

    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, double width, double height, double originX, double originY, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, originX, originY, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, Vector2 dim, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, dim.x, dim.y, origin.x, origin.y, rotation, paint );
    }

    /**
     * Draws a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, Vector2 dim, double originX, double originY, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, dim.x, dim.y, originX, originY, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Rectangle rectangle, double originX, double originY, double rotation, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, originX, originY, rotation, paint );
    }

    /**
     * Fills a rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void fillRectangle( double x, double y, double width, double height, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Fills a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, paint );
    }
    
    /**
     * Fills a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, Vector2 dim, Paint paint ) {
        fillRectangle( pos.x, pos.y, dim.x, dim.y, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, double width, double height, double originX, double originY, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, originX, originY, rotation, paint );
    }

    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, Vector2 dim, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, dim.x, dim.y, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Fills a rectangle.
     *
     * @param rectangle A rectangle.
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Rectangle rectangle, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, Vector2 dim, double originX, double originY, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, dim.x, dim.y, originX, originY, rotation, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );

        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );

        g2d.dispose();

    }

    /**
     * Fills a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Rectangle rectangle, double originX, double originY, double rotation, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, originX, originY, rotation, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Fills a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Clears the image using a color.
     *
     * @param paint Color to use.
     */
    public void clearBackground( Paint paint ) {
        fillRectangle( 0, 0, getWidth(), getHeight(), paint );
    }

    /**
     * Draws an AABB.
     *
     * @param aabb An AABB.
     * @param paint Paint for drawing.
     */
    public void drawAABB( AABB aabb, Paint paint ) {
        drawRectangle( aabb.x1, aabb.y1, aabb.width, aabb.height, paint );
    }
    
    /**
     * Fills an AABB.
     *
     * @param aabb An AABB.
     * @param paint Paint for drawing.
     */
    public void fillAABB( AABB aabb, Paint paint ) {
        fillRectangle( aabb.x1, aabb.y1, aabb.width, aabb.height, paint );
    }
    
    /**
     * Draws a rectangle with rounded corners.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void drawRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
        g2d.dispose();
    }

    /**
     * Draws a rectangle with rounded corners.
     *
     * @param pos Upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void drawRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }

    /**
     * Draws a rectangle with rounded corners.
     *
     * @param roundRectangle A rounded rectangle.
     * @param paint Paint for drawing.
     */
    public void drawRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        drawRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Fills a rectangle with rounded corners.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void fillRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
        g2d.dispose();
    }

    /**
     * Fills a rectangle with rounded corners.
     *
     * @param pos Upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void fillRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }

    /**
     * Fills a rectangle with rounded corners.
     *
     * @param roundRectangle A rounded rectangle.
     * @param paint Paint for drawing.
     */
    public void fillRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        fillRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Draws a circle.
     *
     * @param x X coordinate of the center of the circle.
     * @param y Y coordinate of the center of the circle.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void drawCircle( double x, double y, double radius, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
        g2d.dispose();
    }

    /**
     * Draws a circle.
     *
     * @param center Center of the circle.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void drawCircle( Vector2 center, double radius, Paint paint ) {
        drawCircle( center.x, center.y, radius, paint );
    }

    /**
     * Draws a circle.
     *
     * @param circle A circle.
     * @param paint Paint for drawing.
     */
    public void drawCircle( Circle circle, Paint paint ) {
        drawCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Fills a circle.
     *
     * @param x X coordinate of the center of the circle.
     * @param y Y coordinate of the center of the circle.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void fillCircle( double x, double y, double radius, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
        g2d.dispose();
    }

    /**
     * Fills a circle.
     *
     * @param center Center of the circle.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void fillCircle( Vector2 center, double radius, Paint paint ) {
        fillCircle( center.x, center.y, radius, paint );
    }

    /**
     * Fills a circle.
     *
     * @param circle A circle.
     * @param paint Paint for drawing.
     */
    public void fillCircle( Circle circle, Paint paint ) {
        fillCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Draws an ellipse.
     *
     * @param x X coordinate of the center of the ellipse.
     * @param y Y coordinate of the center of the ellipse.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void drawEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
        g2d.dispose();
    }

    /**
     * Draws an ellipse.
     *
     * @param center Center of the ellipse.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void drawEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Draws an ellipse.
     *
     * @param ellipse An ellipse.
     * @param paint Paint for drawing.
     */
    public void drawEllipse( Ellipse ellipse, Paint paint ) {
        drawEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Fills an ellipse.
     *
     * @param x X coordinate of the ellipse center.
     * @param y Y coordinate of the ellipse center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void fillEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
        g2d.dispose();
    }

    /**
     * Fills an ellipse.
     *
     * @param center Center of the ellipse.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void fillEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Fills an ellipse.
     *
     * @param ellipse An ellipse.
     * @param paint Paint for drawing.
     */
    public void fillEllipse( Ellipse ellipse, Paint paint ) {
        fillEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Draws a circle sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, -startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Draws a circle sector.
     * 
     * @param center Center of the circle sector.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Draws a circle sector.
     * 
     * @param circle A circle.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Draws a circle sector.
     * 
     * @param circleSector A circle sector.
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( CircleSector circleSector, Paint paint ) {
        drawCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Fills a circle sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, -startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Fills a circle sector.
     * 
     * @param center Center of the circle sector.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Fills a circle sector.
     * 
     * @param circle A circle.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Fills a circle sector.
     * 
     * @param circleSector A circle sector.
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( CircleSector circleSector, Paint paint ) {
        fillCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param center Center of the ellipse sector.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param ellipse An ellipse.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param ellipseSector An ellipse sector.
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        drawEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param center Center of the ellipse sector.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param ellipse An ellipse.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param ellipseSector An ellipse sector.
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        fillEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Draws an arc.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.OPEN ) );
        g2d.dispose();
    }

    /**
     * Draws an arc.
     * 
     * @param center Center of the arc.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Draws an arc.
     * 
     * @param arc An arc.
     * @param paint Paint for drawing.
     */
    public void drawArc( Arc arc, Paint paint ) {
        drawArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Fills an arc.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.CHORD ) );
        g2d.dispose();
    }

    /**
     * Fills an arc.
     * 
     * @param center Center of the arc.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Fills an arc.
     * 
     * @param arc An arc.
     * @param paint Paint for drawing.
     */
    public void fillArc( Arc arc, Paint paint ) {
        fillArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Draws a ring.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
        g2d.dispose();
    }

    /**
     * Draws a ring.
     * 
     * @param center Center of the ring.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Draws a ring.
     * 
     * @param ring A ring.
     * @param paint Paint for drawing.
     */
    public void drawRing( Ring ring, Paint paint ) {
        drawRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Fills a ring.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
        g2d.dispose();
    }

    /**
     * Fills a ring.
     * 
     * @param center Center of the ring.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Fills a ring.
     * 
     * @param ring A ring.
     * @param paint Paint for drawing.
     */
    public void fillRing( Ring ring, Paint paint ) {
        fillRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Draws a triangle.
     * 
     * @param v1x X coordinate of the first vertex.
     * @param v1y Y coordinate of the first vertex.
     * @param v2x X coordinate of the second vertex.
     * @param v2y Y coordinate of the second vertex.
     * @param v3x X coordinate of the third vertex.
     * @param v3y Y coordinate of the third vertex.
     * @param paint Paint for drawing.
     */
    public void drawTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
        g2d.dispose();
    }

    /**
     * Draws a triangle.
     * 
     * @param v1 First vertex.
     * @param v2 Second vertex.
     * @param v3 Third vertex.
     * @param paint Paint for drawing.
     */
    public void drawTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Draws a triangle.
     * 
     * @param triangle A triangle.
     * @param paint Paint for drawing.
     */
    public void drawTriangle( Triangle triangle, Paint paint ) {
        drawTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Fills a triangle.
     * 
     * @param v1x X coordinate of the first vertex.
     * @param v1y Y coordinate of the first vertex.
     * @param v2x X coordinate of the second vertex.
     * @param v2y Y coordinate of the second vertex.
     * @param v3x X coordinate of the third vertex.
     * @param v3y Y coordinate of the third vertex.
     * @param paint Paint for drawing.
     */
    public void fillTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
        g2d.dispose();
    }

    /**
     * Fills a triangle.
     * 
     * @param v1 First vertex.
     * @param v2 Second vertex.
     * @param v3 Third vertex.
     * @param paint Paint for drawing.
     */
    public void fillTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Fills a triangle.
     * 
     * @param triangle A triangle.
     * @param paint Paint for drawing.
     */
    public void fillTriangle( Triangle triangle, Paint paint ) {
        fillTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Draws a regular polygon.
     * 
     * @param x X coordinate of the polygon center.
     * @param y Y coordinate of the polygon center.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawPolygon( double x, double y, int sides, double radius, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Draws a regular polygon.
     * 
     * @param center Center of the polygon.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawPolygon( Vector2 center, int sides, double radius, double rotation, Paint paint ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Draws a regular polygon.
     * 
     * @param polygon A regular polygon.
     * @param paint Paint for drawing.
     */
    public void drawPolygon( Polygon polygon, Paint paint ) {
        drawPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Fills a regular polygon.
     * 
     * @param x X coordinate of the polygon center.
     * @param y Y coordinate of the polygon center.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillPolygon( double x, double y, int sides, double radius, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Fills a regular polygon.
     * 
     * @param center Center of the polygon.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillPolygon( Vector2 center, int sides, double radius, double rotation, Paint paint ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Fills a regular polygon.
     * 
     * @param polygon A regular polygon.
     * @param paint Paint for drawing.
     */
    public void fillPolygon( Polygon polygon, Paint paint ) {
        fillPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }
    
    /**
     * Draws a regular star.
     * 
     * @param x X coordinate of the star center.
     * @param y Y coordinate of the star center.
     * @param tips Number of tips.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawStar( double x, double y, int tips, double radius, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createStar( x, y, tips, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Draws a regular star.
     * 
     * @param center Center of the star.
     * @param tips Number of tips.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawStar( Vector2 center, int tips, double radius, double rotation, Paint paint ) {
        drawStar( center.x, center.y, tips, radius, rotation, paint );
    }

    /**
     * Draws a regular star.
     * 
     * @param polygon A regular star.
     * @param paint Paint for drawing.
     */
    public void drawStar( Star polygon, Paint paint ) {
        drawStar( polygon.x, polygon.y, polygon.tips, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Fills a regular star.
     * 
     * @param x X coordinate of the star center.
     * @param y Y coordinate of the star center.
     * @param tips Number of tips.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillStar( double x, double y, int tips, double radius, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createStar( x, y, tips, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Fills a regular star.
     * 
     * @param center Center of the star.
     * @param tips Number of tips.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillStar( Vector2 center, int tips, double radius, double rotation, Paint paint ) {
        fillStar( center.x, center.y, tips, radius, rotation, paint );
    }

    /**
     * Fills a regular star.
     * 
     * @param polygon A regular star.
     * @param paint Paint for drawing.
     */
    public void fillStar( Star polygon, Paint paint ) {
        fillStar( polygon.x, polygon.y, polygon.tips, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Draws a path.
     * 
     * @param path Path to be drawn.
     * @param paint Paint for drawing.
     */
    public void drawPath( Path path, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( path.path );
        g2d.dispose();
    }

    /**
     * Fills a path.
     * 
     * @param path Path to be drawn.
     * @param paint Paint for drawing.
     */
    public void fillPath( Path path, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( path.path );
        g2d.dispose();
    }

    
    
    //**************************************************************************
    // Curve drawing methods.
    //**************************************************************************

    /**
     * Draws a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param cx X coordinate of the control point.
     * @param cy Y coordinate of the control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void drawQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Draws a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c Control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void drawQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }

    /**
     * Draws a quadratic curve (quadratic Bézier curve).
     * 
     * @param quadCurve A quadratic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void drawQuadCurve( QuadCurve quadCurve, Paint paint ) {
        drawQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Fills a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param cx X coordinate of the control point.
     * @param cy Y coordinate of the control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void fillQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Fills a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c Control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void fillQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }

    /**
     * Fills a quadratic curve (quadratic Bézier curve).
     * 
     * @param quadCurve A quadratic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void fillQuadCurve( QuadCurve quadCurve, Paint paint ) {
        fillQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Draws a cubic curve (cubic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param c1x X coordinate of the first control point.
     * @param c1y Y coordinate of the first control point.
     * @param c2x X coordinate of the second control point.
     * @param c2y Y coordinate of the second control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void drawCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Draws a cubic curve (cubic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c1 First control point.
     * @param c2 Second control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void drawCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }

    /**
     * Draws a cubic curve (cubic Bézier curve).
     * 
     * @param cubicCurve A cubic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void drawCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        drawCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }

    /**
     * Fills a cubic curve (cubic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param c1x X coordinate of the first control point.
     * @param c1y Y coordinate of the first control point.
     * @param c2x X coordinate of the second control point.
     * @param c2y Y coordinate of the second control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void fillCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Fills a cubic curve (cubic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c1 First control point.
     * @param c2 Second control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void fillCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }

    /**
     * Fills a cubic curve (cubic Bézier curve).
     * 
     * @param cubicCurve A cubic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void fillCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        fillCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }

    
    
    //**************************************************************************
    // Text drawing methods.
    //**************************************************************************

    /**
     * Draws text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the start of the text drawing.
     * @param y Y coordinate of the start of the text drawing.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }
    
    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the start of the text drawing.
     * @param y Y coordinate of the start of the text drawing.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double rotation, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, paint );
    }

    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the start of the text drawing.
     * @param y Y coordinate of the start of the text drawing.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }

    /**
     * Draws text.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the start of the text drawing.
     * @param y Y coordinate of the start of the text drawing.
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, int fontSize, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }
    
    /**
     * Draws rotated text.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the start of the text drawing.
     * @param y Y coordinate of the start of the text drawing.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double rotation, int fontSize, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, fontSize, paint );
    }
    
    /**
     * Draws rotated text.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the start of the text drawing.
     * @param y Y coordinate of the start of the text drawing.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, int fontSize, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }

    /**
     * Draws text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, Paint paint ) {
        drawText( text, point.x, point.y, paint );
    }
    
    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, paint );
    }

    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param origin Rotation pivot point.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, paint );
    }

    /**
     * Draws text.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, fontSize, paint );
    }
    
    /**
     * Draws rotated text.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, paint );
    }

    /**
     * Draws text.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param origin Rotation pivot point.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, paint );
    }
    
    
    
    //**************************************************************************
    // Methods for drawing images within images.
    //**************************************************************************
    
    /**
     * Draws an image with a background color.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double x, double y, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
        g2d.dispose();
    }
    
    /**
     * Draws an image.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     */
    public void drawImage( Image image, double x, double y ) {
        drawImage( image, x, y, null );
    }
    
    /**
     * Draws a rotated image with a background color.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, x, y, 0, 0, rotation, bgColor );
    }
    
    /**
     * Draws a rotated image.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, double x, double y, double rotation ) {
        drawImage( image, x, y, 0, 0, rotation, null );
    }
    
    /**
     * Draws a rotated image with a background color.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
        g2d.dispose();
    }
    
    /**
     * Draws a rotated image.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, x, y, originX, originY, rotation, null );
    }
    
    /**
     * Draws a cropped region of an image with a background color.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.drawImage( image.buffImage, 
                (int) x, 
                (int) y, 
                (int) ( x + source.width ), 
                (int) ( y + source.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Draws a cropped region of an image.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     */
    public void drawImage( Image image, Rectangle source, double x, double y ) {
        drawImage( image, source, x, y, null );
    }
    
    /**
     * Draws a rotated cropped region of an image with a background color.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, source, x, y, 0, 0, rotation, bgColor );
    }
    
    /**
     * Draws a rotated cropped region of an image.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation ) {
        drawImage( image, source, x, y, 0, 0, rotation, null );
    }
    
    /**
     * Draws a rotated cropped region of an image with a background color.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.drawImage( image.buffImage, 
                (int) x, 
                (int) y, 
                (int) ( x + source.width ), 
                (int) ( y + source.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Draws a rotated cropped region of an image.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, source, x, y, originX, originY, rotation, null );
    }
    
    /**
     * Draws a cropped region of an image into a destination rectangle with a background color.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.drawImage( image.buffImage, 
                (int) dest.x, 
                (int) dest.y, 
                (int) ( dest.x + dest.width ), 
                (int) ( dest.y + dest.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Draws a cropped region of an image into a destination rectangle.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest ) {
        drawImage( image, source, dest, null );
    }
    
    /**
     * Draws a rotated cropped region of an image into a destination rectangle with a background color.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation, Color bgColor ) {
        drawImage( image, source, dest, 0, 0, rotation, bgColor );
    }
    
    /**
     * Draws a rotated cropped region of an image into a destination rectangle.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation ) {
        drawImage( image, source, dest, 0, 0, rotation, null );
    }
    
    /**
     * Draws a rotated cropped region of an image into a destination rectangle with a background color.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.rotate( Math.toRadians( rotation ), dest.x + originX, dest.y + originY );
        g2d.drawImage( image.buffImage, 
                (int) dest.x, 
                (int) dest.y, 
                (int) ( dest.x + dest.width ), 
                (int) ( dest.y + dest.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Draws a rotated cropped region of an image into a destination rectangle.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation ) {
        drawImage( image, source, dest, originX, originY, rotation, null );
    }
    
    
    
    //**************************************************************************
    // Methods for transforming the current image.
    //**************************************************************************
    
    /**
     * Resizes the current image.
     * 
     * @param newImageWidth The new width.
     * @param newImageHeight The new height.
     * @return The current image, allowing chaining.
     */
    public Image resize( int newImageWidth, int newImageHeight ) {
        Image newImage = ImageUtils.imageResize( this, newImageWidth, newImageHeight );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Resizes the current image.
     * 
     * @param newImageWidth The new width. The new height will be calculated
     * proportionally relative to the new width.
     * @return The current image, allowing chaining.
     */
    public Image resize( int newImageWidth ) {
        Image newImage = ImageUtils.imageResize( this, newImageWidth );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Resizes the current image.
     * 
     * @param percentage The resize percentage (0 to 1).
     * @return The current image, allowing chaining.
     */
    public Image resize( double percentage ) {
        Image newImage = ImageUtils.imageResize( this, percentage );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a resized copy of the current image.
     * 
     * @param newImageWidth The new width.
     * @param newImageHeight The new height.
     * @return The resized copy of the current image.
     */
    public Image copyResize( int newImageWidth, int newImageHeight ) {
        return ImageUtils.imageResize( this, newImageWidth, newImageHeight );
    }
    
    /**
     * Creates a resized copy of the current image.
     * 
     * @param newImageWidth The new width. The new height will be calculated
     * proportionally relative to the new width.
     * @return The resized copy of the current image.
     */
    public Image copyResize( int newImageWidth ) {
        return ImageUtils.imageResize( this, newImageWidth );
    }
    
    /**
     * Creates a resized copy of the current image.
     * 
     * @param percentage The resize percentage.
     * @return The resized copy of the current image.
     */
    public Image copyResize( double percentage ) {
        return ImageUtils.imageResize( this, percentage );
    }
    
    /**
     * Flips the current image vertically.
     * 
     * @return The current image, allowing chaining.
     */
    public Image flipVertical() {
        Image newImage = ImageUtils.imageFlipVertical( this );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a vertically flipped copy of the current image.
     * 
     * @return The vertically flipped copy of the current image.
     */
    public Image copyFlipVertical() {
        return ImageUtils.imageFlipVertical( this );
    }
    
    /**
     * Flips the current image horizontally.
     * 
     * @return The current image, allowing chaining.
     */
    public Image flipHorizontal() {
        Image newImage = ImageUtils.imageFlipHorizontal( this );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a horizontally flipped copy of the current image.
     * 
     * @return The horizontally flipped copy of the current image.
     */
    public Image copyFlipHorizontal() {
        return ImageUtils.imageFlipHorizontal( this );
    }
    
    /**
     * Rotates the current image.
     *     
     * @param rotation Rotation in degrees (clockwise).
     * @return The current image, allowing chaining.
     */
    public Image rotate( double rotation ) {
        Image newImage = ImageUtils.imageRotate( this, rotation );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a rotated copy of the current image.
     *     
     * @param rotation Rotation in degrees (clockwise).
     * @return The rotated copy of the current image.
     */
    public Image copyRotate( double rotation ) {
        return ImageUtils.imageRotate( this, rotation );
    }
    
    /**
     * Rotates the current image 90 degrees (clockwise).
     * 
     * @return The current image, allowing chaining.
     */
    public Image rotateCW() {
        Image newImage = ImageUtils.imageRotateCW( this );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a 90-degree clockwise rotated copy of the current image.
     * 
     * @return The 90-degree clockwise rotated copy of the current image.
     */
    public Image copyRotateCW() {
        return ImageUtils.imageRotateCW( this );
    }
    
    /**
     * Rotates the current image 90 degrees (counter-clockwise).
     * 
     * @return The current image, allowing chaining.
     */
    public Image rotateCCW() {
        Image newImage = ImageUtils.imageRotateCCW( this );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a 90-degree counter-clockwise rotated copy of the current image.
     * 
     * @return The 90-degree counter-clockwise rotated copy of the current image.
     */
    public Image copyRotateCCW() {
        return ImageUtils.imageRotateCCW( this );
    }
    
    /**
     * Changes the tint of the current image.
     * 
     * @param color The color used to tint the image.
     * @return The current image, allowing chaining.
     */
    public Image colorTint( Color color ) {
        Image newImage = ImageUtils.imageColorTint( this, color );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a tinted copy of the current image.
     * 
     * @param color The color used to tint the image.
     * @return A tinted copy of the current image.
     */
    public Image copyColorTint( Color color ) {
        return ImageUtils.imageColorTint( this, color );
    }
    
    /**
     * Inverts the colors of the current image.
     * 
     * @return The current image, allowing chaining.
     */
    public Image colorInvert() {
        Image newImage = ImageUtils.imageColorInvert( this );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a color-inverted copy of the current image.
     * 
     * @return A color-inverted copy of the current image.
     */
    public Image copyColorInvert() {
        return ImageUtils.imageColorInvert( this );
    }
    
    /**
     * Converts the colors of the current image to grayscale.
     * 
     * @return The current image, allowing chaining.
     */
    public Image colorGrayscale() {
        Image newImage = ImageUtils.imageColorGrayscale( this );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a grayscale copy of the current image.
     * 
     * @return A grayscale copy of the current image.
     */
    public Image copyColorGrayscale() {
        return ImageUtils.imageColorGrayscale( this );
    }
    
    /**
     * Adjusts the contrast of the current image. The contrast factor ranges
     * from -1.0 to 1.0.
     * 
     * @param contrast The contrast factor from -1.0 to 1.0.
     * @return The current image, allowing chaining.
     */
    public Image colorContrast( double contrast ) {
        Image newImage = ImageUtils.imageColorContrast( this, contrast );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a contrast-adjusted copy of the current image.
     * The contrast factor ranges from -1.0 to 1.0.
     * 
     * @param contrast The contrast factor from -1.0 to 1.0.
     * @return A contrast-adjusted copy of the current image.
     */
    public Image copyColorContrast( double contrast ) {
        return ImageUtils.imageColorContrast( this, contrast );
    }
    
    /**
     * Adjusts the brightness of the current image. The brightness factor ranges from -1.0 to 1.0.
     * 
     * @param brightness The brightness factor from -1.0 to 1.0.
     * @return The current image, allowing chaining.
     */
    public Image colorBrightness( double brightness ) {
        Image newImage = ImageUtils.imageColorBrightness( this, brightness );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a brightness-adjusted copy of the current image.
     * The brightness factor ranges from -1.0 to 1.0.
     * 
     * @param brightness The brightness factor from -1.0 to 1.0.
     * @return A brightness-adjusted copy of the current image.
     */
    public Image copyColorBrightness( double brightness ) {
        return ImageUtils.imageColorBrightness( this, brightness );
    }
    
    /**
     * Replaces one color in the current image with another.
     * 
     * @param color The color to be replaced.
     * @param replace The color that will replace the other color.
     * @return The current image, allowing chaining.
     */
    public Image colorReplace( Color color, Color replace ) {
        Image newImage = ImageUtils.imageColorReplace( this, color, replace );
        buffImage = newImage.buffImage;
        return this;
    }
    
    /**
     * Creates a copy of the current image with one color replaced by another.
     * 
     * @param color The color to be replaced.
     * @param replace The color that will replace the other color.
     * @return A copy of the current image with the colors replaced.
     */
    public Image copyColorReplace( Color color, Color replace ) {
        return ImageUtils.imageColorReplace( this, color, replace );
    }
    
    /**
     * Creates a copy of the current image.
     * 
     * @return A copy of the current image.
     */
    public Image copy() {
        return new Image( ImageUtils.copyBufferedImage( buffImage ) );
    }
    
    
    
    //**************************************************************************
    // Methods for drawing the image on an engine instance.
    //**************************************************************************
    
    /**
     * Draws the current image with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, double x, double y, Color bgColor ) {
        engine.drawImage( this, x, y, bgColor );
    }
    
    /**
     * Draws the current image.
     * 
     * @param engine EngineFrame being used.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     */
    public void draw( EngineFrame engine, double x, double y ) {
        engine.drawImage( this, x, y );
    }
    
    /**
     * Draws the current image rotated with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, double x, double y, double rotation, Color bgColor ) {
        engine.drawImage( this, x, y, rotation, bgColor );
    }
    
    /**
     * Draws the current image rotated.
     * 
     * @param engine EngineFrame being used.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void draw( EngineFrame engine, double x, double y, double rotation ) {
        engine.drawImage( this, x, y, rotation );
    }
    
    /**
     * Draws the current image rotated with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        engine.drawImage( this, x, y, originX, originY, rotation, bgColor );
    }
    
    /**
     * Draws the current image rotated.
     * 
     * @param engine EngineFrame being used.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation ) {
        engine.drawImage( this, x, y, originX, originY, rotation );
    }
    
    /**
     * Draws a cropped region of the current image with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, Rectangle source, double x, double y, Color bgColor ) {
        engine.drawImage( this, source, x, y, bgColor );
    }
    
    /**
     * Draws a cropped region of the current image.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     */
    public void draw( EngineFrame engine, Rectangle source, double x, double y ) {
        engine.drawImage( this, source, x, y );
    }
    
    /**
     * Draws a rotated cropped region of the current image with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, Rectangle source, double x, double y, double rotation, Color bgColor ) {
        engine.drawImage( this, source, x, y, rotation, bgColor );
    }
    
    /**
     * Draws a rotated cropped region of the current image.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void draw( EngineFrame engine, Rectangle source, double x, double y, double rotation ) {
        engine.drawImage( this, source, x, y, rotation );
    }
    
    /**
     * Draws a rotated cropped region of the current image with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, Rectangle source, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        engine.drawImage( this, source, x, y, originX, originY, rotation, bgColor );
    }
    
    /**
     * Draws a rotated cropped region of the current image.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param x X coordinate of the image drawing.
     * @param y Y coordinate of the image drawing.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void draw( EngineFrame engine, Rectangle source, double x, double y, double originX, double originY, double rotation ) {
        engine.drawImage( this, source, x, y, originX, originY, rotation );
    }
    
    /**
     * Draws a cropped region of the current image into a destination rectangle with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, Rectangle source, Rectangle dest, Color bgColor ) {
        engine.drawImage( this, source, dest, bgColor );
    }
    
    /**
     * Draws a cropped region of the current image into a destination rectangle.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     */
    public void draw( EngineFrame engine, Rectangle source, Rectangle dest ) {
        engine.drawImage( this, source, dest );
    }
    
    /**
     * Draws a rotated cropped region of the current image into a destination rectangle with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, Rectangle source, Rectangle dest, double rotation, Color bgColor ) {
        engine.drawImage( this, source, dest, rotation, bgColor );
    }
    
    /**
     * Draws a rotated cropped region of the current image into a destination rectangle.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void draw( EngineFrame engine, Rectangle source, Rectangle dest, double rotation ) {
        engine.drawImage( this, source, dest, rotation );
    }
    
    /**
     * Draws a rotated cropped region of the current image into a destination rectangle with a background color.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void draw( EngineFrame engine, Rectangle source, Rectangle dest, double originX, double originY, double rotation, Color bgColor ) {
        engine.drawImage( this, source, dest, originX, originY, rotation, bgColor );
    }
    
    /**
     * Draws a rotated cropped region of the current image into a destination rectangle.
     * 
     * @param engine EngineFrame being used.
     * @param source A rectangle delimiting the region of the image to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void draw( EngineFrame engine, Rectangle source, Rectangle dest, double originX, double originY, double rotation ) {
        engine.drawImage( this, source, dest, originX, originY, rotation );
    }
    
    
    
    //**************************************************************************
    // Methods for font and stroke management for the image graphics context.
    //**************************************************************************
    
    /**
     * Resets the current font used for drawing processes
     * in the image graphics context.
     * 
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link br.com.davidbuzatto.jsge.core.engine.EngineFrame#FONT_MONOSPACED}</li>
     * <li>Style: {@link br.com.davidbuzatto.jsge.core.engine.EngineFrame#FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     */
    public static void resetFont() {
        font = new Font( Font.MONOSPACED, Font.BOLD, 10 );
    }
    
    /**
     * Sets the name of the current font used for drawing processes
     * in the image graphics context.
     * 
     * @param name The font name.
     */
    public static void setFontName( String name ) {
        font = new Font( name, font.getStyle(), font.getSize() );
    }
    
    /**
     * Sets the style of the current font used for drawing processes
     * in the image graphics context.
     * 
     * @param style The font style.
     */
    public static void setFontStyle( int style ) {
        font = font.deriveFont( style );
    }
    
    /**
     * Sets the size of the current font used for drawing processes
     * in the image graphics context.
     * 
     * @param size The font size.
     */
    public static void setFontSize( int size ) {
        font = font.deriveFont( (float) size );
    }
    
    /**
     * Sets the current font used for drawing processes
     * in the image graphics context.
     * 
     * @param font The new font.
     */
    public static void setFont( Font font ) {
        Image.font = font;
    }
    
    /**
     * Resets the current stroke used for drawing processes
     * in the image graphics context.
     */
    public static void resetStroke() {
        stroke = new BasicStroke( 1 );
    }
    
    /**
     * Sets the current stroke used for drawing processes
     * in the image graphics context.
     * 
     * @param stroke The new stroke.
     */
    public static void setStroke( BasicStroke stroke ) {
        Image.stroke = stroke;
    }
    
    /**
     * Sets the line width of the current stroke used
     * for drawing processes in the image graphics context.
     * 
     * @param lineWidth The line width of the default stroke.
     */
    public static void setStrokeLineWidth( float lineWidth ) {
        stroke = StrokeUtils.cloneStrokeLineWidth( lineWidth, stroke );
    }
    
    /**
     * Sets the end cap style of the current stroke used
     * for drawing processes in the image graphics context.
     * 
     * @param endCap The new end cap style.
     */
    public static void setStrokeEndCap( int endCap ) {
        stroke = StrokeUtils.cloneStrokeEndCap( endCap, stroke );
    }
    
    /**
     * Sets the line join style of the current stroke used
     * for drawing processes in the image graphics context.
     * 
     * @param lineJoin The new line join style.
     */
    public static void setStrokeLineJoin( int lineJoin ) {
        stroke = StrokeUtils.cloneStrokeLineJoin( lineJoin, stroke );
    }
    
    /**
     * Changes the miter limit of the current stroke
     * used for drawing processes in the image graphics context.
     * 
     * @param miterLimit The limit for trimming the miter joint.
     */
    public static void setStrokeMiterLimit( float miterLimit ) {
        stroke = StrokeUtils.cloneStrokeMiterLimit( miterLimit, stroke );
    }
    
    /**
     * Changes the array representing the dash pattern of the current stroke
     * used for drawing processes in the image graphics context.
     * 
     * @param dashArray The array representing the dash pattern.
     */
    public static void setStrokeDashArray( float[] dashArray ) {
        stroke = StrokeUtils.cloneStrokeDashArray( dashArray, stroke );
    }
    
    /**
     * Changes the offset of the start of the dash pattern of the current stroke
     * used for drawing processes in the image graphics context.
     * 
     * @param dashPhase The offset of the start of the dash pattern.
     */
    public static void setStrokeDashPhase( float dashPhase ) {
        stroke = StrokeUtils.cloneStrokeDashPhase( dashPhase, stroke );
    }
    
    /**
     * Changes the dash pattern and offset of the current stroke
     * used for drawing processes in the image graphics context.
     * 
     * @param dashArray The array representing the dash pattern.
     * @param dashPhase The offset of the start of the dash pattern.
     */
    public static void setStrokeDashArrayAndPhase( float[] dashArray, float dashPhase ) {
        stroke = StrokeUtils.cloneStrokeDashArrayAndPhase( dashArray, dashPhase, stroke );
    }
    
    /**
     * Gets the current stroke used for drawing processes
     * in the image graphics context.
     * 
     * @return The current stroke of the graphics context.
     */
    public static BasicStroke getStroke() {
        return stroke;
    }
    
    /**
     * Enables antialiasing for drawing processes in the image
     * graphics context.
     */
    public static void enableAntialiasing() {
        antialiasing = true;
    }
    
    /**
     * Disables antialiasing for drawing processes in the image
     * graphics context.
     */
    public static void disableAntialiasing() {
        antialiasing = false;
    }
    
}
