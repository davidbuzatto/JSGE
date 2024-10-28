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
package br.com.davidbuzatto.jsge.core.engine;


import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.utils.TraceLogUtils;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
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
import java.util.logging.LogManager;
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
public abstract class EngineFrame extends JFrame {

    /**
     * Painel de desenho onde todas as operações de desenho e de registro
     * de eventos ocorretá.
     */
    private DrawingPanel drawingPanel;

    /**
     * Referência ao contexto gráfico do painel de desenho.
     */
    private Graphics2D g2d;

    /**
     * Fonte padrão.
     */
    private Font defaultFont;
    
    /**
     * Fonte padrão para o desenho do FPS.
     */
    private Font defaultFPSFont;

    /**
     * Contorno padrão.
     */
    private BasicStroke defaultStroke;

    /**
     * Tempo antes de iniciar os processos de atualização e desenho.
     */
    private long timeBefore;

    /**
     * Tempo depois de realizar os processos de atualização e desenho.
     */
    private long timeAfter;

    /**
     * Tempo a esperar antes de iniciar o próximo ciclo.
     */
    private long waitTime;

    /**
     * Tempo de um frame.
     */
    private long frameTime;

    /**
     * Quadros por segundo.
     */
    private int targetFPS;

    /**
     * Quadros por segundo atual.
     */
    private int currentFPS;

    /**
     * Tempo esperado baseado na quantidade de quadros por segundo.
     */
    private long waitTimeFPS;

    /**
     * Tempo de início da execução do jogo/simulação.
     */
    private long startTime;

    /**
     * Flag que indica o uso da suavização (antialiasing) para o contexto gráfico.
     */
    private boolean antialiasing;
    
    /**
     * Código da tecla de saída.
     */
    private int exitKeyCode;
    
    /**
     * Flag para controle de execução da thread de desenho
     */
    private boolean running;

    /**
     * Gerenciador de entradas.
     */
    private InputManager inputManager;

    // ações padrão para o mouse
    /** Ação para o botão esquerdo do mouse. */
    private GameAction mouseLeftAction;
    /** Ação para o botão esquerdo do mouse. */
    private GameAction mouseLeftActionInitial;
    
    /** Ação para o botão do meio do mouse. */
    private GameAction mouseMiddleAction;
    /** Ação para o botão do meio do mouse. */
    private GameAction mouseMiddleActionInitial;
    
    /** Ação para o botão direito do mouse. */
    private GameAction mouseRightAction;
    /** Ação para o botão direito do mouse. */
    private GameAction mouseRightActionInitial;
    
    /** Ação para o a rolagem para cima do mouse. */
    private GameAction mouseWheelUpAction;
    /** Ação para o a rolagem para baixo do mouse. */
    private GameAction mouseWheelDownAction;
    
    /** Indicação se o botão da esquerda do mouse foi processo do ciclo atual. */
    private boolean mouseButtonLeftProcessed;
    /** Indicação se o botão do meio do mouse foi processo do ciclo atual. */
    private boolean mouseButtonMiddleProcessed;
    /** Indicação se o botão da direita do mouse foi processo do ciclo atual. */
    private boolean mouseButtonRightProcessed;
    
    /** Indicação se o botão da esquerda do mouse foi pressionado. */
    private boolean mouseLeftPressed;
    /** Indicação se o botão da esquerda do mouse foi solto. */
    private boolean mouseLeftReleased;
    /** Indicação se o botão da esquerda do mouse está pressionado. */
    private boolean mouseLeftDown;
    /** Indicação se o botão da esquerda do mouse não está pressionado. */
    private boolean mouseLeftUp;
    
    /** Indicação se o botão do meio do mouse foi pressionado. */
    private boolean mouseMiddlePressed;
    /** Indicação se o botão do meio do mouse foi solto. */
    private boolean mouseMiddleReleased;
    /** Indicação se o botão do meio do mouse está pressionado. */
    private boolean mouseMiddleDown;
    /** Indicação se o botão do meio do mouse não está pressionado. */
    private boolean mouseMiddleUp;
    
    /** Indicação se o botão da direita do mouse foi pressionado. */
    private boolean mouseRightPressed;
    /** Indicação se o botão da direita do mouse foi solto. */
    private boolean mouseRightReleased;
    /** Indicação se o botão da direita do mouse está pressionado. */
    private boolean mouseRightDown;
    /** Indicação se o botão da direita do mouse não está pressionado. */
    private boolean mouseRightUp;
    
    // mapas para o teclado
    
    /** Mapa de processamento das teclas. */
    private Map<Integer, Boolean> keysProcessedMap;
    /** Mapa de processamento das teclas pressionadas. */
    private Map<Integer, Boolean> keysPressedMap;
    /** Mapa de processamento das teclas soltas. */
    private Map<Integer, Boolean> keysReleasedMap;
    /** Mapa de processamento das teclas que estão pressionadas. */
    private Map<Integer, Boolean> keysDownMap;
    /** Mapa de processamento das teclas não pressionadas. */
    private Map<Integer, Boolean> keysUpMap;
    
    /** Armazena o caractere da última tecla pressionada. */
    private int lastPressedChar = KEY_NULL;
    
    /** Armazena o código da última tecla pressionada. */
    private int lastPressedKeyCode = KEY_NULL;
    
    /** Qual o cursor do momento. */
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
    
    /** Flag que indica se a engine está em modo 2D (câmera). */
    private boolean mode2DActive = false;
    
    /** O contexto gráfico transformado do modo 2D. */
    private Graphics2D cameraGraphics;
    
