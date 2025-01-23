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
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.List;

/**
 * Um componente de lista de items suspensos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiDropdownList extends GuiComponent {
    
    private GuiList itemsList;
    private boolean wasSelected;
    
    /**
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
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param itemsText Lista com o texto dos itens da lista.
     */
    public GuiDropdownList( Rectangle bounds, List<String> itemsText ) {
        super( bounds );
        initComponents( null, itemsText );
    }
    
    private void initComponents( EngineFrame engine, List<String> itemsText ) {
        
        bounds.width += GuiSlider.SLIDER_RADIUS * 2;
        
        int size = MathUtils.clamp( itemsText.size(), 1, 4 );
        
        if ( engine == null ) {
            itemsList = new GuiList( bounds.x, bounds.y + bounds.height + 3, bounds.width - GuiSlider.SLIDER_RADIUS * 2, 3 + GuiList.ITEM_BOUND_HEIGHT * size + 3 * size, itemsText );
        } else {
            itemsList = new GuiList( bounds.x, bounds.y + bounds.height + 3, bounds.width - GuiSlider.SLIDER_RADIUS * 2, 3 + GuiList.ITEM_BOUND_HEIGHT * size + 3 * size, itemsText, engine );
        }
        
        itemsList.setEnabled( false );
        itemsList.setVisible( false );
        
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();
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
                            drawDropdownList( BORDER_COLOR, BACKGROUND_COLOR, TEXT_COLOR );
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
        
        engine.fillPolygon( bounds.x + bounds.width - GuiSlider.SLIDER_RADIUS, bounds.y + bounds.height / 2, 3, 6, 90, textColor );
        
    }
    
    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        itemsList.setEnabled( enabled );
    }
    
    /**
     * 
     * @return 
     */
    public String getSelectedItemText() {
        return itemsList.getSelectedItemText();
    }
    
    /**
     * 
     * @return 
     */
    public int getSelectedItemIndex() {
        return itemsList.getSelectedItemIndex();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isDropdownListVisible() {
        return itemsList.isVisible();
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        itemsList.move( xAmount, yAmount );
    }
    
}
