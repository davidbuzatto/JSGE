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
package br.com.davidbuzatto.jsge.collision;

import br.com.davidbuzatto.jsge.collision.aabb.AABB;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.math.Vector2;

/**
 * Interface with static utility methods for collision detection.
 *
 * Several implementations are based on raylib and its modules
 * (www.raylib.com).
 *
 * @author Prof. Dr. David Buzatto
 */
public interface CollisionUtils {
    
    /**
     * Variation for floating-point values.
     */
    public static final double FLT_EPSILON = 2.2204460492503131e-16;

    /**
     * Checks whether two rectangles have collided.
     *
     * @param rec1 A rectangle.
     * @param rec2 Another rectangle.
     * @return True if the rectangles have collided, false otherwise.
     */
    public static boolean checkCollisionRectangles( Rectangle rec1, Rectangle rec2 ) {
        return ( 
            ( rec1.x < ( rec2.x + rec2.width ) &&  ( rec1.x + rec1.width ) > rec2.x ) &&
            ( rec1.y < ( rec2.y + rec2.height ) && ( rec1.y + rec1.height ) > rec2.y )
        );
    }
    
    /**
     * Checks whether two AABBs have collided.
     *
     * @param aabb1 An AABB.
     * @param aabb2 Another AABB.
     * @return True if both AABBs have collided, false otherwise.
     */
    public static boolean checkCollisionAABBs( AABB aabb1, AABB aabb2 ) {
        return ( 
            ( aabb1.x1 < ( aabb2.x1 + aabb2.width ) &&  ( aabb1.x1 + aabb1.width ) > aabb2.x1 ) &&
            ( aabb1.y1 < ( aabb2.y1 + aabb2.height ) && ( aabb1.y1 + aabb1.height ) > aabb2.y1 )
        );
    }
    
    /**
     * Checks whether a rectangle has collided with an AABB.
     *
     * @param rect A rectangle.
     * @param aabb An AABB.
     * @return True if the rectangle has collided with the AABB, false otherwise.
     */
    public static boolean checkCollisionRectangleAABB( Rectangle rect, AABB aabb ) {
        return ( 
            ( rect.x < ( aabb.x1 + aabb.width ) &&  ( rect.x + rect.width ) > aabb.x1 ) &&
            ( rect.y < ( aabb.y1 + aabb.height ) && ( rect.y + rect.height ) > aabb.y1 )
        );
    }

    /**
     * Checks whether two circles defined by center points and radii have collided.
     *
     * @param center1 Center point of the first circle.
     * @param radius1 Radius of the first circle.
     * @param center2 Center point of the second circle.
     * @param radius2 Radius of the second circle.
     * @return True if the circles have collided, false otherwise.
     */
    public static boolean checkCollisionCircles( Vector2 center1, double radius1, Vector2 center2, double radius2 ) {
        
        double dx = center2.x - center1.x;
        double dy = center2.y - center1.y;
    
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = radius1 + radius2;
    
        return distanceSquared <= radiusSum * radiusSum;

    }

    /**
     * Checks whether two circles have collided.
     *
     * @param circle1 A circle.
     * @param circle2 Another circle.
     * @return True if the circles have collided, false otherwise.
     */
    public static boolean checkCollisionCircles( Circle circle1, Circle circle2 ) {
        return checkCollisionCircles( 
            new Vector2( circle1.x, circle1.y ), circle1.radius,
            new Vector2( circle2.x, circle2.y ), circle2.radius
        );
    }
    
    /**
     * Checks whether a circle, defined by a center point and radius, has collided
     * with a line defined by two points.
     *
     * @param center Center point of the circle.
     * @param radius Radius of the circle.
     * @param p1 Start point of the line.
     * @param p2 End point of the line.
     * @return True if the circle has collided with the line, false otherwise.
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
     * Checks whether a circle has collided with a line defined by two points.
     *
     * @param circle The circle.
     * @param p1 Start point of the line.
     * @param p2 End point of the line.
     * @return True if the circle has collided with the line, false otherwise.
     */
    public static boolean checkCollisionCircleLine( Circle circle, Vector2 p1, Vector2 p2 ) {
        return checkCollisionCircleLine( new Vector2( circle.x, circle.y ), circle.radius, p1, p2 );
    }

