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

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * A panel-type container.
 *
 * Note: no container holds child components.
 * They are purely a graphical device for visually grouping components.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiPanel extends GuiTextComponent {
    
    private Rectangle titleBarBounds;
    private boolean titleBarPressed;
    
    private Color titleBarBackgroundColor;
    private Color titleBarBorderColor;
    private Color titleBarTextColor;
    
    /**
     * Creates the component.
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param title The title of the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiPanel( double x, double y, double width, double height, String title, EngineFrame engine ) {
        super( x, y, width, height, title, engine );
        initComponents();
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
     * @param title The title of the component.
     */
    public GuiPanel( double x, double y, double width, double height, String title ) {
        super( x, y, width, height, title );
        initComponents();
    }
    
    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param title The title of the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiPanel( Rectangle bounds, String title, EngineFrame engine ) {
        super( bounds, title, engine );
        initComponents();
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param title The title of the component.
     */
    public GuiPanel( Rectangle bounds, String title ) {
        super( bounds, title );
        initComponents();
    }
    
    private void initComponents() {
        this.titleBarBounds = new Rectangle( bounds.x, bounds.y, bounds.width, 25 );
        this.backgroundColor = CONTAINER_BACKGROUND_COLOR;
        this.borderColor = CONTAINER_BORDER_COLOR;
        this.titleBarBackgroundColor = CONTAINER_TITLE_BAR_BACKGROUND_COLOR;
        this.titleBarBorderColor = CONTAINER_TITLE_BAR_BORDER_COLOR;
        this.titleBarTextColor = CONTAINER_TITLE_BAR_TEXT_COLOR;
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( text != null && visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, titleBarBounds ) ) {
                titleBarPressed = engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT );
            }
            
        } else {
            titleBarPressed = false;
        }
        
    }
    
    @Override
    public void draw() {
        if ( visible ) {
            engine.setStrokeLineWidth( LINE_WIDTH );
            if ( enabled ) {
                drawPanel( borderColor, backgroundColor, titleBarBorderColor, titleBarBackgroundColor, titleBarTextColor );
            } else {
                drawPanel( DISABLED_CONTAINER_BORDER_COLOR, DISABLED_CONTAINER_BACKGROUND_COLOR, DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR, DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR, DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR );
            }
            drawBounds();
        }
    }
    
    private void drawPanel( Color borderColor, Color backgroundColor, Color titleBarBorderColor, Color titleBarBackgroundColor, Color titleBarTextColor ) {
        
        if ( textWidth == -1 && text != null ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        
        if ( text != null ) {
            engine.fillRectangle( bounds, backgroundColor );
            engine.drawRectangle( bounds, borderColor );
            engine.fillRectangle( titleBarBounds, titleBarBackgroundColor );
            engine.drawRectangle( titleBarBounds, titleBarBorderColor );
            engine.drawText( text, bounds.x + 10, bounds.y + 10, FONT_SIZE, titleBarTextColor );
        } else {
            engine.fillRectangle( bounds, backgroundColor );
            engine.drawRectangle( bounds, borderColor );
        }
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        titleBarBounds.x += xAmount;
        titleBarBounds.y += yAmount;
    }
    
    /**
     * Returns whether the title bar was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isTitleBarPressed() {
        return titleBarPressed;
    }

    /**
     * Gets the background color of the title bar.
     *
     * @return The background color of the title bar.
     */
    public Color getTitleBarBackgroundColor() {
        return titleBarBackgroundColor;
    }

    /**
     * Sets the background color of the title bar.
     *
     * @param titleBarBackgroundColor The background color of the title bar.
     */
    public void setTitleBarBackgroundColor( Color titleBarBackgroundColor ) {
        this.titleBarBackgroundColor = titleBarBackgroundColor;
    }

    /**
     * Gets the border color of the title bar.
     *
     * @return The border color of the title bar.
     */
    public Color getTitleBarBorderColor() {
        return titleBarBorderColor;
    }

    /**
     * Sets the border color of the title bar.
     *
     * @param titleBarBorderColor The border color of the title bar.
     */
    public void setTitleBarBorderColor( Color titleBarBorderColor ) {
        this.titleBarBorderColor = titleBarBorderColor;
    }

    /**
     * Gets the text color of the title bar.
     *
     * @return The text color of the title bar.
     */
    public Color getTitleBarTextColor() {
        return titleBarTextColor;
    }

    /**
     * Sets the text color of the title bar.
     *
     * @param titleBarTextColor The text color of the title bar.
     */
    public void setTitleBarTextColor( Color titleBarTextColor ) {
        this.titleBarTextColor = titleBarTextColor;
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        setBackgroundColor( theme.containerBackgroundColor );
        setBorderColor( theme.containerBorderColor );
        setTitleBarBackgroundColor( theme.containerTitleBarBackgroundColor );
        setTitleBarBorderColor( theme.containerTitleBarBorderColor );
        setTitleBarTextColor( theme.containerTitleBarTextColor );
    }
    
}
