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
package br.com.davidbuzatto.jsge.tests;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 * Classe de testes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ControllerTest extends EngineFrame {
    
    private List<Controller> foundControllers;
    private Gamepad[] gamepads;
    
    /**
     * Cria o teste.
     */
    public ControllerTest() {
        super( 800, 500, "Scratch Pad", 60, true );
    }
    
    @Override
    public void create() {
        createGamepadEnvinronment();
    }
    
    private void createGamepadEnvinronment() {
        
        gamepads = new Gamepad[4];
        
        for ( int i = 0; i < gamepads.length; i++ ) {
            gamepads[i] = new Gamepad( i );
        }
        
        searchForControllers();
        
    }
    
    private void searchForControllers() {

        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        foundControllers = new ArrayList<>();

        for ( int i = 0; i < controllers.length; i++ ) {

            Controller controller = controllers[i];

            if ( controller.getType() == Controller.Type.GAMEPAD ) {
                foundControllers.add( controller );
                gamepads[foundControllers.size()-1].setName( controller.getName() );
            }
            
        }
        
    }
    
    private void acquireAllGamepadData() {
        
        int i = 0;
        
        for ( Controller c : foundControllers ) {
            acquireGamepadData( c, gamepads[i] );
        }
        
    }
    
    private void acquireGamepadData( Controller controller, Gamepad gamepad ) {

        if ( controller.poll() ) {
            
            // configura como disponível
            gamepad.setAvailable( true );
            
            // percorre todos os componentes do controle
            Component[] components = controller.getComponents();

            for ( int i = 0; i < components.length; i++ ) {

                Component component = components[i];
                Identifier componentIdentifier = component.getIdentifier();

                // botões contém apenas números no nome
                if ( componentIdentifier.getName().matches( "^[0-9]*$" ) ) {

                    // o botão está pressionado?
                    boolean isPressed = true;
                    if ( component.getPollData() == 0.0f ) {
                        isPressed = false;
                    }

                    // índice do botão
                    String buttonIndex = component.getIdentifier().toString();

                    gamepad.setButtonState( Integer.parseInt( buttonIndex ), isPressed );

                    // pula para a próxima iteração, não precisa testar outros tipos
                    continue;

                }

                // hat switch
                if ( componentIdentifier == Component.Identifier.Axis.POV ) {
                    float hatSwitch = component.getPollData();
                    int dpadCode = (int) ( component.getPollData() * 1000 ) / 125;
                    gamepad.setHatSwitch( hatSwitch );
                    gamepad.resetHatSwitchButtons();
                    gamepad.setHatSwitchButtonState( dpadCode, true );
                    continue;
                }

                // eixos
                if ( component.isAnalog() ) {

                    float axisValue = component.getPollData();

                    // eixo x
                    if ( componentIdentifier == Component.Identifier.Axis.X ) {
                        gamepad.setX( axisValue );
                        continue;
                    }

                    // eixo y
                    if ( componentIdentifier == Component.Identifier.Axis.Y ) {
                        gamepad.setY( axisValue );
                        continue;
                    }

                    // eixo z
                    if ( componentIdentifier == Component.Identifier.Axis.Z ) {                            
                        gamepad.setZ( axisValue );
                        continue;
                    }

                    // eixo rx
                    if ( componentIdentifier == Component.Identifier.Axis.RX ) {
                        gamepad.setRx( axisValue );
                        continue;
                    }

                    // eixo ry
                    if ( componentIdentifier == Component.Identifier.Axis.RY ) {
                        gamepad.setRy( axisValue );
                        continue;
                    }

                    // eixo rz
                    if ( componentIdentifier == Component.Identifier.Axis.RZ ) {
                        gamepad.setRz( axisValue );
                        continue;
                    }

                }

            }
            
            //System.out.println( gamepad );

        } else {
            traceLogError( "Gamepad %d disconnected", gamepad.getId() );
        }
        
    }
    
    private void prepareGamepadsToNextCycle() {
        for ( Gamepad gp : gamepads ) {
            gp.setAvailable( false );
            gp.copyLastState();
        }
    }
    
    public boolean isGamepadAvailable( int gamepadId ) {
        return gamepads[gamepadId].isAvailable();
    }
    
    // Get gamepad internal name id
    public String getGamepadName( int gamepadId ) {
        return gamepads[gamepadId].getName();
    }
    
    // Check if a gamepad button has been pressed once
    public boolean isGamepadButtonPressed( int gamepadId, int button ) {
        
        Gamepad g = gamepads[gamepadId];
        
        // botões "normais"
        if ( button >= 0 && button <= 9 ) {
            return g.isButtonPressed( button );
        }
        
        // gatilho esquerdo
        if ( button == 44 ) {
            return g.getZ() > 0.0;
        }
        
        // gatilho direito
        if ( button == 55 ) {
            return g.getZ() < -0.0001;
        }
        
        // dpad
        if ( button >= 10 && button <= 13 ) {
            switch ( button ) {
                case GAMEPAD_BUTTON_LEFT_FACE_UP:
                    return g.isHatSwitchButtonPressed( 1 ) || 
                           g.isHatSwitchButtonPressed( 2 ) || 
                           g.isHatSwitchButtonPressed( 3 );
                case GAMEPAD_BUTTON_LEFT_FACE_RIGHT:
                    return g.isHatSwitchButtonPressed( 3 ) || 
                           g.isHatSwitchButtonPressed( 4 ) || 
                           g.isHatSwitchButtonPressed( 5 );
                case GAMEPAD_BUTTON_LEFT_FACE_DOWN:
                    return g.isHatSwitchButtonPressed( 5 ) || 
                           g.isHatSwitchButtonPressed( 6 ) || 
                           g.isHatSwitchButtonPressed( 7 );
                case GAMEPAD_BUTTON_LEFT_FACE_LEFT:
                    return g.isHatSwitchButtonPressed( 7 ) || 
                           g.isHatSwitchButtonPressed( 8 ) ||
                           g.isHatSwitchButtonPressed( 1 );
            }
        }
        
        return false;
        
    }
    
    // Check if a gamepad button is being pressed
    public boolean isGamepadButtonDown( int gamepadId, int button ) {
        return gamepads[gamepadId].isButtonPressed( button );
    }
    
    // Check if a gamepad button has been released once
    public boolean isGamepadButtonReleased( int gamepadId, int button ) {
        return !gamepads[gamepadId].isButtonPressed( button );
    }
    
    // Check if a gamepad button is NOT being pressed
    public boolean isGamepadButtonUp( int gamepadId, int button ) {
        return !gamepads[gamepadId].isButtonPressed( button );
    }
    
    // Get axis movement value for a gamepad axis*/
    public double getGamepadAxisMovement( int gamepadId, int axis ) {
        
        switch ( axis ) {
            case GAMEPAD_AXIS_LEFT_X:
                return gamepads[gamepadId].getX();
            case GAMEPAD_AXIS_LEFT_Y:
                return gamepads[gamepadId].getY();
            case GAMEPAD_AXIS_RIGHT_X:
                return gamepads[gamepadId].getRx();
            case GAMEPAD_AXIS_RIGHT_Y:
                return gamepads[gamepadId].getRy();
            case GAMEPAD_AXIS_LEFT_TRIGGER:
                return gamepads[gamepadId].getZ();
            case GAMEPAD_AXIS_RIGHT_TRIGGER:
                return -gamepads[gamepadId].getZ();
        }
        
        return 0;
        
    }
    
    @Override
    public void update( double delta ) {
        
        if ( !foundControllers.isEmpty() ) {
            prepareGamepadsToNextCycle();
            acquireAllGamepadData();
        } else {
            traceLogError( "No controllers found" );
        }
        
        l1Pressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_LEFT_TRIGGER_1 );
        l2Pressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_LEFT_TRIGGER_2 );
        r1Pressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_RIGHT_TRIGGER_1 );
        r2Pressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_RIGHT_TRIGGER_2 );
        
        leftPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_LEFT_FACE_LEFT );
        upPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_LEFT_FACE_UP );
        rightPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_LEFT_FACE_RIGHT );
        downPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_LEFT_FACE_DOWN );
        
        middleLeftPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_MIDDLE_LEFT );
        middleRightPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_MIDDLE_RIGHT );
        leftThumbPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_LEFT_THUMB );
        rightThumbPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_RIGHT_THUMB );
        
        trianglePressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_RIGHT_FACE_UP );
        circlePressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_RIGHT_FACE_RIGHT );
        xPressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_RIGHT_FACE_DOWN );
        squarePressed = isGamepadButtonPressed( 0, GAMEPAD_BUTTON_RIGHT_FACE_LEFT );
        
        lx = getGamepadAxisMovement( 0, GAMEPAD_AXIS_LEFT_X );
        ly = getGamepadAxisMovement( 0, GAMEPAD_AXIS_LEFT_Y );
        rx = getGamepadAxisMovement( 0, GAMEPAD_AXIS_RIGHT_X );
        ry = getGamepadAxisMovement( 0, GAMEPAD_AXIS_RIGHT_Y );
        leftTriggerPressure = getGamepadAxisMovement( 0, GAMEPAD_AXIS_LEFT_TRIGGER );
        rightTriggerPressure = getGamepadAxisMovement( 0, GAMEPAD_AXIS_RIGHT_TRIGGER );
        
    }
    
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
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new ControllerTest();
    }
    
    
    
    //**************************************************************************
    // Constantes para botões dos gamepads.
    //**************************************************************************
    
    /** Botão desconhecido (para tratamento de erros apenas). */
    public static final int GAMEPAD_BUTTON_UNKNOWN = -1;
    
    /** Botão para baixo do pad digital (dpad) esquerdo */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_DOWN    = 10;
    
    /** Botão para direita do pad digital (dpad) esquerdo */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_RIGHT   = 11;
    
    /** Botão para esquerda do pad digital (dpad) esquerdo */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_LEFT    = 12;
    
    /** Botão para cima do pad digital (dpad) esquerdo */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_UP      = 13;
    
    /** Botão para baixo dos botões da direita. PS: X / Xbox: A */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_DOWN   = 0;
    
    /** Botão para direita dos botões da direita. PS: Círculo / Xbox: B */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_RIGHT  = 1;
    
    /** Botão para esquerda dos botões da direita. PS: Quadrado / Xbox: X */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_LEFT   = 2;
    
    /** Botão para cima dos botões da direita. PS: Triângulo / Xbox: Y */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_UP     = 3;
    
    /** Gatilho de cima da esquerda. PS: L1 / Xbox: LB */
    public static final int GAMEPAD_BUTTON_LEFT_TRIGGER_1    = 4;
    
    /** Gatilho de baixo da esquerda. PS: L2 / Xbox: LT */
    public static final int GAMEPAD_BUTTON_LEFT_TRIGGER_2    = 44;
    
    /** Gatilho de cima da direita. PS: R1 / Xbox: RB */
    public static final int GAMEPAD_BUTTON_RIGHT_TRIGGER_1   = 5;
    
    /** Gatilho de baixo da direita. PS: R2 / Xbox: RT */
    public static final int GAMEPAD_BUTTON_RIGHT_TRIGGER_2   = 55;
    
    /** Botão da esquerda do centro. "Select". */
    public static final int GAMEPAD_BUTTON_MIDDLE_LEFT       = 6;
    
    /** Botão da direita do centro. "Start". */
    public static final int GAMEPAD_BUTTON_MIDDLE_RIGHT      = 7;
    
    /** Botão do analógico esquerdo. */
    public static final int GAMEPAD_BUTTON_LEFT_THUMB        = 8;
    
    /** Botão do analógico direito. */
    public static final int GAMEPAD_BUTTON_RIGHT_THUMB       = 9;
    
    //**************************************************************************
    // Constantes para os eixos dos gamepads.
    //**************************************************************************
    
    /** Eixo x do analógico esquerdo. */
    public static final int GAMEPAD_AXIS_LEFT_X              = 0;
    
    /** Eixo y do analógico esquerdo. */
    public static final int GAMEPAD_AXIS_LEFT_Y              = 1;
    
    /** Eixo x do analógico direito. */
    public static final int GAMEPAD_AXIS_RIGHT_X             = 2;
    
    /** Eixo y do analógico direito. */
    public static final int GAMEPAD_AXIS_RIGHT_Y             = 3;
    
    /** Nível de pressão do gatilho esquerdo. Varia de [0..1]. */
    public static final int GAMEPAD_AXIS_LEFT_TRIGGER        = 4;
    
    /** Nível de pressão do gatilho esquerdo. Varia de [0..1]. */
    public static final int GAMEPAD_AXIS_RIGHT_TRIGGER       = 5;
    
}
