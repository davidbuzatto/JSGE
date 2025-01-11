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
package br.com.davidbuzatto.jsge.examples.basic;

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.collision.aabb.AABB;
import br.com.davidbuzatto.jsge.collision.aabb.AABBQuadtree;
import br.com.davidbuzatto.jsge.collision.aabb.AABBQuadtreeNode;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.math.CurveUtils;
import br.com.davidbuzatto.jsge.math.MathUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Exemplos de utilização dos métodos de detecção de colisão e de pontos
 * em linhas e curvas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CollisionDetectionExample extends EngineFrame {
    
    private double xOffset;
    private double yOffset;
    
    private Line line;
    private Rectangle rectangle;
    private Circle circle;
    private Triangle triangle;
    private Polygon polygon;

    private Line moveableLine;
    private Color moveableLineColor;
    private boolean mlDragging;

    private Rectangle moveableRect;
    private Color moveableRectColor;
    private boolean mrDragging;

    private Circle moveableCircle;
    private Color moveableCircleColor;
    private boolean mcDragging;
    
    private Color noOverlapColor = RAYWHITE;
    private Color overlapColor = GOLD;
    private Rectangle overlapRec;
    private Vector2 lineCollisionPoint;

    private Line lineForPoint;
    private QuadCurve quadForPoint;
    private CubicCurve cubicForPoint;
    private Vector2 pointForLine;
    private Vector2 pointForQuad;
    private Vector2 pointForCubic;

    private double amount;
    private double amountVel;

    private String textPointGeom;
    private String textLineGeom;
    private String textRectGeom;
    private String textCircleGeom;
    
    private Color qnOutlineColor;
    private Color commonQnColor;
    private Color aabbColor;
    private Color aabbNearbyColor;
    private Color aabbOverlapColor;
    
    private int qtX;
    private int qtY;
    private int qtWidth;
    private int qtHeight;
    private int numberOfAABBs;
    private int maxTreeDepth;
    
    private List<AABB> aabbs;
    private Vector2[] vels;
    private AABBQuadtree quadtree;
    private List<Rectangle> overlaps;
    
    /**
     * Cria o exemplo.
     */
    public CollisionDetectionExample() {
        super( 800, 710, "Collision Detection, Points at Lines and Curves and AABBQuadtree", 60, true );
    }
    
    @Override
    public void create() {
        
        setDefaultFontSize( 20 );
        amountVel = 1;
        
        line = new Line( 15, 160, 85, 230 );
        rectangle = new Rectangle( 25, 240, 50, 80 );
        circle = new Circle( 50, 360, 30 );
        triangle = new Triangle( 50, 400, 90, 470, 10, 470 );
        polygon = new Polygon( 50, 530, 5, 45, 0 );

        moveableLine = new Line( 150, 230, 220, 160 );
        moveableRect = new Rectangle( 135, 255, 100, 50 );
        moveableCircle = new Circle( 185, 350, 30 );

        lineForPoint = new Line( 10, 580, 240, 700 ) ;
        quadForPoint = new QuadCurve( 280, 580, 430, 580, 520, 700 ) ;
        cubicForPoint = new CubicCurve( 560, 580, 635, 580, 715, 700, 790, 700 );

        textPointGeom = "none";
        textLineGeom = "none";
        textRectGeom = "none";
        textCircleGeom = "none";
        
        // quadtree
        qtX = 260;
        qtY = 30;
        qtWidth = getScreenWidth() - qtX - 10;
        qtHeight = qtWidth;
        
        qnOutlineColor = new Color( 0, 0, 0 );
        commonQnColor = ColorUtils.fade( LIGHTGRAY, 0.5 );
        
        aabbColor = ColorUtils.fade( GOLD, 0.7 );
        aabbNearbyColor = ColorUtils.fade( LIME, 0.7 );
        aabbOverlapColor = ColorUtils.fade( BLUE, 0.7 );
        
        numberOfAABBs = 50;
        maxTreeDepth = 5;

        initAABBs();
        quadtree = new AABBQuadtree( aabbs, qtWidth, qtHeight, maxTreeDepth );
        
        overlaps = new CopyOnWriteArrayList<>();
        
    }
    
    @Override
    public void update( double delta ) {
        
        Vector2 mousePos = getMousePositionPoint();
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            
            if ( CollisionUtils.checkCollisionPointLine( mousePos, line, 5 ) ) {
                textPointGeom = "line!";
            } else if ( CollisionUtils.checkCollisionPointRectangle( mousePos, rectangle ) ) {
                textPointGeom = "rectangle!";
            } else if ( CollisionUtils.checkCollisionPointCircle( mousePos, circle ) ) {
                textPointGeom = "circle!";
            } else if ( CollisionUtils.checkCollisionPointTriangle( mousePos, triangle ) ) {
                textPointGeom = "triangle!";
            } else if ( CollisionUtils.checkCollisionPointPolygon( mousePos, polygon ) ) {
                textPointGeom = "polygon!";
            } else {
                textPointGeom = "none";
            }

            if ( CollisionUtils.checkCollisionPointLine( mousePos, moveableLine, 10 ) ) {
                mlDragging = true;
                xOffset = moveableLine.x1 - mousePos.x;
                yOffset = moveableLine.y1 - mousePos.y;
            }

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, moveableRect ) ) {
                mrDragging = true;
                xOffset = moveableRect.x - mousePos.x;
                yOffset = moveableRect.y - mousePos.y;
            }

            if ( CollisionUtils.checkCollisionPointCircle( mousePos, moveableCircle ) ) {
                mcDragging = true;
                xOffset = moveableCircle.x - mousePos.x;
                yOffset = moveableCircle.y - mousePos.y;
            }

        }

        if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) {
            mlDragging = false;
            mrDragging = false;
            mcDragging = false;
        }

        amount += amountVel * delta;
        if ( amount < 0.0 ) {
            amountVel = -amountVel;
            amount = 0.0;
        } else if ( amount > 1.0 ) {
            amountVel = -amountVel;
            amount = 1.0;
        }

        pointForLine = CurveUtils.getPointAtLine( lineForPoint, amount );
        pointForQuad = CurveUtils.getPointAtQuadCurve( quadForPoint, amount );
        pointForCubic = CurveUtils.getPointAtCubicCurve( cubicForPoint, amount );

        if ( mlDragging && mousePos != null ) {
            double difX = moveableLine.x2 - moveableLine.x1;
            double difY = moveableLine.y2 - moveableLine.y1;
            moveableLine.x1 = mousePos.x + xOffset;
            moveableLine.y1 = mousePos.y + yOffset;
            moveableLine.x2 = mousePos.x + difX + xOffset;
            moveableLine.y2 = mousePos.y + difY + yOffset;
        } else if ( mrDragging && mousePos != null ) {
            moveableRect.x = mousePos.x + xOffset;
            moveableRect.y = mousePos.y + yOffset;
        } else if ( mcDragging && mousePos != null ) {
            moveableCircle.x = mousePos.x + xOffset;
            moveableCircle.y = mousePos.y + yOffset;
        }

        lineCollisionPoint = CollisionUtils.checkCollisionLines( moveableLine, line );

        if ( lineCollisionPoint != null ) {
            textLineGeom = "line!";
            moveableLineColor = overlapColor;
        } else {
            moveableLineColor = BLACK;
            textLineGeom = "none";
        }

        if ( CollisionUtils.checkCollisionRectangles( moveableRect, rectangle ) ) {
            textRectGeom = "rectangle!";
            moveableRectColor = overlapColor;
            overlapRec = CollisionUtils.getCollisionRectangle( moveableRect, rectangle );
        } else {
            moveableRectColor = noOverlapColor;
            overlapRec = null;
            textRectGeom = "none";
        }

        if ( CollisionUtils.checkCollisionCircleLine( moveableCircle, line ) ) {
            textCircleGeom = "line!";
            moveableCircleColor = overlapColor;
        } else if ( CollisionUtils.checkCollisionCircleRectangle( moveableCircle, rectangle ) ) {
            textCircleGeom = "rectangle!";
            moveableCircleColor = overlapColor;
        } else if ( CollisionUtils.checkCollisionCircles( moveableCircle, circle ) ) {
            textCircleGeom = "circle!";
            moveableCircleColor = overlapColor;
        } else {
            textCircleGeom = "none";
            moveableCircleColor = noOverlapColor;
        }
        
        // quadtree
        updateAABBLocations( delta );
        quadtree.update();
        
    }
    
    @Override
    public void draw() {

        clearBackground( WHITE );
        setFontStyle( FONT_BOLD );
        
        line.draw( this, BLACK );
        
        rectangle.fill( this, BLUE );
        rectangle.draw( this, BLACK );
        
        circle.fill( this, MAROON );
        circle.draw( this, BLACK );
        
        triangle.fill( this, ORANGE );
        triangle.draw( this, BLACK );
        
        polygon.fill( this, LIME );
        polygon.draw( this, BLACK );

        moveableLine.draw( this, moveableLineColor );
        drawText( "move me!", moveableLine.x1 + 20, moveableLine.y1 - 10, -45, 14, BLACK );
        
        moveableRect.fill( this, moveableRectColor );
        moveableRect.draw( this, BLACK );
        drawText( "move me!", moveableRect.x + 20, moveableRect.y + 20, 14, BLACK );

        moveableCircle.fill( this, moveableCircleColor );
        moveableCircle.draw( this, BLACK );
        drawText( "move\nme!", moveableCircle.x - 15, moveableCircle.y - 14, 14, BLACK );

        if ( overlapRec != null ) {
            overlapRec.fill( this, PINK );
            overlapRec.draw( this, BLACK );
        }

        if ( lineCollisionPoint != null ) {
            fillCircle( lineCollisionPoint, 10, VIOLET );
            drawCircle( lineCollisionPoint, 10, BLACK );
        }

        lineForPoint.draw( this, BLACK );
        quadForPoint.draw( this, BLACK );
        cubicForPoint.draw( this, BLACK );

        fillCircle( pointForLine, 10, RED );
        fillCircle( pointForQuad, 10, GREEN );
        fillCircle( pointForCubic, 10, BLUE );

        drawText( " Point x Geom: " + textPointGeom, 10, 40, BLACK );
        drawText( "  Line x Geom: " + textLineGeom, 10, 70, BLACK );
        drawText( "  Rect x Geom: " + textRectGeom, 10, 100, BLACK );
        drawText( "Circle x Geom: " + textCircleGeom, 10, 130, BLACK );
        
        // quadtree
        drawText( String.format( "AABBQuadtree (AABBs: %d, maxDepth: %d) ", numberOfAABBs, maxTreeDepth ), qtX, qtY - 20, BLACK );
        drawQuadTree( qtX, qtY );
        
        drawFPS( 10, 10 );

    }

    private void initAABBs() {
        
        aabbs = new ArrayList<>();
        vels = new Vector2[numberOfAABBs];
        
        for ( int i = 0; i < numberOfAABBs; i++ ) {
            
            AABB aabb = new AABB();
            aabb.setSize( MathUtils.getRandomValue( 5, qtHeight / 15 ), MathUtils.getRandomValue( 5, qtHeight / 15 ) );
            aabb.move( MathUtils.getRandomValue( 1, (int) ( qtWidth - aabb.x2 - aabb.x1 - 2 ) ), MathUtils.getRandomValue( 1, (int) ( qtHeight - aabb.y2 - aabb.y1 - 2 ) ) );
            aabb.type = AABB.Type.DYNAMIC;
            aabbs.add( aabb );
            
            vels[i] = new Vector2( MathUtils.getRandomValue( -100, 100 ), MathUtils.getRandomValue( -100, 100 ) );
            
        }
        
    }
    
    private void calculateOverlaps( AABBQuadtreeNode node ) {
        
        if ( node.depth < quadtree.getMaxDepth() ) {
            
            int size = node.aabbs.size();
            
            for ( int i = 0; i < size; i++ ) {
                for ( int j = i+1; j < size; j++ ) {
                    try {
                        AABB a = node.aabbs.get( i );
                        AABB b = node.aabbs.get( j );
                        if ( a.type != AABB.Type.STATIC || b.type != AABB.Type.STATIC ) {
                            Rectangle ra = new Rectangle( a.x1, a.y1, ( a.x2 - a.x1 ), ( a.y2 - a.y1 ) );
                            Rectangle rb = new Rectangle( b.x1, b.y1, ( b.x2 - b.x1 ), ( b.y2 - b.y1 ) );
                            if ( CollisionUtils.checkCollisionRectangles( ra, rb ) ) {
                                Rectangle ri = CollisionUtils.getCollisionRectangle( ra, rb );
                                ri.x += qtX;
                                ri.y += qtY;
                                overlaps.add( ri );
                            }
                        }
                    } catch ( IndexOutOfBoundsException | NullPointerException exc ) {
                    }
                }
            }
            
            calculateOverlaps( node.nw );
            calculateOverlaps( node.ne );
            calculateOverlaps( node.sw );
            calculateOverlaps( node.se );
            
        }
        
    }
    
    private void drawQuadTree( double x, double y ) {
        
        overlaps.clear();
        calculateOverlaps( quadtree.getRoot() );
        
        quadtree.draw( this, x, y );
        
        for ( Rectangle r : overlaps ) {
            r.fill( this, aabbOverlapColor );
        }
        
    }
    
    private void updateAABBLocations( double delta ) {
        
        int k = 0;
        
        for ( AABB aabb : aabbs ) {
            
            Vector2 vel = vels[k++];
            
            aabb.move( vel.x * delta, vel.y * delta );
            
            if ( aabb.x1 <= 0 ) {
                aabb.moveTo( 0, aabb.y1 );
                vel.x = -vel.x;
            } else if ( aabb.x2 >= qtWidth ) {
                aabb.moveTo( qtWidth - aabb.width, aabb.y1 );
                vel.x = -vel.x;
            }
            
            if ( aabb.y1 <= 0 ) {
                aabb.moveTo( aabb.x1, 0 );
                vel.y = -vel.y;
            } else if ( aabb.y2 >= qtHeight ) {
                aabb.moveTo( aabb.x1, qtHeight - aabb.height );
                vel.y = -vel.y;
            }
            
            /*if ( aabb.x1 <= 0 || aabb.x2 >= qtWidth ) {
                vel.x = -vel.x;
            }
            
            if ( aabb.y1 <= 0 || aabb.y2 >= qtHeight ) {
                vel.y = -vel.y;
            }*/
            
        }
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new CollisionDetectionExample();
    }

}