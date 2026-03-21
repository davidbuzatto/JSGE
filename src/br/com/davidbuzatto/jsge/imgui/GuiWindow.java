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
 * A window-type container.
 *
 * Note: no container holds child components.
 * They are purely a graphical device for visually grouping components.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiWindow extends GuiTextComponent {
    
    protected GuiButtonClose closeButton;
    protected Rectangle titleBarBounds;
    protected boolean titleBarPressed;
    
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
    public GuiWindow( double x, double y, double width, double height, String title, EngineFrame engine ) {
        super( x, y, width, height, title, engine );
        initComponents( engine );
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
    public GuiWindow( double x, double y, double width, double height, String title ) {
        super( x, y, width, height, title );
        initComponents( null );
    }
    
    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param title The title of the component.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiWindow( Rectangle bounds, String title, EngineFrame engine ) {
        super( bounds, title, engine );
        initComponents( engine );
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
    public GuiWindow( Rectangle bounds, String title ) {
        super( bounds, title );
        initComponents( null );
    }
    
    protected void initComponents( EngineFrame engine ) {
        
        titleBarBounds = new Rectangle( bounds.x, bounds.y, bounds.width, 25 );
        
        if ( engine == null ) {
            closeButton = new GuiButtonClose( bounds.x + bounds.width - 22, bounds.y + 3, 19, 19 );
        } else {
            closeButton = new GuiButtonClose( bounds.x + bounds.width - 22, bounds.y + 3, 19, 19, engine );
        }
        
        this.backgroundColor = CONTAINER_BACKGROUND_COLOR;
        this.borderColor = CONTAINER_BORDER_COLOR;
        this.titleBarBackgroundColor = CONTAINER_TITLE_BAR_BACKGROUND_COLOR;
        this.titleBarBorderColor = CONTAINER_TITLE_BAR_BORDER_COLOR;
        this.titleBarTextColor = CONTAINER_TITLE_BAR_TEXT_COLOR;
        
        closeButton.setBackgroundColor( titleBarBackgroundColor );
        closeButton.setBorderColor( titleBarBorderColor );
        closeButton.setTextColor( titleBarTextColor );
        
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            closeButton.update( delta );
            
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
                drawWindow( 
                        borderColor, 
                        backgroundColor, 
                        titleBarBorderColor, 
                        titleBarBackgroundColor, 
                        titleBarTextColor
                );
            } else {
                drawWindow( 
                        DISABLED_CONTAINER_BORDER_COLOR, 
                        DISABLED_CONTAINER_BACKGROUND_COLOR, 
                        DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR, 
                        DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR, 
                        DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR
                );
            }
            drawBounds();
            closeButton.draw();
        }
    }
    
    private void drawWindow( Color borderColor, Color backgroundColor, Color titleBarBorderColor, Color titleBarBackgroundColor, Color titleBarTextColor ) {
        
        if ( textWidth == -1 && text != null ) {
            textWidth = engine.measureText( text, FONT_SIZE );
        }
        
        engine.fillRectangle( bounds, backgroundColor );
        engine.drawRectangle( bounds, borderColor );
        engine.fillRectangle( titleBarBounds, titleBarBackgroundColor );
        engine.drawRectangle( titleBarBounds, titleBarBorderColor );
            
        if ( text != null ) {
            engine.drawText( text, bounds.x + 10, bounds.y + 10, FONT_SIZE, titleBarTextColor );
        }
        
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        closeButton.setEnabled( enabled );
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        closeButton.setVisible( visible );
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        titleBarBounds.x += xAmount;
        titleBarBounds.y += yAmount;
        closeButton.bounds.x += xAmount;
        closeButton.bounds.y += yAmount;
    }
    
    /**
     * Returns whether the close button on the title bar was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isCloseButtonPressed() {
        return closeButton.isMousePressed();
    }
    
    /**
     * Returns whether the title bar was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isTitleBarPressed() {
        if ( closeButton.isMousePressed() ) {
            return false;
        }
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
        this.closeButton.setBackgroundColor( titleBarBackgroundColor );
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
        this.closeButton.setBorderColor( titleBarBorderColor );
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
        this.closeButton.setTextColor( titleBarTextColor );
    }
    
    protected class GuiButtonClose extends GuiButton {
        
        public GuiButtonClose( double x, double y, double width, double height, EngineFrame engine ) {
            super( x, y, width, height, "", engine );
        }
        
        public GuiButtonClose( double x, double y, double width, double height ) {
            super( x, y, width, height, "" );
        }

        @Override
        public void draw() {
            
            super.draw();
            
            if ( visible ) {

                engine.setStrokeLineWidth( LINE_WIDTH );

                if ( enabled ) {

                    switch ( mouseState ) {
                        case MOUSE_OUT:
                            drawButtonClose( titleBarTextColor );
                            break;
                        case MOUSE_OVER:
                            drawButtonClose( MOUSE_OVER_TEXT_COLOR );
                            break;
                        case MOUSE_PRESSED:
                            drawButtonClose( MOUSE_DOWN_TEXT_COLOR );
                            break;
                        case MOUSE_DOWN:
                            drawButtonClose( MOUSE_DOWN_TEXT_COLOR );
                            break;
                    }

                } else {
                    drawButtonClose( DISABLED_TEXT_COLOR );
                }

            }
            
        }
        
        private void drawButtonClose( Color color ) {
            int pad = 6;
            engine.drawLine( bounds.x + pad, bounds.y + pad, bounds.x + bounds.width - pad, bounds.y + bounds.height - pad, color );
            engine.drawLine( bounds.x + pad, bounds.y + bounds.height - pad, bounds.x + bounds.width - pad, bounds.y + pad, color );
        }
        
    }
    
    protected void updateButtonsBounds( GuiButton[] buttons ) {
        
        int minWidth = 50;
        int buttonPadding = 10;
        
        double w = 0;
        int b = 0;
        
        for ( int i = 0; i < buttons.length; i++ ) {
            if ( !buttons[i].text.isEmpty() ) {
                buttons[i].bounds.width = engine.measureText( buttons[i].text, FONT_SIZE ) + buttonPadding * 2;
                if ( buttons[i].bounds.width < minWidth ) {
                    buttons[i].bounds.width = minWidth;
                }
                w += buttons[i].bounds.width;
                b++;
            }
        }
        
        w += ( DIALOG_CONTENT_PADDING / 2 ) * ( b - 1 );
        double start = bounds.x + bounds.width / 2 - w / 2;
        
        for ( int i = 0; i < buttons.length; i++ ) {
            if ( i != 0 ) {
                start += buttons[i-1].bounds.width + DIALOG_CONTENT_PADDING / 2;
            }
            buttons[i].bounds.x = start;
            buttons[i].bounds.y = bounds.y + bounds.height - DIALOG_CONTENT_PADDING - buttons[i].bounds.height;
        }
        
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        closeButton.apply( theme );
        setBackgroundColor( theme.containerBackgroundColor );
        setBorderColor( theme.containerBorderColor );
        setTitleBarBackgroundColor( theme.containerTitleBarBackgroundColor );
        setTitleBarBorderColor( theme.containerTitleBarBorderColor );
        setTitleBarTextColor( theme.containerTitleBarTextColor );
    }
    
}
