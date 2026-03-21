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
package br.com.davidbuzatto.jsge.animation.tween.timing;

import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationStateContainer;

/**
 * Functional interface for the update functions of timing-based tween animations.
 *
 * These functions are responsible for updating the state of an animation.
 *
 * @param <ComponentType> The component type.
 * @author Prof. Dr. David Buzatto
 */
@FunctionalInterface
public interface TimingTweenAnimationUpdateFunction<ComponentType> {

    /**
     * Timing-based update function.
     *
     * @param delta Time variation.
     * @param totalExecutionTime Total execution time of the animation.
     * @param properties Properties for initializing and maintaining the animation state.
     * @param componentMapper Property mapper that acts as the interface between the manipulated component and the update function.
     * @param stateContainer Holds the animation state. It is the developer's responsibility to manage its attributes.
     */
    public void accept( 
        double delta, 
        double totalExecutionTime,
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TweenAnimationStateContainer stateContainer
    );
    
}
