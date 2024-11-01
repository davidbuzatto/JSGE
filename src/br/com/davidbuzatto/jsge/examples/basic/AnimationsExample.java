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

import br.com.davidbuzatto.jsge.animation.FrameByFrameAnimation;
import br.com.davidbuzatto.jsge.animation.MotionTweenAnimation;
import br.com.davidbuzatto.jsge.animation.frame.DrawableAnimationFrame;
import br.com.davidbuzatto.jsge.animation.frame.ImageAnimationFrame;
import br.com.davidbuzatto.jsge.animation.proxy.ComponentProxy;
import br.com.davidbuzatto.jsge.animation.proxy.ComponentProxyAdapter;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationConsumerFactory;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationEasingFunctions;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationProperties;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLUE;
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
    
    private MotionTweenAnimation<Rectangle> mtaPos;
    private MotionTweenAnimation<Circle> mtaRadius;
    private MotionTweenAnimation<AlphaCircleSector> mtaAlpha;
    private MotionTweenAnimation<Polygon> mtaRotation;
    private Image easingFunctionImage;
    
    private static Pair[] easingFunctions = {
        new Pair( "easeInSine", MotionTweenAnimationEasingFunctions.easeInSine ),
        new Pair( "easeOutSine", MotionTweenAnimationEasingFunctions.easeOutSine ),
        new Pair( "easeInOutSine", MotionTweenAnimationEasingFunctions.easeInOutSine ),
        new Pair( "easeInQuad", MotionTweenAnimationEasingFunctions.easeInQuad ),
        new Pair( "easeOutQuad", MotionTweenAnimationEasingFunctions.easeOutQuad ),
        new Pair( "easeInOutQuad", MotionTweenAnimationEasingFunctions.easeInOutQuad ),
        new Pair( "easeInCubic", MotionTweenAnimationEasingFunctions.easeInCubic ),
        new Pair( "easeOutCubic", MotionTweenAnimationEasingFunctions.easeOutCubic ),
        new Pair( "easeInOutCubic", MotionTweenAnimationEasingFunctions.easeInOutCubic ),
        new Pair( "easeInQuart", MotionTweenAnimationEasingFunctions.easeInQuart ),
        new Pair( "easeOutQuart", MotionTweenAnimationEasingFunctions.easeOutQuart ),
        new Pair( "easeInOutQuart", MotionTweenAnimationEasingFunctions.easeInOutQuart ),
        new Pair( "easeInQuint", MotionTweenAnimationEasingFunctions.easeInQuint ),
        new Pair( "easeOutQuint", MotionTweenAnimationEasingFunctions.easeOutQuint ),
        new Pair( "easeInOutQuint", MotionTweenAnimationEasingFunctions.easeInOutQuint ),
        new Pair( "easeInEtpo", MotionTweenAnimationEasingFunctions.easeInEtpo ),
        new Pair( "easeOutEtpo", MotionTweenAnimationEasingFunctions.easeOutEtpo ),
        new Pair( "easeInOutEtpo", MotionTweenAnimationEasingFunctions.easeInOutEtpo ),
        new Pair( "easeInCirc", MotionTweenAnimationEasingFunctions.easeInCirc ),
        new Pair( "easeOutCirc", MotionTweenAnimationEasingFunctions.easeOutCirc ),
        new Pair( "easeInOutCirc", MotionTweenAnimationEasingFunctions.easeInOutCirc ),
        new Pair( "easeInBack", MotionTweenAnimationEasingFunctions.easeInBack ),
        new Pair( "easeOutBack", MotionTweenAnimationEasingFunctions.easeOutBack ),
        new Pair( "easeInOutBack", MotionTweenAnimationEasingFunctions.easeInOutBack ),
        new Pair( "easeInElastic", MotionTweenAnimationEasingFunctions.easeInElastic ),
        new Pair( "easeOutElastic", MotionTweenAnimationEasingFunctions.easeOutElastic ),
        new Pair( "easeInOutElastic", MotionTweenAnimationEasingFunctions.easeInOutElastic ),
        new Pair( "easeInBounce", MotionTweenAnimationEasingFunctions.easeInBounce ),
        new Pair( "easeOutBounce", MotionTweenAnimationEasingFunctions.easeOutBounce ),
        new Pair( "easeInOutBounce", MotionTweenAnimationEasingFunctions.easeInOutBounce )
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
            if ( CollisionUtils.checkCollisionPointRectangle( mousePos, rect ) ) {
                isOver = true;
            } else {
                isOver = false;
            }
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
        
        ComponentProxy<Rectangle> proxyPos = new ComponentProxyAdapter<>( new Rectangle( 0, 0, 50, 50 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public double getX() {
                return component.x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
            @Override
            public double getY() {
                return component.y;
            }
        };
        
        ComponentProxy<Circle> proxyRadius = new ComponentProxyAdapter<>( new Circle( 0, 0, 0 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public double getX() {
                return component.x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
            @Override
            public double getY() {
                return component.y;
            }
            @Override
            public void setRadius( double radius ) {
                component.radius = radius;
            }
            @Override
            public double getRadius() {
                return component.radius;
            }
        };
        
        ComponentProxy<AlphaCircleSector> proxyAlpha = new ComponentProxyAdapter<>( new AlphaCircleSector( 0, 0, 30, -30, 300 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public double getX() {
                return component.x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
            @Override
            public double getY() {
                return component.y;
            }
            @Override
            public void setAlpha( int alpha ) {
                component.alpha = alpha;
            }
            @Override
            public int getAlpha() {
                return component.alpha;
            }
            @Override
            public double getRadius() {
                return component.radius;
            }
        };
        
        ComponentProxy<Polygon> proxyRotation = new ComponentProxyAdapter<>( new Polygon( 0, 0, 5, 35 ) ){
            @Override
            public void setX( double x ) {
                component.x = x;
            }
            @Override
            public double getX() {
                return component.x;
            }
            @Override
            public void setY( double y ) {
                component.y = y;
            }
            @Override
            public double getY() {
                return component.y;
            }
            @Override
            public void setRotation( double rotation ) {
                component.rotation = rotation;
            }
            @Override
            public double getRotation() {
                return component.rotation;
            }
            @Override
            public double getRadius() {
                return component.radius;
            }
        };
        
        MotionTweenAnimationProperties pPos = MotionTweenAnimationProperties.of( 
            "x1", 50,
            "y1", 360,
            "x2", 380,
            "y2", 360,
            "velX", 300,
            "velT", 300,
            "velPercentage", 0.5
        );
        
        MotionTweenAnimationProperties pRadius = MotionTweenAnimationProperties.of( 
            "x1", 520,
            "y1", 385,
            "radius1", 10, 
            "radius2", 30, 
            "velPercentage", 0.5
        );
        
        MotionTweenAnimationProperties pAlpha = MotionTweenAnimationProperties.of( 
            "x1", 660,
            "y1", 385,
            "alpha1", 0, 
            "alpha2", 255, 
            "velPercentage", 0.5
        );
        
        MotionTweenAnimationProperties pRotation = MotionTweenAnimationProperties.of( 
            "x1", 790,
            "y1", 385,
            "startAngle", 0.0, 
            "endAngle", 360.0, 
            "velPercentage", 0.5
        );
        
        fimH = 15;
        fimV = 45;
        
        easingFunctionPair = easingFunctions[currentEasingFunction];
        easingFunctionImage = DrawingUtils.plot( easingFunctionPair.function, 200, 200, fimH, fimV, BLACK, BLUE );
        
        mtaPos = new MotionTweenAnimation<>(
            pPos,
            proxyPos,
            MotionTweenAnimationConsumerFactory.<Rectangle>tweenXEasing(),
            easingFunctionPair.function
        );
        
        mtaRadius = new MotionTweenAnimation<>(
            pRadius,
            proxyRadius,
            MotionTweenAnimationConsumerFactory.<Circle>tweenRadiusEasing(),
            easingFunctionPair.function
        );
        
        mtaAlpha = new MotionTweenAnimation<>(
            pAlpha,
            proxyAlpha,
            MotionTweenAnimationConsumerFactory.<AlphaCircleSector>tweenAlphaEasing(),
            easingFunctionPair.function
        );
        
        mtaRotation = new MotionTweenAnimation<>(
            pRotation,
            proxyRotation,
            MotionTweenAnimationConsumerFactory.<Polygon>tweenRotationEasing(),
            easingFunctionPair.function
        );
        
        setDefaultFontSize( 20 );
        
    }

    @Override
    public void update() {
        
        double delta = getFrameTime();
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
                resetMotionTweenAnimation();
            }
            if ( nextEFR.checkOver( mousePos ) ) {
                currentEasingFunction = ( currentEasingFunction + 1 ) % easingFunctions.length;
                resetMotionTweenAnimation();
            }
            if ( repeatEFR.checkOver( mousePos ) ) {
                resetMotionTweenAnimation();
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
        
        drawText( "motion tween animation", 20, 270, BLACK );
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
        
        drawText( "motion tween options", 460, 20, BLACK );
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
    
    private void resetMotionTweenAnimation() {
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
