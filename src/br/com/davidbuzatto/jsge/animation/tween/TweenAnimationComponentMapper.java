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

/**
 * Um mapeador de propriedades para componentes.
 * 
 * @param <ComponentType> Tipo do componente.
 * @author Prof. Dr. David Buzatto
 */
public abstract class TweenAnimationComponentMapper<ComponentType> {
    
    /**
     * O componente que será manipulado através do mapeador.
     */
    protected ComponentType component;
    
    /**
     * Cria o mapeador de propriedades.
     * 
     * @param component O componente em que os dados serão redirecionados.
     */
    public TweenAnimationComponentMapper( ComponentType component ) {
        this.component = component;
    }

    /**
     * Obtém o componente do mapeador.
     * 
     * @return O componente.
     */
    public ComponentType getComponent() {
        return component;
    }
    
    /**
     * Mapeia uma propriedade para o componente.
     * 
     * @param property A propriedade.
     * @param value O valor da propriedade.
     */
    public abstract void set( String property, Object value );
    
    /**
     * Obtém uma propriedade do componente.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade ou null caso não exista.
     */
    public abstract Object get( String property );
    
}
