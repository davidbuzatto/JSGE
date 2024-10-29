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
package br.com.davidbuzatto.jsge.examples.basic;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.CollisionUtils;
import br.com.davidbuzatto.jsge.math.CurveUtils;
import java.awt.Color;

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
    
    /**
     * Cria o exemplo.
     */
    public CollisionDetectionExample() {
        super( 800, 450, "Collision Detection", 60, true );
    }
    
    @Override
    public void create() {
        
        amountVel = 1;
        
        line = new Line( 270, 10, 340, 80 );
        rectangle = new Rectangle( 280, 90, 50, 80 );
        circle = new Circle( 305, 210, 30 );
        triangle = new Triangle( 305, 250, 345, 320, 265, 320 );
        polygon = new Polygon( 305, 380, 5, 45, 0 );

        moveableLine = new Line( 420, 80, 490, 10 );
        moveableRect = new Rectangle( 400, 105, 100, 50 );
        moveableCircle = new Circle( 450, 210, 30 );

        lineForPoint = new Line( 600, 50, 700, 150 ) ;
        quadForPoint = new QuadCurve( 600, 180, 700, 200, 700, 280 ) ;
        cubicForPoint = new CubicCurve( 600, 310, 600, 340, 700, 370, 700, 410 );

        textPointGeom = "none";
        textLineGeom = "none";
        textRectGeom = "none";
        textCircleGeom = "none";
        
    }
    
    @Override
    public void update() {
        
        double delta = getFrameTime();
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

        moveableRect.fill( this, moveableRectColor );
        moveableRect.draw( this, BLACK );

        moveableCircle.fill( this, moveableCircleColor );
        moveableCircle.draw( this, BLACK );

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

        drawText( " Point x Geom: " + textPointGeom, 10, 40, 20, BLACK );
        drawText( "  Line x Geom: " + textLineGeom, 10, 70, 20, BLACK );
        drawText( "  Rect x Geom: " + textRectGeom, 10, 100, 20, BLACK );
        drawText( "Circle x Geom: " + textCircleGeom, 10, 130, 20, BLACK );
        
        drawFPS( 10, 10 );

    }

    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new CollisionDetectionExample();
    }

}