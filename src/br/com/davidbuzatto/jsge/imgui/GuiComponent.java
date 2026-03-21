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
 * Generic representation of a component.
 *
 * @author Prof. Dr. David Buzatto
 */
public abstract class GuiComponent {
    
    private static int idCounter;
    private int id;
    
    protected Rectangle bounds;
    protected EngineFrame engine;
    
    protected GuiComponentMouseState mouseState;
    protected boolean enabled;
    protected boolean visible;
    protected boolean drawingBounds;
    
    protected Color backgroundColor;
    protected Color borderColor;
    protected Color textColor;
    
    /** Default font size for components. */
    public static final int FONT_SIZE = 12;
    /** Default line width used in components. */
    public static final int LINE_WIDTH = 1;

    /** Padding for dialog content. */
    public static final int DIALOG_CONTENT_PADDING = 20;
    /** Minimum width of dialogs. */
    public static final double DIALOG_MIN_WIDTH = 250;
    /** Minimum height of dialogs. */
    public static final double DIALOG_MIN_HEIGHT = 100;
    /** Height of dialog buttons. */
    public static final double DIALOG_BUTTON_HEIGHT = 30;

    /** Radius of the slider component. */
    public static final double SLIDER_RADIUS = 10;

    /** Size of the scroll bar button. */
    public static final double SCROLL_BAR_BUTTON_SIZE = 20;

    //**************************************************************************
    // Colors.
    // Names are kept in uppercase to avoid mixing with instance attributes.
    //**************************************************************************

    /** Default background color. */
    public static Color BACKGROUND_COLOR;
    /** Default border color. */
    public static Color BORDER_COLOR;
    /** Default text color. */
    public static Color TEXT_COLOR;

    /** Background color when the mouse is over the component. */
    public static Color MOUSE_OVER_BACKGROUND_COLOR;
    /** Border color when the mouse is over the component. */
    public static Color MOUSE_OVER_BORDER_COLOR;
    /** Text color when the mouse is over the component. */
    public static Color MOUSE_OVER_TEXT_COLOR;

    /** Background color when the mouse button is pressed on the component. */
    public static Color MOUSE_DOWN_BACKGROUND_COLOR;
    /** Border color when the mouse button is pressed on the component. */
    public static Color MOUSE_DOWN_BORDER_COLOR;
    /** Text color when the mouse button is pressed on the component. */
    public static Color MOUSE_DOWN_TEXT_COLOR;

    /** Background color when the component is disabled. */
    public static Color DISABLED_BACKGROUND_COLOR;
    /** Border color when the component is disabled. */
    public static Color DISABLED_BORDER_COLOR;
    /** Text color when the component is disabled. */
    public static Color DISABLED_TEXT_COLOR;

    /** Background color for container components. */
    public static Color CONTAINER_BACKGROUND_COLOR;
    /** Border color for container components. */
    public static Color CONTAINER_BORDER_COLOR;
    /** Text color for container components. */
    public static Color CONTAINER_TEXT_COLOR;
    /** Background color for the title bars of container components. */
    public static Color CONTAINER_TITLE_BAR_BACKGROUND_COLOR;
    /** Border color for the title bars of container components. */
    public static Color CONTAINER_TITLE_BAR_BORDER_COLOR;
    /** Text color for the title bars of container components. */
    public static Color CONTAINER_TITLE_BAR_TEXT_COLOR;

    /** Background color for disabled container components. */
    public static Color DISABLED_CONTAINER_BACKGROUND_COLOR;
    /** Border color for disabled container components. */
    public static Color DISABLED_CONTAINER_BORDER_COLOR;
    /** Text color for disabled container components. */
    public static Color DISABLED_CONTAINER_TEXT_COLOR;
    /** Background color for the title bars of disabled container components. */
    public static Color DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR;
    /** Border color for the title bars of disabled container components. */
    public static Color DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR;
    /** Text color for the title bars of disabled container components. */
    public static Color DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR;

    /** Background color for the progress fill of progress bars. */
    public static Color PROGRESS_BAR_PROGRESS_FILL_COLOR;

    /** Background color for item containers of list components. */
    public static Color LIST_CONTAINER_BACKGROUND_COLOR;
    /** Background color for item containers of disabled list components. */
    public static Color DISABLED_LIST_CONTAINER_BACKGROUND_COLOR;

    /** Background color for the track of scroll bars. */
    public static Color SCROLL_BAR_TRACK_COLOR;
    /** Background color for the track of disabled scroll bars. */
    public static Color DISABLED_SCROLL_BAR_TRACK_COLOR;

    /** Overlay color of the color picker when it is disabled. */
    public static Color COLOR_PICKER_DISABLED_OVERLAY_COLOR;

    /** Overlay color for dialogs. */
    public static Color DIALOG_OVERLAY_COLOR;

    /** Default tooltip background color. */
    public static Color TOOL_TIP_BACKGROUND_COLOR;
    /** Default tooltip border color. */
    public static Color TOOL_TIP_BORDER_COLOR;
    /** Default tooltip text color. */
    public static Color TOOL_TIP_TEXT_COLOR;
    
    static {
        GuiTheme.buildLightTheme().install();
    }
    
    /**
     * Builds the basic infrastructure of components.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiComponent( Rectangle bounds, EngineFrame engine ) {
        this.id = idCounter++;
        this.engine = engine;
        this.bounds = bounds;
        this.mouseState = GuiComponentMouseState.MOUSE_OUT;
        this.enabled = true;
        this.visible = true;
        this.backgroundColor = BACKGROUND_COLOR;
        this.borderColor = BORDER_COLOR;
        this.textColor = TEXT_COLOR;
    }
    
    /**
     * Builds the basic infrastructure of components.
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiComponent( double x, double y, double width, double height, EngineFrame engine ) {
        this( new Rectangle( x, y, width, height ), engine );
    }
    
    /**
     * Builds the basic infrastructure of components.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param bounds A rectangle that defines the bounds of the component.
     */
    public GuiComponent( Rectangle bounds ) {
        this( bounds, EngineFrame.getDependencyEngine() );
    }
    
