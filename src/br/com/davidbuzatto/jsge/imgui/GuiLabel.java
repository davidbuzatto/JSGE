/*
 * Copyright (C) 2025 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.imgui;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;

/**
 * Um componente de etiqueta.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiLabel extends GuiTextComponent {
    
    private int horizontalAlignment;
    private int verticalAlignment;
    
    // posição relativa ao retângulo de limite
    private Vector2 startPosition;
    
    /**
     * Cria o componente.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param text Texto utilizado no componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiLabel( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
        initData();
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param text Texto utilizado no componente. 
     */
    public GuiLabel( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
        initData();
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param text Texto utilizado no componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiLabel( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
        initData();
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param text Texto utilizado no componente. 
     */
    public GuiLabel( Rectangle bounds, String text ) {
        super( bounds, text );
        initData();
    }
    
    private void initData() {
        horizontalAlignment = LEFT_ALIGNMENT;
        verticalAlignment = MIDDLE_ALIGNMENT;
        startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            if ( enabled ) {
                engine.drawText( text, bounds.x + startPosition.x, bounds.y + startPosition.y, FONT_SIZE, TEXT_COLOR );
                //drawText( TEXT_COLOR );
            } else {
                engine.drawText( text, bounds.x + startPosition.x, bounds.y + startPosition.y, FONT_SIZE, DISABLED_TEXT_COLOR );
                //drawText( DISABLED_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment( int horizontalAlignment ) {
        if ( horizontalAlignment >= LEFT_ALIGNMENT && horizontalAlignment <= RIGHT_ALIGNMENT ) {
            this.horizontalAlignment = horizontalAlignment;
            startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
        }
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment( int verticalAlignment ) {
        if ( verticalAlignment >= TOP_ALIGNMENT && verticalAlignment <= BOTTOM_ALIGNMENT ) {
            this.verticalAlignment = verticalAlignment;
            startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
        }
    }
    
    @Override
    public void setText( String text ) {
        this.text = text;
        startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
    }
    
}
