/*
 * Copyright (C) 2024 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.core.engine.imgui;

import static br.com.davidbuzatto.jsge.core.engine.imgui.GuiComponent.FONT_SIZE;
import java.awt.Color;

/**
 * Componentes de texto.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class GuiTextComponent extends GuiComponent {

    protected String text;
    protected int textWidth = -1;

    protected void drawText( Color textColor ) {
        e.drawText(
                text,
                bounds.x,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    protected void drawTextAfterBounds( Color textColor ) {
        e.drawText(
                text,
                bounds.x + bounds.width + 5,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    protected void drawCenteredText( Color textColor ) {
        if ( textWidth == -1 ) {
            textWidth = e.measureText( text, FONT_SIZE );
        }
        e.drawText(
                text,
                bounds.x + bounds.width / 2 - textWidth / 2,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }

    public void setText( String text ) {
        this.text = text;
        textWidth = e.measureText( text, FONT_SIZE );
    }

}
