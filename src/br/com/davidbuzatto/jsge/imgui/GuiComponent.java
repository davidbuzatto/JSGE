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
 * Representação genérica de um componente.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class GuiComponent {
    
    private static int idCounter;
    private int id;
    
    protected Rectangle bounds;
    protected EngineFrame engine;
    
    protected GuiComponentMouseState mouseState;
    protected boolean enabled;
    protected boolean visible;
    protected boolean drawingBounds;
    
    public static final int FONT_SIZE = 12;
    public static final int LINE_WIDTH = 1;
    
    public static final Color BACKGROUND_COLOR = new Color( 201, 201, 201 );
    public static final Color BORDER_COLOR = new Color( 131, 131, 131 );
    public static final Color TEXT_COLOR = new Color( 104, 104, 104 );
    
    public static final Color MOUSE_OVER_BACKGROUND_COLOR = new Color( 201, 239, 254 );
    public static final Color MOUSE_OVER_BORDER_COLOR = new Color( 91, 178, 217 );
    public static final Color MOUSE_OVER_TEXT_COLOR = new Color( 108, 155, 188 );
    
    public static final Color MOUSE_DOWN_BACKGROUND_COLOR = new Color( 151, 232, 255 );
    public static final Color MOUSE_DOWN_BORDER_COLOR = new Color( 4, 146, 199 );
    public static final Color MOUSE_DOWN_TEXT_COLOR = new Color( 54, 139, 175 );
    
    public static final Color DISABLED_BACKGROUND_COLOR = new Color( 230, 233, 233 );
    public static final Color DISABLED_BORDER_COLOR = new Color( 181, 193, 194 );
    public static final Color DISABLED_TEXT_COLOR = new Color( 174, 183, 184 );
    
    public static final Color CONTAINER_BORDER_COLOR = new Color( 144, 171, 181 );
    public static final Color CONTAINER_TEXT_COLOR = new Color( 144, 171, 181 );
    public static final Color CONTAINER_BACKGROUNG_COLOR = new Color( 245, 245, 245 );
    public static final Color CONTAINER_TITLE_BAR_BORDER_COLOR = new Color( 131, 131, 131 );
    public static final Color CONTAINER_TITLE_BAR_BACKGROUND_COLOR = new Color( 201, 201, 201 );
    public static final Color CONTAINER_TITLE_BAR_TEXT_COLOR = new Color( 104, 104, 104 );
    
    public static final Color DISABLED_CONTAINER_BORDER_COLOR = new Color( 181, 193, 194 );
    public static final Color DISABLED_CONTAINER_TEXT_COLOR = new Color( 181, 193, 194 );
    public static final Color DISABLED_CONTAINER_BACKGROUND_COLOR = new Color( 230, 233, 233 );
    public static final Color DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR = new Color( 181, 193, 194 );
    public static final Color DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR = new Color( 230, 233, 233 );
    public static final Color DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR = new Color( 181, 193, 194 );
    
    public static final Color PROGRESS_BAR_BACKGROUND_COLOR = new Color( 151, 232, 255 );
    public static final Color DISABLED_PROGRESS_BAR_BACKGROUND_COLOR = new Color( 230, 233, 233 );
    
    public static final Color LIST_CONTAINER_BACKGROUND_COLOR = new Color( 245, 245, 245 );
    public static final Color DISABLED_LIST_CONTAINER_BACKGROUND_COLOR = new Color( 245, 245, 245 );
    
    public static final Color SCROLL_BAR_TRACK_COLOR = new Color( 220, 220, 220 );
    public static final Color DISABLED_SCROLL_BAR_TRACK_COLOR = new Color( 220, 220, 220 );
    
    /**
     * Constrói a infraestrutura básica dos componentes.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiComponent( Rectangle bounds, EngineFrame engine ) {
        this.id = idCounter++;
        this.engine = engine;
        this.bounds = bounds;
        this.mouseState = GuiComponentMouseState.MOUSE_OUT;
        this.enabled = true;
        this.visible = true;
    }
    
    /**
     * Constrói a infraestrutura básica dos componentes.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiComponent( double x, double y, double width, double height, EngineFrame engine ) {
        this( new Rectangle( x, y, width, height ), engine );
    }
    
    /**
     * Constrói a infraestrutura básica dos componentes.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds Um retângulo que define os limites do componente.
     */
    public GuiComponent( Rectangle bounds ) {
        this( bounds, EngineFrame.getDependencyEngine() );
    }
    
    /**
     * Constrói a infraestrutura básica dos componentes.
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
     */
    public GuiComponent( double x, double y, double width, double height ) {
        this( new Rectangle( x, y, width, height ) );
    }
    
    /**
     * Atualiza o estado do componente. Deve ser invocado no método update
     * da instância da engine.
     * 
     * @param delta A variação no tempo, em segundos, de um frame para o outro.
     */
    public void update( double delta ) {
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                mouseState = GuiComponentMouseState.MOUSE_OVER;
                if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentMouseState.MOUSE_PRESSED;
                } else if ( engine.isMouseButtonDown( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentMouseState.MOUSE_DOWN;
                }
            } else {
                mouseState = GuiComponentMouseState.MOUSE_OUT;
            }
            
        } else {
            mouseState = GuiComponentMouseState.MOUSE_OUT;
        }
        
    }
    
    /**
     * Desenha o componente usando a engine configurada.
     */
    public abstract void draw();
    
    /**
     * Move o componente.
     * 
     * @param xAmount Quantidade de movimentação no eixo x.
     * @param yAmount Quantidade de movimentação no eixo y. 
     */
    public abstract void move( double xAmount, double yAmount );
    
    /**
     * Retorna se o botão esquerdo do mouse foi pressionado no componente
     * no ciclo atual.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isMousePressed() {
        return mouseState == GuiComponentMouseState.MOUSE_PRESSED;
    }
    
    /**
     * Retorna se o botão esquerdo do mouse está pressionado no componente
     * no ciclo atual.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isMouseDown() {
        return mouseState == GuiComponentMouseState.MOUSE_DOWN;
    }
    
    /**
     * Retorna se o ponteiro do mouse está fora do componente no ciclo atual.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isMouseOut() {
        return mouseState == GuiComponentMouseState.MOUSE_OUT;
    }
    
    /**
     * Retorna se o ponteiro do mouse em cima do componente no ciclo atual.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isMouseOver() {
        return mouseState == GuiComponentMouseState.MOUSE_OVER;
    }
    
    /**
     * Obtém a coordenada x superior esquerda do retângulo que limita o componente.
     * 
     * @return A coordenada x.
     */
    public double getX() {
        return bounds.x;
    }
    
    /**
     * Obtém a coordenada y superior esquerda do retângulo que limita o componente.
     * 
     * @return A coordenada y.
     */
    public double getY() {
        return bounds.y;
    }
    
    /**
     * Obtém a largura do retângulo que limite o componente.
     * 
     * @return A largura do componente.
     */
    public double getWidth() {
        return bounds.width;
    }
    
    /**
     * Obtém a altura do retângulo que limite o componente.
     * 
     * @return A altura do componente.
     */
    public double getHeight() {
        return bounds.height;
    }
    
    /**
     * Desenha o retângulo que limite o componente.
     */
    protected void drawBounds() {
        if ( drawingBounds ) {
            engine.setStrokeLineWidth( 1 );
            engine.drawRectangle( bounds, EngineFrame.BLUE );
        }
    }
    
    /**
     * Retorna se o componente está habilitado.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Configura se o componente está ou não habilitado.
     * 
     * @param enabled Verdadeiro para habilitar, falso para desabilitar.
     */
    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    /**
     * Retorna se o componente está visível.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Configura se o componente está ou não visível.
     * 
     * @param visible Verdadeiro para mostrar, falso para esconder.
     */
    public void setVisible( boolean visible ) {
        this.visible = visible;
    }

    /**
     * Retorna se os limites do componente está sendo desenhado.
     * 
     * @return Verdadeiro caso sim, falso caso contrário.
     */
    public boolean isDrawingBounds() {
        return drawingBounds;
    }

    /**
     * Configura se deve-se desenhar os limites do componente.
     * 
     * @param drawBounds Verdadeiro para desenhar, falso para não desenhar.
     */
    public void setDrawBounds( boolean drawBounds ) {
        this.drawingBounds = drawBounds;
    }

    /**
     * Obtém o retângulo que define os limites do componente.
     * Retorna uma cópia do retângulo atual para não permitir que o usuário
     * do componente altere o retângulo utilizado.
     * 
     * @return O retângulo que define os limites do componente.
     */
    public Rectangle getBounds() {
        return new Rectangle( bounds.x, bounds.y, bounds.width, bounds.height );
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final GuiComponent other = (GuiComponent) obj;
        return this.id == other.id;
    }
    
}
