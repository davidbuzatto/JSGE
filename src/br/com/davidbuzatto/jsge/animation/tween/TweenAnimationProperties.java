/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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

import java.awt.Color;
import java.awt.Paint;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Packages the initial properties passed as parameters to a MotionTweenConsumer.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TweenAnimationProperties {

    private final Map<String, Object> properties = new HashMap<>();

    /**
     * Creates a property container for a motion tween.
     */
    public TweenAnimationProperties() {
    }

    /**
     * Convenience method for creating a new MotionTweenAnimationProperties.
     * Arguments must be passed as key-value pairs, where the key is a String
     * and the value is any object. Even if the key is passed as something other
     * than a String, toString() will be invoked so the key is effectively a String.
     *
     * @param args Key-value pairs that will populate the properties.
     * @return A new properties object.
     */
    public static TweenAnimationProperties of( Object... args ) {
        
        TweenAnimationProperties p = new TweenAnimationProperties();
        List<Object> argsLi = Arrays.asList( args );
        
        if ( argsLi.size() % 2 != 0 ) {
            argsLi.add( 0 );
        }
        
        for ( int i = 0; i < argsLi.size(); i += 2 ) {
            String property = argsLi.get(i) == null ? "null" : argsLi.get(i).toString();
            p.set( property, argsLi.get(i+1) );
        }
        
        return p;
        
    }
    
    /**
     * Sets a property or overwrites an existing one.
     *
     * @param property The property.
     * @param value The property value.
     */
    public void set( String property, Object value ) {
        properties.put( property, value );
    }

    /**
     * Gets an existing property as Object, or null if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as Object, or null if no such property exists.
     */
    public Object get( String property ) {
        return properties.get( property );
    }

    /**
     * Gets an existing property as char, or '\0' if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as char, or '\0' if no such property exists.
     */
    public char getChar( String property ) {
        if ( properties.getOrDefault( property, '\0' ) instanceof Character v ) {
            return v;
        }
        return '\0';
    }
    
    /**
     * Gets an existing property as int, or 0 if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as int, or 0 if no such property exists.
     */
    public int getInt( String property ) {
        if ( properties.getOrDefault( property, 0 ) instanceof Number v ) {
            return v.intValue();
        }
        return 0;
    }
    
    /**
     * Gets an existing property as double, or 0.0 if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as double, or 0.0 if no such property exists.
     */
    public double getDouble( String property ) {
        if ( properties.getOrDefault( property, 0.0 ) instanceof Number v ) {
            return v.doubleValue();
        }
        return 0.0;
    }
    
    /**
     * Gets an existing property as boolean, or false if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as boolean, or false if no such property exists.
     */
    public boolean getBoolean( String property ) {
        if ( properties.getOrDefault( property, false ) instanceof Boolean v ) {
            return v;
        }
        return false;
    }
    
    /**
     * Gets an existing property as String, or "" if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as String, or "" if no such property exists.
     */
    public String getString( String property ) {
        if ( properties.getOrDefault( property, "" ) instanceof String v ) {
            return v;
        }
        return "";
    }
    
    /**
     * Gets an existing property as Color, or Color.BLACK if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as Color, or Color.BLACK if no such property exists.
     */
    public Color getColor( String property ) {
        if ( properties.getOrDefault( property, Color.BLACK ) instanceof Color v ) {
            return v;
        }
        return Color.BLACK;
    }
    
    /**
     * Gets an existing property as Paint, or Color.BLACK if it does not exist.
     *
     * @param property The property.
     * @return The associated property value as Paint, or Color.BLACK if no such property exists.
     */
    public Paint getPaint( String property ) {
        if ( properties.getOrDefault( property, Color.BLACK ) instanceof Paint v ) {
            return v;
        }
        return Color.BLACK;
    }

    @Override
    public String toString() {
        return properties.toString();
    }
    
}
