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
    
    private static final int ITEM_BOUND_HEIGHT = 30;
    private boolean initialized;
    
    private List<String> itemsText;
    private List<ListItem> items;
    private double itemTextHeight;
    
    private GuiSlider scrollBar;
    private boolean useScrollBar;
    private double itemsHeight;
    private double heightDiff;
    
    private double scrollOffset;
    private double prevScrollOffset;
    
    public GuiList( double x, double y, double width, double height, List<String> itemsText, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initComponents( engine, itemsText );
    }
    
    public GuiList( double x, double y, double width, double height, List<String> itemsText ) {
        super( x, y, width, height );
        initComponents( null, itemsText );
    }
    
    public GuiList( Rectangle bounds, List<String> itemsText, EngineFrame engine ) {
        super( bounds, engine );
        initComponents( engine, itemsText );
    }
    
    public GuiList( Rectangle bounds, List<String> itemsText ) {
        super( bounds );
        initComponents( null, itemsText );
    }
    
    private void initComponents( EngineFrame engine, List<String> itemsText ) {
        scrollBar = new GuiSlider( bounds.x + bounds.width, bounds.y, GuiSlider.SLIDER_RADIUS * 2, bounds.height, itemsText.size(), 0, itemsText.size(), GuiSlider.VERTICAL );
        this.itemsText = itemsText;
        this.items = new ArrayList<>();
        this.useScrollBar = true;
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
                        drawList( MOUSE_OVER_BORDER_COLOR, LIST_CONTAINER_BACKGROUND_COLOR, SCROLL_BAR_TRACK_COLOR );
                        break;
                    case MOUSE_DOWN:
                        drawList( MOUSE_OVER_BORDER_COLOR, LIST_CONTAINER_BACKGROUND_COLOR, SCROLL_BAR_TRACK_COLOR );
                        break;
                    default:
                        drawList( BORDER_COLOR, LIST_CONTAINER_BACKGROUND_COLOR, SCROLL_BAR_TRACK_COLOR );
                        break;
                }
            } else {
                drawList( DISABLED_BORDER_COLOR, DISABLED_LIST_CONTAINER_BACKGROUND_COLOR, DISABLED_SCROLL_BAR_TRACK_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawList( Color borderColor, Color containerColor, Color scrollBarTrackColor ) {
        
        engine.fillRectangle( scrollBar.bounds, scrollBarTrackColor );
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

            Rectangle lastItemBounds = items.getLast().bounds;
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
            
            Color itemBackgroundColor = GuiComponent.BACKGROUND_COLOR;
            Color itemBorderColor = GuiComponent.BORDER_COLOR;
            Color itemTextColor = GuiComponent.TEXT_COLOR;
            
            boolean drawBounds = true;
            
            if ( selected ) {
                itemBackgroundColor = GuiComponent.MOUSE_DOWN_BACKGROUND_COLOR;
                itemBorderColor = GuiComponent.MOUSE_DOWN_BORDER_COLOR;
                itemTextColor = GuiComponent.MOUSE_DOWN_TEXT_COLOR;
            } else if ( mouseOver ) {
                itemBackgroundColor = GuiComponent.MOUSE_OVER_BACKGROUND_COLOR;
                itemBorderColor = GuiComponent.MOUSE_OVER_BORDER_COLOR;
                itemTextColor = GuiComponent.MOUSE_OVER_TEXT_COLOR;
            } else {
                drawBounds = false;
            }
            
            if ( !enabled ) {
                itemBackgroundColor = GuiComponent.DISABLED_BACKGROUND_COLOR;
                itemBorderColor = GuiComponent.DISABLED_BORDER_COLOR;
                itemTextColor = GuiComponent.DISABLED_TEXT_COLOR;
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
    
    public String getSelectedItemText() {
        for ( ListItem item : items ) {
            if ( item.selected ) {
                return item.text;
            }
        }
        // não deve chegar aqui
        return null;
    }
    
    public int getSelectedItemIndex() {
        int index = -1;
        for ( ListItem item : items ) {
            index++;
            if ( item.selected ) {
                return index;
            }
        }
        // não deve chegar aqui
        return index;
    }
    
}
