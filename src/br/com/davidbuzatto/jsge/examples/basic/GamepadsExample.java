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
import java.awt.Color;

/**
 * Exemplo de uso de gamepads/joysticks/controles.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GamepadsExample extends EngineFrame {

    private boolean l1Down;
    private boolean l2Down;
    private boolean r1Down;
    private boolean r2Down;
    
    private boolean leftDown;
    private boolean upDown;
    private boolean rightDown;
    private boolean downDown;
    
    private boolean middleLeftDown;
    private boolean middleRightDown;
    private boolean leftThumbDown;
    private boolean rightThumbDown;
    
    private boolean squareDown;
    private boolean triangleDown;
    private boolean circleDown;
    private boolean xDown;
    
    private double lx;
    private double ly;
    private double rx;
    private double ry;
    private double z;
    private double leftTriggerPressure;
    private double rightTriggerPressure;
    
    private int[] buttons;
    private String[] labels;
    
    private boolean[][] states;
    
    private int currentGamepad;
    private boolean[] availableGamepads;
    private int availableGamepadsQuantity;
    
    // 0 => Playstation, 1 => Xbox, 2 -> Nintendo
    private int currentSkin;
    
    /**
     * Cria o exemplo.
     */
    public GamepadsExample() {
        super( 620, 505, "Gamepads", 60, true );
    }
    
    @Override
    public void create() {
        
        buttons = new int[]{
            GAMEPAD_BUTTON_LEFT_FACE_LEFT,
            GAMEPAD_BUTTON_LEFT_FACE_UP,
            GAMEPAD_BUTTON_LEFT_FACE_RIGHT,
            GAMEPAD_BUTTON_LEFT_FACE_DOWN,
            GAMEPAD_BUTTON_MIDDLE_LEFT,
            GAMEPAD_BUTTON_MIDDLE_RIGHT,
            GAMEPAD_BUTTON_LEFT_THUMB,
            GAMEPAD_BUTTON_RIGHT_THUMB,
            GAMEPAD_BUTTON_RIGHT_FACE_LEFT,
            GAMEPAD_BUTTON_RIGHT_FACE_UP,
            GAMEPAD_BUTTON_RIGHT_FACE_RIGHT,
            GAMEPAD_BUTTON_RIGHT_FACE_DOWN,
            GAMEPAD_BUTTON_LEFT_TRIGGER_1,
            GAMEPAD_BUTTON_LEFT_TRIGGER_2,
            GAMEPAD_BUTTON_RIGHT_TRIGGER_1,
            GAMEPAD_BUTTON_RIGHT_TRIGGER_2
        };
        
        labels = new String[]{
            "left", "up", "right", "down", "select", "start", "left thumb", "right thumb",
            "Square/X", "Triangle/Y", "Circle/B", "Cross/A", "L1/LB", "L2/LT", "R1/RB", "R2/RT"
        };
        
        states = new boolean[buttons.length][4];
        
        currentGamepad = GAMEPAD_1;
        availableGamepads = new boolean[4];
        
    }

    @Override
    public void update( double delta ) {
        
        l1Down = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_LEFT_TRIGGER_1 );
        l2Down = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_LEFT_TRIGGER_2 );
        r1Down = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_RIGHT_TRIGGER_1 );
        r2Down = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_RIGHT_TRIGGER_2 );
        
        leftDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_LEFT_FACE_LEFT );
        upDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_LEFT_FACE_UP );
        rightDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_LEFT_FACE_RIGHT );
        downDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_LEFT_FACE_DOWN );
        
        middleLeftDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_MIDDLE_LEFT );
        middleRightDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_MIDDLE_RIGHT );
        leftThumbDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_LEFT_THUMB );
        rightThumbDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_RIGHT_THUMB );
        
        triangleDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_RIGHT_FACE_UP );
        circleDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_RIGHT_FACE_RIGHT );
        xDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_RIGHT_FACE_DOWN );
        squareDown = isGamepadButtonDown( currentGamepad, GAMEPAD_BUTTON_RIGHT_FACE_LEFT );
        
        lx = getGamepadAxisMovement( currentGamepad, GAMEPAD_AXIS_LEFT_X );
        ly = getGamepadAxisMovement( currentGamepad, GAMEPAD_AXIS_LEFT_Y );
        rx = getGamepadAxisMovement( currentGamepad, GAMEPAD_AXIS_RIGHT_X );
        ry = getGamepadAxisMovement( currentGamepad, GAMEPAD_AXIS_RIGHT_Y );
        z = getGamepadAxisMovement( currentGamepad, GAMEPAD_AXIS_Z );
        leftTriggerPressure = getGamepadAxisMovement( currentGamepad, GAMEPAD_AXIS_LEFT_TRIGGER );
        rightTriggerPressure = getGamepadAxisMovement( currentGamepad, GAMEPAD_AXIS_RIGHT_TRIGGER );
        
        for ( int i = 0; i < states.length; i++ ) {
            states[i][0] = isGamepadButtonPressed( currentGamepad, buttons[i] );
            states[i][1] = isGamepadButtonReleased( currentGamepad, buttons[i] );
            states[i][2] = isGamepadButtonDown( currentGamepad, buttons[i] );
            states[i][3] = isGamepadButtonUp( currentGamepad, buttons[i] );
        }
        
        availableGamepadsQuantity = 0;
        for ( int i = 0; i < 4; i++ ) {
            availableGamepads[i] = isGamepadAvailable( i );
            availableGamepadsQuantity += availableGamepads[i] ? 1 : 0;
        }
        
        if ( isKeyPressed( KEY_LEFT ) ) {
            currentGamepad--;
            if ( currentGamepad < 0 ) {
                currentGamepad = availableGamepadsQuantity - 1;
            }
        }
        
        if ( isKeyPressed( KEY_RIGHT ) ) {
            currentGamepad = ( currentGamepad + 1 ) % availableGamepadsQuantity;
        }
        
        if ( isKeyPressed( KEY_SPACE ) ) {
            currentSkin = ( currentSkin + 1 ) % 3;
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        fillRectangle( 40, 60, 60, 30, l2Down ? GOLD : GRAY );
        fillRectangle( 40, 90 - 30 * leftTriggerPressure, 60, 30 * leftTriggerPressure, BLUE );
        fillRectangle( 40, 110, 60, 30, l1Down ? GOLD : GRAY );
        
        fillRectangle( 320, 60, 60, 30, r2Down ? GOLD : GRAY );
        fillRectangle( 320, 90 - 30 * rightTriggerPressure, 60, 30 * rightTriggerPressure, BLUE );
        fillRectangle( 320, 110, 60, 30, r1Down ? GOLD : GRAY );
        
        fillRectangle( 160, 60, 100 * ( z + 1 ) / 2, 30, BLUE );
        drawRectangle( 160, 60, 100, 29, BLACK );
        drawText( String.format( "%+.3f", z ), 175, 68, 20, BLACK );
        
        fillRectangle( 10, 210, 40, 40, leftDown ? GOLD : GRAY );
        fillRectangle( 50, 170, 40, 40, upDown ? GOLD : GRAY );
        fillRectangle( 90, 210, 40, 40, rightDown ? GOLD : GRAY );
        fillRectangle( 50, 250, 40, 40, downDown ? GOLD : GRAY );
        fillPolygon( 30, 230, 3, 10, 180, BLACK );
        fillPolygon( 70, 190, 3, 10, 270, BLACK );
        fillPolygon( 110, 230, 3, 10, 0, BLACK );
        fillPolygon( 70, 270, 3, 10, 90, BLACK );
        
        fillRectangle( 160, 220, 40, 20, middleLeftDown ? GOLD : GRAY );
        fillRectangle( 220, 220, 40, 20, middleRightDown ? GOLD : GRAY );
        
        switch ( currentSkin ) {
            case 0:
                drawText( "L2", 58, 68, 20, BLACK );
                drawText( "L1", 58, 118, 20, BLACK );
                drawText( "R2", 338, 68, 20, BLACK );
                drawText( "R1", 338, 118, 20, BLACK );
                drawText( "select", 156, 245, 14, BLACK );
                drawText( "start", 220, 245, 14, BLACK );
                break;
            case 1:
                drawText( "LT", 58, 68, 20, BLACK );
                drawText( "LB", 58, 118, 20, BLACK );
                drawText( "RT", 338, 68, 20, BLACK );
                drawText( "RB", 338, 118, 20, BLACK );
                drawText( "back", 164, 245, 14, BLACK );
                drawText( "start", 220, 245, 14, BLACK );
                break;
            case 2:
                drawText( "ZL", 58, 68, 20, BLACK );
                drawText( "L", 64, 118, 20, BLACK );
                drawText( "ZR", 338, 68, 20, BLACK );
                drawText( "R", 344, 118, 20, BLACK );
                drawText( "select", 156, 245, 14, BLACK );
                drawText( "start", 220, 245, 14, BLACK );
                break;
        }
        
        fillCircle( 135, 340, 40, LIGHTGRAY );
        fillCircle( 135, 340, 20, leftThumbDown ? GOLD : GRAY );
        fillCircle( 135 + 25 * lx, 340 + 25 * ly, 15, BLACK );
        
        fillCircle( 285, 340, 40, LIGHTGRAY );
        fillCircle( 285, 340, 20, rightThumbDown ? GOLD : GRAY );
        fillCircle( 285 + 25 * rx, 340 + 25 * ry, 15, BLACK );
        
        Color btLeft = BLACK;
        Color btUp = BLACK;
        Color btRight = BLACK;
        Color btDown = BLACK;
        
        switch ( currentSkin ) {
            case 0:
                btLeft = PINK.darker();
                btUp = LIME.darker();
                btRight = RED.darker();
                btDown = BLUE.darker();
                break;
            case 1:
                btLeft = BLUE.darker();
                btUp = ORANGE.darker();
                btRight = RED.darker();
                btDown = GREEN.darker();
                break;
            case 2:
                btLeft = GREEN.darker();
                btUp = BLUE.darker();
                btRight = RED.darker();
                btDown = GOLD.darker();
                break;
        }
        
        fillCircle( 310, 230, 20, squareDown ? btLeft : GRAY );
        fillCircle( 350, 190, 20, triangleDown ? btUp : GRAY );
        fillCircle( 390, 230, 20, circleDown ? btRight : GRAY );
        fillCircle( 350, 270, 20, xDown ? btDown : GRAY );
        
        setStrokeLineWidth( 4 );
        
        switch ( currentSkin ) {
            case 0:
                drawPolygon( 310, 230, 4, 15, 45, PINK );
                drawPolygon( 350, 190, 3, 12, 30, LIME );
                drawCircle( 390, 228, 12, RED );
                drawLine( 342, 262, 358, 278, BLUE );
                drawLine( 342, 278, 358, 262, BLUE );
                break;
            case 1:
                drawText( "X", 302, 220, 30, BLUE );
                drawText( "Y", 342, 180, 30, ORANGE );
                drawText( "B", 382, 220, 30, RED );
                drawText( "A", 342, 260, 30, GREEN );
                break;
            case 2:
                drawText( "Y", 302, 220, 30, GREEN );
                drawText( "X", 342, 180, 30, BLUE );
                drawText( "A", 382, 220, 30, RED );
                drawText( "B", 342, 260, 30, GOLD );
                break;
        }
        
        setStrokeLineWidth( 1 );
        int xt = 530;
        int yt = 60;
        int w = 20;
        drawText( "pressed", xt+5, yt-10, -45, 14, BLACK );
        drawText( "released", xt+25, yt-10, -45, 14, BLACK );
        drawText( "down", xt+45, yt-10, -45, 14, BLACK );
        drawText( "up", xt+65, yt-10, -45, 14, BLACK );
        for ( int i = 0; i < states.length; i++ ) {
            drawText( labels[i], xt - 10 - measureText( labels[i], 14 ), yt + 5 + w * i, 14, BLACK );
            for ( int j = 0; j < states[i].length; j++ ) {
                fillRectangle( xt + w * j, yt + w * i, w, w, states[i][j] ? GOLD : GRAY );
                drawRectangle( xt + w * j, yt + w * i, w, w, BLACK );
            }
        }
        
        drawLine( 0, 390, getScreenWidth(), 390, BLACK );
        
        String aGpads = "Available Gamepads:";
        int startAGpads = measureText( aGpads, 14 ) + 20;
        drawText( aGpads, 10, 400, 14, BLACK );
        for ( int i = 0; i < availableGamepads.length; i++ ) {
            fillRectangle( startAGpads + i * 20, 395, 20, 20, availableGamepads[i] ? GREEN : GRAY );
            drawRectangle( startAGpads + i * 20, 395, 20, 20, BLACK );
            drawText( String.format( "%d", i+1 ), startAGpads + i * 20 + 6, 401, 14, i == currentGamepad ? BLACK : DARKGRAY );
        }
        
        drawText( "press <LEFT/RIGHT> to change", startAGpads + 90, 400, 14, BLACK );
        
        String cGpad = "Current Gamepad:";
        int startCGpads = measureText( cGpad, 14 ) + 10;
        drawText( 
            String.format( 
                "%s\n  id: %d\n  name: %s", 
                cGpad,
                currentGamepad+1, 
                getGamepadName( currentGamepad )
            ), 
            10, 430, 14, BLACK
        );
        
        int xCGpads = startAGpads + 10 + 20 * currentGamepad;
        drawLine( startCGpads, 435, xCGpads, 435, BLACK );
        drawLine( xCGpads, 435, xCGpads, 415, BLACK );
        
        String skinLabel = "";
        Color skinLabelColor = BLACK;
        
        switch ( currentSkin ) {
            case 0:
                skinLabel = "PlayStation";
                skinLabelColor = BLUE;
                break;
            case 1:
                skinLabel = "Xbox";
                skinLabelColor = LIME;
                break;
            case 2:
                skinLabel = "Nintendo";
                skinLabelColor = RED;
                break;
        }
        
        String skinText = "\"Skin\":";
        drawText( skinText, 10, 485, 14, BLACK );
        drawText( String.format( "%s - press <SPACE> to change", skinLabel ), measureText( skinText, 14 ) + 20, 485, 14, skinLabelColor );
        
        drawFPS( 10, 10 );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new GamepadsExample();
    }
    
}
