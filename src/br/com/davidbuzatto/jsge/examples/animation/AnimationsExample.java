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
package br.com.davidbuzatto.jsge.examples.animation;

import br.com.davidbuzatto.jsge.animation.AnimationExecutionState;
import br.com.davidbuzatto.jsge.animation.AnimationUtils;
import br.com.davidbuzatto.jsge.animation.frame.FrameByFrameAnimation;
import br.com.davidbuzatto.jsge.animation.frame.DrawableAnimationFrame;
import br.com.davidbuzatto.jsge.animation.frame.ImageAnimationFrame;
import br.com.davidbuzatto.jsge.animation.frame.SpriteMapAnimationFrame;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimation;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.easing.EasingTweenAnimation;
import br.com.davidbuzatto.jsge.animation.tween.easing.TweenAnimationEasingFunctions;
import br.com.davidbuzatto.jsge.animation.tween.timing.TimingTweenAnimation;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Star;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

/**
 * Exemplos de animações.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AnimationsExample extends EngineFrame {

    private FrameByFrameAnimation<ImageAnimationFrame> imageAnimation;
    private FrameByFrameAnimation<DrawableAnimationFrame> drawableAnimation;
    private Color[] colors = { RED, GREEN, GOLD, ORANGE, BLUE, PINK, VIOLET };
    
    private static final double GRAVITY = 20;
    private Vector2 spritePos;
    private Vector2 spriteDim;
    private Vector2 spriteVel;
    private double spriteWalkSpeed;
    private double spriteJumpSpeed;
    private double spriteMaxFallSpeed;
    private boolean spriteTurnedRight;
    private boolean spriteIdle;
    private boolean spriteWalking;
    private boolean spriteJumping;
    private Rectangle floorRectangle;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteAnimationIdleRight;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteAnimationIdleLeft;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteAnimationWalkingRight;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteAnimationWalkingLeft;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteAnimationJumpingRight;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteAnimationJumpingLeft;
    
    private EasingTweenAnimation<Rectangle> etaPos;
    private EasingTweenAnimation<Circle> etaRadiusg;
    private EasingTweenAnimation<AlphaCircleSector> etaAlpha;
    private EasingTweenAnimation<Polygon> etaRotation;
    
    private TweenAnimation<Rectangle> taPos;
    private TweenAnimation<Circle> taRadius;
    private TweenAnimation<AlphaCircleSector> taAlpha;
    private TweenAnimation<Polygon> taRotation;
    
    private TimingTweenAnimation<Polygon> timingAnim;
    
    private Image easingFunctionImage;
    
    private static Pair[] easingFunctions = {
        new Pair( "easeInSine", TweenAnimationEasingFunctions.easeInSine ),
        new Pair( "easeOutSine", TweenAnimationEasingFunctions.easeOutSine ),
        new Pair( "easeInOutSine", TweenAnimationEasingFunctions.easeInOutSine ),
        new Pair( "easeInQuad", TweenAnimationEasingFunctions.easeInQuad ),
        new Pair( "easeOutQuad", TweenAnimationEasingFunctions.easeOutQuad ),
        new Pair( "easeInOutQuad", TweenAnimationEasingFunctions.easeInOutQuad ),
        new Pair( "easeInCubic", TweenAnimationEasingFunctions.easeInCubic ),
        new Pair( "easeOutCubic", TweenAnimationEasingFunctions.easeOutCubic ),
        new Pair( "easeInOutCubic", TweenAnimationEasingFunctions.easeInOutCubic ),
        new Pair( "easeInQuart", TweenAnimationEasingFunctions.easeInQuart ),
        new Pair( "easeOutQuart", TweenAnimationEasingFunctions.easeOutQuart ),
        new Pair( "easeInOutQuart", TweenAnimationEasingFunctions.easeInOutQuart ),
        new Pair( "easeInQuint", TweenAnimationEasingFunctions.easeInQuint ),
        new Pair( "easeOutQuint", TweenAnimationEasingFunctions.easeOutQuint ),
        new Pair( "easeInOutQuint", TweenAnimationEasingFunctions.easeInOutQuint ),
        new Pair( "easeInEtpo", TweenAnimationEasingFunctions.easeInEtpo ),
        new Pair( "easeOutEtpo", TweenAnimationEasingFunctions.easeOutEtpo ),
        new Pair( "easeInOutEtpo", TweenAnimationEasingFunctions.easeInOutEtpo ),
        new Pair( "easeInCirc", TweenAnimationEasingFunctions.easeInCirc ),
        new Pair( "easeOutCirc", TweenAnimationEasingFunctions.easeOutCirc ),
        new Pair( "easeInOutCirc", TweenAnimationEasingFunctions.easeInOutCirc ),
        new Pair( "easeInBack", TweenAnimationEasingFunctions.easeInBack ),
        new Pair( "easeOutBack", TweenAnimationEasingFunctions.easeOutBack ),
        new Pair( "easeInOutBack", TweenAnimationEasingFunctions.easeInOutBack ),
        new Pair( "easeInElastic", TweenAnimationEasingFunctions.easeInElastic ),
        new Pair( "easeOutElastic", TweenAnimationEasingFunctions.easeOutElastic ),
        new Pair( "easeInOutElastic", TweenAnimationEasingFunctions.easeInOutElastic ),
        new Pair( "easeInBounce", TweenAnimationEasingFunctions.easeInBounce ),
        new Pair( "easeOutBounce", TweenAnimationEasingFunctions.easeOutBounce ),
        new Pair( "easeInOutBounce", TweenAnimationEasingFunctions.easeInOutBounce )
    };
    
    private Pair easingFunctionPair;
    private int currentEasingFunction;
    private Button nextEFR;
    private Button prevEFR;
    private Button repeatEFR;
    
    private int fimH;
    private int fimV;
    
    private static class Pair {

        String name;
        DoubleFunction<Double> function;
        
        Pair( String name, DoubleFunction<Double> function ) {
            this.name = name;
            this.function = function;
        }
        
    }
    
    private static class Button {
        
        Rectangle rect;
        Triangle tri;
        String label;
        Color defaultColor;
        Color overColor;
        boolean isOver;

        Button( Rectangle rect, boolean rightTri ) {
            this( rect, rightTri, null );
        }
        
        Button( Rectangle rect, String label ) {
            this( rect, false, label );
        }
        
        Button( Rectangle rect, boolean rightTri, String label ) {
            this.rect = rect;
            defaultColor = LIGHTGRAY;
            overColor = BLUE;
            if ( label != null ) {
                this.label = label;
            } else if ( rightTri ) {
                tri = new Triangle(
                    rect.x + rect.width / 2 - 8,
                    rect.y + rect.height / 2 - 8,
                    rect.x + rect.width / 2 + 8,
                    rect.y + rect.height / 2,
                    rect.x + rect.width / 2 - 8,
                    rect.y + rect.height / 2 + 8
                );
            } else {
                tri = new Triangle(
                    rect.x + rect.width / 2 + 8,
                    rect.y + rect.height / 2 - 8,
                    rect.x + rect.width / 2 - 8,
                    rect.y + rect.height / 2,
                    rect.x + rect.width / 2 + 8,
                    rect.y + rect.height / 2 + 8
                );
            }
        }
        
        void draw( EngineFrame engine ) {
            Color color = defaultColor;
            if ( isOver ) {
                color = overColor;
            }
            rect.fill( engine, color );
            rect.draw( engine, BLACK );
            if ( label != null ) {
                engine.drawText( label, rect.x + 9, rect.y + 8, DARKGRAY );
            } else if ( tri != null ) {
                tri.fill( engine, DARKGRAY );
            }
        }
        
        boolean checkOver( Vector2 mousePos ) {
            isOver = CollisionUtils.checkCollisionPointRectangle( mousePos, rect );
            return isOver;
        }
        
    }
    
    private static class AlphaCircleSector extends CircleSector {
        
        int alpha;
        
        public AlphaCircleSector() {
            super();
        }
        public AlphaCircleSector( int x, int y, int radius, int startAngle, int endAngle ) {
            super( x, y, radius, startAngle, endAngle );
        }
        
    }
    
    /**
     * Cria o exemplo.
     */
    public AnimationsExample() {
        super( 875, 940, "Animations", 60, true );
    }
    
    @Override
    public void create() {
        
        /**
         * Cada animação baseada em frames precisa de uma lista de imagens
         * ou de componentes desenháveis (interface Drawable).
         */
        List<ImageAnimationFrame> imageFrames = new ArrayList<>();
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin0.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin1.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin2.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin3.png" ) ) );
        imageAnimation = new FrameByFrameAnimation<>( 0.1, imageFrames );
        
        List<DrawableAnimationFrame> drawableFrames = new ArrayList<>();
        drawableFrames.add( new DrawableAnimationFrame( new Rectangle( 20, 170, 50, 50 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new RoundRectangle( 70, 170, 50, 50, 20 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Circle( 145, 195, 25 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Polygon( 195, 195, 5, 25 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Star( 245, 195, 6, 25, 30 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Ring( 295, 195, 10, 25, 60, 300 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new CubicCurve( 320, 195, 365, 140, 385, 250, 430, 195 ) ) );
        drawableAnimation = new FrameByFrameAnimation<>( 0.5, drawableFrames );
        //drawableAnimation.setRunBackwards( true );
        //drawableAnimation.setStopAtLastFrameWhenFinished( true );
        
        spritePos = new Vector2( 150, 840 );
        spriteDim = new Vector2( 64, 64 );
        spriteVel = new Vector2();
        spriteWalkSpeed = 200;
        spriteJumpSpeed = 400;
        spriteMaxFallSpeed = 400;
        spriteTurnedRight = true;
        spriteIdle = true;
        spriteWalking = false;
        spriteJumping = false;
        floorRectangle = new Rectangle( 150, 904, getScreenWidth() - 175, 10 );
        
        spriteAnimationIdleRight = new FrameByFrameAnimation<>( 
            0.1, 
            AnimationUtils.getSpriteMapAnimationFrameList( 
                loadImage( "resources/images/spriteMapIdle.png" ), 
                4, spriteDim.x, spriteDim.y
            )
        );
        
        spriteAnimationIdleLeft = new FrameByFrameAnimation<>( 
            0.1, 
            AnimationUtils.getSpriteMapAnimationFrameList( 
                loadImage( "resources/images/spriteMapIdle.png" ).flipHorizontal(), 
                4, spriteDim.x, spriteDim.y, true
            )
        );
        
        spriteAnimationWalkingRight = new FrameByFrameAnimation<>( 
            0.05, 
            AnimationUtils.getSpriteMapAnimationFrameList( 
                loadImage( "resources/images/spriteMapWalking.png" ), 
                6, spriteDim.x, spriteDim.y
            )
        );
        
        spriteAnimationWalkingLeft = new FrameByFrameAnimation<>( 
            0.05, 
            AnimationUtils.getSpriteMapAnimationFrameList( 
                loadImage( "resources/images/spriteMapWalking.png" ).flipHorizontal(), 
                6, spriteDim.x, spriteDim.y, true
            )
        );
        
        spriteAnimationJumpingRight = new FrameByFrameAnimation<>( 
            0.1, 
            AnimationUtils.getSpriteMapAnimationFrameList( 
                loadImage( "resources/images/spriteMapJumping.png" ), 
                8, spriteDim.x, spriteDim.y
            )
        );
        
        spriteAnimationJumpingLeft = new FrameByFrameAnimation<>( 
            0.1, 
            AnimationUtils.getSpriteMapAnimationFrameList( 
                loadImage( "resources/images/spriteMapJumping.png" ).flipHorizontal(), 
                8, spriteDim.x, spriteDim.y, true
            )
        );
        
        prevEFR = new Button( new Rectangle( 660, 52, 30, 30 ), false );
        nextEFR = new Button( new Rectangle( 700, 52, 30, 30 ), true );
        repeatEFR = new Button( new Rectangle( 740, 52, 30, 30 ), "R" );
        
        /**
         * Os mapeadores servem como ponte de comunicação entre as funções de 
         * atualização (nesse caso EasingTweenAnimationUpdateFunction) e os
         * componentes que serão atualizados.
         */
        TweenAnimationComponentMapper<Rectangle> posMapperEasing = new TweenAnimationComponentMapper<>( new Rectangle( 0, 0, 80, 80 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                }
                return null;
            }
            
        };
        
        TweenAnimationComponentMapper<Circle> radiusMapperEasing = new TweenAnimationComponentMapper<>( new Circle( 0, 0, 0 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                    case "radius" -> component.radius = (Double) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                    case "radius" -> {
                        return component.radius;
                    }
                }
                return null;
            }
            
        };
        
        TweenAnimationComponentMapper<AlphaCircleSector> alphaMapperEasing = new TweenAnimationComponentMapper<>( new AlphaCircleSector( 0, 0, 40, 30, 330 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                    case "alpha" -> component.alpha = (Integer) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                    case "alpha" -> {
                        return component.alpha;
                    }
                }
                return null;
            }
            
        };
        
        TweenAnimationComponentMapper<Polygon> rotationMapperEasing = new TweenAnimationComponentMapper<>( new Polygon( 0, 0, 5, 40 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                    case "angle" -> component.rotation = (Double) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                    case "angle" -> {
                        return component.rotation;
                    }
                }
                return null;
            }
            
        };
        
        /**
         * Os mapeadores servem como ponte de comunicação entre as funções de 
         * atualização (nesse caso TweenAnimationUpdateFunction) e os
         * componentes que serão atualizados.
         */
        TweenAnimationComponentMapper<Rectangle> posMapper = new TweenAnimationComponentMapper<>( new Rectangle( 0, 0, 80, 80 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                }
                return null;
            }
            
        };
        
        TweenAnimationComponentMapper<Circle> radiusMapper = new TweenAnimationComponentMapper<>( new Circle( 0, 0, 0 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                    case "radius" -> component.radius = (Double) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                    case "radius" -> {
                        return component.radius;
                    }
                }
                return null;
            }
            
        };
        
        TweenAnimationComponentMapper<AlphaCircleSector> alphaMapper = new TweenAnimationComponentMapper<>( new AlphaCircleSector( 0, 0, 40, 30, 330 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                    case "alpha" -> component.alpha = (Integer) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                    case "alpha" -> {
                        return component.alpha;
                    }
                }
                return null;
            }
            
        };
        
        TweenAnimationComponentMapper<Polygon> rotationMapper = new TweenAnimationComponentMapper<>( new Polygon( 0, 0, 5, 40 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                    case "angle" -> component.rotation = (Double) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                    case "angle" -> {
                        return component.rotation;
                    }
                }
                return null;
            }
            
        };
        
        TweenAnimationComponentMapper<Polygon> timingMapper = new TweenAnimationComponentMapper<>( new Polygon( 0, 0, 6, 10 ) ){
            
            @Override
            public void set( String property, Object value ) {
                switch ( property ) {
                    case "x" -> component.x = (Double) value;
                    case "y" -> component.y = (Double) value;
                    case "angle" -> component.rotation = (Double) value;
                    case "radius" -> component.radius = (Double) value;
                }
            }
            
            @Override
            public Object get( String property ) {
                switch ( property ) {
                    case "x" -> {
                        return component.x;
                    }
                    case "y" -> {
                        return component.y;
                    }
                    case "angle" -> {
                        return component.rotation;
                    }
                    case "radius" -> {
                        return component.radius;
                    }
                }
                return null;
            }
            
        };
        
        /**
         * Para simplificar a forma que diversos parâmetros são passados para
         * a execução da função de atualização, use-se a classe TweenAnimationProperties.
         * Todos esses parâmetros poderão ser acessos e alterados caso necessário
         * dentro da função de atualização correspondente.
         */
        TweenAnimationProperties pPosEasing = TweenAnimationProperties.of( 
            "x1", 40,
            "y1", 345,
            "x2", 340
        );
        
        TweenAnimationProperties pRadiusEasing = TweenAnimationProperties.of( 
            "x1", 520,
            "y1", 385,
            "radius1", 10, 
            "radius2", 40
        );
        
        TweenAnimationProperties pAlphaEasing = TweenAnimationProperties.of( 
            "x1", 655,
            "y1", 385,
            "alpha1", 0, 
            "alpha2", 255
        );
        
        TweenAnimationProperties pRotationEasing = TweenAnimationProperties.of( 
            "x1", 790,
            "y1", 385,
            "angle1", 0.0, 
            "angle2", 360.0
        );
        
        TweenAnimationProperties pPos = TweenAnimationProperties.of( 
            "x1", 40,
            "y1", 435,
            "x2", 340,
            "velX", 150
        );
        
        TweenAnimationProperties pRadius = TweenAnimationProperties.of( 
            "x1", 520,
            "y1", 475,
            "radius1", 10, 
            "radius2", 40,
            "velRadius", 15
        );
        
        TweenAnimationProperties pAlpha = TweenAnimationProperties.of( 
            "x1", 655,
            "y1", 475,
            "alpha1", 0, 
            "alpha2", 255,
            "velAlpha", 128
        );
        
        TweenAnimationProperties pRotation = TweenAnimationProperties.of( 
            "x1", 790,
            "y1", 475,
            "angle1", 0.0, 
            "angle2", 360.0,
            "velAngle", 180.0
        );
        
        TweenAnimationProperties pTiming = TweenAnimationProperties.of( 
            "x", 280,
            "y", 700,
            "angle", 0,
            "radius", 10,
            "velX", 200,
            "velY", 200,
            "velAngle", 200,
            "velRadius", 15
        );
        
        fimH = 15;
        fimV = 45;
        
        easingFunctionPair = easingFunctions[currentEasingFunction];
        easingFunctionImage = DrawingUtils.plot( easingFunctionPair.function, 200, 200, fimH, fimV, BLACK, BLUE );
        
        etaPos = new EasingTweenAnimation<>(
            pPosEasing,                                        // propriedades
            posMapperEasing,                                   // mapeador
            EasingUpdateFunctionsFactory.<Rectangle>tweenX(),  // função de atualização
            easingFunctionPair.function,                       // função de suavização
            0.5                                                // 50% por segundo (2 segundos para executar a interpolação inteira)
        );
        
        etaRadiusg = new EasingTweenAnimation<>(
            pRadiusEasing,
            radiusMapperEasing,
            EasingUpdateFunctionsFactory.<Circle>tweenRadius(),
            easingFunctionPair.function,
            0.5
        );
        
        etaAlpha = new EasingTweenAnimation<>(
            pAlphaEasing,
            alphaMapperEasing,
            EasingUpdateFunctionsFactory.<AlphaCircleSector>tweenAlpha(),
            easingFunctionPair.function,
            0.5
        );
        
        etaRotation = new EasingTweenAnimation<>(
            pRotationEasing,
            rotationMapperEasing,
            EasingUpdateFunctionsFactory.<Polygon>tweenRotation(),
            easingFunctionPair.function,
            0.5
        );
        
        taPos = new TweenAnimation<>(
            pPos,                                              // propriedades
            posMapper,                                         // mapeador
            UpdateFunctionsFactory.<Rectangle>tweenX()         // função de atualização
        );
        
        taRadius = new TweenAnimation<>(
            pRadius,
            radiusMapper,
            UpdateFunctionsFactory.<Circle>tweenRadius()
        );
        
        taAlpha = new TweenAnimation<>(
            pAlpha,
            alphaMapper,
            UpdateFunctionsFactory.<AlphaCircleSector>tweenAlpha()
        );
        
        taRotation = new TweenAnimation<>(
            pRotation,
            rotationMapper,
            UpdateFunctionsFactory.<Polygon>tweenRotation()
        );
        
        timingAnim = new TimingTweenAnimation<>(
            pTiming,
            timingMapper,
            TimingFunctionsFactory.<Polygon>tweenTime(),
            1.5
        );
        
        setDefaultFontSize( 20 );
        
    }

    @Override
    public void update( double delta ) {
        
        Vector2 mousePos = getMousePositionPoint();
        
        imageAnimation.update( delta );
        drawableAnimation.update( delta );
        
        spriteAnimationIdleRight.update( delta );
        spriteAnimationIdleLeft.update( delta );
        spriteAnimationWalkingRight.update( delta );
        spriteAnimationWalkingLeft.update( delta );
        spriteAnimationJumpingRight.update( delta );
        spriteAnimationJumpingLeft.update( delta );
        
        if ( isKeyDown( KEY_A ) ) {
            spriteVel.x = -spriteWalkSpeed;
            spriteTurnedRight = false;
            spriteIdle = false;
        } else if ( isKeyDown( KEY_D ) ) {
            spriteVel.x = spriteWalkSpeed;
            spriteTurnedRight = true;
            spriteIdle = false;
        } else {
            spriteVel.x = 0;
            spriteIdle = true;
        }
        
        if ( isKeyPressed( KEY_SPACE ) ) {
            spriteVel.y = -spriteJumpSpeed;
            spriteJumping = true;
        }
        
        spritePos.x += spriteVel.x * delta;
        spritePos.y += spriteVel.y * delta;
        
        if ( spritePos.x <= 130 ) {
            spritePos.x = 130;
        } else if ( spritePos.x + spriteDim.x >= getScreenWidth() - 10 ) {
            spritePos.x = getScreenWidth() - 10 - spriteDim.x;
        }
        
        if ( spritePos.y + spriteDim.y > floorRectangle.y ) {
            spritePos.y = floorRectangle.y - spriteDim.y;
            spriteJumping = false;
        }
        
        spriteVel.y += GRAVITY;
        if ( spriteVel.y > spriteMaxFallSpeed ) {
            spriteVel.y = spriteMaxFallSpeed;
        }
        
        etaPos.update( delta );
        etaRadiusg.update( delta );
        etaAlpha.update( delta );
        etaRotation.update( delta );
        
        taPos.update( delta );
        taRadius.update( delta );
        taAlpha.update( delta );
        taRotation.update( delta );
        
        timingAnim.update( delta );
        
        prevEFR.checkOver( mousePos );
        nextEFR.checkOver( mousePos );
        repeatEFR.checkOver( mousePos );
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            if ( prevEFR.checkOver( mousePos ) ) {
                currentEasingFunction--;
                if ( currentEasingFunction < 0 ) {
                    currentEasingFunction = easingFunctions.length - 1;
                }
                currentEasingFunction %= easingFunctions.length;
                resetTweenAnimations();
            }
            if ( nextEFR.checkOver( mousePos ) ) {
                currentEasingFunction = ( currentEasingFunction + 1 ) % easingFunctions.length;
                resetTweenAnimations();
            }
            if ( repeatEFR.checkOver( mousePos ) ) {
                resetTweenAnimations();
            }
        }
        
        double mw = getMouseWheelMove();
        if ( mw != 0.0 ) {
            mw /= 100.0;
            imageAnimation.setTimeToNextFrame( imageAnimation.getTimeToNextFrame() + mw );
            if ( imageAnimation.getTimeToNextFrame() > 1.0 ) {
                imageAnimation.setTimeToNextFrame( 1.0 );
            } else if ( imageAnimation.getTimeToNextFrame() <= 0.01 ) {
                imageAnimation.setTimeToNextFrame( 0.01 );
            }
        }
        
        if ( isKeyPressed( KEY_LEFT ) ) {
            double t = timingAnim.getTotalExecutionTime();
            resetTimingAnimation( t );
        }
        
        if ( isKeyPressed( KEY_UP ) ) {
            double t = timingAnim.getTotalExecutionTime() + 0.5;
            if ( t >= 3.5 ) {
                t = 3.5;
            }
            resetTimingAnimation( t );
        }
        
        if ( isKeyPressed( KEY_DOWN ) ) {
            double t = timingAnim.getTotalExecutionTime() - 0.5;
            if ( t <= 0.5 ) {
                t = 0.5;
            }
            resetTimingAnimation( t );
        }
        
        if ( isKeyPressed( KEY_F1 ) ) {
            imageAnimation.pause();
        }
        
        if ( isKeyPressed( KEY_F2 ) ) {
            imageAnimation.resume();
        }
        
        if ( isKeyPressed( KEY_F3 ) ) {
            drawableAnimation.reset();
        }
        
        if ( isKeyPressed( KEY_F4 ) ) {
            drawableAnimation.setLooping( !drawableAnimation.isLooping() );
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        fillRectangle( 10, 10, 430, 110, ColorUtils.fade( LIGHTGRAY, 0.2 ) );
        drawText( "image animation (frame by frame)", 20, 20, BLACK );
        for ( int i = 0; i < 4; i++ ) {
            drawImage(imageAnimation.getCurrentFrame().baseImage, 20 + i * 40, 50 );
        }
        drawText( String.format( "%.2fs to next frame\nuse the mouse wheel to change!", imageAnimation.getTimeToNextFrame() ), 190, 55, 14, BLACK );
        drawText( 
            String.format( 
                "state: %s (<F1>: pause, <F2>: resume)",
                imageAnimation.getState() == AnimationExecutionState.RUNNING ? "running" : "paused"
            ),
            20, 100, 14, BLACK
        );
        
        fillRectangle( 10, 130, 430, 130, ColorUtils.fade( LIGHTGRAY, 0.2 ) );
        drawText( "drawable animation (frame by frame)", 20, 140, BLACK );
        drawableAnimation.getCurrentFrame().fill( this, colors[drawableAnimation.getCurrentFramePosition()] );
        drawableAnimation.getCurrentFrame().draw( this, BLACK );
        drawText( 
            String.format( 
                "looping %s (<F3>: reset, <F4> enable/disable)", drawableAnimation.isLooping() ? "enabled" : "disabled"
            ), 
            20, 240, 14, BLACK
        );
        
        //drawLine( 450, 20, 450, 250, BLACK );
        //drawLine( 20, 250, 450, 250, BLACK );
        
        fillRectangle( 450, 10, getScreenWidth() - 460, 250, ColorUtils.fade( LIGHTGRAY, 0.2 ) );
        drawText( "tween animation control:", 460, 20, BLACK );
        drawText( "easing function:", 460, 60, BLACK );
        drawText( String.format( "%02d/%02d", currentEasingFunction + 1, easingFunctions.length ), 780, 60, BLACK );
        prevEFR.draw( this );
        nextEFR.draw( this );
        repeatEFR.draw( this );
        drawImage( easingFunctionImage, 460, 80 );
        drawText( easingFunctionPair.name, 460 + easingFunctionImage.getWidth(), 80 + easingFunctionImage.getHeight() / 2 - 10, BLACK );
        
        fillCircle( 
            ( 460 + fimH ) + ( easingFunctionImage.getWidth() - fimH * 2 ) * etaPos.getPercentage(), 
            ( 80 + easingFunctionImage.getHeight() - fimV ) - ( easingFunctionImage.getHeight() - fimV * 2 ) * easingFunctionPair.function.apply( etaPos.getPercentage() ), 
            5, ColorUtils.fade( DARKBLUE, 0.8 ) );
        
        fillRectangle( 10, 270, getScreenWidth() - 20, 305, ColorUtils.fade( LIGHTGRAY, 0.2 ) );
        fillRectangle( 20, 310, 420, 225, ColorUtils.fade( VIOLET, 0.2 ) );
        fillRectangle( 465, 310, 110, 225, ColorUtils.fade( LIME, 0.2 ) );
        fillRectangle( 595, 310, 115, 225, ColorUtils.fade( ORANGE, 0.2 ) );
        fillRectangle( 730, 310, 125, 225, ColorUtils.fade( MAROON, 0.2 ) );
        
        String tEasing = String.format( "tween animation [with easing] (%.2f%%)", etaPos.getPercentage() * 100 );
        drawText( tEasing, getScreenWidth() / 2 - measureText( tEasing ) / 2, 280, BLACK );
        drawText( "position", 190, 320, BLACK );
        etaPos.getComponent().fill( this, VIOLET );
        etaPos.getComponent().draw( this, BLACK );
        
        drawText( "radius", 485, 320, BLACK );
        etaRadiusg.getComponent().fill( this, LIME );
        etaRadiusg.getComponent().draw( this, BLACK );
        
        drawText( "alpha", 620, 320, BLACK );
        etaAlpha.getComponent().fill( this, ColorUtils.fade( ORANGE, etaAlpha.getComponent().alpha / 255.0 ) );
        etaAlpha.getComponent().draw( this, ColorUtils.fade( BLACK, etaAlpha.getComponent().alpha / 255.0 ) );
        
        drawText( "rotation", 745, 320, BLACK );
        etaRotation.getComponent().fill( this, MAROON );
        etaRotation.getComponent().draw( this, BLACK );
        
        String tNoEasing = String.format( "tween animation [without easing] (%.2f%%)", taPos.getPercentage() * 100 );
        drawText( tNoEasing, getScreenWidth() / 2 - measureText( tNoEasing ) / 2, 550, BLACK );
        taPos.getComponent().fill( this, VIOLET );
        taPos.getComponent().draw( this, BLACK );
        taRadius.getComponent().fill( this, LIME );
        taRadius.getComponent().draw( this, BLACK );
        taAlpha.getComponent().fill( this, ColorUtils.fade( ORANGE, taAlpha.getComponent().alpha / 255.0 ) );
        taAlpha.getComponent().draw( this, ColorUtils.fade( BLACK, taAlpha.getComponent().alpha / 255.0 ) );
        taRotation.getComponent().fill( this, MAROON );
        taRotation.getComponent().draw( this, BLACK );
        
        fillRectangle( 10, 585, getScreenWidth() - 20, 205, ColorUtils.fade( LIGHTGRAY, 0.2 ) );
        String tLabel = String.format( "timing animation (%.2fs/%.2fs)", timingAnim.getExecutionTime(), timingAnim.getTotalExecutionTime() );
        drawText( tLabel, getScreenWidth() / 2 - measureText( tLabel ) / 2, 600, BLACK );
        drawText( """
                    <UP>: increase time
                          and reset
                  
                  <DOWN>: decrease time
                          and reset
                  
                  <LEFT>: reset
                  """, 20, 655, 14, BLACK );
        timingAnim.getComponent().fill( this, BLUE );
        timingAnim.getComponent().draw( this, BLACK );
        
        drawText( "sprite map animation (frame by frame)", 20, 810, BLACK );
        drawText( "<A>: move left\n<D>: move right\n<SPACE>: jump", 20, 860, 14, BLACK );
        fillRectangle( 10, 800, getScreenWidth() - 20, 130, ColorUtils.fade( LIGHTGRAY, 0.2 ) );
        floorRectangle.fill( this, PINK );
        floorRectangle.draw( this, BLACK );
        
        if ( spriteJumping ) {
            if ( spriteTurnedRight ) {
                spriteAnimationJumpingRight.getCurrentFrame().draw( this, spritePos.x, spritePos.y );
            } else {
                spriteAnimationJumpingLeft.getCurrentFrame().draw( this, spritePos.x, spritePos.y );
            }
        } else {
            if ( spriteIdle ) {
                if ( spriteTurnedRight ) {
                    spriteAnimationIdleRight.getCurrentFrame().draw( this, spritePos.x, spritePos.y );
                } else {
                    spriteAnimationIdleLeft.getCurrentFrame().draw( this, spritePos.x, spritePos.y );
                }
            } else {
                if ( spriteTurnedRight ) {
                    spriteAnimationWalkingRight.getCurrentFrame().draw( this, spritePos.x, spritePos.y );
                } else {
                    spriteAnimationWalkingLeft.getCurrentFrame().draw( this, spritePos.x, spritePos.y );
                }
            }
        }
        
        drawFPS( getScreenWidth() - 90, 20 );
        
    }
    
    private void resetTweenAnimations() {
        
        etaPos.reset();
        etaRadiusg.reset();
        etaAlpha.reset();
        etaRotation.reset();
        
        taPos.reset();
        taRadius.reset();
        taAlpha.reset();
        taRotation.reset();
        
        easingFunctionPair = easingFunctions[currentEasingFunction];
        easingFunctionImage = DrawingUtils.plot( easingFunctionPair.function, 200, 200, fimH, fimV, BLACK, BLUE );
        
        etaPos.setEasingFunction( easingFunctionPair.function );
        etaRadiusg.setEasingFunction( easingFunctionPair.function );
        etaAlpha.setEasingFunction( easingFunctionPair.function );
        etaRotation.setEasingFunction( easingFunctionPair.function );
        
    }
    
    private void resetTimingAnimation( double newTotalExecutionTime ) {
        timingAnim.reset();
        timingAnim.setTotalExecutionTime( newTotalExecutionTime );
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new AnimationsExample();
    }
    
}
