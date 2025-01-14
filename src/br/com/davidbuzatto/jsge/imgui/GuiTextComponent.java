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
import static br.com.davidbuzatto.jsge.imgui.GuiComponent.FONT_SIZE;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import java.awt.Color;

/**
 * Componentes que contém texto na representação gráfica.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class GuiTextComponent extends GuiComponent {

    protected String text;
    protected int textWidth = -1;

    public GuiTextComponent( Rectangle rectangle, String text, EngineFrame engine ) {
        super( rectangle, engine );
        this.text = text;
    }
    
    public GuiTextComponent( Rectangle rectangle, String text ) {
        super( rectangle );
        this.text = text;
    }
    
    public GuiTextComponent( double x, double y, double width, double height, String text, EngineFrame engine ) {
        this( new Rectangle( x, y, width, height ), text, engine );
    }
    
    public GuiTextComponent( double x, double y, double width, double height, String text ) {
        this( new Rectangle( x, y, width, height ), text );
    }
    
    protected void drawText( Color textColor ) {
        engine.drawText(
                text,
                bounds.x,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    protected void drawText( Color textColor, double xOffset, double yOffset ) {
        engine.drawText(
                text,
                bounds.x + xOffset,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4 + yOffset,
                FONT_SIZE,
                textColor );
    }
    
    protected void drawTextAfterBounds( Color textColor ) {
        engine.drawText(
                text,
                bounds.x + bounds.width,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    protected void drawTextAfterBounds( Color textColor, double xOffset ) {
        engine.drawText(
                text,
                bounds.x + bounds.width + xOffset,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
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

    public void setText( String text ) {
        this.text = text;
        textWidth = engine.measureText( text, FONT_SIZE );
    }

}
