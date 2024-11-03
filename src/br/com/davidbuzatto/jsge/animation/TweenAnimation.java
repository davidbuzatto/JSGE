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
    
    private double deltaPercentagePerSecond;
    private double totalExecutionTime;

    /**
     * Constroi uma nova animação de interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     */
    public TweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        this( properties, componentMapper, updateFunction, ( x ) -> 1.0, 0.0, 0.0 );
    }
    
    /**
     * Constroi uma nova animação de interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     * @param totalExecutionTime Tempo de total de execução da animação.
     */
    public TweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TweenAnimationUpdateFunction<ComponentType> updateFunction,
        double totalExecutionTime ) {
        this( properties, componentMapper, updateFunction, ( x ) -> 1.0, 0.0, totalExecutionTime );
    }
    
    /**
     * Constroi uma nova animação interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     * @param easingFunction A função de suavização da animação.
     * @param deltaPercentagePerSecond Quanto a porcentagem da execução deve variar por segundo. A porcentagem é medida de 0 a 1.
     */
    public TweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TweenAnimationUpdateFunction<ComponentType> updateFunction, 
        DoubleFunction<Double> easingFunction,
        double deltaPercentagePerSecond ) {
        this( properties, componentMapper, updateFunction, easingFunction, deltaPercentagePerSecond, 0.0 );
    }
    
    /**
     * Constroi uma nova animação interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     * @param easingFunction A função de suavização da animação.
     * @param deltaPercentagePerSecond Quanto a porcentagem da execução deve variar por segundo. A porcentagem é medida de 0 a 1.
     * @param totalExecutionTime Tempo de total de execução da animação.
     */
    public TweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TweenAnimationUpdateFunction<ComponentType> updateFunction, 
        DoubleFunction<Double> easingFunction,
        double deltaPercentagePerSecond, 
        double totalExecutionTime ) {
        
        this.properties = properties;
        this.componentMapper = componentMapper;
        this.updateFunction = updateFunction;
        this.easingFunction = easingFunction;
        this.deltaPercentagePerSecond = deltaPercentagePerSecond;
        this.totalExecutionTime = totalExecutionTime;
        
        this.stateContainer = new TweenAnimationStateContainer( TweenAnimationExecutionState.INITIALIZED );
        
    }
    
    /**
     * Atualiza a animação usando a função de atualização definida.
     * 
     * @param delta Variação do tempo.
     */
    public void update( double delta ) {
        updateFunction.accept( delta, deltaPercentagePerSecond, totalExecutionTime, properties, componentMapper, easingFunction, stateContainer );
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
     * Obtém o tempo de execução total animação.
     * 
     * @return O tempo de execução total da animação.
     */
    public double getExecutionTime() {
        return stateContainer.executionTime;
    }

    /**
     * Obtém quanto a porcentagem da execução deve variar por segundo. A porcentagem é medida de 0 a 1.
     * 
     * @return Quanto a porcentagem da execução deve variar por segundo.
     */
    public double getDeltaPercentagePerSecond() {
        return deltaPercentagePerSecond;
    }

    /**
     * Configura quanto a porcentagem da execução deve variar por segundo. A porcentagem é medida de 0 a 1.
     * 
     * @param deltaPercentagePerSecond Quanto a porcentagem da execução deve variar por segundo.
     */
    public void setDeltaPercentagePerSecond( double deltaPercentagePerSecond ) {
        this.deltaPercentagePerSecond = deltaPercentagePerSecond;
    }

    /**
     * Obtém o tempo de total de execução da animação.
     * 
     * @return O tempo de total de execução da animação.
     */
    public double getTotalExecutionTime() {
        return totalExecutionTime;
    }

    /**
     * Configura o tempo de total de execução da animação.
     * 
     * @param totalExecutionTime O tempo de total de execução da animação.
     */
    public void setTotalExecutionTime( double totalExecutionTime ) {
        this.totalExecutionTime = totalExecutionTime;
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
