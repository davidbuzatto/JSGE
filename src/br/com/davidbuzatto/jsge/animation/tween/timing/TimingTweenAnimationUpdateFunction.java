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

import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationStateContainer;

/**
 * Interface funcional para as funções de atualização das animações interpoladas
 * temporizadas.
 * 
 * São essas funções que são responsáveis em atualizar o estado de uma animação.
 * 
 * @param <ComponentType> Tipo do componente.
 * @author Prof. Dr. David Buzatto
 */
@FunctionalInterface
public interface TimingTweenAnimationUpdateFunction<ComponentType> {
    
    /**
     * Função de atualização temporizada.
     * 
     * @param delta Variação no tempo.
     * @param totalExecutionTime Tempo de total de execução da animação.
     * @param properties Propriedades para inicialização e manutenção do estado da animação.
     * @param componentMapper Mapeador de propriedades que realiza a interface entre o componente manipulado e a função de atualização.
     * @param stateContainer Mantém o estado da animação. ´É responsabilidade do desenvolvedor lidar com seus atributos.
     */
    public void accept( 
        double delta, 
        double totalExecutionTime,
        TweenAnimationProperties properties,
        TweenAnimationComponentMapper<ComponentType> componentMapper, 
        TweenAnimationStateContainer stateContainer
    );
    
}
