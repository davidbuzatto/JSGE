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

import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerInterface;
import java.io.File;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Test {

    private static final String AUDIO_FILE_NAME = "resources/sfx/kick.mp3";

    private final StreamPlayerInterface streamPlayer;
    //private final StreamPlayerListener listener;

    public Test( StreamPlayerInterface streamPlayer ) {
        this.streamPlayer = streamPlayer;
        //this.listener = new AnotherStreamPlayerListener(AUDIO_FILE_NAME, streamPlayer);

    }

    void start() {
        try {

            //1 Register to the Listeners
            //streamPlayer.addStreamPlayerListener(listener);
            // Open a File
            // open(new File("...")) //..Here must be the file absolute path
            // open(INPUTSTREAM)
            // open(AUDIOURL)
            // Example
            streamPlayer.open( new File( AUDIO_FILE_NAME ) );

            //Seek by bytes
            //seekBytes(500000L);
            //Seek +x seconds starting from the current position
            //streamPlayer.seekSeconds( 15 ); // forward 15 seconds
            //streamPlayer.seekSeconds( 15 ); // forward 15 seconds again

            /* Seek starting from the begginning of the audio */
            //seekTo(200);
            // Play it
            streamPlayer.play();
            //pause();

        } catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private String getExtension( String audioFileName ) {
        return audioFileName.split( "\\.(?=[^.]+$)" )[1];
    }

//	public static void main(final String[] args) {
//		new AnotherDemoApplication();
//	}
    public static void main( String[] args ) {
        new Test( new StreamPlayer() ).start();
    }

}
