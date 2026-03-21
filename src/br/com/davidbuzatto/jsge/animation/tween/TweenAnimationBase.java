/*
 * Copyright (C) 2025 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.animation.tween;

import br.com.davidbuzatto.jsge.animation.AnimationExecutionState;

/**
 * A base tween animation.
 *
 * @param <ComponentType> The type of the component that will undergo interpolation.
 * @author Prof. Dr. David Buzatto
 */
public abstract class TweenAnimationBase<ComponentType> {

    protected TweenAnimationProperties properties;
    protected TweenAnimationComponentMapper<ComponentType> componentMapper;
    protected TweenAnimationStateContainer stateContainer;

    /**
     * Constructs a new tween animation.
     *
     * @param properties The properties used to control the animation.
     * @param componentMapper A property mapper for the component that will be manipulated in the animation.
     */
    public TweenAnimationBase( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper ) {
        this.properties = properties;
        this.componentMapper = componentMapper;
        this.stateContainer = new TweenAnimationStateContainer( AnimationExecutionState.INITIALIZED );
    }
    
    /**
     * Updates the animation using the defined update function.
     *
     * @param delta Time variation.
     */
    public abstract void update( double delta );

    /**
     * Gets the component manipulated in the animation.
     *
     * @return The component manipulated in the animation.
     */
    public ComponentType getComponent() {
        return componentMapper.getComponent();
    }

    /**
     * Gets the animation state.
     *
     * @return The animation state.
     */
    public AnimationExecutionState getState() {
        return stateContainer.state;
    }

    /**
     * Gets the animation percentage.
     *
     * @return The execution percentage of the animation.
     */
    public double getPercentage() {
        return stateContainer.percentage;
    }

    /**
     * Gets the total execution time of the animation.
     *
     * @return The total execution time of the animation.
     */
    public double getExecutionTime() {
        return stateContainer.executionTime;
    }

    /**
     * Pauses the animation. This method only changes the animation state.
     * The actual pausing logic must be implemented in the update function.
     */
    public void pause() {
        stateContainer.state = AnimationExecutionState.PAUSED;
    }

    /**
     * Resumes the animation. This method only changes the animation state.
     * The actual resuming logic must be implemented in the update function.
     */
    public void resume() {
        stateContainer.state = AnimationExecutionState.RUNNING;
    }

    /**
     * Resets the animation. This method only changes the animation state.
     * The actual reset logic must be implemented in the update function.
     */
    public void reset() {
        stateContainer.state = AnimationExecutionState.INITIALIZED;
        stateContainer.percentage = 0.0;
        stateContainer.executionTime = 0.0;
    }

    /**
     * Sets the component mapper.
     *
     * @param componentMapper The mapper.
     */
    public void setComponentMapper( TweenAnimationComponentMapper<ComponentType> componentMapper ) {
        this.componentMapper = componentMapper;
    }

    /**
     * Sets the properties.
     *
     * @param properties The properties.
     */
    public void setProperties( TweenAnimationProperties properties ) {
        this.properties = properties;
    }
    
}
