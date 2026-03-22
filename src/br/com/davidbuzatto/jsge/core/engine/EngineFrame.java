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
package br.com.davidbuzatto.jsge.core.engine;

import br.com.davidbuzatto.jsge.collision.aabb.AABB;
import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.utils.TraceLogUtils;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.core.utils.StrokeUtils;
import br.com.davidbuzatto.jsge.font.FontUtils;
import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Path;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Star;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.image.ImageUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.sound.Music;
import br.com.davidbuzatto.jsge.sound.Sound;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogManager;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 * Simple engine for creating games or simulations using Java 2D.
 * Much of its API is based on the Raylib game engine (www.raylib.com).
 *
 * @author Prof. Dr. David Buzatto
 */
public abstract class EngineFrame extends JFrame {

    /**
     * Drawing panel where all drawing operations and event registrations
     * will occur.
     */
    private DrawingPanel drawingPanel;

    /**
     * Reference to the current graphics context of the drawing panel.
     */
    private Graphics2D g2d;

    /**
     * Stack of graphics contexts.
     */
    private Deque<Graphics2D> g2dStack;

    /**
     * Default font.
     */
    private Font defaultFont;

    /**
     * Default font for drawing the FPS counter.
     */
    private Font defaultFPSFont;

    /**
     * Default stroke.
     */
    private BasicStroke defaultStroke;

    /**
     * Time before starting the update and drawing processes.
     */
    private long timeBefore;

    /**
     * Time after completing the update and drawing processes.
     */
    private long timeAfter;

    /**
     * Time to wait before starting the next cycle.
     */
    private long waitTime;

    /**
     * Duration of one frame.
     */
    private long frameTime;

    /**
     * Target frames per second.
     */
    private int targetFPS;

    /**
     * Current frames per second.
     */
    private int currentFPS;

    /**
     * Expected wait time based on the target frames per second.
     */
    private long waitTimeFPS;

    /**
     * Start time of game/simulation execution.
     */
    private long startTime;

    /**
     * Flag indicating whether antialiasing is used for the graphics context.
     */
    private boolean antialiasing;

    /**
     * Exit key code.
     */
    private int exitKeyCode;
    
    /**
     * Flag for controlling the drawing thread execution.
     */
    private boolean running;

    /**
     * Input manager.
     */
    private InputManager inputManager;

    // default mouse actions
    /** Action for the left mouse button. */
    private GameAction mouseLeftAction;
    /** Action for the left mouse button (initial press). */
    private GameAction mouseLeftActionInitial;

    /** Action for the middle mouse button. */
    private GameAction mouseMiddleAction;
    /** Action for the middle mouse button (initial press). */
    private GameAction mouseMiddleActionInitial;

    /** Action for the right mouse button. */
    private GameAction mouseRightAction;
    /** Action for the right mouse button (initial press). */
    private GameAction mouseRightActionInitial;

    /** Action for scrolling up with the mouse wheel. */
    private GameAction mouseWheelUpAction;
    /** Action for scrolling down with the mouse wheel. */
    private GameAction mouseWheelDownAction;

    /** Scroll-up value. */
    private double mouseWheelUpValue;
    /** Scroll-down value. */
    private double mouseWheelDownValue;

    /** Indicates whether the left mouse button was processed this cycle. */
    private boolean mouseButtonLeftProcessed;
    /** Indicates whether the middle mouse button was processed this cycle. */
    private boolean mouseButtonMiddleProcessed;
    /** Indicates whether the right mouse button was processed this cycle. */
    private boolean mouseButtonRightProcessed;

    /** Indicates whether the left mouse button was pressed. */
    private boolean mouseLeftPressed;
    /** Indicates whether the left mouse button was released. */
    private boolean mouseLeftReleased;
    /** Indicates whether the left mouse button is held down. */
    private boolean mouseLeftDown;
    /** Indicates whether the left mouse button is not pressed. */
    private boolean mouseLeftUp;

    /** Indicates whether the middle mouse button was pressed. */
    private boolean mouseMiddlePressed;
    /** Indicates whether the middle mouse button was released. */
    private boolean mouseMiddleReleased;
    /** Indicates whether the middle mouse button is held down. */
    private boolean mouseMiddleDown;
    /** Indicates whether the middle mouse button is not pressed. */
    private boolean mouseMiddleUp;

    /** Indicates whether the right mouse button was pressed. */
    private boolean mouseRightPressed;
    /** Indicates whether the right mouse button was released. */
    private boolean mouseRightReleased;
    /** Indicates whether the right mouse button is held down. */
    private boolean mouseRightDown;
    /** Indicates whether the right mouse button is not pressed. */
    private boolean mouseRightUp;

    // keyboard maps

    /** Key processing map. */
    private Map<Integer, Boolean> keysProcessedMap;
    /** Pressed keys processing map. */
    private Map<Integer, Boolean> keysPressedMap;
    /** Released keys processing map. */
    private Map<Integer, Boolean> keysReleasedMap;
    /** Held-down keys processing map. */
    private Map<Integer, Boolean> keysDownMap;
    /** Not-pressed keys processing map. */
    private Map<Integer, Boolean> keysUpMap;

    /** Stores the character of the last key pressed. */
    private int lastPressedChar = KEY_NULL;

    /** Stores the code of the last key pressed. */
    private int lastPressedKeyCode = KEY_NULL;

    /**
     * Gamepad input manager.
     */
    private GamepadInputManager gpInputManager;

    /** The current cursor. */
    private Cursor currentCursor;
    
    /**
     * An invisible cursor.
     */
    public static final Cursor INVISIBLE_CURSOR =
            Toolkit.getDefaultToolkit().createCustomCursor(
                Toolkit.getDefaultToolkit().getImage( "" ),
                new java.awt.Point( 0, 0 ),
                "invisible"
            );
    
    /** Flag indicating whether the engine is in 2D mode (camera). */
    private boolean mode2DActive = false;

    /** The transformed graphics context for 2D mode. */
    private Graphics2D cameraGraphics;

    /** The original graphics context for the current cycle. */
    private Graphics2D baseGraphics;
        
    /**
     * Processes the initial input provided by the user and creates
     * and/or initializes the game or simulation objects/contexts/variables.
     *
     * Executed only ONCE.
     */
    public abstract void create();

    /**
     * Updates the game or simulation objects/contexts/variables.
     *
     * Executed once per frame, always before the drawing method.
     *
     * @param delta The time variation, in seconds, from one frame to the next.
     */
    public abstract void update( double delta );

    /**
     * Draws the state of the game or simulation objects/contexts/variables.
     *
     * Executed once per frame, always after the update method.
     */
    public abstract void draw();

