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
 * A property mapper for components.
 *
 * @param <ComponentType> The component type.
 * @author Prof. Dr. David Buzatto
 */
public abstract class TweenAnimationComponentMapper<ComponentType> {

    /**
     * The component that will be manipulated through the mapper.
     */
    protected ComponentType component;

    /**
     * Creates the property mapper.
     *
     * @param component The component to which data will be redirected.
     */
    public TweenAnimationComponentMapper( ComponentType component ) {
        this.component = component;
    }

    /**
     * Gets the component from the mapper.
     *
     * @return The component.
     */
    public ComponentType getComponent() {
        return component;
    }

    /**
     * Maps a property to the component.
     *
     * @param property The property.
     * @param value The property value.
     */
    public abstract void set( String property, Object value );

    /**
     * Gets a property from the component.
     *
     * @param property The property.
     * @return The property value, or null if it does not exist.
     */
    public abstract Object get( String property );
    
}
