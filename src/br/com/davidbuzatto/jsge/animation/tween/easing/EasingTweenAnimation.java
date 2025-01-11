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
 * Uma animação interpolada com função de suavização.
 * 
 * @param <ComponentType> O tipo do componente que passará pela interpolação.
 * @author Prof. Dr. David Buzatto
 */
public class EasingTweenAnimation<ComponentType> extends TweenAnimationBase<ComponentType> {
    
    private EasingTweenAnimationUpdateFunction<ComponentType> updateFunction;
    private DoubleFunction<Double> easingFunction;
    private double deltaPercentagePerSecond;
    
    /**
     * Constroi uma nova animação interpolada com suavização.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
     * @param easingFunction A função de suavização da animação.
     * @param deltaPercentagePerSecond Quanto a porcentagem da execução deve variar por segundo. A porcentagem é medida de 0 a 1.
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
     * Configura a função de atualização.
     * 
     * @param updateFunction A função.
     */
    public void setUpdateFunction( EasingTweenAnimationUpdateFunction<ComponentType> updateFunction ) {
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
    
}
