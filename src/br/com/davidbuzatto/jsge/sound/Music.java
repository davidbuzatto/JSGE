/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.sound;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.MathUtils;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.FloatControl;

/**
 * A class for representing music tracks.
 * Use it for music, i.e., sounds with a duration greater than 10 seconds.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Music {
    
    private static class GainControl extends FloatControl {
        public GainControl() {
            super( FloatControl.Type.MASTER_GAIN, 0.0f, 1.0f, 0.1f, 1, 1.0f, "" );
        }
    }
    
    private static ExecutorService executor = Executors.newFixedThreadPool( 10 );
    
    private class InternalPlayer extends StreamPlayer {
        
        File file;
        InputStream is;
        URL url;
        
        InternalPlayer() {
            getOutlet().setGainControl( new GainControl() );
        }
        
        InternalPlayer( File file ) {
            this();
            this.file = file;
        }
        
        InternalPlayer( InputStream is ) {
            this();
            this.is = is;
        }
        
        InternalPlayer( URL url ) {
            this();
            this.url = url;
        }
        
        void playNow() {
            try {
                boolean ok = false;
                if ( file != null ) {
                    open( file );
                    ok = true;
                } else if ( is != null ) {
                    open( is );
                    ok = true;
                } else if ( url != null ) {
                    open( url );
                    ok = true;
                }
                if ( ok ) {
                    play();
                }
            } catch ( StreamPlayerException exc ) {
                EngineFrame.traceLogError(CoreUtils.stackTraceToString( exc ) );
            }
        }
        
    }
    
    private InternalPlayer internalPlayer;
    
    /**
     * Creates a music track using the file path.
     *
     * @param filePath Path to the file.
     */
    public Music( String filePath ) {
        internalPlayer = new InternalPlayer( new File( filePath ) );
    }
    
    /**
     * Creates a music track using an input stream.
     *
     * @param is Input stream.
     */
    public Music( InputStream is ) {
        internalPlayer = new InternalPlayer( is );
    }
    
    /**
     * Creates a music track using a URL.
     *
     * @param url URL
     */
    public Music( URL url ) {
        internalPlayer = new InternalPlayer( url );
    }
    
    /**
     * Unloads a music track, releasing its resources.
     */
    public void unload() {
        internalPlayer.reset();
    }
    
    /**
     * Plays the music track.
     */
    public void play() {
        executor.execute( () -> {
            internalPlayer.playNow();
        });
    }
    
    /**
     * Stops playing the music track.
     */
    public void stop() {
        internalPlayer.stop();
    }
    
    /**
     * Pauses the music track.
     */
    public void pause() {
        internalPlayer.pause();
    }
    
    /**
     * Resumes playback of the music track.
     */
    public void resume() {
        internalPlayer.resume();
    }
    
    /**
     * Checks whether the music track is playing.
     *
     * @return True if the music track is playing, false otherwise.
     */
    public boolean isPlaying() {
        return internalPlayer.isPlaying();
    }
    
    /**
     * Checks whether the music track is stopped.
     *
     * @return True if the music track is stopped, false otherwise.
     */
    public boolean isStopped() {
        return internalPlayer.isStopped();
    }
    
    /**
     * Checks whether the music track is paused.
     *
     * @return True if the music track is paused, false otherwise.
     */
    public boolean isPaused() {
        return internalPlayer.isPaused();
    }
    
    /**
     * Checks whether the music track is seeking.
     *
     * @return True if the music track is seeking, false otherwise.
     */
    public boolean isSeeking() {
        return internalPlayer.isSeeking();
    }
    
    /**
     * Sets the volume of the music track.
     *
     * @param volume The volume of the music track, ranging from 0.0 to 1.0.
     */
    public void setVolume( double volume ) {
        volume = MathUtils.clamp( volume, 0.01, 1.0 );
        if ( volume <= 0.01 ) {
            volume = 0;
        }
        internalPlayer.setGain( volume );
    }
    
    /**
     * Seeks to a position in the music track.
     *
     * @param position Position in seconds of the desired moment.
     */
    public void seek( int position ) {
        try {
            internalPlayer.seekTo( position );
        } catch ( StreamPlayerException exc ) {
            EngineFrame.traceLogError(CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Gets the duration of the music track.
     *
     * @return Duration of the music track in seconds.
     */
    public int getTimeLength() {
        return internalPlayer.getDurationInSeconds();
    }
    
    /**
     * Gets the elapsed playback time of the music track.
     *
     * @return The elapsed playback time in seconds.
     */
    public int getTimePlayed() {
        return (int) ( internalPlayer.getEncodedStreamPosition() / (double) internalPlayer.getTotalBytes() * internalPlayer.getDurationInSeconds() );
    }
    
}
