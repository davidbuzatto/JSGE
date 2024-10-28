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
 * Uma classe para representação de músicas.
 * Utilize-a para músicas, ou seja, sons com duração maior que 10 segundos.
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
     * Cria uma música usando o caminho do arquivo.
     * 
     * @param filePath Caminho do arquivo.
     */
    public Music( String filePath ) {
        internalPlayer = new InternalPlayer( new File( filePath ) );
    }
    
    /**
     * Cria uma música usando um input stream.
     * 
     * @param is Input stream.
     */
    public Music( InputStream is ) {
        internalPlayer = new InternalPlayer( is );
    }
    
    /**
     * Cria uma música usando uma URL.
     * 
     * @param url URL
     */
    public Music( URL url ) {
        internalPlayer = new InternalPlayer( url );
    }
    
    /**
     * Descarrega uma música, liberando os recursos.
     */
    public void unload() {
        internalPlayer.reset();
    }
    
    /**
     * Executa a música.
     */
    public void play() {
        executor.execute( () -> {
            internalPlayer.playNow();
        });
    }
    
    /**
     * Para de executar a música.
     */
    public void stop() {
        internalPlayer.stop();
    }
    
    /**
     * Pausa a música.
     */
    public void pause() {
        internalPlayer.pause();
    }
    
    /**
     * Retoma a execução da música.
     */
    public void resume() {
        internalPlayer.resume();
    }
    
    /**
     * Verifica se a música está executando.
     * 
     * @return Verdadeiro caso a música esteja em execução, falso caso contrário.
     */
    public boolean isPlaying() {
        return internalPlayer.isPlaying();
    }
    
    /**
     * Verifica se a música está parada.
     * 
     * @return Verdadeiro caso a música esteja parada, falso caso contrário.
     */
    public boolean isStopped() {
        return internalPlayer.isStopped();
    }
    
    /**
     * Verifica se a música está pausada.
     * 
     * @return Verdadeiro caso a música esteja pausada, falso caso contrário.
     */
    public boolean isPaused() {
        return internalPlayer.isPaused();
    }
    
    /**
     * Verifica se a música está sendo procurada.
     * 
     * @return Verdadeiro caso a música esteja sendo procurada, falso caso contrário.
     */
    public boolean isSeeking() {
        return internalPlayer.isSeeking();
    }
    
    /**
     * Configura o volume da música.
     * 
     * @param volume O volume da música, variando de 0.0 a 1.0.
     */
    public void setVolume( double volume ) {
        volume = MathUtils.clamp( volume, 0.01, 1.0 );
        if ( volume <= 0.01 ) {
            volume = 0;
        }
        internalPlayer.setGain( volume );
    }
    
    /**
     * Procura uma posição da música.
     * 
     * @param position Posição em segundos do momento desejado.
     */
    public void seek( int position ) {
        try {
            internalPlayer.seekTo( position );
        } catch ( StreamPlayerException exc ) {
            EngineFrame.traceLogError(CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Obtém a duração da da música.
     * 
     * @return Duração da música em segundos.
     */
    public int getTimeLength() {
        return internalPlayer.getDurationInSeconds();
    }
    
    /**
     * Obtém o tempo de execução da música.
     * 
     * @return O tempo de execução em segundos.
     */
    public int getTimePlayed() {
        return (int) ( internalPlayer.getEncodedStreamPosition() / (double) internalPlayer.getTotalBytes() * internalPlayer.getDurationInSeconds() );
    }
    
}
