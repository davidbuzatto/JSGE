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
 * Um separador do tipo linha.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiLine extends GuiTextComponent {
    
    public GuiLine( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    public GuiLine( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    public GuiLine( double x, double y, double width, double height, EngineFrame engine ) {
        this( x, y, width, height, null, engine );
    }
    
    public GuiLine( double x, double y, double width, double height ) {
        super( x, y, width, height, null );
    }
    
    public GuiLine( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    public GuiLine( Rectangle bounds, String text ) {
        super( bounds, text );
    }
    
    public GuiLine( Rectangle bounds, EngineFrame engine ) {
        this( bounds, null, engine );
    }
    
    public GuiLine( Rectangle bounds ) {
        super( bounds, null );
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            engine.setStrokeLineWidth( LINE_WIDTH );
            if ( enabled ) {
                drawLine( CONTAINER_BORDER_COLOR, CONTAINER_TEXT_COLOR);
            } else {
                drawLine( DISABLED_CONTAINER_BORDER_COLOR, DISABLED_CONTAINER_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    private void drawLine( Color borderColor, Color textColor ) {
        
        if ( textWidth == -1 && text != null ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        
        if ( text != null ) {
            engine.drawLine( bounds.x, bounds.y + bounds.height / 2, bounds.x + 6, bounds.y + bounds.height / 2, borderColor );
            engine.drawLine( bounds.x + textWidth + 16, bounds.y + bounds.height / 2, bounds.x + bounds.width, bounds.y + bounds.height / 2, borderColor );
            engine.drawText( text, bounds.x + 12, bounds.y + bounds.height / 2 - 3, FONT_SIZE, textColor );
        } else {
            engine.drawLine( bounds.x, bounds.y + bounds.height / 2, bounds.x + bounds.width, bounds.y + bounds.height / 2, borderColor );
        }
        
    }
    
}
