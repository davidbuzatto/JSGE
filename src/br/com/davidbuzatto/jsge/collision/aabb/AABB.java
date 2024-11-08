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
package br.com.davidbuzatto.jsge.collision.aabb;

/**
 * Representação de uma Axis-aligned Bounding Box (AABB).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AABB {

    /**
     * Coordenada x do vértice superior esquerdo.
     */
    public double x1;
    
    /**
     * Coordenada y do vértice superior esquerdo.
     */
    public double y1;
    
    /**
     * Coordenada x do vértice inferior direito.
     */
    public double x2;
    
    /**
     * Coordenada y do vértice inferior direito.
     */
    public double y2;
    
    /**
     * Largura.
     */
    public double width;
    
    /**
     * Altura.
     */
    public double height;
    
    /**
     * Uma AABB que está próxima.
     */
    public AABB nearby;

    /**
     * Constroi uma AABB com o vértice superior esquerdo em 0, 0 e vértice
     * inferior direito em 50, 50.
     */
    public AABB() {
        x1 = 0;
        y1 = 0;
        x2 = 50;
        y2 = 50;
        width = x2;
        height = y2;
        nearby = null;
    }

    /**
     * Constroi uma AABB.
     * 
     * @param x1 Coordenada x do vértice superior esquerdo.
     * @param y1 Coordenada y do vértice superior esquerdo.
     * @param x2 Coordenada x do vértice inferior direito.
     * @param y2 Coordenada y do vértice inferior direito. 
     */
    public AABB( int x1, int y1, int x2, int y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
        this.nearby = null;
    }

    /**
     * Verifica se a AABB corrente colide com outro AABB.
     * 
     * @param aabb Uma AABB.
     * @return Verdadeiro caso as AABBs colidem, falso caso contrário.
     */
    public boolean collidesWith( AABB aabb ) {

        if ( x1 > aabb.x2 || aabb.x1 > x2 ) {
            return false;
        }

        if ( y2 < aabb.y1 || aabb.y2 < y1 ) {
            return false;
        }

        return true;

    }

    /**
     * Reposiciona a AABB.
     * 
     * @param deltaX Variação em x.
     * @param deltaY Variação em y.
     */
    public void move( double deltaX, double deltaY ) {
        x1 += deltaX;
        y1 += deltaY;
        x2 += deltaX;
        y2 += deltaY;
    }

    /**
     * Configura o tamanho da AABB, recalculando x2 e y2.
     * @param w
     * @param h 
     */
    public void setSize( double w, double h ) {
        x2 = x1 + w;
        y2 = y1 + h;
        width = x2 - x1;
        height = y2 - y1;
    }

}
