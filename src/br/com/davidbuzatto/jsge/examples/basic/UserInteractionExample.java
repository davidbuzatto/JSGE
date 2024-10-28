/*
 * Copyright (C) 2024 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.examples.basic;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;

/**
 * Exemplo básico de entrada do usuário.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class UserInteractionExample extends EngineFrame {

    private String keyAPress;
    private String keyADown;
    private String mouseLeftPress;
    private String mouseLeftDown;
    private String mouseMiddlePress;
    private String mouseMiddleDown;
    private String mouseRightPress;
    private String mouseRightDown;
    
    private Rectangle wheelRect;
    
    private char lastChar;
    private int lastKey;
    
    /**
     * Cria o exemplo.
     */
    public UserInteractionExample() {
        super( 400, 280, "User Interaction", 60, true );
    }
    
    @Override
    public void create() {
        
        keyAPress = "none";
        keyADown = "none";
        
        mouseLeftPress = "none";
        mouseLeftDown = "none";
        mouseMiddlePress = "none";
        mouseMiddleDown = "none";
        mouseRightPress = "none";
        mouseRightDown = "none";
        
        wheelRect = new Rectangle( 10, getScreenHeight() - 90, 80, 80 );
        
    }

    @Override
    public void update() {
        
        if ( isKeyPressed( KEY_A ) ) keyAPress = "pressed";
        if ( isKeyReleased( KEY_A ) ) keyAPress = "released";
        keyADown = isKeyDown( KEY_A ) ? "down" : "up";
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) mouseLeftPress = "pressed";
        if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) mouseLeftPress = "released";
        mouseLeftDown = isMouseButtonDown( MOUSE_BUTTON_LEFT ) ? "down" : "up";
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_MIDDLE ) ) mouseMiddlePress = "pressed";
        if ( isMouseButtonReleased( MOUSE_BUTTON_MIDDLE ) ) mouseMiddlePress = "released";
        mouseMiddleDown = isMouseButtonDown( MOUSE_BUTTON_MIDDLE ) ? "down" : "up";
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_RIGHT ) ) mouseRightPress = "pressed";
        if ( isMouseButtonReleased( MOUSE_BUTTON_RIGHT ) ) mouseRightPress = "released";
        mouseRightDown = isMouseButtonDown( MOUSE_BUTTON_RIGHT ) ? "down" : "up";
        
        char c = getCharPressed();
        if ( c != KEY_NULL ) {
            lastChar = c;
        }
        
        int k = getKeyPressed();
        if ( k != KEY_NULL ) {
            lastKey = k;
        }
        
        wheelRect.x += getMouseWheelMove() * 5;
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        setFontStyle( FONT_BOLD );
        setStrokeWidth( 2 );
        
        drawText( "        Key A: " + keyAPress + " | " + keyADown, 15, 10, 20, BLACK );
        drawText( "   Mouse left: " + mouseLeftPress + " | " + mouseLeftDown, 15, 40, 20, BLACK );
        drawText( " Mouse middle: " + mouseMiddlePress + " | " + mouseMiddleDown, 15, 70, 20, BLACK );
        drawText( "  Mouse right: " + mouseRightPress + " | " + mouseRightDown, 15, 100, 20, BLACK );
        drawText( "Last key char: " + lastChar, 15, 130, 20, BLACK );
        drawText( "     Last key: " + lastKey, 15, 160, 20, BLACK );
        
        wheelRect.fill( this, GOLD );
        wheelRect.draw( this, BLACK );
        drawText( "roll", wheelRect.x + 5, wheelRect.y + 12, 20, BLACK );
        drawText( "the", wheelRect.x + 5, wheelRect.y + 32, 20, BLACK );
        drawText( "wheel!", wheelRect.x + 5, wheelRect.y + 52, 20, BLACK );
        
        drawFPS( 10, 10 );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new UserInteractionExample();
    }
    
}
