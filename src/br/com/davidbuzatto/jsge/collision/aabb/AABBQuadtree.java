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

import java.util.List;

/**
 * Uma quadtree para AABBs.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AABBQuadtree {
    
    /**
     * Raiz.
     */
    private AABBQuadtreeNode root;
    
    /**
     * Profundidade máxima.
     */
    private int maxDepth;
    
    /**
     * Lista de AABBs da árvore.
     */
    private List<AABB> aabbs;
    
    /**
     * Enumeração para os tipos dos nós da quadtree.
     */
    public static enum Shape {
        SQUARE,
        RECTANGULAR
    }

    /**
     * Constroi uma nova quadtree a partir de um conjunto de AABBs.
     * 
     * @param aabbs As AABBs da árvore.
     * @param width Largura da árvore (retângulo mais externo).
     * @param height Altura da árvore (retângulo mais externo).
     * @param maxDepth Maior profundidade da árvore.
     * @param shape Formato dos nós da árvore.
     */
    public AABBQuadtree( List<AABB> aabbs, int width, int height, int maxDepth, Shape shape ) {
        this.aabbs = aabbs;
        this.maxDepth = maxDepth;
        reshape( width, height, shape );
    }
    
    /**
     * Constroi uma nova quadtree a partir de um conjunto de AABBs com nós quadrados.
     * 
     * @param aabbs As AABBs da árvore.
     * @param width Largura da árvore (retângulo mais externo).
     * @param height Altura da árvore (retângulo mais externo).
     * @param maxDepth Maior profundidade da árvore.
     */
    public AABBQuadtree( List<AABB> aabbs, int width, int height, int maxDepth ) {
        this( aabbs, width, height, maxDepth, Shape.SQUARE );
    }

    /**
     * Constroi a estrutura da árvore recursivamente.
     * 
     * @param x1 Coordenada x do vértice superior esquerdo do nó atual.
     * @param y1 Coordenada y do vértice superior esquerdo do nó atual.
     * @param x2 Coordenada x do vértice inferior direito do nó atual.
     * @param y2 Coordenada y do vértice inferior direito do nó atual.
     * @param depth A profundidade do nó atual.
     * @return 
     */
    public AABBQuadtreeNode buildQuadtree( int x1, int y1, int x2, int y2, int depth ) {
        
        AABBQuadtreeNode node = null;
        
        if ( depth <= maxDepth ) {
            node = new AABBQuadtreeNode( x1, y1, x2, y2, depth );
            node.nw = buildQuadtree( x1, y1, node.xCenter, node.yCenter, depth + 1 );
            node.ne = buildQuadtree( node.xCenter, y1, x2, node.yCenter, depth + 1 );
            node.sw = buildQuadtree( x1, node.yCenter, node.xCenter, y2, depth + 1 );
            node.se = buildQuadtree( node.xCenter, node.yCenter, x2, y2, depth + 1 );
        }
        
        return node;
        
    }

    /**
     * Insere um novo nó na árvore.
     * 
     * @param node O nó que será inserido.
     */
    public void insert( AABBQuadtreeNode node ) {

        if ( node.depth < maxDepth ) {
            if ( node.aabbs.size() > 1 ) {
                for ( AABB aabb : node.aabbs ) {
                    if ( aabb.y1 < node.yCenter ) {
                        if ( aabb.x1 < node.xCenter ) {
                            if ( node.nw != null ) {
                                node.nw.aabbs.add( aabb );
                            }
                        }
                        if ( aabb.x2 > node.xCenter ) {
                            if ( node.ne != null ) {
                                node.ne.aabbs.add( aabb );
                            }
                        }
                    }
                    if ( aabb.y2 > node.yCenter ) {
                        if ( aabb.x1 < node.xCenter ) {
                            if ( node.sw != null ) {
                                node.sw.aabbs.add( aabb );
                            }
                        }
                        if ( aabb.x2 > node.xCenter ) {
                            if ( node.se != null ) {
                                node.se.aabbs.add( aabb );
                            }
                        }
                    }
                }
                
                insert( node.nw );
                insert( node.ne );
                insert( node.sw );
                insert( node.se );
                
            }
            
        } else {
            for ( AABB aabb : node.aabbs ) {
                setNearby( node.aabbs, aabb );
            }
        }
    }

    /**
     * Remodela a árvore.
     * 
     * @param width Nova largura.
     * @param height Nova altura.
     * @param shape O formato da árvore.
     */
    public void reshape( int width, int height, AABBQuadtree.Shape shape ) {
        
        if ( shape != AABBQuadtree.Shape.SQUARE && shape != AABBQuadtree.Shape.RECTANGULAR ) {
            throw new IllegalArgumentException( shape + " is not recognized as a valid argument for Quadtree shape" );
        } else {
            
            int newWidth = width;
            int newHeight = height;

            if ( shape == AABBQuadtree.Shape.SQUARE ) {
                newWidth = newHeight = width > height ? width - 1 : height - 1;
            }
            
            root = buildQuadtree( 0, 0, newWidth, newHeight, 0 );
            
        }
        
    }
    
    /**
     * Atualiza a árvore.
     */
    public void update() {
        resetNodes( root );
        root.aabbs.addAll( aabbs );
        for ( AABB aabb : aabbs ) {
            aabb.nearby = null;
        }
        insert( root );
    }

    /**
     * Obtém as AABBs.
     * 
     * @return Uma lista de AABBs.
     */
    public List<AABB> getAABBs() {
        return aabbs;
    }

    /**
     * Obtém a raiz da árvore.
     * 
     * @return A raiz da árvore.
     */
    public AABBQuadtreeNode getRoot() {
        return root;
    }
    
    /**
     * Configura as AABBs.
     * 
     * @param aabbs Novas AABBs. 
     */
    public void setAABBs( List<AABB> aabbs ) {
        this.aabbs = aabbs;
    }

    /**
     * Configura a profundidade máxima.
     * 
     * @param maxDepth A nova profundidade máxina.
     */
    public void setMaxDepth( int maxDepth ) {
        if ( this.maxDepth != maxDepth ) {
            this.maxDepth = ( maxDepth < 1 || maxDepth > 10 ) ? this.maxDepth : maxDepth;
            root = buildQuadtree( root.x1, root.y1, root.x2, root.y2, 0 );
            root.aabbs.addAll( aabbs );
        }
    }

    /**
     * Reseta os nós recursivamente.
     * 
     * @param node O nó a ser resetado.
     */
    private void resetNodes( AABBQuadtreeNode node ) {
        if ( node != null ) {
            if ( !node.aabbs.isEmpty() ) {
                node.aabbs.clear();
            }
            resetNodes( node.nw );
            resetNodes( node.ne );
            resetNodes( node.sw );
            resetNodes( node.se );
        }
    }
    
    /**
     * Configura as AABBs que estão próximas.
     * 
     * @param aabbs A lista de AABBs.
     * @param target O alvo, ou seja, a AABB próxima.
     */
    private void setNearby( List<AABB> aabbs, AABB target ) {
        for ( AABB aabb : aabbs ) {
            aabb.nearby = target;
        }
    }

}
