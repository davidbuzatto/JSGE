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
import java.util.ArrayList;
import java.util.List;

/**
 * Um componente de lista de items.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiList extends GuiComponent {
    
    public static final int ITEM_BOUND_HEIGHT = 30;
    private boolean initialized;
    
    private List<String> itemsText;
    private List<ListItem> items;
    private double itemTextHeight;
    
    protected GuiScrollBar scrollBar;
    private boolean useScrollBar;
    private double itemsHeight;
    private double heightDiff;
    
    private double scrollOffset;
    private double prevScrollOffset;
    
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
    public GuiList( double x, double y, double width, double height, List<String> itemsText, EngineFrame engine ) {
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
    public GuiList( double x, double y, double width, double height, List<String> itemsText ) {
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
    public GuiList( Rectangle bounds, List<String> itemsText, EngineFrame engine ) {
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
    public GuiList( Rectangle bounds, List<String> itemsText ) {
        super( bounds );
        initComponents( null, itemsText );
    }
    
    private void initComponents( EngineFrame engine, List<String> itemsText ) {
        
        if ( engine == null ) {
            scrollBar = new GuiScrollBar( bounds.x + bounds.width, bounds.y, SLIDER_RADIUS * 2, bounds.height, itemsText.size(), 0, itemsText.size(), GuiSlider.VERTICAL );
        } else {
            scrollBar = new GuiScrollBar( bounds.x + bounds.width, bounds.y, SLIDER_RADIUS * 2, bounds.height, itemsText.size(), 0, itemsText.size(), GuiSlider.VERTICAL, engine );
        }
        
        this.itemsText = itemsText;
        this.items = new ArrayList<>();
        this.useScrollBar = true;
        this.backgroundColor = LIST_CONTAINER_BACKGROUND_COLOR;
        
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();
            
            if ( useScrollBar ) {
                
                double mouseWheelMove = engine.getMouseWheelMove();
                
                if ( mouseState == GuiComponentMouseState.MOUSE_OVER ) {
                    scrollBar.setValue( scrollBar.getValue() + mouseWheelMove );
                }
                
                scrollBar.update( delta );
                scrollOffset = heightDiff * ( 1 - ( scrollBar.getValue() / items.size() ) );
                if ( !Double.isNaN( scrollOffset ) ) {
                    double scrollDiff = prevScrollOffset - scrollOffset;
                    for ( ListItem item : items ) {
                        item.bounds.y += scrollDiff;
                    }
                    prevScrollOffset = scrollOffset;
                }
                
            }
            
            switch ( mouseState ) {
                case MOUSE_OVER:
                    for ( ListItem item : items ) {
                        item.mouseOver = item.checkCollision( mousePos );
                    }
                    break;
                case MOUSE_DOWN:
                    ListItem selectedItem = null;
                    ListItem newSelectedItem = null;
                    for ( ListItem item : items ) {
                        if ( item.selected ) {
                            selectedItem = item;
                            break;
                        }
                    }
                    for ( ListItem item : items ) {
                        if ( item.checkCollision( mousePos ) ) {
                            newSelectedItem = item;
                            break;
                        }
                    }
                    if ( newSelectedItem != null && newSelectedItem != selectedItem ) {
                        newSelectedItem.selected = true;
                        selectedItem.selected = false;
                    }
                    break;
                default:
                    for ( ListItem item : items ) {
                        item.mouseOver = false;
                    }
                    break;
            }
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( !initialized ) {
            createItems();
        }
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                switch ( mouseState ) {
                    case MOUSE_OVER:
                        drawList( MOUSE_OVER_BORDER_COLOR, backgroundColor );
                        break;
                    case MOUSE_DOWN:
                        drawList( MOUSE_OVER_BORDER_COLOR, backgroundColor );
                        break;
                    default:
                        drawList( borderColor, backgroundColor );
                        break;
                }
            } else {
                drawList( DISABLED_BORDER_COLOR, DISABLED_LIST_CONTAINER_BACKGROUND_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawList( Color borderColor, Color containerColor ) {
        
        //engine.fillRectangle( scrollBar.bounds, scrollBarTrackColor );
        engine.fillRectangle( bounds, containerColor );
        
        engine.beginScissorMode( bounds );
        
        for ( ListItem item : items ) {
            item.draw();
        }
        
        engine.endScissorMode();
        
        if ( useScrollBar ) {
            scrollBar.draw();
        }
        
        engine.drawRectangle( bounds.x, bounds.y, bounds.width + scrollBar.bounds.width, bounds.height, borderColor );
        
    }
    
    private void createItems() {
        
        if ( !itemsText.isEmpty() && items.isEmpty() ) {
            
            itemTextHeight = engine.measureTextBounds( " ", FONT_SIZE ).height;
            double vOffset = 0;
            boolean first = true;

            for ( String text : itemsText ) {
                items.add( 
                        new ListItem( 
                                text, 
                                new Rectangle( bounds.x + 3, bounds.y + 3 + vOffset, bounds.width - 6, ITEM_BOUND_HEIGHT ),
                                first
                        )
                );
                vOffset += ITEM_BOUND_HEIGHT + 3;
                first = false;
            }
            
            // JDK 21 e posteriores :D
            //Rectangle lastItemBounds = items.getLast().bounds;
            
            Rectangle lastItemBounds = items.get( items.size() - 1 ).bounds;
            itemsHeight = lastItemBounds.y + lastItemBounds.height - bounds.y;
            heightDiff = itemsHeight - bounds.height + 3;

            if ( lastItemBounds.y + lastItemBounds.height > bounds.y + bounds.height ) {
                useScrollBar = true;
            } else {
                useScrollBar = false;
                scrollBar.setEnabled( false );
            }
            
            initialized = true;
            
        }
        
        if ( !initialized ) {
            useScrollBar = false;
            scrollBar.setEnabled( false );
            initialized = true;
        }
            
    }
    
    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        if ( useScrollBar ) {
            scrollBar.setEnabled( enabled );
        }
    }
    
    /**
     * Retorna o texto do item selecionado no momento.
     * 
     * @return O texto do item selecionado ou null caso nenhum item esteja
     * selecionado.
     */
    public String getSelectedItemText() {
        for ( ListItem item : items ) {
            if ( item.selected ) {
                return item.text;
            }
        }
        return null;
    }
    
    /**
     * Retorna o índice do item selecionado no momento.
     * 
     * @return O índice do item selecionado ou -1 caso nenhum item esteja
     * selecionado.
     */
    public int getSelectedItemIndex() {
        int index = -1;
        for ( ListItem item : items ) {
            index++;
            if ( item.selected ) {
                return index;
            }
        }
        return index;
    }

    /**
     * Retorna a altura do texto utilizada do desenho do componente.
     * 
     * @return A altura do texto.
     */
    protected double getItemTextHeight() {
        return itemTextHeight;
    }
    
    /**
     * Retorna se o ponteiro do mouse está totalmente fora do componente, ou seja,
     * fora do retângulo limítrofe e da barra de rolagem.
     * 
     * @return Verdadeiro caso esteja fora, falso caso contrário.
     */
    public boolean isMouseOutEntirely() {
        return mouseState == GuiComponentMouseState.MOUSE_OUT && scrollBar.mouseState == GuiComponentMouseState.MOUSE_OUT;
    }
    
    /**
     * Obtém a cor do fundo da barra de rolagem.
     * 
     * @return A cor do fundo.
     */
    public Color getScrollBarBackgroundColor() {
        return scrollBar.getBackgroundColor();
    }
    
    /**
     * Confira a cor do fundo da barra de rolagem.
     * 
     * @param backgroundColor A cor do fundo.
     */
    public void setScrollBarBackgroundColor( Color backgroundColor ) {
        scrollBar.setBackgroundColor( backgroundColor );
    }
    
    /**
     * Obtém a cor da borda da barra de rolagem.
     * 
     * @return A cor da borda.
     */
    public Color getScrollBarBorderColor() {
        return scrollBar.getBorderColor();
    }
    
    /**
     * Confira a cor da borda da barra de rolagem.
     * 
     * @param borderColor A cor da borda.
     */
    public void setScrollBarBorderColor( Color borderColor ) {
        scrollBar.setBorderColor( borderColor );
    }
    
    /**
     * Obtém a cor do texto da barra de rolagem.
     * 
     * @return A cor do texto.
     */
    public Color getScrollBarTextColor() {
        return scrollBar.getTextColor();
    }
    
    /**
     * Confira a cor do texto da barra de rolagem.
     * 
     * @param textColor A cor do texto.
     */
    public void setScrollBarTextColor( Color textColor ) {
        scrollBar.setTextColor( textColor );
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        scrollBar.move( xAmount, yAmount );
        for ( ListItem item : items ) {
            item.bounds.x += xAmount;
            item.bounds.y += yAmount;
        }
    }
    
    private class ListItem {
        
        private final String text;
        private final Rectangle bounds;
        private boolean selected;
        private boolean mouseOver;
        
        public ListItem( String text, Rectangle bounds, boolean selected ) {
            this.text = text;
            this.bounds = bounds;
            this.selected = selected;
        }
        
        public void draw() {
            
            double textWidth = engine.measureText( text, FONT_SIZE );
            
            Color itemBackgroundColor = backgroundColor;
            Color itemBorderColor = borderColor;
            Color itemTextColor = textColor;
            
            boolean drawBounds = true;
            
            if ( selected ) {
                itemBackgroundColor = MOUSE_DOWN_BACKGROUND_COLOR;
                itemBorderColor = MOUSE_DOWN_BORDER_COLOR;
                itemTextColor = MOUSE_DOWN_TEXT_COLOR;
            } else if ( mouseOver ) {
                itemBackgroundColor = MOUSE_OVER_BACKGROUND_COLOR;
                itemBorderColor = MOUSE_OVER_BORDER_COLOR;
                itemTextColor = MOUSE_OVER_TEXT_COLOR;
            } else {
                drawBounds = false;
            }
            
            if ( !enabled ) {
                itemBackgroundColor = DISABLED_BACKGROUND_COLOR;
                itemBorderColor = DISABLED_BORDER_COLOR;
                itemTextColor = DISABLED_TEXT_COLOR;
            }
            
            if ( drawBounds ) {
                engine.fillRectangle( bounds, itemBackgroundColor );
                engine.drawRectangle( bounds, itemBorderColor );
            }
            engine.drawText( text, bounds.x + bounds.width / 2 - textWidth / 2, bounds.y + bounds.height / 2 - itemTextHeight / 5, FONT_SIZE, itemTextColor );
            
        }
        
        public boolean checkCollision( Vector2 point ) {
            return CollisionUtils.checkCollisionPointRectangle( point, bounds );
        }
        
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        setBackgroundColor( theme.listContainerBackgroundColor );
        scrollBar.apply( theme );
    }
    
}
