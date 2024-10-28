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
 * Classe para representação de um retângulo em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Rectangle implements Drawable {

    /**
     * Coordenada x do vértice superior esquerdo.
     */
    public double x;
    
    /**
     * Coordenada y do vértice superior esquerdo.
     */
    public double y;
    
    /**
     * Largura.
     */
    public double width;
    
    /**
     * Altura.
     */
    public double height;

    /**
     * Cria um novo retângulo com valores padrão.
     */
    public Rectangle() {
    }

    /**
     * Cria um novo retângulo.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     */
    public Rectangle( double x, double y, double width, double height ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw( EngineFrame engine, Paint color ) {
        engine.drawRectangle( this, color );
    }

    @Override
    public void fill( EngineFrame engine, Paint color ) {
        engine.fillRectangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Rectangle[%.2f, %.2f, %.2f, %.2f]", x, y, width, height );
    }

}
