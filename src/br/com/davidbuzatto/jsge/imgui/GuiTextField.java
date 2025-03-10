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
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * Um componente de campo de texto. Este componente é um tanto rudimentar,
 * aceitando apenas letras não acentuadas, dígitos e alguns sinais
 * de pontuação, como vírgulas, pontos etc. Não há suporte para 
 * diferenciação de letras maiúsculas e minúsculas.
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
    
    /**
     * Cria o componente.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiTextField( double x, double y, double width, double height, String value, EngineFrame engine ) {
        super( x, y, width, height, engine );
        initData( value );
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param width Largura do retângulo que define os limites do componente.
     * @param height Altura do retângulo que define os limites do componente.
     * @param value O valor inicial do componente. 
     */
    public GuiTextField( double x, double y, double width, double height, String value ) {
        super( x, y, width, height );
        initData( value );
    }
    
    /**
     * Cria o componente.
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiTextField( Rectangle bounds, String value, EngineFrame engine ) {
        super( bounds, engine );
        initData( value );
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param bounds Um retângulo que define os limites do componente.
     * @param value O valor inicial do componente. 
     */
    public GuiTextField( Rectangle bounds, String value ) {
        super( bounds );
        initData( value );
    }
    
    private void initData( String value ) {
        setValue( value );
        this.showCaret = true;
        this.backgroundColor = CONTAINER_BACKGROUND_COLOR;
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
                
                Iterator<Integer> it = engine.getKeysPressed().iterator();
                boolean isShiftDown = false;
                boolean isCapsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState( KeyEvent.VK_CAPS_LOCK );
                int key = EngineFrame.KEY_NULL;
                
                while ( it.hasNext() ) {
                    int k = it.next();
                    if ( k == EngineFrame.KEY_SHIFT ) {
                        isShiftDown = true;
                    } else {
                        key = k;
                    }
                }
                
                //int key = engine.getKeysPressed().iterator().next();
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
                            if ( isKeyValid( key ) && value.length() < maxVisibleChars ) {
                                if ( !value.isEmpty() ) {
                                    String prev = value.substring( 0, caretPosition );
                                    String pos = value.substring( caretPosition );
                                    value = prev + keyToChar( key, isShiftDown, isCapsLockOn ) + pos;
                                } else {
                                    value += keyToChar( key, isShiftDown, isCapsLockOn );
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
                    if ( mouseState == GuiComponentMouseState.MOUSE_OVER ) {
                        drawTextField( MOUSE_OVER_BACKGROUND_COLOR, MOUSE_OVER_BORDER_COLOR, TEXT_COLOR );
                    } else {
                        drawTextField( backgroundColor, borderColor, textColor );
                    }
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
    
    private boolean isKeyValid( int key ) {
        return ( key >= EngineFrame.KEY_A && key <= EngineFrame.KEY_Z ) ||
               ( key >= EngineFrame.KEY_ZERO && key <= EngineFrame.KEY_NINE ) ||
               ( key >= EngineFrame.KEY_KP_0 && key <= EngineFrame.KEY_KP_9 ) ||
               key == EngineFrame.KEY_BACKSLASH ||
               key == EngineFrame.KEY_COMMA ||
               key == EngineFrame.KEY_PERIOD ||
               key == EngineFrame.KEY_SEMICOLON ||
               key == EngineFrame.KEY_SLASH ||
               key == EngineFrame.KEY_APOSTROPHE ||
               key == EngineFrame.KEY_LEFT_BRACKET ||
               key == EngineFrame.KEY_RIGHT_BRACKET ||
               key == EngineFrame.KEY_MINUS ||
               key == EngineFrame.KEY_EQUAL ||
               key == EngineFrame.KEY_KP_ADD ||
               key == EngineFrame.KEY_KP_SUBTRACT ||
               key == EngineFrame.KEY_KP_MULTIPLY ||
               key == EngineFrame.KEY_KP_DIVIDE ||
               key == EngineFrame.KEY_SPACE;
    }
    
    private char keyToChar( int key, boolean isShiftDown, boolean isCapsLockOn ) {
        
        if ( key >= EngineFrame.KEY_A && key <= EngineFrame.KEY_Z ) {
            
            if ( isShiftDown || isCapsLockOn ) {
                return Character.toUpperCase( (char) key );
            }
            
            return Character.toLowerCase( (char) key );
            
        } else if ( key >= EngineFrame.KEY_ZERO && key <= EngineFrame.KEY_NINE ) {
            
            if ( isShiftDown ) {
                switch ( key ) {
                    case EngineFrame.KEY_ONE: return '!';
                    case EngineFrame.KEY_TWO: return '@';
                    case EngineFrame.KEY_THREE: return '#';
                    case EngineFrame.KEY_FOUR: return '$';
                    case EngineFrame.KEY_FIVE: return '%';
                    case EngineFrame.KEY_SEVEN: return '&';
                    case EngineFrame.KEY_EIGHT: return '*';
                    case EngineFrame.KEY_NINE: return '(';
                    case EngineFrame.KEY_ZERO: return ')';
                    default: return (char) key;
                }
            }
            
            return (char) key;
            
        } else if ( key >= EngineFrame.KEY_KP_0 && key <= EngineFrame.KEY_KP_9 ) {
            
            switch ( key ) {
                case EngineFrame.KEY_KP_0: return '0';
                case EngineFrame.KEY_KP_1: return '1';
                case EngineFrame.KEY_KP_2: return '2';
                case EngineFrame.KEY_KP_3: return '3';
                case EngineFrame.KEY_KP_4: return '4';
                case EngineFrame.KEY_KP_5: return '5';
                case EngineFrame.KEY_KP_6: return '6';
                case EngineFrame.KEY_KP_7: return '7';
                case EngineFrame.KEY_KP_8: return '8';
                case EngineFrame.KEY_KP_9: return '9';
                default: return (char) key;
            }
            
        } else if ( key == EngineFrame.KEY_BACKSLASH ) {
            if ( isShiftDown ) {
                return '|';
            }
            return '\\';
        } else if ( key == EngineFrame.KEY_COMMA ) {
            if ( isShiftDown ) {
                return '<';
            }
            return ',';
        } else if ( key == EngineFrame.KEY_PERIOD ) {
            if ( isShiftDown ) {
                return '>';
            }
            return '.';
        } else if ( key == EngineFrame.KEY_SEMICOLON ) {
            if ( isShiftDown ) {
                return ':';
            }
            return ';';
        } else if ( key == EngineFrame.KEY_SLASH ) {
            if ( isShiftDown ) {
                return '?';
            }
            return '/';
        } else if ( key == EngineFrame.KEY_APOSTROPHE ) {
            if ( isShiftDown ) {
                return '"';
            }
            return '\'';
        } else if ( key == EngineFrame.KEY_LEFT_BRACKET ) {
            if ( isShiftDown ) {
                return '{';
            }
            return '[';
        } else if ( key == EngineFrame.KEY_RIGHT_BRACKET ) {
            if ( isShiftDown ) {
                return '}';
            }
            return ']';
        } else if ( key == EngineFrame.KEY_MINUS ) {
            if ( isShiftDown ) {
                return '_';
            }
            return '-';
        } else if ( key == EngineFrame.KEY_EQUAL ) {
            if ( isShiftDown ) {
                return '+';
            }
            return '=';
        } else if ( key == EngineFrame.KEY_KP_ADD ) {
            return '+';
        } else if ( key == EngineFrame.KEY_KP_SUBTRACT ) {
            return '-';
        } else if ( key == EngineFrame.KEY_KP_MULTIPLY ) {
            return '*';
        } else if ( key == EngineFrame.KEY_KP_DIVIDE ) {
            return '/';
        }
        
        return (char) key;
        
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
    
    /**
     * Obtém o valor do componente.
     * 
     * @return O valor do componente.
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Configura o valor do componente.
     * 
     * @param value O valor do componente. 
     */
    public void setValue( String value ) {
        if ( value == null ) {
            this.value = "";
        } else {
            this.value = value;
        }
        caretPosition = this.value.length();
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        setBackgroundColor( theme.containerBackgroundColor );
    }
    
}
