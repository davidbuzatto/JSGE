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

/**
 * Um componente de etiqueta que se comporta como um botão.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiLabelButton extends GuiTextComponent {
    
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
    public GuiLabelButton( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
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
    public GuiLabelButton( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param text Texto utilizado no componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiLabelButton( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
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
    public GuiLabelButton( Rectangle bounds, String text ) {
        super( bounds, text );
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            if ( enabled ) {

                switch ( mouseState ) {
                    case MOUSE_OUT:
                        drawText( TEXT_COLOR );
                        break;
                    case MOUSE_OVER:
                        drawText( MOUSE_OVER_TEXT_COLOR );
                        break;
                    case MOUSE_PRESSED:
                        drawText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                    case MOUSE_DOWN:
                        drawText( MOUSE_DOWN_TEXT_COLOR );
                        break;
                }

            } else {
                drawText( DISABLED_TEXT_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
}