    /**
     * Creates an instance of the engine and starts its execution.
     *
     * @param windowWidth Window width.
     * @param windowHeight Window height.
     * @param windowTitle Window title.
     * @param targetFPS The maximum number of frames per second the update and drawing process should maintain.
     * @param antialiasing Flag indicating whether to use antialiasing for drawing in the graphics context.
     * @param resizable Flag indicating whether the window is resizable.
     * @param fullScreen Flag indicating whether the window should run in exclusive fullscreen mode.
     * @param undecorated Flag indicating whether the window should be undecorated.
     * @param alwaysOnTop Flag indicating whether the window is always on top.
     * @param invisibleBackground Flag indicating whether the window background is invisible. The window must be undecorated.
     */
    public EngineFrame( int windowWidth, 
                   int windowHeight, 
                   String windowTitle, 
                   int targetFPS, 
                   boolean antialiasing,
                   boolean resizable, 
                   boolean fullScreen, 
                   boolean undecorated, 
                   boolean alwaysOnTop,
                   boolean invisibleBackground ) {
        
        // disables the JDK logging system used by the sound processing library
        LogManager.getLogManager().reset();
        
        if ( windowWidth <= 0 ) {
            throw new IllegalArgumentException( "width must be positive!" );
        }

        if ( windowHeight <= 0 ) {
            throw new IllegalArgumentException( "height must be positive!" );
        }

        startTime = System.currentTimeMillis();
        setTargetFPS( targetFPS );

        defaultFont = FontUtils.DEFAULT_FONT;
        defaultFPSFont = FontUtils.DEFAULT_FPS_FONT;
        defaultStroke = new BasicStroke( 1 );

        this.antialiasing = antialiasing;
        waitTimeFPS = (long) ( 1000.0 / this.targetFPS );   // expected duration per frame
        
        // creates and configures the drawing panel
        drawingPanel = new DrawingPanel( undecorated && invisibleBackground );
        drawingPanel.setPreferredSize( new Dimension( windowWidth, windowHeight ) );
        drawingPanel.setFocusable( true );
        drawingPanel.addKeyListener( new KeyAdapter(){
            @Override
            public void keyPressed( KeyEvent e ) {
                if ( e.getKeyCode() == exitKeyCode ) {
                    switch ( getDefaultCloseOperation() ) {
                        case HIDE_ON_CLOSE:
                            setVisible( false );
                            break;
                        case DISPOSE_ON_CLOSE:
                            dispose();
                            break;
                        case EXIT_ON_CLOSE:
                            System.exit( 0 );
                            break;
                        case DO_NOTHING_ON_CLOSE:
                        default:
                            break;
                    }
                }
            }
        });
        exitKeyCode = KEY_ESCAPE;
        currentCursor = getCursor();
        
        g2dStack = new ArrayDeque<>();
        
        prepareInputManager();
        gpInputManager = new GamepadInputManager();

        // configures the engine
        setTitle( windowTitle );
        setAlwaysOnTop( alwaysOnTop );
        setIgnoreRepaint( true );

        if ( fullScreen ) {
            setResizable( false );
            setUndecorated( true );
            setExtendedState( MAXIMIZED_BOTH );
        } else {
            setResizable( resizable );
            setUndecorated( undecorated );
        }
        
        if ( undecorated && invisibleBackground ) {
            setBackground( new Color( 0, 0, 0, 1 ) );
        }
        
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        add( drawingPanel, BorderLayout.CENTER );

        if ( !fullScreen ) {
            pack();
        }

        setLocationRelativeTo( null );

        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
                running = false;
            }
        });

        // initializes the current game objects/context/variables
        try {
            create();
        } catch ( RuntimeException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }

        // starts the game or simulation execution process
        running = true;
        setVisible( true );
        start();

    }

    /**
     * Creates an instance of the engine and starts its execution.
     *
     * @param windowWidth Window width.
     * @param windowHeight Window height.
     * @param windowTitle Window title.
     * @param targetFPS The maximum number of frames per second the update and drawing process should maintain.
     * @param antialiasing Flag indicating whether to use antialiasing for drawing in the graphics context.
     */
    public EngineFrame( int windowWidth, 
                   int windowHeight, 
                   String windowTitle, 
                   int targetFPS, 
                   boolean antialiasing ) {
        this( windowWidth, windowHeight, windowTitle, targetFPS, antialiasing, false, false, false, false, false );
    }
    
    private void start() {

        new Thread( () -> {

            while ( running ) {

                timeBefore = System.currentTimeMillis();
                
                gpInputManager.prepareToNextCycle();
                mouseWheelUpValue = mouseWheelUpAction.getAmount();
                mouseWheelDownValue = mouseWheelDownAction.getAmount();
                
                try {
                    update( frameTime / 1000.0 ); // getFrameTime();
                } catch ( RuntimeException exc ) {
                    traceLogError( CoreUtils.stackTraceToString( exc ) );
                }
                
                inputManager.consumeKeyActions();
                resetMouseButtonsState();
                resetKeysState();
                
                try {
                    SwingUtilities.invokeAndWait( () -> {
                        drawingPanel.repaint();
                    });
                } catch ( InterruptedException | InvocationTargetException exc ) {
                    traceLogError( CoreUtils.stackTraceToString( exc ) );
                }

                timeAfter = System.currentTimeMillis();

                // how long did a frame take?
                frameTime = timeAfter - timeBefore;

                // how long should we wait?
                waitTime = waitTimeFPS - frameTime;

                //traceLogInfo( "%d %d %d %d", timeBefore, timeAfter, frameTime, waitTime );

                // if the wait time is negative, it means there was not
                // enough time, based on the expected time
                // for the entire frame to be updated and drawn
                if ( waitTime < 0 ) {
                    waitTime = 0;      // no wait
                }

                // if the frame time is less than the wait time,
                // it means there was time left to execute the frame, i.e.
                // the frame was updated and drawn in less time than
                // expected based on the target frames per second
                if ( frameTime < waitTime ) {
                    frameTime = waitTime;  // the time the frame took to execute
                }

                int localFPS = (int) ( Math.round( 1000.0 / frameTime / 10.0 ) ) * 10;

                if ( localFPS > targetFPS ) {
                    localFPS = targetFPS;
                }

                if ( localFPS >= 0 ) {
                    currentFPS = localFPS;
                }

                try {
                    Thread.yield();
                    Thread.sleep( waitTime );
                } catch ( InterruptedException exc ) {
                    traceLogError( CoreUtils.stackTraceToString( exc ) );
                }

            }

        }).start();

    }

    private void prepareInputManager() {

        inputManager = new InputManager( drawingPanel );

        mouseLeftAction = new GameAction( "mouse button left" );
        mouseLeftActionInitial = new GameAction( "mouse button left initial", true );
        mouseMiddleAction = new GameAction( "mouse button center" );
        mouseMiddleActionInitial = new GameAction( "mouse button center initial", true );
        mouseRightAction = new GameAction( "mouse button right" );
        mouseRightActionInitial = new GameAction( "mouse button right initial", true );

        mouseWheelUpAction = new GameAction( "mouse wheel up", true );
        mouseWheelDownAction = new GameAction( "mouse wheel down", true );

        inputManager.mapToMouse( mouseLeftAction, MOUSE_BUTTON_LEFT );
        inputManager.mapToMouse( mouseLeftActionInitial, MOUSE_BUTTON_LEFT );
        inputManager.mapToMouse( mouseMiddleAction, MOUSE_BUTTON_MIDDLE );
        inputManager.mapToMouse( mouseMiddleActionInitial, MOUSE_BUTTON_MIDDLE );
        inputManager.mapToMouse( mouseRightAction, MOUSE_BUTTON_RIGHT );
        inputManager.mapToMouse( mouseRightActionInitial, MOUSE_BUTTON_RIGHT );

        inputManager.mapToMouse( mouseWheelUpAction, InputManager.MOUSE_WHEEL_UP );
        inputManager.mapToMouse( mouseWheelDownAction, InputManager.MOUSE_WHEEL_DOWN );

        registerAllKeys();
        
        keysProcessedMap = new HashMap<>();
        keysPressedMap = new HashMap<>();
        keysReleasedMap = new HashMap<>();
        keysDownMap = new HashMap<>();
        keysUpMap = new HashMap<>();

    }
    
    private void resetMouseButtonsState() {
        mouseButtonLeftProcessed = false;
        mouseButtonMiddleProcessed = false;
        mouseButtonRightProcessed = false;
        mouseLeftActionInitial.isPressed();
        mouseLeftAction.isPressed();
        mouseMiddleActionInitial.isPressed();
        mouseMiddleAction.isPressed();
        mouseRightActionInitial.isPressed();
        mouseRightAction.isPressed();
    }
    
    private void processMouseButtonsState( int button ) {
        switch ( button ) {
            case MOUSE_BUTTON_LEFT:
                if ( !mouseButtonLeftProcessed ) {
                    mouseLeftPressed = mouseLeftActionInitial.isPressed();
                    mouseLeftReleased = mouseLeftAction.isPressed() && mouseLeftAction.getAmount() == 0;
                    mouseLeftDown = mouseLeftAction.isPressed();
                    mouseLeftUp = !mouseLeftDown;
                    mouseButtonLeftProcessed = true;
                }
                break;    
            case MOUSE_BUTTON_MIDDLE:
                if ( !mouseButtonMiddleProcessed ) {
                    mouseMiddlePressed = mouseMiddleActionInitial.isPressed();
                    mouseMiddleReleased = mouseMiddleAction.isPressed() && mouseMiddleAction.getAmount() == 0;
                    mouseMiddleDown = mouseMiddleAction.isPressed();
                    mouseMiddleUp = !mouseMiddleDown;
                    mouseButtonMiddleProcessed = true;
                }
                break;    
            case MOUSE_BUTTON_RIGHT:
                if ( !mouseButtonRightProcessed ) {
                    mouseRightPressed = mouseRightActionInitial.isPressed();
                    mouseRightReleased = mouseRightAction.isPressed() && mouseRightAction.getAmount() == 0;
                    mouseRightDown = mouseRightAction.isPressed();
                    mouseRightUp = !mouseRightDown;
                    mouseButtonRightProcessed = true;
                }
                break;    
        }
    }
    
    
    
    //**************************************************************************
    // Mouse handling.
    //**************************************************************************

    /**
     * Returns whether a mouse button was pressed once.
     *
     * @param button The integer identifying the desired mouse button.
     * @return True if the button was pressed once, false otherwise.
     */
    public boolean isMouseButtonPressed( int button ) {
        processMouseButtonsState( button );
        switch ( button ) {
            case MOUSE_BUTTON_LEFT: return mouseLeftPressed;
            case MOUSE_BUTTON_MIDDLE: return mouseMiddlePressed;
            case MOUSE_BUTTON_RIGHT: return mouseRightPressed;
        }
        return false;
    }
    /*public boolean isMouseButtonPressed( int button ) {
        switch ( button ) {
            case MOUSE_BUTTON_LEFT:
                return mouseLeftActionInitial.isPressed();
            case MOUSE_BUTTON_MIDDLE:
                return mouseMiddleActionInitial.isPressed();
            case MOUSE_BUTTON_RIGHT:
                return mouseRightActionInitial.isPressed();
        }
        return false;
    }*/

    /**
     * Returns whether a mouse button was released.
     *
     * @param button The integer identifying the desired mouse button.
     * @return True if the button was released, false otherwise.
     */
    public boolean isMouseButtonReleased( int button ) {
        processMouseButtonsState( button );
        switch ( button ) {
            case MOUSE_BUTTON_LEFT: return mouseLeftReleased;
            case MOUSE_BUTTON_MIDDLE: return mouseMiddleReleased;
            case MOUSE_BUTTON_RIGHT: return mouseRightReleased;
        }
        return false;
    }
    /*public boolean isMouseButtonReleased( int button ) {
        switch ( button ) {
            case MOUSE_BUTTON_LEFT:
                return mouseLeftAction.isPressed() && mouseLeftAction.getAmount() == 0;
            case MOUSE_BUTTON_MIDDLE:
                return mouseMiddleAction.isPressed() && mouseMiddleAction.getAmount() == 0;
            case MOUSE_BUTTON_RIGHT:
                return mouseRightAction.isPressed() && mouseRightAction.getAmount() == 0;
        }
        return false;
    }*/

    /**
     * Returns whether a mouse button is held down.
     *
     * @param button The integer identifying the desired mouse button.
     * @return True if the button is held down, false otherwise.
     */
    public boolean isMouseButtonDown( int button ) {
        processMouseButtonsState( button );
        switch ( button ) {
            case MOUSE_BUTTON_LEFT: return mouseLeftDown;
            case MOUSE_BUTTON_MIDDLE: return mouseMiddleDown;
            case MOUSE_BUTTON_RIGHT: return mouseRightDown;
        }
        return false;
    }
    /*public boolean isMouseButtonDown( int button ) {
        switch ( button ) {
            case MOUSE_BUTTON_LEFT:
                return mouseLeftAction.isPressed();
            case MOUSE_BUTTON_MIDDLE:
                return mouseMiddleAction.isPressed();
            case MOUSE_BUTTON_RIGHT:
                return mouseRightAction.isPressed();
        }
        return false;
    }*/
    
    /**
     * Returns whether a mouse button is not pressed.
     *
     * @param button The integer identifying the desired mouse button.
     * @return True if the button is not pressed, false otherwise.
     */
    public boolean isMouseButtonUp( int button ) {
        processMouseButtonsState( button );
        switch ( button ) {
            case MOUSE_BUTTON_LEFT: return mouseLeftUp;
            case MOUSE_BUTTON_MIDDLE: return mouseMiddleUp;
            case MOUSE_BUTTON_RIGHT: return mouseRightUp;
        }
        return false;
    }
    /*public boolean isMouseButtonUp( int button ) {
        return !isMouseButtonDown( button );
    }*/

    /**
     * Gets the x position of the mouse.
     *
     * @return The x position of the mouse.
     */
    public int getMouseX() {
        return inputManager.getMouseX();
    }
    
    /**
     * Gets the y position of the mouse.
     *
     * @return The y position of the mouse.
     */
    public int getMouseY() {
        return inputManager.getMouseY();
    }

    /**
     * Gets the mouse position as a point.
     *
     * @return The mouse position as a point.
     */
    public Vector2 getMousePositionPoint() {
        return new Vector2( inputManager.getMouseX(), inputManager.getMouseY() );
    }

    /**
     * Gets the movement of the mouse scroll wheel.
     * Positive for up, negative for down, and zero for stationary.
     *
     * @return The mouse scroll wheel movement.
     */
    public double getMouseWheelMove() {
        //double mouseWheelUpValue = mouseWheelUpAction.getAmount();
        //double mouseWheelDownValue = mouseWheelDownAction.getAmount();
        return mouseWheelUpValue >= mouseWheelDownValue ? mouseWheelUpValue : -mouseWheelDownValue;
    }

    /**
     * Gets a point with the mouse scroll wheel movement.
     * x contains the scroll-up movement and y contains the scroll-down movement.
     *
     * @return A point with the mouse scroll wheel movement.
     */
    public Vector2 getMouseWheelMoveVector() {
        double vUp = mouseWheelUpAction.getAmount();
        double vDown = mouseWheelDownAction.getAmount();
        return new Vector2( vUp, vDown );
    }



    //**************************************************************************
    // Keyboard handling.
    //**************************************************************************

    /**
     * Sets the exit key. By default it is the &lt;ESC&gt; key.
     *
     * @param keyCode The key code.
     */
    public void setExitKey( int keyCode ) {
        exitKeyCode = keyCode;
    }
    
    /**
     * Registers a key code to be "listened to".
     *
     * @param keyCode The desired key code.
     */
    @SuppressWarnings( "unused" )
    private void registerKey( int keyCode ) {
        inputManager.mapToKey( new GameAction( "key " + keyCode ), keyCode );
        inputManager.mapToKey( new GameAction( "key " + keyCode, true ), keyCode );
    }

    /**
     * Registers all keys configured as constants.
     */
    private void registerAllKeys() {

        try {

            Class<? extends EngineFrame> klass = EngineFrame.class;
            
            for ( Field f : klass.getDeclaredFields() ) {
                if ( Modifier.isStatic( f.getModifiers() ) ) {
                    if ( f.getName().startsWith( "KEY_" ) ) {
                        int keyCode = f.getInt( null );
                        inputManager.mapToKey( new GameAction( "key " + keyCode ), keyCode );
                        inputManager.mapToKey( new GameAction( "key " + keyCode, true ), keyCode );
                    }
                }
            }

        } catch ( IllegalAccessException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }

    }
    
    private void resetKeysState() {
        keysProcessedMap.clear();
    }
    
    private void processKeysState( int keyCode ) {
        
        if ( !keysProcessedMap.containsKey( keyCode ) ) {
            
            List<GameAction> keyActions = inputManager.getKeyActions( keyCode );
            keysProcessedMap.put( keyCode, true );
        
            if ( keyActions != null ) {
                for ( GameAction ga : keyActions ) {
                    if ( ga.isInitialPressOnly() ) {
                        keysPressedMap.put( keyCode, ga.isPressed() );
                    } else {
                        boolean down = ga.isPressed();
                        keysReleasedMap.put( keyCode, down && ga.getAmount() == 0 );
                        keysDownMap.put( keyCode, down );
                        keysUpMap.put( keyCode, !down );
                    }
                }
            }
            
        }
        
    }

    /**
     * Returns whether a key was pressed once.
     *
     * @param keyCode The integer identifying the desired key.
     * @return True if the key was pressed once, false otherwise.
     */
    public boolean isKeyPressed( int keyCode ) {
        processKeysState( keyCode );
        return keysPressedMap.getOrDefault( keyCode, false );
    }
    /*public boolean isKeyPressed( int keyCode ) {

        List<GameAction> keyActions = inputManager.getKeyActions( keyCode );
        
        if ( keyActions != null ) {
            for ( GameAction ga : keyActions ) {
                if ( ga.isInitialPressOnly() ) {
                    return ga.isPressed();
                }
            }
        }

        return false;

    }*/

    /**
     * Returns whether a key was released.
     *
     * @param keyCode The integer identifying the desired key.
     * @return True if the key was released, false otherwise.
     */
    public boolean isKeyReleased( int keyCode ) {
        processKeysState( keyCode );
        return keysReleasedMap.getOrDefault( keyCode, false );
    }
    /*public boolean isKeyReleased( int keyCode ) {

        List<GameAction> keyActions = inputManager.getKeyActions( keyCode );
        
        if ( keyActions != null ) {
            for ( GameAction ga : keyActions ) {
                if ( !ga.isInitialPressOnly() ) {
                    return ga.isPressed() && ga.getAmount() == 0;
                }
            }
        }

        return false;

    }*/

    /**
     * Returns whether a key is held down.
     *
     * @param keyCode The integer identifying the desired key.
     * @return True if the key is held down, false otherwise.
     */
    public boolean isKeyDown( int keyCode ) {
        processKeysState( keyCode );
        return keysDownMap.getOrDefault( keyCode, false );
    }
    /*public boolean isKeyDown( int keyCode ) {
        
        List<GameAction> keyActions = inputManager.getKeyActions( keyCode );
        
        if ( keyActions != null ) {
            for ( GameAction ga : keyActions ) {
                if ( !ga.isInitialPressOnly() ) {
                    return ga.isPressed();
                }
            }
        }

        return false;

    }*/
    
    /**
     * Returns whether a key is not pressed.
     *
     * @param keyCode The integer identifying the desired key.
     * @return True if the key is not pressed, false otherwise.
     */
    public boolean isKeyUp( int keyCode ) {
        processKeysState( keyCode );
        return keysUpMap.getOrDefault( keyCode, false );
    }
    /*public boolean isKeyUp( int keyCode ) {
        return !isKeyDown( keyCode );
    }*/
    
    /**
     * Returns a set of the currently pressed key codes.
     *
     * @return A set of pressed key codes.
     */
    public Set<Integer> getKeysPressed() {
        return inputManager.getKeysFromPressedActions();
    }
    
    /**
     * Returns the code of the pressed key.
     *
     * @return The code of the pressed key, or KEY_NULL if no key was pressed.
     */
    public int getKeyPressed() {
        
        Set<Integer> s = inputManager.getKeysFromPressedActions();
        
        if ( s.isEmpty() ) {
            return KEY_NULL;
        }
        
        int key = s.iterator().next();
        
        if ( key != lastPressedKeyCode ) {
            lastPressedKeyCode = key;
            return key;
        }
        
        return KEY_NULL;
        
    }
    
    /**
     * Returns the character of the pressed key.
     *
     * @return The character of the pressed key, or KEY_NULL if no key was pressed.
     */
    public char getCharPressed() {
        
        Set<Integer> s = inputManager.getKeysFromPressedActions();
        
        if ( s.isEmpty() ) {
            return KEY_NULL;
        }
        
        int key = s.iterator().next();
        
        if ( key != lastPressedChar ) {
            lastPressedChar = key;
            return (char) key;
        }
        
        return KEY_NULL;
        
    }


    
    //**************************************************************************
    // Drawing methods
    //**************************************************************************

    /**
     * Draws a pixel.
     *
     * @param x X coordinate of the pixel.
     * @param y Y coordinate of the pixel.
     * @param paint Paint for drawing.
     */
    public void drawPixel( double x, double y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( x, y, x, y ) );
    }

    /**
     * Draws a pixel.
     *
     * @param point The pixel point.
     * @param paint Paint for drawing.
     */
    public void drawPixel( Vector2 point, Paint paint ) {
        drawPixel( point.x, point.y, paint );
    }

    /**
     * Draws a line.
     *
     * @param startX X coordinate of the start point.
     * @param startY Y coordinate of the start point.
     * @param endX X coordinate of the end point.
     * @param endY Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void drawLine( double startX, double startY, double endX, double endY, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( startX, startY, endX, endY ) );
    }

    /**
     * Draws a line.
     *
     * @param start Start point.
     * @param end End point.
     * @param paint Paint for drawing.
     */
    public void drawLine( Vector2 start, Vector2 end, Paint paint ) {
        drawLine( start.x, start.y, end.x, end.y, paint );
    }

    /**
     * Draws a line.
     *
     * @param line a line.
     * @param paint Paint for drawing.
     */
    public void drawLine( Line line, Paint paint ) {
        drawLine( line.x1, line.y1, line.x2, line.y2, paint );
    }

    /**
     * Draws a rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void drawRectangle( double x, double y, double width, double height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Draws a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, paint );
    }
    
    /**
     * Draws a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, Vector2 dim, Paint paint ) {
        drawRectangle( pos.x, pos.y, dim.x, dim.y, paint );
    }

    /**
     * Draws a rectangle.
     *
     * @param rectangle A rectangle.
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Rectangle rectangle, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }

    /**
     * Fills a rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void fillRectangle( double x, double y, double width, double height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Fills a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, paint );
    }
    
    /**
     * Fills a rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, Vector2 dim, Paint paint ) {
        fillRectangle( pos.x, pos.y, dim.x, dim.y, paint );
    }

    /**
     * Fills a rectangle.
     *
     * @param rectangle A rectangle.
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Rectangle rectangle, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }

    /**
     * Draws a rotated rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setPaint( paint );

        gc.rotate( Math.toRadians( rotation ), originX, originY );
        gc.draw( new Rectangle2D.Double( x, y, width, height ) );

        gc.dispose();

    }

    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, double width, double height, double originX, double originY, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, originX, originY, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, Vector2 dim, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, dim.x, dim.y, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Vector2 pos, Vector2 dim, double originX, double originY, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, dim.x, dim.y, originX, originY, rotation, paint );
    }

    /**
     * Draws a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Draws a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRectangle( Rectangle rectangle, double originX, double originY, double rotation, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, originX, originY, rotation, paint );
    }

    /**
     * Fills a rotated rectangle.
     *
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setPaint( paint );

        gc.rotate( Math.toRadians( rotation ), originX, originY );
        gc.fill( new Rectangle2D.Double( x, y, width, height ) );

        gc.dispose();

    }

    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param width Width.
     * @param height Height.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, double width, double height, double originX, double originY, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, originX, originY, rotation, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, Vector2 dim, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, dim.x, dim.y, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param pos Upper-left vertex.
     * @param dim Dimensions (x: width, y: height).
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Vector2 pos, Vector2 dim, double originX, double originY, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, dim.x, dim.y, originX, originY, rotation, paint );
    }

    /**
     * Fills a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param origin Rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }
    
    /**
     * Fills a rotated rectangle.
     *
     * @param rectangle A rectangle.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRectangle( Rectangle rectangle, double originX, double originY, double rotation, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, originX, originY, rotation, paint );
    }
    
    /**
     * Draws an AABB.
     * 
     * @param aabb An AABB.
     * @param paint Paint for drawing.
     */
    public void drawAABB( AABB aabb, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Rectangle2D.Double( aabb.x1, aabb.y1, aabb.width, aabb.height ) );
    }
    
    /**
     * Fills an AABB.
     * 
     * @param aabb An AABB.
     * @param paint Paint for drawing.
     */
    public void fillAABB( AABB aabb, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new Rectangle2D.Double( aabb.x1, aabb.y1, aabb.width, aabb.height ) );
    }

    /**
     * Draws a rounded rectangle.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void drawRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
    }

    /**
     * Draws a rounded rectangle.
     * 
     * @param pos Upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void drawRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }
    
    /**
     * Draws a rounded rectangle.
     * 
     * @param pos Upper-left vertex of the rectangle.
     * @param dim Dimensions (x: width, y: height).
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void drawRoundRectangle( Vector2 pos, Vector2 dim, double roundness, Paint paint ) {
        drawRoundRectangle( pos.x, pos.y, dim.x, dim.y, roundness, paint );
    }

    /**
     * Draws a rounded rectangle.
     * 
     * @param roundRectangle A rounded rectangle.
     * @param paint Paint for drawing.
     */
    public void drawRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        drawRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Fills a rounded rectangle.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void fillRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
    }

    /**
     * Fills a rounded rectangle.
     * 
     * @param pos Upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void fillRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }
    
    /**
     * Fills a rounded rectangle.
     * 
     * @param pos Upper-left vertex of the rectangle.
     * @param dim Dimensions (x: width, y: height).
     * @param roundness Corner roundness.
     * @param paint Paint for drawing.
     */
    public void fillRoundRectangle( Vector2 pos, Vector2 dim, double roundness, Paint paint ) {
        fillRoundRectangle( pos.x, pos.y, dim.x, dim.y, roundness, paint );
    }

    /**
     * Fills a rounded rectangle.
     * 
     * @param roundRectangle A rounded rectangle.
     * @param paint Paint for drawing.
     */
    public void fillRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        fillRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Draws a circle.
     * 
     * @param x X coordinate of the circle center.
     * @param y Y coordinate of the circle center.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void drawCircle( double x, double y, double radius, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Draws a circle.
     * 
     * @param center Circle center.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void drawCircle( Vector2 center, double radius, Paint paint ) {
        drawCircle( center.x, center.y, radius, paint );
    }

    /**
     * Draws a circle.
     * 
     * @param circle A circle.
     * @param paint Paint for drawing.
     */
    public void drawCircle( Circle circle, Paint paint ) {
        drawCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Fills a circle.
     * 
     * @param x X coordinate of the circle center.
     * @param y Y coordinate of the circle center.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void fillCircle( double x, double y, double radius, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Fills a circle.
     * 
     * @param center Circle center.
     * @param radius Radius.
     * @param paint Paint for drawing.
     */
    public void fillCircle( Vector2 center, double radius, Paint paint ) {
        fillCircle( center.x, center.y, radius, paint );
    }

    /**
     * Fills a circle.
     * 
     * @param circle A circle.
     * @param paint Paint for drawing.
     */
    public void fillCircle( Circle circle, Paint paint ) {
        fillCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Draws an ellipse.
     * 
     * @param x X coordinate of the ellipse center.
     * @param y Y coordinate of the ellipse center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void drawEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Draws an ellipse.
     * 
     * @param center Ellipse center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void drawEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Draws an ellipse.
     * 
     * @param ellipse An ellipse.
     * @param paint Paint for drawing.
     */
    public void drawEllipse( Ellipse ellipse, Paint paint ) {
        drawEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Fills an ellipse.
     * 
     * @param x X coordinate of the ellipse center.
     * @param y Y coordinate of the ellipse center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void fillEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Fills an ellipse.
     * 
     * @param center Ellipse center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param paint Paint for drawing.
     */
    public void fillEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Fills an ellipse.
     * 
     * @param ellipse An ellipse.
     * @param paint Paint for drawing.
     */
    public void fillEllipse( Ellipse ellipse, Paint paint ) {
        fillEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Draws a circle sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, -startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Draws a circle sector.
     * 
     * @param center Circle sector center.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Draws a circle sector.
     * 
     * @param circle A circle.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Draws a circle sector.
     * 
     * @param circleSector A circle sector.
     * @param paint Paint for drawing.
     */
    public void drawCircleSector( CircleSector circleSector, Paint paint ) {
        drawCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Fills a circle sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, -startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Fills a circle sector.
     * 
     * @param center Circle sector center.
     * @param radius Radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Fills a circle sector.
     * 
     * @param circle A circle.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Fills a circle sector.
     * 
     * @param circleSector A circle sector.
     * @param paint Paint for drawing.
     */
    public void fillCircleSector( CircleSector circleSector, Paint paint ) {
        fillCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param center Ellipse center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param ellipse An ellipse.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Draws an ellipse sector.
     * 
     * @param ellipseSector An ellipse sector.
     * @param paint Paint for drawing.
     */
    public void drawEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        drawEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param center Ellipse center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param ellipse An ellipse.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Fills an ellipse sector.
     * 
     * @param ellipseSector An ellipse sector.
     * @param paint Paint for drawing.
     */
    public void fillEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        fillEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Draws an arc.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.OPEN ) );
    }

    /**
     * Draws an arc.
     * 
     * @param center Arc center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Draws an arc.
     * 
     * @param arc An arc.
     * @param paint Paint for drawing.
     */
    public void drawArc( Arc arc, Paint paint ) {
        drawArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Fills an arc.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, -startAngle, -extent, Arc2D.CHORD ) );
    }

    /**
     * Fills an arc.
     * 
     * @param center Arc center.
     * @param radiusH Horizontal radius.
     * @param radiusV Vertical radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Fills an arc.
     * 
     * @param arc An arc.
     * @param paint Paint for drawing.
     */
    public void fillArc( Arc arc, Paint paint ) {
        fillArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Draws a ring.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
    }

    /**
     * Draws a ring.
     * 
     * @param center Ring center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Draws a ring.
     * 
     * @param ring A ring.
     * @param paint Paint for drawing.
     */
    public void drawRing( Ring ring, Paint paint ) {
        drawRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Fills a ring.
     * 
     * @param x X coordinate of the center.
     * @param y Y coordinate of the center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
    }

    /**
     * Fills a ring.
     * 
     * @param center Ring center.
     * @param innerRadius Inner radius.
     * @param outerRadius Outer radius.
     * @param startAngle Start angle in degrees (clockwise).
     * @param endAngle End angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Fills a ring.
     * 
     * @param ring A ring.
     * @param paint Paint for drawing.
     */
    public void fillRing( Ring ring, Paint paint ) {
        fillRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Draws a triangle. Provide vertices in clockwise order.
     * 
     * @param v1x X coordinate of the first vertex.
     * @param v1y Y coordinate of the first vertex.
     * @param v2x X coordinate of the second vertex.
     * @param v2y Y coordinate of the second vertex.
     * @param v3x X coordinate of the third vertex.
     * @param v3y Y coordinate of the third vertex.
     * @param paint Paint for drawing.
     */
    public void drawTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
    }

    /**
     * Draws a triangle. Provide vertices in clockwise order.
     * 
     * @param v1 First vertex.
     * @param v2 Second vertex.
     * @param v3 Third vertex.
     * @param paint Paint for drawing.
     */
    public void drawTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Draws a triangle.
     * 
     * @param triangle A triangle.
     * @param paint Paint for drawing.
     */
    public void drawTriangle( Triangle triangle, Paint paint ) {
        drawTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Fills a triangle. Provide vertices in clockwise order.
     * 
     * @param v1x X coordinate of the first vertex.
     * @param v1y Y coordinate of the first vertex.
     * @param v2x X coordinate of the second vertex.
     * @param v2y Y coordinate of the second vertex.
     * @param v3x X coordinate of the third vertex.
     * @param v3y Y coordinate of the third vertex.
     * @param paint Paint for drawing.
     */
    public void fillTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
    }

    /**
     * Fills a triangle. Provide vertices in clockwise order.
     * 
     * @param v1 First vertex.
     * @param v2 Second vertex.
     * @param v3 Third vertex.
     * @param paint Paint for drawing.
     */
    public void fillTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Fills a triangle.
     * 
     * @param triangle A triangle.
     * @param paint Paint for drawing.
     */
    public void fillTriangle( Triangle triangle, Paint paint ) {
        fillTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Draws a regular polygon.
     * 
     * @param x X coordinate of the polygon center.
     * @param y Y coordinate of the polygon center.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawPolygon( double x, double y, int sides, double radius, double rotation, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
    }

    /**
     * Draws a regular polygon.
     * 
     * @param center Polygon center.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawPolygon( Vector2 center, int sides, double radius, double rotation, Paint paint ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Draws a regular polygon.
     * 
     * @param polygon A regular polygon.
     * @param paint Paint for drawing.
     */
    public void drawPolygon( Polygon polygon, Paint paint ) {
        drawPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Fills a regular polygon.
     * 
     * @param x X coordinate of the polygon center.
     * @param y Y coordinate of the polygon center.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillPolygon( double x, double y, int sides, double radius, double rotation, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
    }

    /**
     * Fills a regular polygon.
     * 
     * @param center Polygon center.
     * @param sides Number of sides.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillPolygon( Vector2 center, int sides, double radius, double rotation, Paint paint ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Fills a regular polygon.
     * 
     * @param polygon A regular polygon.
     * @param paint Paint for drawing.
     */
    public void fillPolygon( Polygon polygon, Paint paint ) {
        fillPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }
    
    /**
     * Draws a regular star.
     * 
     * @param x X coordinate of the star center.
     * @param y Y coordinate of the star center.
     * @param tips Number of tips/points.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawStar( double x, double y, int tips, double radius, double rotation, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createStar( x, y, tips, radius, rotation ) );
    }

    /**
     * Draws a regular star.
     * 
     * @param center Star center.
     * @param tips Number of tips/points.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawStar( Vector2 center, int tips, double radius, double rotation, Paint paint ) {
        drawStar( center.x, center.y, tips, radius, rotation, paint );
    }

    /**
     * Draws a regular star.
     * 
     * @param polygon A regular star.
     * @param paint Paint for drawing.
     */
    public void drawStar( Star polygon, Paint paint ) {
        drawStar( polygon.x, polygon.y, polygon.tips, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Fills a regular star.
     * 
     * @param x X coordinate of the star center.
     * @param y Y coordinate of the star center.
     * @param tips Number of tips/points.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillStar( double x, double y, int tips, double radius, double rotation, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createStar( x, y, tips, radius, rotation ) );
    }

    /**
     * Fills a regular star.
     * 
     * @param center Star center.
     * @param tips Number of tips/points.
     * @param radius Radius.
     * @param rotation Rotation in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void fillStar( Vector2 center, int tips, double radius, double rotation, Paint paint ) {
        fillStar( center.x, center.y, tips, radius, rotation, paint );
    }

    /**
     * Fills a regular star.
     * 
     * @param polygon A regular star.
     * @param paint Paint for drawing.
     */
    public void fillStar( Star polygon, Paint paint ) {
        fillStar( polygon.x, polygon.y, polygon.tips, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Draws a path.
     * 
     * @param path The path to be drawn.
     * @param paint Paint for drawing.
     */
    public void drawPath( Path path, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( path.path );
    }

    /**
     * Fills a path.
     * 
     * @param path The path to be drawn.
     * @param paint Paint for drawing.
     */
    public void fillPath( Path path, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( path.path );
    }

    
    
    //**************************************************************************
    // Curve drawing methods.
    //**************************************************************************

    /**
     * Draws a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param cx X coordinate of the control point.
     * @param cy Y coordinate of the control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void drawQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Draws a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c Control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void drawQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }
    
    /**
     * Draws a quadratic curve (quadratic Bézier curve).
     * 
     * @param points Curve points. At least 3. Each pair of subsequent points
     * represents a new control point and a new anchor point.
     * @param paint Paint for drawing.
     */
    public void drawQuadCurve( Vector2[] points, Paint paint ) {
        
        if ( points.length < 3 ) {
            throw new IllegalArgumentException( "QuadCurves need at least 3 points." );
        } else if ( ( points.length - 3 ) % 2 != 0 ) {
            throw new IllegalArgumentException( "QuadCurves need at least 3 points and a set of pairs for the remaining points." );
        }
        
        drawQuadCurve( points[0], points[1], points[2], paint );
        for ( int i = 3; i < points.length; i += 2 ) {
            drawQuadCurve( points[i-1], points[i], points[i+1], paint );
        }
        
    }

    /**
     * Draws a quadratic curve (quadratic Bézier curve).
     * 
     * @param quadCurve A quadratic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void drawQuadCurve( QuadCurve quadCurve, Paint paint ) {
        drawQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Fills a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param cx X coordinate of the control point.
     * @param cy Y coordinate of the control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void fillQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Fills a quadratic curve (quadratic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c Control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void fillQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }
    
    /**
     * Fills a quadratic curve (quadratic Bézier curve).
     * 
     * @param points Curve points. At least 3. Each pair of subsequent points
     * represents a new control point and a new anchor point.
     * @param paint Paint for drawing.
     */
    public void fillQuadCurve( Vector2[] points, Paint paint ) {
        
        if ( points.length < 3 ) {
            throw new IllegalArgumentException( "QuadCurves need at least 3 points." );
        } else if ( ( points.length - 3 ) % 2 != 0 ) {
            throw new IllegalArgumentException( "QuadCurves need at least 3 points and a set of pairs for the remaining points." );
        }
        
        fillQuadCurve( points[0], points[1], points[2], paint );
        for ( int i = 3; i < points.length; i += 2 ) {
            fillQuadCurve( points[i-1], points[i], points[i+1], paint );
        }
        
    }

    /**
     * Fills a quadratic curve (quadratic Bézier curve).
     * 
     * @param quadCurve A quadratic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void fillQuadCurve( QuadCurve quadCurve, Paint paint ) {
        fillQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Draws a cubic curve (cubic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param c1x X coordinate of the first control point.
     * @param c1y Y coordinate of the first control point.
     * @param c2x X coordinate of the second control point.
     * @param c2y Y coordinate of the second control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void drawCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Draws a cubic curve (cubic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c1 First control point.
     * @param c2 Second control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void drawCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }
    
    /**
     * Draws a cubic curve (cubic Bézier curve).
     * 
     * @param points Curve points. At least 4. Each set of three subsequent points
     * represents two new control points and a new anchor point.
     * @param paint Paint for drawing.
     */
    public void drawCubicCurve( Vector2[] points, Paint paint ) {
        
        if ( points.length < 4 ) {
            throw new IllegalArgumentException( "CubicCurves need at least 4 points." );
        } else if ( ( points.length - 4 ) % 3 != 0 ) {
            throw new IllegalArgumentException( "CubicCurves need at least 4 points and a set of trios for the remaining points." );
        }
        
        drawCubicCurve( points[0], points[1], points[2], points[3], paint );
        for ( int i = 4; i < points.length; i += 3 ) {
            drawCubicCurve( points[i-1], points[i], points[i+1], points[i+2], paint );
        }
        
    }

    /**
     * Draws a cubic curve (cubic Bézier curve).
     * 
     * @param cubicCurve A cubic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void drawCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        drawCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }

    /**
     * Fills a cubic curve (cubic Bézier curve).
     * 
     * @param p1x X coordinate of the start point.
     * @param p1y Y coordinate of the start point.
     * @param c1x X coordinate of the first control point.
     * @param c1y Y coordinate of the first control point.
     * @param c2x X coordinate of the second control point.
     * @param c2y Y coordinate of the second control point.
     * @param p2x X coordinate of the end point.
     * @param p2y Y coordinate of the end point.
     * @param paint Paint for drawing.
     */
    public void fillCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Fills a cubic curve (cubic Bézier curve).
     * 
     * @param p1 Start point.
     * @param c1 First control point.
     * @param c2 Second control point.
     * @param p2 End point.
     * @param paint Paint for drawing.
     */
    public void fillCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }

    /**
     * Fills a cubic curve (cubic Bézier curve).
     * 
     * @param points Curve points. At least 4. Each set of three subsequent points
     * represents two new control points and a new anchor point.
     * @param paint Paint for drawing.
     */
    public void fillCubicCurve( Vector2[] points, Paint paint ) {
        
        if ( points.length < 4 ) {
            throw new IllegalArgumentException( "CubicCurves need at least 4 points." );
        } else if ( ( points.length - 4 ) % 3 != 0 ) {
            throw new IllegalArgumentException( "CubicCurves need at least 4 points and a set of trios for the remaining points." );
        }
        
        fillCubicCurve( points[0], points[1], points[2], points[3], paint );
        for ( int i = 4; i < points.length; i += 3 ) {
            fillCubicCurve( points[i-1], points[i], points[i+1], points[i+2], paint );
        }
        
    }
    
    /**
     * Fills a cubic curve (cubic Bézier curve).
     * 
     * @param cubicCurve A cubic Bézier curve.
     * @param paint Paint for drawing.
     */
    public void fillCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        fillCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }
    

    
    //**************************************************************************
    // Text drawing methods.
    //**************************************************************************

    /**
     * Draws text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the text drawing start.
     * @param y Y coordinate of the text drawing start.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, Paint paint ) {
        g2d.setPaint( paint );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
    }
    
    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the text drawing start.
     * @param y Y coordinate of the text drawing start.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double rotation, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, paint );
    }

    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the text drawing start.
     * @param y Y coordinate of the text drawing start.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, Paint paint ) {
        g2d.setPaint( paint );
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = ig2d.getFontMetrics().getStringBounds( text, ig2d );
        //ig2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, ig2d );
        ig2d.dispose();
    }

    /**
     * Draws text.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the text drawing start.
     * @param y Y coordinate of the text drawing start.
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, int fontSize, Paint paint ) {
        g2d.setPaint( paint );
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        //Rectangle2D r = ig2d.getFontMetrics().getStringBounds( text, ig2d );
        //ig2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, ig2d );
        ig2d.dispose();
    }
    
    /**
     * Draws rotated text.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the text drawing start.
     * @param y Y coordinate of the text drawing start.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double rotation, int fontSize, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, fontSize, paint );
    }
    
    /**
     * Draws rotated text.
     * 
     * @param text The text to be drawn.
     * @param x X coordinate of the text drawing start.
     * @param y Y coordinate of the text drawing start.
     * @param originX X coordinate of the rotation pivot.
     * @param originY Y coordinate of the rotation pivot.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, int fontSize, Paint paint ) {
        g2d.setPaint( paint );
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = ig2d.getFontMetrics().getStringBounds( text, ig2d );
        //ig2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, ig2d );
        ig2d.dispose();
    }

    /**
     * Draws text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, Paint paint ) {
        drawText( text, point.x, point.y, paint );
    }
    
    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, paint );
    }

    /**
     * Draws rotated text using the current font size.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param origin Rotation pivot point.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, paint );
    }

    /**
     * Draws text.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, fontSize, paint );
    }
    
    /**
     * Draws rotated text.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, paint );
    }

    /**
     * Draws text.
     * 
     * @param text The text to be drawn.
     * @param point Start point of the text drawing.
     * @param origin Rotation pivot point.
     * @param rotation Rotation angle in degrees (clockwise).
     * @param fontSize Font size.
     * @param paint Paint for drawing.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, paint );
    }
    
    /**
     * Measures the width of a text.
     * 
     * @param text The text to be measured.
     * @return The width of the text.
     */
    public int measureText( String text ) {
        return g2d.getFontMetrics().stringWidth( text );
    }

    /**
     * Measures the width of a text.
     * 
     * @param text The text to be measured.
     * @param fontSize Font size.
     * @return The width of the text.
     */
    public int measureText( String text, int fontSize ) {
        Font f = g2d.getFont();
        g2d.setFont( f.deriveFont( (float) fontSize ) );
        int width = g2d.getFontMetrics().stringWidth( text );
        g2d.setFont( f );
        return width;
    }
    
    /**
     * Measures the text bounds.
     * 
     * @param text The text to be measured.
     * @return A rectangle bounding the text.
     */
    public Rectangle measureTextBounds( String text ) {
        Rectangle2D r2d = g2d.getFontMetrics().getStringBounds( text, g2d );
        return new Rectangle( 0, 0, r2d.getWidth(), r2d.getHeight() );
    }

    /**
     * Measures the width of a text.
     * 
     * @param text The text to be measured.
     * @param fontSize Font size.
     * @return The width of the text.
     */
    public Rectangle measureTextBounds( String text, int fontSize ) {
        Font f = g2d.getFont();
        g2d.setFont( f.deriveFont( (float) fontSize ) );
        Rectangle2D r2d = g2d.getFontMetrics().getStringBounds( text, g2d );
        Rectangle r = new Rectangle( 0, 0, r2d.getWidth(), r2d.getHeight() );
        g2d.setFont( f );
        return r;
    }
    
    

    //**************************************************************************
    // Various utility methods.
    //**************************************************************************

    /**
     * Clears the drawing canvas background.
     * 
     * @param paint Paint to be used.
     */
    public void clearBackground( Paint paint ) {
        fillRectangle( 0, 0, getScreenWidth(), getScreenHeight(), paint );
    }

    
    
    //**************************************************************************
    // Methods for getting and/or setting options and/or data
    // related to execution.
    //**************************************************************************
    
    /**
     * Sets the desired number of frames per second for the game/simulation
     * execution.
     * 
     * @param targetFPS The number of frames per second.
     */
    public void setTargetFPS( int targetFPS ) {

        if ( targetFPS <= 0 ) {
            throw new IllegalArgumentException( "target FPS must be positive!" );
        }

        this.targetFPS = targetFPS;

    }
    
    /**
     * Sets the window icon.
     * 
     * @param image Image used to set the icon.
     */
    public void setWindowIcon( Image image ) {
        setIconImage( image.buffImage );
    }

    /**
     * Gets the time a frame took to be updated and drawn.
     * 
     * @return The time a frame took to be updated and drawn.
     */
    public double getFrameTime() {
        return frameTime / 1000.0;
    }

    /**
     * Gets the current game/simulation execution time, in seconds.
     * 
     * @return The current game/simulation execution time, in seconds.
     */
    public double getTime() {
        return ( System.currentTimeMillis() - startTime ) / 1000.0;
    }

    /**
     * Gets the current frames per second count.
     * 
     * @return The current frames per second count.
     */
    public int getFPS() {
        return currentFPS;
    }

    /**
     * Gets the screen width.
     * 
     * @return The screen width.
     */
    public int getScreenWidth() {
        return drawingPanel.getWidth();
    }

    /**
     * Gets the screen height.
     * 
     * @return The screen height.
     */
    public int getScreenHeight() {
        return drawingPanel.getHeight();
    }

    /**
     * Gets the current graphics context.
     * Note: Use only inside the draw method!
     * 
     * @return The current graphics context.
     */
    public Graphics2D getGraphics2D() {
        return g2d;
    }

    /**
     * Returns whether antialiasing is active for the graphics context.
     * 
     * @return True if antialiasing is enabled, false otherwise.
     */
    public boolean isAntialiasing() {
        return antialiasing;
    }

    /**
     * Changes the antialiasing control flag for the graphics context.
     * 
     * @param antialiasing The flag state.
     */
    public void setAntialiasing( boolean antialiasing ) {
        this.antialiasing = antialiasing;
    }
    
    /**
     * Rotates the current graphics context from coordinate (0, 0).
     * Note: Use only inside the draw method!
     * 
     * @param degrees Rotation angle measure in degrees.
     */
    public void rotate( double degrees ) {
        g2d.rotate( Math.toRadians( degrees ) );
    }

    /**
     * Rotates the current graphics context from a given coordinate.
     * Note: Use only inside the draw method!
     * 
     * @param x X coordinate of the rotation point.
     * @param y Y coordinate of the rotation point.
     * @param degrees Rotation angle measure in degrees.
     */
    public void rotate( double degrees, double x, double y ) {
        g2d.rotate( Math.toRadians( degrees ), x, y );
    }

    /**
     * Rotates the current graphics context from a given pivot point.
     * Note: Use only inside the draw method!
     *
     * @param degrees Rotation angle measure in degrees.
     * @param pivot The pivot point of the rotation.
     */
    public void rotate( double degrees, Vector2 pivot ) {
        g2d.rotate( Math.toRadians( degrees ), pivot.x, pivot.y );
    }

    /**
     * Translates the current graphics context.
     * Note: Use only inside the draw method!
     * 
     * @param x New origin in x.
     * @param y New origin in y.
     */
    public void translate( double x, double y ) {
        g2d.translate( x, y );
    }

    /**
     * Translates the current graphics context.
     * Note: Use only inside the draw method!
     *
     * @param offset The translation offset.
     */
    public void translate( Vector2 offset ) {
        g2d.translate( offset.x, offset.y );
    }

    /**
     * Scales the current graphics context.
     * Note: Use only inside the draw method!
     * 
     * @param x New scale in x.
     * @param y New scale in y.
     */
    public void scale( double x, double y ) {
        g2d.scale( x, y );
    }

    /**
     * Scales the current graphics context.
     * Note: Use only inside the draw method!
     *
     * @param factor The scale factor vector.
     */
    public void scale( Vector2 factor ) {
        g2d.scale( factor.x, factor.y );
    }

    /**
     * Applies shearing to the current graphics context.
     * Note: Use only inside the draw method!
     * 
     * @param x New shear in x.
     * @param y New shear in y.
     */
    public void shear( double x, double y ) {
        g2d.shear( x, y );
    }

    /**
     * Applies shearing to the current graphics context.
     * Note: Use only inside the draw method!
     *
     * @param factor The shear factor vector.
     */
    public void shear( Vector2 factor ) {
        g2d.shear( factor.x, factor.y );
    }

    /**
     * Draws the current FPS (frames per second) count.
     * 
     * @param x The x position for drawing.
     * @param y The y position for drawing.
     */
    public void drawFPS( double x, double y ) {

        Font t = g2d.getFont();
        g2d.setFont( defaultFPSFont );

        drawText( 
            String.format( "%d FPS", currentFPS ), 
            x, y, ColorUtils.lerp( RED, LIME, currentFPS / (double) targetFPS ) );

        g2d.setFont( t );

    }
    
    
    
    //**************************************************************************
    // Logging methods.
    //**************************************************************************
    
    /**
     * Emits a log message to the error output stream.
     * 
     * @param logLevel The log level.
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLog( int logLevel, String text, Object... args ) {
        TraceLogUtils.traceLog( logLevel, text, args );
    }
    
    /**
     * Emits an INFO level log message to the error output stream.
     * 
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogInfo( String text, Object... args ) {
        TraceLogUtils.traceLogInfo( text, args );
    }
    
    /**
     * Emits a WARNING level log message to the error output stream.
     * 
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogWarning( String text, Object... args ) {
        TraceLogUtils.traceLogWarning( text, args );
    }
    
    /**
     * Emits an ERROR level log message to the error output stream.
     * 
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogError( String text, Object... args ) {
        TraceLogUtils.traceLogError( text, args );
    }
    
    /**
     * Emits a FATAL level log message to the error output stream.
     * 
     * @param text The text to be emitted.
     * @param args The arguments for text formatting.
     */
    public static void traceLogFatal( String text, Object... args ) {
        TraceLogUtils.traceLogFatal( text, args );
    }
    
    /**
     * Sets the log level for the engine logging system.
     * 
     * @param logLevel The log level.
     */
    public static void setTraceLogLevel( int logLevel ) {
        TraceLogUtils.setTraceLogLevel( logLevel );
    }
    
    
    
    //**************************************************************************
    // Methods for configuring font and stroke.
    //**************************************************************************

    /**
     * Changes the default font of the graphics context.
     * 
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link #FONT_MONOSPACED}</li>
     * <li>Style: {@link #FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     * @param font Font to be used.
     */
    public void setDefaultFont( Font font ) {
        this.defaultFont = font;
    }

    /**
     * Changes the name of the default font of the graphics context.
     * 
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link #FONT_MONOSPACED}</li>
     * <li>Style: {@link #FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     * @param name Default font name.
     */
    public void setDefaultFontName( String name ) {
        defaultFont = new Font( name, defaultFont.getStyle(), defaultFont.getSize() );
    }

    /**
     * Changes the style of the default font of the graphics context.
     * 
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link #FONT_MONOSPACED}</li>
     * <li>Style: {@link #FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     * @param style The default font style.
     */
    public void setDefaultFontStyle( int style ) {
        defaultFont = defaultFont.deriveFont( style );
    }

    /**
     * Changes the size of the default font of the graphics context.
     * 
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link #FONT_MONOSPACED}</li>
     * <li>Style: {@link #FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     * @param size The default font size.
     */
    public void setDefaultFontSize( int size ) {
        defaultFont = defaultFont.deriveFont( (float) size );
    }

    /**
     * Changes the name of the current font of the graphics context.
     * 
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link #FONT_MONOSPACED}</li>
     * <li>Style: {@link #FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     * @param name Font name.
     */
    public void setFontName( String name ) {
        g2d.setFont( new Font( name, g2d.getFont().getStyle(), g2d.getFont().getSize() ) );
    }

    /**
     * Changes the style of the current font of the graphics context.
     *
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link #FONT_MONOSPACED}</li>
     * <li>Style: {@link #FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     * @param style The current font style.
     */
    public void setFontStyle( int style ) {
        g2d.setFont( g2d.getFont().deriveFont( style ) );
    }

    /**
     * Changes the size of the current font of the graphics context.
     * 
     * The default font has the following attributes:
     * <ul>
     * <li>Name: {@link #FONT_MONOSPACED}</li>
     * <li>Style: {@link #FONT_BOLD}</li>
     * <li>Size: 10</li>
     * </ul>
     * 
     * @param size The current font size.
     */
    public void setFontSize( int size ) {
        g2d.setFont( g2d.getFont().deriveFont( (float) size ) );
    }

    /**
     * Changes the default stroke of the graphics context.
     * 
     * @param stroke Stroke to be used.
     */
    public void setDefaultStroke( BasicStroke stroke ) {
        this.defaultStroke = stroke;
    }

    /**
     * Changes the line width of the default stroke of the graphics context.
     * 
     * @param lineWidth The default stroke line width.
     */
    public void setDefaultStrokeLineWidth( float lineWidth ) {
        defaultStroke = StrokeUtils.cloneStrokeLineWidth( lineWidth, defaultStroke );
    }

    /**
     * Changes the end cap style of the default stroke of the graphics context.
     * 
     * @param endCap The new end cap style.
     */
    public void setDefaultStrokeEndCap( int endCap ) {
        defaultStroke = StrokeUtils.cloneStrokeEndCap( endCap, defaultStroke );
    }

    /**
     * Changes the line join style of the default stroke of the graphics context.
     * 
     * @param lineJoin The new line join style.
     */
    public void setDefaultStrokeLineJoin( int lineJoin ) {
        defaultStroke = StrokeUtils.cloneStrokeLineJoin( lineJoin, defaultStroke );
    }
    
    /**
     * Changes the miter join trim limit of the default stroke.
     * 
     * @param miterLimit The miter join trim limit.
     */
    public void setDefaultStrokeMiterLimit( float miterLimit ) {
        defaultStroke = StrokeUtils.cloneStrokeMiterLimit( miterLimit, defaultStroke );
    }
    
    /**
     * Changes the array representing the dash pattern of the default stroke.
     * 
     * @param dashArray The array representing the dash pattern.
     */
    public void setDefaultStrokeDashArray( float[] dashArray ) {
        defaultStroke = StrokeUtils.cloneStrokeDashArray( dashArray, defaultStroke );
    }
    
    /**
     * Changes the dash pattern offset of the default stroke.
     * 
     * @param dashPhase The dash pattern offset.
     */
    public void setDefaultStrokeDashPhase( float dashPhase ) {
        defaultStroke = StrokeUtils.cloneStrokeDashPhase( dashPhase, defaultStroke );
    }
    
    /**
     * Changes both the dash pattern and offset of the default stroke.
     * 
     * @param dashArray The array representing the dash pattern.
     * @param dashPhase The dash pattern offset.
     */
    public void setDefaultStrokeDashArrayAndPhase( float[] dashArray, float dashPhase ) {
        defaultStroke = StrokeUtils.cloneStrokeDashArrayAndPhase( dashArray, dashPhase, defaultStroke );
    }
    
    /**
     * Gets the default stroke.
     * 
     * @return The default stroke.
     */
    public BasicStroke getDefaultStroke() {
        return defaultStroke;
    }

    /**
     * Changes the stroke of the graphics context.
     * 
     * @param stroke Stroke to be used.
     */
    public void setStroke( BasicStroke stroke ) {
        g2d.setStroke( stroke );
    }
    
    /**
     * Changes the line width of the current stroke of the graphics context.
     * 
     * @param lineWidth The stroke line width.
     */
    public void setStrokeLineWidth( float lineWidth ) {
        g2d.setStroke( StrokeUtils.cloneStrokeLineWidth( lineWidth, g2d.getStroke() ) );
    }

    /**
     * Changes the end cap style of the current stroke of the graphics context.
     * 
     * @param endCap The new end cap style.
     */
    public void setStrokeEndCap( int endCap ) {
        g2d.setStroke( StrokeUtils.cloneStrokeEndCap( endCap, g2d.getStroke() ) );
    }

    /**
     * Changes the line join style of the current stroke of the graphics context.
     * 
     * @param lineJoin The new line join style.
     */
    public void setStrokeLineJoin( int lineJoin ) {
        g2d.setStroke( StrokeUtils.cloneStrokeLineJoin( lineJoin, g2d.getStroke() ) );
    }
    
    /**
     * Changes the miter join trim limit of the current stroke.
     * 
     * @param miterLimit The miter join trim limit.
     */
    public void setStrokeMiterLimit( float miterLimit ) {
        g2d.setStroke( StrokeUtils.cloneStrokeMiterLimit( miterLimit, g2d.getStroke() ) );
    }
    
    /**
     * Changes the array representing the dash pattern of the current stroke.
     * 
     * @param dashArray The array representing the dash pattern.
     */
    public void setStrokeDashArray( float[] dashArray ) {
        g2d.setStroke( StrokeUtils.cloneStrokeDashArray( dashArray, g2d.getStroke() ) );
    }
    
    /**
     * Changes the dash pattern offset of the current stroke.
     * 
     * @param dashPhase The dash pattern offset.
     */
    public void setStrokeDashPhase( float dashPhase ) {
        g2d.setStroke( StrokeUtils.cloneStrokeDashPhase( dashPhase, g2d.getStroke() ) );
    }
    
    /**
     * Changes both the dash pattern and offset of the current stroke.
     * 
     * @param dashArray The array representing the dash pattern.
     * @param dashPhase The dash pattern offset.
     */
    public void setStrokeDashArrayAndPhase( float[] dashArray, float dashPhase ) {
        g2d.setStroke( StrokeUtils.cloneStrokeDashArrayAndPhase( dashArray, dashPhase, g2d.getStroke() ) );
    }
    
    /**
     * Resets the current stroke of the graphics context to the one defined as
     * the default stroke.
     */
    public void resetStrokeToDefault() {
        g2d.setStroke( defaultStroke );
    }
    
    /**
     * Gets the current stroke of the graphics context.
     * 
     * @return The current stroke of the graphics context.
     */
    public BasicStroke getStroke() {
        return (BasicStroke) g2d.getStroke();
    }
    
    
    
    //**************************************************************************
    // Methods for loading and drawing images.
    //**************************************************************************
    
    /**
     * Loads an image.
     * 
     * @param filePath Image file path.
     * @return An image.
     */
    public Image loadImage( String filePath ) {
        return ImageUtils.loadImage( filePath );
    }
    
    /**
     * Loads an image.
     * 
     * @param input An input stream for an image.
     * @return An image.
     */
    public Image loadImage( InputStream input ) {
        return ImageUtils.loadImage( input );
    }
    
    /**
     * Loads an image.
     * 
     * @param url A URL for an image.
     * @return An image.
     */
    public Image loadImage( URL url ) {
        return ImageUtils.loadImage( url );
    }
    
    /**
     * Draws an image with a background color.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double x, double y, Color bgColor ) {
        g2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
    }
    
    /**
     * Draws an image.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     */
    public void drawImage( Image image, double x, double y ) {
        drawImage( image, x, y, null );
    }
    
    /**
     * Draws a rotated image with a background color.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, x, y, 0, 0, rotation, bgColor );
    }
    
    /**
     * Draws a rotated image.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, double x, double y, double rotation ) {
        drawImage( image, x, y, 0, 0, rotation, null );
    }
    
    /**
     * Draws a rotated image with a background color.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        ig2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
        ig2d.dispose();
    }
    
    /**
     * Draws a rotated image.
     * 
     * @param image The image to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, x, y, originX, originY, rotation, null );
    }
    
    /**
     * Draws a cropped image with a background color.
     * 
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, Color bgColor ) {
        drawImage( image, source.x, source.y, source.width, source.height, x, y, bgColor );
    }

    /**
     * Draws a cropped image with a background color.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double x, double y, Color bgColor ) {
        g2d.drawImage( image.buffImage,
                (int) x,
                (int) y,
                (int) ( x + srcWidth ),
                (int) ( y + srcHeight ),
                (int) srcX,
                (int) srcY,
                (int) ( srcX + srcWidth ),
                (int) ( srcY + srcHeight ),
                bgColor,
                null
        );
    }

    /**
     * Draws a cropped image.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     */
    public void drawImage( Image image, Rectangle source, double x, double y ) {
        drawImage( image, source, x, y, null );
    }

    /**
     * Draws a cropped image.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double x, double y ) {
        drawImage( image, srcX, srcY, srcWidth, srcHeight, x, y, null );
    }

    /**
     * Draws a rotated cropped image with a background color.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, source, x, y, 0, 0, rotation, bgColor );
    }

    /**
     * Draws a rotated cropped image with a background color.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, srcX, srcY, srcWidth, srcHeight, x, y, 0, 0, rotation, bgColor );
    }

    /**
     * Draws a rotated cropped image.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation ) {
        drawImage( image, source, x, y, 0, 0, rotation, null );
    }

    /**
     * Draws a rotated cropped image.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double x, double y, double rotation ) {
        drawImage( image, srcX, srcY, srcWidth, srcHeight, x, y, 0, 0, rotation, null );
    }

    /**
     * Draws a rotated cropped image with a background color.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        drawImage( image, source.x, source.y, source.width, source.height, x, y, originX, originY, rotation, bgColor );
    }

    /**
     * Draws a rotated cropped image with a background color.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        ig2d.drawImage( image.buffImage,
                (int) x,
                (int) y,
                (int) ( x + srcWidth ),
                (int) ( y + srcHeight ),
                (int) srcX,
                (int) srcY,
                (int) ( srcX + srcWidth ),
                (int) ( srcY + srcHeight ),
                bgColor,
                null
        );
        ig2d.dispose();
    }

    /**
     * Draws a rotated cropped image.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, source, x, y, originX, originY, rotation, null );
    }

    /**
     * Draws a rotated cropped image.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param x X coordinate of the image drawing position.
     * @param y Y coordinate of the image drawing position.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, srcX, srcY, srcWidth, srcHeight, x, y, originX, originY, rotation, null );
    }

    /**
     * Draws a cropped image into a destination rectangle with a background color.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, Color bgColor ) {
        drawImage( image, source.x, source.y, source.width, source.height, dest.x, dest.y, dest.width, dest.height, bgColor );
    }

    /**
     * Draws a cropped image into a destination rectangle with a background color.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param destX X coordinate of the destination position.
     * @param destY Y coordinate of the destination position.
     * @param destWidth Width of the destination region.
     * @param destHeight Height of the destination region.
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double destX, double destY, double destWidth, double destHeight, Color bgColor ) {
        g2d.drawImage( image.buffImage,
                (int) destX,
                (int) destY,
                (int) ( destX + destWidth ),
                (int) ( destY + destHeight ),
                (int) srcX,
                (int) srcY,
                (int) ( srcX + srcWidth ),
                (int) ( srcY + srcHeight ),
                bgColor,
                null
        );
    }

    /**
     * Draws a cropped image into a destination rectangle.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest ) {
        drawImage( image, source, dest, null );
    }

    /**
     * Draws a cropped image into a destination rectangle.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param destX X coordinate of the destination position.
     * @param destY Y coordinate of the destination position.
     * @param destWidth Width of the destination region.
     * @param destHeight Height of the destination region.
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double destX, double destY, double destWidth, double destHeight ) {
        drawImage( image, srcX, srcY, srcWidth, srcHeight, destX, destY, destWidth, destHeight, null );
    }

    /**
     * Draws a rotated cropped image into a destination rectangle with a background color.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation, Color bgColor ) {
        drawImage( image, source, dest, 0, 0, rotation, bgColor );
    }

    /**
     * Draws a rotated cropped image into a destination rectangle.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation ) {
        drawImage( image, source, dest, 0, 0, rotation, null );
    }

    /**
     * Draws a rotated cropped image into a destination rectangle with a background color.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation, Color bgColor ) {
        drawImage( image, source.x, source.y, source.width, source.height, dest.x, dest.y, dest.width, dest.height, originX, originY, rotation, bgColor );
    }

    /**
     * Draws a rotated cropped image into a destination rectangle with a background color.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param destX X coordinate of the destination position.
     * @param destY Y coordinate of the destination position.
     * @param destWidth Width of the destination region.
     * @param destHeight Height of the destination region.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     * @param bgColor A background color.
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double destX, double destY, double destWidth, double destHeight, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), destX + originX, destY + originY );
        ig2d.drawImage( image.buffImage,
                (int) destX,
                (int) destY,
                (int) ( destX + destWidth ),
                (int) ( destY + destHeight ),
                (int) srcX,
                (int) srcY,
                (int) ( srcX + srcWidth ),
                (int) ( srcY + srcHeight ),
                bgColor,
                null
        );
        ig2d.dispose();
    }

    /**
     * Draws a rotated cropped image into a destination rectangle.
     *
     * @param image The image to be drawn.
     * @param source A rectangle delimiting the image crop to be drawn.
     * @param dest A destination rectangle defining the position and dimensions where the image will be drawn.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation ) {
        drawImage( image, source, dest, originX, originY, rotation, null );
    }

    /**
     * Draws a rotated cropped image into a destination rectangle.
     *
     * @param image The image to be drawn.
     * @param srcX X coordinate of the source crop region.
     * @param srcY Y coordinate of the source crop region.
     * @param srcWidth Width of the source crop region.
     * @param srcHeight Height of the source crop region.
     * @param destX X coordinate of the destination position.
     * @param destY Y coordinate of the destination position.
     * @param destWidth Width of the destination region.
     * @param destHeight Height of the destination region.
     * @param originX X coordinate of the rotation axis.
     * @param originY Y coordinate of the rotation axis.
     * @param rotation Rotation in degrees of the image drawing (clockwise).
     */
    public void drawImage( Image image, double srcX, double srcY, double srcWidth, double srcHeight, double destX, double destY, double destWidth, double destHeight, double originX, double originY, double rotation ) {
        drawImage( image, srcX, srcY, srcWidth, srcHeight, destX, destY, destWidth, destHeight, originX, originY, rotation, null );
    }


    
    //**************************************************************************
    // Drawing methods mapped directly from Graphics and Graphics2D.
    //**************************************************************************
    
    /**
     * Draws a shape.
     * Mapping for the draw method of Graphics2D.
     * 
     * @param shape The shape to be drawn.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics2D#draw
     */
    public void g2Draw( Shape shape, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( shape );
    }
    
    /**
     * Draws a line.
     * Mapping for the drawLine method of Graphics.
     * 
     * @param startX X coordinate of the start point.
     * @param startY Y coordinate of the start point.
     * @param endX X coordinate of the end point.
     * @param endY Y coordinate of the end point.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawLine
     */
    public void g2DrawLine( int startX, int startY, int endX, int endY, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawLine( startX, startY, endX, endY );
    }
    
    /**
     * Draws a rectangle.
     * Mapping for the drawRect method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawRect
     */
    public void g2DrawRect( int x, int y, int width, int height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawRect( x, y, width, height );
    }
    
    /**
     * Draws a rounded rectangle.
     * Mapping for the drawRoundRect method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param arcWidth The width of the rounding arc.
     * @param arcHeight The height of the rounding arc.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawRoundRect
     */
    public void g2DrawRoundRect( int x, int y, int width, int height, int arcWidth, int arcHeight, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawRoundRect( x, y, width, height, arcWidth, arcHeight );
    }
    
    /**
     * Draws a "3D" rectangle.
     * Mapping for the draw3DRect method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param raised Whether the 3D effect shows the rectangle raised. 
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#draw3DRect
     */
    public void g2Draw3DRect( int x, int y, int width, int height, boolean raised, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw3DRect( x, y, width, height, raised );
    }
    
    /**
     * Draws an ellipse.
     * Mapping for the drawOval method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the bounding rectangle.
     * @param y Y coordinate of the upper-left vertex of the bounding rectangle.
     * @param width Width of the bounding rectangle.
     * @param height Height of the bounding rectangle.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawOval
     */
    public void g2DrawOval( int x, int y, int width, int height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawOval( x, y, width, height );
    }
    
    /**
     * Draws an arc.
     * Mapping for the drawArc method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the bounding rectangle.
     * @param y Y coordinate of the upper-left vertex of the bounding rectangle.
     * @param width Width of the bounding rectangle.
     * @param height Height of the bounding rectangle.
     * @param startAngle Start angle in degrees.
     * @param arcAngle Arc angle size in degrees, counter-clockwise.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawOval
     */
    public void g2DrawArc( int x, int y, int width, int height, int startAngle, int arcAngle, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawArc(x, y, width, height, startAngle, arcAngle );
    }
    
    /**
     * Draws a polygon.
     * Mapping for the drawPolygon method of Graphics.
     * 
     * @param polygon The polygon.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawPolygon
     */
    public void g2DrawPolygon( java.awt.Polygon polygon, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawPolygon( polygon );
    }
    
    /**
     * Draws a polygon.
     * Mapping for the drawPolygon method of Graphics.
     * 
     * @param xVertices Array of x coordinates of the vertices.
     * @param yVertices Array of y coordinates of the vertices.
     * @param nVertices Number of vertices.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawPolygon
     */
    public void g2DrawPolygon( int[] xVertices, int[] yVertices, int nVertices, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawPolygon( xVertices, yVertices, nVertices );
    }
    
    /**
     * Draws a polyline.
     * Mapping for the drawPolyline method of Graphics.
     * 
     * @param xVertices Array of x coordinates of the vertices.
     * @param yVertices Array of y coordinates of the vertices.
     * @param nVertices Number of vertices.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawPolyline
     */
    public void g2DrawPolyline( int[] xVertices, int[] yVertices, int nVertices, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawPolyline( xVertices, yVertices, nVertices );
    }
    
    /**
     * Fills a shape.
     * Mapping for the fill method of Graphics2D.
     * 
     * @param shape The shape to be filled.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics2D#fill
     */
    public void g2Fill( Shape shape, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( shape );
    }
    
    /**
     * Fills a rectangle.
     * Mapping for the fillRect method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#fillRect
     */
    public void g2FillRect( int x, int y, int width, int height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fillRect( x, y, width, height );
    }
    
    /**
     * Fills a rounded rectangle.
     * Mapping for the fillRoundRect method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param arcWidth The width of the rounding arc.
     * @param arcHeight The height of the rounding arc.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#fillRoundRect
     */
    public void g2FillRoundRect( int x, int y, int width, int height, int arcWidth, int arcHeight, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fillRoundRect( x, y, width, height, arcWidth, arcHeight );
    }
    
    /**
     * Fills a "3D" rectangle.
     * Mapping for the fill3DRect method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     * @param raised Whether the 3D effect shows the rectangle raised. 
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#fill3DRect
     */
    public void g2Fill3DRect( int x, int y, int width, int height, boolean raised, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill3DRect( x, y, width, height, raised );
    }
    
    /**
     * Fills an ellipse.
     * Mapping for the fillOval method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the bounding rectangle.
     * @param y Y coordinate of the upper-left vertex of the bounding rectangle.
     * @param width Width of the bounding rectangle.
     * @param height Height of the bounding rectangle.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#fillOval
     */
    public void g2FillOval( int x, int y, int width, int height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fillOval( x, y, width, height );
    }
    
    /**
     * Fills an arc.
     * Mapping for the fillArc method of Graphics.
     * 
     * @param x X coordinate of the upper-left vertex of the bounding rectangle.
     * @param y Y coordinate of the upper-left vertex of the bounding rectangle.
     * @param width Width of the bounding rectangle.
     * @param height Height of the bounding rectangle.
     * @param startAngle Start angle in degrees.
     * @param arcAngle Arc angle size in degrees, counter-clockwise.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#fillArc
     */
    public void g2FillArc( int x, int y, int width, int height, int startAngle, int arcAngle, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fillArc( x, y, width, height, startAngle, arcAngle );
    }
    
    /**
     * Fills a polygon.
     * Mapping for the fillPolygon method of Graphics.
     * 
     * @param polygon The polygon.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#fillPolygon
     */
    public void g2FillPolygon( java.awt.Polygon polygon, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fillPolygon( polygon );
    }
    
    /**
     * Fills a polygon.
     * Mapping for the fillPolygon method of Graphics.
     * 
     * @param xVertices Array of x coordinates of the vertices.
     * @param yVertices Array of y coordinates of the vertices.
     * @param nVertices Number of vertices.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#fillPolygon
     */
    public void g2FillPolygon( int[] xVertices, int[] yVertices, int nVertices, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fillPolygon( xVertices, yVertices, nVertices );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics2D.
     * 
     * @param image The image.
     * @param transform The transformation to be applied to the image.
     * @param obs An image observer.
     * @see java.awt.Graphics2D#drawImage
     */
    public void g2DrawImage( java.awt.Image image, AffineTransform transform, ImageObserver obs ) {
        g2d.drawImage( image, transform, obs );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics2D.
     * 
     * @param image The image.
     * @param op The image operator.
     * @param x X coordinate of the image drawing position. 
     * @param y Y coordinate of the image drawing position.
     * @see java.awt.Graphics2D#drawImage
     */
    public void g2DrawImage( BufferedImage image, BufferedImageOp op, int x, int y ) {
        g2d.drawImage( image, op, x, y );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics.
     * 
     * @param image The image.
     * @param x X coordinate of the image drawing position. 
     * @param y Y coordinate of the image drawing position.
     * @param obs An image observer.
     * @see java.awt.Graphics#drawImage
     */
    public void g2DrawImage( java.awt.Image image, int x, int y, ImageObserver obs ) {
        g2d.drawImage( image, x, y, obs );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics.
     * 
     * @param image The image.
     * @param x X coordinate of the image drawing position. 
     * @param y Y coordinate of the image drawing position.
     * @param bgColor The background color.
     * @param obs An image observer.
     * @see java.awt.Graphics#drawImage
     */
    public void g2DrawImage( java.awt.Image image, int x, int y, Color bgColor, ImageObserver obs ) {
        g2d.drawImage( image, x, y, bgColor, obs );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics.
     * 
     * @param image The image.
     * @param x X coordinate of the image drawing position. 
     * @param y Y coordinate of the image drawing position.
     * @param width Width of the image portion to be rendered.
     * @param height Height of the image portion to be rendered.
     * @param obs An image observer.
     * @see java.awt.Graphics#drawImage
     */
    public void g2DrawImage( java.awt.Image image, int x, int y, int width, int height, ImageObserver obs ) {
        g2d.drawImage( image, x, y, width, height, obs );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics.
     * 
     * @param image The image.
     * @param x X coordinate of the image drawing position. 
     * @param y Y coordinate of the image drawing position.
     * @param width Width of the image portion to be rendered.
     * @param height Height of the image portion to be rendered.
     * @param bgColor The background color.
     * @param obs An image observer.
     * @see java.awt.Graphics#drawImage
     */
    public void g2DrawImage( java.awt.Image image, int x, int y, int width, int height, Color bgColor, ImageObserver obs ) {
        g2d.drawImage( image, x, y, width, height, bgColor, obs );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics.
     * 
     * @param image The image.
     * @param dx1 X coordinate of the upper-left vertex of the destination rectangle.
     * @param dy1 Y coordinate of the upper-left vertex of the destination rectangle.
     * @param dx2 X coordinate of the lower-right vertex of the destination rectangle.
     * @param dy2 Y coordinate of the lower-right vertex of the destination rectangle.
     * @param sx1 X coordinate of the upper-left vertex of the source rectangle.
     * @param sy1 Y coordinate of the upper-left vertex of the source rectangle.
     * @param sx2 X coordinate of the lower-right vertex of the source rectangle.
     * @param sy2 Y coordinate of the lower-right vertex of the source rectangle.
     * @param obs An image observer.
     * @see java.awt.Graphics#drawImage
     */
    public void g2DrawImage( java.awt.Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver obs ) {
        g2d.drawImage( image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, obs );
    }
    
    /**
     * Draws an image.
     * Mapping for the drawImage method of Graphics.
     * 
     * @param image The image.
     * @param dx1 X coordinate of the upper-left vertex of the destination rectangle.
     * @param dy1 Y coordinate of the upper-left vertex of the destination rectangle.
     * @param dx2 X coordinate of the lower-right vertex of the destination rectangle.
     * @param dy2 Y coordinate of the lower-right vertex of the destination rectangle.
     * @param sx1 X coordinate of the upper-left vertex of the source rectangle.
     * @param sy1 Y coordinate of the upper-left vertex of the source rectangle.
     * @param sx2 X coordinate of the lower-right vertex of the source rectangle.
     * @param sy2 Y coordinate of the lower-right vertex of the source rectangle.
     * @param bgColor The background color.
     * @param obs An image observer.
     * @see java.awt.Graphics#drawImage
     */
    public void g2DrawImage( java.awt.Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgColor, ImageObserver obs ) {
        g2d.drawImage( image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgColor, obs );
    }
    
    /**
     * Draws a renderable image.
     * Mapping for the drawRenderableImage method of Graphics2D.
     * 
     * @param image The image.
     * @param transform The transformation to be applied to the image.
     * @see java.awt.Graphics2D#drawRenderableImage
     */
    public void g2DrawRenderableImage( RenderableImage image, AffineTransform transform ) {
        g2d.drawRenderableImage( image, transform );
    }
    
    /**
     * Draws a rendered image.
     * Mapping for the drawRenderedImage method of Graphics2D.
     * 
     * @param image The image.
     * @param transform The transformation to be applied to the image.
     * * @see java.awt.Graphics2D#drawRenderedImage
     */
    public void g2DrawRenderedImage( RenderedImage image, AffineTransform transform ) {
        g2d.drawRenderedImage( image, transform );
    }
    
    /**
     * Draws characters.
     * Mapping for the drawChars method of Graphics.
     * 
     * @param data Characters to be drawn.
     * @param offset Data offset in the array.
     * @param length Number of characters to be drawn.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawChars
     */
    public void g2DrawChars( char[] data, int offset, int length, int x, int y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawChars( data, offset, length, x, y );
    }
    
    /**
     * Draws a GlyphVector.
     * Mapping for the drawGlyphVector method of Graphics2D.
     * 
     * @param gv The GlyphVector.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics2D#drawGlyphVector
     */
    public void g2DrawGlyphVector( GlyphVector gv, float x, float y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawGlyphVector( gv, x, y );
    }
    
    /**
     * Draws a String.
     * Mapping for the drawString method of Graphics.
     * 
     * @param iterator The character iterator.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawString
     */
    public void g2DrawString( AttributedCharacterIterator iterator, int x, int y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawString( iterator, x, y );
    }
    
    /**
     * Draws a String.
     * Mapping for the drawString method of Graphics2D.
     * 
     * @param iterator The character iterator.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics2D#drawString
     */
    public void g2DrawString( AttributedCharacterIterator iterator, float x, float y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawString( iterator, x, y );
    }
    
    /**
     * Draws a String.
     * Mapping for the drawString method of Graphics.
     * 
     * @param string The string to be drawn.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics#drawString
     */
    public void g2DrawString( String string, int x, int y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawString( string, x, y );
    }
    
    /**
     * Draws a String.
     * Mapping for the drawString method of Graphics2D.
     * 
     * @param string The string to be drawn.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     * @param paint Paint for drawing.
     * @see java.awt.Graphics2D#drawString
     */
    public void g2DrawString( String string, float x, float y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.drawString( string, x, y );
    }
    
    
    
    //**************************************************************************
    // Methods for taking screenshots.
    //**************************************************************************
    
    /**
     * Takes a screenshot of the current engine, respecting a bounding area, returning an image.
     * 
     * @param x X coordinate of the upper-left vertex of the capture rectangle.
     * @param y Y coordinate of the upper-left vertex of the capture rectangle.
     * @param width Width of the capture rectangle.
     * @param height Height of the capture rectangle.
     * @return An image with the capture.
     */
    public Image takeScreenshot( double x, double y, double width, double height ) {
        
        try {
            
            java.awt.Rectangle fBounds = getBounds();
            Insets insets = getInsets();

            int xStart = fBounds.x + insets.left + (int) x;
            int yStart = fBounds.y + insets.top + (int) y;

            Robot rb = new Robot();
            Image image = new Image( rb.createScreenCapture( new java.awt.Rectangle( xStart, yStart, (int) width, (int) height ) ) );
            
            return image;
            
        } catch ( AWTException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
        
        return ImageUtils.createTextImage( "error", 20, Font.BOLD, EngineFrame.WHITE, EngineFrame.BLACK );
        
    }
    
    /**
     * Takes a screenshot of the current engine, respecting a bounding area, returning an image.
     * 
     * @param source The capture rectangle.
     * @return An image with the capture.
     */
    public Image takeScreenshot( Rectangle source ) {
        return takeScreenshot( source.x, source.y, source.width, source.height );
    }
    
    /**
     * Takes a full screenshot of the current engine, returning an image.
     * 
     * @return An image with the capture.
     */
    public Image takeScreenshot() {
        Dimension dpSize = drawingPanel.getSize();
        return takeScreenshot( 0, 0, dpSize.width, dpSize.height );
    }
    
    /**
     * Saves a screenshot of the current engine, respecting a bounding area.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param outputFile Output file.
     * @param x X coordinate of the upper-left vertex of the capture rectangle.
     * @param y Y coordinate of the upper-left vertex of the capture rectangle.
     * @param width Width of the capture rectangle.
     * @param height Height of the capture rectangle.
     */
    public void saveScreenshot( String formatName, File outputFile, double x, double y, double width, double height ) {
        try {
            ImageIO.write( takeScreenshot( x, y, width, height ).buffImage, formatName, outputFile );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a screenshot of the current engine, respecting a bounding area.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param outputFile Output file.
     * @param source The capture rectangle.
     */
    public void saveScreenshot( String formatName, File outputFile, Rectangle source ) {
        try {
            ImageIO.write( takeScreenshot( source ).buffImage, formatName, outputFile );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a full screenshot of the current engine.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param outputFile Output file.
     */
    public void saveScreenshot( String formatName, File outputFile ) {
        try {
            ImageIO.write( takeScreenshot().buffImage, formatName, outputFile );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a screenshot of the current engine, respecting a bounding area.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param imageOutputStream Image output stream.
     * @param x X coordinate of the upper-left vertex of the capture rectangle.
     * @param y Y coordinate of the upper-left vertex of the capture rectangle.
     * @param width Width of the capture rectangle.
     * @param height Height of the capture rectangle.
     */
    public void saveScreenshot( String formatName, ImageOutputStream imageOutputStream, double x, double y, double width, double height ) {
        try {
            ImageIO.write( takeScreenshot( x, y, width, height ).buffImage, formatName, imageOutputStream );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a screenshot of the current engine, respecting a bounding area.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param imageOutputStream Image output stream.
     * @param source The capture rectangle.
     */
    public void saveScreenshot( String formatName, ImageOutputStream imageOutputStream, Rectangle source ) {
        try {
            ImageIO.write( takeScreenshot( source ).buffImage, formatName, imageOutputStream );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a full screenshot of the current engine.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param imageOutputStream Image output stream.
     */
    public void saveScreenshot( String formatName, ImageOutputStream imageOutputStream ) {
        try {
            ImageIO.write( takeScreenshot().buffImage, formatName, imageOutputStream );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a screenshot of the current engine, respecting a bounding area.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param outputStream Output stream.
     * @param x X coordinate of the upper-left vertex of the capture rectangle.
     * @param y Y coordinate of the upper-left vertex of the capture rectangle.
     * @param width Width of the capture rectangle.
     * @param height Height of the capture rectangle.
     */
    public void saveScreenshot( String formatName, OutputStream outputStream, double x, double y, double width, double height ) {
        try {
            ImageIO.write( takeScreenshot( x, y, width, height ).buffImage, formatName, outputStream );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a screenshot of the current engine, respecting a bounding area.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param outputStream Output stream.
     * @param source The capture rectangle.
     */
    public void saveScreenshot( String formatName, OutputStream outputStream, Rectangle source ) {
        try {
            ImageIO.write( takeScreenshot( source ).buffImage, formatName, outputStream );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    /**
     * Saves a full screenshot of the current engine.
     * 
     * @param formatName Format name (bmp, BMP, gif, GIF, jpg, JPG, jpeg, JPEG, png, PNG, tif, TIF, tiff, TIFF, wbmp, WBMP).
     * @param outputStream Output stream.
     */
    public void saveScreenshot( String formatName, OutputStream outputStream ) {
        try {
            ImageIO.write( takeScreenshot().buffImage, formatName, outputStream );
        } catch ( IOException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
    }
    
    
    
    //**************************************************************************
    // Methods for loading fonts.
    //**************************************************************************
    
    /**
     * Loads a new font and registers it in the GraphicsEnvironment.
     * 
     * @param filePath Font file path.
     * @return The loaded font.
     */
    public static Font loadFont( String filePath ) {
        return FontUtils.loadFont( filePath );
    }
    
    /**
     * Loads a new font and registers it in the GraphicsEnvironment.
     * 
     * @param fontFile The font file.
     * @return The loaded font.
     */
    public static Font loadFont( File fontFile ) {
        return FontUtils.loadFont( fontFile );
    }
    
    /**
     * Loads a new font and registers it in the GraphicsEnvironment.
     * 
     * @param inputStream InputStream for the font.
     * @param fontType The font type (Font.TRUETYPE_FONT or Font.TYPE1_FONT).
     * @return The loaded font.
     */
    public static Font loadFont( InputStream inputStream, int fontType ) {
        return FontUtils.loadFont( inputStream, fontType );
    }
    
    
    
    //**************************************************************************
    // Methods for managing the mouse cursor.
    //**************************************************************************
    
    /**
     * Configures the mouse cursor.
     * 
     * @param cursor The cursor identifier.
     */
    public void setMouseCursor( int cursor ) {
        currentCursor = Cursor.getPredefinedCursor( cursor );
        drawingPanel.setCursor( currentCursor );
    }
    
    /**
     * Shows the cursor.
     */
    public void showCursor() {
        drawingPanel.setCursor( currentCursor );
    }
    
    /**
     * Hides the cursor.
     */
    public void hideCursor() {
        drawingPanel.setCursor( INVISIBLE_CURSOR );
    }
    
    /**
     * Returns whether the cursor is hidden.
     * 
     * @return true if the cursor is hidden, false otherwise.
     */
    public boolean isCursorHidden() {
        return drawingPanel.getCursor() == INVISIBLE_CURSOR;
    }
    
    
    
    //**************************************************************************
    // Methods for managing gamepads.
    //**************************************************************************
    
    /**
     * Checks whether a gamepad is available.
     * 
     * @param gamepadId The gamepad identifier.
     * @return True if the gamepad is available, false otherwise.
     */
    public boolean isGamepadAvailable( int gamepadId ) {
        return gpInputManager.isGamepadAvailable( gamepadId );
    }

    /**
     * Gets the internal name of the gamepad.
     * 
     * @param gamepadId The gamepad identifier.
     * @return The internal name of the gamepad.
     */
    public String getGamepadName( int gamepadId ) {
        return gpInputManager.getGamepadName( gamepadId );
    }

    /**
     * Returns whether a gamepad button was pressed once.
     * 
     * Note: the entire gamepad management mechanism is based on an
     * implementation where the bottom triggers, both left (L2/LT)
     * and right (R2/RT), work by varying the Z axis in the range -1.0 to 1.0.
     * Using old/standard controllers for Windows.
     * 
     * The left trigger varies Z from 0.0 (not pressed) to 1.0 (fully pressed).
     * The right trigger varies Z from 0.0 (not pressed) to -1.0 (fully pressed).
     * 
     * When both triggers are not pressed, the Z value is 0.0, and
     * when both are fully pressed, the Z value is also 0.0, since
     * they cancel each other out.
     * 
     * Therefore, pressed/not-pressed checks do not work
     * properly for these buttons.
     * 
     * @param gamepadId The gamepad identifier.
     * @param button The button.
     * @return True if the button was pressed once, false otherwise.
     */
    public boolean isGamepadButtonPressed( int gamepadId, int button ) {
        return gpInputManager.isGamepadButtonPressed( gamepadId, button );
    }

    /**
     * Returns whether a gamepad button is currently pressed.
     * 
     * 
     * Note: the entire gamepad management mechanism is based on an
     * implementation where the bottom triggers, both left (L2/LT)
     * and right (R2/RT), work by varying the Z axis in the range -1.0 to 1.0.
     * Using old/standard controllers for Windows.
     * 
     * The left trigger varies Z from 0.0 (not pressed) to 1.0 (fully pressed).
     * The right trigger varies Z from 0.0 (not pressed) to -1.0 (fully pressed).
     * 
     * When both triggers are not pressed, the Z value is 0.0, and
     * when both are fully pressed, the Z value is also 0.0, since
     * they cancel each other out.
     * 
     * Therefore, pressed/not-pressed checks do not work
     * properly for these buttons.
     * 
     * @param gamepadId The gamepad identifier.
     * @param button The button.
     * @return True if the button is pressed, false otherwise.
     */
    public boolean isGamepadButtonDown( int gamepadId, int button ) {
        return gpInputManager.isGamepadButtonDown( gamepadId, button );
    }

    /**
     * Returns whether a gamepad button was released.
     * 
     * Note: the entire gamepad management mechanism is based on an
     * implementation where the bottom triggers, both left (L2/LT)
     * and right (R2/RT), work by varying the Z axis in the range -1.0 to 1.0.
     * Using old/standard controllers for Windows.
     * 
     * The left trigger varies Z from 0.0 (not pressed) to 1.0 (fully pressed).
     * The right trigger varies Z from 0.0 (not pressed) to -1.0 (fully pressed).
     * 
     * When both triggers are not pressed, the Z value is 0.0, and
     * when both are fully pressed, the Z value is also 0.0, since
     * they cancel each other out.
     * 
     * Therefore, pressed/not-pressed checks do not work
     * properly for these buttons.
     * 
     * @param gamepadId The gamepad identifier.
     * @param button The button.
     * @return True if the button was released, false otherwise.
     */
    public boolean isGamepadButtonReleased( int gamepadId, int button ) {
        return gpInputManager.isGamepadButtonReleased( gamepadId, button );
    }

    /**
     * Returns whether a gamepad button is not pressed.
     * 
     * Note: the entire gamepad management mechanism is based on an
     * implementation where the bottom triggers, both left (L2/LT)
     * and right (R2/RT), work by varying the Z axis in the range -1.0 to 1.0.
     * Using old/standard controllers for Windows.
     * 
     * The left trigger varies Z from 0.0 (not pressed) to 1.0 (fully pressed).
     * The right trigger varies Z from 0.0 (not pressed) to -1.0 (fully pressed).
     * 
     * When both triggers are not pressed, the Z value is 0.0, and
     * when both are fully pressed, the Z value is also 0.0, since
     * they cancel each other out.
     * 
     * Therefore, pressed/not-pressed checks do not work
     * properly for these buttons.
     * 
     * @param gamepadId The gamepad identifier.
     * @param button The button.
     * @return True if the button is not pressed, false otherwise.
     */
    public boolean isGamepadButtonUp( int gamepadId, int button ) {
        return gpInputManager.isGamepadButtonUp( gamepadId, button );
    }

    /**
     * Returns the movement value of a gamepad axis.
     * 
     * Note: the entire gamepad management mechanism is based on an
     * implementation where the bottom triggers, both left (L2/LT)
     * and right (R2/RT), work by varying the Z axis in the range -1.0 to 1.0.
     * Using old/standard controllers for Windows.
     * 
     * The left trigger varies Z from 0.0 (not pressed) to 1.0 (fully pressed).
     * The right trigger varies Z from 0.0 (not pressed) to -1.0 (fully pressed).
     * 
     * When both triggers are not pressed, the Z value is 0.0, and
     * when both are fully pressed, the Z value is also 0.0, since
     * they cancel each other out.
     * 
     * Therefore, pressed/not-pressed checks do not work
     * properly for these buttons.
     * 
     * @param gamepadId The gamepad identifier.
     * @param axis The axis.
     * @return The movement value of a gamepad axis.
     */
    public double getGamepadAxisMovement( int gamepadId, int axis ) {
        return gpInputManager.getGamepadAxisMovement( gamepadId, axis );
    }
    
    
    
    //**************************************************************************
    // Methods for camera control
    //**************************************************************************
    
    /**
     * Starts 2D mode using the camera.
     * 
     * @param camera The camera to be used.
     */
    public void beginMode2D( Camera2D camera ) {
        
        if ( !mode2DActive ) {
            
            copyAndSaveGraphics2D();
            
            // reference: MathUtils.getCameraMatrix2D
            g2d.translate( camera.offset.x, camera.offset.y );
            g2d.scale( camera.zoom, camera.zoom );
            g2d.rotate( Math.toRadians( camera.rotation ) );
            g2d.translate( -camera.target.x, -camera.target.y );
            
            mode2DActive = true;
            
        }
        
    }
    
    /**
     * Ends 2D mode, returning to the original mode
     */
    public void endMode2D() {
        if ( mode2DActive ) {
            disposeAndRestoreGraphics2D();
            mode2DActive = false;
        }
    }
    
    
    
    //**************************************************************************
    // Methods for drawing clip control
    //**************************************************************************
    
    /**
     * Starts scissor mode for a rectangle.
     * 
     * @param x X coordinate of the upper-left vertex of the rectangle.
     * @param y Y coordinate of the upper-left vertex of the rectangle.
     * @param width Width.
     * @param height Height.
     */
    public void beginScissorMode( double x, double y, double width, double height ) {
        copyAndSaveGraphics2D();
        g2d.setClip( (int) x, (int) y, (int) width, (int) height );
    }
    
    /**
     * Starts scissor mode for an arc.
     * 
     * @param arc The arc.
     */
    public void beginScissorMode( Arc arc ) {
        copyAndSaveGraphics2D();
        double extent = arc.endAngle - arc.startAngle;
        g2d.setClip( new Arc2D.Double( arc.x - arc.radiusH, arc.y - arc.radiusV, arc.radiusH * 2, arc.radiusV * 2, -arc.startAngle, -extent, Arc2D.CHORD ) );
    }
    
    /**
     * Starts scissor mode for a circle.
     * 
     * @param circle The circle.
     */
    public void beginScissorMode( Circle circle ) {
        copyAndSaveGraphics2D();
        g2d.setClip( new Ellipse2D.Double( circle.x - circle.radius, circle.y - circle.radius, circle.radius * 2, circle.radius * 2 ) );
    }
    
    /**
     * Starts scissor mode for a circle sector.
     * 
     * @param circleSector The circle sector.
     */
    public void beginScissorMode( CircleSector circleSector ) {
        copyAndSaveGraphics2D();
        double extent = circleSector.endAngle - circleSector.startAngle;
        g2d.setClip( new Arc2D.Double( circleSector.x - circleSector.radius, circleSector.y - circleSector.radius, circleSector.radius * 2, circleSector.radius * 2, -circleSector.startAngle, -extent, Arc2D.PIE ) );
    }
    
    /**
     * Starts scissor mode for a cubic Bézier curve.
     * 
     * @param cubicCurve The cubic Bézier curve.
     */
    public void beginScissorMode( CubicCurve cubicCurve ) {
        copyAndSaveGraphics2D();
        g2d.setClip( new CubicCurve2D.Double( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2 ) );
    }
    
    /**
     * Starts scissor mode for an ellipse.
     * 
     * @param ellipse The ellipse.
     */
    public void beginScissorMode( Ellipse ellipse ) {
        copyAndSaveGraphics2D();
        g2d.setClip( new Ellipse2D.Double( ellipse.x - ellipse.radiusH, ellipse.y - ellipse.radiusV, ellipse.radiusH * 2, ellipse.radiusV * 2 ) );
    }
    
    /**
     * Starts scissor mode for an ellipse sector.
     * 
     * @param ellipseSector The ellipse sector.
     */
    public void beginScissorMode( EllipseSector ellipseSector ) {
        copyAndSaveGraphics2D();
        double extent = ellipseSector.endAngle - ellipseSector.startAngle;
        g2d.setClip( new Arc2D.Double( ellipseSector.x - ellipseSector.radiusH, ellipseSector.y - ellipseSector.radiusV, ellipseSector.radiusH * 2, ellipseSector.radiusV * 2, -ellipseSector.startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Starts scissor mode for a path.
     * 
     * @param path The path.
     */
    public void beginScissorMode( Path path ) {
        copyAndSaveGraphics2D();
        g2d.setClip( path.path );
    }
    
    /**
     * Starts scissor mode for a polygon.
     * 
     * @param polygon The polygon.
     */
    public void beginScissorMode( Polygon polygon ) {
        copyAndSaveGraphics2D();
        g2d.setClip( DrawingUtils.createPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation ) );
    }
    
    /**
     * Starts scissor mode for a quadratic Bézier curve.
     * 
     * @param quadCurve The quadratic Bézier curve.
     */
    public void beginScissorMode( QuadCurve quadCurve ) {
        copyAndSaveGraphics2D();
        g2d.setClip( new QuadCurve2D.Double( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2 ) );
    }
    
    /**
     * Starts scissor mode for a rectangle.
     * 
     * @param rectangle The rectangle.
     */
    public void beginScissorMode( Rectangle rectangle )  {
        beginScissorMode( rectangle.x, rectangle.y, rectangle.width, rectangle.height );
    }
    
    /**
     * Starts scissor mode for a ring.
     * 
     * @param ring The ring.
     */
    public void beginScissorMode( Ring ring ) {
        copyAndSaveGraphics2D();
        g2d.setClip( DrawingUtils.createRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle ) );
    }
    
    /**
     * Starts scissor mode for a rounded rectangle.
     * 
     * @param roundRectangle The rounded rectangle.
     */
    public void beginScissorMode( RoundRectangle roundRectangle ) {
        copyAndSaveGraphics2D();
        g2d.setClip( new RoundRectangle2D.Double( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, roundRectangle.roundness ) );
    }
    
    /**
     * Starts scissor mode for a star.
     * 
     * @param star The star.
     */
    public void beginScissorMode( Star star ) {
        copyAndSaveGraphics2D();
        g2d.setClip( DrawingUtils.createStar( star.x, star.y, star.tips, star.radius, star.rotation ) );
    }
    
    /**
     * Starts scissor mode for a triangle.
     * 
     * @param triangle The triangle.
     */
    public void beginScissorMode( Triangle triangle ) {
        copyAndSaveGraphics2D();
        g2d.setClip( DrawingUtils.createTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3 ) );
    }
    
    /**
     * Ends the current scissor mode.
     */
    public void endScissorMode() {
        disposeAndRestoreGraphics2D();
    }
    
    
    
    //**************************************************************************
    // Methods for managing graphics contexts.
    //**************************************************************************
    
    /**
     * Creates a copy of the current graphics context and saves a reference
     * to the previous graphics context.
     */
    private void copyAndSaveGraphics2D() {
        Graphics2D newG2d = (Graphics2D) g2d.create();
        g2dStack.push( g2d );
        g2d = newG2d;
    }
    
    /**
     * Discards the current graphics context and restores the previously
     * saved graphics context.
     */
    private void disposeAndRestoreGraphics2D() {
        g2d.dispose();
        g2d = g2dStack.pop();
    }
    
    
    
    //**************************************************************************
    // Methods for sound and music control
    //**************************************************************************
    
    /**
     * Loads a sound from a file.
     * 
     * @param filePath File path.
     * @return A new sound.
     */
    public static Sound loadSound( String filePath ) {
        return new Sound( filePath );
    }
    
    /**
     * Loads a sound from an input stream.
     * 
     * @param is Input stream.
     * @return A new sound.
     */
    public static Sound loadSound( InputStream is ) {
        return new Sound( is );
    }
    
    /**
     * Loads a sound from a URL.
     * 
     * @param url URL.
     * @return A new sound.
     */
    public static Sound loadSound( URL url ) {
        return new Sound( url );
    }
    
    /**
     * Plays the sound.
     * 
     * @param sound The sound.
     */
    public static void playSound( Sound sound ) {
        sound.play();
    }
    
    /**
     * Loads music from a file.
     * 
     * @param filePath File path.
     * @return A new music instance.
     */
    public static Music loadMusic( String filePath ) {
        return new Music( filePath );
    }
    
    /**
     * Loads music from an input stream.
     * 
     * @param is Input stream.
     * @return A new music instance.
     */
    public static Music loadMusic( InputStream is ) {
        return new Music( is );
    }
    
    /**
     * Loads music from a URL.
     * 
     * @param url URL.
     * @return A new music instance.
     */
    public static Music loadMusic( URL url ) {
        return new Music( url );
    }
    
    /**
     * Unloads music.
     * 
     * @param music The music to be unloaded.
     */
    public static void unloadMusic( Music music ) {
        music.unload();
    }
    
    /**
     * Plays music.
     * 
     * @param music The music.
     */
    public static void playMusic( Music music ) {
        music.play();
    }
    
    /**
     * Stops playing music.
     * 
     * @param music The music.
     */
    public static void stopMusic( Music music ) {
        music.stop();
    }
    
    /**
     * Pauses music.
     * 
     * @param music The music.
     */
    public void pauseMusic( Music music ) {
        music.pause();
    }
    
    /**
     * Resumes playing music.
     * 
     * @param music The music.
     */
    public void resumeMusic( Music music ) {
        music.resume();
    }
    
    /**
     * Checks whether the music is playing.
     * 
     * @param music The music.
     * @return True if the music is playing, false otherwise.
     */
    public boolean isMusicPlaying( Music music ) {
        return music.isPlaying();
    }
    
    /**
     * Checks whether the music is stopped.
     * 
     * @param music The music.
     * @return True if the music is stopped, false otherwise.
     */
    public boolean isMusicStopped( Music music ) {
        return music.isStopped();
    }
    
    /**
     * Checks whether the music is paused.
     * 
     * @param music The music.
     * @return True if the music is paused, false otherwise.
     */
    public boolean isMusicPaused( Music music ) {
        return music.isPaused();
    }
    
    /**
     * Checks whether the music is being seeked.
     * 
     * @param music The music.
     * @return True if the music is being seeked, false otherwise.
     */
    public boolean isMusicSeeking( Music music ) {
        return music.isSeeking();
    }
    
    /**
     * Sets the music volume.
     * 
     * @param music The music.
     * @param volume The music volume, ranging from 0.0 to 1.0.
     */
    public void setMusicVolume( Music music, double volume ) {
        music.setVolume( volume );
    }
    
    /**
     * Seeks to a position in the music.
     * 
     * @param music The music.
     * @param position Position in seconds of the desired moment.
     */
    public static void seekMusic( Music music, int position ) {
        music.seek( position );
    }
    
    /**
     * Gets the duration of the music.
     * 
     * @param music The music.
     * @return Duration of the music in seconds.
     */
    public static int getMusicTimeLength( Music music ) {
        return music.getTimeLength();
    }
    
    /**
     * Gets the playback time of the music.
     * 
     * @param music The music.
     * @return The playback time in seconds.
     */
    public int getMusicTimePlayed( Music music ) {
        return music.getTimePlayed();
    }
    
    
    
    //**************************************************************************
    // Private inner classes.
    //**************************************************************************
    
    /**
     * Inner class that encapsulates the drawing process.
     */
    private class DrawingPanel extends JPanel {

        public DrawingPanel( boolean invisibleBackground ) {
            setBackground( null );
            setIgnoreRepaint( true );
            if ( invisibleBackground ) {
                setBackground( new Color( 0, 0, 0, 1 ) );
                setOpaque( false );
            }
        }
        
        @Override
        public void paintComponent( Graphics g ) {

            super.paintComponent( g );
            g2d = (Graphics2D) g.create();

            g2d.setFont( defaultFont );
            g2d.setStroke( defaultStroke );
            
            g2d.clearRect( 0, 0, getWidth(), getHeight() );

            if ( antialiasing ) {
                g2d.setRenderingHint( 
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );
            }
            
            try {
                draw();
            } catch ( RuntimeException exc ) {
                traceLogError( CoreUtils.stackTraceToString( exc ) );
            }
            
            g2d.dispose();

        }

    }

    
    
    /**
     * Inner class for managing keyboard and mouse input.
     * Events are mapped to GameActions.
     *
     * @author Prof. Dr. David Buzatto
     */
    private class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
        
        /*
         * Códigos do mouse (apenas para diferenciar as operações de rolagem
         * da roda do mouse).
         */
        
        /**
         * Constant indicating a mouse wheel scroll-up operation.
         */
        public static final int MOUSE_WHEEL_UP = 3000;
        
        /**
         * Constant indicating a mouse wheel scroll-down operation.
         */
        public static final int MOUSE_WHEEL_DOWN = 4000;

        private Map<Integer, List<GameAction>> keyActionsMap = new HashMap<>();
        private Map<Integer, List<GameAction>> mouseActionsMap = new HashMap<>();

        private java.awt.Point mouseLocation;
        private java.awt.Point centerLocation;
        private java.awt.Component comp;
        private Robot robot;
        private boolean isRecentering;
        
        /**
         * Creates a new InputManager that listens to input from a specific
         * component.
         */
        public InputManager( java.awt.Component comp ) {

            this.comp = comp;
            mouseLocation = new java.awt.Point();
            centerLocation = new java.awt.Point();

            // registers keyboard and mouse listeners
            comp.addKeyListener( this );
            comp.addMouseListener( this );
            comp.addMouseMotionListener( this );
            comp.addMouseWheelListener( this );
            
            /*
             * allows TAB key and other keys normally used
             * by focus traversal to be entered.
             */
            comp.setFocusTraversalKeysEnabled( false );

        }
        
        /**
         * Sets the cursor on the InputManager component.
         */
        @SuppressWarnings( "unused" )
        public void setCursor( Cursor cursor ) {
            comp.setCursor( cursor );
        }

        
        /**
         * Configures whether relative mouse mode is on or off.
         * In relative mouse mode, the cursor is "locked" in the center
         * of the screen, and only mouse movement changes are measured.
         * In normal mode, the mouse is free to move across the screen.
         */
        @SuppressWarnings( "unused" )
        public void setRelativeMouseMode( boolean mode ) {

            if ( mode == isRelativeMouseMode() ) {
                return;
            }

            if ( mode ) {
                try {
                    robot = new Robot();
                    recenterMouse();
                }
                catch ( AWTException exc ) {
                    // could not create a Robot
                    robot = null;
                }
            } else {
                robot = null;
            }

        }
        
        /**
         * Returns whether relative mouse mode is on or off.
         */
        public boolean isRelativeMouseMode() {
            return ( robot != null );
        }

        /**
         * Maps a GameAction to a specific key.
         * Key codes are defined in java.awt.KeyEvent, but
         * remapped to constants matching Raylib's.
         * Note: there is no overriding of actions, as they are lists.
         */
        public void mapToKey( GameAction gameAction, int keyCode ) {
            if ( !keyActionsMap.containsKey(keyCode) ) {
                keyActionsMap.put( keyCode, new ArrayList<>() );
            }
            keyActionsMap.get( keyCode ).add( gameAction );
        }
        
        /**
         * Maps a GameAction to a specific mouse action.
         * Mouse codes are defined here in the engine constants.
         * Note: there is no overriding of actions, as they are lists.
         */
        public void mapToMouse( GameAction gameAction, int mouseCode ) {
            if ( !mouseActionsMap.containsKey(mouseCode) ) {
                mouseActionsMap.put( mouseCode, new ArrayList<>() );
            }
            mouseActionsMap.get( mouseCode ).add( gameAction );
        }
        
        /**
         * Clears all mapped keys and mouse actions for this GameAction.
         */
        @SuppressWarnings( "unused" )
        public void clearMap( GameAction gameAction ) {
            keyActionsMap.clear();
            mouseActionsMap.clear();
            gameAction.reset();
        }
        
        /**
         * Resets all GameActions so they appear as if they were not executed.
         */
        @SuppressWarnings( "unused" )
        public void resetAllGameActions() {

            for ( Map.Entry<Integer, List<GameAction>> e : keyActionsMap.entrySet() ) {
                for ( GameAction ga : e.getValue() ) {
                    ga.reset();
                }
            }

            for ( Map.Entry<Integer, List<GameAction>> e : mouseActionsMap.entrySet() ) {
                for ( GameAction ga : e.getValue() ) {
                    ga.reset();
                }
            }

        }
        
        /**
         * Gets the name of a key code.
         */
        @SuppressWarnings( "unused" )
        public static String getKeyName( int keyCode ) {
            return KeyEvent.getKeyText( keyCode );
        }
        
        /**
         * Gets the name of a mouse code.
         */
        @SuppressWarnings( "unused" )
        public static String getMouseName( int mouseCode ) {

            switch ( mouseCode ) {
                    
                case MOUSE_BUTTON_LEFT: 
                    return "Mouse Button Left";
                    
                case MOUSE_BUTTON_RIGHT: 
                    return "Mouse Button Right";
                    
                case MOUSE_BUTTON_MIDDLE: 
                    return "Mouse Button Middle";

                case MOUSE_WHEEL_UP: 
                    return "Mouse Wheel Up";
                    
                case MOUSE_WHEEL_DOWN: 
                    return "Mouse Wheel Down";
                    
                default: 
                    return "Unknown mouse code " + mouseCode;
            }

        }

        /**
         * Gets the x position of the mouse.
         */
        public int getMouseX() {
            return mouseLocation.x;
        }
        
        /**
         * Gets the y position of the mouse.
         */
        public int getMouseY() {
            return mouseLocation.y;
        }

        /**
         * Uses the Robot class to attempt to position the mouse at the center of the screen.
         * Note that the use of the Robot class may not be possible on all
         * platforms.
         */
        private synchronized void recenterMouse() {

            if ( robot != null && comp.isShowing() ) {
                centerLocation.x = comp.getWidth() / 2;
                centerLocation.y = comp.getHeight() / 2;
                SwingUtilities.convertPointToScreen( centerLocation, comp );
                isRecentering = true;
                robot.mouseMove( centerLocation.x, centerLocation.y );
            }

        }
        
        /**
         * Returns the GameActions associated with the KeyEvent.
         */
        private List<GameAction> getKeyActions( KeyEvent e ) {

            int keyCode = e.getKeyCode();
            
            if ( keyCode == 0 ) {
                keyCode = e.getExtendedKeyCode();
            }

            if ( keyActionsMap.containsKey( keyCode ) ) {
                return keyActionsMap.get( keyCode );
            }

            return null;

        }

        /**
         * Returns the GameActions associated with the key code.
         */
        public List<GameAction> getKeyActions( int keyCode ) {

            if ( keyActionsMap.containsKey( keyCode ) ) {
                return keyActionsMap.get( keyCode );
            }

            return null;

        }

        /**
         * Gets a set with the codes of all currently pressed keys.
         * Returns a set with only the null code if no key has been pressed.
         * 
         * @return A set with the codes of the pressed keys.
         */
        public Set<Integer> getKeysFromPressedActions() {

            Set<Integer> keys = new HashSet<>();

            for ( Map.Entry<Integer, List<GameAction>> e : keyActionsMap.entrySet() ) {
                for ( GameAction ga : e.getValue() ) {
                    if ( ga.isPressed() ) {
                        keys.add( e.getKey() );
                    }
                }
            }

            if ( keys.isEmpty() ) {
                keys.add( KEY_NULL );
            }

            return keys;

        }
        
        /**
         * Consumes all key actions.
         */
        public void consumeKeyActions() {
            for ( Map.Entry<Integer, List<GameAction>> e : keyActionsMap.entrySet() ) {
                for ( GameAction ga : e.getValue() ) {
                    if ( ga.isPressed() ) {
                    }
                }
            }
        }
        
        /**
         * Gets the mouse code for the button specified in the MouseEvent
         */
        public static int getMouseButtonCode( MouseEvent e ) {

            switch ( e.getButton() ) {
                
                case MouseEvent.BUTTON1:
                    return MOUSE_BUTTON_LEFT;
                    
                case MouseEvent.BUTTON2:
                    return MOUSE_BUTTON_MIDDLE;
                    
                case MouseEvent.BUTTON3:
                    return MOUSE_BUTTON_RIGHT;
                    
                default:
                    return -1;

            }

        }
        
        /**
         * Returns the GameActions associated with the MouseEvent.
         */
        private List<GameAction> getMouseButtonActions( MouseEvent e ) {

            int mouseCode = getMouseButtonCode( e );

            if ( mouseCode != -1 ) {
                return mouseActionsMap.get( mouseCode );
            }

            return null;

        }

        @Override
        public void keyTyped( KeyEvent e ) {
            // ensures that the key is not processed by anyone else
            e.consume();
        }
        
        @Override
        public void keyPressed( KeyEvent e ) {

            List<GameAction> gameActions = getKeyActions( e );

            if ( gameActions != null ) {
                for ( GameAction ga : gameActions ) {
                    ga.press();
                }
            }

            // ensures that the key is not processed by anyone else
            e.consume();

        }

        @Override
        public void keyReleased( KeyEvent e ) {

            List<GameAction> gameActions = getKeyActions( e );

            if ( gameActions != null ) {
                for ( GameAction ga : gameActions ) {
                    ga.release();
                }
            }

            // ensures that the key is not processed by anyone else
            e.consume();

        }

        @Override
        public void mouseClicked( MouseEvent e ) {
            // does nothing
        }
        
        @Override
        public void mousePressed( MouseEvent e ) {

            List<GameAction> gameActions = getMouseButtonActions( e );

            if ( gameActions != null ) {
                for ( GameAction ga : gameActions ) {
                    ga.press();
                }
            }

        }

        @Override
        public void mouseReleased( MouseEvent e ) {

            List<GameAction> gameActions = getMouseButtonActions( e );

            if ( gameActions != null ) {
                for ( GameAction ga : gameActions ) {
                    ga.release();
                }
            }

        }

        @Override
        public void mouseEntered( MouseEvent e ) {
            mouseMoved( e );
        }
        
        @Override
        public void mouseExited( MouseEvent e ) {
            mouseMoved( e );
        }
        
        @Override
        public void mouseDragged( MouseEvent e ) {
            mouseMoved( e );
        }
        
        @Override
        public synchronized void mouseMoved( MouseEvent e ) {

            // this event is for recentering the mouse
            if ( isRecentering &&
                centerLocation.x == e.getX() &&
                centerLocation.y == e.getY() ) {
                isRecentering = false;
            } else {
                if ( isRelativeMouseMode() ) {
                    recenterMouse();
                }
            }

            mouseLocation.x = e.getX();
            mouseLocation.y = e.getY();

        }
        
        @Override
        public void mouseWheelMoved( MouseWheelEvent e ) {
            mouseHelper( MOUSE_WHEEL_UP, MOUSE_WHEEL_DOWN, e.getWheelRotation() );
        }
        
        /**
         * Calculates and configures mouse movement.
         */
        private void mouseHelper( int codeNeg, int codePos, int amount ) {

            List<GameAction> gameActions;

            if ( amount < 0 ) {
                gameActions = mouseActionsMap.get( codeNeg );
            } else {
                gameActions = mouseActionsMap.get( codePos );
            }
            
            if ( gameActions != null ) {
                for ( GameAction ga : gameActions ) {
                    ga.press( Math.abs( amount ) );
                    ga.release();
                }
            }

        }

    }

    
    
    /**
     * The GameAction class is an abstraction for a user-initiated action,
     * such as jumping or moving. GameActions can be mapped to keys or
     * mouse using the InputManager. They are currently not exposed, as they
     * are used to simulate Raylib behavior.
     *
     * @author Prof. Dr. David Buzatto
     */
    private class GameAction {
        
        private static final int STATE_RELEASED = 0;
        private static final int STATE_PRESSED = 1;
        private static final int STATE_WAITING_FOR_RELEASE = 2;
        
        private String name;
        private boolean initialPressOnly;
        private int amount;
        private int state;
        
        /**
         * Creates a new GameAction with normal behavior.
         */
        public GameAction( String name ) {
            this( name, false );
        }
        
        /**
         * Creates a new GameAction with the behavior of detecting
         * only the initial press.
         */
        public GameAction( String name, boolean initialPressOnly ) {
            this.name = name;
            this.initialPressOnly = initialPressOnly;
            reset();
        }
        
        /**
         * Gets the name of this GameAction.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Returns whether the action is initial-press-only.
         * @return 
         */
        public boolean isInitialPressOnly() {
            return initialPressOnly;
        }

        /**
         * Resets this GameAction, making it appear as if it was not pressed.
         */
        public void reset() {
            state = STATE_RELEASED;
            amount = 0;
        }
        
        /**
         * Quick press for this GameAction. Same as calling press()
         * followed by release().
         */
        public synchronized void tap() {
            press();
            release();
        }
        
        /**
         * Signals that the key was pressed.
         */
        public synchronized void press() {
            press( 1 );
        }
        
        /**
         * Signals that the key was pressed the specified number of times,
         * or that the mouse moved a specified distance.
         */
        public synchronized void press( int amount ) {
            if ( state != STATE_WAITING_FOR_RELEASE ) {
                this.amount += amount;
                state = STATE_PRESSED;
            }
        }
        
        /**
         * Signals that the key was released.
         */
        public synchronized void release() {
            state = STATE_RELEASED;
        }
        
        /**
         * Returns whether the key has been pressed since the last check.
         */
        public synchronized boolean isPressed() {
            return ( getAmount() != 0 );
        }
        
        /**
         * For keys, it is the number of times the key was pressed since
         * the last check.
         * For mouse events, it is the distance the cursor moved.
         */
        public synchronized int getAmount() {

            int retVal = amount;

            if ( retVal != 0 ) {
                if ( state == STATE_RELEASED ) {
                    amount = 0;
                } else if ( initialPressOnly ) {
                    state = STATE_WAITING_FOR_RELEASE;
                    amount = 0;
                }
            }

            return retVal;
            
        }
        
    }

    
    
    /** 
     * Inner class for managing gamepad input.
     *
     * @author Prof. Dr. David Buzatto
     */
    private class GamepadInputManager {
    
        private List<Controller> foundControllers;
        private Gamepad[] gamepads;

        public GamepadInputManager() {
            createGamepadEnvinronment();
        }

        /**
         * Prepares the environment for 4 gamepads.
         */
        private void createGamepadEnvinronment() {

            gamepads = new Gamepad[4];

            for ( int i = 0; i < gamepads.length; i++ ) {
                gamepads[i] = new Gamepad( i );
            }

            searchForControllers();

        }

        /**
         * Searches for available controllers of type Controller.Type.GAMEPAD.
         * Up to four gamepads are handled.
         */
        private void searchForControllers() {
            
            foundControllers = new ArrayList<>();
            
            try {
                
                Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

                for ( int i = 0; i < controllers.length; i++ ) {

                    Controller controller = controllers[i];

                    if ( controller.getType() == Controller.Type.GAMEPAD ) {
                        foundControllers.add( controller );
                        gamepads[foundControllers.size()-1].setName( controller.getName() );
                    }

                    if ( foundControllers.size() == 4 ) {
                        break;
                    }

                }
                
            } catch ( Exception exc ) {
                traceLogError( CoreUtils.stackTraceToString( exc ) );
            }

        }

        /**
         * Acquires data for the available gamepads.
         */
        private void acquireAllGamepadData() {

            int i = 0;

            for ( Controller c : foundControllers ) {
                acquireGamepadData( c, gamepads[i++] );
            }

        }
        
        /**
         * Processes a Controller and populates the corresponding gamepad.
         * At most 20 common buttons are used, which already exceeds the
         * number of buttons on a standard gamepad.
         */
        private void acquireGamepadData( Controller controller, Gamepad gamepad ) {

            // if there is data
            if ( controller.poll() ) {

                // configure as available
                gamepad.setAvailable( true );

                // iterate over all controller components
                net.java.games.input.Component[] components = controller.getComponents();

                for ( int i = 0; i < components.length; i++ ) {

                    Component component = components[i];
                    Identifier componentIdentifier = component.getIdentifier();
                    
                    // buttons contain only numbers in the name
                    if ( componentIdentifier.getName().matches( "^[0-9]*$" ) ) {

                        // is the button pressed?
                        boolean isPressed = true;
                        if ( component.getPollData() == 0.0f ) {
                            isPressed = false;
                        }

                        // button index
                        int buttonIndex = Integer.parseInt( component.getIdentifier().toString() );
                        
                        if ( buttonIndex < 20 ) {
                            gamepad.setButtonState( buttonIndex, isPressed );
                        }

                        // skip to the next iteration, no need to test other types
                        continue;

                    }

                    // hat switch
                    if ( componentIdentifier == Component.Identifier.Axis.POV ) {
                        float hatSwitch = component.getPollData();
                        int dpadCode = (int) ( component.getPollData() * 1000 ) / 125;
                        gamepad.setHatSwitch( hatSwitch );
                        gamepad.resetHatSwitchButtonsState();
                        gamepad.setHatSwitchButtonState( dpadCode, true );
                        continue;
                    }

                    // axes
                    if ( component.isAnalog() ) {

                        float axisValue = component.getPollData();

                        // x axis
                        if ( componentIdentifier == Component.Identifier.Axis.X ) {
                            gamepad.setX( axisValue );
                            continue;
                        }

                        // y axis
                        if ( componentIdentifier == Component.Identifier.Axis.Y ) {
                            gamepad.setY( axisValue );
                            continue;
                        }

                        // z axis
                        if ( componentIdentifier == Component.Identifier.Axis.Z ) {                            
                            gamepad.setZ( axisValue );
                            continue;
                        }

                        // rx axis
                        if ( componentIdentifier == Component.Identifier.Axis.RX ) {
                            gamepad.setRx( axisValue );
                            continue;
                        }

                        // ry axis
                        if ( componentIdentifier == Component.Identifier.Axis.RY ) {
                            gamepad.setRy( axisValue );
                            continue;
                        }

                        // rz axis
                        if ( componentIdentifier == Component.Identifier.Axis.RZ ) {
                            gamepad.setRz( axisValue );
                            continue;
                        }

                    }

                }

                //System.out.println( gamepad );

            } else {
                traceLogError( "Gamepad %d disconnected", gamepad.getId() + 1 );
            }

        }

        /**
         * Advances to the next cycle.
         */
        public void prepareToNextCycle() {
            if ( !foundControllers.isEmpty() ) {
                prepareGamepadsToNextCycle();
                acquireAllGamepadData();
            }
        }

        /**
         * Prepares the gamepads for a new cycle.
         */
        private void prepareGamepadsToNextCycle() {
            for ( Gamepad gp : gamepads ) {
                gp.setAvailable( false );
                gp.copyLastState();
            }
        }

        /**
         * Checks whether a gamepad is available.
         * 
         * @param gamepadId The gamepad identifier.
         * @return True if the gamepad is available, false otherwise.
         */
        public boolean isGamepadAvailable( int gamepadId ) {
            if ( gamepadId >= GAMEPAD_1 && gamepadId <= GAMEPAD_4 ) {
                return gamepads[gamepadId].isAvailable();
            }
            traceLogError( "Invalid gamepad %d.", gamepadId );
            return false;
        }

        /**
         * Gets the internal name of the gamepad.
         * 
         * @param gamepadId The gamepad identifier.
         * @return The internal name of the gamepad.
         */
        public String getGamepadName( int gamepadId ) {
            if ( isGamepadAvailable( gamepadId ) ) {
                return gamepads[gamepadId].getName();
            }
            return null;
        }

        /**
         * Returns whether a gamepad button was pressed once.
         * 
         * @param gamepadId The gamepad identifier.
         * @param button The button.
         * @return True if the button was pressed once, false otherwise.
         */
        public boolean isGamepadButtonPressed( int gamepadId, int button ) {
            
            if ( isGamepadAvailable( gamepadId ) ) {
                
                Gamepad g = gamepads[gamepadId];

                // "normal" buttons
                if ( button >= 0 && button <= 9 ) {
                    return g.isButtonDown( button ) && !g.isLastButtonDown( button );
                }
                
                // left trigger
                if ( button == 44 ) {
                    return g.isTriggerButtonDown( 0 ) && !g.isLastTriggerButtonDown( 0 );
                }

                // right trigger
                if ( button == 55 ) {
                    return g.isTriggerButtonDown( 1 ) && !g.isLastTriggerButtonDown( 1 );
                }

                // dpad
                if ( button >= 10 && button <= 13 ) {
                    switch ( button ) {
                        case GAMEPAD_BUTTON_LEFT_FACE_UP:
                            return ( g.isHatSwitchButtonDown( 1 ) && !g.isLastHatSwitchButtonDown( 1 ) ) ||
                                   ( g.isHatSwitchButtonDown( 2 ) && !g.isLastHatSwitchButtonDown( 2 ) ) ||
                                   ( g.isHatSwitchButtonDown( 3 ) && !g.isLastHatSwitchButtonDown( 3 ) );
                        case GAMEPAD_BUTTON_LEFT_FACE_RIGHT:
                            return ( g.isHatSwitchButtonDown( 3 ) && !g.isLastHatSwitchButtonDown( 3 ) ) ||
                                   ( g.isHatSwitchButtonDown( 4 ) && !g.isLastHatSwitchButtonDown( 4 ) ) ||
                                   ( g.isHatSwitchButtonDown( 5 ) && !g.isLastHatSwitchButtonDown( 5 ) );
                        case GAMEPAD_BUTTON_LEFT_FACE_DOWN:
                            return ( g.isHatSwitchButtonDown( 5 ) && !g.isLastHatSwitchButtonDown( 5 ) ) ||
                                   ( g.isHatSwitchButtonDown( 6 ) && !g.isLastHatSwitchButtonDown( 6 ) ) ||
                                   ( g.isHatSwitchButtonDown( 7 ) && !g.isLastHatSwitchButtonDown( 7 ) );
                        case GAMEPAD_BUTTON_LEFT_FACE_LEFT:
                            return ( g.isHatSwitchButtonDown( 7 ) && !g.isLastHatSwitchButtonDown( 7 ) ) ||
                                   ( g.isHatSwitchButtonDown( 8 ) && !g.isLastHatSwitchButtonDown( 8 ) ) ||
                                   ( g.isHatSwitchButtonDown( 1 ) && !g.isLastHatSwitchButtonDown( 1 ) );
                    }
                }
                
            }
            
            return false;

        }

        /**
         * Returns whether a gamepad button is currently pressed.
         * 
         * @param gamepadId The gamepad identifier.
         * @param button The button.
         * @return True if the button is pressed, false otherwise.
         */
        public boolean isGamepadButtonDown( int gamepadId, int button ) {
            
            if ( isGamepadAvailable( gamepadId ) ) {
                
                Gamepad g = gamepads[gamepadId];

                // "normal" buttons
                if ( button >= 0 && button <= 9 ) {
                    return g.isButtonDown( button );
                }

                // left trigger
                if ( button == 44 ) {
                    g.setTriggerButtonState( 0, g.getZ() > 0.0 );
                    return g.isTriggerButtonDown( 0 );
                }

                // right trigger
                if ( button == 55 ) {
                    g.setTriggerButtonState( 1, g.getZ() < -0.0001 );
                    return g.isTriggerButtonDown( 1 );
                }

                // dpad
                if ( button >= 10 && button <= 13 ) {
                    switch ( button ) {
                        case GAMEPAD_BUTTON_LEFT_FACE_UP:
                            return g.isHatSwitchButtonDown( 1 ) || 
                                   g.isHatSwitchButtonDown( 2 ) || 
                                   g.isHatSwitchButtonDown( 3 );
                        case GAMEPAD_BUTTON_LEFT_FACE_RIGHT:
                            return g.isHatSwitchButtonDown( 3 ) || 
                                   g.isHatSwitchButtonDown( 4 ) || 
                                   g.isHatSwitchButtonDown( 5 );
                        case GAMEPAD_BUTTON_LEFT_FACE_DOWN:
                            return g.isHatSwitchButtonDown( 5 ) || 
                                   g.isHatSwitchButtonDown( 6 ) || 
                                   g.isHatSwitchButtonDown( 7 );
                        case GAMEPAD_BUTTON_LEFT_FACE_LEFT:
                            return g.isHatSwitchButtonDown( 7 ) || 
                                   g.isHatSwitchButtonDown( 8 ) ||
                                   g.isHatSwitchButtonDown( 1 );
                    }
                }
            
            }
            
            return false;
            
        }

        /**
         * Returns whether a gamepad button was released.
         * 
         * @param gamepadId The gamepad identifier.
         * @param button The button.
         * @return True if the button was released, false otherwise.
         */
        public boolean isGamepadButtonReleased( int gamepadId, int button ) {
            
            if ( isGamepadAvailable( gamepadId ) ) {
                
                Gamepad g = gamepads[gamepadId];

                // "normal" buttons
                if ( button >= 0 && button <= 9 ) {
                    return !g.isButtonDown( button ) && g.isLastButtonDown( button );
                }
                
                // left trigger
                if ( button == 44 ) {
                    return !g.isTriggerButtonDown( 0 ) && g.isLastTriggerButtonDown( 0 );
                }

                // right trigger
                if ( button == 55 ) {
                    return !g.isTriggerButtonDown( 1 ) && g.isLastTriggerButtonDown( 1 );
                }

                // dpad
                if ( button >= 10 && button <= 13 ) {
                    switch ( button ) {
                        case GAMEPAD_BUTTON_LEFT_FACE_UP:
                            return ( !g.isHatSwitchButtonDown( 1 ) && g.isLastHatSwitchButtonDown( 1 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 2 ) && g.isLastHatSwitchButtonDown( 2 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 3 ) && g.isLastHatSwitchButtonDown( 3 ) );
                        case GAMEPAD_BUTTON_LEFT_FACE_RIGHT:
                            return ( !g.isHatSwitchButtonDown( 3 ) && g.isLastHatSwitchButtonDown( 3 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 4 ) && g.isLastHatSwitchButtonDown( 4 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 5 ) && g.isLastHatSwitchButtonDown( 5 ) );
                        case GAMEPAD_BUTTON_LEFT_FACE_DOWN:
                            return ( !g.isHatSwitchButtonDown( 5 ) && g.isLastHatSwitchButtonDown( 5 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 6 ) && g.isLastHatSwitchButtonDown( 6 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 7 ) && g.isLastHatSwitchButtonDown( 7 ) );
                        case GAMEPAD_BUTTON_LEFT_FACE_LEFT:
                            return ( !g.isHatSwitchButtonDown( 7 ) && g.isLastHatSwitchButtonDown( 7 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 8 ) && g.isLastHatSwitchButtonDown( 8 ) ) ||
                                   ( !g.isHatSwitchButtonDown( 1 ) && g.isLastHatSwitchButtonDown( 1 ) );
                    }
                }
                
            }
            
            return false;
            
        }

        /**
         * Returns whether a gamepad button is not pressed.
         * 
         * @param gamepadId The gamepad identifier.
         * @param button The button.
         * @return True if the button is not pressed, false otherwise.
         */
        public boolean isGamepadButtonUp( int gamepadId, int button ) {
            if ( isGamepadAvailable( gamepadId ) ) {
                return !isGamepadButtonDown( gamepadId, button );
            }
            return false;
        }

        /**
         * Returns the movement value of a gamepad axis.
         * 
         * @param gamepadId The gamepad identifier.
         * @param axis The axis.
         * @return The movement value of a gamepad axis.
         */
        public double getGamepadAxisMovement( int gamepadId, int axis ) {

            if ( isGamepadAvailable( gamepadId ) ) {
                
                switch ( axis ) {
                    case GAMEPAD_AXIS_LEFT_X:
                        return gamepads[gamepadId].getX();
                    case GAMEPAD_AXIS_LEFT_Y:
                        return gamepads[gamepadId].getY();
                    case GAMEPAD_AXIS_RIGHT_X:
                        return gamepads[gamepadId].getRx();
                    case GAMEPAD_AXIS_RIGHT_Y:
                        return gamepads[gamepadId].getRy();
                    case GAMEPAD_AXIS_Z:
                        return gamepads[gamepadId].getZ();
                    case GAMEPAD_AXIS_LEFT_TRIGGER:
                        return gamepads[gamepadId].getZ();
                    case GAMEPAD_AXIS_RIGHT_TRIGGER:
                        return -gamepads[gamepadId].getZ();
                }
                
            }
            
            return 0;

        }
    
    }
    
    
    
    /**
     * Representation of a gamepad/joystick/controller.
     * 
     * @author Prof. Dr. David Buzatto
     */
    private class Gamepad {

        private static final int HAT_SWITCH_BUTTONS_LENGTH = 9;
        private static final boolean[] HAT_SWITCH_DEFAULT_VALUES = new boolean[HAT_SWITCH_BUTTONS_LENGTH];

        private int id;
        private String name;
        private boolean available;
        private boolean[] buttonsState;
        private boolean[] previousButtonsState;
        private boolean[] triggerButtonsState;
        private boolean[] previousTriggerButtonsState;
        private boolean[] hatSwitchButtonsState;
        private boolean[] previousHatSwitchButtonsState;
        private float hatSwitch;
        private double x;
        private double y;
        private double z;
        private double rx;
        private double ry;
        private double rz;

        public Gamepad( int id ) {
            this.id = id;
            this.buttonsState = new boolean[20];
            this.previousButtonsState = new boolean[20];
            this.triggerButtonsState = new boolean[2];
            this.previousTriggerButtonsState = new boolean[20];
            this.hatSwitchButtonsState = new boolean[HAT_SWITCH_BUTTONS_LENGTH];
            this.previousHatSwitchButtonsState = new boolean[HAT_SWITCH_BUTTONS_LENGTH];
        }

        public void setButtonState( int button, boolean value ) {
            if ( button < buttonsState.length ) {
                buttonsState[button] = value;
            }
        }
        
        public void setTriggerButtonState( int button, boolean value ) {
            if ( button < triggerButtonsState.length ) {
                triggerButtonsState[button] = value;
            }
        }

        public void setHatSwitchButtonState( int button, boolean value ) {
            if ( button < hatSwitchButtonsState.length ) {
                hatSwitchButtonsState[button] = value;
            }
        }

        public void resetHatSwitchButtonsState() {
            System.arraycopy( HAT_SWITCH_DEFAULT_VALUES, 0, hatSwitchButtonsState, 0, HAT_SWITCH_BUTTONS_LENGTH );
        }

        public boolean isButtonDown( int button ) {
            if ( button < buttonsState.length ) {
                return buttonsState[button];
            }
            return false;
        }
        
        public boolean isLastButtonDown( int button ) {
            if ( button < previousButtonsState.length ) {
                return previousButtonsState[button];
            }
            return false;
        }
        
        public boolean isTriggerButtonDown( int button ) {
            if ( button < triggerButtonsState.length ) {
                return triggerButtonsState[button];
            }
            return false;
        }
        
        public boolean isLastTriggerButtonDown( int button ) {
            if ( button < previousTriggerButtonsState.length ) {
                return previousTriggerButtonsState[button];
            }
            return false;
        }

        public boolean isHatSwitchButtonDown( int button ) {
            if ( button < hatSwitchButtonsState.length ) {
                return hatSwitchButtonsState[button];
            }
            return false;
        }
        
        public boolean isLastHatSwitchButtonDown( int button ) {
            if ( button < previousHatSwitchButtonsState.length ) {
                return previousHatSwitchButtonsState[button];
            }
            return false;
        }

        public void copyLastState() {
            System.arraycopy( buttonsState, 0, previousButtonsState, 0, buttonsState.length );
            System.arraycopy( triggerButtonsState, 0, previousTriggerButtonsState, 0, triggerButtonsState.length );
            System.arraycopy( hatSwitchButtonsState, 0, previousHatSwitchButtonsState, 0, hatSwitchButtonsState.length );
        }

        public float getHatSwitch() {
            return hatSwitch;
        }

        public void setHatSwitch( float hatSwitch ) {
            this.hatSwitch = hatSwitch;
        }

        public double getX() {
            return x;
        }

        public void setX( double x ) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY( double y ) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ( double z ) {
            this.z = z;
        }

        public double getRx() {
            return rx;
        }

        public void setRx( double rx ) {
            this.rx = rx;
        }

        public double getRy() {
            return ry;
        }

        public void setRy( double ry ) {
            this.ry = ry;
        }

        public double getRz() {
            return rz;
        }

        public void setRz( double rz ) {
            this.rz = rz;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable( boolean available ) {
            this.available = available;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();

            sb.append( "Gamepad: " ).append( id ).append( " " ).append( name );

            sb.append( "\nbuttons: " );
            for ( int i = 0; i < buttonsState.length; i++ ) {
                if ( buttonsState[i] ) {
                    sb.append( String.format( "%d ", i ) );
                }
            }

            sb.append( "\nhat switch: " ).append( hatSwitch ).append( " " );
            for ( int i = 0; i < hatSwitchButtonsState.length; i++ ) {
                if ( hatSwitchButtonsState[i] ) {
                    sb.append( String.format( "%d ", i ) );
                }
            }

            sb.append( "\naxes: " )
                .append( String.format( "x: %.2f ", x ) )
                .append( String.format( "y: %.2f ", y ) )
                .append( String.format( "z: %.2f ", z ) )
                .append( String.format( "rx: %.2f ", rx ) )
                .append( String.format( "ry: %.2f ", ry ) )
                .append( String.format( "rz: %.2f ", rz ) );

            return sb.toString();

        }

    }
    
    
    
    //**************************************************************************
    // "Dependency injection" for IMGUI.
    //**************************************************************************
    
    /**
     * Dependency injection controller for IMGUI components.
     */
    private static class IMGUIDependencyContainer {
        
        private static EngineFrame engine;
        
        static void setEngine( EngineFrame engine ) {
            IMGUIDependencyContainer.engine = engine;
        }
        
    }
    
    /**
     * Marks the calling instance as the engine to be injected
     * into IMGUI components that require it.
     */
    public void useAsDependencyForIMGUI() {
        IMGUIDependencyContainer.setEngine( this );
    }
    
    /**
     * Gets the engine configured to be injected as a dependency.
     * 
     * @return The engine previously configured to be injected as a dependency.
     * @throws IllegalStateException If no engine has been configured as
     * a dependency.
     */
    public static EngineFrame getDependencyEngine() throws IllegalStateException {
        if ( IMGUIDependencyContainer.engine != null ) {
            return IMGUIDependencyContainer.engine;
        }
        throw new IllegalStateException( "You must set one engine instance to be used as dependency injection." );
    }
    
    
    
    //**************************************************************************
    // Constants for keys.
    //**************************************************************************
    
    /** Key: NULL, used to indicate that no key was pressed */
    public static final int KEY_NULL            = 0;

    // alphanumeric
    
    /** Key: ' */
    public static final int KEY_APOSTROPHE      = KeyEvent.VK_QUOTE;
    
    /** Key: , */
    public static final int KEY_COMMA           = KeyEvent.VK_COMMA;
    
    /** Key: - */
    public static final int KEY_MINUS           = KeyEvent.VK_MINUS;
    
    /** Key: . */
    public static final int KEY_PERIOD          = KeyEvent.VK_PERIOD;
    
    /** Key: / */
    public static final int KEY_SLASH           = KeyEvent.VK_SLASH;
    
    /** Key: 0 */
    public static final int KEY_ZERO            = KeyEvent.VK_0;
    
    /** Key: 1 */
    public static final int KEY_ONE             = KeyEvent.VK_1;
    
    /** Key: 2 */
    public static final int KEY_TWO             = KeyEvent.VK_2;
    
    /** Key: 3 */
    public static final int KEY_THREE           = KeyEvent.VK_3;
    
    /** Key: 4 */
    public static final int KEY_FOUR            = KeyEvent.VK_4;
    
    /** Key: 5 */
    public static final int KEY_FIVE            = KeyEvent.VK_5;
    
    /** Key: 6 */
    public static final int KEY_SIX             = KeyEvent.VK_6;
    
    /** Key: 7 */
    public static final int KEY_SEVEN           = KeyEvent.VK_7;
    
    /** Key: 8 */
    public static final int KEY_EIGHT           = KeyEvent.VK_8;
    
    /** Key: 9 */
    public static final int KEY_NINE            = KeyEvent.VK_9;
    
    /** Key: ; */
    public static final int KEY_SEMICOLON       = KeyEvent.VK_SEMICOLON;
    
    /** Key: = */
    public static final int KEY_EQUAL           = KeyEvent.VK_EQUALS;
    
    /** Key: A */
    public static final int KEY_A               = KeyEvent.VK_A;
    
    /** Key: B */
    public static final int KEY_B               = KeyEvent.VK_B;
    
    /** Key: C */
    public static final int KEY_C               = KeyEvent.VK_C;
    
    /** Key: D */
    public static final int KEY_D               = KeyEvent.VK_D;
    
    /** Key: E */
    public static final int KEY_E               = KeyEvent.VK_E;
    
    /** Key: F */
    public static final int KEY_F               = KeyEvent.VK_F;
    
    /** Key: G */
    public static final int KEY_G               = KeyEvent.VK_G;
    
    /** Key: H */
    public static final int KEY_H               = KeyEvent.VK_H;
    
    /** Key: I */
    public static final int KEY_I               = KeyEvent.VK_I;
    
    /** Key: J */
    public static final int KEY_J               = KeyEvent.VK_J;
    
    /** Key: K */
    public static final int KEY_K               = KeyEvent.VK_K;
    
    /** Key: L */
    public static final int KEY_L               = KeyEvent.VK_L;
    
    /** Key: M */
    public static final int KEY_M               = KeyEvent.VK_M;
    
    /** Key: N */
    public static final int KEY_N               = KeyEvent.VK_N;
    
    /** Key: O */
    public static final int KEY_O               = KeyEvent.VK_O;
    
    /** Key: P */
    public static final int KEY_P               = KeyEvent.VK_P;
    
    /** Key: Q */
    public static final int KEY_Q               = KeyEvent.VK_Q;
    
    /** Key: R */
    public static final int KEY_R               = KeyEvent.VK_R;
    
    /** Key: S */
    public static final int KEY_S               = KeyEvent.VK_S;
    
    /** Key: T */
    public static final int KEY_T               = KeyEvent.VK_T;
    
    /** Key: U */
    public static final int KEY_U               = KeyEvent.VK_U;
    
    /** Key: V */
    public static final int KEY_V               = KeyEvent.VK_V;
    
    /** Key: W */
    public static final int KEY_W               = KeyEvent.VK_W;
    
    /** Key: X */
    public static final int KEY_X               = KeyEvent.VK_X;
    
    /** Key: Y */
    public static final int KEY_Y               = KeyEvent.VK_Y;
    
    /** Key: Z */
    public static final int KEY_Z               = KeyEvent.VK_Z;
    
    /** Key: [ */
    public static final int KEY_LEFT_BRACKET    = KeyEvent.VK_OPEN_BRACKET;
    
    /** Key: ] */
    public static final int KEY_RIGHT_BRACKET   = KeyEvent.VK_CLOSE_BRACKET;
    
    /** Key: \ */
    public static final int KEY_BACKSLASH       = KeyEvent.VK_BACK_SLASH;
    
    /** Key: ` */
    public static final int KEY_GRAVE           = KeyEvent.VK_BACK_QUOTE;

    /** Key: &lt;SPACE&gt; */
    public static final int KEY_SPACE           = KeyEvent.VK_SPACE;
    
    /** Key: &lt;ESC&gt; */
    public static final int KEY_ESCAPE          = KeyEvent.VK_ESCAPE;
    
    /** Key: &lt;ENTER&gt; */
    public static final int KEY_ENTER           = KeyEvent.VK_ENTER;
    
    /** Key: &lt;TAB&gt; */
    public static final int KEY_TAB             = KeyEvent.VK_TAB;
    
    /** Key: &lt;BACKSPACE&gt; */
    public static final int KEY_BACKSPACE       = KeyEvent.VK_BACK_SPACE;
    
    /** Key: &lt;INSERT&gt; */
    public static final int KEY_INSERT          = KeyEvent.VK_INSERT;
    
    /** Key: &lt;DELETE&gt; */
    public static final int KEY_DELETE          = KeyEvent.VK_DELETE;
    
    /** Key: &lt;ARROW RIGHT&gt; */
    public static final int KEY_RIGHT           = KeyEvent.VK_RIGHT;
    
    /** Key: &lt;ARROW LEFT&gt; */
    public static final int KEY_LEFT            = KeyEvent.VK_LEFT;
    
    /** Key: &lt;ARROW DOWN&gt; */
    public static final int KEY_DOWN            = KeyEvent.VK_DOWN;
    
    /** Key: &lt;ARROW UP&gt; */
    public static final int KEY_UP              = KeyEvent.VK_UP;
    
    /** Key: &lt;PAGE UP&gt; */
    public static final int KEY_PAGE_UP         = KeyEvent.VK_PAGE_UP;
    
    /** Key: &lt;PAGE DOWN&gt; */
    public static final int KEY_PAGE_DOWN       = KeyEvent.VK_PAGE_DOWN;
    
    /** Key: &lt;HOME&gt; */
    public static final int KEY_HOME            = KeyEvent.VK_HOME;
    
    /** Key: &lt;END&gt; */
    public static final int KEY_END             = KeyEvent.VK_END;
    
    /** Key: &lt;CAPS LOCK&gt; */
    public static final int KEY_CAPS_LOCK       = KeyEvent.VK_CAPS_LOCK;
    
    /** Key: &lt;SCROLL LOCK&gt; */
    public static final int KEY_SCROLL_LOCK     = KeyEvent.VK_SCROLL_LOCK;
    
    /** Key: &lt;NUM LOCK&gt; */
    public static final int KEY_NUM_LOCK        = KeyEvent.VK_NUM_LOCK;
    
    /** Key: &lt;PRINT SCREEN&gt; */
    public static final int KEY_PRINT_SCREEN    = KeyEvent.VK_PRINTSCREEN;
    
    /** Key: &lt;PAUSE&gt; */
    public static final int KEY_PAUSE           = KeyEvent.VK_PAUSE;
    
    /** Key: &lt;F1&gt; */
    public static final int KEY_F1              = KeyEvent.VK_F1;
    
    /** Key: &lt;F2&gt; */
    public static final int KEY_F2              = KeyEvent.VK_F2;
    
    /** Key: &lt;F3&gt; */
    public static final int KEY_F3              = KeyEvent.VK_F3;
    
    /** Key: &lt;F4&gt; */
    public static final int KEY_F4              = KeyEvent.VK_F4;
    
    /** Key: &lt;F5&gt; */
    public static final int KEY_F5              = KeyEvent.VK_F5;
    
    /** Key: &lt;F6&gt; */
    public static final int KEY_F6              = KeyEvent.VK_F6;
    
    /** Key: &lt;F7&gt; */
    public static final int KEY_F7              = KeyEvent.VK_F7;
    
    /** Key: &lt;F8&gt; */
    public static final int KEY_F8              = KeyEvent.VK_F8;
    
    /** Key: &lt;F9&gt; */
    public static final int KEY_F9              = KeyEvent.VK_F9;
    
    /** Key: &lt;F10&gt; */
    public static final int KEY_F10             = KeyEvent.VK_F10;
    
    /** Key: &lt;F11&gt; */
    public static final int KEY_F11             = KeyEvent.VK_F11;
    
    /** Key: &lt;F12&gt; */
    public static final int KEY_F12             = KeyEvent.VK_F12;
    
    /** Key: &lt;SHIFT&gt; */
    public static final int KEY_SHIFT           = KeyEvent.VK_SHIFT;
    
    /** Key: &lt;CONTROL&gt; */
    public static final int KEY_CONTROL         = KeyEvent.VK_CONTROL;
    
    /** Key: &lt;ALT&gt; */
    public static final int KEY_ALT             = KeyEvent.VK_ALT;
    
    /** Key: &lt;WINDOWS/SUPER&gt; */
    public static final int KEY_SUPER           = KeyEvent.VK_WINDOWS;
    
    /** Key: &lt;NUMPAD 0&gt; */
    public static final int KEY_KP_0            = KeyEvent.VK_NUMPAD0;
    
    /** Key: &lt;NUMPAD 1&gt; */
    public static final int KEY_KP_1            = KeyEvent.VK_NUMPAD1;
    
    /** Key: &lt;NUMPAD 2&gt; */
    public static final int KEY_KP_2            = KeyEvent.VK_NUMPAD2;
    
    /** Key: &lt;NUMPAD 3&gt; */
    public static final int KEY_KP_3            = KeyEvent.VK_NUMPAD3;
    
    /** Key: &lt;NUMPAD 4&gt; */
    public static final int KEY_KP_4            = KeyEvent.VK_NUMPAD4;
    
    /** Key: &lt;NUMPAD 5&gt; */
    public static final int KEY_KP_5            = KeyEvent.VK_NUMPAD5;
    
    /** Key: &lt;NUMPAD 6&gt; */
    public static final int KEY_KP_6            = KeyEvent.VK_NUMPAD6;
    
    /** Key: &lt;NUMPAD 7&gt; */
    public static final int KEY_KP_7            = KeyEvent.VK_NUMPAD7;
    
    /** Key: &lt;NUMPAD 8&gt; */
    public static final int KEY_KP_8            = KeyEvent.VK_NUMPAD8;
    
    /** Key: &lt;NUMPAD 9&gt; */
    public static final int KEY_KP_9            = KeyEvent.VK_NUMPAD9;
    
    /** Key: &lt;NUMPAD /&gt; */
    public static final int KEY_KP_DIVIDE       = KeyEvent.VK_DIVIDE;
    
    /** Key: &lt;NUMPAD *&gt; */
    public static final int KEY_KP_MULTIPLY     = KeyEvent.VK_MULTIPLY;
    
    /** Key: &lt;NUMPAD -&gt; */
    public static final int KEY_KP_SUBTRACT     = KeyEvent.VK_SUBTRACT;
    
    /** Key: &lt;NUMPAD +&gt; */
    public static final int KEY_KP_ADD          = KeyEvent.VK_ADD;

    
    
    //**************************************************************************
    // Constants for mouse buttons.
    //**************************************************************************
    
    /**
     * Constant representing the left mouse button.
     */
    public static final int MOUSE_BUTTON_LEFT    = MouseEvent.BUTTON1;
    
    /**
     * Constant representing the middle mouse button.
     */
    public static final int MOUSE_BUTTON_MIDDLE  = MouseEvent.BUTTON2;
    
    /**
     * Constant representing the right mouse button.
     */
    public static final int MOUSE_BUTTON_RIGHT   = MouseEvent.BUTTON3;
    

    
    //**************************************************************************
    // Constants for gamepad identifiers.
    //**************************************************************************
    
    /** Gamepad 1 */
    public static final int GAMEPAD_1 = 0;
    
    /** Gamepad 2 */
    public static final int GAMEPAD_2 = 1;
    
    /** Gamepad 3 */
    public static final int GAMEPAD_3 = 2;
    
    /** Gamepad 4 */
    public static final int GAMEPAD_4 = 3;
    
    
    
    //**************************************************************************
    // Constants for gamepad buttons.
    //**************************************************************************
    
    /** Unknown button (for error handling only). */
    public static final int GAMEPAD_BUTTON_UNKNOWN = -1;
    
    /** Down button on the left digital pad (dpad) */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_DOWN    = 10;
    
    /** Right button on the left digital pad (dpad) */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_RIGHT   = 11;
    
    /** Left button on the left digital pad (dpad) */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_LEFT    = 12;
    
    /** Up button on the left digital pad (dpad) */
    public static final int GAMEPAD_BUTTON_LEFT_FACE_UP      = 13;
    
    /** Down button on the right face buttons. PS: X / Xbox: A */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_DOWN   = 0;
    
    /** Right button on the right face buttons. PS: Circle / Xbox: B */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_RIGHT  = 1;
    
    /** Left button on the right face buttons. PS: Square / Xbox: X */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_LEFT   = 2;
    
    /** Up button on the right face buttons. PS: Triangle / Xbox: Y */
    public static final int GAMEPAD_BUTTON_RIGHT_FACE_UP     = 3;
    
    /** Top-left trigger. PS: L1 / Xbox: LB */
    public static final int GAMEPAD_BUTTON_LEFT_TRIGGER_1    = 4;
    
    /** Bottom-left trigger. PS: L2 / Xbox: LT */
    public static final int GAMEPAD_BUTTON_LEFT_TRIGGER_2    = 44;
    
    /** Top-right trigger. PS: R1 / Xbox: RB */
    public static final int GAMEPAD_BUTTON_RIGHT_TRIGGER_1   = 5;
    
    /** Bottom-right trigger. PS: R2 / Xbox: RT */
    public static final int GAMEPAD_BUTTON_RIGHT_TRIGGER_2   = 55;
    
    /** Left center button. "Select". */
    public static final int GAMEPAD_BUTTON_MIDDLE_LEFT       = 6;
    
    /** Right center button. "Start". */
    public static final int GAMEPAD_BUTTON_MIDDLE_RIGHT      = 7;
    
    /** Left analog stick button. */
    public static final int GAMEPAD_BUTTON_LEFT_THUMB        = 8;
    
    /** Right analog stick button. */
    public static final int GAMEPAD_BUTTON_RIGHT_THUMB       = 9;
    
    //**************************************************************************
    // Constants for gamepad axes.
    //**************************************************************************
    
    /** X axis of the left analog stick. */
    public static final int GAMEPAD_AXIS_LEFT_X              = 0;
    
    /** Y axis of the left analog stick. */
    public static final int GAMEPAD_AXIS_LEFT_Y              = 1;
    
    /** X axis of the right analog stick. */
    public static final int GAMEPAD_AXIS_RIGHT_X             = 2;
    
    /** Y axis of the right analog stick. */
    public static final int GAMEPAD_AXIS_RIGHT_Y             = 3;
    
    /** Z axis (bottom triggers). Ranges between [1..-1] */
    public static final int GAMEPAD_AXIS_Z                   = 4;
    
    /** Left trigger pressure level. Ranges between [0..1]. */
    public static final int GAMEPAD_AXIS_LEFT_TRIGGER        = 5;
    
    /** Right trigger pressure level. Ranges between [0..1]. */
    public static final int GAMEPAD_AXIS_RIGHT_TRIGGER       = 6;
    
    
    
    //**************************************************************************
    // Constants for the mouse cursor.
    //**************************************************************************
    
    /**
     * Constant representing the default (arrow) mouse cursor.
     */
    public static final int MOUSE_CURSOR_DEFAULT       = Cursor.DEFAULT_CURSOR;
    
    /**
     * Constant representing the text insertion mouse cursor.
     */
    public static final int MOUSE_CURSOR_IBEAM         = Cursor.TEXT_CURSOR;
    
    /**
     * Constant representing the crosshair mouse cursor.
     */
    public static final int MOUSE_CURSOR_CROSSHAIR     = Cursor.CROSSHAIR_CURSOR;
    
    /**
     * Constant representing the pointing hand mouse cursor.
     */
    public static final int MOUSE_CURSOR_POINTING_HAND = Cursor.HAND_CURSOR;
    
    /**
     * Constant representing the horizontal resize mouse cursor.
     */
    public static final int MOUSE_CURSOR_RESIZE_EW     = Cursor.E_RESIZE_CURSOR;
    
    /**
     * Constant representing the vertical resize mouse cursor.
     */
    public static final int MOUSE_CURSOR_RESIZE_NS     = Cursor.N_RESIZE_CURSOR;
    
    /**
     * Constant representing the diagonal resize (top-left to bottom-right) mouse cursor.
     */
    public static final int MOUSE_CURSOR_RESIZE_NWSE   = Cursor.NW_RESIZE_CURSOR;
    
    /**
     * Constant representing the diagonal resize (bottom-left to top-right) mouse cursor.
     */
    public static final int MOUSE_CURSOR_RESIZE_NESW   = Cursor.NE_RESIZE_CURSOR;
    
    /**
     * Constant representing the omnidirectional resize mouse cursor.
     */
    public static final int MOUSE_CURSOR_RESIZE_ALL    = Cursor.MOVE_CURSOR;
    
    /**
     * Constant representing the wait mouse cursor.
     */
    public static final int MOUSE_CURSOR_WAIT          = Cursor.WAIT_CURSOR;
    
    
    
    //**************************************************************************
    // Constants for fonts.
    //**************************************************************************
    
    /**
     * Constant representing the plain font style.
     */
    public static final int FONT_PLAIN                 = Font.PLAIN;
    
    /**
     * Constant representing the bold font style.
     */
    public static final int FONT_BOLD                  = Font.BOLD;
    
    /**
     * Constant representing the italic font style.
     */
    public static final int FONT_ITALIC                = Font.ITALIC;
    
    /**
     * Constant representing the bold and italic font style.
     */
    public static final int FONT_BOLD_ITALIC           = Font.BOLD + Font.ITALIC;
    
    /**
     * Constant representing the dialog font type.
     */
    public static final String FONT_DIALOG             = Font.DIALOG;
    
    /**
     * Constant representing the dialog input font type.
     */
    public static final String FONT_DIALOG_INPUT       = Font.DIALOG_INPUT;
    
    /**
     * Constant representing the monospaced font type.
     */
    public static final String FONT_MONOSPACED         = Font.MONOSPACED;
    
    /**
     * Constant representing the sans-serif font type.
     */
    public static final String FONT_SANS_SERIF         = Font.SANS_SERIF;
    
    /**
     * Constant representing the serif font type.
     */
    public static final String FONT_SERIF              = Font.SERIF;
    
    
    
    //**************************************************************************
    // Constants for strokes.
    //**************************************************************************
    
    /**
     * Constant representing the butt end cap style for strokes.
     */
    public static final int STROKE_CAP_BUTT            = BasicStroke.CAP_BUTT;
    
    /**
     * Constant representing the round end cap style for strokes.
     */
    public static final int STROKE_CAP_ROUND           = BasicStroke.CAP_ROUND;
    
    /**
     * Constant representing the square end cap style for strokes.
     */
    public static final int STROKE_CAP_SQUARE          = BasicStroke.CAP_SQUARE;
    
    /**
     * Constant representing the bevel line join style for strokes.
     */
    public static final int STROKE_JOIN_BEVEL          = BasicStroke.JOIN_BEVEL;
    
    /**
     * Constant representing the miter line join style for strokes.
     */
    public static final int STROKE_JOIN_MITER          = BasicStroke.JOIN_MITER;
    
    /**
     * Constant representing the round line join style for strokes.
     */
    public static final int STROKE_JOIN_ROUND          = BasicStroke.JOIN_ROUND;
    


    //**************************************************************************
    // Default colors.
    //**************************************************************************
    
    /** Light gray color. */
    public static final Color LIGHTGRAY  = new Color( 200, 200, 200 );
    
    /** Gray color. */
    public static final Color GRAY       = new Color( 130, 130, 130 );
    
    /** Dark gray color. */
    public static final Color DARKGRAY   = new Color( 80, 80, 80 );
    
    /** Yellow color. */
    public static final Color YELLOW     = new Color( 253, 249, 0 );
    
    /** Gold color. */
    public static final Color GOLD       = new Color( 255, 203, 0 );
    
    /** Orange color. */
    public static final Color ORANGE     = new Color( 255, 161, 0 );
    
    /** Pink color. */
    public static final Color PINK       = new Color( 255, 109, 194 );
    
    /** Red color. */
    public static final Color RED        = new Color( 230, 41, 55 );
    
    /** Dark red color. */
    public static final Color MAROON     = new Color( 190, 33, 55 );
    
    /** Green color. */
    public static final Color GREEN      = new Color( 0, 228, 48 );
    
    /** Lime color. */
    public static final Color LIME       = new Color( 0, 158, 47 );
    
    /** Dark green color. */
    public static final Color DARKGREEN  = new Color( 0, 117, 44 );
    
    /** Sky blue color. */
    public static final Color SKYBLUE    = new Color( 102, 191, 255 );
    
    /** Blue color. */
    public static final Color BLUE       = new Color( 0, 121, 241 );
    
    /** Dark blue color. */
    public static final Color DARKBLUE   = new Color( 0, 82, 172 );
    
    /** Purple color. */
    public static final Color PURPLE     = new Color( 200, 122, 255 );
    
    /** Violet color. */
    public static final Color VIOLET     = new Color( 135, 60, 190 );
    
    /** Dark purple color. */
    public static final Color DARKPURPLE = new Color( 112, 31, 126 );
    
    /** Beige color. */
    public static final Color BEIGE      = new Color( 211, 176, 131 );
    
    /** Brown color. */
    public static final Color BROWN      = new Color( 127, 106, 79 );
    
    /** Dark brown color. */
    public static final Color DARKBROWN  = new Color( 76, 63, 47 );
    
    /** White color. */
    public static final Color WHITE      = new Color( 255, 255, 255 );
    
    /** Black color. */
    public static final Color BLACK      = new Color( 0, 0, 0 );
    
    /** Transparent color. */
    public static final Color BLANK      = new Color( 0, 0, 0, 0 );
    
    /** Magenta color. */
    public static final Color MAGENTA    = new Color( 255, 0, 255 );
    
    /** Raywhite color: a tribute to raylib www.raylib.com */
    public static final Color RAYWHITE   = new Color( 245, 245, 245 );
    
    
    
    //**************************************************************************
    // Log levels.
    //**************************************************************************
    
    /** Constant to disable the logging system */
    public static final int LOG_NONE           = TraceLogUtils.LOG_NONE;
    
    /** Constant for INFO level logs. */
    public static final int LOG_INFO           = TraceLogUtils.LOG_INFO;
    
    /** Constant for WARNING level logs. */
    public static final int LOG_WARNING        = TraceLogUtils.LOG_WARNING;
    
    /** Constant for ERROR level logs. */
    public static final int LOG_ERROR          = TraceLogUtils.LOG_ERROR;
    
    /** Constant for FATAL level logs. */
    public static final int LOG_FATAL          = TraceLogUtils.LOG_FATAL;
    
    /** Constant to log at any level. */
    public static final int LOG_ALL            = TraceLogUtils.LOG_ALL;
    
}
