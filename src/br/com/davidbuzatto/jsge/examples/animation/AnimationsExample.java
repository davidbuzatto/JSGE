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
package br.com.davidbuzatto.jsge.examples.animation;

import br.com.davidbuzatto.jsge.animation.FrameByFrameAnimation;
import br.com.davidbuzatto.jsge.animation.TweenAnimation;
import br.com.davidbuzatto.jsge.animation.frame.DrawableAnimationFrame;
import br.com.davidbuzatto.jsge.animation.frame.ImageAnimationFrame;
import br.com.davidbuzatto.jsge.animation.tween.proxy.ComponentProxy;
import br.com.davidbuzatto.jsge.animation.tween.proxy.ComponentProxyAdapter;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationEasingFunctions;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
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
import br.com.davidbuzatto.jsge.math.CollisionUtils;
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
    
    private TweenAnimation<Rectangle> mtaPos;
    private TweenAnimation<Circle> mtaRadius;
    private TweenAnimation<AlphaCircleSector> mtaAlpha;
    private TweenAnimation<Polygon> mtaRotation;
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
        super( 870, 450, "Animations", 60, true );
    }
    
    @Override
    public void create() {
        
        /**
         * Cada animação baseada em frames precisa de uma lista de imagens
         * ou de componenets desenháveis (interface Drawable).
         */
        List<ImageAnimationFrame> imageFrames = new ArrayList<>();
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin0.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin1.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin2.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin3.png" ) ) );
        imageAnimation = new FrameByFrameAnimation<>( 0.1, imageFrames );
        
        List<DrawableAnimationFrame> drawableFrames = new ArrayList<>();
        drawableFrames.add( new DrawableAnimationFrame( new Rectangle( 20, 180, 50, 50 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new RoundRectangle( 70, 180, 50, 50, 20 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Circle( 145, 205, 25 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Polygon( 195, 205, 5, 25 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Star( 245, 205, 6, 25, 30 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Ring( 295, 205, 10, 25, 60, 300 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new CubicCurve( 320, 205, 365, 150, 385, 260, 430, 205 ) ) );
        drawableAnimation = new FrameByFrameAnimation<>( 0.5, drawableFrames );
        
        prevEFR = new Button( new Rectangle( 660, 52, 30, 30 ), false );
        nextEFR = new Button( new Rectangle( 700, 52, 30, 30 ), true );
        repeatEFR = new Button( new Rectangle( 740, 52, 30, 30 ), "R" );
        
        /**
         * Os proxies servem como ponte de comunicação entre as funções de 
         * atualização (TweenAnimationUpdateFunction) e os componentes que
         * serão atualizados. Implemente apenas os métodos necessários. Há diversos
         * métodos semanticamente associados às formas geométricas e algumas
         * propriedades físicas. Os métodos set devem ser usados para alterar
         * propriedades dos componenets e os métodos get devem ser usados
         * para ler tais propriedades.
         */
        ComponentProxy<Rectangle> proxyPos = new ComponentProxyAdapter<>( new Rectangle( 0, 0, 80, 80 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
        };
        
        ComponentProxy<Circle> proxyRadius = new ComponentProxyAdapter<>( new Circle( 0, 0, 0 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
            @Override
            public void setRadius( double radius ) {
                component.radius = radius;
            }
        };
        
        ComponentProxy<AlphaCircleSector> proxyAlpha = new ComponentProxyAdapter<>( new AlphaCircleSector( 0, 0, 40, 30, 330 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
            @Override
            public void setAlpha( int alpha ) {
                component.alpha = alpha;
            }
        };
        
        ComponentProxy<Polygon> proxyRotation = new ComponentProxyAdapter<>( new Polygon( 0, 0, 5, 40 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
            @Override
            public void setRotation( double rotation ) {
                component.rotation = rotation;
            }
        };
        
        /**
         * Para simplificar a forma que diversos parâmetros são passados para
         * a execução da função de atualização, use-se a classe TweenAnimationProperties.
         * Todos esses parâmetros poderão ser acessos e alterados caso necessário
         * dentro da função de atualização correspondente.
         */
        TweenAnimationProperties pPos = TweenAnimationProperties.of( 
            "x1", 50,
            "y1", 345,
            "x2", 340,
            "velX", 300,
            "velPercentage", 0.5
        );
        
        TweenAnimationProperties pRadius = TweenAnimationProperties.of( 
            "x1", 520,
            "y1", 385,
            "radius1", 10, 
            "radius2", 40, 
            "velPercentage", 0.5
        );
        
        TweenAnimationProperties pAlpha = TweenAnimationProperties.of( 
            "x1", 660,
            "y1", 385,
            "alpha1", 0, 
            "alpha2", 255, 
            "velPercentage", 0.5
        );
        
        TweenAnimationProperties pRotation = TweenAnimationProperties.of( 
            "x1", 790,
            "y1", 385,
            "angle1", 0.0, 
            "angle2", 360.0, 
            "velPercentage", 0.5
        );
        
        fimH = 15;
        fimV = 45;
        
        easingFunctionPair = easingFunctions[currentEasingFunction];
        easingFunctionImage = DrawingUtils.plot( easingFunctionPair.function, 200, 200, fimH, fimV, BLACK, BLUE );
        
        mtaPos = new TweenAnimation<>(
            pPos,
            proxyPos,
            UpdateFunctionsFactory.<Rectangle>tweenXEasing(),
            easingFunctionPair.function
        );
        
        mtaRadius = new TweenAnimation<>(
            pRadius,
            proxyRadius,
            UpdateFunctionsFactory.<Circle>tweenRadiusEasing(),
            easingFunctionPair.function
        );
        
        mtaAlpha = new TweenAnimation<>(
            pAlpha,
            proxyAlpha,
            UpdateFunctionsFactory.<AlphaCircleSector>tweenAlphaEasing(),
            easingFunctionPair.function
        );
        
        mtaRotation = new TweenAnimation<>(
            pRotation,
            proxyRotation,
            UpdateFunctionsFactory.<Polygon>tweenRotationEasing(),
            easingFunctionPair.function
        );
        
        setDefaultFontSize( 20 );
        
    }

    @Override
    public void update( double delta ) {
        
        Vector2 mousePos = getMousePositionPoint();
        
        imageAnimation.update( delta );
        drawableAnimation.update( delta );
        mtaPos.update( delta );
        mtaRadius.update( delta );
        mtaAlpha.update( delta );
        mtaRotation.update( delta );
        
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
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        drawText( "image animation (frame by frame)", 20, 50, BLACK );
        for ( int i = 0; i < 10; i++ ) {
            drawImage( imageAnimation.getCurrentFrame().image, 20 + i * 40, 80 );
        }
        
        drawText( "drawable animation (frame by frame)", 20, 150, BLACK );
        drawableAnimation.getCurrentFrame().fill( this, colors[drawableAnimation.getCurrentFramePosition()] );
        drawableAnimation.getCurrentFrame().draw( this, BLACK );
        
        drawLine( 450, 20, 450, 250, BLACK );
        drawLine( 20, 250, 450, 250, BLACK );
        
        drawText( "tween animations", 20, 270, BLACK );
        drawText( "position:", 20, 300, BLACK );
        mtaPos.getComponent().fill( this, VIOLET );
        mtaPos.getComponent().draw( this, BLACK );
        
        drawText( "radius:", 480, 300, BLACK );
        mtaRadius.getComponent().fill( this, LIME );
        mtaRadius.getComponent().draw( this, BLACK );
        
        drawText( "alpha:", 620, 300, BLACK );
        mtaAlpha.getComponent().fill( this, ColorUtils.fade( ORANGE, mtaAlpha.getComponent().alpha / 255.0 ) );
        mtaAlpha.getComponent().draw( this, ColorUtils.fade( BLACK, mtaAlpha.getComponent().alpha / 255.0 ) );
        
        drawText( "rotation:", 740, 300, BLACK );
        mtaRotation.getComponent().fill( this, MAROON );
        mtaRotation.getComponent().draw( this, BLACK );
        
        drawText( "tween animations options", 460, 20, BLACK );
        drawText( "easing function:", 460, 60, BLACK );
        drawText( String.format( "%02d/%02d", currentEasingFunction + 1, easingFunctions.length ), 780, 60, BLACK );
        prevEFR.draw( this );
        nextEFR.draw( this );
        repeatEFR.draw( this );
        drawImage( easingFunctionImage, 460, 80 );
        drawText( easingFunctionPair.name, 460 + easingFunctionImage.getWidth(), 80 + easingFunctionImage.getHeight() / 2 - 10, BLACK );
        
        fillCircle( 
            ( 460 + fimH ) + ( easingFunctionImage.getWidth() - fimH * 2 ) * mtaPos.getPercentage(), 
            ( 80 + easingFunctionImage.getHeight() - fimV ) - ( easingFunctionImage.getHeight() - fimV * 2 ) * easingFunctionPair.function.apply( mtaPos.getPercentage() ), 
            5, ColorUtils.fade( DARKBLUE, 0.8 ) );
        
        drawFPS( 10, 10 );
        
    }
    
    private void resetTweenAnimations() {
        mtaPos.reset();
        mtaRadius.reset();
        mtaAlpha.reset();
        mtaRotation.reset();
        easingFunctionPair = easingFunctions[currentEasingFunction];
        easingFunctionImage = DrawingUtils.plot( easingFunctionPair.function, 200, 200, fimH, fimV, BLACK, BLUE );
        mtaPos.setEasingFunction( easingFunctionPair.function );
        mtaRadius.setEasingFunction( easingFunctionPair.function );
        mtaAlpha.setEasingFunction( easingFunctionPair.function );
        mtaRotation.setEasingFunction( easingFunctionPair.function );
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new AnimationsExample();
    }
    
}
