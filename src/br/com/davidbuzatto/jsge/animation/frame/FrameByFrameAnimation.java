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
package br.com.davidbuzatto.jsge.animation.frame;

import br.com.davidbuzatto.jsge.animation.AnimationExecutionState;
import java.util.List;

/**
 * A frame-by-frame animation. After a given time, the current drawing frame
 * is changed.
 *
 * @param <FrameType> The type of the animation frame.
 * @author Prof. Dr. David Buzatto
 */
public class FrameByFrameAnimation<FrameType extends AnimationFrame> {
    
    private double timeCounter;
    private double timeToNextFrame;
    private double[] timesToNextFrame;
    
    private int currentFrame;
    private int maxFrames;
    private boolean looping;
    private boolean runBackwards;
    private boolean stopAtLastFrameWhenFinished;
    
    private AnimationExecutionState state;
    
    private List<FrameType> frames;
    
    /**
     * Creates a frame-by-frame animation with looping.
     *
     * @param timeToNextFrame Time for the transition from one frame to the next, in seconds.
     * @param frames The animation frames.
     */
    public FrameByFrameAnimation( double timeToNextFrame, List<FrameType> frames ) {
        this( timeToNextFrame, frames, true );
    }

    /**
     * Creates a frame-by-frame animation.
     *
     * @param timeToNextFrame Time for the transition from one frame to the next, in seconds.
     * @param frames The animation frames.
     * @param looping Whether the animation should run indefinitely.
     */
    public FrameByFrameAnimation( double timeToNextFrame, List<FrameType> frames, boolean looping ) {
        this.timeToNextFrame = timeToNextFrame;
        this.frames = frames;
        this.maxFrames = frames.size();
        this.looping = looping;
        this.state = AnimationExecutionState.INITIALIZED;
    }
    
    /**
     * Creates a frame-by-frame animation with looping.
     *
     * @param timesToNextFrame Times for the transition between frames, in seconds.
     * @param frames The animation frames.
     */
    public FrameByFrameAnimation( double[] timesToNextFrame, List<FrameType> frames ) {
        this( timesToNextFrame, frames, true );
    }

    /**
     * Creates a frame-by-frame animation with looping.
     *
     * @param timesToNextFrame Times for the transition between frames, in seconds.
     * @param frames The animation frames.
     * @param looping Whether the animation should run indefinitely.
     */
    public FrameByFrameAnimation( double[] timesToNextFrame, List<FrameType> frames, boolean looping ) {
        this.timesToNextFrame = timesToNextFrame;
        this.frames = frames;
        this.maxFrames = frames.size();
        this.looping = looping;
        this.state = AnimationExecutionState.INITIALIZED;
    }
    
    /**
     * Updates the animation.
     *
     * @param delta Time variation.
     */
    public void update( double delta ) {
        
        if ( runBackwards ) {
            if ( currentFrame == maxFrames - 1 && state == AnimationExecutionState.INITIALIZED ) {
                state = AnimationExecutionState.RUNNING;
            }
        } else {
            if ( currentFrame == 0 && state == AnimationExecutionState.INITIALIZED ) {
                state = AnimationExecutionState.RUNNING;
            }
        }
        
        if ( state == AnimationExecutionState.RUNNING ) {
            
            timeCounter += delta;
            
            double timeToWait = timesToNextFrame == null ? 
                timeToNextFrame : 
                timesToNextFrame[currentFrame%timesToNextFrame.length];
            
            if ( timeCounter >= timeToWait ) {
                timeCounter = 0;
                if ( runBackwards ) {
                    currentFrame--;
                } else {
                    currentFrame++;
                }
            }
                
            if ( runBackwards ) {
                if ( currentFrame == -1 ) {
                    if ( looping ) {
                        currentFrame = maxFrames - 1;
                    } else {
                        state = AnimationExecutionState.FINISHED;
                        if ( stopAtLastFrameWhenFinished ) {
                            currentFrame = 0;
                        } else {
                            currentFrame = maxFrames - 1;
                        }
                    }
                }
            } else {
                if ( currentFrame == maxFrames ) {
                    if ( looping ) {
                        currentFrame = 0;
                    } else {
                        state = AnimationExecutionState.FINISHED;
                        if ( stopAtLastFrameWhenFinished ) {
                            currentFrame = maxFrames - 1;
                        } else {
                            currentFrame = 0;
                        }
                    }
                }
            }
            
        }
        
    }

