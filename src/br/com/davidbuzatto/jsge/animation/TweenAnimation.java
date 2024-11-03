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

import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationExecutionState;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationStateContainer;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import java.util.function.DoubleFunction;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationUpdateFunction;

/**
 * Uma animação interpolada.
 * 
 * @param <ComponentType> O tipo do componente que passará pela interpolação.
 * @author Prof. Dr. David Buzatto
 */
public class TweenAnimation<ComponentType> {
    
    private TweenAnimationComponentMapper<ComponentType> componentMapper;
    private TweenAnimationUpdateFunction<ComponentType> updateFunction;
    private DoubleFunction<Double> easingFunction;
    private TweenAnimationProperties properties;
    private TweenAnimationStateContainer stateContainer;

    /**
     * Constroi uma nova animação de interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param mapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     */
    public TweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> mapper, 
        TweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        this( properties, mapper, updateFunction, null );
    }
    
    /**
     * Constroi uma nova animação interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param mapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     * @param easingFunction A função de suavização da animação.
     */
    public TweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> mapper, 
        TweenAnimationUpdateFunction<ComponentType> updateFunction, 
        DoubleFunction<Double> easingFunction ) {
        
        this.properties = properties;
        this.componentMapper = mapper;
        this.updateFunction = updateFunction;
        this.easingFunction = easingFunction;
        
        stateContainer = new TweenAnimationStateContainer( TweenAnimationExecutionState.INITIALIZED );
        
    }
    
    /**
     * Atualiza a animação usando a função de atualização definida.
     * 
     * @param delta Variação do tempo.
     */
    public void update( double delta ) {
        updateFunction.accept(delta, properties, componentMapper, easingFunction, stateContainer );
    }
    
    /**
     * Obtém o componente manipulado na animação.
     * 
     * @return O componente manipulado na animação.
     */
    public ComponentType getComponent() {
        return componentMapper.getComponent();
    }
    
    /**
     * Obtém o estado da animação.
     * 
     * @return O estado da animação.
     */
    public TweenAnimationExecutionState getState() {
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
        stateContainer.state = TweenAnimationExecutionState.PAUSED;
    }
    
    /**
     * Retoma uma animação. Esse método apenas muda o estado da animação.
     * O processo de retomar de fato deve ser implementado na função
     * de atualização.
     */
    public void resume() {
        stateContainer.state = TweenAnimationExecutionState.RUNNING;
    }
    
    /**
     * Reseta uma animação. Esse método apenas muda o estado da animação.
     * O processo de resetar de fato deve ser implementado na função
     * de atualização.
     */
    public void reset() {
        stateContainer.state = TweenAnimationExecutionState.INITIALIZED;
        stateContainer.percentage = 0.0;
    }

    /**
     * Configura o mapeador.
     * 
     * @param componentMapper Mapeador.
     */
    public void setComponentMapper( TweenAnimationComponentMapper<ComponentType> componentMapper ) {
        this.componentMapper = componentMapper;
    }

    /**
     * Configura a função de atualização.
     * 
     * @param updateFunction A função.
     */
    public void setUpdateFunction( TweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        this.updateFunction = updateFunction;
    }

    /**
     * Configura a função de suavização.
     * 
     * @param easingFunction A função.
     */
    public void setEasingFunction( DoubleFunction<Double> easingFunction ) {
        this.easingFunction = easingFunction;
    }

    /**
     * Configura as propriedades.
     * 
     * @param properties As propriedades.
     */
    public void setProperties( TweenAnimationProperties properties ) {
        this.properties = properties;
    }
    
}
