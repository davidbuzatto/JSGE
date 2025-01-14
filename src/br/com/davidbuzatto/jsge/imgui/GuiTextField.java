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

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.MathUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Um componente de campo de texto.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiTextField extends GuiComponent {
    
    private static final double CARET_BLINK_TIME = 0.3;
    private static final double KEY_DELAY_TIME = 0.5;
    
    private String value;
    private boolean hasFocus;
    private int caretPosition;
    
    private double caretBlinkCounter;
    private boolean showCaret;
    
    private int charWidth;
    private int maxVisibleChars;
    private double keyDelayCounter;
    private int lastKey;
    
    public GuiTextField( double x, double y, double width, double height, String value, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value );
    }
    
    public GuiTextField( double x, double y, double width, double height, String value ) {
        super( x, y, width, height );
        initData( value );
    }
    
    public GuiTextField( Rectangle bounds, String value, EngineFrame engine ) {
        super( bounds, engine );
        initData( value );
    }
    
    public GuiTextField( Rectangle bounds, String value ) {
        super( bounds );
        initData( value );
    }
    
    private void initData( String value ) {
        setValue( value );
        this.showCaret = true;
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            Vector2 mousePos = engine.getMousePositionPoint();
            
            if ( engine.isMouseButtonPressed( EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                if ( CollisionUtils.checkCollisionPointRectangle( mousePos, bounds ) ) {
                    hasFocus = true;
                    int pos = (int) ( ( mousePos.x - ( bounds.x + 5 ) ) / charWidth );
                    caretPosition = MathUtils.clamp( pos, 0, value.length() );
                } else {
                    hasFocus = false;
                }
            }
            
            if ( hasFocus ) {
                
                int key = engine.getKeysPressed().iterator().next();
                boolean ok = key != lastKey;
                boolean delayOk = false;
                
                if ( !ok && key != EngineFrame.KEY_NULL ) {
                    keyDelayCounter += delta;
                    if ( keyDelayCounter > KEY_DELAY_TIME ) {
                        delayOk = true;
                    }
                }
                
                if ( key == EngineFrame.KEY_NULL ) {
                    delayOk = false;
                    keyDelayCounter = 0;
                }
                
                if ( ( ok || delayOk ) && key != EngineFrame.KEY_NULL ) {
                    
                    showCaret = true;
                    caretBlinkCounter = 0;
                    
                    switch ( key ) {
                        case EngineFrame.KEY_BACKSPACE:
                            if ( !value.isEmpty() && caretPosition > 0 ) {
                                String prev = value.substring( 0, caretPosition - 1 );
                                String pos = value.substring( caretPosition );
                                value = prev + pos;
                                caretPosition--;
                            }
                            break;
                        case EngineFrame.KEY_DELETE:
                            if ( !value.isEmpty() && caretPosition < value.length() ) {
                                String prev = value.substring( 0, caretPosition );
                                String pos = value.substring( caretPosition + 1 );
                                value = prev + pos;
                            }
                            break;
                        case EngineFrame.KEY_TAB:
                            hasFocus = false;
                            break;
                        case EngineFrame.KEY_LEFT:
                            caretPosition--;
                            if ( caretPosition < 0 ) {
                                caretPosition = 0;
                            }
                            break;
                        case EngineFrame.KEY_RIGHT:
                            caretPosition++;
                            if ( caretPosition > value.length() ) {
                                caretPosition = value.length();
                            }
                            break;
                        case EngineFrame.KEY_HOME:
                            caretPosition = 0;
                            break;
                        case EngineFrame.KEY_END:
                            caretPosition = value.length();
                            break;
                        default:
                            if ( isValid( key ) && value.length() < maxVisibleChars ) {
                                if ( !value.isEmpty() ) {
                                    String prev = value.substring( 0, caretPosition );
                                    String pos = value.substring( caretPosition );
                                    value = prev + ( (char) key ) + pos;
                                } else {
                                    value += (char) key;
                                }
                                caretPosition++;
                            }
                            break;
                    }
                    
                }
                
                caretBlinkCounter += delta;
                if ( caretBlinkCounter > CARET_BLINK_TIME ) {
                    showCaret = !showCaret;
                    caretBlinkCounter = 0;
                }
                
                lastKey = key;
                
            }
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( charWidth == 0 ) {
            charWidth = engine.measureText( " ", FONT_SIZE );
            maxVisibleChars = (int) ( ( bounds.width - 8 ) / charWidth );
        }
        
        if ( visible ) {
            
            engine.setStrokeLineWidth( LINE_WIDTH );

            if ( enabled ) {
                if ( hasFocus ) {
                    drawTextField( MOUSE_DOWN_BACKGROUND_COLOR, MOUSE_DOWN_BORDER_COLOR, MOUSE_DOWN_TEXT_COLOR );
                } else {
                    drawTextField(CONTAINER_BACKGROUNG_COLOR, BORDER_COLOR, TEXT_COLOR );
                }
                if ( hasFocus && showCaret ) {
                    double caretX = bounds.x + 5 + caretPosition * charWidth;
                    engine.drawLine( caretX, bounds.y + 2, caretX, bounds.y + bounds.height - 2, MOUSE_DOWN_TEXT_COLOR );
                }
            } else {
                drawTextField( DISABLED_CONTAINER_BACKGROUND_COLOR, DISABLED_BORDER_COLOR, DISABLED_TEXT_COLOR );
            }
            
            drawBounds();
            
        }
        
    }
    
    private void drawTextField( Color backgroundColor, Color borderColor, Color textColor ) {
        engine.fillRectangle( bounds, backgroundColor );
        engine.drawRectangle( bounds, borderColor );
        engine.drawText( value, bounds.x + 5, bounds.y + bounds.height / 2 - 3, FONT_SIZE, textColor );
    }
    
    public boolean isValid( int key ) {
        return ( key >= EngineFrame.KEY_A && key <= EngineFrame.KEY_Z ) ||
               ( key >= EngineFrame.KEY_ZERO && key <= EngineFrame.KEY_NINE ) ||
               key == EngineFrame.KEY_PERIOD ||
               key == EngineFrame.KEY_COMMA ||
               key == EngineFrame.KEY_SEMICOLON ||
               key == EngineFrame.KEY_SPACE;
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        if ( enabled == false ) {
            hasFocus = false;
        }
    }
    
    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        if ( visible == false ) {
            hasFocus = false;
        }
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue( String value ) {
        if ( value == null ) {
            this.value = "";
        } else {
            this.value = value;
        }
        caretPosition = this.value.length();
    }
    
}
