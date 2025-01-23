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

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Um container do tipo janela.
 * 
 * Atenção: nenhum container possui componentes filhos.
 * Eles são apenas um artifício gráfico para agrupar componentes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiWindow extends GuiTextComponent {
    
    protected GuiButtonClose closeButton;
    protected Rectangle titleBarBounds;
    protected boolean titleBarPressed;
    
    /**
     * Cria o componente.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param title O título do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiWindow( double x, double y, double width, double height, String title, EngineFrame engine ) {
        super( x, y, width, height, title, engine );
        initComponents( engine );
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
     * @param title O título do componente. 
     */
    public GuiWindow( double x, double y, double width, double height, String title ) {
        super( x, y, width, height, title );
        initComponents( null );
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param title O título do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiWindow( Rectangle bounds, String title, EngineFrame engine ) {
        super( bounds, title, engine );
        initComponents( engine );
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param title O título do componente. 
     */
    public GuiWindow( Rectangle bounds, String title ) {
        super( bounds, title );
        initComponents( null );
    }
    
    protected void initComponents( EngineFrame engine ) {
        titleBarBounds = new Rectangle( bounds.x, bounds.y, bounds.width, 25 );
        if ( engine == null ) {
            closeButton = new GuiButtonClose( bounds.x + bounds.width - 22, bounds.y + 3, 19, 19 );
        } else {
            closeButton = new GuiButtonClose( bounds.x + bounds.width - 22, bounds.y + 3, 19, 19, engine );
        }
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            closeButton.update( delta );
            
            Vector2 mousePos = engine.getMousePositionPoint();

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, titleBarBounds ) ) {
                titleBarPressed = engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT );
            }
            
        } else {
            titleBarPressed = false;
        }
        
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            engine.setStrokeLineWidth( LINE_WIDTH );
            if ( enabled ) {
                drawWindow( 
                        CONTAINER_BORDER_COLOR, 
                        CONTAINER_BACKGROUNG_COLOR, 
                        CONTAINER_TITLE_BAR_BORDER_COLOR, 
                        CONTAINER_TITLE_BAR_BACKGROUND_COLOR, 
                        CONTAINER_TITLE_BAR_TEXT_COLOR
                );
            } else {
                drawWindow( 
                        DISABLED_CONTAINER_BORDER_COLOR, 
                        DISABLED_CONTAINER_BACKGROUND_COLOR, 
                        DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR, 
                        DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR, 
                        DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR
                );
            }
            drawBounds();
            closeButton.draw();
        }
    }
    
    private void drawWindow( Color borderColor, Color backgroundColor, Color titleBarBorderColor, Color titleBarBackgroundColor, Color titleBarTextColor ) {
        
        if ( textWidth == -1 && text != null ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        
        engine.fillRectangle( bounds, backgroundColor );
        engine.drawRectangle( bounds, borderColor );
        engine.fillRectangle( titleBarBounds, titleBarBackgroundColor );
        engine.drawRectangle( titleBarBounds, titleBarBorderColor );
            
        if ( text != null ) {
            engine.drawText( text, bounds.x + 10, bounds.y + 10, FONT_SIZE, titleBarTextColor );
        }
        
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        closeButton.setEnabled( enabled );
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        closeButton.setVisible( visible );
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        titleBarBounds.x += xAmount;
        titleBarBounds.y += yAmount;
        closeButton.bounds.x += xAmount;
        closeButton.bounds.y += yAmount;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isCloseButtonPressed() {
        return closeButton.isMousePressed();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isTitleBarPressed() {
        if ( closeButton.isMousePressed() ) {
            return false;
        }
        return titleBarPressed;
    }
    
    protected class GuiButtonClose extends GuiButton {
        
        public GuiButtonClose( double x, double y, double width, double height, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
        }
        
        public GuiButtonClose( double x, double y, double width, double height ) {
            super( x, y, width, height, "" );
        }

        @Override
        public void draw() {
            
            super.draw();
            
            if ( visible ) {

                engine.setStrokeLineWidth( LINE_WIDTH );

                if ( enabled ) {

                    switch ( mouseState ) {
                        case MOUSE_OUT:
                            drawButtonLabel( TEXT_COLOR );
                            break;
                        case MOUSE_OVER:
                            drawButtonLabel( MOUSE_OVER_TEXT_COLOR );
                            break;
                        case MOUSE_PRESSED:
                            drawButtonLabel( MOUSE_DOWN_TEXT_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawButtonLabel( MOUSE_DOWN_TEXT_COLOR );
                            break;
                    }

                } else {
                    drawButtonLabel( DISABLED_TEXT_COLOR );
                }

            }
            
        }
        
        private void drawButtonLabel( Color color ) {
            int pad = 6;
            engine.drawLine( bounds.x + pad, bounds.y + pad, bounds.x + bounds.width - pad, bounds.y + bounds.height - pad, color );
            engine.drawLine( bounds.x + pad, bounds.y + bounds.height - pad, bounds.x + bounds.width - pad, bounds.y + pad, color );
        }
        
    }
    
}
