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
package br.com.davidbuzatto.jsge.animation.tween;

/**
 * Uma animação interpolada.
 * 
 * @param <ComponentType> O tipo do componente que passará pela interpolação.
 * @author Prof. Dr. David Buzatto
 */
public class TweenAnimation<ComponentType> extends TweenAnimationBase<ComponentType> {
    
    protected TweenAnimationUpdateFunction<ComponentType> updateFunction;
    
    /**
     * Constroi uma nova animação interpolada.
     * 
     * @param properties As propriedades utilizadas para o controle da animação.
     * @param componentMapper Um mapeador de propriedades do componente que será manipulado na animação.
     * @param updateFunction A função de atualização de animação.
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
     * Configura a função de atualização.
     * 
     * @param updateFunction A função.
     */
    public void setUpdateFunction( TweenAnimationUpdateFunction<ComponentType> updateFunction ) {
        this.updateFunction = updateFunction;
    }
    
}
