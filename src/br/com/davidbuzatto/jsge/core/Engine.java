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
package br.com.davidbuzatto.jsge.core;

import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Path;
import br.com.davidbuzatto.jsge.geom.Point;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.geom.Vector2;
import br.com.davidbuzatto.jsge.sound.Music;
import br.com.davidbuzatto.jsge.sound.Sound;
import br.com.davidbuzatto.jsge.utils.ColorUtils;
import br.com.davidbuzatto.jsge.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.utils.ImageUtils;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Robot;
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
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Engine simples para criação de jogos ou simulações usando Java 2D.
 * Grande parte da sua API é baseada na engine de jogos Raylib (www.raylib.com).
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class Engine extends JFrame {

    /*
     * painel de desenho onde todas as operações de desenho e de registro
     * de eventos ocorretá.
     */
    private DrawingPanel drawingPanel;

    // referência ao contexto gráfico do painel de desenho.
    private Graphics2D g2d;

    // fonte padrão
    private Font defaultFont;
    private Font defaultFPSFont;

    // contorno padrão
    private BasicStroke defaultStroke;

    // tempo antes de iniciar os processos de atualização e desenho
    private long timeBefore;

    // tempo depois de realizar os processos de atualização e desenho
    private long timeAfter;

    // tempo a esperar antes de iniciar o próximo ciclo
    private long waitTime;

    // tempo de um frame
    private long frameTime;

    // quadros por segundo
    private int targetFPS;

    // quadros por segundo atual
    private int currentFPS;

    // tempo esperado baseado na quantidade de quadros por segundo
    private long waitTimeFPS;

    // tempo de início da execução do jogo/simulação
    private long startTime;

    // flag que sinaliza o uso da suavização (antialiasing) para o contexto gráfico
    private boolean antialiasing;
    
    // código da tecla de saída
    private int exitKeyCode;
    
    // flag para controle de execução da thread de desenho
    private boolean running;

    // gerenciador de entradas
    private InputManager inputManager;

    // ações padrão para o mouse
    private GameAction mouseLeftAction;
    private GameAction mouseLeftActionInitial;
    private GameAction mouseMiddleAction;
    private GameAction mouseMiddleActionInitial;
    private GameAction mouseRightAction;
    private GameAction mouseRightActionInitial;
    private GameAction mouseWheelUpAction;
    private GameAction mouseWheelDownAction;
    
    private boolean mouseButtonLeftProcessed;
    private boolean mouseButtonMiddleProcessed;
    private boolean mouseButtonRightProcessed;
    
    private boolean mouseLeftPressed;
    private boolean mouseLeftReleased;
    private boolean mouseLeftDown;
    private boolean mouseLeftUp;
    
    private boolean mouseMiddlePressed;
    private boolean mouseMiddleReleased;
    private boolean mouseMiddleDown;
    private boolean mouseMiddleUp;
    
    private boolean mouseRightPressed;
    private boolean mouseRightReleased;
    private boolean mouseRightDown;
    private boolean mouseRightUp;
    
    // mapas para o teclado
    private Map<Integer, Boolean> keysProcessedMap;
    private Map<Integer, Boolean> keysPressedMap;
    private Map<Integer, Boolean> keysReleasedMap;
    private Map<Integer, Boolean> keysDownMap;
    private Map<Integer, Boolean> keysUpMap;
    
    // controle do cursor
    private Cursor currentCursor;
    
    /**
     * Um cursor invisível.
     */
    public static final Cursor INVISIBLE_CURSOR =
            Toolkit.getDefaultToolkit().createCustomCursor(
                Toolkit.getDefaultToolkit().getImage( "" ),
                new java.awt.Point( 0, 0 ),
                "invisible"
            );
    
    // controle da câmera (modo 2D)
    private boolean mode2DActive = false;
    private Graphics2D cameraGraphics;
    private Graphics2D baseGraphics;
        
    /**
     * Processa a entrada inicial fornecida pelo usuário e cria
     * e/ou inicializa os objetos/contextos/variáveis do jogo ou simulação.
     * 
     * É executado apenas UMA VEZ.
     */
    public abstract void create();

    /**
     * Atualiza os objetos/contextos/variáveis do jogo ou simulação.
     * 
     * É executado uma vez a cada frame, sempre antes do método de desenho.
     */
    public abstract void update();

    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     * 
     * É executado uma vez a cada frame, sempre após o método de atualização.
     */
    public abstract void draw();

    /**
     * Cria uma instância da engine e inicia sua execução.
     * 
     * @param windowWidth Largura da janela.
     * @param windowHeight Altura da janela.
     * @param windowTitle Título de janela.
     * @param targetFPS A quantidade máxima de frames por segundo que se deseja que o processo de atualização e desenho mantenha.
     * @param antialiasing Flag que indica se deve ou não usar suavização para o desenho no contexto gráfico.
     * @param resizable Flag que indica se a janela é redimensionável.
     * @param fullScreen Flag que indica se a janela deve rodar em modo tela cheia exclusivo.
     * @param undecorated Flag que indica se a janela deve ser não decorada.
     * @param alwaysOnTop Flag que indica se a janela está sempre por cima.
     */
    public Engine( int windowWidth, 
                   int windowHeight, 
                   String windowTitle, 
                   int targetFPS, 
                   boolean antialiasing,
                   boolean resizable, 
                   boolean fullScreen, 
                   boolean undecorated, 
                   boolean alwaysOnTop ) {
                    
        if ( windowWidth <= 0 ) {
            throw new IllegalArgumentException( "width must be positive!" );
        }

        if ( windowHeight <= 0 ) {
            throw new IllegalArgumentException( "height must be positive!" );
        }

        startTime = System.currentTimeMillis();
        setTargetFPS( targetFPS );

        defaultFont = new Font( Font.MONOSPACED, Font.PLAIN, 10 );
        defaultFPSFont = new Font( Font.MONOSPACED, Font.BOLD, 20 );
        defaultStroke = new BasicStroke( 1 );

        this.antialiasing = antialiasing;
        waitTimeFPS = (long) ( 1000.0 / this.targetFPS );   // quanto se espera que cada frame demore

        // cria e configura o painel de desenho
        drawingPanel = new DrawingPanel();
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
        
        prepareInputManager();

        // configura a engine
        setTitle( windowTitle );
        setAlwaysOnTop( alwaysOnTop );

        if ( fullScreen ) {
            setResizable( false );
            setUndecorated( true );
            setExtendedState( MAXIMIZED_BOTH );
        } else {
            setResizable( resizable );
            setUndecorated( undecorated );
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

        // inicializa os objetos/contexto/variáveis do jogo atual
        create();

        // inicia o processo de execução do jogo ou simulação
        running = true;
        start();

        setVisible( true );

    }

    /**
     * Cria uma instância da engine e inicia sua execução.
     * 
     * @param windowWidth Largura da janela.
     * @param windowHeight Altura da janela.
     * @param windowTitle Título de janela.
     * @param targetFPS A quantidade máxima de frames por segundo que se deseja que o processo de atualização e desenho mantenha.
     * @param antialiasing Flag que indica se deve ou não usar suavização para o desenho no contexto gráfico.
     */
    public Engine( int windowWidth, 
                   int windowHeight, 
                   String windowTitle, 
                   int targetFPS, 
                   boolean antialiasing ) {
        this( windowWidth, windowHeight, windowTitle, targetFPS, antialiasing, false, false, false, false );
    }

    private void start() {

        new Thread( () -> {

            while ( running ) {

                timeBefore = System.currentTimeMillis();
                update();
                resetMouseButtonsState();
                resetKeysState();
                
                try {
                    SwingUtilities.invokeAndWait( () -> {
                        drawingPanel.repaint();
                    });
                } catch ( InterruptedException | InvocationTargetException exc ) {
                    exc.printStackTrace();
                }

                timeAfter = System.currentTimeMillis();

                // quanto um frame demorou?
                frameTime = timeAfter - timeBefore;

                // quanto se deve esperar?
                waitTime = waitTimeFPS - frameTime;

                //System.out.printf( "%d %d %d %d\n", timeBefore, timeAfter, frameTime, waitTime );

                // se o tempo a esperar for negativo, quer dizer que não
                // houve tempo suficiente, baseado no tempo esperado
                // para todo o frame ser atualizado e desenhado
                if ( waitTime < 0 ) {
                    waitTime = 0;      // não espera
                }

                // se o tempo do frame é menor que o tempo se que deve esperar,
                // quer dizer que sobrou tempo para executar o frame, ou seja
                // o frame foi atualizado e desenhado em menos tempo do que 
                // é esperado baseado na quantidade de frames por segundo
                if ( frameTime < waitTime ) {
                    frameTime = waitTime;  // o tempo que o frame demorou para executar
                }

                int localFPS = (int) ( Math.round( 1000.0 / frameTime / 10.0 ) ) * 10;

                if ( localFPS > targetFPS ) {
                    localFPS = targetFPS;
                }

                if ( localFPS >= 0 ) {
                    currentFPS = localFPS;
                }

                try {
                    Thread.sleep( waitTime );
                } catch ( InterruptedException exc ) {
                    exc.printStackTrace();
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
    
    
    
    /***************************************************************************
     * Tratamento do mouse.
     **************************************************************************/
    /**
     * Retorna se um botão do mouse foi pressionado uma vez.
     * 
     * @param button O inteiro que identifica o botão do mouse desejado.
     * @return Verdadeiro caso o botão tenha sido pressionado uma vez, falso caso contrário.
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
     * Retorna se um botão do mouse foi solto.
     * 
     * @param button O inteiro que identifica o botão do mouse desejado.
     * @return Verdadeiro caso o botão tenha sido solto, falso caso contrário.
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
     * Retorna se um botão do mouse está pressionado.
     * 
     * @param button O inteiro que identifica o botão do mouse desejado.
     * @return Verdadeiro caso o botão esteja pressionado, falso caso contrário.
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
     * Retorna se um botão do mouse não está pressionado.
     * 
     * @param button O inteiro que identifica o botão do mouse desejado.
     * @return Verdadeiro caso o botão não esteja pressionado, falso caso contrário.
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
     * Obtém a posição x do mouse.
     * 
     * @return A posição x do mouse.
     */
    public int getMouseX() {
        return inputManager.getMouseX();
    }
    
    /**
     * Obtém a posição y do mouse.
     * 
     * @return A posição y do mouse.
     */
    public int getMouseY() {
        return inputManager.getMouseY();
    }

    /**
     * Obtém a posição do mouse como um ponto.
     * 
     * @return A posição do mouse como um ponto.
     */
    public Point getMousePositionPoint() {
        return new Point( inputManager.getMouseX(), inputManager.getMouseY() );
    }

    /**
     * Obtém a posição do mouse como um vetor.
     * 
     * @return A posição do mouse como um vetor.
     */
    public Vector2 getMousePositionVector2() {
        return new Vector2( inputManager.getMouseX(), inputManager.getMouseY() );
    }

    /**
     * Obtém a movimentação da roda de rolagem do mouse.
     * Positivo para cima, negativo para baixo e zero para estacionária.
     * 
     * @return A movimentação da roda de rolagem do mouse.
     */
    public double getMouseWheelMove() {
        double vUp = mouseWheelUpAction.getAmount();
        double vDown = mouseWheelDownAction.getAmount();
        return vUp >= vDown ? vUp : -vDown;
    }

    /**
     * Obtém um ponto com movimentação da roda de rolagem do mouse.
     * Em x a movimentação para cima e em y a movimentação para baixo.
     * 
     * @return Um ponto com a movimentação da roda de rolagem do mouse.
     */
    public Point getMouseWheelMovePoint() {
        double vUp = mouseWheelUpAction.getAmount();
        double vDown = mouseWheelDownAction.getAmount();
        return new Point( vUp, vDown );
    }

    /**
     * Obtém um vetor com movimentação da roda de rolagem do mouse.
     * Em x a movimentação para cima e em y a movimentação para baixo.
     * 
     * @return Um vetor com a movimentação da roda de rolagem do mouse.
     */
    public Vector2 getMouseWheelMoveVector() {
        double vUp = mouseWheelUpAction.getAmount();
        double vDown = mouseWheelDownAction.getAmount();
        return new Vector2( vUp, vDown );
    }



    /***************************************************************************
     * Tratamento do teclado.
     **************************************************************************/
    
    /**
     * Configura a tecla de saída. Por padrão é a tecla <ESC>
     * 
     * @param keyCode Código da tecla.
     */
    public void setExitKey( int keyCode ) {
        exitKeyCode = keyCode;
    }
    
     /**
     * Registra um código de tecla para ser "ouvido".
     * 
     * @param keyCode O código da tecla desejada.
     */
    @SuppressWarnings( "unused" )
    private void registerKey( int keyCode ) {
        inputManager.mapToKey( new GameAction( "key " + keyCode ), keyCode );
        inputManager.mapToKey( new GameAction( "key " + keyCode, true ), keyCode );
    }

    /**
     * Registra todas as teclas configuradas como constantes.
     */
    private void registerAllKeys() {

        try {

            Class<? extends Engine> klass = Engine.class;
            
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
            exc.printStackTrace();
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
     * Retorna se uma tecla foi pressionada uma vez.
     * 
     * @param keyCode O inteiro que identifica a tecla desejado.
     * @return Verdadeiro caso a tecla tenha sido pressionada uma vez, falso caso contrário.
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
     * Retorna se uma tecla foi solta.
     * 
     * @param keyCode O inteiro que identifica a tecla desejada.
     * @return Verdadeiro caso a tecla tenha sido solta, falso caso contrário.
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
     * Retorna se uma tecla está pressionada.
     * 
     * @param keyCode O inteiro que identifica a tecla desejad.
     * @return Verdadeiro caso a tecla esteja pressionada, falso caso contrário.
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
     * Retorna se uma tecla não está pressionada.
     * 
     * @param keyCode O inteiro que identifica a tecla desejada.
     * @return Verdadeiro caso a tecla não esteja pressionada, falso caso contrário.
     */
    public boolean isKeyUp( int keyCode ) {
        processKeysState( keyCode );
        return keysUpMap.getOrDefault( keyCode, false );
    }
    /*public boolean isKeyUp( int keyCode ) {
        return !isKeyDown( keyCode );
    }*/

    /**
     * Retorna um conjunto dos códigos das teclas pressionadas no momento.
     * 
     * @return Um conjunto dos códigos de teclas pressionadas.
     */
    public Set<Integer> getKeyPressed() {
        return inputManager.getKeysFromPressedActions();
    }


    
    /***************************************************************************
     * Métodos de desenho
     **************************************************************************/

    /**
     * Desenha um pixel.
     * 
     * @param x coordenada x do pixel.
     * @param y coordenada y do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( double x, double y, Color color ) {
        g2d.setColor( color );
        g2d.draw( new Line2D.Double( x, y, x, y ) );
    }

    /**
     * Desenha um pixel.
     * 
     * @param vector vetor do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( Vector2 vector, Color color ) {
        drawPixel( vector.x, vector.y, color );
    }

    /**
     * Desenha um pixel.
     * 
     * @param point ponto do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( Point point, Color color ) {
        drawPixel( point.x, point.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startPosX coordenada x do ponto inicial.
     * @param startPosY coordenada y do ponto inicial.
     * @param endPosX coordenada x do ponto final.
     * @param endPosY coordenada y do ponto final.
     * @param color cor de desenho.
     */
    public void drawLine( double startPosX, double startPosY, double endPosX, double endPosY, Color color ) {
        g2d.setColor( color );
        g2d.draw( new Line2D.Double( startPosX, startPosY, endPosX, endPosY ) );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startVector vetor inicial.
     * @param endVector vetor final.
     * @param color cor de desenho.
     */
    public void drawLine( Vector2 startVector, Vector2 endVector, Color color ) {
        drawLine( startVector.x, startVector.y, endVector.x, endVector.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startPoint ponto inicial.
     * @param endPoint ponto final.
     * @param color cor de desenho.
     */
    public void drawLine( Point startPoint, Point endPoint, Color color ) {
        drawLine( startPoint.x, startPoint.y, endPoint.x, endPoint.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param line uma linha.
     * @param color cor de desenho.
     */
    public void drawLine( Line line, Color color ) {
        drawLine( line.x1, line.y1, line.x2, line.y2, color );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color cor de desenho.
     */
    public void drawRectangle( double x, double y, double width, double height, Color color ) {
        g2d.setColor( color );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Color color ) {
        drawRectangle( pos.x, pos.y, width, height, color );
    }
    
    /**
     * Desenha um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void drawRectangle( Point pos, double width, double height, Color color ) {
        drawRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param rectangle um retângulo.
     * @param color cor de desenho.
     */
    public void drawRectangle( Rectangle rectangle, Color color ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color cor de desenho.
     */
    public void fillRectangle( double x, double y, double width, double height, Color color ) {
        g2d.setColor( color );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Color color ) {
        fillRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void fillRectangle( Point pos, double width, double height, Color color ) {
        fillRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param rectangle um retângulo.
     * @param color cor de desenho.
     */
    public void fillRectangle( Rectangle rectangle, Color color ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param originX coordenada x do pivô da rotação.
     * @param originY coordenada y do pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( double x, double y, double width, double height, double originX, double originY, double rotation, Color color ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setColor( color );

        gc.rotate( Math.toRadians( rotation ), originX, originY );
        gc.draw( new Rectangle2D.Double( x, y, width, height ) );

        gc.dispose();

    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Vector2 pos, double width, double height, Point origin, double rotation, Color color ) {
        drawRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Point pos, double width, double height, Point origin, double rotation, Color color ) {
        drawRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param rectangle um retângulo.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Rectangle rectangle, Point origin, double rotation, Color color ) {
        drawRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param originX coordenada x do pivô da rotação.
     * @param originY coordenada y do pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( double x, double y, double width, double height, double originX, double originY, double rotation, Color color ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setColor( color );

        gc.rotate( Math.toRadians( rotation ), originX, originY );
        gc.fill( new Rectangle2D.Double( x, y, width, height ) );

        gc.dispose();

    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Vector2 pos, double width, double height, Point origin, double rotation, Color color ) {
        fillRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Point pos, double width, double height, Point origin, double rotation, Color color ) {
        fillRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param rectangle um retângulo.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Rectangle rectangle, Point origin, double rotation, Color color ) {
        fillRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( double x, double y, double width, double height, double roundness, Color color ) {
        g2d.setColor( color );
        g2d.draw( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( Vector2 pos, double width, double height, double roundness, Color color ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( Point pos, double width, double height, double roundness, Color color ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param roundRectangle um retângulo com os cantos arredondados.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( RoundRectangle roundRectangle, Color color ) {
        drawRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( double x, double y, double width, double height, double roundness, Color color ) {
        g2d.setColor( color );
        g2d.fill( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( Vector2 pos, double width, double height, double roundness, Color color ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( Point pos, double width, double height, double roundness, Color color ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param roundRectangle um retângulo com os cantos arredondados.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( RoundRectangle roundRectangle, Color color ) {
        fillRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, color );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( double x, double y, double width, double height, Color color1, Color color2 ) {
        g2d.setPaint( new GradientPaint( (int) x, (int) (y + height / 2), color1, (int) (x + width), (int) (y + height / 2), color2 ) );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Vector2 pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientH( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Point pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientH( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param rectangle um retângulo.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Rectangle rectangle, Color color1, Color color2 ) {
        fillRectangleGradientH( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param x coordenada x do vértice superior esquerdo do retângulo.
     * @param y coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( double x, double y, double width, double height, Color color1, Color color2 ) {
        g2d.setPaint( new GradientPaint( (int) (x + width / 2), (int) y, color1, (int) (x + width / 2), (int) (y + height), color2 ) );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Point pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientV( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Vector2 pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientV( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param rectangle um retângulo.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Rectangle rectangle, Color color1, Color color2 ) {
        fillRectangleGradientV( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color1, color2 );
    }

    /**
     * Desenha um círculo.
     * 
     * @param centerX coordenada x do centro do círculo.
     * @param centerY coordenada y do centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( double centerX, double centerY, double radius, Color color ) {
        g2d.setColor( color );
        g2d.draw( new Ellipse2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Desenha um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( Vector2 center, double radius, Color color ) {
        drawCircle( center.x, center.y, radius, color );
    }

    /**
     * Desenha um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( Point center, double radius, Color color ) {
        drawCircle( center.x, center.y, radius, color );
    }

    /**
     * Desenha um círculo.
     * 
     * @param circle um círculo.
     * @param color cor de desenho.
     */
    public void drawCircle( Circle circle, Color color ) {
        drawCircle( circle.x, circle.y, circle.radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param centerX coordenada x do centro do círculo.
     * @param centerY coordenada y do centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( double centerX, double centerY, double radius, Color color ) {
        g2d.setColor( color );
        g2d.fill( new Ellipse2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Pinta um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( Vector2 center, double radius, Color color ) {
        fillCircle( center.x, center.y, radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( Point center, double radius, Color color ) {
        fillCircle( center.x, center.y, radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param circle um círculo.
     * @param color cor de desenho.
     */
    public void fillCircle( Circle circle, Color color ) {
        fillCircle( circle.x, circle.y, circle.radius, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param centerX coordenada x do centro da elipse.
     * @param centerY coordenada y do centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( double centerX, double centerY, double radiusH, double radiusV, Color color ) {
        g2d.setColor( color );
        g2d.draw( new Ellipse2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( Vector2 center, double radiusH, double radiusV, Color color ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( Point center, double radiusH, double radiusV, Color color ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param color cor de desenho.
     */
    public void drawEllipse( Ellipse ellipse, Color color ) {
        drawEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param centerX coordenada x do centro da elipse.
     * @param centerY coordenada y do centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( double centerX, double centerY, double radiusH, double radiusV, Color color ) {
        g2d.setColor( color );
        g2d.fill( new Ellipse2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( Vector2 center, double radiusH, double radiusV, Color color ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( Point center, double radiusH, double radiusV, Color color ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param color cor de desenho.
     */
    public void fillEllipse( Ellipse ellipse, Color color ) {
        fillEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( double centerX, double centerY, double radius, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Color color ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( Point center, double radius, double startAngle, double endAngle, Color color ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circle um círculo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawCircleSector( Circle circle, double startAngle, double endAngle, Color color ) {
        drawCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circleSector um setor circular.
     * @param color cor de desenho.
     */
    public void drawCircleSector( CircleSector circleSector, Color color ) {
        drawCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( double centerX, double centerY, double radius, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Color color ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( Point center, double radius, double startAngle, double endAngle, Color color ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circle um círculo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillCircleSector( Circle circle, double startAngle, double endAngle, Color color ) {
        fillCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circleSector um setor circular.
     * @param color cor de desenho.
     */
    public void fillCircleSector( CircleSector circleSector, Color color ) {
        fillCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipseSector um setor de uma elipse.
     * @param color cor de desenho.
     */
    public void drawEllipseSector( EllipseSector ellipseSector, Color color ) {
        drawEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipseSector um setor de uma elipse.
     * @param color cor de desenho.
     */
    public void fillEllipseSector( EllipseSector ellipseSector, Color color ) {
        fillEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, color );
    }

    /**
     * Desenha um arco.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawArc( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.OPEN ) );
    }

    /**
     * Desenha um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawArc( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um arco
     * 
     * @param arc um arco.
     * @param color cor de desenho.
     */
    public void drawArc( Arc arc, Color color ) {
        drawArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, color );
    }

    /**
     * Pinta um arco.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillArc( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.CHORD ) );
    }

    /**
     * Pinta um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillArc( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um arco
     * 
     * @param arc um arco.
     * @param color cor de desenho.
     */
    public void fillArc( Arc arc, Color color ) {
        fillArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        g2d.draw( DrawingUtils.createRing( centerX, centerY, innerRadius, outerRadius, startAngle, endAngle ) );
    }

    /**
     * Desenha um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawRing( Point center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param ring um anel.
     * @param color cor de desenho.
     */
    public void drawRing( Ring ring, Color color ) {
        drawRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        g2d.fill( DrawingUtils.createRing( centerX, centerY, innerRadius, outerRadius, startAngle, endAngle ) );
    }

    /**
     * Pinta um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus (sentido horário).
     * @param endAngle ângulo final em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillRing( Point center, double innerRadius, double outerRadius, double startAngle, double endAngle, Color color ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param ring um anel.
     * @param color cor de desenho.
     */
    public void fillRing( Ring ring, Color color ) {
        fillRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1x coordenada x do primeiro vértice.
     * @param v1y coordenada y do primeiro vértice.
     * @param v2x coordenada x do segundo vértice.
     * @param v2y coordenada y do segundo vértice.
     * @param v3x coordenada x do terceiro vértice.
     * @param v3y coordenada y do terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Color color ) {
        g2d.setColor( color );
        g2d.draw( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Color color ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( Point v1, Point v2, Point v3, Color color ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param triangle um triângulo.
     * @param color cor de desenho.
     */
    public void drawTriangle( Triangle triangle, Color color ) {
        drawTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1x coordenada x do primeiro vértice.
     * @param v1y coordenada y do primeiro vértice.
     * @param v2x coordenada x do segundo vértice.
     * @param v2y coordenada y do segundo vértice.
     * @param v3x coordenada x do terceiro vértice.
     * @param v3y coordenada y do terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Color color ) {
        g2d.setColor( color );
        g2d.fill( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Color color ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( Point v1, Point v2, Point v3, Color color ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param triangle um triângulo.
     * @param color cor de desenho.
     */
    public void fillTriangle( Triangle triangle, Color color ) {
        fillTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param centerX coordenada x do centro do polígono.
     * @param centerY coordenada y do centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawPolygon( double centerX, double centerY, double sides, double radius, double rotation, Color color ) {
        g2d.setColor( color );
        g2d.draw( DrawingUtils.createPolygon( centerX, centerY, sides, radius, rotation ) );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawPolygon( Vector2 center, double sides, double radius, double rotation, Color color ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawPolygon( Point center, double sides, double radius, double rotation, Color color ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param polygon um polígono regular.
     * @param color cor de desenho.
     */
    public void drawPolygon( Polygon polygon, Color color ) {
        drawPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param centerX coordenada x do centro do polígono.
     * @param centerY coordenada y do centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillPolygon( double centerX, double centerY, double sides, double radius, double rotation, Color color ) {
        g2d.setColor( color );
        g2d.fill( DrawingUtils.createPolygon( centerX, centerY, sides, radius, rotation ) );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillPolygon( Vector2 center, double sides, double radius, double rotation, Color color ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void fillPolygon( Point center, double sides, double radius, double rotation, Color color ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param polygon um polígono regular.
     * @param color cor de desenho.
     */
    public void fillPolygon( Polygon polygon, Color color ) {
        fillPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, color );
    }

    /**
     * Desenha um caminho.
     * 
     * @param path caminho a ser desenhado.
     * @param color cor de desenho.
     */
    public void drawPath( Path path, Color color ) {
        g2d.setColor( color );
        g2d.draw( path.path );
    }

    /**
     * Ponta um caminho.
     * 
     * @param path caminho a ser desenhado.
     * @param color cor de desenho.
     */
    public void fillPath( Path path, Color color ) {
        g2d.setColor( color );
        g2d.fill( path.path );
    }

    
    
    /***************************************************************************
     * Métodos de desenhos de curvas.
     **************************************************************************/

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Color color ) {
        g2d.setColor( color );
        g2d.draw( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Color color ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( Point p1, Point c, Point p2, Color color ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( QuadCurve quadCurve, Color color ) {
        drawQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Color color ) {
        g2d.setColor( color );
        g2d.fill( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Color color ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( Point p1, Point c, Point p2, Color color ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( QuadCurve quadCurve, Color color ) {
        fillQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Color color ) {
        g2d.setColor( color );
        g2d.draw( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Color color ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( Point p1, Point c1, Point c2, Point p2, Color color ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( CubicCurve cubicCurve, Color color ) {
        drawCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Color color ) {
        g2d.setColor( color );
        g2d.fill( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Color color ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( Point p1, Point c1, Point c2, Point p2, Color color ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( CubicCurve cubicCurve, Color color ) {
        fillCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, color );
    }
    

    
    /***************************************************************************
     * Métodos de desenho de texto.
     **************************************************************************/

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, Color color ) {
        g2d.setColor( color );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double rotation, Color color ) {
        drawText( text, x, y, 0, 0, rotation, color );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param originX coordenada x do pivô de rotação.
     * @param originY coordenada y do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, Color color ) {
        g2d.setColor( color );
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = ig2d.getFontMetrics().getStringBounds( text, ig2d );
        //ig2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, ig2d );
        ig2d.dispose();
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, int fontSize, Color color ) {
        g2d.setColor( color );
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        //Rectangle2D r = ig2d.getFontMetrics().getStringBounds( text, ig2d );
        //ig2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, ig2d );
        ig2d.dispose();
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double rotation, int fontSize, Color color ) {
        drawText( text, x, y, 0, 0, rotation, fontSize, color );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param x coordenada x do início do desenho do texto.
     * @param y coordenada y do início do desenho do texto.
     * @param originX coordenada x do pivô de rotação.
     * @param originY coordenada y do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, int fontSize, Color color ) {
        g2d.setColor( color );
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = ig2d.getFontMetrics().getStringBounds( text, ig2d );
        //ig2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, ig2d );
        ig2d.dispose();
    }

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, Color color ) {
        drawText( text, point.x, point.y, color );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, double rotation, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, color );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, Point origin, double rotation, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, int fontSize, Color color ) {
        drawText( text, point.x, point.y, fontSize, color );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, color );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, Point origin, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, color );
    }

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, Color color ) {
        drawText( text, point.x, point.y, color );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, color );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, int fontSize, Color color ) {
        drawText( text, point.x, point.y, fontSize, color );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, color );
    }

    /**
     * Desenha um texto rotacionado.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation ângulo de rotação em graus (sentido horário).
     * @param fontSize tamanho da fonte.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, int fontSize, Color color ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, color );
    }
    
    /**
     * Mede a largura de um texto.
     * 
     * @param text o texto a ser medido.
     * @return a largura de um texto.
     */
    public int measureText( String text ) {
        return g2d.getFontMetrics().stringWidth( text );
    }

    /**
     * Mede a largura de um texto.
     * 
     * @param text o texto a ser medido.
     * @param fontSize tamanho da fonte.
     * @return a largura de um texto.
     */
    public int measureText( String text, int fontSize ) {
        Font f = g2d.getFont();
        g2d.setFont( f.deriveFont( (float) fontSize ) );
        int width = g2d.getFontMetrics().stringWidth( text );
        g2d.setFont( f );
        return width;
    }
    
    

    /***************************************************************************
     * Métodos utilitários variados.
     **************************************************************************/

    /**
     * Limpa o fundo da tela de desenho.
     * 
     * @param color cor a ser usada.
     */
    public void clearBackground( Color color ) {
        fillRectangle( 0, 0, getScreenWidth(), getScreenHeight(), color );
    }

    /***************************************************************************
     * Métodos para obtenção e/ou configuração de opções e/ou dados
     * relativos à execução.
     **************************************************************************/
    
    /**
     * Configura o quantidade de quadros por segundo desejado para a execução
     * do jogo/simulação.
     * 
     * @param targetFPS A quantidade de quadros por segundo.
     */
    public void setTargetFPS( int targetFPS ) {

        if ( targetFPS <= 0 ) {
            throw new IllegalArgumentException( "target FPS must be positive!" );
        }

        this.targetFPS = targetFPS;

    }
    
    /**
     * Configura o ícone da janela.
     * 
     * @param image Imagem utilizada para configurar o ícone.
     */
    public void setWindowIcon( Image image ) {
        setIconImage( image.buffImage );
    }

    /**
     * Obtém o tempo que um frame demorou para ser atualizado e desenhado.
     * 
     * @return o tempo que um frame demorou para ser atualizado e desenhado.
     */
    public double getFrameTime() {
        return frameTime / 1000.0;
    }

    /**
     * Obtém o tempo atual de execução do jogo/simulação, em segundos.
     * 
     * @return O tempo atual de execução do jogo/simulação, em segundos.
     */
    public double getTime() {
        return ( System.currentTimeMillis() - startTime ) / 1000.0;
    }

    /**
     * Obtém a quantidade de quadros por segundo atual.
     * 
     * @return a quantidade de quadros por segundo atual.
     */
    public int getFPS() {
        return currentFPS;
    }

    /**
     * Obtém a largura da tela.
     * 
     * @return largura da tela.
     */
    public int getScreenWidth() {
        return drawingPanel.getWidth();
    }

    /**
     * Obtém a altura da tela.
     * 
     * @return altura da tela.
     */
    public int getScreenHeight() {
        return drawingPanel.getHeight();
    }

    /**
     * Obtém o contexto gráfico atual.
     * Observação: Utilize apenas no método draw!
     * 
     * @return O contexto gráfico atual.
     */
    public Graphics2D getGraphics2D() {
        return g2d;
    }

    /**
     * Rotaciona o contexto gráfico atual a partir da coordenada (0, 0).
     * Observação: Utilize apenas no método draw!
     * 
     * @param degrees Medida em graus para o ângulo de rotação.
     */
    public void rotate( double degrees ) {
        g2d.rotate( Math.toRadians( degrees ) );
    }

    /**
     * Rotaciona o contexto gráfico atual a partir de uma coordenada
     * Observação: Utilize apenas no método draw!
     * 
     * @param x Coordenada x do ponto de rotação.
     * @param y Coordenada y do ponto de rotação.
     * @param degrees Medida em graus para o ângulo de rotação.
     */
    public void rotate( double degrees, double x, double y ) {
        g2d.rotate( Math.toRadians( degrees ), x, y );
    }

    /**
     * Translada o contexto gráfico atual.
     * Observação: Utilize apenas no método draw!
     * 
     * @param x Nova origem em x.
     * @param y Nova origem em y.
     */
    public void translate( double x, double y ) {
        g2d.translate( x, y );
    }

    /**
     * Escalona o contexto gráfico atual.
     * Observação: Utilize apenas no método draw!
     * 
     * @param x Nova escala em x.
     * @param y Nova escala em y.
     */
    public void scale( double x, double y ) {
        g2d.scale( x, y );
    }

    /**
     * Desenha o quantidade de FPS (quadros por segundo) atual.
     * 
     * @param x A posição em x para o desenho.
     * @param y A posição em y para o desenho.
     */
    public void drawFPS( double x, double y ) {

        Font t = g2d.getFont();
        g2d.setFont( defaultFPSFont );

        drawText( 
            String.format( "%d FPS", currentFPS ), 
            x, y, ColorUtils.lerp( RED, LIME, currentFPS / (double) targetFPS ) );

        g2d.setFont( t );

    }



    /***************************************************************************
     * Métodos para configuração da fonte e do contorno.
     **************************************************************************/

    /**
     * Altera a fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param font Fonte a ser usada.
     */
    public void setDefaultFont( Font font ) {
        this.defaultFont = font;
    }

    /**
     * Altera o nome da fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param name Nome da fonte padrão.
     */
    public void setDefaultFontName( String name ) {
        defaultFont = new Font( defaultFont.getName(), defaultFont.getStyle(), defaultFont.getSize() );
    }

    /**
     * Altera o estilo da fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param style O estilo da fonte padrão.
     */
    public void setDefaultFontStyle( int style ) {
        defaultFont = defaultFont.deriveFont( style );
    }

    /**
     * Altera o tamanho da fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param size O tamanho da fonte padrão.
     */
    public void setDefaultFontSize( int size ) {
        defaultFont = defaultFont.deriveFont( (float) size );
    }

    /**
     * Altera o nome da fonte corrente do contexto gráfico.
     * Cuidado, essa operação alterará a fonte toda vez que for executada.
     * 
     * @param name Nome da fonte.
     */
    public void setFontName( String name ) {
        g2d.setFont( new Font( name, g2d.getFont().getStyle(), g2d.getFont().getSize() ) );
    }

    /**
     * Altera o estilo da fonte corrente do contexto gráfico.
     * Cuidado, essa operação alterará a fonte toda vez que for executada.
     * 
     * @param style O estilo da fonte corrente.
     */
    public void setFontStyle( int style ) {
        g2d.setFont( g2d.getFont().deriveFont( style ) );
    }

    /**
     * Altera o tamanho da fonte corrente do contexto gráfico.
     * Cuidado, essa operação alterará a fonte toda vez que for executada.
     * 
     * @param size O tamanho da fonte corrente.
     */
    public void setFontSize( int size ) {
        g2d.setFont( g2d.getFont().deriveFont( (float) size ) );
    }

    /**
     * Altera o contorno padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param stroke Contorno a ser usado.
     */
    public void setDefaultStroke( BasicStroke stroke ) {
        this.defaultStroke = stroke;
    }

    /**
     * Altera a largura da linha do contorno padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param width A largura da linha do contorno padrão.
     */
    public void setDefaultStrokeWidth( float width ) {
        defaultStroke = new BasicStroke( width, defaultStroke.getEndCap(), defaultStroke.getLineJoin() );
    }

    /**
     * Altera o modelo desenho do fim das linhas do contorno padrão do contexto
     * gráfico.
     * Utilize no método create();
     * 
     * @param endCap O novo modelo de desenho.
     */
    public void setDefaultStrokeEndCap( int endCap ) {
        defaultStroke = new BasicStroke( defaultStroke.getLineWidth(), endCap, defaultStroke.getLineJoin() );
    }

    /**
     * Altera o modelo de junção de linhas do contorno padrão do contexto
     * gráfico.
     * Utilize no método create();
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public void setDefaultStrokeLineJoin( int lineJoin ) {
        defaultStroke = new BasicStroke( defaultStroke.getLineWidth(), defaultStroke.getEndCap(), lineJoin );
    }

    /**
     * Altera a largura da linha do contorno corrente do contexto gráfico.
     * Cuidado, essa operação alterará o contorno toda vez que for executada.
     * 
     * @param width A largura da linha do contorno padrão.
     */
    public void setStrokeWidth( float width ) {
        g2d.setStroke( new BasicStroke( width, defaultStroke.getEndCap(), defaultStroke.getLineJoin() ) );
    }

    /**
     * Altera o modelo de desenho do fim das linhas do contorno corrente do contexto
     * gráfico.
     * Cuidado, essa operação alterará o contorno toda vez que for executada.
     * 
     * @param endCap O novo modelo de desenho.
     */
    public void setStrokeEndCap( int endCap ) {
        g2d.setStroke( new BasicStroke( defaultStroke.getLineWidth(), endCap, defaultStroke.getLineJoin() ) );
    }

    /**
     * Altera o modelo de junção de linhas do contorno corrente do contexto
     * gráfico.
     * Cuidado, essa operação alterará o contorno toda vez que for executada.
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public void setStrokeLineJoin( int lineJoin ) {
        g2d.setStroke( new BasicStroke( defaultStroke.getLineWidth(), defaultStroke.getEndCap(), lineJoin ) );
    }

    
    
    /***************************************************************************
     * Métodos para carga e desenho de imagens.
     **************************************************************************/
    /**
     * Carrega uma imagem.
     * 
     * @param filePath Caminho do arquivo da imagem.
     * @return Uma imagem.
     */
    public Image loadImage( String filePath ) {
        
        try {
            return new Image( ImageIO.read( new File( filePath ) ) );
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        return ImageUtils.createTextImage( "error", 20, Font.BOLD, WHITE, BLACK );
        
    }
    
    /**
     * Carrega uma imagem.
     * 
     * @param input Um input stream para uma imagem.
     * @return Uma imagem.
     */
    public Image loadImage( InputStream input ) {
        
        try {
            return new Image( ImageIO.read( input ) );
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        return ImageUtils.createTextImage( "error", 20, Font.BOLD, WHITE, BLACK );
        
    }
    
    /**
     * Carrega uma imagem.
     * 
     * @param url Uma URL para uma imagem.
     * @return Uma imagem.
     */
    public Image loadImage( URL url ) {
        
        try {
            return new Image( ImageIO.read( url ) );
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        return ImageUtils.createTextImage( "error", 20, Font.BOLD, WHITE, BLACK );
        
    }
    
    /**
     * Desenha uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, double x, double y, Color bgColor ) {
        g2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
    }
    
    /**
     * Desenha uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     */
    public void drawImage( Image image, double x, double y ) {
        drawImage( image, x, y, null );
    }
    
    /**
     * Desenha uma imagem rotacionada com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, x, y, 0, 0, rotation, bgColor );
    }
    
    /**
     * Desenha uma imagem rotacionada.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, double x, double y, double rotation ) {
        drawImage( image, x, y, 0, 0, rotation, null );
    }
    
    /**
     * Desenha uma imagem rotacionada com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        ig2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
        ig2d.dispose();
    }
    
    /**
     * Desenha uma imagem rotacionada.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, x, y, originX, originY, rotation, null );
    }
    
    /**
     * Desenha o recorte de uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, Color bgColor ) {
        g2d.drawImage( image.buffImage, 
                (int) x, 
                (int) y, 
                (int) ( x + source.width ), 
                (int) ( y + source.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
    }
    
    /**
     * Desenha o recorte de uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     */
    public void drawImage( Image image, Rectangle source, double x, double y ) {
        drawImage( image, source, x, y, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, source, x, y, 0, 0, rotation, bgColor );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation ) {
        drawImage( image, source, x, y, 0, 0, rotation, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        ig2d.drawImage( image.buffImage, 
                (int) x, 
                (int) y, 
                (int) ( x + source.width ), 
                (int) ( y + source.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        ig2d.dispose();
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, source, x, y, originX, originY, rotation, null );
    }
    
    /**
     * Desenha o recorte de uma imagem em um retângulo de destino com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, Color bgColor ) {
        g2d.drawImage( image.buffImage, 
                (int) dest.x, 
                (int) dest.y, 
                (int) ( dest.x + dest.width ), 
                (int) ( dest.y + dest.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
    }
    
    /**
     * Desenha o recorte de uma imagem em um retângulo de destino.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest ) {
        drawImage( image, source, dest, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation, Color bgColor ) {
        drawImage( image, source, dest, 0, 0, rotation, bgColor );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation ) {
        drawImage( image, source, dest, 0, 0, rotation, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D ig2d = (Graphics2D) g2d.create();
        ig2d.rotate( Math.toRadians( rotation ), dest.x + originX, dest.y + originY );
        ig2d.drawImage( image.buffImage, 
                (int) dest.x, 
                (int) dest.y, 
                (int) ( dest.x + dest.width ), 
                (int) ( dest.y + dest.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        ig2d.dispose();
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation ) {
        drawImage( image, source, dest, originX, originY, rotation, null );
    }
    
    
    /***************************************************************************
     * Métodos para gerenciamento do cursor do mouse.
     **************************************************************************/
    /**
     * Configura o cursor do mouse.
     * 
     * @param cursor O identificador do cursor.
     */
    public void setMouseCursor( int cursor ) {
        currentCursor = Cursor.getPredefinedCursor( cursor );
        drawingPanel.setCursor( currentCursor );
    }
    
    /**
     * Mostra o cursor.
     */
    public void showCursor() {
        drawingPanel.setCursor( currentCursor );
    }
    
    /**
     * Esconde o cursor.
     */
    public void hideCursor() {
        drawingPanel.setCursor( INVISIBLE_CURSOR );
    }
    
    /**
     * Retorna se o cursor está escondido.
     * 
     * @return verdadeiro se o cursor estiver escondido, falso caso contrário.
     */
    public boolean isCursorHidden() {
        return drawingPanel.getCursor() == INVISIBLE_CURSOR;
    }
    
    
    
    /***************************************************************************
     * Métodos para controle da câmera
     **************************************************************************/
    
    /**
     * Inicia o modo 2D usando a câmera.
     * 
     * @param camera câmera que deve ser usada.
     */
    public void beginMode2D( Camera2D camera ) {
        
        if ( !mode2DActive ) {
            
            baseGraphics = g2d;
            cameraGraphics = (Graphics2D) g2d.create();
            
            // referência: MathUtils.getCameraMatrix2D
            cameraGraphics.translate( camera.offset.x, camera.offset.y );
            cameraGraphics.scale( camera.zoom, camera.zoom );
            cameraGraphics.rotate( Math.toRadians( camera.rotation ) );
            cameraGraphics.translate( -camera.target.x, -camera.target.y );
            
            g2d = cameraGraphics;
            mode2DActive = true;
            
        }
        
    }
    
    /**
     * Finaliza o modo 2D, voltando ao modo original
     */
    public void endMode2D() {
        if ( mode2DActive ) {
            g2d = baseGraphics;
            cameraGraphics.dispose();
            mode2DActive = false;
        }
    }
    
    
    
    /***************************************************************************
     * Métodos para controle de sons e músicas
     **************************************************************************/
    
    /**
     * Carrega um som de um arquivo.
     * 
     * @param filePath Caminho do arquivo.
     * @return Um novo som.
     */
    public static Sound loadSound( String filePath ) {
        return new Sound( filePath );
    };
    
    /**
     * Carrega um som de um input stream.
     * 
     * @param is Input stream.
     * @return Um novo som.
     */
    public static Sound loadSound( InputStream is ) {
        return new Sound( is );
    };
    
    /**
     * Carrega um som de uma URL.
     * 
     * @param url URL.
     * @return Um novo som.
     */
    public static Sound loadSound( URL url ) {
        return new Sound( url );
    };
    
    /**
     * Descarrega um som.
     * 
     * @param sound O som a ser descarregado.
     */
    public static void unloadSound( Sound sound ) {
        sound.unload();
    };
    
    /**
     * Executa o som.
     * 
     * @param sound O som.
     */
    public static void playSound( Sound sound ) {
        sound.play();
    };
    
    /**
     * Para de executar o som.
     * 
     * @param sound O som.
     */
    public static void stopSound( Sound sound ) {
        sound.stop();
    };
    
    /**
     * Pausa o som.
     * 
     * @param sound O som.
     */
    public void pauseSound( Sound sound ) {
        sound.pause();
    };
    
    /**
     * Retoma a execução do som.
     * 
     * @param sound O som.
     */
    public void resumeSound( Sound sound ) {
        sound.resume();
    };
    
    /**
     * Verifica se o som está executando.
     * 
     * @param sound O som.
     * @return Verdadeiro caso o som esteja em execução, falso caso contrário.
     */
    public boolean isSoundPlaying( Sound sound ) {
        return sound.isPlaying();
    };
    
    /**
     * Configura o volume do som.
     * 
     * @param sound O som.
     * @param volume O volume do som, variando de 0.0 a 1.0.
     */
    public void setSoundVolume( Sound sound, double volume ) {
        sound.setVolume( volume );
    };
    
    /**
     * Carrega uma música de um arquivo.
     * 
     * @param filePath Caminho do arquivo.
     * @return Uma nova música.
     */
    public static Music loadMusic( String filePath ) {
        return new Music( filePath );
    };
    
    /**
     * Carrega uma música de um input stream.
     * 
     * @param is Input stream.
     * @return Uma nova música.
     */
    public static Music loadMusic( InputStream is ) {
        return new Music( is );
    };
    
    /**
     * Carrega uma música de uma URL.
     * 
     * @param url URL.
     * @return Uma nova música.
     */
    public static Music loadMusic( URL url ) {
        return new Music( url );
    };
    
    /**
     * Descarrega uma música.
     * 
     * @param music A música a ser descarregado.
     */
    public static void unloadMusic( Music music ) {
        music.unload();
    };
    
    /**
     * Executa a música.
     * 
     * @param music A música.
     */
    public static void playMusic( Music music ) {
        music.play();
    };
    
    /**
     * Para de executar a música.
     * 
     * @param music A música.
     */
    public static void stopMusic( Music music ) {
        music.stop();
    };
    
    /**
     * Pausa a música.
     * 
     * @param music A música.
     */
    public void pauseMusic( Music music ) {
        music.pause();
    };
    
    /**
     * Retoma a execução da música.
     * 
     * @param music A música.
     */
    public void resumeMusic( Music music ) {
        music.resume();
    };
    
    /**
     * Verifica se a música está executando.
     * 
     * @param music A música.
     * @return Verdadeiro caso a música esteja em execução, falso caso contrário.
     */
    public boolean isMusicPlaying( Music music ) {
        return music.isPlaying();
    };
    
    /**
     * Configura o volume da música.
     * 
     * @param music A música.
     * @param volume O volume da música, variando de 0.0 a 1.0.
     */
    public void setMusicVolume( Music music, double volume ) {
        music.setVolume( volume );
    };
    
    /**
     * Procura uma posição da música.
     * 
     * @param music A música.
     * @param position Posição em segundos do momento desejado.
     */
    public static void seekMusic( Music music, int position ) {
        music.seek( position );
    };
    
    /**
     * Obtém a duração da da música.
     * 
     * @param music A música.
     * @return Duração da música em segundos.
     */
    public static int getMusicTimeLength( Music music ) {
        return music.getTimeLength();
    }
    
    /**
     * Obtém o tempo de execução da música.
     * 
     * @param music A música.
     * @return O tempo de execução em segundos.
     */
    public int getMusicTimePlayed( Music music ) {
        return music.getTimePlayed();
    }
    
    
    
    /***************************************************************************
     * Classes internas privadas.
     **************************************************************************/
    
    /**
     * Classe interna que encapsula o processo de desenho.
     */
    private class DrawingPanel extends JPanel {

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

            draw();
            g2d.dispose();

        }

    }

    
    
    /**
     * Classe interna para gerenciamento da entrada de teclas e mouse.
     * Os eventos são mapeados para GameActions.
     *
     * @author Prof. Dr. David Buzatto
     */
    private class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
        
        /**
         * Códigos do mouse (apenas para diferenciar as operações de rolagem
         * da roda do mouse).
         */
        public static final int MOUSE_WHEEL_UP = 3000;
        public static final int MOUSE_WHEEL_DOWN = 4000;

        private Map<Integer, List<GameAction>> keyActionsMap = new HashMap<>();
        private Map<Integer, List<GameAction>> mouseActionsMap = new HashMap<>();

        private java.awt.Point mouseLocation;
        private java.awt.Point centerLocation;
        private Component comp;
        private Robot robot;
        private boolean isRecentering;
        
        /**
         * Cria um novo InputManager que ouve as entradas de um componente 
         * específico.
         */
        public InputManager( Component comp ) {

            this.comp = comp;
            mouseLocation = new java.awt.Point();
            centerLocation = new java.awt.Point();

            // registra os ouvintes de tecla e do mouse
            comp.addKeyListener( this );
            comp.addMouseListener( this );
            comp.addMouseMotionListener( this );
            comp.addMouseWheelListener( this );
            
            /*
            * permite a entrada da tecla TAB e outras teclas normalmente usadas
            * pelo focus traversal.
            */
            comp.setFocusTraversalKeysEnabled( false );

        }
        
        /**
         * Configura o cursor no componente do InputManager.
         */
        @SuppressWarnings( "unused" )
        public void setCursor( Cursor cursor ) {
            comp.setCursor( cursor );
        }

        
        /**
         * Configura quando o modo relativo do mouse está ligado ou não.
         * Para o modo relativo do mouse, o cursor fica "trancado" no centro
         * da tela, e somente a mudança no movimento do mouse é medida.
         * No modo normal, o mouse fica livre para mover pela a tela.
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
                    // não pôde criar um Robot
                    robot = null;
                }
            } else {
                robot = null;
            }

        }
        
        /**
         * Retorna se o modo relativo do mouse está ligado ou não.
         */
        public boolean isRelativeMouseMode() {
            return ( robot != null );
        }

        /**
         * Mapeia uma GameAction para uma tecla específica.
         * Os códigos das telas são definidos em java.awt.KeyEvent, mas
         * remapeados para constantes iguais à da Raylib.
         * Cuidado, não há sobrescrição de actions, pois são listas.
         */
        public void mapToKey( GameAction gameAction, int keyCode ) {
            if ( !keyActionsMap.containsKey(keyCode) ) {
                keyActionsMap.put( keyCode, new ArrayList<>() );
            }
            keyActionsMap.get( keyCode ).add( gameAction );
        }
        
        /**
         * Mapeia uma GameAction para uma ação específica do mouse.
         * Os códigos do mouse são definidos aqui nas constantes da engine
         * Cuidado, não há sobrescrição de actions, pois são listas.
         */
        public void mapToMouse( GameAction gameAction, int mouseCode ) {
            if ( !mouseActionsMap.containsKey(mouseCode) ) {
                mouseActionsMap.put( mouseCode, new ArrayList<>() );
            }
            mouseActionsMap.get( mouseCode ).add( gameAction );
        }
        
        /**
         * Limpa todas as teclas mapeadas e ações do mouse para essa GameAction.
         */
        @SuppressWarnings( "unused" )
        public void clearMap( GameAction gameAction ) {
            keyActionsMap.clear();
            mouseActionsMap.clear();
            gameAction.reset();
        }
        
        /**
         * Reseta todas as GameAction, então elas ficam em um estado que parece
         * que elas não foram executadas.
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
         * Obtém o nome de um código de tecla.
         */
        @SuppressWarnings( "unused" )
        public static String getKeyName( int keyCode ) {
            return KeyEvent.getKeyText( keyCode );
        }
        
        /**
         * Obtém o nome de um código do mouse.
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
         * Obtém a posição x do mouse.
         */
        public int getMouseX() {
            return mouseLocation.x;
        }
        
        /**
         * Obtém a posição y do mouse.
         */
        public int getMouseY() {
            return mouseLocation.y;
        }

        /**
         * Usa a classe Robot para tentar posicionar o mouse no centro da tela.
         * Note que o uso da classe Robot pode não ser possível em todas as 
         * plataformas.
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
         * Retorna as GameActions associadas ao KeyEvent.
         */
        private List<GameAction> getKeyActions( KeyEvent e ) {

            int keyCode = e.getKeyCode();

            if ( keyActionsMap.containsKey( keyCode ) ) {
                return keyActionsMap.get( keyCode );
            }

            return null;

        }

        /**
         * Retorna as GameActions associada ao código da tecla.
         */
        public List<GameAction> getKeyActions( int keyCode ) {

            if ( keyActionsMap.containsKey( keyCode ) ) {
                return keyActionsMap.get( keyCode );
            }

            return null;

        }

        /**
         * Obtém um conjunto com os códigos de todas as teclas pressionadas
         * no momento. Retorna um conjunto com apenas o código nulo caso nenhuma
         * tecla tenha sido pressionada.
         * 
         * @return Um conjunto com o código das teclas pressionadas.
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
         * Obtém o código do mouse para o botão especificado no MouseEvent
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
         * Retorna as GameActions associadas ao MouseEvent.
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
            // dá certeza que a tecla não é processada por mais ninguém
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

            // dá certeza que a tecla não é processada por mais ninguém
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

            // dá certeza que a tecla não é processada por mais ninguém
            e.consume();

        }

        @Override
        public void mouseClicked( MouseEvent e ) {
            // não faz nada
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

            // este evento é para recentralizar o mouse
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
         * Calcula e configura a movimentação do mouse.
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
     * A classe GameAction é uma abstração para uma ação iniciada pelo usuário,
     * como pular ou mover. As GameActions podem ser mapeadas para teclas ou
     * mouse usando o InputManager. Atualmente não são expostas, pois são
     * utilizadas para simular o comportamento da Raylib.
     *
     * @author Prof. Dr. David Buzatto
     */
    public class GameAction {
        
        private static final int STATE_RELEASED = 0;
        private static final int STATE_PRESSED = 1;
        private static final int STATE_WAITING_FOR_RELEASE = 2;
        
        private String name;
        private boolean initialPressOnly;
        private int amount;
        private int state;
        
        /**
         * Cria uma nova GameAction com comportamento normal.
         */
        public GameAction( String name ) {
            this( name, false );
        }
        
        /**
         * Cria uma nova GameAction com o comportamento de detectar
         * apenas o pressionamento inicial.
         */
        public GameAction( String name, boolean initialPressOnly ) {
            this.name = name;
            this.initialPressOnly = initialPressOnly;
            reset();
        }
        
        /**
         * Obtém o nome dessa GameAction.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Retorna se a ação é só de pressionamento inicial.
         * @return 
         */
        public boolean isInitialPressOnly() {
            return initialPressOnly;
        }

        /**
         * Reseta esta GameAction, fazendo parecer que esta não foi pressionada.
         */
        public void reset() {
            state = STATE_RELEASED;
            amount = 0;
        }
        
        /**
         * Pressionamento rápido para essa GameAction. O mesmo que chamar press()
         * seguido de release().
         */
        public synchronized void tap() {
            press();
            release();
        }
        
        /**
         * Sinaliza que a tecla foi pressionada.
         */
        public synchronized void press() {
            press( 1 );
        }
        
        /**
         * Sinaliza que a tecla foi pressionada na quantidade de vezes especificada,
         * ou que o mouse se moveu numa distância especificada.
         */
        public synchronized void press( int amount ) {
            if ( state != STATE_WAITING_FOR_RELEASE ) {
                this.amount += amount;
                state = STATE_PRESSED;
            }
        }
        
        /**
         * Sinaliza que a tecla foi solta.
         */
        public synchronized void release() {
            state = STATE_RELEASED;
        }
        
        /**
         * Retorna se a tecla foi pressionada ou não desde a última checagem.
         */
        public synchronized boolean isPressed() {
            return ( getAmount() != 0 );
        }
        
        /**
         * Para teclas, é a quantidade de vezes que a tecla foi pressionada desde
         * a última vez que foi checada.
         * Para eventos do mouse é a distância que cursor foi movido.
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

    
    
    /***************************************************************************
     * Constantes para controle de teclado e mouse, iguais à da raylib.
     **************************************************************************/
    public static final int KEY_NULL            = 0;                         // Tecla: NULL, usada para indicar que nenhuma tecla foi pressionada

    // alfa-numéricos
    public static final int KEY_APOSTROPHE      = KeyEvent.VK_QUOTE;         // Tecla: '
    public static final int KEY_COMMA           = KeyEvent.VK_COMMA;         // Tecla: ,
    public static final int KEY_MINUS           = KeyEvent.VK_MINUS;         // Tecla: -
    public static final int KEY_PERIOD          = KeyEvent.VK_PERIOD;        // Tecla: .
    public static final int KEY_SLASH           = KeyEvent.VK_SLASH;         // Tecla: /
    public static final int KEY_ZERO            = KeyEvent.VK_0;             // Tecla: 0
    public static final int KEY_ONE             = KeyEvent.VK_1;             // Tecla: 1
    public static final int KEY_TWO             = KeyEvent.VK_2;             // Tecla: 2
    public static final int KEY_THREE           = KeyEvent.VK_3;             // Tecla: 3
    public static final int KEY_FOUR            = KeyEvent.VK_4;             // Tecla: 4
    public static final int KEY_FIVE            = KeyEvent.VK_5;             // Tecla: 5
    public static final int KEY_SIX             = KeyEvent.VK_6;             // Tecla: 6
    public static final int KEY_SEVEN           = KeyEvent.VK_7;             // Tecla: 7
    public static final int KEY_EIGHT           = KeyEvent.VK_8;             // Tecla: 8
    public static final int KEY_NINE            = KeyEvent.VK_9;             // Tecla: 9
    public static final int KEY_SEMICOLON       = KeyEvent.VK_SEMICOLON;     // Tecla: ;
    public static final int KEY_EQUAL           = KeyEvent.VK_EQUALS;        // Tecla: =
    public static final int KEY_A               = KeyEvent.VK_A;             // Tecla: A | a
    public static final int KEY_B               = KeyEvent.VK_B;             // Tecla: B | b
    public static final int KEY_C               = KeyEvent.VK_C;             // Tecla: C | c
    public static final int KEY_D               = KeyEvent.VK_D;             // Tecla: D | d
    public static final int KEY_E               = KeyEvent.VK_E;             // Tecla: E | e
    public static final int KEY_F               = KeyEvent.VK_F;             // Tecla: F | f
    public static final int KEY_G               = KeyEvent.VK_G;             // Tecla: G | g
    public static final int KEY_H               = KeyEvent.VK_H;             // Tecla: H | h
    public static final int KEY_I               = KeyEvent.VK_I;             // Tecla: I | i
    public static final int KEY_J               = KeyEvent.VK_J;             // Tecla: J | j
    public static final int KEY_K               = KeyEvent.VK_K;             // Tecla: K | k
    public static final int KEY_L               = KeyEvent.VK_L;             // Tecla: L | l
    public static final int KEY_M               = KeyEvent.VK_M;             // Tecla: M | m
    public static final int KEY_N               = KeyEvent.VK_N;             // Tecla: N | n
    public static final int KEY_O               = KeyEvent.VK_O;             // Tecla: O | o
    public static final int KEY_P               = KeyEvent.VK_P;             // Tecla: P | p
    public static final int KEY_Q               = KeyEvent.VK_Q;             // Tecla: Q | q
    public static final int KEY_R               = KeyEvent.VK_R;             // Tecla: R | r
    public static final int KEY_S               = KeyEvent.VK_S;             // Tecla: S | s
    public static final int KEY_T               = KeyEvent.VK_T;             // Tecla: T | t
    public static final int KEY_U               = KeyEvent.VK_U;             // Tecla: U | u
    public static final int KEY_V               = KeyEvent.VK_V;             // Tecla: V | v
    public static final int KEY_W               = KeyEvent.VK_W;             // Tecla: W | w
    public static final int KEY_X               = KeyEvent.VK_X;             // Tecla: X | x
    public static final int KEY_Y               = KeyEvent.VK_Y;             // Tecla: Y | y
    public static final int KEY_Z               = KeyEvent.VK_Z;             // Tecla: Z | z
    public static final int KEY_LEFT_BRACKET    = KeyEvent.VK_OPEN_BRACKET;  // Tecla: [
    public static final int KEY_RIGHT_BRACKET   = KeyEvent.VK_CLOSE_BRACKET; // Tecla: ]
    public static final int KEY_BACKSLASH       = KeyEvent.VK_BACK_SLASH;    // Tecla: '\'
    public static final int KEY_GRAVE           = KeyEvent.VK_BACK_QUOTE;    // Tecla: `

    // teclas de funções
    public static final int KEY_SPACE           = KeyEvent.VK_SPACE;         // Tecla: Space
    public static final int KEY_ESCAPE          = KeyEvent.VK_ESCAPE;        // Tecla: Esc
    public static final int KEY_ENTER           = KeyEvent.VK_ENTER;         // Tecla: Enter
    public static final int KEY_TAB             = KeyEvent.VK_TAB;           // Tecla: Tab
    public static final int KEY_BACKSPACE       = KeyEvent.VK_BACK_SPACE;    // Tecla: Backspace
    public static final int KEY_INSERT          = KeyEvent.VK_INSERT;        // Tecla: Ins
    public static final int KEY_DELETE          = KeyEvent.VK_DELETE;        // Tecla: Del
    public static final int KEY_RIGHT           = KeyEvent.VK_RIGHT;         // Tecla: Cursor right
    public static final int KEY_LEFT            = KeyEvent.VK_LEFT;          // Tecla: Cursor left
    public static final int KEY_DOWN            = KeyEvent.VK_DOWN;          // Tecla: Cursor down
    public static final int KEY_UP              = KeyEvent.VK_UP;            // Tecla: Cursor up
    public static final int KEY_PAGE_UP         = KeyEvent.VK_PAGE_UP;       // Tecla: Page up
    public static final int KEY_PAGE_DOWN       = KeyEvent.VK_PAGE_DOWN;     // Tecla: Page down
    public static final int KEY_HOME            = KeyEvent.VK_HOME;          // Tecla: Home
    public static final int KEY_END             = KeyEvent.VK_END;           // Tecla: End
    public static final int KEY_CAPS_LOCK       = KeyEvent.VK_CAPS_LOCK;     // Tecla: Caps lock
    public static final int KEY_SCROLL_LOCK     = KeyEvent.VK_SCROLL_LOCK;   // Tecla: Scroll down
    public static final int KEY_NUM_LOCK        = KeyEvent.VK_NUM_LOCK;      // Tecla: Num lock
    public static final int KEY_PRINT_SCREEN    = KeyEvent.VK_PRINTSCREEN;   // Tecla: Print screen
    public static final int KEY_PAUSE           = KeyEvent.VK_PAUSE;         // Tecla: Pause
    public static final int KEY_F1              = KeyEvent.VK_F1;            // Tecla: F1
    public static final int KEY_F2              = KeyEvent.VK_F2;            // Tecla: F2
    public static final int KEY_F3              = KeyEvent.VK_F3;            // Tecla: F3
    public static final int KEY_F4              = KeyEvent.VK_F4;            // Tecla: F4
    public static final int KEY_F5              = KeyEvent.VK_F5;            // Tecla: F5
    public static final int KEY_F6              = KeyEvent.VK_F6;            // Tecla: F6
    public static final int KEY_F7              = KeyEvent.VK_F7;            // Tecla: F7
    public static final int KEY_F8              = KeyEvent.VK_F8;            // Tecla: F8
    public static final int KEY_F9              = KeyEvent.VK_F9;            // Tecla: F9
    public static final int KEY_F10             = KeyEvent.VK_F10;           // Tecla: F10
    public static final int KEY_F11             = KeyEvent.VK_F11;           // Tecla: F11
    public static final int KEY_F12             = KeyEvent.VK_F12;           // Tecla: F12
    public static final int KEY_SHIFT           = KeyEvent.VK_SHIFT;         // Tecla: Shift left
    public static final int KEY_CONTROL         = KeyEvent.VK_CONTROL;       // Tecla: Control left
    public static final int KEY_ALT             = KeyEvent.VK_ALT;           // Tecla: Alt left
    public static final int KEY_SUPER           = KeyEvent.VK_WINDOWS;       // Tecla: Super left

    // teclas do teclado numérico
    public static final int KEY_KP_0            = KeyEvent.VK_NUMPAD0;       // Tecla: Keypad 0
    public static final int KEY_KP_1            = KeyEvent.VK_NUMPAD1;       // Tecla: Keypad 1
    public static final int KEY_KP_2            = KeyEvent.VK_NUMPAD2;       // Tecla: Keypad 2
    public static final int KEY_KP_3            = KeyEvent.VK_NUMPAD3;       // Tecla: Keypad 3
    public static final int KEY_KP_4            = KeyEvent.VK_NUMPAD4;       // Tecla: Keypad 4
    public static final int KEY_KP_5            = KeyEvent.VK_NUMPAD5;       // Tecla: Keypad 5
    public static final int KEY_KP_6            = KeyEvent.VK_NUMPAD6;       // Tecla: Keypad 6
    public static final int KEY_KP_7            = KeyEvent.VK_NUMPAD7;       // Tecla: Keypad 7
    public static final int KEY_KP_8            = KeyEvent.VK_NUMPAD8;       // Tecla: Keypad 8
    public static final int KEY_KP_9            = KeyEvent.VK_NUMPAD9;       // Tecla: Keypad 9
    public static final int KEY_KP_DIVIDE       = KeyEvent.VK_DIVIDE;        // Tecla: Keypad /
    public static final int KEY_KP_MULTIPLY     = KeyEvent.VK_MULTIPLY;      // Tecla: Keypad *
    public static final int KEY_KP_SUBTRACT     = KeyEvent.VK_SUBTRACT;      // Tecla: Keypad -
    public static final int KEY_KP_ADD          = KeyEvent.VK_ADD;           // Tecla: Keypad +

    // constantes para o mouse
    public static final int MOUSE_BUTTON_LEFT    = MouseEvent.BUTTON1;       // botão da esquerda do mouse
    public static final int MOUSE_BUTTON_RIGHT   = MouseEvent.BUTTON3;       // botão da direitra do mouse
    public static final int MOUSE_BUTTON_MIDDLE  = MouseEvent.BUTTON2;       // botão do meio do mouse (pressionado)

    // constantes para o cursor
    public static final int MOUSE_CURSOR_DEFAULT       = Cursor.DEFAULT_CURSOR;   // cursor padrão
    public static final int MOUSE_CURSOR_IBEAM         = Cursor.TEXT_CURSOR;      // cursor de texto
    public static final int MOUSE_CURSOR_CROSSHAIR     = Cursor.CROSSHAIR_CURSOR; // cursor em cruz
    public static final int MOUSE_CURSOR_POINTING_HAND = Cursor.HAND_CURSOR;      // cursor dedo apontando
    public static final int MOUSE_CURSOR_RESIZE_EW     = Cursor.E_RESIZE_CURSOR;  // cursor redimensionamento horizontal
    public static final int MOUSE_CURSOR_RESIZE_NS     = Cursor.N_RESIZE_CURSOR;  // cursor redimensionamento vertical
    public static final int MOUSE_CURSOR_RESIZE_NWSE   = Cursor.NW_RESIZE_CURSOR; // cursor redimensionamento diagonal cima-esquerda -> baixo-direita
    public static final int MOUSE_CURSOR_RESIZE_NESW   = Cursor.NE_RESIZE_CURSOR; // cursor redimensionamento diagonal baixo-esquerda -> cima-direita
    public static final int MOUSE_CURSOR_RESIZE_ALL    = Cursor.MOVE_CURSOR;      // cursor redimensionamento omnidirecional
    public static final int MOUSE_CURSOR_WAIT          = Cursor.WAIT_CURSOR;      // cursor aguarde
    
    // constantes para fontes
    public static final int FONT_PLAIN                 = Font.PLAIN;              // fonte sem estilo
    public static final int FONT_BOLD                  = Font.BOLD;               // fonte em negrito
    public static final int FONT_ITALIC                = Font.ITALIC;             // fonte em itálico
    
    // constantes para contornos
    public static final int STROKE_CAP_BUTT            = BasicStroke.CAP_BUTT;    // fim da linha sem decoração
    public static final int STROKE_CAP_ROUND           = BasicStroke.CAP_ROUND;   // fim da linha redondo
    public static final int STROKE_CAP_SQUARE          = BasicStroke.CAP_SQUARE;  // fim da linha quadrado
    public static final int STROKE_JOIN_BEVEL          = BasicStroke.JOIN_BEVEL;  // junção entre linhas chanfrada
    public static final int STROKE_JOIN_MITER          = BasicStroke.JOIN_MITER;  // junção entre linhas em esquadria
    public static final int STROKE_JOIN_ROUND          = BasicStroke.JOIN_ROUND;  // junção entre linhas arredondado
    


    /***************************************************************************
     * Cores padrão.
     **************************************************************************/
    public static final Color LIGHTGRAY  = new Color( 200, 200, 200 );
    public static final Color GRAY       = new Color( 130, 130, 130 );
    public static final Color DARKGRAY   = new Color( 80, 80, 80 );
    public static final Color YELLOW     = new Color( 253, 249, 0 );
    public static final Color GOLD       = new Color( 255, 203, 0 );
    public static final Color ORANGE     = new Color( 255, 161, 0 );
    public static final Color PINK       = new Color( 255, 109, 194 );
    public static final Color RED        = new Color( 230, 41, 55 );
    public static final Color MAROON     = new Color( 190, 33, 55 );
    public static final Color GREEN      = new Color( 0, 228, 48 );
    public static final Color LIME       = new Color( 0, 158, 47 );
    public static final Color DARKGREEN  = new Color( 0, 117, 44 );
    public static final Color SKYBLUE    = new Color( 102, 191, 255 );
    public static final Color BLUE       = new Color( 0, 121, 241 );
    public static final Color DARKBLUE   = new Color( 0, 82, 172 );
    public static final Color PURPLE     = new Color( 200, 122, 255 );
    public static final Color VIOLET     = new Color( 135, 60, 190 );
    public static final Color DARKPURPLE = new Color( 112, 31, 126 );
    public static final Color BEIGE      = new Color( 211, 176, 131 );
    public static final Color BROWN      = new Color( 127, 106, 79 );
    public static final Color DARKBROWN  = new Color( 76, 63, 47 );
    public static final Color WHITE      = new Color( 255, 255, 255 );
    public static final Color BLACK      = new Color( 0, 0, 0 );
    public static final Color BLANK      = new Color( 0, 0, 0, 0 );
    public static final Color MAGENTA    = new Color( 255, 0, 255 );
    public static final Color RAYWHITE   = new Color( 245, 245, 245 );

}
