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
package br.com.davidbuzatto.jsge.core.utils;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * Interface with utility methods for manipulating strokes (outlines).
 *
 * @author Prof. Dr. David Buzatto
 */
public interface StrokeUtils {

    /**
     * Creates a new stroke, copying the given stroke and changing the line
     * width.
     *
     * @param lineWidth The new line width.
     * @param stroke The base stroke.
     * @return A new stroke.
     */
    public static BasicStroke cloneStrokeLineWidth( float lineWidth, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( lineWidth, s.getEndCap(), s.getLineJoin(), s.getMiterLimit(), s.getDashArray(), s.getDashPhase() );
    }
    
    /**
     * Creates a new stroke, copying the given stroke and changing the line
     * end cap style.
     *
     * @param endCap The line end cap style.
     * @param stroke The base stroke.
     * @return A new stroke.
     */
    public static BasicStroke cloneStrokeEndCap( int endCap, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), endCap, s.getLineJoin(), s.getMiterLimit(), s.getDashArray(), s.getDashPhase() );
    }
    
    /**
     * Creates a new stroke, copying the given stroke and changing the line
     * join style.
     *
     * @param lineJoin The line join style.
     * @param stroke The base stroke.
     * @return A new stroke.
     */
    public static BasicStroke cloneStrokeLineJoin( int lineJoin, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), lineJoin, s.getMiterLimit(), s.getDashArray(), s.getDashPhase() );
    }
    
    /**
     * Creates a new stroke, copying the given stroke and changing the miter
     * join trim limit.
     *
     * @param miterLimit The miter join trim limit.
     * @param stroke The base stroke.
     * @return A new stroke.
     */
    public static BasicStroke cloneStrokeMiterLimit( float miterLimit, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), s.getLineJoin(), miterLimit, s.getDashArray(), s.getDashPhase() );
    }
    
    /**
     * Creates a new stroke, copying the given stroke and changing the dash
     * pattern.
     *
     * @param dashArray The dash pattern.
     * @param stroke The base stroke.
     * @return A new stroke.
     */
    public static BasicStroke cloneStrokeDashArray( float[] dashArray, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), s.getLineJoin(), s.getMiterLimit(), dashArray, s.getDashPhase() );
    }
    
    /**
     * Creates a new stroke, copying the given stroke and changing the
     * dash pattern offset.
     *
     * @param dashPhase The dash pattern offset.
     * @param stroke The base stroke.
     * @return A new stroke.
     */
    public static BasicStroke cloneStrokeDashPhase( float dashPhase, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), s.getLineJoin(), s.getMiterLimit(), s.getDashArray(), dashPhase );
    }
    
    /**
     * Creates a new stroke, copying the given stroke and changing both the
     * dash pattern and the dash pattern offset.
     *
     * @param dashArray The dash pattern.
     * @param dashPhase The dash pattern offset.
     * @param stroke The base stroke.
     * @return A new stroke.
     */
    public static BasicStroke cloneStrokeDashArrayAndPhase( float[] dashArray, float dashPhase, Stroke stroke ) {
        BasicStroke s = (BasicStroke) stroke;
        return new BasicStroke( s.getLineWidth(), s.getEndCap(), s.getLineJoin(), s.getMiterLimit(), dashArray, dashPhase );
    }
    
}
