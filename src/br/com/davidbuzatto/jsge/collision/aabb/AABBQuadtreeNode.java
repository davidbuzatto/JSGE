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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Um nó da AABBQuadtree, representado por um quadrante com coordenadas inicias,
 * finais e centrais.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AABBQuadtreeNode {

    /**
     * Coordenada x do vértice superior esquerdo.
     */
    public int x1;
    
    /**
     * Coordenada y do vértice superior esquerdo.
     */
    public int y1;
    
    /**
     * Coordenada x do vértice inferior direito.
     */
    public int x2;
    
    /**
     * Coordenada y do vértice inferior direito.
     */
    public int y2;
    
    /**
     * Coordenada x do centro do quadrante.
     */
    public int xCenter;
    
    /**
     * Coordenada y do centro do quadrante.
     */
    public int yCenter;
    
    /**
     * A profundidade na árvore.
     */
    public int depth;

    /**
     * Nó filho noroeste (superior esquerdo).
     */
    public AABBQuadtreeNode nw;
    
    /**
     * Nó filho nordeste (superior direito).
     */
    public AABBQuadtreeNode ne;
    
    /**
     * Nó filho sudoeste (inferior esquerdo).
     */
    public AABBQuadtreeNode sw;
    
    /**
     * Nó filho sudeste (inferior direito).
     */
    public AABBQuadtreeNode se;

    /**
     * Uma lista de AABBs que interceptam os limites do nó.
     */
    public List<AABB> aabbs;

    /**
     * Constrói um novo nó da AABBQuadtree.
     * 
     * @param x1 Coordenada x do vértice superior esquerdo.
     * @param y1 Coordenada y do vértice superior esquerdo.
     * @param x2 Coordenada x do vértice inferior direito.
     * @param y2 Coordenada y do vértice inferior direito. 
     * @param depth Profundidade do nó na árvore. 
     */
    public AABBQuadtreeNode( int x1, int y1, int x2, int y2, int depth ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.xCenter = ( x1 + x2 ) / 2;
        this.yCenter = ( y1 + y2 ) / 2;
        this.depth = depth;
        this.aabbs = new CopyOnWriteArrayList<>();
        //this.aabbs = new ArrayList<>();
    }

}
