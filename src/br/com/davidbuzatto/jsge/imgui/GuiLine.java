/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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
 * A line separator component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiLine extends GuiTextComponent {
    
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private int orientation;
    private int textHeight;
    private int charWidth;
    
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
    public GuiLine( double x, double y, double width, double height, String text, EngineFrame engine ) {
        this( x, y, width, height, text, HORIZONTAL, engine );
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
    public GuiLine( double x, double y, double width, double height, String text ) {
        this( x, y, width, height, text, HORIZONTAL );
    }

    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiLine( Rectangle bounds, String text, EngineFrame engine ) {
        this( bounds, text, HORIZONTAL, engine );
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
    public GuiLine( Rectangle bounds, String text ) {
        this( bounds, text, HORIZONTAL );
    }

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
     * @param orientation Whether the line is horizontal or vertical.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiLine( double x, double y, double width, double height, String text, int orientation, EngineFrame engine ) {
        super( x, y, width, height, text, engine );
        initData( orientation );
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
     * @param orientation Whether the line is horizontal or vertical.
     */
    public GuiLine( double x, double y, double width, double height, String text, int orientation ) {
        super( x, y, width, height, text );
        initData( orientation );
    }

    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     * @param orientation Whether the line is horizontal or vertical.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiLine( Rectangle bounds, String text, int orientation, EngineFrame engine ) {
        super( bounds, text, engine );
        initData( orientation );
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
     * @param orientation Whether the line is horizontal or vertical.
     */
    public GuiLine( Rectangle bounds, String text, int orientation ) {
        super( bounds, text );
        initData( orientation );
    }
    
    private void initData( int orientation ) {
        this.orientation = orientation;
        this.borderColor = CONTAINER_BORDER_COLOR;
        this.textColor = CONTAINER_TEXT_COLOR;
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            engine.setStrokeLineWidth( LINE_WIDTH );
            if ( enabled ) {
                drawLine( borderColor, textColor );
            } else {
                drawLine( DISABLED_CONTAINER_BORDER_COLOR, DISABLED_CONTAINER_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    private void drawLine( Color borderColor, Color textColor ) {
        
        if ( textWidth == -1 && text != null && !text.isEmpty() ) {
            textWidth = engine.measureText( text, FONT_SIZE );
            textHeight = (int) engine.measureTextBounds( text, FONT_SIZE ).height;
            charWidth = engine.measureText( String.valueOf( text.charAt( 0 ) ), FONT_SIZE );
        }
        
        if ( orientation == VERTICAL ) {
            if ( textWidth > 0 ) {
                engine.drawLine( bounds.x + bounds.width / 2, bounds.y, bounds.x + bounds.width / 2, bounds.y + 6, borderColor );
                double start = 0;
                for ( int i = 0; i < text.length(); i++ ) {
                    char c = text.charAt( i );
                    start = bounds.y + ( ( textHeight - 2 ) * ( i + 1 ) ) + 2;
                    engine.drawText( String.valueOf( c ), bounds.x + bounds.width / 2 - charWidth / 2, start, FONT_SIZE, textColor );
                }
                engine.drawLine( bounds.x + bounds.width / 2, start + textHeight - 2, bounds.x + bounds.width / 2, bounds.y + bounds.height, borderColor );
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
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        setBorderColor( theme.containerBorderColor );
        setTextColor( theme.containerTextColor );
    }
    
}
