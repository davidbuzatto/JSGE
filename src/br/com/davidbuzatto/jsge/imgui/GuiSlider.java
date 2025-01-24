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
 * Um componente de controle deslizante.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiSlider extends GuiComponent {
    
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private double value;
    private double min;
    private double max;
    
    private GuiSliderButton sliderButton;
    private int orientation;
    private boolean showTrack;
    
    private boolean mouseWheelEnabled;
    
    private Color trackFillColor;
    
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
    public GuiSlider( double x, double y, double width, double height, double value, double min, double max, EngineFrame engine ) {
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
    public GuiSlider( double x, double y, double width, double height, double value, double min, double max ) {
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
    public GuiSlider( Rectangle bounds, double value, double min, double max, EngineFrame engine ) {
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
    public GuiSlider( Rectangle bounds, double value, double min, double max ) {
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
     * @param orientation Se o controle deslizante é horizontal ou vertical.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiSlider( double x, double y, double width, double height, double value, double min, double max, int orientation, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value, min, max, orientation );
        initComponents( engine, SLIDER_RADIUS );
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
     * @param orientation Se o controle deslizante é horizontal ou vertical.
     */
    public GuiSlider( double x, double y, double width, double height, double value, double min, double max, int orientation ) {
        super( x, y, width, height );
        initData( value, min, max, orientation );
        initComponents( null, SLIDER_RADIUS );
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param min O valor mínimo do componente.
     * @param max O valor máximo do componente.
     * @param orientation Se o controle deslizante é horizontal ou vertical.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiSlider( Rectangle bounds, double value, double min, double max, int orientation, EngineFrame engine ) {
        super( bounds, engine );
        initData( value, min, max, orientation );
        initComponents( engine, SLIDER_RADIUS );
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
     * @param orientation Se o controle deslizante é horizontal ou vertical.
     */
    public GuiSlider( Rectangle bounds, double value, double min, double max, int orientation ) {
        super( bounds );
        initData( value, min, max, orientation );
        initComponents( null, SLIDER_RADIUS );
    }
    
    private void initData( double value, double min, double max, int orientation ) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.orientation = orientation;
        this.showTrack = true;
        this.mouseWheelEnabled = true;
        this.backgroundColor = CONTAINER_BACKGROUNG_COLOR;
        this.trackFillColor = MOUSE_DOWN_BACKGROUND_COLOR;
    }
    
    private void initComponents( EngineFrame engine, double sliderRadius ) {
        if ( engine == null ) {
            if ( orientation == HORIZONTAL ) {
                sliderButton = new GuiSliderButton( bounds.x, bounds.y + bounds.height / 2 - sliderRadius, sliderRadius * 2, sliderRadius * 2, sliderRadius, true );
            } else {
                sliderButton = new GuiSliderButton( bounds.x + bounds.width / 2 - sliderRadius, bounds.y, sliderRadius * 2, sliderRadius * 2, sliderRadius, false );
            }
        } else {
            if ( orientation == HORIZONTAL ) {
                sliderButton = new GuiSliderButton( bounds.x, bounds.y + bounds.height / 2 - sliderRadius, sliderRadius * 2, sliderRadius * 2, sliderRadius, true, engine );
            } else {
                sliderButton = new GuiSliderButton( bounds.x + bounds.width / 2 - sliderRadius, bounds.y, sliderRadius * 2, sliderRadius * 2, sliderRadius, false, engine );
            }
        }
        updateSliderButtonPosition();
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();
            sliderButton.update( delta, bounds );
            
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
            
            if ( mouseState == GuiComponentMouseState.MOUSE_DOWN && sliderButton.mouseState == GuiComponentMouseState.MOUSE_OUT ) {
                if ( orientation == VERTICAL ) {
                    sliderButton.bounds.y = mousePos.y - SLIDER_RADIUS;
                } else {
                    sliderButton.bounds.x = mousePos.x - SLIDER_RADIUS;
                }
                sliderButton.update( delta, bounds );
            }
            
            if ( orientation == VERTICAL ) {
                
                double maxSliderY = bounds.y + sliderButton.radius;
                double minSliderY = bounds.y + bounds.height - sliderButton.radius;
                double sliderY = sliderButton.bounds.y + sliderButton.radius;
                double percentage = MathUtils.inverseLerp( minSliderY, maxSliderY, sliderY );

                value = MathUtils.lerp( min, max, percentage );
                
            } else {
                
                double minSliderX = bounds.x + sliderButton.radius;
                double maxSliderX = bounds.x + bounds.width - sliderButton.radius;
                double sliderX = sliderButton.bounds.x + sliderButton.radius;
                double percentage = MathUtils.inverseLerp( minSliderX, maxSliderX, sliderX );

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
                        drawSlider( CONTAINER_BACKGROUNG_COLOR, MOUSE_OVER_BORDER_COLOR, MOUSE_DOWN_BACKGROUND_COLOR, 4 );
                        break;
                    case MOUSE_DOWN:
                        drawSlider( CONTAINER_BACKGROUNG_COLOR, MOUSE_DOWN_BORDER_COLOR, MOUSE_DOWN_BACKGROUND_COLOR, 4 );
                        break;
                    default:
                        drawSlider( backgroundColor, borderColor, trackFillColor, 4 );
                        break;
                }
            } else {
                drawSlider( DISABLED_CONTAINER_BACKGROUND_COLOR, DISABLED_BORDER_COLOR, MOUSE_DOWN_BACKGROUND_COLOR, 4 );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawSlider( Color backgroundColor, Color borderColor, Color valueColor, double sliderBarSize ) {
        if ( showTrack ) {
            if ( orientation == VERTICAL ) {
                engine.fillRectangle( bounds.x + bounds.width / 2 - sliderBarSize / 2, bounds.y, sliderBarSize, bounds.height, backgroundColor );
                if ( enabled ) {
                    engine.fillRectangle( bounds.x + bounds.width / 2 - sliderBarSize / 2, sliderButton.bounds.y, sliderBarSize, bounds.y + bounds.height - sliderButton.bounds.y, valueColor );
                }
                engine.drawRectangle( bounds.x + bounds.width / 2 - sliderBarSize / 2, bounds.y, sliderBarSize, bounds.height, borderColor );
            } else { // horizontal
                engine.fillRectangle( bounds.x, bounds.y + bounds.height / 2 - sliderBarSize / 2, bounds.width, sliderBarSize, backgroundColor );
                if ( enabled ) {
                    engine.fillRectangle( bounds.x, bounds.y + bounds.height / 2 - sliderBarSize / 2, sliderButton.bounds.x - bounds.x, sliderBarSize, valueColor );
                }
                engine.drawRectangle( bounds.x, bounds.y + bounds.height / 2 - sliderBarSize / 2, bounds.width, sliderBarSize, borderColor );
            }
        }
        sliderButton.draw();
    }
    
    private void updateSliderButtonPosition() {
        
        if ( orientation == VERTICAL ) {
            
            double maxSliderY = bounds.y + sliderButton.radius;
            double minSliderY = bounds.y + bounds.height - sliderButton.radius;
            double percentage = MathUtils.inverseLerp( min, max, value );

            double sliderY = MathUtils.lerp( minSliderY, maxSliderY, percentage );
            sliderButton.bounds.y = sliderY - sliderButton.radius;
            
        } else {
            
            double minSliderX = bounds.x + sliderButton.radius;
            double maxSliderX = bounds.x + bounds.width - sliderButton.radius;
            double percentage = MathUtils.inverseLerp( min, max, value );

            double sliderX = MathUtils.lerp( minSliderX, maxSliderX, percentage );
            sliderButton.bounds.x = sliderX - sliderButton.radius;
            
        }
        
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        sliderButton.setEnabled( enabled );
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        sliderButton.setVisible( visible );
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
        updateSliderButtonPosition();
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

    /**
     * Obtém a cor do valor preenchido.
     * 
     * @return A cor do valor preenchido.
     */
    public Color getTrackFillColor() {
        return trackFillColor;
    }

    /**
     * Configura a cor do valor preenchido.
     * 
     * @param trackFillColor A cor do valor preenchido.
     */
    public void setTrackFillColor( Color trackFillColor ) {
        this.trackFillColor = trackFillColor;
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        sliderButton.bounds.x += xAmount;
        sliderButton.bounds.y += yAmount;
    }
    
    private class GuiSliderButton extends GuiButton {
        
        private final double radius;
        private boolean dragging;
        private Vector2 prevMousePos;
        private final boolean moveHorizontally;
        
        public GuiSliderButton( double x, double y, double width, double height, double radius, boolean moveHorizontally, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
            this.radius = radius;
            this.moveHorizontally = moveHorizontally;
        }
        
        public GuiSliderButton( double x, double y, double width, double height, double radius, boolean moveHorizontally ) {
            super( x, y, width, height, "" );
            this.radius = radius;
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
                        if ( bounds.x + radius * 2 > containerBounds.x + containerBounds.width ) {
                            bounds.x = containerBounds.x + containerBounds.width - radius * 2;
                        } else if ( bounds.x < containerBounds.x ) {
                            bounds.x = containerBounds.x;
                        }
                    } else {
                        bounds.y += mousePos.y - prevMousePos.y;
                        if ( bounds.y + radius * 2 > containerBounds.y + containerBounds.height ) {
                            bounds.y = containerBounds.y + containerBounds.height - radius * 2;
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
                            drawSliderButton(BACKGROUND_COLOR, BORDER_COLOR );
                            break;
                        case MOUSE_OVER:
                            drawSliderButton( MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_BORDER_COLOR );
                            break;
                        case MOUSE_PRESSED:
                            drawSliderButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawSliderButton( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR );
                            break;
                    }

                } else {
                    drawSliderButton( DISABLED_BACKGROUND_COLOR, DISABLED_BORDER_COLOR );
                }
                
                drawBounds();

            }
            
        }
        
        private void drawSliderButton( Color backgroundColor, Color borderColor ) {
            engine.fillCircle( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, radius, backgroundColor );
            engine.drawCircle( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, radius, borderColor );
        }
        
    }
    
}
