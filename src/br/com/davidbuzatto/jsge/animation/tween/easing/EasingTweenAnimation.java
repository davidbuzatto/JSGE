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

import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationBase;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import java.util.function.DoubleFunction;

/**
 * A tween animation with an easing function.
 *
 * @param <ComponentType> The type of the component that will undergo interpolation.
 * @author Prof. Dr. David Buzatto
 */
public class EasingTweenAnimation<ComponentType> extends TweenAnimationBase<ComponentType> {

    private EasingTweenAnimationUpdateFunction<ComponentType> updateFunction;
    private DoubleFunction<Double> easingFunction;
    private double deltaPercentagePerSecond;

    /**
     * Constructs a new easing tween animation.
     *
     * @param properties The properties used to control the animation.
     * @param componentMapper A property mapper for the component that will be manipulated in the animation.
     * @param updateFunction The animation update function.
     * @param easingFunction The animation easing function.
     * @param deltaPercentagePerSecond How much the execution percentage should change per second. The percentage is measured from 0 to 1.
     */
    public EasingTweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        EasingTweenAnimationUpdateFunction<ComponentType> updateFunction, 
        DoubleFunction<Double> easingFunction,
        double deltaPercentagePerSecond ) {
        
        super( properties, componentMapper );
        this.updateFunction = updateFunction;
        this.easingFunction = easingFunction;
        this.deltaPercentagePerSecond = deltaPercentagePerSecond;
        
    }
    
    @Override
    public void update( double delta ) {
        updateFunction.accept( delta, deltaPercentagePerSecond, properties, componentMapper, easingFunction, stateContainer );
    }

    /**
     * Gets how much the execution percentage should change per second. The percentage is measured from 0 to 1.
     *
     * @return How much the execution percentage changes per second.
     */
    public double getDeltaPercentagePerSecond() {
        return deltaPercentagePerSecond;
    }

    /**
     * Sets how much the execution percentage should change per second. The percentage is measured from 0 to 1.
     *
     * @param deltaPercentagePerSecond How much the execution percentage should change per second.
     */
    public void setDeltaPercentagePerSecond( double deltaPercentagePerSecond ) {
        this.deltaPercentagePerSecond = deltaPercentagePerSecond;
    }

    /**
     * Sets the update function.
     *
     * @param updateFunction The function.
     */
    public void setUpdateFunction( EasingTweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        this.updateFunction = updateFunction;
    }

    /**
     * Sets the easing function.
     *
     * @param easingFunction The function.
     */
    public void setEasingFunction( DoubleFunction<Double> easingFunction ) {
        this.easingFunction = easingFunction;
    }
    
}
