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
package br.com.davidbuzatto.jsge.geom;

import br.com.davidbuzatto.jsge.core.Drawable;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.awt.Paint;

/**
 * Classe para representação de uma elipse em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Ellipse implements Drawable {
    
    /**
     * Coordenada x do centro.
     */
    public double x;
    
    /**
     * Coordenada y do centro.
     */
    public double y;
    
    /**
     * Raio horizontal.
     */
    public double radiusH;
    
    /**
     * Raio vertical.
     */
    public double radiusV;

    /**
     * Cria uma nova elipse com valores padrão.
     */
    public Ellipse() {
    }

    /**
     * Cria uma nova elipse.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     */
    public Ellipse( double x, double y, double radiusH, double radiusV ) {
        this.x = x;
        this.y = y;
        this.radiusH = radiusH;
        this.radiusV = radiusV;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawEllipse( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillEllipse( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Ellipse[%.2f, %.2f, %.2f, %.2f]", x, y, radiusH, radiusV );
    }

}
