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
package br.com.davidbuzatto.jsge.animation.tween;

/**
 * A tween animation.
 *
 * @param <ComponentType> The type of the component that will undergo interpolation.
 * @author Prof. Dr. David Buzatto
 */
public class TweenAnimation<ComponentType> extends TweenAnimationBase<ComponentType> {

    protected TweenAnimationUpdateFunction<ComponentType> updateFunction;

    /**
     * Constructs a new tween animation.
     *
     * @param properties The properties used to control the animation.
     * @param componentMapper A property mapper for the component that will be manipulated in the animation.
     * @param updateFunction The animation update function.
     */
    public TweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        super( properties, componentMapper );
        this.updateFunction = updateFunction;
    }
    
    @Override
    public void update( double delta ) {
        updateFunction.accept( delta, properties, componentMapper, stateContainer );
    }

    /**
     * Sets the update function.
     *
     * @param updateFunction The function.
     */
    public void setUpdateFunction( TweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        this.updateFunction = updateFunction;
    }
    
}
