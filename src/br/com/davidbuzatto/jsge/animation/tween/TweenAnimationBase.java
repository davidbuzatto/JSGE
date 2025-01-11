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
 * Uma animação interpolada base.
 * 
 * @param <ComponentType> O tipo do componente que passará pela interpolação.
 * @author Prof. Dr. David Buzatto
 */
public abstract class TweenAnimationBase<ComponentType> {
    
    protected TweenAnimationProperties properties;
    protected TweenAnimationComponentMapper<ComponentType> componentMapper;
    protected TweenAnimationStateContainer stateContainer;
    
    /**
     * Constroi uma nova animação interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     */
    public TweenAnimationBase( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper ) {
        this.properties = properties;
        this.componentMapper = componentMapper;
        this.stateContainer = new TweenAnimationStateContainer( AnimationExecutionState.INITIALIZED );
    }
    
    /**
     * Atualiza a animação usando a função de atualização definida.
     * 
     * @param delta Variação do tempo.
     */
    public abstract void update( double delta );
    
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
    public AnimationExecutionState getState() {
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
     * Obtém o tempo de execução total animação.
     * 
     * @return O tempo de execução total da animação.
     */
    public double getExecutionTime() {
        return stateContainer.executionTime;
    }
    
    /**
     * Pausa a animação. Esse método apenas muda o estado da animação.
     * O processo de pausar de fato deve ser implementado na função
     * de atualização.
     */
    public void pause() {
        stateContainer.state = AnimationExecutionState.PAUSED;
    }
    
    /**
     * Retoma a animação. Esse método apenas muda o estado da animação.
     * O processo de retomar de fato deve ser implementado na função
     * de atualização.
     */
    public void resume() {
        stateContainer.state = AnimationExecutionState.RUNNING;
    }
    
    /**
     * Reseta a animação. Esse método apenas muda o estado da animação.
     * O processo de resetar de fato deve ser implementado na função
     * de atualização.
     */
    public void reset() {
        stateContainer.state = AnimationExecutionState.INITIALIZED;
        stateContainer.percentage = 0.0;
        stateContainer.executionTime = 0.0;
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
     * Configura as propriedades.
     * 
     * @param properties As propriedades.
     */
    public void setProperties( TweenAnimationProperties properties ) {
        this.properties = properties;
    }
    
}
