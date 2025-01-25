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
import br.com.davidbuzatto.jsge.font.FontUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Componentes que contém texto na representação gráfica.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class GuiTextComponent extends GuiComponent {

    /** Alinhamento à esquerda na horizontal. */
    public static final int LEFT_ALIGNMENT = 1;
    /** Alinhamento ao centro na horizontal. */
    public static final int CENTER_ALIGNMENT = 2;
    /** Alinhamento à direita na horizontal. */
    public static final int RIGHT_ALIGNMENT = 3;
    /** Alinhamento acima na vertical. */
    public static final int TOP_ALIGNMENT = 4;
    /** Alinhamento ao meio na vertical. */
    public static final int MIDDLE_ALIGNMENT = 5;
    /** Alinhamento abaixo na vertical. */
    public static final int BOTTOM_ALIGNMENT = 6;
    
    private static BufferedImage dummyImage = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
    
    protected String text;
    protected int textWidth = -1;
    protected int textLineHeight = -1;

    /**
     * Cria o componente.
     * 
     * @param bounds
     * @param text Texto utilizado no componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiTextComponent( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, engine );
        this.text = text;
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds
     * @param text Texto utilizado no componente. 
     */
    public GuiTextComponent( Rectangle bounds, String text ) {
        super( bounds );
        this.text = text;
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
     * @param text Texto utilizado no componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiTextComponent( double x, double y, double width, double height, String text, EngineFrame engine ) {
        this( new Rectangle( x, y, width, height ), text, engine );
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
    public GuiTextComponent( double x, double y, double width, double height, String text ) {
        this( new Rectangle( x, y, width, height ), text );
    }
    
    /**
     * Desenha texto centralizado na vertical.
     * 
     * @param textColor Cor do texto.
     */
    protected void drawText( Color textColor ) {
        engine.drawText(
                text,
                bounds.x,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Desenha texto centralizado na vertical com deslocamento.
     * 
     * @param textColor Cor do texto.
     * @param xOffset Deslocamento no eixo x.
     * @param yOffset Deslocamento no eixo y.
     */
    protected void drawText( Color textColor, double xOffset, double yOffset ) {
        engine.drawText(
                text,
                bounds.x + xOffset,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4 + yOffset,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Desenha texto centralizado na vertical depois do retângulo de limite.
     * 
     * @param textColor Cor do texto.
     */
    protected void drawTextAfterBounds( Color textColor ) {
        engine.drawText(
                text,
                bounds.x + bounds.width,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Desenha texto centralizado na vertical depois do retângulo de limite, 
     * com deslocamento.
     * 
     * @param textColor Cor do texto.
     * @param xOffset Deslocamento no eixo x.
     */
    protected void drawTextAfterBounds( Color textColor, double xOffset ) {
        engine.drawText(
                text,
                bounds.x + bounds.width + xOffset,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Desenha texto centralizado na vertical e na horizontal.
     * 
     * @param textColor Cor do texto.
     */
    protected void drawCenteredText( Color textColor ) {
        if ( textWidth == -1 ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        engine.drawText(
                text,
                bounds.x + bounds.width / 2 - textWidth / 2,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }

    /**
     * Obtém o texto do componente.
     * 
     * @return O texto do componente.
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * Configura o texto do componente.
     * 
     * @param text Texto utilizado no componente. 
     */
    public void setText( String text ) {
        this.text = text;
        updateTextProperties();
    }
    
    protected void updateTextProperties() {
        
        Graphics g = dummyImage.createGraphics();
        g.setFont( FontUtils.DEFAULT_FONT.deriveFont( (float) FONT_SIZE ) );
        FontMetrics fm = g.getFontMetrics();
        
        textWidth = fm.stringWidth( text );
        textLineHeight = (int) fm.getStringBounds( text, g ).getHeight();
        g.dispose();
        
    }
    
    protected Vector2 calculateStartPosition( int horizontalAlignment, int verticalAlignment ) {
        
        Vector2 pos = new Vector2();
        
        updateTextProperties();
        
        switch ( horizontalAlignment ) {
            case LEFT_ALIGNMENT -> pos.x = 0;
            case CENTER_ALIGNMENT -> pos.x = bounds.width / 2 - textWidth / 2;
            case RIGHT_ALIGNMENT -> pos.x = bounds.width - textWidth;
        }
        
        switch ( verticalAlignment ) {
            case TOP_ALIGNMENT -> pos.y = 2;
            case MIDDLE_ALIGNMENT -> pos.y = bounds.height / 2 - textLineHeight / 4;
            case BOTTOM_ALIGNMENT -> pos.y = bounds.height - textLineHeight / 2;
        }
        
        return pos;
        
    }

}
