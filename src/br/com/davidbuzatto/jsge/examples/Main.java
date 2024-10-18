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
package br.com.davidbuzatto.jsge.examples;

import br.com.davidbuzatto.jsge.core.Engine;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Point;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.geom.Vector2;
import br.com.davidbuzatto.jsge.utils.CollisionUtils;
import br.com.davidbuzatto.jsge.utils.ColorUtils;
import br.com.davidbuzatto.jsge.utils.MathUtils;
import br.com.davidbuzatto.jsge.utils.Utils;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Exemplos de utilização da engine.
 * 
 * Todas as classes necessárias estão implementadas como classes internas para
 * que o exemplo seja auto-contido.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Main extends Engine {
    
    private class Bolinha {

        Vector2 pos;
        Vector2 posAnt;
        Vector2 vel;
        Color cor;
        double raio;
        double atrito;
        double elasticidade;
        boolean arrastando;

        void desenhar() {
            fillCircle( pos, raio, cor );
        }

        void atualizar( double delta, int width, int height ) {

            if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
                if ( CollisionUtils.checkCollisionPointCircle( getMousePositionPoint(), pos, raio ) ) {
                    arrastando = true;
                    xOffset = pos.x - getMouseX();
                    yOffset = pos.y - getMouseY();
                }
            } else if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) {
                arrastando = false;
            }
    
            if ( !arrastando ) {
    
                pos.x += vel.x * delta;
                pos.y += vel.y * delta;
    
                if ( pos.x - raio <= 0 ) {
                    pos.x = raio;
                    vel.x = -vel.x * elasticidade;
                } else if ( pos.x + raio >= width ) {
                    pos.x = width - raio;
                    vel.x = -vel.x * elasticidade;
                }
    
                if ( pos.y - raio <= 0 ) {
                    pos.y = raio;
                    vel.y = -vel.y * elasticidade;
                } else if ( pos.y + raio >= height ) {
                    pos.y = height - raio;
                    vel.y = -vel.y * elasticidade;
                }
    
                vel.x = vel.x * atrito;
                vel.y = vel.y * atrito + GRAVIDADE;
    
            } else {
                pos.x = getMouseX() + xOffset;
                pos.y = getMouseY() + yOffset;
                vel.x = ( pos.x - posAnt.x ) / delta;
                vel.y = ( pos.y - posAnt.y ) / delta;
                posAnt.x = pos.x;
                posAnt.y = pos.y;
            }

        }

    }
    
    private class Particula {

        Vector2 pos;
        Vector2 vel;
        Color cor;
        double raio;
        double atrito;
        double elasticidade;

        void desenhar() {
            fillCircle( pos, raio, cor );
        }

        void atualizar( double delta, Rectangle limite ) {

            pos.x += vel.x * delta;
            pos.y += vel.y * delta;

            if ( pos.x - raio <= limite.x ) {
                pos.x = limite.x + raio;
                vel.x = -vel.x * elasticidade;
            } else if ( pos.x + raio >= limite.x + limite.width ) {
                pos.x = limite.x + limite.width - raio;
                vel.x = -vel.x * elasticidade;
            }

            /*if ( pos.y - raio <= limite.y ) {
                pos.y = limite.y + raio;
                vel.y = -vel.y * elasticidade / raio;
            } else if ( pos.y + raio >= limite.y + limite.height ) {
                pos.y = limite.y + limite.height - raio;
                vel.y = -vel.y * elasticidade / raio;
            }*/

            vel.x = vel.x * atrito;
            vel.y = vel.y * atrito + GRAVIDADE;

        }

    }
    
    private class DrawingTests {

        private Point point;
        private Vector2 vector;
        private Line line;
        private Rectangle rectangle;
        private RoundRectangle roundRectangle;
        private Circle circle;
        private Ellipse ellipse;
        private CircleSector circleSector;
        private EllipseSector ellipseSector;
        private Arc arc;
        private Ring ring;
        private Triangle triangle;
        private Polygon polygon;
        private QuadCurve quadCurve;
        private CubicCurve cubicCurve;
        
        private int dx = 1000;
        private int dy = 450;

        public DrawingTests() {
            
            point = new Point( dx + 50, dy + 50 );
            vector = new Vector2( dx + 30, dy + 30 );
            line = new Line( dx + 60, dy + 60, dx + 100, dy + 100 );
            rectangle = new Rectangle( dx + 50, dy + 120, 50, 100 );
            roundRectangle = new RoundRectangle( dx + 50, dy + 370, 80, 60, 20 );
            circle = new Circle( dx + 250, dy + 70, 30 );
            ellipse = new Ellipse( dx + 250, dy + 160, 60, 30 );
            circleSector = new CircleSector( dx + 250, dy + 220, 30, 0, 130 );
            ellipseSector = new EllipseSector( dx + 250, dy + 280, 60, 30, 0, 130 );
            arc = new Arc( dx + 250, dy + 350, 60, 30, 0, 130 );
            ring = new Ring( dx + 250, dy + 400, 10, 30, 0, 130 );
            triangle = new Triangle( dx + 400, dy + 50, dx + 440, dy + 100, dx + 360, dy + 100 );
            polygon = new Polygon( dx + 400, dy + 160, 5, 35, 0 );
            quadCurve = new QuadCurve( dx + 400, dy + 220, dx + 450, dy + 270, dx + 400, dy + 320 );
            cubicCurve = new CubicCurve( dx + 400, dy + 340, dx + 350, dy + 380, dx + 450, dy + 420, dx + 400, dy + 460 );

        }

        private void drawUsingPrimitives() {

            drawPixel( dx + 50, dy + 50, BLACK );
            drawLine( dx + 60, dy + 60, dx + 100, dy + 100, BLACK );

            fillRectangle( dx + 50, dy + 120, 50, 100, BLUE );
            drawRectangle( dx + 50, dy + 120, 50, 100, BLACK );

            fillRectangle( dx + 50, dy + 120, 50, 100, BLUE );
            drawRectangle( dx + 50, dy + 120, 50, 100, BLACK );

            fillRectanglePro( dx + 50, dy + 240, 50, 100, dx + 50, dy + 240, 15, BLUE );
            drawRectanglePro( dx + 50, dy + 240, 50, 100, dx + 50, dy + 240, 15, BLACK );

            fillRoundRectangle( dx + 50, dy + 370, 80, 60, 20, BLUE );
            drawRoundRectangle( dx + 50, dy + 370, 80, 60, 20, BLACK );

            fillRectangleGradientH( dx + 50, dy + 450, 100, 50, BLUE, GREEN );
            fillRectangleGradientV( dx + 50, dy + 520, 100, 50, BLUE, GREEN );

            fillCircle( dx + 250, dy + 70, 30, MAROON );
            drawCircle( dx + 250, dy + 70, 30, BLACK );

            fillEllipse( dx + 250, dy + 160, 60, 30, MAROON );
            drawEllipse( dx + 250, dy + 160, 60, 30, BLACK );

            fillCircleSector( dx + 250, dy + 220, 30, 0, 130, MAROON );
            drawCircleSector( dx + 250, dy + 220, 30, 0, 130, BLACK );

            fillEllipseSector( dx + 250, dy + 280, 60, 30, 0, 130, MAROON );
            drawEllipseSector( dx + 250, dy + 280, 60, 30, 0, 130, BLACK );

            fillArc( dx + 250, dy + 350, 60, 30, 0, 130, MAROON );
            drawArc( dx + 250, dy + 350, 60, 30, 0, 130, BLACK );

            fillRing( dx + 250, dy + 400, 10, 30, 0, 130, MAROON );
            drawRing( dx + 250, dy + 400, 10, 30, 0, 130, BLACK );

            fillTriangle( dx + 400, dy + 50, dx + 440, dy + 100, dx + 360, dy + 100, ORANGE );
            drawTriangle( dx + 400, dy + 50, dx + 440, dy + 100, dx + 360, dy + 100, BLACK );

            fillPolygon( dx + 400, dy + 160, 5, 35, 0, ORANGE );
            drawPolygon( dx + 400, dy + 160, 5, 35, 0, BLACK );

            fillQuadCurve( dx + 400, dy + 220, dx + 450, dy + 270, dx + 400, dy + 320, ORANGE );
            drawQuadCurve( dx + 400, dy + 220, dx + 450, dy + 270, dx + 400, dy + 320, BLACK );

            fillCubicCurve( dx + 400, dy + 340, dx + 350, dy + 380, dx + 450, dy + 420, dx + 400, dy + 460, ORANGE );
            drawCubicCurve( dx + 400, dy + 340, dx + 350, dy + 380, dx + 450, dy + 420, dx + 400, dy + 460, BLACK );

            drawText( "test", new Vector2( dx + 500, dy + 200 ), new Vector2( dx + 500, dy + 200 ), -45, 10, BLACK );
            drawText( getFrameTime() + "", new Vector2( dx + 500, dy + 300 ), new Vector2( dx + 500, dy + 300 ), 45, 20, BLACK );

        }

        private void drawUsingObjects( Engine engine ) {

            point.draw( engine, BLACK );
            vector.draw( engine, BLACK );

            line.draw( engine, BLACK );

            rectangle.fill( engine, BLUE );
            rectangle.draw( engine, BLACK );

            roundRectangle.fill( engine, BLUE );
            roundRectangle.draw( engine, BLACK );

            circle.fill( engine, MAROON );
            circle.draw( engine, BLACK );

            ellipse.fill( engine, MAROON );
            ellipse.draw( engine, BLACK );

            circleSector.fill( engine, MAROON );
            circleSector.draw( engine, BLACK );

            ellipseSector.fill( engine, MAROON );
            ellipseSector.draw( engine, BLACK );

            arc.fill( engine, MAROON );
            arc.draw( engine, BLACK );

            ring.fill( engine, MAROON );
            ring.draw( engine, BLACK );

            triangle.fill( engine, ORANGE );
            triangle.draw( engine, BLACK );

            polygon.fill( engine, ORANGE );
            polygon.draw( engine, BLACK );

            quadCurve.fill( engine, ORANGE );
            quadCurve.draw( engine, BLACK );

            cubicCurve.fill( engine, ORANGE );
            cubicCurve.draw( engine, BLACK );

        }
        
    }
    
    private static final double GRAVIDADE = 50;
    private Point mousePos;
    
    // bolinha
    private double xOffset;
    private double yOffset;
    Bolinha bolinha;
    
    // testes de colisão
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
    private Point lineCollisionPoint;

    private Line lineForPoint;
    private QuadCurve quadForPoint;
    private CubicCurve cubicForPoint;
    private Point pointForLine;
    private Point pointForQuad;
    private Point pointForCubic;

    private double amount;
    private double amountVel;

    private String textPointGeom;
    private String textLineGeom;
    private String textRectGeom;
    private String textCircleGeom;
    
    // partículas
    private List<Particula> particulas;
    private Rectangle limitesParticulas;
    
    // testes de desenho
    private DrawingTests drawingTests;
    
    // testes de entrada
    private String aPress = "none";
    private String aDown = "none";
    private String mouseLeftPress = "none";
    private String mouseLeftDown = "none";
    private String mouseMiddlePress = "none";
    private String mouseMiddleDown = "none";
    private String mouseRightPress = "none";
    private String mouseRightDown = "none";
    
    // imagens
    private Image img;
    private Color cr = new Color( 255, 0, 0, 100 );
    private Color cg = new Color( 0, 255, 0, 100 );
    private Color cb = new Color( 0, 0, 255, 100 );
    
    
    public Main() {
        super( 800, 450, "JSGE - v" + Utils.getVersion(), 60, true );
        setExtendedState( MAXIMIZED_BOTH );
    }
    
    /**
     * Processa a entrada inicial fornecida pelo usuário e cria
     * e/ou inicializa os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void create() {
        
        // bolinha
        bolinha = new Bolinha();
        bolinha.pos = new Vector2( 400, 225 );
        bolinha.posAnt = new Vector2();
        bolinha.vel = new Vector2( 200, 200 );
        bolinha.raio = 30;
        bolinha.atrito = 0.99;
        bolinha.elasticidade = 0.9;
        bolinha.cor = BLUE;
        
        // testes de colisão
        amountVel = 1;
        int xStartC = 810;
        
        line = new Line( xStartC + 270, 10, xStartC + 340, 80 );
        rectangle = new Rectangle( xStartC + 280, 90, 50, 80 );
        circle = new Circle( xStartC + 305, 210, 30 );
        triangle = new Triangle( xStartC + 305, 250, xStartC + 345, 320, xStartC + 265, 320 );
        polygon = new Polygon( xStartC + 305, 380, 5, 45, 0 );

        moveableLine = new Line( xStartC + 420, 80, xStartC + 490, 10 );
        moveableRect = new Rectangle( xStartC + 400, 105, 100, 50 );
        moveableCircle = new Circle( xStartC + 450, 210, 30 );

        lineForPoint = new Line( xStartC + 600, 50, xStartC + 700, 150 ) ;
        quadForPoint = new QuadCurve( xStartC + 600, 180, xStartC + 700, 200, xStartC + 700, 280 ) ;
        cubicForPoint = new CubicCurve( xStartC + 600, 310, xStartC + 600, 340, xStartC + 700, 370, xStartC + 700, 410 );

        textPointGeom = "none";
        textLineGeom = "none";
        textRectGeom = "none";
        textCircleGeom = "none";
        
        // partículas
        particulas = new CopyOnWriteArrayList<>();
        limitesParticulas = new Rectangle( 500, 460, 500, 400 ) ;
        
        // testes de desenho
        drawingTests = new DrawingTests();
        
        // imagens
        img = loadImage( "resources/images/duke.png" );
        
    }

    /**
     * Atualiza os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void update() {
        
        double delta = getFrameTime();
        mousePos = getMousePositionPoint();        
        
        // bolinha
        bolinha.atualizar( delta, 800, 450 );
        
        // testes de colisão
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

        pointForLine = MathUtils.getPointAtLine( lineForPoint, amount );
        pointForQuad = MathUtils.getPointAtQuadCurve( quadForPoint, amount );
        pointForCubic = MathUtils.getPointAtCubicCurve( cubicForPoint, amount );

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
        
        // partículas
        if ( isMouseButtonDown( MOUSE_BUTTON_LEFT ) && CollisionUtils.checkCollisionPointRectangle( mousePos, limitesParticulas ) ) {
            for ( int i = 0; i < 20; i++ ) {
                Particula p = new Particula();
                p.pos = new Vector2( mousePos.x, mousePos.y );
                p.vel = new Vector2( MathUtils.getRandomValue( -200, 200 ), MathUtils.getRandomValue( -200, 200 ) );
                p.raio = MathUtils.getRandomValue( 2, 6 );
                p.atrito = 0.99;
                p.elasticidade = 0.8;
                p.cor = ColorUtils.colorFromHSV( MathUtils.getRandomValue( 0, 30 ), 1, 1 );
                particulas.add( p );
            }
        }

        for ( Particula p : particulas ) {
            p.atualizar( delta, limitesParticulas );
        }
        
        // entrada
        if ( isKeyPressed( KEY_A ) ) aPress = "pressed";
        if ( isKeyReleased( KEY_A ) ) aPress = "released";
        aDown = isKeyDown( KEY_A ) ? "down" : "up";
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) mouseLeftPress = "pressed";
        if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) mouseLeftPress = "released";
        mouseLeftDown = isMouseButtonDown( MOUSE_BUTTON_LEFT ) ? "down" : "up";
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_MIDDLE ) ) mouseMiddlePress = "pressed";
        if ( isMouseButtonReleased( MOUSE_BUTTON_MIDDLE ) ) mouseMiddlePress = "released";
        mouseMiddleDown = isMouseButtonDown( MOUSE_BUTTON_MIDDLE ) ? "down" : "up";
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_RIGHT ) ) mouseRightPress = "pressed";
        if ( isMouseButtonReleased( MOUSE_BUTTON_RIGHT ) ) mouseRightPress = "released";
        mouseRightDown = isMouseButtonDown( MOUSE_BUTTON_RIGHT ) ? "down" : "up";
        
    }

    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void draw() {

        clearBackground( WHITE );
        setFontStyle( Font.BOLD );
        
        // bolinha
        bolinha.desenhar();
        drawRectangle( 0, 0, 800, 450, BLACK );
        
        // testes de colisão
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

        drawText( " Point x Geom: " + textPointGeom, 810, 30, 20, BLACK );
        drawText( "  Line x Geom: " + textLineGeom, 810, 60, 20, BLACK );
        drawText( "  Rect x Geom: " + textRectGeom, 810, 90, 20, BLACK );
        drawText( "Circle x Geom: " + textCircleGeom, 810, 120, 20, BLACK );
        
        // testes das funções de cores
        for ( int i = 0; i <= 360; i++ ) {
            drawRectangle( 100 + i, 460 + i, 2, 2, ColorUtils.colorFromHSV( i, 1, 1 ) );
        }

        Color c = LIME;
        fillRectangle( 10, 460, 50, 50, c );
        fillRectangle( 10, 510, 50, 50, ColorUtils.colorAlpha( c, 0.5 ) );
        fillRectangle( 10, 560, 50, 50, ColorUtils.colorTint( c, WHITE ) );
        fillRectangle( 10, 610, 50, 50, ColorUtils.colorBrightness( c, -0.5 ) );
        fillRectangle( 10, 660, 50, 50, ColorUtils.colorContrast( c, -0.5 ) );
        
        // partículas
        fillRectangle( limitesParticulas, BLACK );
        drawText( "Particles: " + particulas.size(), limitesParticulas.x + 10, limitesParticulas.y + 20, 20, WHITE );

        for ( Particula p : particulas ) {
            p.desenhar();
        }
        
        // testes de desenhos
        drawingTests.drawUsingPrimitives();
        //drawingTests.drawUsingObjects( this );
        
        // entrada
        drawRectangle( 10, 800, 360, 120, BLACK );
        drawText( "       Key A: " + aPress + " | " + aDown, 15, 810, 20, BLACK );
        drawText( "  Mouse left: " + mouseLeftPress + " | " + mouseLeftDown, 15, 840, 20, BLACK );
        drawText( "Mouse middle: " + mouseMiddlePress + " | " + mouseMiddleDown, 15, 870, 20, BLACK );
        drawText( " Mouse right: " + mouseRightPress + " | " + mouseRightDown, 15, 900, 20, BLACK );
        
        // imagens
        drawImage( img, 1700, 10, cg );
        drawImage( img, 1700, 10, 45, cb );
        drawImage( img, 1700, 10, 10, 10, 90, cr );
        
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), 1700, 410, cg );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), 1700, 410, 45, cb );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), 1700, 410, 10, 10, 90 );
        
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 1700, 610, 150, 150 ), cg );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 1700, 610, 150, 150 ), 45, cb );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 1700, 610, 150, 150 ), 10, 10, 90 );
        
        drawFPS( 10, 20 );

    }

    public static void main( String[] args ) {
        new Main();
    }

}