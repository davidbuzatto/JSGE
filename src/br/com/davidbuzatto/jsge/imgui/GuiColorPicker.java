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
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * A color picker component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiColorPicker extends GuiComponent {
    
    private static final int SPACING = 8;
    private static final int BAR_SIZE = 15;
    private static final int RET_SIZE = 5;
    
    private Vector2 saturationAndValuePosition;
    
    protected GuiSlider hueSlider;
    protected GuiSlider alphaSlider;
    
    private boolean allowingAlphaChange;
    
    /**
     * Creates the component.
     *
     * @param x The x coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param y The y coordinate of the upper-left vertex of the rectangle that
     * defines the bounds of the component.
     * @param width Width of the rectangle that defines the bounds of the component.
     * @param height Height of the rectangle that defines the bounds of the component.
     * @param initialColor The initial color.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiColorPicker( double x, double y, double width, double height, Color initialColor, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initComponents( engine, initialColor );
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
     * @param initialColor The initial color.
     */
    public GuiColorPicker( double x, double y, double width, double height, Color initialColor ) {
        super( x, y, width, height );
        initComponents( null, initialColor );
    }
    
    /**
     * Creates the component.
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param initialColor The initial color.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiColorPicker( Rectangle bounds, Color initialColor, EngineFrame engine ) {
        super( bounds, engine );
        initComponents( engine, initialColor );
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param bounds A rectangle that defines the bounds of the component.
     * @param initialColor The initial color.
     */
    public GuiColorPicker( Rectangle bounds, Color initialColor ) {
        super( bounds );
        initComponents( null, initialColor );
    }
    
    private void initComponents( EngineFrame engine, Color initialColor ) {
        
        double[] hsv = ColorUtils.colorToHSV( initialColor );
        
        if ( engine == null ) {
            hueSlider = new GuiSlider( bounds.x + bounds.width + SPACING, bounds.y - SLIDER_RADIUS, BAR_SIZE, bounds.height + SLIDER_RADIUS * 2, hsv[0], 359, 0, GuiSlider.VERTICAL );
            alphaSlider = new GuiSlider( bounds.x - SLIDER_RADIUS, bounds.y + bounds.height + SPACING, bounds.width + SLIDER_RADIUS * 2, BAR_SIZE, initialColor.getAlpha(), 0, 255 );
        } else {
            hueSlider = new GuiSlider( bounds.x + bounds.width + SPACING, bounds.y - SLIDER_RADIUS, BAR_SIZE, bounds.height + SLIDER_RADIUS * 2, hsv[0], 359, 0, GuiSlider.VERTICAL, engine );
            alphaSlider = new GuiSlider( bounds.x - SLIDER_RADIUS, bounds.y + bounds.height + SPACING, bounds.width + SLIDER_RADIUS * 2, BAR_SIZE, initialColor.getAlpha(), 0, 255, engine );
        }
        
        hueSlider.setShowTrack( false );
        alphaSlider.setShowTrack( false );
        hueSlider.setMouseWheelEnabled( false );
        alphaSlider.setMouseWheelEnabled( false );
        
        saturationAndValuePosition = new Vector2( 
                bounds.x + bounds.width * hsv[1],
                bounds.y + bounds.height * ( 1 - hsv[2] )
        );
        
        allowingAlphaChange = true;
        
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();
            
            double mouseWheelMove = engine.getMouseWheelMove();
            
            hueSlider.update( delta );
            
            if ( hueSlider.mouseState == GuiComponentMouseState.MOUSE_OVER ) {
                hueSlider.setValue( hueSlider.getValue() - mouseWheelMove * 5 );
            }
            
            if ( allowingAlphaChange ) {
                
                alphaSlider.update( delta );

                if ( alphaSlider.mouseState == GuiComponentMouseState.MOUSE_OVER ) {
                    alphaSlider.setValue( alphaSlider.getValue() + mouseWheelMove * 5 );
                }
                
            }
            
            if ( engine.isMouseButtonDown( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                    saturationAndValuePosition.x = mousePos.x;
                    saturationAndValuePosition.y = mousePos.y;
                }
            }
            
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                switch ( mouseState ) {
                    case MOUSE_OVER:
                        drawColorPicker( MOUSE_OVER_BORDER_COLOR );
                        break;
                    case MOUSE_DOWN:
                        drawColorPicker( MOUSE_DOWN_BORDER_COLOR );
                        break;
                    default:
                        drawColorPicker( borderColor );
                        break;
                }
            } else {
                drawColorPicker( DISABLED_BORDER_COLOR );
            }
            
            drawBounds();
            
            //engine.drawText( String.format( "hue: %.2f", hueSlider.getValue() ), bounds.x, bounds.y, 20, EngineFrame.BLACK );
            //engine.drawText( String.format( "alpha: %.2f", alphaSlider.getValue() ), bounds.x, bounds.y + 20, 20, EngineFrame.BLACK );
            
        }
        
    }
    
    private void drawColorPicker( Color borderColor ) {
        drawColorPanel( borderColor );
        drawHueAndAlphaBars( borderColor );
    }
    
    private void drawColorPanel( Color borderColor ) {
        
        int h = (int) bounds.height;
        int w = (int) bounds.width;
        
        for ( int i = 1; i < h; i++ ) {
            for ( int j = 1; j < w; j++ ) {
                double sat = j / (double) w;
                double val = 1.0 - ( i / (double) h );
                Color c = ColorUtils.colorFromHSV( hueSlider.getValue(), sat, val );
                engine.drawPixel( bounds.x + j, bounds.y + i, c );
            }
        }
        
        if ( !enabled ) {
            engine.fillRectangle( bounds, COLOR_PICKER_DISABLED_OVERLAY_COLOR );
        } else {
            engine.beginScissorMode( bounds );
            engine.fillCircle( saturationAndValuePosition.x, saturationAndValuePosition.y, 4, EngineFrame.WHITE );
            engine.endScissorMode();
        }
        
        engine.drawRectangle( bounds, borderColor );
        
    }
    
    private void drawHueAndAlphaBars( Color borderColor ) {
        
        int h = (int) bounds.height;
        int w = (int) bounds.width;
        
        // hue bar
        for ( int i = 1; i < h; i++ ) {
            engine.drawLine( 
                    bounds.x + bounds.width + SPACING, 
                    bounds.y + i, 
                    bounds.x + bounds.width + SPACING + BAR_SIZE, 
                    bounds.y + i, 
                    ColorUtils.colorFromHSV( 360.0 / h * i, 1, 1 )
            );
        }
        
        if ( !enabled ) {
            engine.fillRectangle( bounds.x + bounds.width + SPACING, bounds.y, BAR_SIZE, bounds.height, COLOR_PICKER_DISABLED_OVERLAY_COLOR );
        }
        
        engine.drawRectangle( bounds.x + bounds.width + SPACING, bounds.y, BAR_SIZE, bounds.height, borderColor );
        
        hueSlider.draw();
            
        // alpha bar
        if ( allowingAlphaChange ) {
            
            int lines = BAR_SIZE / RET_SIZE;
            int columns = w / RET_SIZE;
        
            for ( int i = 0; i < lines; i++ ) {
                for ( int j = 0; j < columns; j++ ) {
                    engine.fillRectangle( 
                            bounds.x + j * RET_SIZE,
                            bounds.y + bounds.height + SPACING + i * RET_SIZE,
                            RET_SIZE,
                            RET_SIZE,
                            i % 2 == 0 ?
                                    j % 2 == 0 ? EngineFrame.WHITE : EngineFrame.GRAY
                                    :
                                    j % 2 == 0 ? EngineFrame.GRAY : EngineFrame.WHITE
                    );
                }
            }
        
            for ( int i = 1; i < w; i++ ) {
                engine.drawLine( 
                        bounds.x + i, 
                        bounds.y + bounds.height + SPACING, 
                        bounds.x + i, 
                        bounds.y + bounds.height + SPACING + BAR_SIZE, 
                        ColorUtils.fade( Color.BLACK, i / (double) w )
                );
            }
            
            if ( !enabled ) {
                engine.fillRectangle( bounds.x, bounds.y + bounds.height + SPACING, bounds.width, BAR_SIZE, COLOR_PICKER_DISABLED_OVERLAY_COLOR );
            }
            
            engine.drawRectangle( bounds.x, bounds.y + bounds.height + SPACING, bounds.width, BAR_SIZE, borderColor );
            
            alphaSlider.draw();
            
        }
        
    }
    
    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        hueSlider.setEnabled( enabled );
        alphaSlider.setEnabled( enabled );
    }

    /**
     * Returns whether the component allows editing of the alpha channel.
     *
     * @return True if it allows, false otherwise.
     */
    public boolean isAllowingAlphaChange() {
        return allowingAlphaChange;
    }

    /**
     * Configures the component to allow or disallow modification of the
     * alpha channel of the color.
     *
     * @param allowingAlphaChange True to allow, false otherwise.
     */
    public void setAllowingAlphaChange( boolean allowingAlphaChange ) {
        this.allowingAlphaChange = allowingAlphaChange;
    }
    
    /**
     * Returns the currently selected color.
     *
     * @return The selected color.
     */
    public Color getColor() {
        return ColorUtils.fade( 
                ColorUtils.colorFromHSV( 
                        getHue(), 
                        getSaturation(),
                        getValue()
                ), 
                alphaSlider.getValue() / 255
        );
    }
    
    /**
     * Returns the hue of the currently selected color.
     *
     * @return The hue ranging from 0 to 360.
     */
    public double getHue() {
        return hueSlider.getValue();
    }
    
    /**
     * Returns the saturation of the currently selected color.
     *
     * @return The saturation from 0 to 1.
     */
    public double getSaturation() {
        return ( saturationAndValuePosition.x - bounds.x ) / bounds.width;
    }
    
    /**
     * Returns the value (brightness) of the currently selected color.
     *
     * @return The value from 0 to 1.
     */
    public double getValue() {
        return 1.0 - ( saturationAndValuePosition.y - bounds.y ) / bounds.height;
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        saturationAndValuePosition.x += xAmount;
        saturationAndValuePosition.y += yAmount;
        hueSlider.move( xAmount, yAmount );
        alphaSlider.move( xAmount, yAmount );
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        hueSlider.apply( theme );
        alphaSlider.apply( theme );
    }
    
}
