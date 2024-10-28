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
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.CollisionUtils;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;

/**
 * Exemplos de utilização dos métotodos de desenho de primitivas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawingWithPrimitivesMethodsExample extends EngineFrame {
    
    private Vector2[] pointsQuadCurve;
    private Vector2[] pointsCubicCurve;
    private Vector2 draggedPoint;
    
    /**
     * Cria o exemplo.
     */
    public DrawingWithPrimitivesMethodsExample() {
        super( 720, 520, "Drawing with Primitive Methods", 60, true );
    }
    
    @Override
    public void create() {
        
        pointsQuadCurve = new Vector2[]{
            new Vector2( 500, 100 ),
            new Vector2( 525, 70 ),
            new Vector2( 550, 100 ),
            new Vector2( 575, 150 ),
            new Vector2( 600, 100 ),
            new Vector2( 625, 50 ),
            new Vector2( 650, 100 ),
            new Vector2( 675, 180 ),
            new Vector2( 700, 100 )
        };
        
        pointsCubicCurve = new Vector2[]{
            new Vector2( 450, 225 ),
            new Vector2( 465, 214 ),
            new Vector2( 477, 236 ),
            new Vector2( 500, 225 ),
            new Vector2( 517, 206 ),
            new Vector2( 559, 263 ),
            new Vector2( 580, 225 ),
            new Vector2( 603, 116 ),
            new Vector2( 650, 360 ),
            new Vector2( 700, 225 )
        };
        
    }

    @Override
    public void update() {
        
        Vector2 mousePos = getMousePositionPoint();
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            
            for ( Vector2 p : pointsQuadCurve ) {
                if ( CollisionUtils.checkCollisionPointCircle( mousePos, p, 5 ) ) {
                    draggedPoint = p;
                    break;
                }
            }

            if ( draggedPoint == null ) {
                for ( Vector2 p : pointsCubicCurve ) {
                    if ( CollisionUtils.checkCollisionPointCircle( mousePos, p, 5 ) ) {
                        draggedPoint = p;
                        break;
                    }
                }
            }
            
        }
        
        if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) {
            draggedPoint = null;
        }
        
        if ( draggedPoint != null ) {
            draggedPoint.x = mousePos.x;
            draggedPoint.y = mousePos.y;
        }
        
    }

    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void draw() {

        clearBackground( WHITE );
        
        drawPixel( 50, 50, BLACK );
        drawLine( 60, 60, 100, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectangle( 50, 240, 50, 100, 50, 240, 15, BLUE );
        drawRectangle( 50, 240, 50, 100, 50, 240, 15, BLACK );

        fillRoundRectangle( 50, 370, 80, 60, 20, BLUE );
        drawRoundRectangle( 50, 370, 80, 60, 20, BLACK );

        fillCircle( 250, 70, 30, MAROON );
        drawCircle( 250, 70, 30, BLACK );

        fillEllipse( 250, 160, 60, 30, MAROON );
        drawEllipse( 250, 160, 60, 30, BLACK );

        fillCircleSector( 250, 220, 30, 0, 130, MAROON );
        drawCircleSector( 250, 220, 30, 0, 130, BLACK );

        fillEllipseSector( 250, 280, 60, 30, 0, 130, MAROON );
        drawEllipseSector( 250, 280, 60, 30, 0, 130, BLACK );

        fillArc( 250, 350, 60, 30, 0, 130, MAROON );
        drawArc( 250, 350, 60, 30, 0, 130, BLACK );

        fillRing( 250, 400, 10, 30, 0, 130, MAROON );
        drawRing( 250, 400, 10, 30, 0, 130, BLACK );

        fillTriangle( 400, 50, 440, 100, 360, 100, ORANGE );
        drawTriangle( 400, 50, 440, 100, 360, 100, BLACK );

        fillPolygon( 400, 160, 5, 35, 0, ORANGE );
        drawPolygon( 400, 160, 5, 35, 0, BLACK );

        fillQuadCurve( 400, 220, 450, 270, 400, 320, ORANGE );
        drawQuadCurve( 400, 220, 450, 270, 400, 320, BLACK );

        fillCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, ORANGE );
        drawCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, BLACK );

        fillQuadCurve( pointsQuadCurve, PINK );
        drawQuadCurve( pointsQuadCurve, BLACK );
        
        fillCubicCurve( pointsCubicCurve, LIME );
        drawCubicCurve( pointsCubicCurve, BLACK );
        
        drawText( "This is a text!", 450, 320, 20, BLACK );
        drawText( "This is a rotated text!", 450, 350, 30, 20, BLACK );
        
        for ( Vector2 p : pointsQuadCurve ) {
            fillCircle( p, 5, ColorUtils.fade( VIOLET, 0.5 ) );
            drawCircle( p, 5, BLACK );
        }
        
        for ( Vector2 p : pointsCubicCurve ) {
            fillCircle( p, 5, ColorUtils.fade( DARKGREEN, 0.5 ) );
            drawCircle( p, 5, BLACK );
        }
        
        drawFPS( 10, 10 );

    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new DrawingWithPrimitivesMethodsExample();
    }

}