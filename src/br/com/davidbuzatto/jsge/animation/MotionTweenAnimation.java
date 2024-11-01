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
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationExecutionState;
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

    /**
     * Constroi uma nova animação de interpolação de movimento.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param proxy Um proxy do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     */
    public MotionTweenAnimation( 
        MotionTweenAnimationProperties properties,
        ComponentProxy<ComponentType> proxy, 
        MotionTweenAnimationConsumer<ComponentType> updateFunction ) {
        this( properties, proxy, updateFunction, null );
    }
    
    /**
     * Constroi uma nova animação de interpolação de movimento.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param proxy Um proxy do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     * @param easingFunction A função de suavização da animação.
     */
    public MotionTweenAnimation( 
        MotionTweenAnimationProperties properties,
        ComponentProxy<ComponentType> proxy, 
        MotionTweenAnimationConsumer<ComponentType> updateFunction, 
        DoubleFunction<Double> easingFunction ) {
        
        this.properties = properties;
        this.componentProxy = proxy;
        this.updateFunction = updateFunction;
        this.easingFunction = easingFunction;
        
        stateContainer = new MotionTweenAnimationStateContainer( MotionTweenAnimationExecutionState.INITIALIZED );
        
    }
    
    /**
     * Atualiza a animação usando a função de atualização definida.
     * 
     * @param delta Variação do tempo.
     */
    public void update( double delta ) {
        updateFunction.accept( delta, properties, componentProxy, easingFunction, stateContainer );
    }
    
    /**
     * Obtém o componente manipulado na animação.
     * 
     * @return O componente manipulado na animação.
     */
    public ComponentType getComponent() {
        return componentProxy.getComponent();
    }
    
    /**
     * Obtém o estado da animação.
     * 
     * @return O estado da animação.
     */
    public MotionTweenAnimationExecutionState getState() {
        return stateContainer.state;
    }
    
    /**
     * Obtém a porcentagem da animação.
     * 
     * @return A porcentagem da execução da animação.
     */
    public double getPercentage() {
        return stateContainer.percentage;
    }
    
    /**
     * Pausa a animação. Esse método apenas muda o estado da animação.
     * O processo de pausar de fato deve ser implementado na função
     * de atualização.
     */
    public void pause() {
        stateContainer.state = MotionTweenAnimationExecutionState.PAUSED;
    }
    
    /**
     * Retoma uma animação. Esse método apenas muda o estado da animação.
     * O processo de retomar de fato deve ser implementado na função
     * de atualização.
     */
    public void resume() {
        stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
    }
    
    /**
     * Reseta uma animação. Esse método apenas muda o estado da animação.
     * O processo de resetar de fato deve ser implementado na função
     * de atualização.
     */
    public void reset() {
        stateContainer.state = MotionTweenAnimationExecutionState.INITIALIZED;
        stateContainer.percentage = 0.0;
    }

    public void setComponentProxy( ComponentProxy<ComponentType> componentProxy ) {
        this.componentProxy = componentProxy;
    }

    public void setUpdateFunction( MotionTweenAnimationConsumer<ComponentType> updateFunction ) {
        this.updateFunction = updateFunction;
    }

    public void setEasingFunction( DoubleFunction<Double> easingFunction ) {
        this.easingFunction = easingFunction;
    }

    public void setProperties( MotionTweenAnimationProperties properties ) {
        this.properties = properties;
    }
    
}
