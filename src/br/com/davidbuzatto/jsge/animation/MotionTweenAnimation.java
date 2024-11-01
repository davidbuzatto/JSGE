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
package br.com.davidbuzatto.jsge.animation;

import br.com.davidbuzatto.jsge.animation.proxy.ComponentProxy;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationState;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationStateContainer;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationProperties;
import java.util.function.DoubleFunction;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationConsumer;

/**
 * Uma animação de interpolação de movimento.
 * 
 * @param <ComponentType> O tipo do componente que passará pela interpolação.
 * @author Prof. Dr. David Buzatto
 */
public class MotionTweenAnimation<ComponentType> {
    
    private ComponentProxy<ComponentType> componentProxy;
    private MotionTweenAnimationConsumer<ComponentType> updateFunction;
    private DoubleFunction<Double> easingFunction;
    private MotionTweenAnimationProperties properties;
    private MotionTweenAnimationStateContainer stateContainer;

    public MotionTweenAnimation( 
        MotionTweenAnimationProperties properties,
        ComponentProxy<ComponentType> proxy, 
        MotionTweenAnimationConsumer<ComponentType> updateFunction ) {
        this( properties, proxy, updateFunction, null );
    }
    
    public MotionTweenAnimation( 
        MotionTweenAnimationProperties properties,
        ComponentProxy<ComponentType> proxy, 
        MotionTweenAnimationConsumer<ComponentType> updateFunction, 
        DoubleFunction<Double> easingFunction ) {
        
        this.properties = properties;
        this.componentProxy = proxy;
        this.updateFunction = updateFunction;
        this.easingFunction = easingFunction;
        stateContainer = new MotionTweenAnimationStateContainer( MotionTweenAnimationState.INITIALIZED );
        
    }
    
    public void update( double delta ) {
        updateFunction.accept( delta, properties, componentProxy, easingFunction, stateContainer );
    }
    
    public ComponentType getComponent() {
        return componentProxy.getComponent();
    }
    
}
