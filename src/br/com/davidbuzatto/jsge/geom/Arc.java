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

import br.com.davidbuzatto.jsge.core.Engine;
import java.awt.Color;

/**
 * Classe para representação de um arco em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Arc implements Drawable {
    
    public double x;
    public double y;
    public double radiusH;
    public double radiusV;
    public double startAngle;
    public double endAngle;

    /**
     * Cria um arco com valores padrão.
     */
    public Arc() {
    }

    /**
     * Cria um novo arco.
     * 
     * @param x coordenada x do centro.
     * @param y coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em gradus (sentido horário).
     */
    public Arc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle ) {
        this.x = x;
        this.y = y;
        this.radiusH = radiusH;
        this.radiusV = radiusV;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public Arc( double x, double y, double radius, double startAngle, double endAngle ) {
        this( x, y, radius, radius, startAngle, endAngle );
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawArc( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillArc( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Arc[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x, y, radiusH, radiusV, startAngle, endAngle );
    }

}
