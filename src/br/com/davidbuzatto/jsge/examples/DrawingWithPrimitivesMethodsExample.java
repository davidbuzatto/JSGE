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

/**
 * Exemplos de utilização dos métotodos de desenho de primitivas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawingWithPrimitivesMethodsExample extends Engine {
    
    /**
     * Cria o exemplo.
     */
    public DrawingWithPrimitivesMethodsExample() {
        super( 720, 520, "Drawing with Primitive Methods", 60, true );
    }
    
    @Override
    public void create() {
    }

    @Override
    public void update() {
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

        drawText( "This is a text!", 500, 200, 20, BLACK );
        drawText( "This is a rotated text!", 500, 300, 45, 20, BLACK );
        
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