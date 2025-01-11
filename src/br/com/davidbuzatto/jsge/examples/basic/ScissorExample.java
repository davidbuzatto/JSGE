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

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Path;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Star;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.image.Image;

/**
 * Exemplo de recortes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ScissorExample extends EngineFrame {
    
    private enum ScissorType {
        
        RECTANGLE( "Rectangle" ),
        ROUND_RECTANGLE( "Round Rectangle" ),
        CIRCLE( "Circle" ),
        CIRCLE_SECTOR( "Circle Sector" ),
        ELLIPSE( "Ellipse" ),
        ELLIPSE_SECTOR( "Ellipse Sector" ),
        ARC( "Arc" ),
        RING( "Ring" ),
        QUAD_CURVE( "Quadratic Curve" ),
        CUBIC_CURVE( "Cubic Curve" ),
        TRIANGLE( "Triangle" ),
        POLYGON( "Polygon" ),
        STAR( "Star" ),
        PATH( "Path" );
        
        private String name;

        private ScissorType( String name ) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
        
        
    }
    
    private Image logo;
    private int x;
    private int y;
    private int radius;
    private int currentScissor;
    
    /**
     * Cria o exemplo.
     */
    public ScissorExample() {
        super( 600, 600, "Scissor", 60, true );
    }
    
    @Override
    public void create() {
        logo = DrawingUtils.createLogo().resize( 0.3 );
        radius = 120;
        setDefaultFontSize( 20 );
    }

    @Override
    public void update( double delta ) {
        
        x = getMouseX();
        y = getMouseY();
        
        if ( isKeyPressed( KEY_RIGHT ) ) {
            currentScissor++;
        } else if ( isKeyPressed( KEY_LEFT ) ) {
            currentScissor--;
        }
        
        if ( currentScissor < 0 ) {
            currentScissor = ScissorType.values().length - 1;
        } else {
            currentScissor = currentScissor % ScissorType.values().length;
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( ORANGE );
        
        switch ( ScissorType.values()[currentScissor] ) {
            case RECTANGLE: beginScissorMode( x - radius, y - radius, radius * 2, radius * 2 ); break;
            case ROUND_RECTANGLE: beginScissorMode( new RoundRectangle( x - radius, y - radius, radius * 2, radius * 2, 50 ) ); break;
            case CIRCLE: beginScissorMode( new Circle( x, y, radius ) ); break;
            case CIRCLE_SECTOR: beginScissorMode( new CircleSector( x, y, radius, 0, 270 ) ); break;
            case ELLIPSE: beginScissorMode( new Ellipse( x, y, radius, radius / 2 ) ); break;
            case ELLIPSE_SECTOR: beginScissorMode( new EllipseSector( x, y, radius, radius / 2, 0, 270 ) ); break;
            case ARC: beginScissorMode( new Arc( x, y, radius, 0, 270 ) ); break;
            case RING: beginScissorMode( new Ring( x, y, radius / 2, radius, 0, 270 ) ); break;
            case QUAD_CURVE: beginScissorMode( new QuadCurve( x - radius, y, x, y - radius, x + radius, y) ); break;
            case CUBIC_CURVE: beginScissorMode( new CubicCurve( x - radius, y, x - radius + radius / 1.5, y - radius * 2, x + radius - radius / 1.5, y + radius * 2, x + radius, y ) ); break;
            case TRIANGLE: beginScissorMode( new Triangle( x, y - radius, x + radius, y + radius, x - radius, y + radius ) ); break;
            case POLYGON: beginScissorMode( new Polygon( x, y, 5, radius, -18 ) ); break;
            case STAR: beginScissorMode( new Star( x, y, 6, radius, 30 ) ); break;
            case PATH: 
                Path p = new Path();
                p.moveTo( x - radius, y - radius );
                p.lineTo( x, y - radius );
                p.quadTo( x + 20, y - radius / 2, x, y );
                p.lineTo( x + radius, y + radius );
                p.lineTo( x - radius, y + radius );
                p.cubicTo( x - radius - 20, y + radius - 50, x - radius + 20, y + radius - 100, x - radius, y - radius );
                p.close();
                beginScissorMode( p );
                break;
        }
        
        fillRectangle( 0, 0, getScreenWidth(), getScreenHeight(), BLUE );
        drawImage( 
                logo, 
                getScreenWidth() / 2 - logo.getWidth() / 2,
                getScreenHeight() / 2 - logo.getHeight() / 2
        );
        
        endScissorMode();
        
        drawFPS( 10, 10 );
        drawText( "Current Scissor: " + ScissorType.values()[currentScissor], 10, 30, BLACK );
        drawText( "<LEFT> or <RIGHT> to change.", 10, 50, BLACK );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new ScissorExample();
    }
    
}
