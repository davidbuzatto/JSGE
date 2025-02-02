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
import br.com.davidbuzatto.jsge.math.MathUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Um componente de barra de rolagem.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiScrollBar extends GuiComponent {
    
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private double value;
    private double min;
    private double max;
    
    protected GuiScrollBarButton scrollBarButton;
    private int orientation;
    private boolean showTrack;
    
    private boolean mouseWheelEnabled;
    
    /**
     * Cria o componente.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiScrollBar( double x, double y, double width, double height, double value, double min, double max, EngineFrame engine ) {
        this( x, y, width, height, value, min, max, HORIZONTAL, engine );
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
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente. 
     */
    public GuiScrollBar( double x, double y, double width, double height, double value, double min, double max ) {
        this( x, y, width, height, value, min, max, HORIZONTAL );
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiScrollBar( Rectangle bounds, double value, double min, double max, EngineFrame engine ) {
        this( bounds, value, min, max, HORIZONTAL, engine );
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente. 
     */
    public GuiScrollBar( Rectangle bounds, double value, double min, double max ) {
        this( bounds, value, min, max, HORIZONTAL );
    }
    
    /**
     * Cria o componente.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param orientation Se a barra de rolagem é horizontal ou vertical.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiScrollBar( double x, double y, double width, double height, double value, double min, double max, int orientation, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value, min, max, orientation );
        initComponents( engine, SCROLL_BAR_BUTTON_SIZE );
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
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param orientation Se a barra de rolagem é horizontal ou vertical.
     */
    public GuiScrollBar( double x, double y, double width, double height, double value, double min, double max, int orientation ) {
        super( x, y, width, height );
        initData( value, min, max, orientation );
        initComponents( null, SCROLL_BAR_BUTTON_SIZE );
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param orientation Se a barra de rolagem é horizontal ou vertical.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiScrollBar( Rectangle bounds, double value, double min, double max, int orientation, EngineFrame engine ) {
        super( bounds, engine );
        initData( value, min, max, orientation );
        initComponents( engine, SCROLL_BAR_BUTTON_SIZE );
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param orientation Se a barra de rolagem é horizontal ou vertical.
     */
    public GuiScrollBar( Rectangle bounds, double value, double min, double max, int orientation ) {
        super( bounds );
        initData( value, min, max, orientation );
        initComponents( null, SCROLL_BAR_BUTTON_SIZE );
    }
    
    private void initData( double value, double min, double max, int orientation ) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.orientation = orientation;
        this.showTrack = true;
        this.mouseWheelEnabled = true;
        this.backgroundColor = SCROLL_BAR_TRACK_COLOR;
    }
    
    private void initComponents( EngineFrame engine, double scrollBarButtonSize ) {
        if ( engine == null ) {
            if ( orientation == HORIZONTAL ) {
                scrollBarButton = new GuiScrollBarButton( bounds.x, bounds.y, scrollBarButtonSize, bounds.height, scrollBarButtonSize, true );
            } else {
                scrollBarButton = new GuiScrollBarButton( bounds.x, bounds.y, bounds.width, scrollBarButtonSize, scrollBarButtonSize, false );
            }
        } else {
            if ( orientation == HORIZONTAL ) {
                scrollBarButton = new GuiScrollBarButton( bounds.x, bounds.y, scrollBarButtonSize, bounds.height, scrollBarButtonSize, true, engine );
            } else {
                scrollBarButton = new GuiScrollBarButton( bounds.x, bounds.y, bounds.width, scrollBarButtonSize, scrollBarButtonSize, false, engine );
            }
        }
        updateScrollBarButtonPosition();
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();
            scrollBarButton.update( delta, bounds );
            
            if ( mouseWheelEnabled ) {
                if ( mouseState == GuiComponentMouseState.MOUSE_OVER ) {
                    double mouseWheelMove = engine.getMouseWheelMove() * ( ( max - min ) / 10 );
                    if ( min <= max ) {
                        setValue( getValue() + mouseWheelMove );
                    } else {
                        setValue( getValue() - mouseWheelMove );
                    }
                }
            }
            
            if ( mouseState == GuiComponentMouseState.MOUSE_DOWN && scrollBarButton.mouseState == GuiComponentMouseState.MOUSE_OUT ) {
                if ( orientation == VERTICAL ) {
                    scrollBarButton.bounds.y = mousePos.y - SCROLL_BAR_BUTTON_SIZE / 2;
                } else {
                    scrollBarButton.bounds.x = mousePos.x - SCROLL_BAR_BUTTON_SIZE / 2;
                }
                scrollBarButton.update( delta, bounds );
            }
            
            if ( orientation == VERTICAL ) {
                
                double maxScrollBarButtonY = bounds.y + scrollBarButton.bounds.height / 2;
                double minScrollBarButtonY = bounds.y + bounds.height - scrollBarButton.bounds.height / 2;
                double scrollBarButtonY = scrollBarButton.bounds.y + scrollBarButton.bounds.height / 2;
                double percentage = MathUtils.inverseLerp( minScrollBarButtonY, maxScrollBarButtonY, scrollBarButtonY );

                value = MathUtils.lerp( min, max, percentage );
                
            } else {
                
                double minScrollBarButtonX = bounds.x + scrollBarButton.bounds.width / 2;
                double maxScrollBarButtonX = bounds.x + bounds.width - scrollBarButton.bounds.width / 2;
                double scrollBarButtonX = scrollBarButton.bounds.x + scrollBarButton.bounds.width / 2;
                double percentage = MathUtils.inverseLerp( minScrollBarButtonX, maxScrollBarButtonX, scrollBarButtonX );

                value = MathUtils.lerp( min, max, percentage );
            
            }
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                switch ( mouseState ) {
                    case MOUSE_OVER:
                        drawScrollBar( SCROLL_BAR_TRACK_COLOR );
                        break;
                    case MOUSE_DOWN:
                        drawScrollBar( SCROLL_BAR_TRACK_COLOR );
                        break;
                    default:
                        drawScrollBar( SCROLL_BAR_TRACK_COLOR );
                        break;
                }
            } else {
                drawScrollBar( DISABLED_CONTAINER_BACKGROUND_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawScrollBar( Color backgroundColor ) {
        engine.fillRectangle( bounds, backgroundColor );
        scrollBarButton.draw();
    }
    
    private void updateScrollBarButtonPosition() {
        
        if ( orientation == VERTICAL ) {
            
            double maxScrollBarButtonY = bounds.y + scrollBarButton.bounds.height / 2;
            double minScrollBarButtonY = bounds.y + bounds.height - scrollBarButton.bounds.height / 2;
            double percentage = MathUtils.inverseLerp( min, max, value );

            double scrollBarButtonY = MathUtils.lerp( minScrollBarButtonY, maxScrollBarButtonY, percentage );
            scrollBarButton.bounds.y = scrollBarButtonY - scrollBarButton.bounds.height / 2;
            
        } else {
            
            double minScrollBarButtonX = bounds.x + scrollBarButton.bounds.width / 2;
            double maxScrollBarButtonX = bounds.x + bounds.width - scrollBarButton.bounds.width / 2;
            double percentage = MathUtils.inverseLerp( min, max, value );

            double scrollBarButtonX = MathUtils.lerp( minScrollBarButtonX, maxScrollBarButtonX, percentage );
            scrollBarButton.bounds.x = scrollBarButtonX - scrollBarButton.bounds.width / 2;
            
        }
        
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        scrollBarButton.setEnabled( enabled );
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        scrollBarButton.setVisible( visible );
    }
    
    /**
     * Obtém o valor atual do componente.
     * 
     * @return O valor atual.
     */
    public double getValue() {
        return value;
    }

    /**
     * Configura o valor do componente.
     * 
     * @param value O valor do componente. 
     */
    public void setValue( double value ) {
        if ( min <= max ) {
            this.value = MathUtils.clamp( value, min, max );
        } else {
            this.value = MathUtils.clamp( value, max, min );
        }
        updateScrollBarButtonPosition();
    }

    /**
     * Retorna o valor mínimo do componente.
     * 
     * @return O valor mínimo.
     */
    public double getMin() {
        return min;
    }

    /**
     * Configura o valor mínimo do componente.
     * 
     * @param min O valor mínimo do componente. 
     */
    public void setMin( double min ) {
        this.min = min;
    }

    /**
     * Retorna o valor máximo do componente.
     * 
     * @return O valor máximo.
     */
    public double getMax() {
        return max;
    }

    /**
     * Configura o valor máximo do componente.
     * 
     * @param max O valor máximo do componente. 
     */
    public void setMax( double max ) {
        this.max = max;
    }

    /**
     * Retorna se a trilha do componente está visível.
     * 
     * @return Verdadeiro caso esteja visível, falso caso contrário.
     */
    public boolean isShowTrack() {
        return showTrack;
    }

    /**
     * Configura a visibilidade da trilha do componente.
     * 
     * @param showTrack Verdeiro para exibir a trilha, falso para esconder.
     */
    public void setShowTrack( boolean showTrack ) {
        this.showTrack = showTrack;
    }

    /**
     * Retorna se o componente responde à rolagem da roda do mouse.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isMouseWheelEnabled() {
        return mouseWheelEnabled;
    }

    /**
     * Configura se o componente deve responder à rolagem da roda do mouse.
     * 
     * @param mouseWheelEnabled Verdadeiro para responder, falso para não responder.
     */
    public void setMouseWheelEnabled( boolean mouseWheelEnabled ) {
        this.mouseWheelEnabled = mouseWheelEnabled;
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        scrollBarButton.bounds.x += xAmount;
        scrollBarButton.bounds.y += yAmount;
    }
    
    protected class GuiScrollBarButton extends GuiButton {
        
        private boolean dragging;
        private Vector2 prevMousePos;
        private final boolean moveHorizontally;
        
        public GuiScrollBarButton( double x, double y, double width, double height, double radius, boolean moveHorizontally, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
            this.moveHorizontally = moveHorizontally;
        }
        
        public GuiScrollBarButton( double x, double y, double width, double height, double radius, boolean moveHorizontally ) {
            super( x, y, width, height, "" );
            this.moveHorizontally = moveHorizontally;
        }

        public void update( double delta, Rectangle containerBounds ) {

            if ( visible && enabled ) {

                Vector2 mousePos = engine.getMousePositionPoint();

                if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                    mouseState = GuiComponentMouseState.MOUSE_OVER;
                    if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                        mouseState = GuiComponentMouseState.MOUSE_PRESSED;
                    } else if ( engine.isMouseButtonDown( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                        mouseState = GuiComponentMouseState.MOUSE_DOWN;
                        dragging = true;
                    }
                } else {
                    mouseState = GuiComponentMouseState.MOUSE_OUT;
                }
                
                if ( engine.isMouseButtonUp( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    dragging = false;
                }
                
                if ( dragging ) {
                    
                    mouseState = GuiComponentMouseState.MOUSE_DOWN;
                    
                    if ( moveHorizontally ) {
                        bounds.x += mousePos.x - prevMousePos.x;
                        if ( bounds.x + bounds.width > containerBounds.x + containerBounds.width ) {
                            bounds.x = containerBounds.x + containerBounds.width - bounds.width;
                        } else if ( bounds.x < containerBounds.x ) {
                            bounds.x = containerBounds.x;
                        }
                    } else {
                        bounds.y += mousePos.y - prevMousePos.y;
                        if ( bounds.y + bounds.height > containerBounds.y + containerBounds.height ) {
                            bounds.y = containerBounds.y + containerBounds.height - bounds.height;
                        } else if ( bounds.y < containerBounds.y ) {
                            bounds.y = containerBounds.y;
                        }
                    }
                }
                
                prevMousePos = mousePos;

            } else {
                mouseState = GuiComponentMouseState.MOUSE_OUT;
            }

        }
    
        @Override
        public void draw() {
            
            if ( visible ) {

                engine.setStrokeLineWidth( LINE_WIDTH );

                if ( enabled ) {

                    switch ( mouseState ) {
                        case MOUSE_OUT:
                            drawScrollBarButtonButton( backgroundColor, borderColor );
                            break;
                        case MOUSE_OVER:
                            drawScrollBarButtonButton( MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_BORDER_COLOR );
                            break;
                        case MOUSE_PRESSED:
                            drawScrollBarButtonButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawScrollBarButtonButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            break;
                    }

                } else {
                    drawScrollBarButtonButton( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR );
                }
                
                drawBounds();

            }
            
        }
        
        private void drawScrollBarButtonButton( Color backgroundColor, Color borderColor ) {
            if ( orientation == VERTICAL ) {
                engine.fillRectangle( bounds, backgroundColor );
            } else {
                engine.fillRectangle( bounds, backgroundColor );
            }
        }
        
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        
    }
    
}