    /**
     * Checks whether a circle has collided with a line.
     *
     * @param circle The circle.
     * @param line The line.
     * @return True if the circle has collided with the line, false otherwise.
     */
    public static boolean checkCollisionCircleLine( Circle circle, Line line ) {
        return checkCollisionCircleLine( 
            new Vector2( circle.x, circle.y ), circle.radius,
            new Vector2( line.x1, line.y1 ), 
            new Vector2( line.x2, line.y2 )
        );
    }

    /**
     * Checks whether a circle, defined by a center point and radius, has collided
     * with a rectangle.
     *
     * @param center Center point of the circle.
     * @param radius Radius of the circle.
     * @param rec A rectangle.
     * @return True if the circle has collided with the rectangle, false otherwise.
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
     * Checks whether a circle has collided with a rectangle.
     *
     * @param circle The circle.
     * @param rec The rectangle.
     * @return True if the circle has collided with the rectangle, false otherwise.
     */
    public static boolean checkCollisionCircleRectangle( Circle circle, Rectangle rec ) {
        return checkCollisionCircleRectangle( new Vector2( circle.x, circle.y ), circle.radius, rec );
    }
    
    /**
     * Checks whether a circle, defined by a center point and radius, has collided
     * with an AABB.
     *
     * @param center Center point of the circle.
     * @param radius Radius of the circle.
     * @param aabb The AABB.
     * @return True if the circle has collided with the AABB, false otherwise.
     */
    public static boolean checkCollisionCircleAABB( Vector2 center, double radius, AABB aabb ) {

        double recCenterX = aabb.x1 + aabb.width / 2.0;
        double recCenterY = aabb.y1 + aabb.height / 2.0;

        double dx = Math.abs( center.x - recCenterX );
        double dy = Math.abs( center.y - recCenterY );

        if ( dx > ( aabb.width / 2.0 + radius ) ) {
            return false;
        }
        if ( dy > ( aabb.height / 2.0 + radius ) ) { 
            return false;
        }

        if ( dx <= ( aabb.width / 2.0 ) ) { 
            return true;
        }
        if ( dy <= ( aabb.height / 2.0 ) ) { 
            return true;
        }

        double cornerDistanceSq = ( dx - aabb.width / 2.0 ) * ( dx - aabb.width / 2.0 ) +
                                  ( dy - aabb.height / 2.0 ) * ( dy - aabb.height / 2.0 );

        return cornerDistanceSq <= ( radius * radius );

    }

    /**
     * Checks whether a circle has collided with an AABB.
     *
     * @param circle The circle.
     * @param aabb The AABB.
     * @return True if the circle has collided with the AABB, false otherwise.
     */
    public static boolean checkCollisionCircleAABB( Circle circle, AABB aabb ) {
        return checkCollisionCircleAABB( new Vector2( circle.x, circle.y ), circle.radius, aabb );
    }

    /**
     * Checks whether a point, defined by its coordinates, has collided with a rectangle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param rec The rectangle.
     * @return True if the point has collided with the rectangle, false otherwise.
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
     * Checks whether a point has collided with a rectangle.
     *
     * @param point The point.
     * @param rec The rectangle.
     * @return True if the point has collided with the rectangle, false otherwise.
     */
    public static boolean checkCollisionPointRectangle( Vector2 point, Rectangle rec ) {
        return checkCollisionPointRectangle( point.x, point.y, rec );
    }
    
    /**
     * Checks whether a point, defined by its coordinates, has collided with an AABB.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param aabb The AABB.
     * @return True if the point has collided with the AABB, false otherwise.
     */
    public static boolean checkCollisionPointAABB( double x, double y, AABB aabb ) {
        return ( 
            ( x >= aabb.x1 ) && 
            ( x < ( aabb.x1 + aabb.width ) ) && 
            ( y >= aabb.y1 ) && 
            ( y < ( aabb.y1 + aabb.height ) )
        );
    }

    /**
     * Checks whether a point has collided with an AABB.
     *
     * @param point The point.
     * @param aabb The AABB.
     * @return True if the point has collided with the AABB, false otherwise.
     */
    public static boolean checkCollisionPointAABB( Vector2 point, AABB aabb ) {
        return checkCollisionPointAABB( point.x, point.y, aabb );
    }

