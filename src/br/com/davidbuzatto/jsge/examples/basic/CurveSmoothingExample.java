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
import br.com.davidbuzatto.jsge.math.CurveUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * Exemplo de uso dos métodos de suavização de curvas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CurveSmoothingExample extends EngineFrame {
    
    private List<Vector2> points;
    private List<Vector2> smoothChaikin;
    private List<Vector2> smoothCatmullRom;
    private boolean pointsChanged;
    
    private double tension;
    private int iterations;
    
    /**
     * Cria o exemplo.
     */
    public CurveSmoothingExample() {
        super( 600, 600, "Curve Smoothing", 60, true );
    }
    
    @Override
    public void create() {
        points = new ArrayList<>();
        smoothChaikin = new ArrayList<>();
        smoothCatmullRom = new ArrayList<>();
        tension = 0;
        iterations = 1;
        setDefaultFontSize( 20 );
        setDefaultStrokeLineWidth( 2 );
    }
    
    @Override
    public void update( double delta ) {
        
        if ( isKeyPressed( KEY_UP ) ) {
            tension += 0.05;
            pointsChanged = true;
        } else if ( isKeyPressed( KEY_DOWN ) ) {
            tension -= 0.05;
            pointsChanged = true;
        }
        
        if ( tension < 0 ) {
            tension = 0;
            pointsChanged = false;
        } else if ( tension > 1 ) {
            tension = 1;
            pointsChanged = false;
        }
        
        if ( isKeyPressed( KEY_LEFT ) ) {
            iterations--;
            pointsChanged = true;
        } else if ( isKeyPressed( KEY_RIGHT ) ) {
            iterations++;
            pointsChanged = true;
        }
        
        if ( iterations < 1 ) {
            iterations = 1;
            pointsChanged = false;
        } else if ( iterations > 10 ) {
            iterations = 10;
            pointsChanged = false;
        }
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            points.add( getMousePositionPoint() );
            pointsChanged = true;
        }
        
        if ( pointsChanged ) {
            smoothChaikin = CurveUtils.getCurveSmoothingChaikin( points, tension, iterations );
            smoothCatmullRom = CurveUtils.getSplineInterpolationCatmullRom( points, iterations );
            pointsChanged = false;
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        setStrokeLineWidth( 1 );
        for ( Vector2 point : points ) {
            drawCircle( point, 5, RED );
        }
        
        setStrokeDashArray( new float[]{ 4, 4 } );
        for ( int i = 0; i < points.size() - 1; i++ ) {
            drawLine( points.get( i ), points.get( i + 1 ), BLACK );
        }
        
        setStrokeLineWidth( 4 );
        setStrokeDashArray( null );
        for ( int i = 0; i < smoothChaikin.size() - 1; i++ ) {
            drawLine( smoothChaikin.get( i ), smoothChaikin.get( i + 1 ), BLUE );
        }
        
        for ( int i = 0; i < smoothCatmullRom.size() - 1; i++ ) {
            drawLine( smoothCatmullRom.get( i ), smoothCatmullRom.get( i + 1 ), ORANGE );
        }
        
        drawFPS( 10, 10 );
        
        
        drawText( "Draw a curve by clicking with the mouse!", 115, 10, BLACK );
        drawText( "Chaikin", getScreenWidth() - measureText( "Chaikin" ) - 10, getScreenHeight() - 50, BLUE );
        drawText( "Catmull-Rom", getScreenWidth() - measureText( "Catmull-Rom" ) - 10, getScreenHeight() - 30, ORANGE );
        
        drawText( String.format( "tension: %.2f (UP/DOWN)", tension ), 10, getScreenHeight() - 50, BLACK );
        drawText( String.format( "iterations: %d (LEFT/RIGHT)", iterations ), 10, getScreenHeight() - 30, BLACK );
        
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new CurveSmoothingExample();
    }
    
}
