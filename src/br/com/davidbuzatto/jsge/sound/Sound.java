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

import br.com.davidbuzatto.jsge.utils.MathUtils;
import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
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
    
    private class InternalPlayer extends StreamPlayer {
        
        File file;
        InputStream is;
        URL url;
        
        InternalPlayer() {
            getOutlet().setGainControl( new GainControl() );
        }
        
        InternalPlayer( String filePath ) {
            this();
            this.file = new File( filePath );
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
                exc.printStackTrace();
            }
        }
        
    }
    
    private InternalPlayer internalPlayer;
    
    /**
     * Cria um som usando o caminho do arquivo.
     * 
     * @param filePath Caminho do arquivo.
     */
    public Sound( String filePath ) {
        internalPlayer = new InternalPlayer( filePath );
    }
    
    /**
     * Cria um som usando um input stream.
     * 
     * @param is Input stream.
     */
    public Sound( InputStream is ) {
        internalPlayer = new InternalPlayer( is );
    }
    
    /**
     * Cria um som usando uma URL.
     * 
     * @param url URL.
     */
    public Sound( URL url ) {
        internalPlayer = new InternalPlayer( url );
    }
    
    /**
     * Descarrega um som, liberando os recursos.
     */
    public void unload() {
        internalPlayer.reset();
    }
    
    /**
     * Executa o som.
     */
    public void play() {
        internalPlayer.playNow();
    }
    
    /**
     * Para de executar o som.
     */
    public void stop() {
        internalPlayer.stop();
    }
    
    /**
     * Pausa o som.
     */
    public void pause() {
        internalPlayer.pause();
    }
    
    /**
     * Retoma a execução do som.
     */
    public void resume() {
        internalPlayer.resume();
    }
    
    /**
     * Verifica se o som está executando.
     * 
     * @return Verdadeiro caso o som esteja em execução, falso caso contrário.
     */
    public boolean isPlaying() {
        return internalPlayer.isPlaying();
    }
    
    /**
     * Configura o volume do som.
     * 
     * @param volume O volume do som, variando de 0.0 a 1.0.
     */
    public void setVolume( double volume ) {
        internalPlayer.setGain( MathUtils.clamp( volume, 0.01, 1.0 ) );
    }
    
}
