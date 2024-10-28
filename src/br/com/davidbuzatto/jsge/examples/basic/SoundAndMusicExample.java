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
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.CollisionUtils;
import br.com.davidbuzatto.jsge.math.MathUtils;
import br.com.davidbuzatto.jsge.sound.Music;
import br.com.davidbuzatto.jsge.sound.Sound;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Exemplo de uso de som e música.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SoundAndMusicExample extends EngineFrame {
    
    private class Button {
        
        Rectangle rect;
        String label;
        boolean pressed;
        boolean down;
        boolean over;

        Button( Rectangle rect, String label ) {
            this.rect = rect;
            this.label = label;
        }
        
        void update( Vector2 mousePos ) {
            
            over = CollisionUtils.checkCollisionPointRectangle( mousePos.x, mousePos.y, rect );
            
            if ( over && isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
                pressed = true;
            } else {
                pressed = false;
            }
            
            if ( over && isMouseButtonDown( MOUSE_BUTTON_LEFT ) ) {
                down = true;
            }
            
            if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) {
                down = false;
            }
            
        }
        
        void draw() {
            
            Color c;
            
            if ( over ) {
                c = GOLD;
            } else {
                c = BLUE;
            }
            
            if ( down ) {
                c = LIME;
            }
            
            fillRectangle( rect, c );
            drawRectangle( rect, BLACK );
            drawText( label, rect.x + rect.width / 2 - measureText( label, 30 ) / 2, rect.y + 15, 30, BLACK );
            
        }
        
    }
    
    private Sound sound;
    private Music music;
    private Button btnPlay;
    private Button btnStop;
    private Button btnPause;
    private Button btnStart;
    private Button btnPrevious;
    private Button btnNext;
    private double musicVolume;
    
    /**
     * Cria o exemplo.
     */
    public SoundAndMusicExample() {
        super( 610, 160, "Sound and Music", 60, true );
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
                if ( music.isPlaying() ) {
                    music.stop();
                }
            }
        });
    }
    
    @Override
    public void create() {
        
        music = loadMusic( "resources/musics/musicSample.mp3" );
        musicVolume = 1.0;
        
        int btnX = 10;
        int btnY = 100;
        int btnD = 50;
        int btnSep = 10;
        
        btnPlay = new Button( new Rectangle( btnX, btnY, btnD, btnD ), ">" );
        btnStop = new Button( new Rectangle( btnX += ( btnD + btnSep ), btnY, btnD, btnD ), "S" );
        btnPause = new Button( new Rectangle( btnX += ( btnD + btnSep ), btnY, btnD, btnD ), "P" );
        btnStart = new Button( new Rectangle( btnX += ( btnD + btnSep ), btnY, btnD, btnD ), "|<" );
        btnPrevious = new Button( new Rectangle( btnX += ( btnD + btnSep ), btnY, btnD, btnD ), "<<" );
        btnNext = new Button( new Rectangle( btnX += ( btnD + btnSep ), btnY, btnD, btnD ), ">>" );
        
    }

    @Override
    public void update() {
        
        double delta = getFrameTime();
        Vector2 mousePos = getMousePositionPoint();
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_RIGHT ) ) {
            sound = loadSound( "resources/sfx/coin.wav" );
            sound.play();
        }
        
        btnPlay.update( mousePos );
        btnStop.update( mousePos );
        btnPause.update( mousePos );
        btnStart.update( mousePos );
        btnPrevious.update( mousePos );
        btnNext.update( mousePos );
        
        if ( btnPlay.pressed ) {
            if ( music.isPaused() ) {
                music.resume();
                music.setVolume( musicVolume );
            } else if ( !music.isPlaying() ) {
                music.play();
                music.setVolume( musicVolume );
            }
        }
        
        if ( btnStop.pressed ) {
            if ( music.isPlaying() || music.isPaused() ) {
                music.stop();
            }
        }
        
        if ( btnPause.pressed ) {
            if ( music.isPlaying() ) {
                music.pause();
            } else if ( music.isPaused() ) {
                music.resume();
                music.setVolume( musicVolume );
            }
        }
        
        if ( btnStart.pressed ) {
            if ( music.isPlaying() ) {
                music.stop();
                music.play();
            }
        }
        
        if ( btnPrevious.down ) {
            if ( music.isPlaying() ) {
                int p = music.getTimePlayed() - (int) ( delta * 30 );
                if ( p < 0 ) {
                    p = 0;
                }
                music.seek( p );
            }
        }
        
        if ( btnNext.down ) {
            if ( music.isPlaying() ) {
                int p = music.getTimePlayed() + (int) ( delta * 30 );
                if ( p >= music.getTimeLength() ) {
                    p = music.getTimeLength();
                }
                music.seek( p );
            }
        }
        
        double wheelMove = getMouseWheelMove();
        if ( wheelMove != 0.0 ) {
            musicVolume += wheelMove / 100.0;
            musicVolume = MathUtils.clamp( musicVolume, 0.0, 1.0 );
            if ( music.isPlaying() ) {
                music.setVolume( musicVolume );
            }
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        setFontSize( 20 );
        setFontStyle( FONT_BOLD );
        
        drawText( "Right click to play\ncoin sound effect!", 10, 10, BLACK );
        
        fillRectangle( 10, 60, 350, 20, LIGHTGRAY );
        if ( music.isPlaying() || music.isPaused() ) {
            
            double w = music.getTimePlayed() / (double) music.getTimeLength() * 340;
            
            fillRectangle( 15, 65, w, 10, DARKGRAY );
            drawText( 
                    String.format( "%03d/%03d" , 
                            music.getTimePlayed(), 
                            music.getTimeLength() ), 
                    370, 63, BLACK
            );
            
        } else {
            drawText( "000/000", 370, 63, BLACK );
        }
        
        int volume = (int) ( musicVolume * 100 );
        int h = (int) ( 80 * ( 1.0 - musicVolume ) );
        fillRectangle( 480, 60, 20, 90, LIGHTGRAY );
        fillRectangle( 485, 65 + h, 10, 80 - h, DARKGRAY );
        drawText( String.format( "Volume\n %d%%", volume ), 520, 70, BLACK );
        
        drawText( "use mouse\n  wheel", 510, 115, 16, LIGHTGRAY );
        
        btnPlay.draw();
        btnStop.draw();
        btnPause.draw();
        btnStart.draw();
        btnPrevious.draw();
        btnNext.draw();
        
        String musicState;
        
        if ( music.isPlaying() ) {
            musicState = "Playing";
        } else if ( music.isStopped() ) {
            musicState = "Stopped";
        } else if ( music.isPaused() ) {
            musicState = "Paused";
        } else if ( music.isSeeking() ) {
            musicState = "Seeking";
        } else {
            musicState = "Ready";
        }
        
        drawText( musicState, 370, 116, BLACK );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new SoundAndMusicExample();
    }
    
}