    /**
     * Builds the basic infrastructure of components.
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
     */
    public GuiComponent( double x, double y, double width, double height ) {
        this( new Rectangle( x, y, width, height ) );
    }
    
    /**
     * Updates the state of the component. Must be called in the update method
     * of the engine instance.
     *
     * @param delta The time variation, in seconds, from one frame to the next.
     */
    public void update( double delta ) {
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();

            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                mouseState = GuiComponentMouseState.MOUSE_OVER;
                if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentMouseState.MOUSE_PRESSED;
                } else if ( engine.isMouseButtonDown( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                    mouseState = GuiComponentMouseState.MOUSE_DOWN;
                }
            } else {
                mouseState = GuiComponentMouseState.MOUSE_OUT;
            }
            
        } else {
            mouseState = GuiComponentMouseState.MOUSE_OUT;
        }
        
    }
    
    /**
     * Draws the component using the configured engine.
     */
    public abstract void draw();
    
    /**
     * Moves the component.
     *
     * @param xAmount Amount of movement on the x axis.
     * @param yAmount Amount of movement on the y axis.
     */
    public abstract void move( double xAmount, double yAmount );
    
    /**
     * Applies a theme to the component.
     *
     * @param theme The theme.
     */
    public void apply( GuiTheme theme ) {
        setBackgroundColor( theme.backgroundColor );
        setBorderColor( theme.borderColor );
        setTextColor( theme.textColor );
    }
    
    /**
     * Returns whether the left mouse button was pressed on the component
     * in the current cycle.
     *
     * @return True if so, false otherwise.
     */
    public boolean isMousePressed() {
        return mouseState == GuiComponentMouseState.MOUSE_PRESSED;
    }
    
    /**
     * Returns whether the left mouse button is held down on the component
     * in the current cycle.
     *
     * @return True if so, false otherwise.
     */
    public boolean isMouseDown() {
        return mouseState == GuiComponentMouseState.MOUSE_DOWN;
    }
    
    /**
     * Returns whether the mouse pointer is outside the component in the current cycle.
     *
     * @return True if so, false otherwise.
     */
    public boolean isMouseOut() {
        return mouseState == GuiComponentMouseState.MOUSE_OUT;
    }
    
    /**
     * Returns whether the mouse pointer is over the component in the current cycle.
     *
     * @return True if so, false otherwise.
     */
    public boolean isMouseOver() {
        return mouseState == GuiComponentMouseState.MOUSE_OVER;
    }
    
    /**
     * Gets the upper-left x coordinate of the rectangle that bounds the component.
     *
     * @return The x coordinate.
     */
    public double getX() {
        return bounds.x;
    }
    
    /**
     * Gets the upper-left y coordinate of the rectangle that bounds the component.
     *
     * @return The y coordinate.
     */
    public double getY() {
        return bounds.y;
    }
    
    /**
     * Gets the width of the rectangle that bounds the component.
     *
     * @return The width of the component.
     */
    public double getWidth() {
        return bounds.width;
    }
    
    /**
     * Gets the height of the rectangle that bounds the component.
     *
     * @return The height of the component.
     */
    public double getHeight() {
        return bounds.height;
    }
    
    /**
     * Draws the rectangle that bounds the component.
     */
    protected void drawBounds() {
        if ( drawingBounds ) {
            engine.setStrokeLineWidth( LINE_WIDTH );
            engine.drawRectangle( bounds, EngineFrame.BLUE );
        }
    }
    
    /**
     * Returns whether the component is enabled.
     *
     * @return True if so, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the component is enabled or not.
     *
     * @param enabled True to enable, false to disable.
     */
    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    /**
     * Returns whether the component is visible.
     *
     * @return True if so, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets whether the component is visible or not.
     *
     * @param visible True to show, false to hide.
     */
    public void setVisible( boolean visible ) {
        this.visible = visible;
    }

    /**
     * Returns whether the bounds of the component are being drawn.
     *
     * @return True if so, false otherwise.
     */
    public boolean isDrawingBounds() {
        return drawingBounds;
    }

    /**
     * Sets whether the bounds of the component should be drawn.
     *
     * @param drawingBounds True to draw, false to not draw.
     */
    public void setDrawingBounds( boolean drawingBounds ) {
        this.drawingBounds = drawingBounds;
    }

    /**
     * Gets the rectangle that defines the bounds of the component.
     * Returns a copy of the current rectangle to prevent the component user
     * from modifying the rectangle in use.
     *
     * @return The rectangle that defines the bounds of the component.
     */
    public Rectangle getBounds() {
        return new Rectangle( bounds.x, bounds.y, bounds.width, bounds.height );
    }

    /**
     * Gets the background color of the component.
     *
     * @return The background color of the component.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background color of the component.
     *
     * @param backgroundColor The background color of the component.
     */
    public void setBackgroundColor( Color backgroundColor ) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Gets the border color of the component.
     *
     * @return The border color of the component.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the border color of the component.
     *
     * @param borderColor The border color of the component.
     */
    public void setBorderColor( Color borderColor ) {
        this.borderColor = borderColor;
    }

    /**
     * Gets the text color of the component.
     *
     * @return The text color of the component.
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * Sets the text color of the component.
     *
     * @param textColor The text color of the component.
     */
    public void setTextColor( Color textColor ) {
        this.textColor = textColor;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final GuiComponent other = (GuiComponent) obj;
        return this.id == other.id;
    }
    
}
