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
import java.awt.Color;

/**
 * Um container do tipo grupo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiGroup extends GuiTextComponent {
    
    public GuiGroup( double x, double y, double width, double height, String title, EngineFrame engine ) {
        super( x, y, width, height, title, engine );
    }
    
    public GuiGroup( double x, double y, double width, double height, String title ) {
        super( x, y, width, height, title );
    }
    
    public GuiGroup( Rectangle bounds, String title, EngineFrame engine ) {
        super( bounds, title, engine );
    }
    
    public GuiGroup( Rectangle bounds, String title ) {
        super( bounds, title );
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            engine.setStrokeLineWidth( LINE_WIDTH );
            if ( enabled ) {
                drawGroup( CONTAINER_BORDER_COLOR, CONTAINER_TEXT_COLOR);
            } else {
                drawGroup( DISABLED_CONTAINER_BORDER_COLOR, DISABLED_CONTAINER_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    private void drawGroup( Color borderColor, Color textColor ) {
        
        if ( textWidth == -1 && text != null ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        
        if ( textWidth > 0 ) {
            engine.drawLine( bounds.x, bounds.y, bounds.x + 6, bounds.y, borderColor );
            engine.drawLine( bounds.x + textWidth + 16, bounds.y, bounds.x + bounds.width, bounds.y, borderColor );
            engine.drawLine( bounds.x, bounds.y, bounds.x, bounds.y + bounds.height, borderColor );
            engine.drawLine( bounds.x + bounds.width, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, borderColor );
            engine.drawLine( bounds.x, bounds.y + bounds.height, bounds.x + bounds.width, bounds.y + bounds.height, borderColor );
            engine.drawText( text, bounds.x + 12, bounds.y - 3, FONT_SIZE, textColor );
        } else {
            engine.drawRectangle( bounds, borderColor );
        }
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
}
