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

/**
 * Template de exemplo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EmptyExample extends EngineFrame {

    /**
     * Cria o exemplo.
     */
    public EmptyExample() {
        super( 800, 450, "Empty", 60, true );
    }
    
    @Override
    public void create() {
    }

    @Override
    public void update() {
    }
    
    @Override
    public void draw() {
        clearBackground( WHITE );
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new EmptyExample();
    }
    
}
