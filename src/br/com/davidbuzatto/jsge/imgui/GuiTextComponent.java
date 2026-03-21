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
import br.com.davidbuzatto.jsge.font.FontUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Components that contain text in their graphical representation.
 *
 * @author Prof. Dr. David Buzatto
 */
public abstract class GuiTextComponent extends GuiComponent {

    /** Left horizontal alignment. */
    public static final int LEFT_ALIGNMENT = 1;
    /** Center horizontal alignment. */
    public static final int CENTER_ALIGNMENT = 2;
    /** Right horizontal alignment. */
    public static final int RIGHT_ALIGNMENT = 3;
    /** Top vertical alignment. */
    public static final int TOP_ALIGNMENT = 4;
    /** Middle vertical alignment. */
    public static final int MIDDLE_ALIGNMENT = 5;
    /** Bottom vertical alignment. */
    public static final int BOTTOM_ALIGNMENT = 6;
    
    private static BufferedImage dummyImage = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
    
    protected String text;
    protected int textWidth = -1;
    protected int textLineHeight = -1;

    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param text Text used in the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiTextComponent( Rectangle bounds, String text, EngineFrame engine ) {
        super( bounds, engine );
        this.text = text;
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
    public GuiTextComponent( Rectangle bounds, String text ) {
        super( bounds );
        this.text = text;
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
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiTextComponent( double x, double y, double width, double height, String text, EngineFrame engine ) {
        this( new Rectangle( x, y, width, height ), text, engine );
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
    public GuiTextComponent( double x, double y, double width, double height, String text ) {
        this( new Rectangle( x, y, width, height ), text );
    }
    
    /**
     * Draws text centered vertically.
     *
     * @param textColor The text color.
     */
    protected void drawText( Color textColor ) {
        engine.drawText(
                text,
                bounds.x,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Draws text centered vertically with an offset.
     *
     * @param textColor The text color.
     * @param xOffset Offset on the x axis.
     * @param yOffset Offset on the y axis.
     */
    protected void drawText( Color textColor, double xOffset, double yOffset ) {
        engine.drawText(
                text,
                bounds.x + xOffset,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4 + yOffset,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Draws text centered vertically after the bounding rectangle.
     *
     * @param textColor The text color.
     */
    protected void drawTextAfterBounds( Color textColor ) {
        engine.drawText(
                text,
                bounds.x + bounds.width,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Draws text centered vertically after the bounding rectangle,
     * with an offset.
     *
     * @param textColor The text color.
     * @param xOffset Offset on the x axis.
     */
    protected void drawTextAfterBounds( Color textColor, double xOffset ) {
        engine.drawText(
                text,
                bounds.x + bounds.width + xOffset,
                bounds.y + bounds.height / 2 - FONT_SIZE / 4,
                FONT_SIZE,
                textColor );
    }
    
    /**
     * Draws text centered both vertically and horizontally.
     *
     * @param textColor The text color.
     */
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

    /**
     * Gets the text of the component.
     *
     * @return The text of the component.
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * Sets the text of the component.
     *
     * @param text Text used in the component.
     */
    public void setText( String text ) {
        this.text = text;
        updateTextProperties();
    }
    
    protected void updateTextProperties() {
        
        Graphics g = dummyImage.createGraphics();
        g.setFont( FontUtils.DEFAULT_FONT.deriveFont( (float) FONT_SIZE ) );
        FontMetrics fm = g.getFontMetrics();
        
        textWidth = fm.stringWidth( text );
        textLineHeight = (int) fm.getStringBounds( text, g ).getHeight();
        g.dispose();
        
    }
    
    protected Vector2 calculateStartPosition( int horizontalAlignment, int verticalAlignment ) {
        
        Vector2 pos = new Vector2();
        
        updateTextProperties();
        
        switch ( horizontalAlignment ) {
            case LEFT_ALIGNMENT -> pos.x = 0;
            case CENTER_ALIGNMENT -> pos.x = bounds.width / 2 - textWidth / 2;
            case RIGHT_ALIGNMENT -> pos.x = bounds.width - textWidth;
        }
        
        switch ( verticalAlignment ) {
            case TOP_ALIGNMENT -> pos.y = 2;
            case MIDDLE_ALIGNMENT -> pos.y = bounds.height / 2 - textLineHeight / 4;
            case BOTTOM_ALIGNMENT -> pos.y = bounds.height - textLineHeight / 2;
        }
        
        return pos;
        
    }

}