    /**
     * Gets the time for the transition from one frame to the next, in seconds.
     *
     * @return The time to the next frame, in seconds.
     */
    public double getTimeToNextFrame() {
        return timeToNextFrame;
    }

    /**
     * Sets the time for the transition from one frame to the next, in seconds.
     *
     * @param timeToNextFrame The time to the next frame, in seconds.
     */
    public void setTimeToNextFrame( double timeToNextFrame ) {
        this.timeToNextFrame = timeToNextFrame;
    }

    /**
     * Gets the times for the transition from one frame to the next, in seconds.
     *
     * @return The times to the next frames, in seconds.
     */
    public double[] getTimesToNextFrame() {
        return timesToNextFrame;
    }

    /**
     * Sets the times for the transition from one frame to the next, in seconds.
     *
     * @param timesToNextFrame The times to the next frames, in seconds.
     */
    public void setTimesToNextFrame( double[] timesToNextFrame ) {
        this.timesToNextFrame = timesToNextFrame;
    }

    /**
     * Gets the current frame.
     *
     * @return The current animation frame.
     */
    public FrameType getCurrentFrame() {
        return frames.get( currentFrame );
    }

    /**
     * Gets the position of the current animation frame.
     *
     * @return The position of the current animation frame.
     */
    public int getCurrentFramePosition() {
        return currentFrame;
    }

    /**
     * Sets the current animation frame, for programmatic control of frame changes.
     *
     * @param index The frame index.
     */
    public void setCurrentFramePosition( int index ) {
        if ( index >= 0 && index < frames.size() ) {
            currentFrame = index;
        }
    }
    
    /**
     * Gets a specific frame of the animation.
     *
     * @param index The frame index.
     * @return A specific animation frame, or null if it does not exist.
     */
    public FrameType getFrame( int index ) {
        if ( index >= 0 && index < frames.size() ) {
            return frames.get( index );
        }
        return null;
    }

    /**
     * Gets the animation state.
     *
     * @return The animation state.
     */
    public AnimationExecutionState getState() {
        return state;
    }

    /**
     * Returns whether the animation is configured to run in a loop.
     *
     * @return True if the animation is looping, false otherwise.
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * Sets whether the animation should run indefinitely (loop).
     *
     * @param looping Whether the animation should run in a loop.
     */
    public void setLooping( boolean looping ) {
        this.looping = looping;
    }

    /**
     * Returns whether the animation is running backwards.
     *
     * @return True if running backwards, false otherwise.
     */
    public boolean isRunBackwards() {
        return runBackwards;
    }

    /**
     * Sets whether the animation should run backwards.
     *
     * @param runBackwards True if it should run backwards, false otherwise.
     */
    public void setRunBackwards( boolean runBackwards ) {
        this.runBackwards = runBackwards;
        reset();
    }

    /**
     * Returns whether a non-looping animation stops at the last frame when finished.
     *
     * @return True if the animation stops at the last frame when finished, false otherwise.
     */
    public boolean isStopAtLastFrameWhenFinished() {
        return stopAtLastFrameWhenFinished;
    }

    /**
     * Sets whether the animation should stop at the last frame when it finishes. The default
     * behavior is to return to the initial frame.
     *
     * @param stopAtLastFrameWhenFinished True if it should stop at the last frame, false otherwise.
     */
    public void setStopAtLastFrameWhenFinished( boolean stopAtLastFrameWhenFinished ) {
        this.stopAtLastFrameWhenFinished = stopAtLastFrameWhenFinished;
    }

    /**
     * Pauses the animation.
     */
    public void pause() {
        state = AnimationExecutionState.PAUSED;
    }

    /**
     * Resumes the animation.
     */
    public void resume() {
        state = AnimationExecutionState.RUNNING;
    }

    /**
     * Resets the animation.
     */
    public void reset() {
        state = AnimationExecutionState.INITIALIZED;
        if ( runBackwards ) {
            currentFrame = maxFrames - 1;
        } else {
            currentFrame = 0;
        }
        timeCounter = 0;
    }
    
}
