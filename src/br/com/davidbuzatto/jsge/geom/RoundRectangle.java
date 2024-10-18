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
 * Classe para representação de um retângulo com cantos arrendondados em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RoundRectangle implements Drawable {

    public double x;
    public double y;
    public double width;
    public double height;
    public double roundness;

    /**
     * Cria um novo retângulo com cantos arredondados com valores padrão.
     */
    public RoundRectangle() {
    }

    /**
     * Cria um novo retângulo com cantos arredondados.
     * 
     * @param x coordenada x do vértice superior esquerdo.
     * @param y coordenada y do vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param roundness arredondamento dos cantos.
     */
    public RoundRectangle( double x, double y, double width, double height, double roundness ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roundness = roundness;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawRoundRectangle( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillRoundRectangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "RoundRectangle[%.2f, %.2f, %.2f, %.2f, %.2f]", x, y, width, height, roundness );
    }

}
