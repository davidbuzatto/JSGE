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

/**
 * Um componente de etiqueta.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiLabel extends GuiTextComponent {
    
    public GuiLabel( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
    }
    
    public GuiLabel( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
    }
    
    public GuiLabel( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
    }
    
    public GuiLabel( Rectangle bounds, String text ) {
        super( bounds, text );
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            if ( enabled ) {
                drawText( TEXT_COLOR );
            } else {
                drawText( DISABLED_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
}
