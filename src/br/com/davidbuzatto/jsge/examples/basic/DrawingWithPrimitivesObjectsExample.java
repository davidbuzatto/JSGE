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
import br.com.davidbuzatto.jsge.geom.Triangle;

/**
 * Exemplos de utilização dos objetos primitivos para desenho.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawingWithPrimitivesObjectsExample extends EngineFrame {
    
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
    private Path path;
    
    /**
     * Cria o exemplo.
     */
    public DrawingWithPrimitivesObjectsExample() {
        super( 500, 560, "Drawing with Primitive Objects", 60, true );
    }
    
    @Override
    public void create() {
        
        line = new Line( 60, 60, 100, 100 );
        rectangle = new Rectangle( 50, 120, 50, 100 );
        roundRectangle = new RoundRectangle( 50, 240, 80, 60, 20 );
        circle = new Circle( 250, 70, 30 );
        ellipse = new Ellipse( 250, 160, 60, 30 );
        circleSector = new CircleSector( 250, 220, 30, 0, 130 );
        ellipseSector = new EllipseSector( 250, 280, 60, 30, 0, 130 );
        arc = new Arc( 250, 350, 60, 30, 0, 130 );
        ring = new Ring( 250, 400, 10, 30, 0, 130 );
        triangle = new Triangle( 400, 50, 440, 100, 360, 100 );
        polygon = new Polygon( 400, 160, 5, 35, 0 );
        quadCurve = new QuadCurve( 400, 220, 450, 270, 400, 320 );
        cubicCurve = new CubicCurve( 400, 340, 350, 380, 450, 420, 400, 460 );
        
        path = new Path();
        path.moveTo( 50, 320 );
        path.lineTo( 100, 350 );
        path.lineTo( 120, 310 );
        path.lineTo( 180, 420 );
        path.lineTo( 180, 500 );
        path.quadTo( 130, 450, 80, 550 );
        path.lineTo( 50, 500 );
        path.cubicTo( 100, 450, 0, 400, 20, 340 );
        path.close();
        
    }
    
    @Override
    public void update() {
    }
    
    @Override
    public void draw() {

        clearBackground( WHITE );
        setFontStyle( FONT_BOLD );

        line.draw( this, BLACK );

        rectangle.fill( this, BLUE );
        rectangle.draw( this, BLACK );

        roundRectangle.fill( this, BLUE );
        roundRectangle.draw( this, BLACK );

        circle.fill( this, MAROON );
        circle.draw( this, BLACK );

        ellipse.fill( this, MAROON );
        ellipse.draw( this, BLACK );

        circleSector.fill( this, MAROON );
        circleSector.draw( this, BLACK );

        ellipseSector.fill( this, MAROON );
        ellipseSector.draw( this, BLACK );

        arc.fill( this, MAROON );
        arc.draw( this, BLACK );

        ring.fill( this, MAROON );
        ring.draw( this, BLACK );

        triangle.fill( this, ORANGE );
        triangle.draw( this, BLACK );

        polygon.fill( this, ORANGE );
        polygon.draw( this, BLACK );

        quadCurve.fill( this, ORANGE );
        quadCurve.draw( this, BLACK );

        cubicCurve.fill( this, ORANGE );
        cubicCurve.draw( this, BLACK );
        
        path.fill( this, LIME );
        path.draw( this, BLACK );
        
        drawFPS( 10, 10 );

    }

    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new DrawingWithPrimitivesObjectsExample();
    }

}