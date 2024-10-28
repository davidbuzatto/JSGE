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
package br.com.davidbuzatto.jsge.sound;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import com.goxr3plus.streamplayer.enums.Status;
import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerEvent;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import com.goxr3plus.streamplayer.stream.StreamPlayerListener;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.FloatControl;

/**
 * Uma classe para representação de sons.
 * Utilize-a para sons curtos, de menos de 10 segundos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Sound {
    
    private static class GainControl extends FloatControl {
        public GainControl() {
            super( FloatControl.Type.MASTER_GAIN, 0.0f, 1.0f, 0.1f, 1, 1.0f, "" );
        }
    }
    
    private static ExecutorService executor = Executors.newFixedThreadPool( 10 );
    
    private class InternalPlayer extends StreamPlayer implements StreamPlayerListener {
        
        File file;
        InputStream is;
        URL url;
        
        InternalPlayer() {
            getOutlet().setGainControl( new GainControl() );
            addStreamPlayerListener( this );
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
        
        void playWithFile() {
            try {
                open( file );
                play();
            } catch ( StreamPlayerException exc ) {
                EngineFrame.traceLogError( CoreUtils.stackTraceToString( exc ) );
            }
        }
        
        void playWithInputStream() {
            try {
                open( is );
                play();
            } catch ( StreamPlayerException exc ) {
                EngineFrame.traceLogError( CoreUtils.stackTraceToString( exc ) );
            }
        }
        
        void playWithUrl() {
            try {
                open( url );
                play();
            } catch ( StreamPlayerException exc ) {
               EngineFrame.traceLogError( CoreUtils.stackTraceToString( exc ) );
            }
        }

        @Override
        public void opened( Object o, Map<String, Object> map ) {
        }

        @Override
        public void progress( int i, long l, byte[] bytes, Map<String, Object> map ) {
        }

        @Override
        public void statusUpdated( StreamPlayerEvent spe ) {
            if ( spe.getPlayerStatus() == Status.STOPPED ) {
                reset();
            }
        }
        
    }
    
    private File file;
    private InputStream is;
    private URL url;
    
    /**
     * Cria um som usando o caminho do arquivo.
     * 
     * @param filePath Caminho do arquivo.
     */
    public Sound( String filePath ) {
        this.file = new File( filePath );
    }
    
    /**
     * Cria um som usando um input stream.
     * 
     * @param is Input stream.
     */
    public Sound( InputStream is ) {
        this.is = is;
    }
    
    /**
     * Cria um som usando uma URL.
     * 
     * @param url URL.
     */
    public Sound( URL url ) {
        this.url = url;
    }
    
    /**
     * Executa o som.
     */
    public void play() {
        executor.execute( () -> {
            if ( file != null ) {
                InternalPlayer p = new InternalPlayer( file );
                p.playWithFile();
            } else if ( is != null ) {
                InternalPlayer p = new InternalPlayer( is );
                p.playWithInputStream();
            } else if ( url != null ) {
                InternalPlayer p = new InternalPlayer( url );
                p.playWithUrl();
            }
        });
    }
    
}
