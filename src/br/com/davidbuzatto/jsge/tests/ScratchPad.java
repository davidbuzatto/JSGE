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

import br.com.davidbuzatto.jsge.core.Engine;

/**
 * Classe de testes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ScratchPad extends Engine {

    public ScratchPad() {
        super( 800, 450, "Scratch Pad", 60, true );
    }
    
    @Override
    public void create() {
    }

    @Override
    public void update() {
    }
    
    @Override
    public void draw() {
        
        setFontSize( 40 );
        setFontStyle( FONT_BOLD );
        
        drawText( "test", 100, 100, RED );
        drawText( "test\ntest\ntest", 100, 100, 45.0, BLUE );
        drawText( "test\ntesÇãt\ntAAAAest", 100, 100, 5, 5, 10.0, 80, BLACK );
                
    }
    
    public static void main( String[] args ) {
        new ScratchPad();
    }
    
}
