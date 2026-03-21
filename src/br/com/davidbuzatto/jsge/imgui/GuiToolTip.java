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
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * A tooltip component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiToolTip extends GuiTextComponent {
    
    private static final int HORIZONTAL_PADDING = 10;
    private static final int VERTICAL_PADDING = 10;
    private static final double TIME_TO_SHOW = 1;
    
    private GuiComponent component;
    private double timeToShowCounter;
    
    /**
     * Creates the component.
     *
     * @param component The component that will receive the tooltip.
     * @param text Text used in the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiToolTip( GuiComponent component, String text, EngineFrame engine ) {
        super( 0, 0, 0, 0, text, engine );
        initData( component );
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param component The component that will receive the tooltip.
     * @param text Text used in the component.
     */
    public GuiToolTip( GuiComponent component, String text ) {
        super( 0, 0, 0, 0, text );
        initData( component );
    }
    
    private void initData( GuiComponent component ) {
        this.component = component;
        this.backgroundColor = TOOL_TIP_BACKGROUND_COLOR;
        this.borderColor = TOOL_TIP_BORDER_COLOR;
        this.textColor = TOOL_TIP_TEXT_COLOR;
    }

    @Override
    public void update( double delta ) {
        
        if ( component.isVisible() && component.isEnabled() && component.isMouseOver() ) {
            
            timeToShowCounter += delta;
            
            if ( timeToShowCounter >= TIME_TO_SHOW ) {
                updateTextProperties();
                updateBounds( engine.getMousePositionPoint() );
                visible = true;
            }
            
        } else {
            visible = false;
            timeToShowCounter = 0;
        }
    }
    
    @Override
    public void draw() {
        
        if ( component.isVisible() && component.isEnabled() && component.isMouseOver() && visible ) {
            drawToolTip( borderColor, backgroundColor, textColor );
        }
        
    }
    
    private void drawToolTip( Color borderColor, Color backgroundColor, Color textColot ) {
        engine.fillRectangle( bounds, backgroundColor );
        engine.drawRectangle( bounds, borderColor );
        engine.drawText( text, bounds.x + HORIZONTAL_PADDING, bounds.y + VERTICAL_PADDING, FONT_SIZE, textColor );
    }
    
    private void updateBounds( Vector2 position ) {
        
        bounds.x = position.x;
        bounds.y = position.y + 20;
        
        bounds.width = textWidth + HORIZONTAL_PADDING * 2;
        bounds.height = textLineHeight + VERTICAL_PADDING;
        
        if ( bounds.x + bounds.width > engine.getScreenWidth() ) {
            bounds.x -= bounds.width;
        }
        
        if ( bounds.y + bounds.height > engine.getScreenHeight() ) {
            bounds.y = position.y - bounds.height - 5;
        }
        
    }
    
    @Override
    public void setText( String text ) {
        this.text = text;
    }

    @Override
    public void move( double xAmount, double yAmount ) {
    }

    @Override
    public void setEnabled( boolean enabled ) {
        throw new IllegalStateException( "This component cannot be manually enabled/disabled." );
    }
    
    @Override
    public void setVisible( boolean visible ) {
        throw new IllegalStateException( "This component cannot be manually turned visible/invisible." );
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        setBackgroundColor( theme.toolTipBackgroundColor );
        setBorderColor( theme.toolTipBorderColor );
        setTextColor( theme.toolTipTextColor );
    }
    
}
