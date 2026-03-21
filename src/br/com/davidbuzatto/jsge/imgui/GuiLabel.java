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
import br.com.davidbuzatto.jsge.math.Vector2;

/**
 * A label component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiLabel extends GuiTextComponent {
    
    private int horizontalAlignment;
    private int verticalAlignment;
    
    // position relative to the bounding rectangle
    private Vector2 startPosition;
    
    /**
     * Creates the component.
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiLabel( double x, double y, double width, double height, String text, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
        initData();
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     */
    public GuiLabel( double x, double y, double width, double height, String text ) {
        super( x, y, width, height, text );
        initData();
    }
    
    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiLabel( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, text, engine );
        initData();
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     */
    public GuiLabel( Rectangle bounds, String text ) {
        super( bounds, text );
        initData();
    }
    
    private void initData() {
        horizontalAlignment = LEFT_ALIGNMENT;
        verticalAlignment = MIDDLE_ALIGNMENT;
        startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            if ( enabled ) {
                engine.drawText( text, bounds.x + startPosition.x, bounds.y + startPosition.y, FONT_SIZE, textColor );
                //drawText( TEXT_COLOR );
            } else {
                engine.drawText( text, bounds.x + startPosition.x, bounds.y + startPosition.y, FONT_SIZE, DISABLED_TEXT_COLOR );
                //drawText( DISABLED_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment( int horizontalAlignment ) {
        if ( horizontalAlignment >= LEFT_ALIGNMENT && horizontalAlignment <= RIGHT_ALIGNMENT ) {
            this.horizontalAlignment = horizontalAlignment;
            startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
        }
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment( int verticalAlignment ) {
        if ( verticalAlignment >= TOP_ALIGNMENT && verticalAlignment <= BOTTOM_ALIGNMENT ) {
            this.verticalAlignment = verticalAlignment;
            startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
        }
    }
    
    @Override
    public void setText( String text ) {
        this.text = text;
        startPosition = calculateStartPosition( horizontalAlignment, verticalAlignment );
    }
    
}
