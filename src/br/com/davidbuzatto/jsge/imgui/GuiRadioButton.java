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
import java.awt.Color;

/**
 * Um componente botão de radio.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiRadioButton extends GuiToggleButton {
    
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
    public GuiRadioButton( double x, double y, double width, double height, String text, EngineFrame engine ) {
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
    public GuiRadioButton( double x, double y, double width, double height, String text ) {
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
    public GuiRadioButton( Rectangle bounds, String text, EngineFrame engine ) {
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
    public GuiRadioButton( Rectangle bounds, String text ) {
        super( bounds, text );
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( 1 );

            if ( enabled ) {

                switch ( mouseState ) {
                    case MOUSE_OUT:
                        drawRadioButton( borderColor, 10, selected );
                        drawText( textColor, 25, 0 );
                        break;
                    case MOUSE_OVER:
                        drawRadioButton( MOUSE_OVER_BORDER_COLOR, 10, selected );
                        drawText( MOUSE_OVER_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_PRESSED:
                        drawRadioButton( MOUSE_DOWN_BORDER_COLOR, 10, selected );
                        drawText( MOUSE_DOWN_TEXT_COLOR, 25, 0 );
                        break;
                    case MOUSE_DOWN:
                        drawRadioButton( MOUSE_DOWN_BORDER_COLOR, 10, selected );
                        drawText( MOUSE_DOWN_TEXT_COLOR, 25, 0 );
                        break;
                }

            } else {
                drawRadioButton( DISABLED_BORDER_COLOR, 10, selected );
                drawText( DISABLED_TEXT_COLOR, 25, 0 );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawRadioButton( Color borderColor, double radioRadius, boolean fillInternal ) {
        double x = bounds.x + radioRadius;
        double y = bounds.y + bounds.height / 2;
        engine.drawCircle( x, y, radioRadius, borderColor );
        if ( fillInternal ) {
            engine.fillCircle( x + 0.5, y + 0.5, radioRadius - 2, borderColor );
        }
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        
    }
    
}
