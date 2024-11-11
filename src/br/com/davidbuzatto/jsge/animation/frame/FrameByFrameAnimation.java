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
package br.com.davidbuzatto.jsge.animation.frame;

import br.com.davidbuzatto.jsge.animation.AnimationExecutionState;
import java.util.List;

/**
 * Uma animação quadro a quadro. A partir de um tempo, muda-se o frame 
 * de desenho atual.
 * 
 * @param <FrameType> O tipo do frame da animação.
 * @author Prof. Dr. David Buzatto
 */
public class FrameByFrameAnimation<FrameType extends AnimationFrame> {
    
    private double timeCounter;
    private double timeToNextFrame;
    private double[] timesToNextFrame;
    
    private int currentFrame;
    private int maxFrames;
    private boolean looping;
    
    private AnimationExecutionState state;
    
    private List<FrameType> frames;
    
    /**
     * Cria uma animação quadro a quadro com repetição (looping).
     * 
     * @param timeToNextFrame Tempo para a transição de um quadro para outro em segundos.
     * @param frames Quadros da animação.
     */
    public FrameByFrameAnimation( double timeToNextFrame, List<FrameType> frames ) {
        this( timeToNextFrame, frames, true );
    }
    
    /**
     * Cria uma animação quadro a quadro.
     * 
     * @param timeToNextFrame Tempo para a transição de um quadro para outro em segundos.
     * @param frames Quadros da animação.
     * @param looping Se a animação deve ser executada indefinidamente.
     */
    public FrameByFrameAnimation( double timeToNextFrame, List<FrameType> frames, boolean looping ) {
        this.timeToNextFrame = timeToNextFrame;
        this.frames = frames;
        this.maxFrames = frames.size();
        this.looping = looping;
        this.state = AnimationExecutionState.INITIALIZED;
    }
    
    /**
     * Cria uma animação quadro a quadro com repetição (looping).
     * 
     * @param timesToNextFrame Tempos para a transição dos quadros em segundos.
     * @param frames Quadros da animação.
     */
    public FrameByFrameAnimation( double[] timesToNextFrame, List<FrameType> frames ) {
        this( timesToNextFrame, frames, true );
    }
    
    /**
     * Cria uma animação quadro a quadro com repetição (looping).
     * 
     * @param timesToNextFrame Tempos para a transição dos quadros em segundos.
     * @param frames Quadros da animação.
     * @param looping Se a animação deve ser executada indefinidamente.
     */
    public FrameByFrameAnimation( double[] timesToNextFrame, List<FrameType> frames, boolean looping ) {
        this.timesToNextFrame = timesToNextFrame;
        this.frames = frames;
        this.maxFrames = frames.size();
        this.looping = looping;
        this.state = AnimationExecutionState.INITIALIZED;
    }
    
    /**
     * Atualiza a animação.
     * 
     * @param delta Variação do tempo.
     */
    public void update( double delta ) {
        
        if ( currentFrame == 0 && state == AnimationExecutionState.INITIALIZED ) {
            state = AnimationExecutionState.RUNNING;
        }
        
        if ( state == AnimationExecutionState.RUNNING ) {
            
            timeCounter += delta;

            if ( timesToNextFrame == null ) {
                if ( timeCounter >= timeToNextFrame ) {
                    timeCounter = 0;
                    currentFrame++;
                    //currentFrame = ( currentFrame + 1 ) % maxFrames;
                }
            } else {
                if ( timeCounter >= timesToNextFrame[currentFrame] ) {
                    timeCounter = 0;
                    currentFrame++;
                    //currentFrame = ( currentFrame + 1 ) % maxFrames;
                }
            }
            
            /*if ( !looping && currentFrame == maxFrames - 1 ) {
                state = AnimationExecutionState.FINISHED;
            }*/
            
            // para uma animação chegar ao final ela precisa alcançar o próximo
            // frame que "não existe".
            if ( currentFrame == maxFrames ) {
                currentFrame = 0;
                if ( !looping ) {
                    state = AnimationExecutionState.FINISHED;
                }
            }
            
        }
        
    }

    /**
     * Obtém o tempo para a transição de um quadro para outro em segundos.
     * 
     * @return O tempo para o próximo quadro em segundos.
     */
    public double getTimeToNextFrame() {
        return timeToNextFrame;
    }

    /**
     * Configura o tempo para a transição de um quadro para outro em segundos.
     * 
     * @param timeToNextFrame O tempo para o próximo quadro em segundos.
     */
    public void setTimeToNextFrame( double timeToNextFrame ) {
        this.timeToNextFrame = timeToNextFrame;
    }

    /**
     * Obtém os tempos para a transição de um quadro para outro em segundos.
     * 
     * @return Os tempos para os próximos quadros em segundos.
     */
    public double[] getTimesToNextFrame() {
        return timesToNextFrame;
    }

    /**
     * Configura os tempos para a transição de um quadro para outro em segundos.
     * 
     * @param timesToNextFrame Os tempos para os próximos quadros em segundos.
     */
    public void setTimesToNextFrame( double[] timesToNextFrame ) {
        this.timesToNextFrame = timesToNextFrame;
    }
    
    /**
     * Obtém o quadro atual.
     * 
     * @return O quadro atual da animação.
     */
    public FrameType getCurrentFrame() {
        return frames.get( currentFrame );
    }
    
    /**
     * Obtém a posição do quadro atual da animação.
     * 
     * @return A posição do quadro atual da animação.
     */
    public int getCurrentFramePosition() {
        return currentFrame;
    }
    
    /**
     * Obtém um quadro específico da animação.
     * 
     * @param index O índice do quadro.
     * @return Um quadro específico da animação ou null caso não exista.
     */
    public FrameType getFrame( int index ) {
        if ( index >= 0 && index < frames.size() ) {
            return frames.get( index );
        }
        return null;
    }

    /**
     * Obtém o estado da animação.
     * 
     * @return O estado da animação.
     */
    public AnimationExecutionState getState() {
        return state;
    }

    /**
     * Retorna se a animação está configurada para executar em loop (laço).
     * 
     * @return Verdadeiro caso a animação esteja em loop, falso caso contrário.
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * Configura se a animação deve executar indefinidamente (loop, laço).
     * 
     * @param looping Se a animação deve executar em loop (laço)
     */
    public void setLooping( boolean looping ) {
        this.looping = looping;
    }
    
    /**
     * Pausa a animação.
     */
    public void pause() {
        state = AnimationExecutionState.PAUSED;
    }
    
    /**
     * Retoma a animação.
     */
    public void resume() {
        state = AnimationExecutionState.RUNNING;
    }
    
    /**
     * Reseta a animação.
     */
    public void reset() {
        state = AnimationExecutionState.INITIALIZED;
        currentFrame = 0;
        timeCounter = 0;
    }
    
}
