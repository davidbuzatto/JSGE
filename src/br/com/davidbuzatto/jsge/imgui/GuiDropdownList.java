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
import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.Color;
import java.util.List;

/**
 * Um componente de lista de items suspensos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiDropdownList extends GuiComponent {
    
    protected GuiList itemsList;
    private boolean wasSelected;
    
    /**
     * Cria o componente.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param itemsText Lista com o texto dos itens da lista.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiDropdownList( double x, double y, double width, double height, List<String> itemsText, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initComponents( engine, itemsText );
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
     * @param itemsText Lista com o texto dos itens da lista.
     */
    public GuiDropdownList( double x, double y, double width, double height, List<String> itemsText ) {
        super( x, y, width, height );
        initComponents( null, itemsText );
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param itemsText Lista com o texto dos itens da lista.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiDropdownList( Rectangle bounds, List<String> itemsText, EngineFrame engine ) {
        super( bounds, engine );
        initComponents( engine, itemsText );
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param itemsText Lista com o texto dos itens da lista.
     */
    public GuiDropdownList( Rectangle bounds, List<String> itemsText ) {
        super( bounds );
        initComponents( null, itemsText );
    }
    
    private void initComponents( EngineFrame engine, List<String> itemsText ) {
        
        //bounds.width += SLIDER_RADIUS * 2;
        
        int size = MathUtils.clamp( itemsText.size(), 1, 4 );
        
        if ( engine == null ) {
            itemsList = new GuiList( bounds.x, bounds.y + bounds.height + 3, bounds.width - SLIDER_RADIUS * 2, 3 + GuiList.ITEM_BOUND_HEIGHT * size + 3 * size, itemsText );
        } else {
            itemsList = new GuiList( bounds.x, bounds.y + bounds.height + 3, bounds.width - SLIDER_RADIUS * 2, 3 + GuiList.ITEM_BOUND_HEIGHT * size + 3 * size, itemsText, engine );
        }
        
        itemsList.setEnabled( false );
        itemsList.setVisible( false );
        
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            itemsList.update( delta );
            
            if ( isMousePressed() ) {
                itemsList.setVisible( !itemsList.isVisible() );
                itemsList.setEnabled( !itemsList.isEnabled() );
            }
            
            if ( wasSelected ) {
                itemsList.setVisible( false );
                itemsList.setEnabled( false );
                wasSelected = false;
            }
            
            if ( itemsList.isMousePressed() ) {
                wasSelected = true;
            }
            
            if ( mouseState == GuiComponentMouseState.MOUSE_OUT && 
                 itemsList.isMouseOutEntirely() &&
                 engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                itemsList.setVisible( false );
                itemsList.setEnabled( false );
                wasSelected = false;
            }
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                if ( itemsList.isVisible() ) {
                    drawDropdownList( MOUSE_OVER_BORDER_COLOR, MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_TEXT_COLOR );
                } else {
                    switch ( mouseState ) {
                        case MOUSE_OVER:
                            drawDropdownList( MOUSE_OVER_BORDER_COLOR, MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_TEXT_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawDropdownList( MOUSE_OVER_BORDER_COLOR, MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_TEXT_COLOR );
                            break;
                        default:
                            drawDropdownList( borderColor, backgroundColor, textColor );
                            break;
                    }
                }
            } else {
                drawDropdownList( DISABLED_BORDER_COLOR, DISABLED_BACKGROUND_COLOR, DISABLED_TEXT_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawDropdownList( Color borderColor, Color containerColor, Color textColor ) {
        
        itemsList.draw();
        
        engine.fillRectangle( bounds, containerColor );
        engine.drawRectangle( bounds, borderColor );
        
        String selectedText = itemsList.getSelectedItemText();
        selectedText = selectedText == null ? "" : selectedText;
        
        double textWidth = engine.measureText( selectedText, FONT_SIZE );
        engine.drawText( selectedText, bounds.x + bounds.width / 2 - textWidth / 2, bounds.y + bounds.height / 2 - itemsList.getItemTextHeight() / 5, FONT_SIZE, textColor );
        
        engine.fillPolygon( bounds.x + bounds.width - SLIDER_RADIUS, bounds.y + bounds.height / 2, 3, 6, 90, textColor );
        
    }
    
    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        //itemsList.setEnabled( enabled );
    }
    
    /**
     * Retorna o texto do item selecionado no momento.
     * 
     * @return O texto do item selecionado ou null caso nenhum item esteja
     * selecionado.
     */
    public String getSelectedItemText() {
        return itemsList.getSelectedItemText();
    }
    
    /**
     * Retorna o índice do item selecionado no momento.
     * 
     * @return O índice do item selecionado ou -1 caso nenhum item esteja
     * selecionado.
     */
    public int getSelectedItemIndex() {
        return itemsList.getSelectedItemIndex();
    }
    
    /**
     * Retorna se a lista de itens está visível no momento.
     * 
     * @return Verdadeiro caso esteja visível, falso caso contrário.
     */
    public boolean isDropdownListVisible() {
        return itemsList.isVisible();
    }
    
    /**
     * Obtém a cor do fundo da barra de rolagem.
     * 
     * @return A cor do fundo.
     */
    public Color getScrollBarBackgroundColor() {
        return itemsList.scrollBar.getBackgroundColor();
    }
    
    /**
     * Confira a cor do fundo da barra de rolagem.
     * 
     * @param backgroundColor A cor do fundo.
     */
    public void setScrollBarBackgroundColor( Color backgroundColor ) {
        itemsList.scrollBar.setBackgroundColor( backgroundColor );
    }
    
    /**
     * Obtém a cor da borda da barra de rolagem.
     * 
     * @return A cor da borda.
     */
    public Color getScrollBarBorderColor() {
        return itemsList.scrollBar.getBorderColor();
    }
    
    /**
     * Confira a cor da borda da barra de rolagem.
     * 
     * @param borderColor A cor da borda.
     */
    public void setScrollBarBorderColor( Color borderColor ) {
        itemsList.scrollBar.setBorderColor( borderColor );
    }
    
    /**
     * Obtém a cor do texto da barra de rolagem.
     * 
     * @return A cor do texto.
     */
    public Color getScrollBarTextColor() {
        return itemsList.scrollBar.getTextColor();
    }
    
    /**
     * Confira a cor do texto da barra de rolagem.
     * 
     * @param textColor A cor do texto.
     */
    public void setScrollBarTextColor( Color textColor ) {
        itemsList.scrollBar.setTextColor( textColor );
    }

    @Override
    public void setBackgroundColor( Color backgroundColor ) {
        super.setBackgroundColor( backgroundColor );
        itemsList.setBackgroundColor( backgroundColor );
    }

    @Override
    public void setBorderColor( Color borderColor ) {
        super.setBorderColor( borderColor );
        itemsList.setBorderColor( borderColor );
    }

    @Override
    public void setTextColor( Color textColor ) {
        super.setTextColor( textColor );
        itemsList.setTextColor( textColor );
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        itemsList.move( xAmount, yAmount );
    }
    
}