    /**
     * Checks whether a point has collided with a circle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param center Center point of the circle.
     * @param radius Radius of the circle.
     * @return True if the point has collided with the circle, false otherwise.
     */
    public static boolean checkCollisionPointCircle( double x, double y, Vector2 center, double radius ) {
        double distanceSquared = ( x - center.x ) * ( x - center.x ) +
                                 ( y - center.y ) * ( y - center.y );
        return distanceSquared <= radius * radius;
    }

    /**
     * Checks whether a point, defined by its coordinates, has collided with a circle
     * defined by its center coordinates and radius.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param cx The x coordinate of the circle center.
     * @param cy The y coordinate of the circle center.
     * @param radius Radius of the circle.
     * @return True if the point has collided with the circle, false otherwise.
     */
    public static boolean checkCollisionPointCircle( double x, double y, double cx, double cy, double radius ) {
        double distanceSquared = ( x - cx ) * ( x - cx ) +
                                 ( y - cy ) * ( y - cy );
        return distanceSquared <= radius * radius;
    }

    /**
     * Checks whether a point has collided with a circle.
     *
     * @param point The point.
     * @param center Center point of the circle.
     * @param radius Radius of the circle.
     * @return True if the point has collided with the circle, false otherwise.
     */
    public static boolean checkCollisionPointCircle( Vector2 point, Vector2 center, double radius ) {
        return checkCollisionPointCircle( point.x, point.y, center, radius );
    }

    /**
     * Checks whether a point has collided with a circle.
     *
     * @param point The point.
     * @param circle The circle.
     * @return True if the point has collided with the circle, false otherwise.
     */
    public static boolean checkCollisionPointCircle( Vector2 point, Circle circle ) {
        return checkCollisionPointCircle( point.x, point.y, new Vector2( circle.x, circle.y ), circle.radius );
    }

    /**
     * Checks whether a point has collided with a triangle.
     *
     * @param point The point.
     * @param p1 First vertex point of the triangle.
     * @param p2 Second vertex point of the triangle.
     * @param p3 Third vertex point of the triangle.
     * @return True if the point has collided with the triangle, false otherwise.
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
     * Checks whether a point has collided with a triangle.
     *
     * @param point The point.
     * @param triangle The triangle.
     * @return True if the point has collided with the triangle, false otherwise.
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
     * Checks whether a point, defined by its coordinates, has collided with a triangle.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param triangle The triangle.
     * @return True if the point has collided with the triangle, false otherwise.
     */
    public static boolean checkCollisionPointTriangle( double x, double y, Triangle triangle ) {
        return checkCollisionPointTriangle( new Vector2( x, y ), triangle );
    }

    /**
     * Checks whether a point, defined by its coordinates, has collided with a triangle
     * defined by its three vertex coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param p1x The x coordinate of the first vertex of the triangle.
     * @param p1y The y coordinate of the first vertex of the triangle.
     * @param p2x The x coordinate of the second vertex of the triangle.
     * @param p2y The y coordinate of the second vertex of the triangle.
     * @param p3x The x coordinate of the third vertex of the triangle.
     * @param p3y The y coordinate of the third vertex of the triangle.
     * @return True if the point has collided with the triangle, false otherwise.
     */
    public static boolean checkCollisionPointTriangle( double x, double y, double p1x, double p1y, double p2x, double p2y, double p3x, double p3y ) {
        return checkCollisionPointTriangle( new Vector2( x, y ), new Vector2( p1x, p1y ), new Vector2( p2x, p2y ), new Vector2( p3x, p3y ) );
    }

    /**
     * Checks whether a point has collided with a regular polygon.
     *
     * @param point The point.
     * @param polygon The polygon.
     * @return True if the point has collided with the polygon, false otherwise.
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
     * Checks whether a point, defined by its coordinates, has collided with a regular polygon.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param polygon The polygon.
     * @return True if the point has collided with the polygon, false otherwise.
     */
    public static boolean checkCollisionPointPolygon( double x, double y, Polygon polygon ) {
        return checkCollisionPointPolygon( new Vector2( x, y ), polygon );
    }

