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

import java.awt.Color;
import java.awt.Paint;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Empacota as propriedades iniciais passadas por parâmetro para um MotionTweenConsumer.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TweenAnimationProperties {
    
    private final Map<String, Object> properties = new HashMap<>();
    
    /**
     * Cria um container de propriedades para uma interpolação de movimento.
     */
    public TweenAnimationProperties() {
    }
    
    /**
     * Método de conveniência para criar um novo MotionTweenAnimationProperties.
     * Os argumentos devem ser passados em pares chave-valor, sendo a chave
     * uma String e o valor qualquer objeto. Mesmo que a chave seja passada
     * como algo diferente de String, toString() será invocado para que a chave
     * seja de fato uma String.
     * 
     * @param args Pares chave-valor que preencherão as propriedades.
     * @return Um novo objeto de propriedades.
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
     * Configura uma propriedade ou sobrescreve uma existente.
     * 
     * @param property A propriedade.
     * @param value O valor da propriedade.
     */
    public void set( String property, Object value ) {
        properties.put( property, value );
    }
    
    /**
     * Obtém uma propriedade existente como Object ou null caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como Object, ou null caso não exista tal propriedade.
     */
    public Object get( String property ) {
        return properties.get( property );
    }
    
    /**
     * Obtém uma propriedade existente como char ou '\0' caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como char, ou '\0' caso não exista tal propriedade.
     */
    public char getChar( String property ) {
        if ( properties.getOrDefault( property, '\0' ) instanceof Character v ) {
            return v;
        }
        return '\0';
    }
    
    /**
     * Obtém uma propriedade existente como int ou 0 caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como int, ou 0 caso não exista tal propriedade.
     */
    public int getInt( String property ) {
        if ( properties.getOrDefault( property, 0 ) instanceof Number v ) {
            return v.intValue();
        }
        return 0;
    }
    
    /**
     * Obtém uma propriedade existente como double ou 0.0 caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como double, ou 0.0 caso não exista tal propriedade.
     */
    public double getDouble( String property ) {
        if ( properties.getOrDefault( property, 0.0 ) instanceof Number v ) {
            return v.doubleValue();
        }
        return 0.0;
    }
    
    /**
     * Obtém uma propriedade existente como boolean ou false caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como boolean, ou false caso não exista tal propriedade.
     */
    public boolean getBoolean( String property ) {
        if ( properties.getOrDefault( property, false ) instanceof Boolean v ) {
            return v;
        }
        return false;
    }
    
    /**
     * Obtém uma propriedade existente como String ou "" caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como String, ou "" caso não exista tal propriedade.
     */
    public String getString( String property ) {
        if ( properties.getOrDefault( property, "" ) instanceof String v ) {
            return v;
        }
        return "";
    }
    
    /**
     * Obtém uma propriedade existente como Color ou Color.BLACK caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como Color, ou Color.BLACK caso não exista tal propriedade.
     */
    public Color getColor( String property ) {
        if ( properties.getOrDefault( property, Color.BLACK ) instanceof Color v ) {
            return v;
        }
        return Color.BLACK;
    }
    
    /**
     * Obtém uma propriedade existente como Paint ou Color.BLACK caso não exista.
     * 
     * @param property A propriedade.
     * @return O valor da propriedade associada, como Paint, ou Color.BLACK caso não exista tal propriedade.
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
