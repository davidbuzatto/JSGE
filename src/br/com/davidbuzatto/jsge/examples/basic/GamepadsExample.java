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

/**
 * Exemplo de uso de gamepads/joysticks/controles.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GamepadsExample extends EngineFrame {

    private boolean l1Pressed;
    private boolean l2Pressed;
    private boolean r1Pressed;
    private boolean r2Pressed;
    
    private boolean leftPressed;
    private boolean upPressed;
    private boolean rightPressed;
    private boolean downPressed;
    
    private boolean middleLeftPressed;
    private boolean middleRightPressed;
    private boolean leftThumbPressed;
    private boolean rightThumbPressed;
    
    private boolean squarePressed;
    private boolean trianglePressed;
    private boolean circlePressed;
    private boolean xPressed;
    
    private double lx;
    private double ly;
    private double rx;
    private double ry;
    private double leftTriggerPressure;
    private double rightTriggerPressure;
    
    /**
     * Cria o exemplo.
     */
    public GamepadsExample() {
        super( 800, 500, "Gamepads", 60, true );
    }
    
    @Override
    public void create() {
    }

    @Override
    public void update( double delta ) {
        
        l1Pressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_LEFT_TRIGGER_1 );
        l2Pressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_LEFT_TRIGGER_2 );
        r1Pressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_RIGHT_TRIGGER_1 );
        r2Pressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_RIGHT_TRIGGER_2 );
        
        leftPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_LEFT_FACE_LEFT );
        upPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_LEFT_FACE_UP );
        rightPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_LEFT_FACE_RIGHT );
        downPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_LEFT_FACE_DOWN );
        
        middleLeftPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_MIDDLE_LEFT );
        middleRightPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_MIDDLE_RIGHT );
        leftThumbPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_LEFT_THUMB );
        rightThumbPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_RIGHT_THUMB );
        
        trianglePressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_RIGHT_FACE_UP );
        circlePressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_RIGHT_FACE_RIGHT );
        xPressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_RIGHT_FACE_DOWN );
        squarePressed = isGamepadButtonPressed( GAMEPAD_1, GAMEPAD_BUTTON_RIGHT_FACE_LEFT );
        
        lx = getGamepadAxisMovement( GAMEPAD_1, GAMEPAD_AXIS_LEFT_X );
        ly = getGamepadAxisMovement( GAMEPAD_1, GAMEPAD_AXIS_LEFT_Y );
        rx = getGamepadAxisMovement( GAMEPAD_1, GAMEPAD_AXIS_RIGHT_X );
        ry = getGamepadAxisMovement( GAMEPAD_1, GAMEPAD_AXIS_RIGHT_Y );
        leftTriggerPressure = getGamepadAxisMovement( GAMEPAD_1, GAMEPAD_AXIS_LEFT_TRIGGER );
        rightTriggerPressure = getGamepadAxisMovement( GAMEPAD_1, GAMEPAD_AXIS_RIGHT_TRIGGER );
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        fillRectangle( 50, 100 - 50 * leftTriggerPressure, 50, 50 * leftTriggerPressure, PINK );
        drawRectangle( 50, 50, 50, 50, BLACK );
        fillRectangle( 150, 50, 50, 50, l2Pressed ? GREEN : BLUE );
        fillRectangle( 150, 150, 50, 50, l1Pressed ? GREEN : BLUE );
        
        fillRectangle( 650, 100 - 50 * rightTriggerPressure, 50, 50 * rightTriggerPressure, PINK );
        drawRectangle( 650, 50, 50, 50, BLACK );
        fillRectangle( 550, 50, 50, 50, r2Pressed ? GREEN : BLUE );
        fillRectangle( 550, 150, 50, 50, r1Pressed ? GREEN : BLUE );
        
        fillRectangle( 100, 300, 50, 50, leftPressed ? GREEN : BLUE );
        fillRectangle( 150, 250, 50, 50, upPressed ? GREEN : BLUE );
        fillRectangle( 200, 300, 50, 50, rightPressed ? GREEN : BLUE );
        fillRectangle( 150, 350, 50, 50, downPressed ? GREEN : BLUE );
        
        fillRectangle( 300, 300, 50, 50, middleLeftPressed ? GREEN : BLUE );
        fillRectangle( 400, 300, 50, 50, middleRightPressed ? GREEN : BLUE );
        
        fillCircle( 275, 425, 50, LIGHTGRAY );
        fillRectangle( 250, 400, 50, 50, leftThumbPressed ? GREEN : BLUE );
        fillCircle( 275 + 50 * lx, 425 + 50 * ly, 20, BLACK );
        
        fillCircle( 475, 425, 50, LIGHTGRAY );
        fillRectangle( 450, 400, 50, 50, rightThumbPressed ? GREEN : BLUE );
        fillCircle( 475 + 50 * rx, 425 + 50 * ry, 20, BLACK );
        
        fillRectangle( 500, 300, 50, 50, squarePressed ? GREEN : BLUE );
        fillRectangle( 550, 250, 50, 50, trianglePressed ? GREEN : BLUE );
        fillRectangle( 600, 300, 50, 50, circlePressed ? GREEN : BLUE );
        fillRectangle( 550, 350, 50, 50, xPressed ? GREEN : BLUE );
        
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
