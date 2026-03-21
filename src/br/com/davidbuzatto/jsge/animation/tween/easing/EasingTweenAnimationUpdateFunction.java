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
package br.com.davidbuzatto.jsge.animation.tween.easing;

import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationStateContainer;
import java.util.function.DoubleFunction;

/**
 * Functional interface for the update functions of easing tween animations.
 *
 * These functions are responsible for updating the state of an animation.
 *
 * @param <ComponentType> The component type.
 * @author Prof. Dr. David Buzatto
 */
@FunctionalInterface
public interface EasingTweenAnimationUpdateFunction<ComponentType> {

    /**
     * Easing update function.
     *
     * @param delta Time variation.
     * @param deltaPercentagePerSecond How much the execution percentage should change per second. The percentage is measured from 0 to 1.
     * @param properties Properties for initializing and maintaining the animation state.
     * @param componentMapper Property mapper that acts as the interface between the manipulated component and the update function.
     * @param easingFunction Easing function.
     * @param stateContainer Holds the animation state. It is the developer's responsibility to manage its attributes.
     */
    public void accept( 
        double delta, 
        double deltaPercentagePerSecond,
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        DoubleFunction<Double> easingFunction,
        TweenAnimationStateContainer stateContainer
    );
    
}
