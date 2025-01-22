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
    
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private int orientation;
    
    public GuiLine( double x, double y, double width, double height, String text, EngineFrame engine ) {
        this( x, y, width, height, text, HORIZONTAL, engine );
    }
    
    public GuiLine( double x, double y, double width, double height, String text ) {
        this( x, y, width, height, text, HORIZONTAL );
    }
    
    public GuiLine( Rectangle bounds, String text, EngineFrame engine ) {
        this( bounds, text, HORIZONTAL, engine );
    }
    
    public GuiLine( Rectangle bounds, String text ) {
        this( bounds, text, HORIZONTAL );
    }
    
    public GuiLine( double x, double y, double width, double height, String text, int orientation, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
        this.orientation = orientation;
    }
    
    public GuiLine( double x, double y, double width, double height, String text, int orientation ) {
        super( x, y, width, height, text );
        this.orientation = orientation;
    }
    
    public GuiLine( Rectangle bounds, String text, int orientation, EngineFrame engine ) {
        super( bounds, text, engine );
        this.orientation = orientation;
    }
    
    public GuiLine( Rectangle bounds, String text, int orientation ) {
        super( bounds, text );
        this.orientation = orientation;
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
        
        if ( orientation == VERTICAL ) {
            if ( textWidth > 0 ) {
                engine.drawLine( bounds.x, bounds.y + bounds.height / 2, bounds.x + 6, bounds.y + bounds.height / 2, borderColor );
                engine.drawLine( bounds.x + textWidth + 16, bounds.y + bounds.height / 2, bounds.x + bounds.width, bounds.y + bounds.height / 2, borderColor );
                engine.drawText( text, bounds.x + 12, bounds.y + bounds.height / 2 - 3, FONT_SIZE, textColor );
                engine.drawLine( bounds.x + bounds.width / 2, bounds.y, bounds.x + bounds.width / 2, bounds.y + bounds.height, borderColor );
            } else {
                engine.drawLine( bounds.x + bounds.width / 2, bounds.y, bounds.x + bounds.width / 2, bounds.y + bounds.height, borderColor );
            }
        } else {
            if ( textWidth > 0 ) {
                engine.drawLine( bounds.x, bounds.y + bounds.height / 2, bounds.x + 6, bounds.y + bounds.height / 2, borderColor );
                engine.drawLine( bounds.x + textWidth + 16, bounds.y + bounds.height / 2, bounds.x + bounds.width, bounds.y + bounds.height / 2, borderColor );
                engine.drawText( text, bounds.x + 12, bounds.y + bounds.height / 2 - 3, FONT_SIZE, textColor );
            } else {
                engine.drawLine( bounds.x, bounds.y + bounds.height / 2, bounds.x + bounds.width, bounds.y + bounds.height / 2, borderColor );
            }
        }
        
    }
    
}
