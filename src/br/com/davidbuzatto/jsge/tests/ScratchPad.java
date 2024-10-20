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
import br.com.davidbuzatto.jsge.sound.Music;
import br.com.davidbuzatto.jsge.sound.Sound;
import java.util.logging.LogManager;

/**
 * Classe de testes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ScratchPad extends Engine {
    
    private Sound s;
    private Music music;
    private double volume;
    
    public ScratchPad() {
        super( 800, 450, "Scratch Pad", 60, true );
    }
    
    @Override
    public void create() {
        volume = 1.0;
        music = new Music( "resources/sfx/musicSample.mp3" );
        music.play();
    }

    @Override
    public void update() {
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            s = new Sound( "resources/sfx/coin.wav" );
            s.play();
        }
        
        if ( isKeyPressed( KEY_UP ) ) {
            volume += 0.1;
            
        }
        if ( isKeyPressed( KEY_DOWN ) ) {
            volume -= 0.1;
        }
        
        //volume = MathUtils.clamp( volume, 0.0, 1.0 );
        music.setVolume( volume );
        
    }
    
    @Override
    public void draw() {
        
        drawText( String.format( "%d/%d", music.getTimePlayed(), music.getTimeLength() ), 10, 10, 20, BLACK );
        
    }
    
    public static void main( String[] args ) {
        LogManager.getLogManager().reset();
        new ScratchPad();
    }
    
}
