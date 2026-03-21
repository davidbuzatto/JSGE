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

import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

/**
 * Interface with static utility methods for creating various types of Paints.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface PaintUtils {

    /**
     * Enumeration for gradient cycle types.
     */
    public static enum CycleMethod {

        /**
         * No cycle.
         */
        NO_CYCLE( MultipleGradientPaint.CycleMethod.NO_CYCLE ),

        /**
         * Reflection cycle.
         */
        REFLECT( MultipleGradientPaint.CycleMethod.REFLECT ),

        /**
         * Repeat cycle.
         */
        REPEAT( MultipleGradientPaint.CycleMethod.REPEAT );
        
        private final MultipleGradientPaint.CycleMethod method;
        
        private CycleMethod( MultipleGradientPaint.CycleMethod method ) {
            this.method = method;
        }
        
    }
    
    
    
    //**************************************************************************
    // Gradient paints (GradientPaint).
    //**************************************************************************
    /**
     * Creates a two-color gradient paint with start and end points.
     *
     * @param startX X coordinate of the start point.
     * @param startY Y coordinate of the start point.
     * @param endX X coordinate of the end point.
     * @param endY Y coordinate of the end point.
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A gradient paint.
     */
    public static Paint getGradientPaint( double startX, double startY, double endX, double endY, Color startColor, Color endColor, boolean cyclic ) {
        return new GradientPaint( (float) startX, (float) startY, startColor, (float) endX, (float) endY, endColor, cyclic );
    }
    
    /**
     * Creates an acyclic two-color gradient paint with start and end points.
     *
     * @param startX X coordinate of the start point.
     * @param startY Y coordinate of the start point.
     * @param endX X coordinate of the end point.
     * @param endY Y coordinate of the end point.
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic gradient paint.
     */
    public static Paint getGradientPaint( double startX, double startY, double endX, double endY, Color startColor, Color endColor ) {
        return new GradientPaint( (float) startX, (float) startY, startColor, (float) endX, (float) endY, endColor, false );
    }
    
    /**
     * Creates a two-color gradient paint with start and end points.
     *
     * @param start Start point.
     * @param end End point.
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A gradient paint.
     */
    public static Paint getGradientPaint( Vector2 start, Vector2 end, Color startColor, Color endColor, boolean cyclic ) {
        return getGradientPaint( start.x, start.y, end.x, end.y, startColor, endColor, cyclic );
    }
    
    /**
     * Creates an acyclic two-color gradient paint with start and end points.
     *
     * @param start Start point.
     * @param end End point.
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic gradient paint.
     */
    public static Paint getGradientPaint( Vector2 start, Vector2 end, Color startColor, Color endColor ) {
        return getGradientPaint( start.x, start.y, end.x, end.y, startColor, endColor, false );
    }
    
    
    
    //**************************************************************************
    // Horizontal gradient paints (GradientPaint).
    //**************************************************************************
    /**
     * Creates a horizontal two-color gradient.
     *
     * @param x X coordinate of the start point.
     * @param y Y coordinate of the start point.
     * @param width Width.
     * @param height Height.
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A horizontal two-color gradient.
     */
    public static Paint getHorizontalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor, boolean cyclic ) {
        return new GradientPaint( (float) x, (float) (y + height / 2), startColor, (float) (x + width), (float) (y + height / 2), endColor, cyclic );
    }
    
    /**
     * Creates an acyclic horizontal two-color gradient.
     *
     * @param x X coordinate of the start point.
     * @param y Y coordinate of the start point.
     * @param width Width.
     * @param height Height.
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic horizontal two-color gradient.
     */
    public static Paint getHorizontalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor ) {
        return getHorizontalGradientPaint( x, y, width, height, startColor, endColor, false );
    }
    
    /**
     * Creates a horizontal two-color gradient.
     *
     * @param pos Start point.
     * @param dim Gradient dimensions (width and height).
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A horizontal two-color gradient.
     */
    public static Paint getHorizontalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor, boolean cyclic ) {
        return getHorizontalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, cyclic );
    }
    
    /**
     * Creates an acyclic horizontal two-color gradient.
     *
     * @param pos Start point.
     * @param dim Gradient dimensions (width and height).
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic horizontal two-color gradient.
     */
    public static Paint getHorizontalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor ) {
        return getHorizontalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, false );
    }
    
    /**
     * Creates a horizontal two-color gradient.
     *
     * @param rect The gradient rectangle.
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A horizontal two-color gradient.
     */
    public static Paint getHorizontalGradientPaint( Rectangle rect, Color startColor, Color endColor, boolean cyclic ) {
        return getHorizontalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, cyclic );
    }
    
    /**
     * Creates an acyclic horizontal two-color gradient.
     *
     * @param rect The gradient rectangle.
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic horizontal two-color gradient.
     */
    public static Paint getHorizontalGradientPaint( Rectangle rect, Color startColor, Color endColor ) {
        return getHorizontalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, false );
    }
    
    
    
    //**************************************************************************
    // Vertical gradient paints (GradientPaint).
    //**************************************************************************
    /**
     * Creates a vertical two-color gradient.
     *
     * @param x X coordinate of the start point.
     * @param y Y coordinate of the start point.
     * @param width Width.
     * @param height Height.
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A vertical two-color gradient.
     */
    public static Paint getVerticalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor, boolean cyclic ) {
        return new GradientPaint( (float) (x + width / 2), (float) y, startColor, (float) (x + width / 2), (float) (y + height), endColor, cyclic );
    }
    
    /**
     * Creates an acyclic vertical two-color gradient.
     *
     * @param x X coordinate of the start point.
     * @param y Y coordinate of the start point.
     * @param width Width.
     * @param height Height.
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic vertical two-color gradient.
     */
    public static Paint getVerticalGradientPaint( double x, double y, double width, double height, Color startColor, Color endColor ) {
        return getVerticalGradientPaint( x, y, width, height, startColor, endColor, false );
    }
    
    /**
     * Creates a vertical two-color gradient.
     *
     * @param pos Start point.
     * @param dim Gradient dimensions (width and height).
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A vertical two-color gradient.
     */
    public static Paint getVerticalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor, boolean cyclic ) {
        return getVerticalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, cyclic );
    }
    
    /**
     * Creates an acyclic vertical two-color gradient.
     *
     * @param pos Start point.
     * @param dim Gradient dimensions (width and height).
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic vertical two-color gradient.
     */
    public static Paint getVerticalGradientPaint( Vector2 pos, Vector2 dim, Color startColor, Color endColor ) {
        return getVerticalGradientPaint( pos.x, pos.y, dim.x, dim.y, startColor, endColor, false );
    }
    
    /**
     * Creates a vertical two-color gradient.
     *
     * @param rect The gradient rectangle.
     * @param startColor Start color.
     * @param endColor End color.
     * @param cyclic Whether the gradient is cyclic.
     * @return A vertical two-color gradient.
     */
    public static Paint getVerticalGradientPaint( Rectangle rect, Color startColor, Color endColor, boolean cyclic ) {
        return getVerticalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, cyclic );
    }
    
    /**
     * Creates an acyclic vertical two-color gradient.
     *
     * @param rect The gradient rectangle.
     * @param startColor Start color.
     * @param endColor End color.
     * @return An acyclic vertical two-color gradient.
     */
    public static Paint getVerticalGradientPaint( Rectangle rect, Color startColor, Color endColor ) {
        return getVerticalGradientPaint( rect.x, rect.y, rect.width, rect.height, startColor, endColor, false );
    }
    
    
    
    //**************************************************************************
    // Linear gradient paints (LinearGradientPaint).
    //**************************************************************************
    /**
     * Creates a multi-color linear gradient.
     *
     * @param startX X coordinate of the start point.
     * @param startY Y coordinate of the start point.
     * @param endX X coordinate of the end point.
     * @param endY Y coordinate of the end point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A linear gradient.
     */
    public static Paint getLinearGradientPaint( double startX, double startY, double endX, double endY, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new LinearGradientPaint( (float) startX, (float) startY, (float) endX, (float) endY, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color linear gradient.
     *
     * @param startX X coordinate of the start point.
     * @param startY Y coordinate of the start point.
     * @param endX X coordinate of the end point.
     * @param endY Y coordinate of the end point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic linear gradient.
     */
    public static Paint getLinearGradientPaint( double startX, double startY, double endX, double endY, float[] fractions, Color[] colors ) {
        return getLinearGradientPaint( startX, startY, endX, endY, fractions, colors, CycleMethod.NO_CYCLE );
    }
    
    /**
     * Creates a multi-color linear gradient.
     *
     * @param start Start point.
     * @param end End point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A linear gradient.
     */
    public static Paint getLinearGradientPaint( Vector2 start, Vector2 end, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return getLinearGradientPaint( start.x, start.y, end.x, end.y, fractions, colors, cycleMethod );
    }
    
    /**
     * Creates an acyclic multi-color linear gradient.
     *
     * @param start Start point.
     * @param end End point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic linear gradient.
     */
    public static Paint getLinearGradientPaint( Vector2 start, Vector2 end, float[] fractions, Color[] colors ) {
        return getLinearGradientPaint( start.x, start.y, end.x, end.y, fractions, colors, CycleMethod.NO_CYCLE );
    }
    
    
    
    //**************************************************************************
    // Radial gradient paints (RadialGradientPaint).
    //**************************************************************************
    /**
     * Creates a multi-color radial gradient.
     *
     * @param cx X coordinate of the center.
     * @param cy Y coordinate of the center.
     * @param radius Radius.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A radial gradient.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color radial gradient.
     *
     * @param cx X coordinate of the center.
     * @param cy Y coordinate of the center.
     * @param radius Radius.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic radial gradient.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, fractions, colors );
    }
    
    /**
     * Creates a multi-color radial gradient.
     *
     * @param cx X coordinate of the center.
     * @param cy Y coordinate of the center.
     * @param radius Radius.
     * @param fx X coordinate of the focus.
     * @param fy Y coordinate of the focus.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A radial gradient.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, double fx, double fy, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, (float) fx, (float) fy, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color radial gradient.
     *
     * @param cx X coordinate of the center.
     * @param cy Y coordinate of the center.
     * @param radius Radius.
     * @param fx X coordinate of the focus.
     * @param fy Y coordinate of the focus.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic radial gradient.
     */
    public static Paint getRadialGradientPaint( double cx, double cy, double radius, double fx, double fy, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) cx, (float) cy, (float) radius, (float) fx, (float) fy, fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    /**
     * Creates a multi-color radial gradient.
     *
     * @param center Center.
     * @param radius Radius.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A radial gradient.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) center.x, (float) center.x, (float) radius, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color radial gradient.
     *
     * @param center Center.
     * @param radius Radius.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic radial gradient.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) center.x, (float) center.x, (float) radius, fractions, colors );
    }
    
    /**
     * Creates a multi-color radial gradient.
     *
     * @param center Center.
     * @param radius Radius.
     * @param focus Focus point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A radial gradient.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, Vector2 focus, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) center.x, (float) center.y, (float) radius, (float) focus.x, (float) focus.y, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color radial gradient.
     *
     * @param center Center.
     * @param radius Radius.
     * @param focus Focus point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic radial gradient.
     */
    public static Paint getRadialGradientPaint( Vector2 center, double radius, Vector2 focus, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) center.x, (float) center.y, (float) radius, (float) focus.x, (float) focus.y, fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    /**
     * Creates a multi-color radial gradient.
     *
     * @param circle Circle.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A radial gradient.
     */
    public static Paint getRadialGradientPaint( Circle circle, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color radial gradient.
     *
     * @param circle Circle.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic radial gradient.
     */
    public static Paint getRadialGradientPaint( Circle circle, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, fractions, colors );
    }
    
    /**
     * Creates a multi-color radial gradient.
     *
     * @param circle Circle.
     * @param focus Focus point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A radial gradient.
     */
    public static Paint getRadialGradientPaint( Circle circle, Vector2 focus, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, (float) focus.x, (float) focus.y, fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color radial gradient.
     *
     * @param circle Circle.
     * @param focus Focus point.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic radial gradient.
     */
    public static Paint getRadialGradientPaint( Circle circle, Vector2 focus, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( (float) circle.x, (float) circle.x, (float) circle.radius, (float) focus.x, (float) focus.y, fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    /**
     * Creates a multi-color radial gradient.
     *
     * @param gradientBounds Rectangle defining the gradient bounds.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @param cycleMethod The type of cycle to use.
     * @return A radial gradient.
     */
    public static Paint getRadialGradientPaint( Rectangle gradientBounds, float[] fractions, Color[] colors, CycleMethod cycleMethod ) {
        return new RadialGradientPaint( new Rectangle2D.Double( gradientBounds.x, gradientBounds.y, gradientBounds.width, gradientBounds.height ), fractions, colors, cycleMethod.method );
    }
    
    /**
     * Creates an acyclic multi-color radial gradient.
     *
     * @param gradientBounds Rectangle defining the gradient bounds.
     * @param fractions Percentage positions of the color boundaries.
     * @param colors Colors.
     * @return An acyclic radial gradient.
     */
    public static Paint getRadialGradientPaint( Rectangle gradientBounds, float[] fractions, Color[] colors ) {
        return new RadialGradientPaint( new Rectangle2D.Double( gradientBounds.x, gradientBounds.y, gradientBounds.width, gradientBounds.height ), fractions, colors, CycleMethod.NO_CYCLE.method );
    }
    
    
    
    //**************************************************************************
    // Image paints (TexturePaint).
    //**************************************************************************
    /**
     * Creates an image paint.
     *
     * @param image Image.
     * @param anchor Rectangle representing the anchor.
     * @return An image paint.
     */
    public static Paint getImagePaint( Image image, Rectangle anchor ) {
        return new TexturePaint( image.buffImage, new Rectangle2D.Double( anchor.x, anchor.y, anchor.width, anchor.height ) );
    }
    
    /**
     * Creates an image paint.
     *
     * @param image Image.
     * @param anchorPos Position of the rectangle representing the anchor.
     * @param anchorDim Dimensions of the rectangle representing the anchor.
     * @return An image paint.
     */
    public static Paint getImagePaint( Image image, Vector2 anchorPos, Vector2 anchorDim ) {
        return new TexturePaint( image.buffImage, new Rectangle2D.Double( anchorPos.x, anchorPos.y, anchorDim.x, anchorDim.y ) );
    }
    
    /**
     * Creates an image paint.
     *
     * @param image Image.
     * @param anchorX X coordinate of the rectangle representing the anchor.
     * @param anchorY Y coordinate of the rectangle representing the anchor.
     * @param anchorWidth Width of the rectangle representing the anchor.
     * @param anchorHeight Height of the rectangle representing the anchor.
     * @return An image paint.
     */
    public static Paint getImagePaint( Image image, double anchorX, double anchorY, double anchorWidth, double anchorHeight ) {
        return new TexturePaint( image.buffImage, new Rectangle2D.Double( anchorX, anchorY, anchorWidth, anchorHeight ) );
    }
    
}
