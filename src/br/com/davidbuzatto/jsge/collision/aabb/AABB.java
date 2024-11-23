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
 * Mantém algumas propriedades para guiar a execução da AABBQuadtree, como
 * o tipo da AABB (estática ou dinâmica), se é uma AABB ativa e qual o objeto
 * que faz referência.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AABB {

    /**
     * Tipo da AABB.
     */
    public static enum Type {
        
        /**
         * Tipo estático. Indica que na quadtree AABBs estáticas não interagem
         * entre si, apenas com AABBs dinâmicas.
         */
        STATIC,
        
        /**
         * Tipo dinâmico. Indique que na quadtree AABBs dinâmicas interagem
         * tanto com AABBs estáticas quando dinâmicas.
         */
        DYNAMIC;
        
    }
    
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
     * Tipo da AABB.
     */
    public Type type;
    
    /**
     * Indica se a AABB está ativa.
     */
    public boolean active;
    
    /**
     * Objeto referenciado por essa AABB, ou seja, o objeto que deu origem 
     * a essa AABB.
     */
    public Object referencedObject;
    
    /**
     * Uma AABB que está próxima.
     */
    public AABB nearby;

    /**
     * Constroi uma AABB estática com o vértice superior esquerdo em 0, 0 e
     * vértice inferior direito em 50, 50 que não referencia nenhum objeto.
     */
    public AABB() {
        this( 0, 0, 50, 50, Type.STATIC, null );
    }

    /**
     * Constroi uma AABB estática.
     * 
     * @param x1 Coordenada x do vértice superior esquerdo.
     * @param y1 Coordenada y do vértice superior esquerdo.
     * @param x2 Coordenada x do vértice inferior direito.
     * @param y2 Coordenada y do vértice inferior direito. 
     * @param referencedObject Objeto referenciado por essa AABB, ou seja, o
     * objeto que deu origem à mesma.
     */
    public AABB( double x1, double y1, double x2, double y2, Object referencedObject ) {
        this( x1, y1, x2, y2, Type.STATIC, referencedObject );
    }
    
    /**
     * Constroi uma AABB ativa.
     * 
     * @param x1 Coordenada x do vértice superior esquerdo.
     * @param y1 Coordenada y do vértice superior esquerdo.
     * @param x2 Coordenada x do vértice inferior direito.
     * @param y2 Coordenada y do vértice inferior direito. 
     * @param type Tipo da AABB.
     * @param referencedObject Objeto referenciado por essa AABB, ou seja, o
     * objeto que deu origem à mesma.
     */
    public AABB( double x1, double y1, double x2, double y2, Type type, Object referencedObject ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
        this.type = type;
        this.active = true;
        this.referencedObject = referencedObject;
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
     * Move a AABB.
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
     * Reposiciona a AABB.
     * 
     * @param x1 Coordenada x do vértice superior esquerdo.
     * @param y1 Coordenada y do vértice superior esquerdo.
     */
    public void moveTo( double x1, double y1 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + this.width;
        this.y2 = y1 + this.height;
    }

    /**
     * Configura o tamanho da AABB, recalculando x2 e y2.
     * 
     * @param width Largura.
     * @param height Altura.
     */
    public void setSize( double width, double height ) {
        x2 = x1 + width;
        y2 = y1 + height;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

}
