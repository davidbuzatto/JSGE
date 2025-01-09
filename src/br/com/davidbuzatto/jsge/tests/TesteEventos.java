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
package br.com.davidbuzatto.jsge.tests;

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;

/**
 * Classe de testes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TesteEventos extends EngineFrame {
    
    /**
     * Cria o teste.
     */
    public TesteEventos() {
        super( 800, 450, "Eventos", 60, true );
    }
    
    Rectangle r;
    
    @Override
    public void create() {
        r = new Rectangle( 10, 10, 100, 100 );
    }
    
    
    
    @Override
    public void update( double delta ) {
        
        Vector2 mousePos = getMousePositionPoint();
        
        if ( CollisionUtils.checkCollisionPointRectangle( mousePos, r ) ) {
            System.out.println( "a" );
            if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
                System.out.println( "xxx" );
            }
        }
        
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        r.fill( this, GOLD );
        
    }
    
    /**
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new TesteEventos();
    }
    
}