    /**
     * Checks the collision between two lines.
     *
     * @param startPos1 Start point of the first line.
     * @param endPos1 End point of the first line.
     * @param startPos2 Start point of the second line.
     * @param endPos2 End point of the second line.
     * @return Returns the collision point if the lines intersected, or null otherwise.
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
     * Checks the collision between two lines.
     *
     * @param line1 A line.
     * @param line2 Another line.
     * @return Returns the collision point if the lines intersected, or null otherwise.
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
     * Checks the collision of a point with a line.
     *
     * @param point The point.
     * @param p1 The start point of the line.
     * @param p2 The end point of the line.
     * @param threshold The proximity threshold between the point and the line.
     * @return True if the point has collided with the line, false otherwise.
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
     * Checks the collision of a point with a line.
     *
     * @param point The point.
     * @param line The line.
     * @param threshold The proximity threshold between the point and the line.
     * @return True if the point has collided with the line, false otherwise.
     */
    public static boolean checkCollisionPointLine( Vector2 point, Line line, int threshold ) {
        return checkCollisionPointLine( point, new Vector2( line.x1, line.y1 ), new Vector2( line.x2, line.y2 ), threshold );
    }

    /**
     * Checks the collision of a point, defined by its coordinates, with a line.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param line The line.
     * @param threshold The proximity threshold between the point and the line.
     * @return True if the point has collided with the line, false otherwise.
     */
    public static boolean checkCollisionPointLine( double x, double y, Line line, int threshold ) {
        return checkCollisionPointLine( new Vector2( x, y ), line, threshold );
    }

    /**
     * Checks the collision of a point, defined by its coordinates, with a line
     * defined by its two endpoint coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param p1x The x coordinate of the start point of the line.
     * @param p1y The y coordinate of the start point of the line.
     * @param p2x The x coordinate of the end point of the line.
     * @param p2y The y coordinate of the end point of the line.
     * @param threshold The proximity threshold between the point and the line.
     * @return True if the point has collided with the line, false otherwise.
     */
    public static boolean checkCollisionPointLine( double x, double y, double p1x, double p1y, double p2x, double p2y, int threshold ) {
        return checkCollisionPointLine( new Vector2( x, y ), new Vector2( p1x, p1y ), new Vector2( p2x, p2y ), threshold );
    }

    /**
     * Computes the overlap rectangle between two rectangles.
     *
     * @param rec1 A rectangle.
     * @param rec2 Another rectangle.
     * @return The overlap rectangle between the two rectangles.
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
    
    /**
     * Computes the overlap rectangle between two AABBs.
     *
     * @param aabb1 An AABB.
     * @param aabb2 Another AABB.
     * @return The overlap rectangle between the two AABBs.
     */
    public static Rectangle getCollisionRectangle( AABB aabb1, AABB aabb2 ) {
        
        Rectangle overlap = new Rectangle();

        double left = ( aabb1.x1 > aabb2.x1 ) ? aabb1.x1 : aabb2.x1;
        double right1 = aabb1.x1 + aabb1.width;
        double right2 = aabb2.x1 + aabb2.width;
        double right = ( right1 < right2 ) ? right1 : right2;
        double top = ( aabb1.y1 > aabb2.y1 ) ? aabb1.y1 : aabb2.y1;
        double bottom1 = aabb1.y1 + aabb1.height;
        double bottom2 = aabb2.y1 + aabb2.height;
        double bottom = ( bottom1 < bottom2 ) ? bottom1 : bottom2;

        if ( ( left < right ) && ( top < bottom ) ) {
            overlap.x = left;
            overlap.y = top;
            overlap.width = right - left;
            overlap.height = bottom - top;
        }

        return overlap;

    }
    
    /**
     * Computes the overlap rectangle between a rectangle and an AABB.
     *
     * @param rect A rectangle.
     * @param aabb An AABB.
     * @return The overlap rectangle between a rectangle and an AABB.
     */
    public static Rectangle getCollisionRectangle( Rectangle rect, AABB aabb ) {
        
        Rectangle overlap = new Rectangle();

        double left = ( rect.x > aabb.x1 ) ? rect.x : aabb.x1;
        double right1 = rect.x + rect.width;
        double right2 = aabb.x1 + aabb.width;
        double right = ( right1 < right2 ) ? right1 : right2;
        double top = ( rect.y > aabb.y1 ) ? rect.y : aabb.y1;
        double bottom1 = rect.y + rect.height;
        double bottom2 = aabb.y1 + aabb.height;
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
