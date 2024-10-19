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
import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import java.io.File;
import java.util.logging.LogManager;

/**
 * Classe de testes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ScratchPad extends Engine {

    private class T extends StreamPlayer {
        public T() {
            super();
            try {
                //open( new File( "resources/sfx/smw_coin.wav" ) );
                //open( new File( "resources/sfx/kick.wav" ) );
                //open( new File( "resources/sfx/kick.mp3" ) );
                //open( new File( "resources/sfx/test.mp3" ) );
                play();
            } catch ( StreamPlayerException exc ){
                exc.printStackTrace();
            }
                
        }
    }
    
    public ScratchPad() {
        super( 800, 450, "Scratch Pad", 60, true );
    }
    
    @Override
    public void create() {
    }

    @Override
    public void update() {
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            new T();
        }
    }
    
    @Override
    public void draw() {
    }
    
    public static void main( String[] args ) {
        LogManager.getLogManager().reset();
        new ScratchPad();
    }
    
}