    /** O contexto gráfico original do ciclo. */
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
    public EngineFrame( int windowWidth, 
                   int windowHeight, 
                   String windowTitle, 
                   int targetFPS, 
                   boolean antialiasing,
                   boolean resizable, 
                   boolean fullScreen, 
                   boolean undecorated, 
                   boolean alwaysOnTop ) {
        
        // desliga o sistema de logging do JDK usado pela biblioteca de processamento de som
        LogManager.getLogManager().reset();
        
        if ( windowWidth <= 0 ) {
            throw new IllegalArgumentException( "width must be positive!" );
        }

        if ( windowHeight <= 0 ) {
            throw new IllegalArgumentException( "height must be positive!" );
        }

        startTime = System.currentTimeMillis();
        setTargetFPS( targetFPS );

        defaultFont = new Font( Font.MONOSPACED, Font.BOLD, 10 );
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
        try {
            create();
        } catch ( RuntimeException exc ) {
            traceLogError( CoreUtils.stackTraceToString( exc ) );
        }

        // inicia o processo de execução do jogo ou simulação
        running = true;
        setVisible( true );
        start();

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
    public EngineFrame( int windowWidth, 
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
                
                try {
                    update();
                } catch ( RuntimeException exc ) {
                    traceLogError( CoreUtils.stackTraceToString( exc ) );
                }
                
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

                // quanto um frame demorou?
                frameTime = timeAfter - timeBefore;

                // quanto se deve esperar?
                waitTime = waitTimeFPS - frameTime;

                //traceLogInfo( "%d %d %d %d", timeBefore, timeAfter, frameTime, waitTime );

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
    // Tratamento do mouse.
    //**************************************************************************
    
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
    public Vector2 getMousePositionPoint() {
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
    public Vector2 getMouseWheelMoveVector() {
        double vUp = mouseWheelUpAction.getAmount();
        double vDown = mouseWheelDownAction.getAmount();
        return new Vector2( vUp, vDown );
    }



    //**************************************************************************
    // Tratamento do teclado.
    //**************************************************************************
    
    /**
     * Configura a tecla de saída. Por padrão é a tecla &lt;ESC&gt;
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
    public Set<Integer> getKeysPressed() {
        return inputManager.getKeysFromPressedActions();
    }
    
    /**
     * Retorna a o código da tecla pressionada.
     * 
     * @return O código da tecla pressionada ou KEY_NULL caso nenhuma tenha sido
     * pressionada.
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
     * Retorna o caractere da tecla pressionada.
     * 
     * @return O caractere da tecla pressionada ou KEY_NULL caso nenhuma tenha
     * sido pressionada.
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
    // Métodos de desenho
    //**************************************************************************

    /**
     * Desenha um pixel.
     * 
     * @param x Coordenada x do pixel.
     * @param y Coordenada y do pixel.
     * @param paint Paint para o desenho.
     */
    public void drawPixel( double x, double y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( x, y, x, y ) );
    }

    /**
     * Desenha um pixel.
     * 
     * @param point Ponto do pixel.
     * @param paint Paint para o desenho.
     */
    public void drawPixel( Vector2 point, Paint paint ) {
        drawPixel( point.x, point.y, paint );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startX Coordenada x do ponto inicial.
     * @param startY Coordenada y do ponto inicial.
     * @param endX Coordenada x do ponto final.
     * @param endY Coordenada y do ponto final.
     * @param paint Paint para o desenho.
     */
    public void drawLine( double startX, double startY, double endX, double endY, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( startX, startY, endX, endY ) );
    }

    /**
     * Desenha uma linha.
     * 
     * @param start Ponto inicial.
     * @param end Ponto final.
     * @param paint Paint para o desenho.
     */
    public void drawLine( Vector2 start, Vector2 end, Paint paint ) {
        drawLine( start.x, start.y, end.x, end.y, paint );
    }

    /**
     * Desenha uma linha.
     * 
     * @param line uma linha.
     * @param paint Paint para o desenho.
     */
    public void drawLine( Line line, Paint paint ) {
        drawLine( line.x1, line.y1, line.x2, line.y2, paint );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( double x, double y, double width, double height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, paint );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param rectangle Um retângulo.
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( Rectangle rectangle, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( double x, double y, double width, double height, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, paint );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param rectangle Um retângulo.
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( Rectangle rectangle, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param originX Coordenada x do pivô da rotação.
     * @param originY Coordenada y do pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setPaint( paint );

        gc.rotate( Math.toRadians( rotation ), originX, originY );
        gc.draw( new Rectangle2D.Double( x, y, width, height ) );

        gc.dispose();

    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param origin pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param rectangle Um retângulo.
     * @param origin pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param originX Coordenada x do pivô da rotação.
     * @param originY Coordenada y do pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setPaint( paint );

        gc.rotate( Math.toRadians( rotation ), originX, originY );
        gc.fill( new Rectangle2D.Double( x, y, width, height ) );

        gc.dispose();

    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param origin pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param rectangle Um retângulo.
     * @param origin pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void drawRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos Vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void drawRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param roundRectangle Um retângulo com os cantos arredondados.
     * @param paint Paint para o desenho.
     */
    public void drawRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        drawRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void fillRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos Vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void fillRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param roundRectangle Um retângulo com os cantos arredondados.
     * @param paint Paint para o desenho.
     */
    public void fillRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        fillRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Desenha um círculo.
     * 
     * @param x Coordenada x do centro do círculo.
     * @param y Coordenada y do centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void drawCircle( double x, double y, double radius, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Desenha um círculo.
     * 
     * @param center Centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void drawCircle( Vector2 center, double radius, Paint paint ) {
        drawCircle( center.x, center.y, radius, paint );
    }

    /**
     * Desenha um círculo.
     * 
     * @param circle Um círculo.
     * @param paint Paint para o desenho.
     */
    public void drawCircle( Circle circle, Paint paint ) {
        drawCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Pinta um círculo.
     * 
     * @param x Coordenada x do centro do círculo.
     * @param y Coordenada y do centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void fillCircle( double x, double y, double radius, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Pinta um círculo.
     * 
     * @param center Centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void fillCircle( Vector2 center, double radius, Paint paint ) {
        fillCircle( center.x, center.y, radius, paint );
    }

    /**
     * Pinta um círculo.
     * 
     * @param circle Um círculo.
     * @param paint Paint para o desenho.
     */
    public void fillCircle( Circle circle, Paint paint ) {
        fillCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param x Coordenada x do centro da elipse.
     * @param y Coordenada y do centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void drawEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center Centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void drawEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param paint Paint para o desenho.
     */
    public void drawEllipse( Ellipse ellipse, Paint paint ) {
        drawEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param x Coordenada x do centro da elipse.
     * @param y Coordenada y do centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void fillEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center Centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void fillEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param paint Paint para o desenho.
     */
    public void fillEllipse( Ellipse ellipse, Paint paint ) {
        fillEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center Centro do setor circular.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circle Um círculo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circleSector Um setor circular.
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( CircleSector circleSector, Paint paint ) {
        drawCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center Centro do setor circular.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circle Um círculo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circleSector Um setor circular.
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( CircleSector circleSector, Paint paint ) {
        fillCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center Centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipseSector Um setor de uma elipse.
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        drawEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center Centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipseSector Um setor de uma elipse.
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        fillEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Desenha um arco.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.OPEN ) );
    }

    /**
     * Desenha um arco.
     * 
     * @param center Centro do arco.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Desenha um arco
     * 
     * @param arc Um arco.
     * @param paint Paint para o desenho.
     */
    public void drawArc( Arc arc, Paint paint ) {
        drawArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Pinta um arco.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.CHORD ) );
    }

    /**
     * Pinta um arco.
     * 
     * @param center Centro do arco.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Pinta um arco
     * 
     * @param arc Um arco.
     * @param paint Paint para o desenho.
     */
    public void fillArc( Arc arc, Paint paint ) {
        fillArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Desenha um anel.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
    }

    /**
     * Desenha um anel.
     * 
     * @param center Centro do anel.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Desenha um anel.
     * 
     * @param ring Um anel.
     * @param paint Paint para o desenho.
     */
    public void drawRing( Ring ring, Paint paint ) {
        drawRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Pinta um anel.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
    }

    /**
     * Pinta um anel.
     * 
     * @param center Centro do anel.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Pinta um anel.
     * 
     * @param ring Um anel.
     * @param paint Paint para o desenho.
     */
    public void fillRing( Ring ring, Paint paint ) {
        fillRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Desenha um triângulo. Forneça os vértices no sentido horário.
     * 
     * @param v1x Coordenada x do primeiro vértice.
     * @param v1y Coordenada y do primeiro vértice.
     * @param v2x Coordenada x do segundo vértice.
     * @param v2y Coordenada y do segundo vértice.
     * @param v3x Coordenada x do terceiro vértice.
     * @param v3y Coordenada y do terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void drawTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
    }

    /**
     * Desenha um triângulo. Forneça os vértices no sentido horário.
     * 
     * @param v1 Primeiro vértice.
     * @param v2 Segundo vértice.
     * @param v3 Terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void drawTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param triangle Um triângulo.
     * @param paint Paint para o desenho.
     */
    public void drawTriangle( Triangle triangle, Paint paint ) {
        drawTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Pinta um triângulo. Forneça os vértices no sentido horário.
     * 
     * @param v1x Coordenada x do primeiro vértice.
     * @param v1y Coordenada y do primeiro vértice.
     * @param v2x Coordenada x do segundo vértice.
     * @param v2y Coordenada y do segundo vértice.
     * @param v3x Coordenada x do terceiro vértice.
     * @param v3y Coordenada y do terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void fillTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
    }

    /**
     * Pinta um triângulo. Forneça os vértices no sentido horário.
     * 
     * @param v1 Primeiro vértice.
     * @param v2 Segundo vértice.
     * @param v3 Terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void fillTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param triangle Um triângulo.
     * @param paint Paint para o desenho.
     */
    public void fillTriangle( Triangle triangle, Paint paint ) {
        fillTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param x Coordenada x do centro do polígono.
     * @param y Coordenada y do centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawPolygon( double x, double y, double sides, double radius, double rotation, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center Centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawPolygon( Vector2 center, double sides, double radius, double rotation, Paint paint ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param polygon Um polígono regular.
     * @param paint Paint para o desenho.
     */
    public void drawPolygon( Polygon polygon, Paint paint ) {
        drawPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param x Coordenada x do centro do polígono.
     * @param y Coordenada y do centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillPolygon( double x, double y, double sides, double radius, double rotation, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center Centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillPolygon( Vector2 center, double sides, double radius, double rotation, Paint paint ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param polygon Um polígono regular.
     * @param paint Paint para o desenho.
     */
    public void fillPolygon( Polygon polygon, Paint paint ) {
        fillPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Desenha um caminho.
     * 
     * @param path Caminho a ser desenhado.
     * @param paint Paint para o desenho.
     */
    public void drawPath( Path path, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( path.path );
    }

    /**
     * Ponta um caminho.
     * 
     * @param path Caminho a ser desenhado.
     * @param paint Paint para o desenho.
     */
    public void fillPath( Path path, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( path.path );
    }

    
    
    //**************************************************************************
    // Métodos de desenhos de curvas.
    //**************************************************************************

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param cx Coordenada x do ponto de controle.
     * @param cy Coordenada y do ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 Ponto inicial.
     * @param c Ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }
    
    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param points Pontos da curva. No mínimo 3. Cada dois pontos subsequentes
     * representam um novo ponto de controle e um novo ponto âncora.
     * @param paint Paint para o desenhho.
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
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve Uma curva Bézier quadrática.
     * @param paint Paint para o desenhho.
     */
    public void drawQuadCurve( QuadCurve quadCurve, Paint paint ) {
        drawQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param cx Coordenada x do ponto de controle.
     * @param cy Coordenada y do ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 Ponto inicial.
     * @param c Ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }
    
    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param points Pontos da curva. No mínimo 3. Cada dois pontos subsequentes
     * representam um novo ponto de controle e um novo ponto âncora.
     * @param paint Paint para o desenhho.
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
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve Uma curva Bézier quadrática.
     * @param paint Paint para o desenhho.
     */
    public void fillQuadCurve( QuadCurve quadCurve, Paint paint ) {
        fillQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param c1x Coordenada x do primeiro ponto de controle.
     * @param c1y Coordenada y do primeiro ponto de controle.
     * @param c2x Coordenada x do segundo ponto de controle.
     * @param c2y Coordenada y do segundo ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.draw( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 Ponto inicial.
     * @param c1 Primeiro ponto de controle.
     * @param c2 Segundo ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }
    
    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param points Pontos da curva. No mínimo 4. Cada três pontos subsequentes
     * representam dois novos pontos de controle e um novo ponto âncora.
     * @param paint Paint para o desenhho.
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
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve Uma curva Bézier cúbica.
     * @param paint Paint para o desenhho.
     */
    public void drawCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        drawCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param c1x Coordenada x do primeiro ponto de controle.
     * @param c1y Coordenada y do primeiro ponto de controle.
     * @param c2x Coordenada x do segundo ponto de controle.
     * @param c2y Coordenada y do segundo ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        g2d.setPaint( paint );
        g2d.fill( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 Ponto inicial.
     * @param c1 Primeiro ponto de controle.
     * @param c2 Segundo ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param points Pontos da curva. No mínimo 4. Cada três pontos subsequentes
     * representam dois novos pontos de controle e um novo ponto âncora.
     * @param paint Paint para o desenhho.
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
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve Uma curva Bézier cúbica.
     * @param paint Paint para o desenhho.
     */
    public void fillCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        fillCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }
    

    
    //**************************************************************************
    // Métodos de desenho de texto.
    //**************************************************************************

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, Paint paint ) {
        g2d.setPaint( paint );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, double rotation, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, paint );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param originX Coordenada x do pivô de rotação.
     * @param originY Coordenada y do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
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
     * Desenha um texto.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
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
     * Desenha um texto rotacionado.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, double rotation, int fontSize, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, fontSize, paint );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param originX Coordenada x do pivô de rotação.
     * @param originY Coordenada y do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
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
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, Paint paint ) {
        drawText( text, point.x, point.y, paint );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, paint );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, paint );
    }

    /**
     * Desenha um texto.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, fontSize, paint );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, paint );
    }

    /**
     * Desenha um texto.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param origin ponto do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, paint );
    }
    
    /**
     * Mede a largura de um texto.
     * 
     * @param text O texto a ser medido.
     * @return A largura de um texto.
     */
    public int measureText( String text ) {
        return g2d.getFontMetrics().stringWidth( text );
    }

    /**
     * Mede a largura de um texto.
     * 
     * @param text O texto a ser medido.
     * @param fontSize Tamanho da fonte.
     * @return A largura de um texto.
     */
    public int measureText( String text, int fontSize ) {
        Font f = g2d.getFont();
        g2d.setFont( f.deriveFont( (float) fontSize ) );
        int width = g2d.getFontMetrics().stringWidth( text );
        g2d.setFont( f );
        return width;
    }
    
    /**
     * Mede os limites do texto.
     * 
     * @param text O texto a ser medido.
     * @return Um retângulo que limita o texto.
     */
    public Rectangle measureTextBounds( String text ) {
        Rectangle2D r2d = g2d.getFontMetrics().getStringBounds( text, g2d );
        return new Rectangle( 0, 0, r2d.getWidth(), r2d.getHeight() );
    }

    /**
     * Mede a largura de um texto.
     * 
     * @param text O texto a ser medido.
     * @param fontSize Tamanho da fonte.
     * @return A largura de um texto.
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
    // Métodos utilitários variados.
    //**************************************************************************

    /**
     * Limpa o fundo da tela de desenho.
     * 
     * @param paint Cor a ser usada.
     */
    public void clearBackground( Paint paint ) {
        fillRectangle( 0, 0, getScreenWidth(), getScreenHeight(), paint );
    }

    
    
    //**************************************************************************
    // Métodos para obtenção e/ou configuração de opções e/ou dados
    // relativos à execução.
    //**************************************************************************
    
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
     * @return O tempo que um frame demorou para ser atualizado e desenhado.
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
     * @return A quantidade de quadros por segundo atual.
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
    
    
    
    //**************************************************************************
    // Métodos de logging.
    //**************************************************************************
    
    /**
     * Emite uma mensagem de log no stream de saída de erro.
     * 
     * @param logLevel O nível do log.
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLog( int logLevel, String text, Object... args ) {
        TraceLogUtils.traceLog( logLevel, text, args );
    }
    
    /**
     * Emite uma mensagem de log no nível INFO no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogInfo( String text, Object... args ) {
        TraceLogUtils.traceLogInfo( text, args );
    }
    
    /**
     * Emite uma mensagem de log no nível WARNING no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogWarning( String text, Object... args ) {
        TraceLogUtils.traceLogWarning( text, args );
    }
    
    /**
     * Emite uma mensagem de log no nível ERROR no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogError( String text, Object... args ) {
        TraceLogUtils.traceLogError( text, args );
    }
    
    /**
     * Emite uma mensagem de log no nível FATAL no stream de saída de erro.
     * 
     * @param text O texto a ser emitido.
     * @param args Os argumentos para a formatação do texto.
     */
    public static void traceLogFatal( String text, Object... args ) {
        TraceLogUtils.traceLogFatal( text, args );
    }
    
    /**
     * Configura o nível de log do sistema de loggin da engine.
     * 
     * @param logLevel O nível de log.
     */
    public static void setTraceLogLevel( int logLevel ) {
        TraceLogUtils.setTraceLogLevel( logLevel );
    }
    
    
    
    //**************************************************************************
    // Métodos para configuração da fonte e do contorno.
    //**************************************************************************

    /**
     * Altera a fonte padrão do contexto gráfico.
     * 
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     * @param font Fonte a ser usada.
     */
    public void setDefaultFont( Font font ) {
        this.defaultFont = font;
    }

    /**
     * Altera o nome da fonte padrão do contexto gráfico.
     * 
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     * @param name Nome da fonte padrão.
     */
    public void setDefaultFontName( String name ) {
        defaultFont = new Font( defaultFont.getName(), defaultFont.getStyle(), defaultFont.getSize() );
    }

    /**
     * Altera o estilo da fonte padrão do contexto gráfico.
     * 
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     * @param style O estilo da fonte padrão.
     */
    public void setDefaultFontStyle( int style ) {
        defaultFont = defaultFont.deriveFont( style );
    }

    /**
     * Altera o tamanho da fonte padrão do contexto gráfico.
     * 
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     * @param size O tamanho da fonte padrão.
     */
    public void setDefaultFontSize( int size ) {
        defaultFont = defaultFont.deriveFont( (float) size );
    }

    /**
     * Altera o nome da fonte corrente do contexto gráfico.
     * 
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     * @param name Nome da fonte.
     */
    public void setFontName( String name ) {
        g2d.setFont( new Font( name, g2d.getFont().getStyle(), g2d.getFont().getSize() ) );
    }

    /**
     * Altera o estilo da fonte corrente do contexto gráfico.
     *
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     * @param style O estilo da fonte corrente.
     */
    public void setFontStyle( int style ) {
        g2d.setFont( g2d.getFont().deriveFont( style ) );
    }

    /**
     * Altera o tamanho da fonte corrente do contexto gráfico.
     * 
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     * @param size O tamanho da fonte corrente.
     */
    public void setFontSize( int size ) {
        g2d.setFont( g2d.getFont().deriveFont( (float) size ) );
    }

    /**
     * Altera o contorno padrão do contexto gráfico.
     * 
     * @param stroke Contorno a ser usado.
     */
    public void setDefaultStroke( BasicStroke stroke ) {
        this.defaultStroke = stroke;
    }

    /**
     * Altera a largura da linha do contorno padrão do contexto gráfico.
     * 
     * @param width A largura da linha do contorno padrão.
     */
    public void setDefaultStrokeWidth( float width ) {
        defaultStroke = new BasicStroke( width, defaultStroke.getEndCap(), defaultStroke.getLineJoin() );
    }

    /**
     * Altera o modelo desenho do fim das linhas do contorno padrão do contexto
     * gráfico.
     * 
     * @param endCap O novo modelo de desenho.
     */
    public void setDefaultStrokeEndCap( int endCap ) {
        defaultStroke = new BasicStroke( defaultStroke.getLineWidth(), endCap, defaultStroke.getLineJoin() );
    }

    /**
     * Altera o modelo de junção de linhas do contorno padrão do contexto
     * gráfico.
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public void setDefaultStrokeLineJoin( int lineJoin ) {
        defaultStroke = new BasicStroke( defaultStroke.getLineWidth(), defaultStroke.getEndCap(), lineJoin );
    }

    /**
     * Altera a largura da linha do contorno corrente do contexto gráfico.
     * 
     * @param width A largura da linha do contorno padrão.
     */
    public void setStrokeWidth( float width ) {
        g2d.setStroke( new BasicStroke( width, defaultStroke.getEndCap(), defaultStroke.getLineJoin() ) );
    }

    /**
     * Altera o modelo de desenho do fim das linhas do contorno corrente do contexto
     * gráfico.
     * 
     * @param endCap O novo modelo de desenho.
     */
    public void setStrokeEndCap( int endCap ) {
        g2d.setStroke( new BasicStroke( defaultStroke.getLineWidth(), endCap, defaultStroke.getLineJoin() ) );
    }

    /**
     * Altera o modelo de junção de linhas do contorno corrente do contexto
     * gráfico.
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public void setStrokeLineJoin( int lineJoin ) {
        g2d.setStroke( new BasicStroke( defaultStroke.getLineWidth(), defaultStroke.getEndCap(), lineJoin ) );
    }

    
    
    //**************************************************************************
    // Métodos para carga e desenho de imagens.
    //**************************************************************************
    
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
            traceLogError(CoreUtils.stackTraceToString( exc ) );
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
            traceLogError(CoreUtils.stackTraceToString( exc ) );
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
            traceLogError(CoreUtils.stackTraceToString( exc ) );
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
    
    
    
    //**************************************************************************
    // Métodos para gerenciamento do cursor do mouse.
    //**************************************************************************
    
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
    
    
    
    //**************************************************************************
    // Métodos para controle da câmera
    //**************************************************************************
    
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
    
    
    
    //**************************************************************************
    // Métodos para controle de sons e músicas
    //**************************************************************************
    
    /**
     * Carrega um som de um arquivo.
     * 
     * @param filePath Caminho do arquivo.
     * @return Um novo som.
     */
    public static Sound loadSound( String filePath ) {
        return new Sound( filePath );
    }
    
    /**
     * Carrega um som de um input stream.
     * 
     * @param is Input stream.
     * @return Um novo som.
     */
    public static Sound loadSound( InputStream is ) {
        return new Sound( is );
    }
    
    /**
     * Carrega um som de uma URL.
     * 
     * @param url URL.
     * @return Um novo som.
     */
    public static Sound loadSound( URL url ) {
        return new Sound( url );
    }
    
    /**
     * Executa o som.
     * 
     * @param sound O som.
     */
    public static void playSound( Sound sound ) {
        sound.play();
    }
    
    /**
     * Carrega uma música de um arquivo.
     * 
     * @param filePath Caminho do arquivo.
     * @return Uma nova música.
     */
    public static Music loadMusic( String filePath ) {
        return new Music( filePath );
    }
    
    /**
     * Carrega uma música de um input stream.
     * 
     * @param is Input stream.
     * @return Uma nova música.
     */
    public static Music loadMusic( InputStream is ) {
        return new Music( is );
    }
    
    /**
     * Carrega uma música de uma URL.
     * 
     * @param url URL.
     * @return Uma nova música.
     */
    public static Music loadMusic( URL url ) {
        return new Music( url );
    }
    
    /**
     * Descarrega uma música.
     * 
     * @param music A música a ser descarregado.
     */
    public static void unloadMusic( Music music ) {
        music.unload();
    }
    
    /**
     * Executa a música.
     * 
     * @param music A música.
     */
    public static void playMusic( Music music ) {
        music.play();
    }
    
    /**
     * Para de executar a música.
     * 
     * @param music A música.
     */
    public static void stopMusic( Music music ) {
        music.stop();
    }
    
    /**
     * Pausa a música.
     * 
     * @param music A música.
     */
    public void pauseMusic( Music music ) {
        music.pause();
    }
    
    /**
     * Retoma a execução da música.
     * 
     * @param music A música.
     */
    public void resumeMusic( Music music ) {
        music.resume();
    }
    
    /**
     * Verifica se a música está executando.
     * 
     * @param music A música.
     * @return Verdadeiro caso a música esteja em execução, falso caso contrário.
     */
    public boolean isMusicPlaying( Music music ) {
        return music.isPlaying();
    }
    
    /**
     * Verifica se a música está parada.
     * 
     * @param music A música.
     * @return Verdadeiro caso a música esteja parada, falso caso contrário.
     */
    public boolean isMusicStopped( Music music ) {
        return music.isStopped();
    }
    
    /**
     * Verifica se a música está pausada.
     * 
     * @param music A música.
     * @return Verdadeiro caso a música esteja pausada, falso caso contrário.
     */
    public boolean isMusicPaused( Music music ) {
        return music.isPaused();
    }
    
    /**
     * Verifica se a música está sendo procurada.
     * 
     * @param music A música.
     * @return Verdadeiro caso a música esteja sendo procurada, falso caso contrário.
     */
    public boolean isMusicSeeking( Music music ) {
        return music.isSeeking();
    }
    
    /**
     * Configura o volume da música.
     * 
     * @param music A música.
     * @param volume O volume da música, variando de 0.0 a 1.0.
     */
    public void setMusicVolume( Music music, double volume ) {
        music.setVolume( volume );
    }
    
    /**
     * Procura uma posição da música.
     * 
     * @param music A música.
     * @param position Posição em segundos do momento desejado.
     */
    public static void seekMusic( Music music, int position ) {
        music.seek( position );
    }
    
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
    
    
    
    //**************************************************************************
    // Classes internas privadas.
    //**************************************************************************
    
    /**
     * Classe interna que encapsula o processo de desenho.
     */
    private class DrawingPanel extends JPanel {

        public DrawingPanel() {
            setBackground( null );
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
     * Classe interna para gerenciamento da entrada de teclas e mouse.
     * Os eventos são mapeados para GameActions.
     *
     * @author Prof. Dr. David Buzatto
     */
    private class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
        
        /*
         * Códigos do mouse (apenas para diferenciar as operações de rolagem
         * da roda do mouse).
         */
        
        /**
         * Constante que indica operação da roda de rolagem do mouse para cima.
         */
        public static final int MOUSE_WHEEL_UP = 3000;
        
        /**
         * Constante que indica operação da roda de rolagem do mouse para baixo.
         */
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

    
    
    //**************************************************************************
    // Constantes para teclas.
    //**************************************************************************
    
    /** Tecla: NULL, usada para indicar que nenhuma tecla foi pressionada */
    public static final int KEY_NULL            = 0;

    // alfa-numéricos
    
    /** Tecla: ' */
    public static final int KEY_APOSTROPHE      = KeyEvent.VK_QUOTE;
    
    /** Tecla: , */
    public static final int KEY_COMMA           = KeyEvent.VK_COMMA;
    
    /** Tecla: - */
    public static final int KEY_MINUS           = KeyEvent.VK_MINUS;
    
    /** Tecla: . */
    public static final int KEY_PERIOD          = KeyEvent.VK_PERIOD;
    
    /** Tecla: / */
    public static final int KEY_SLASH           = KeyEvent.VK_SLASH;
    
    /** Tecla: 0 */
    public static final int KEY_ZERO            = KeyEvent.VK_0;
    
    /** Tecla: 1 */
    public static final int KEY_ONE             = KeyEvent.VK_1;
    
    /** Tecla: 2 */
    public static final int KEY_TWO             = KeyEvent.VK_2;
    
    /** Tecla: 3 */
    public static final int KEY_THREE           = KeyEvent.VK_3;
    
    /** Tecla: 4 */
    public static final int KEY_FOUR            = KeyEvent.VK_4;
    
    /** Tecla: 5 */
    public static final int KEY_FIVE            = KeyEvent.VK_5;
    
    /** Tecla: 6 */
    public static final int KEY_SIX             = KeyEvent.VK_6;
    
    /** Tecla: 7 */
    public static final int KEY_SEVEN           = KeyEvent.VK_7;
    
    /** Tecla: 8 */
    public static final int KEY_EIGHT           = KeyEvent.VK_8;
    
    /** Tecla: 9 */
    public static final int KEY_NINE            = KeyEvent.VK_9;
    
    /** Tecla: ; */
    public static final int KEY_SEMICOLON       = KeyEvent.VK_SEMICOLON;
    
    /** Tecla: = */
    public static final int KEY_EQUAL           = KeyEvent.VK_EQUALS;
    
    /** Tecla: A */
    public static final int KEY_A               = KeyEvent.VK_A;
    
    /** Tecla: B */
    public static final int KEY_B               = KeyEvent.VK_B;
    
    /** Tecla: C */
    public static final int KEY_C               = KeyEvent.VK_C;
    
    /** Tecla: D */
    public static final int KEY_D               = KeyEvent.VK_D;
    
    /** Tecla: E */
    public static final int KEY_E               = KeyEvent.VK_E;
    
    /** Tecla: F */
    public static final int KEY_F               = KeyEvent.VK_F;
    
    /** Tecla: G */
    public static final int KEY_G               = KeyEvent.VK_G;
    
    /** Tecla: H */
    public static final int KEY_H               = KeyEvent.VK_H;
    
    /** Tecla: I */
    public static final int KEY_I               = KeyEvent.VK_I;
    
    /** Tecla: J */
    public static final int KEY_J               = KeyEvent.VK_J;
    
    /** Tecla: K */
    public static final int KEY_K               = KeyEvent.VK_K;
    
    /** Tecla: L */
    public static final int KEY_L               = KeyEvent.VK_L;
    
    /** Tecla: M */
    public static final int KEY_M               = KeyEvent.VK_M;
    
    /** Tecla: N */
    public static final int KEY_N               = KeyEvent.VK_N;
    
    /** Tecla: O */
    public static final int KEY_O               = KeyEvent.VK_O;
    
    /** Tecla: P */
    public static final int KEY_P               = KeyEvent.VK_P;
    
    /** Tecla: Q */
    public static final int KEY_Q               = KeyEvent.VK_Q;
    
    /** Tecla: R */
    public static final int KEY_R               = KeyEvent.VK_R;
    
    /** Tecla: S */
    public static final int KEY_S               = KeyEvent.VK_S;
    
    /** Tecla: T */
    public static final int KEY_T               = KeyEvent.VK_T;
    
    /** Tecla: U */
    public static final int KEY_U               = KeyEvent.VK_U;
    
    /** Tecla: V */
    public static final int KEY_V               = KeyEvent.VK_V;
    
    /** Tecla: W */
    public static final int KEY_W               = KeyEvent.VK_W;
    
    /** Tecla: X */
    public static final int KEY_X               = KeyEvent.VK_X;
    
    /** Tecla: Y */
    public static final int KEY_Y               = KeyEvent.VK_Y;
    
    /** Tecla: Z */
    public static final int KEY_Z               = KeyEvent.VK_Z;
    
    /** Tecla: [ */
    public static final int KEY_LEFT_BRACKET    = KeyEvent.VK_OPEN_BRACKET;
    
    /** Tecla: ] */
    public static final int KEY_RIGHT_BRACKET   = KeyEvent.VK_CLOSE_BRACKET;
    
    /** Tecla: \ */
    public static final int KEY_BACKSLASH       = KeyEvent.VK_BACK_SLASH;
    
    /** Tecla: ` */
    public static final int KEY_GRAVE           = KeyEvent.VK_BACK_QUOTE;

    /** Tecla: &lt;ESPAÇO&gt; */
    public static final int KEY_SPACE           = KeyEvent.VK_SPACE;
    
    /** Tecla: &lt;ESC&gt; */
    public static final int KEY_ESCAPE          = KeyEvent.VK_ESCAPE;
    
    /** Tecla: &lt;ENTER&gt; */
    public static final int KEY_ENTER           = KeyEvent.VK_ENTER;
    
    /** Tecla: &lt;TAB&gt; */
    public static final int KEY_TAB             = KeyEvent.VK_TAB;
    
    /** Tecla: &lt;BACKSPACE&gt; */
    public static final int KEY_BACKSPACE       = KeyEvent.VK_BACK_SPACE;
    
    /** Tecla: &lt;INSERT&gt; */
    public static final int KEY_INSERT          = KeyEvent.VK_INSERT;
    
    /** Tecla: &lt;DELETE&gt; */
    public static final int KEY_DELETE          = KeyEvent.VK_DELETE;
    
    /** Tecla: &lt;ARROW RIGHT&gt; */
    public static final int KEY_RIGHT           = KeyEvent.VK_RIGHT;
    
    /** Tecla: &lt;ARROW LEFT&gt; */
    public static final int KEY_LEFT            = KeyEvent.VK_LEFT;
    
    /** Tecla: &lt;ARROW DOWN&gt; */
    public static final int KEY_DOWN            = KeyEvent.VK_DOWN;
    
    /** Tecla: &lt;ARROW UP&gt; */
    public static final int KEY_UP              = KeyEvent.VK_UP;
    
    /** Tecla: &lt;PAGE UP&gt; */
    public static final int KEY_PAGE_UP         = KeyEvent.VK_PAGE_UP;
    
    /** Tecla: &lt;PAGE DOWN&gt; */
    public static final int KEY_PAGE_DOWN       = KeyEvent.VK_PAGE_DOWN;
    
    /** Tecla: &lt;HOME&gt; */
    public static final int KEY_HOME            = KeyEvent.VK_HOME;
    
    /** Tecla: &lt;END&gt; */
    public static final int KEY_END             = KeyEvent.VK_END;
    
    /** Tecla: &lt;CAPS LOCK&gt; */
    public static final int KEY_CAPS_LOCK       = KeyEvent.VK_CAPS_LOCK;
    
    /** Tecla: &lt;SCROLL LOCK&gt; */
    public static final int KEY_SCROLL_LOCK     = KeyEvent.VK_SCROLL_LOCK;
    
    /** Tecla: &lt;NUM LOCK&gt; */
    public static final int KEY_NUM_LOCK        = KeyEvent.VK_NUM_LOCK;
    
    /** Tecla: &lt;PRINT SCREEN&gt; */
    public static final int KEY_PRINT_SCREEN    = KeyEvent.VK_PRINTSCREEN;
    
    /** Tecla: &lt;PAUSE&gt; */
    public static final int KEY_PAUSE           = KeyEvent.VK_PAUSE;
    
    /** Tecla: &lt;F1&gt; */
    public static final int KEY_F1              = KeyEvent.VK_F1;
    
    /** Tecla: &lt;F2&gt; */
    public static final int KEY_F2              = KeyEvent.VK_F2;
    
    /** Tecla: &lt;F3&gt; */
    public static final int KEY_F3              = KeyEvent.VK_F3;
    
    /** Tecla: &lt;F4&gt; */
    public static final int KEY_F4              = KeyEvent.VK_F4;
    
    /** Tecla: &lt;F5&gt; */
    public static final int KEY_F5              = KeyEvent.VK_F5;
    
    /** Tecla: &lt;F6&gt; */
    public static final int KEY_F6              = KeyEvent.VK_F6;
    
    /** Tecla: &lt;F7&gt; */
    public static final int KEY_F7              = KeyEvent.VK_F7;
    
    /** Tecla: &lt;F8&gt; */
    public static final int KEY_F8              = KeyEvent.VK_F8;
    
    /** Tecla: &lt;F9&gt; */
    public static final int KEY_F9              = KeyEvent.VK_F9;
    
    /** Tecla: &lt;F10&gt; */
    public static final int KEY_F10             = KeyEvent.VK_F10;
    
    /** Tecla: &lt;F11&gt; */
    public static final int KEY_F11             = KeyEvent.VK_F11;
    
    /** Tecla: &lt;F12&gt; */
    public static final int KEY_F12             = KeyEvent.VK_F12;
    
    /** Tecla: &lt;SHIFT&gt; */
    public static final int KEY_SHIFT           = KeyEvent.VK_SHIFT;
    
    /** Tecla: &lt;CONTROL&gt; */
    public static final int KEY_CONTROL         = KeyEvent.VK_CONTROL;
    
    /** Tecla: &lt;ALT&gt; */
    public static final int KEY_ALT             = KeyEvent.VK_ALT;
    
    /** Tecla: &lt;WINDOWS/SUPER&gt; */
    public static final int KEY_SUPER           = KeyEvent.VK_WINDOWS;
    
    /** Tecla: &lt;NUMPAD 0&gt; */
    public static final int KEY_KP_0            = KeyEvent.VK_NUMPAD0;
    
    /** Tecla: &lt;NUMPAD 1&gt; */
    public static final int KEY_KP_1            = KeyEvent.VK_NUMPAD1;
    
    /** Tecla: &lt;NUMPAD 2&gt; */
    public static final int KEY_KP_2            = KeyEvent.VK_NUMPAD2;
    
    /** Tecla: &lt;NUMPAD 3&gt; */
    public static final int KEY_KP_3            = KeyEvent.VK_NUMPAD3;
    
    /** Tecla: &lt;NUMPAD 4&gt; */
    public static final int KEY_KP_4            = KeyEvent.VK_NUMPAD4;
    
    /** Tecla: &lt;NUMPAD 5&gt; */
    public static final int KEY_KP_5            = KeyEvent.VK_NUMPAD5;
    
    /** Tecla: &lt;NUMPAD 6&gt; */
    public static final int KEY_KP_6            = KeyEvent.VK_NUMPAD6;
    
    /** Tecla: &lt;NUMPAD 7&gt; */
    public static final int KEY_KP_7            = KeyEvent.VK_NUMPAD7;
    
    /** Tecla: &lt;NUMPAD 8&gt; */
    public static final int KEY_KP_8            = KeyEvent.VK_NUMPAD8;
    
    /** Tecla: &lt;NUMPAD 9&gt; */
    public static final int KEY_KP_9            = KeyEvent.VK_NUMPAD9;
    
    /** Tecla: &lt;NUMPAD /&gt; */
    public static final int KEY_KP_DIVIDE       = KeyEvent.VK_DIVIDE;
    
    /** Tecla: &lt;NUMPAD *&gt; */
    public static final int KEY_KP_MULTIPLY     = KeyEvent.VK_MULTIPLY;
    
    /** Tecla: &lt;NUMPAD -&gt; */
    public static final int KEY_KP_SUBTRACT     = KeyEvent.VK_SUBTRACT;
    
    /** Tecla: &lt;NUMPAD +&gt; */
    public static final int KEY_KP_ADD          = KeyEvent.VK_ADD;

    
    
    //**************************************************************************
    // Constantes para os botões do mouse.
    //**************************************************************************
    
    /**
     * Constante que representa o botão esquerdo do mouse.
     */
    public static final int MOUSE_BUTTON_LEFT    = MouseEvent.BUTTON1;
    
    /**
     * Constante que representa o botão do meio do mouse.
     */
    public static final int MOUSE_BUTTON_MIDDLE  = MouseEvent.BUTTON2;
    
    /**
     * Constante que representa o botão direito do mouse.
     */
    public static final int MOUSE_BUTTON_RIGHT   = MouseEvent.BUTTON3;
    

    
    //**************************************************************************
    // Constantes para o cursor do mouse.
    //**************************************************************************
    
    /**
     * Constante que representa o cursor padrão (seta) do mouse.
     */
    public static final int MOUSE_CURSOR_DEFAULT       = Cursor.DEFAULT_CURSOR;
    
    /**
     * Constante que representa o cursor de inserção de texto do mouse.
     */
    public static final int MOUSE_CURSOR_IBEAM         = Cursor.TEXT_CURSOR;
    
    /**
     * Constante que representa o cursor em cruz do mouse.
     */
    public static final int MOUSE_CURSOR_CROSSHAIR     = Cursor.CROSSHAIR_CURSOR;
    
    /**
     * Constante que representa o cursor de dedo apontando do mouse.
     */
    public static final int MOUSE_CURSOR_POINTING_HAND = Cursor.HAND_CURSOR;
    
    /**
     * Constante que representa o cursor de redimensionamento horizontal do mouse.
     */
    public static final int MOUSE_CURSOR_RESIZE_EW     = Cursor.E_RESIZE_CURSOR;
    
    /**
     * Constante que representa o cursor de redimensionamento vertical do mouse.
     */
    public static final int MOUSE_CURSOR_RESIZE_NS     = Cursor.N_RESIZE_CURSOR;
    
    /**
     * Constante que representa o cursor de redimensionamento diagonal cima-esquerda -> baixo-direita horizontal do mouse.
     */
    public static final int MOUSE_CURSOR_RESIZE_NWSE   = Cursor.NW_RESIZE_CURSOR;
    
    /**
     * Constante que representa o cursor de redimensionamento diagonal baixo-esquerda -> cima-direita horizontal do mouse.
     */
    public static final int MOUSE_CURSOR_RESIZE_NESW   = Cursor.NE_RESIZE_CURSOR;
    
    /**
     * Constante que representa o cursor de redimensionamento omnidirecional do mouse.
     */
    public static final int MOUSE_CURSOR_RESIZE_ALL    = Cursor.MOVE_CURSOR;
    
    /**
     * Constante que representa o cursor de redimensionamento aguarde do mouse.
     */
    public static final int MOUSE_CURSOR_WAIT          = Cursor.WAIT_CURSOR;
    
    
    
    //**************************************************************************
    // Constantes para fontes.
    //**************************************************************************
    
    /**
     * Constante que representa o estilo de fonte simples.
     */
    public static final int FONT_PLAIN                 = Font.PLAIN;
    
    /**
     * Constante que representa o estilo de fonte negrito.
     */
    public static final int FONT_BOLD                  = Font.BOLD;
    
    /**
     * Constante que representa o estilo de fonte itálico.
     */
    public static final int FONT_ITALIC                = Font.ITALIC;
    
    /**
     * Constante que representa o estilo de fonte negrito e itálico.
     */
    public static final int FONT_BOLD_ITALIC           = Font.BOLD + Font.ITALIC;
    
    /**
     * Constante que representa a fonte do tipo diálogo.
     */
    public static final String FONT_DIALOG             = Font.DIALOG;
    
    /**
     * Constante que representa a fonte do tipo diálogo de entrada.
     */
    public static final String FONT_DIALOG_INPUT       = Font.DIALOG_INPUT;
    
    /**
     * Constante que representa a fonte do tipo monoespaçada.
     */
    public static final String FONT_MONOSPACED         = Font.MONOSPACED;
    
    /**
     * Constante que representa a fonte do tipo não serifada.
     */
    public static final String FONT_SANS_SERIF         = Font.SANS_SERIF;
    
    /**
     * Constante que representa a fonte do tipo serifada.
     */
    public static final String FONT_SERIF              = Font.SERIF;
    
    
    
    //**************************************************************************
    // Constantes para contornos.
    //**************************************************************************
    
    /**
     * Constante que representa o estilo de fim da linha sem decoração para contornos.
     */
    public static final int STROKE_CAP_BUTT            = BasicStroke.CAP_BUTT;
    
    /**
     * Constante que representa o estilo de fim da linha redondo para contornos.
     */
    public static final int STROKE_CAP_ROUND           = BasicStroke.CAP_ROUND;
    
    /**
     * Constante que representa o estilo de fim da linha quadrado para contornos.
     */
    public static final int STROKE_CAP_SQUARE          = BasicStroke.CAP_SQUARE;
    
    /**
     * Constante que representa o estilo de junção entre linhas chanfrada para contornos.
     */
    public static final int STROKE_JOIN_BEVEL          = BasicStroke.JOIN_BEVEL;
    
    /**
     * Constante que representa o estilo de junção entre linhas em esquadria para contornos.
     */
    public static final int STROKE_JOIN_MITER          = BasicStroke.JOIN_MITER;
    
    /**
     * Constante que representa o estilo de junção entre linhas arredondado para contornos.
     */
    public static final int STROKE_JOIN_ROUND          = BasicStroke.JOIN_ROUND;
    


    //**************************************************************************
    // Cores padrão.
    //**************************************************************************
    
    /** Cor cinza claro. */
    public static final Color LIGHTGRAY  = new Color( 200, 200, 200 );
    
    /** Cor cinza. */
    public static final Color GRAY       = new Color( 130, 130, 130 );
    
    /** Cor cinza escuro. */
    public static final Color DARKGRAY   = new Color( 80, 80, 80 );
    
    /** Cor amarela. */
    public static final Color YELLOW     = new Color( 253, 249, 0 );
    
    /** Cor dourada. */
    public static final Color GOLD       = new Color( 255, 203, 0 );
    
    /** Cor laranja. */
    public static final Color ORANGE     = new Color( 255, 161, 0 );
    
    /** Cor rosa. */
    public static final Color PINK       = new Color( 255, 109, 194 );
    
    /** Cor vermelha. */
    public static final Color RED        = new Color( 230, 41, 55 );
    
    /** Cor vermelha escura. */
    public static final Color MAROON     = new Color( 190, 33, 55 );
    
    /** Cor verde. */
    public static final Color GREEN      = new Color( 0, 228, 48 );
    
    /** Cor limão. */
    public static final Color LIME       = new Color( 0, 158, 47 );
    
    /** Cor verde escuro. */
    public static final Color DARKGREEN  = new Color( 0, 117, 44 );
    
    /** Cor azul céu. */
    public static final Color SKYBLUE    = new Color( 102, 191, 255 );
    
    /** Cor azul. */
    public static final Color BLUE       = new Color( 0, 121, 241 );
    
    /** Cor azul escuro. */
    public static final Color DARKBLUE   = new Color( 0, 82, 172 );
    
    /** Cor roxa. */
    public static final Color PURPLE     = new Color( 200, 122, 255 );
    
    /** Cor violeta. */
    public static final Color VIOLET     = new Color( 135, 60, 190 );
    
    /** Cor roxo escuro. */
    public static final Color DARKPURPLE = new Color( 112, 31, 126 );
    
    /** Cor bege. */
    public static final Color BEIGE      = new Color( 211, 176, 131 );
    
    /** Cor marrom. */
    public static final Color BROWN      = new Color( 127, 106, 79 );
    
    /** Cor marrom escuro. */
    public static final Color DARKBROWN  = new Color( 76, 63, 47 );
    
    /** Cor branca. */
    public static final Color WHITE      = new Color( 255, 255, 255 );
    
    /** Cor preta. */
    public static final Color BLACK      = new Color( 0, 0, 0 );
    
    /** Cor transparente. */
    public static final Color BLANK      = new Color( 0, 0, 0, 0 );
    
    /** Cor magenta. */
    public static final Color MAGENTA    = new Color( 255, 0, 255 );
    
    /** Cor raywhite: homenagem à raylib www.raylib.com */
    public static final Color RAYWHITE   = new Color( 245, 245, 245 );
    
    
    
    //**************************************************************************
    // Níveis de log.
    //**************************************************************************
    
    /** Constante para desabilitar o sistema de logs */
    public static final int LOG_NONE           = TraceLogUtils.LOG_NONE;
    
    /** Constante para logs em nível INFO (informação). */
    public static final int LOG_INFO           = TraceLogUtils.LOG_INFO;
    
    /** Constante para logs em nível WARNING (aviso). */
    public static final int LOG_WARNING        = TraceLogUtils.LOG_WARNING;
    
    /** Constante para logs em nível ERROR (erro). */
    public static final int LOG_ERROR          = TraceLogUtils.LOG_ERROR;
    
    /** Constante para logs em nível FATAL. */
    public static final int LOG_FATAL          = TraceLogUtils.LOG_FATAL;
    
    /** Constante para realizar o log de quaisquer níveis. */
    public static final int LOG_ALL            = TraceLogUtils.LOG_ALL;
    
}
