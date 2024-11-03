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
package br.com.davidbuzatto.jsge.core.utils;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * Interface com métodos utilitários para manipular strokes (contornos).
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface StrokeUtils {
    
    public static BasicStroke cloneStrokeLineWidth( float lineWidth, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( lineWidth, s.getEndCap(), s.getLineJoin(), s.getMiterLimit(), s.getDashArray(), s.getDashPhase() );
    }
    
    public static BasicStroke cloneStrokeEndCap( int endCap, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), endCap, s.getLineJoin(), s.getMiterLimit(), s.getDashArray(), s.getDashPhase() );
    }
    
    public static BasicStroke cloneStrokeLineJoin( int lineJoin, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), lineJoin, s.getMiterLimit(), s.getDashArray(), s.getDashPhase() );
    }
    
    public static BasicStroke cloneStrokeMiterLimit( float miterLimit, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), s.getLineJoin(), miterLimit, s.getDashArray(), s.getDashPhase() );
    }
    
    public static BasicStroke cloneStrokeDashArray( float[] dashArray, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), s.getLineJoin(), s.getMiterLimit(), dashArray, s.getDashPhase() );
    }
    
    public static BasicStroke cloneStrokeDashPhase( float dashPhase, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), s.getLineJoin(), s.getMiterLimit(), s.getDashArray(), dashPhase );
    }
    
}
