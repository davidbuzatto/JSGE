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
public class EllipseSector implements Drawable {
    
    public double x;
    public double y;
    public double radiusH;
    public double radiusV;
    public double startAngle;
    public double endAngle;

    /**
     * Cria um novo setor de elipse com valores padrão.
     */
    public EllipseSector() {
    }

    /**
     * Cria um novo setor de elipse.
     * 
     * @param x coordenada x do centro.
     * @param y coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em gradus (sentido horário).
     */
    public EllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle ) {
        this.x = x;
        this.y = y;
        this.radiusH = radiusH;
        this.radiusV = radiusV;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawEllipseSector( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillEllipseSector( this, color );
    }

    @Override
    public String toString() {
        return String.format( "EllipseSector[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x, y, radiusH, radiusV, startAngle, endAngle );
    }

}
