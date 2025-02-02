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
    
    protected Color backgroundColor;
    protected Color borderColor;
    protected Color textColor;
    
    /** Tamanho padrão da fonte dos componentes. */
    public static final int FONT_SIZE = 12;
    /** Largura padrão da linha utilizada nos componentes. */
    public static final int LINE_WIDTH = 1;
    
    /** Espaçamento dos diálogos. */
    public static final int DIALOG_CONTENT_PADDING = 20;
    /** Largura mínima dos diálogos. */
    public static final double DIALOG_MIN_WIDTH = 250;
    /** Altura mínima dos diálogos. */
    public static final double DIALOG_MIN_HEIGHT = 100;
    /** Altura dos botões dos diálogos. */
    public static final double DIALOG_BUTTON_HEIGHT = 30;
    
    /** Raio do componente deslizante. */
    public static final double SLIDER_RADIUS = 10;
    
    /** Tamanho do botão da barra de rolagem. */
    public static final double SCROLL_BAR_BUTTON_SIZE = 20;
    
    //**************************************************************************
    // Cores.
    // Os nomes são mantidos em maiúsculas para não misturar com os atributos.
    //**************************************************************************
    
    /** Cor padrão de fundo. */
    public static Color BACKGROUND_COLOR;
    /** Cor padrão da borda. */
    public static Color BORDER_COLOR;
    /** Cor padrão do texto. */
    public static Color TEXT_COLOR;
    
    /** Cor de fundo quando o mouse está em cima do componente. */
    public static Color MOUSE_OVER_BACKGROUND_COLOR;
    /** Cor da borda quando o mouse está em cima do componente. */
    public static Color MOUSE_OVER_BORDER_COLOR;
    /** Cor do texto quando o mouse está em cima do componente. */
    public static Color MOUSE_OVER_TEXT_COLOR;
    
    /** Cor de fundo quando o mouse está sendo pressionado no componente. */
    public static Color MOUSE_DOWN_BACKGROUND_COLOR;
    /** Cor da borda quando o mouse está sendo pressionado no componente. */
    public static Color MOUSE_DOWN_BORDER_COLOR;
    /** Cor do texto quando o mouse está sendo pressionado no componente. */
    public static Color MOUSE_DOWN_TEXT_COLOR;
    
    /** Cor de fundo quando o componente está desabilitado. */
    public static Color DISABLED_BACKGROUND_COLOR;
    /** Cor da borda quando o componente está desabilitado. */
    public static Color DISABLED_BORDER_COLOR;
    /** Cor do texto quando o componente está desabilitado. */
    public static Color DISABLED_TEXT_COLOR;
    
    /** Cor de fundo para os componentes contâineres. */
    public static Color CONTAINER_BACKGROUND_COLOR;
    /** Cor da borda para os componentes contâineres. */
    public static Color CONTAINER_BORDER_COLOR;
    /** Cor do texto para os componentes contâineres. */
    public static Color CONTAINER_TEXT_COLOR;
    /** Cor de fundo para as barras de título dos componentes contâineres. */
    public static Color CONTAINER_TITLE_BAR_BACKGROUND_COLOR;
    /** Cor da borda para as barras de título dos componentes contâineres. */
    public static Color CONTAINER_TITLE_BAR_BORDER_COLOR;
    /** Cor do texto para as barras de título dos componentes contâineres. */
    public static Color CONTAINER_TITLE_BAR_TEXT_COLOR;
    
    /** Cor de fundo para os componentes contâineres desabilitados. */
    public static Color DISABLED_CONTAINER_BACKGROUND_COLOR;
    /** Cor da borda para os componentes contâineres desabilitados. */
    public static Color DISABLED_CONTAINER_BORDER_COLOR;
    /** Cor do texto para os componentes contâineres desabilitados. */
    public static Color DISABLED_CONTAINER_TEXT_COLOR;
    /** Cor de fundo para as barras de título dos componentes contâineres desabilitados. */
    public static Color DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR;
    /** Cor da borda para as barras de título dos componentes contâineres desabilitados. */
    public static Color DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR;
    /** Cor do texto para as barras de título dos componentes contâineres desabilitados. */
    public static Color DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR;
    
    /** Cor de fundo para o progresso das barras de progresso. */
    public static Color PROGRESS_BAR_PROGRESS_FILL_COLOR;
    
    /** Cor de fundo para os contâineres de itens dos componentes de lista. */
    public static Color LIST_CONTAINER_BACKGROUND_COLOR;
    /** Cor de fundo para os contâineres de itens dos componentes de lista desabilitados. */
    public static Color DISABLED_LIST_CONTAINER_BACKGROUND_COLOR;
    
    /** Cor de fundo para a trila das barras de rolagem. */
    public static Color SCROLL_BAR_TRACK_COLOR;
    /** Cor de fundo para a trila das barras de rolagem desabiltiadas. */
    public static Color DISABLED_SCROLL_BAR_TRACK_COLOR;
    
    /** Cor da sobreposição do seletor de cor quando está desabilitado. */
    public static Color COLOR_PICKER_DISABLED_OVERLAY_COLOR;
    
    /** Cor da sobreposição dos diálogos. */
    public static Color DIALOG_OVERLAY_COLOR;
    
    /** Cor padrão de fundo. */
    public static Color TOOL_TIP_BACKGROUND_COLOR;
    /** Cor padrão da borda. */
    public static Color TOOL_TIP_BORDER_COLOR;
    /** Cor padrão do texto. */
    public static Color TOOL_TIP_TEXT_COLOR;
    
    static {
        GuiTheme.buildLightTheme().install();
    }
    
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
        this.backgroundColor = BACKGROUND_COLOR;
        this.borderColor = BORDER_COLOR;
        this.textColor = TEXT_COLOR;
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
            engine.setStrokeLineWidth( LINE_WIDTH );
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
     * @param drawingBounds Verdadeiro para desenhar, falso para não desenhar.
     */
    public void setDrawingBounds( boolean drawingBounds ) {
        this.drawingBounds = drawingBounds;
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

    /**
     * Obtém a cor de fundo do componente.
     * 
     * @return A cor de fundo do componente.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Configura a cor de fundo do componente.
     * 
     * @param backgroundColor A cor de fundo do componente.
     */
    public void setBackgroundColor( Color backgroundColor ) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Obtém a cor da borda do componente.
     * 
     * @return A cor da borda do componente.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Configura a cor da borda do componente.
     * 
     * @param borderColor A cor da borda do componente.
     */
    public void setBorderColor( Color borderColor ) {
        this.borderColor = borderColor;
    }

    /**
     * Obtém a cor do texto do componente.
     * 
     * @return A cor do texto do componente.
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * Configura a cor do texto do componente.
     * 
     * @param textColor A cor do texto do componente.
     */
    public void setTextColor( Color textColor ) {
        this.textColor = textColor;
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
