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
package br.com.davidbuzatto.jsge.animation.tween.timing;

import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationBase;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;

/**
 * Uma animação interpolada temporizada.
 * 
 * @param <ComponentType> O tipo do componente que passará pela interpolação.
 * @author Prof. Dr. David Buzatto
 */
public class TimingTweenAnimation<ComponentType> extends TweenAnimationBase<ComponentType> {
    
    private TimingTweenAnimationUpdateFunction<ComponentType> updateFunction;
    private double totalExecutionTime;
    
    /**
     * Constroi uma nova animação interpolada temporizada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     * @param totalExecutionTime Tempo de total de execução da animação.
     */
    public TimingTweenAnimation( 
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TimingTweenAnimationUpdateFunction<ComponentType> updateFunction, 
        double totalExecutionTime ) {
        super( properties, componentMapper );
        this.updateFunction = updateFunction;
        this.totalExecutionTime = totalExecutionTime;
    }
    
    @Override
    public void update( double delta ) {
        updateFunction.accept( delta, totalExecutionTime, properties, componentMapper, stateContainer );
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
     * Configura a função de atualização.
     * 
     * @param updateFunction A função.
     */
    public void setUpdateFunction( TimingTweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        this.updateFunction = updateFunction;
    }
    
}
