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

import br.com.davidbuzatto.jsge.animation.proxy.ComponentProxy;
import br.com.davidbuzatto.jsge.animation.proxy.ComponentProxyAdapter;
import br.com.davidbuzatto.jsge.animation.frame.DrawableAnimationFrame;
import br.com.davidbuzatto.jsge.animation.FrameByFrameAnimation;
import br.com.davidbuzatto.jsge.animation.MotionTweenAnimation;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationConsumerFactory;
import br.com.davidbuzatto.jsge.animation.frame.ImageAnimationFrame;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationEasingFunctions;
import br.com.davidbuzatto.jsge.animation.tween.MotionTweenAnimationProperties;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

/**
 * Classe de testes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ScratchPad extends EngineFrame {
    
    private FrameByFrameAnimation<ImageAnimationFrame> imgAnimationFixed;
    private FrameByFrameAnimation<ImageAnimationFrame> imgAnimationVariable;
    private FrameByFrameAnimation<DrawableAnimationFrame> drAnimationFixed;
    private MotionTweenAnimation<Rectangle> motionTweenAnimation;
    private Image easingFunctionImage;
    
    /**
     * Cria o teste.
     */
    public ScratchPad() {
        super( 800, 450, "Scratch Pad", 60, true );
    }
    
    @Override
    public void create() {
        
        List<ImageAnimationFrame> imageFrames = new ArrayList<>();
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin0.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin1.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin2.png" ) ) );
        imageFrames.add( new ImageAnimationFrame( loadImage( "resources/images/coin3.png" ) ) );
        imgAnimationFixed = new FrameByFrameAnimation( 0.1, imageFrames );
        imgAnimationVariable = new FrameByFrameAnimation( new double[]{ 3, 2, 1, 0.5 }, imageFrames );
        
        List<DrawableAnimationFrame> drawableFrames = new ArrayList<>();
        drawableFrames.add( new DrawableAnimationFrame( new Rectangle( 35, 165, 50, 50 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Circle( 60, 190, 25 ) ) );
        drawableFrames.add( new DrawableAnimationFrame( new Polygon( 60, 190, 5, 25 ) ) );
        drAnimationFixed = new FrameByFrameAnimation( 0.5, drawableFrames );
        
        ComponentProxy<Rectangle> proxy = new ComponentProxyAdapter<>(new Rectangle( 0, 0, 50, 50 )){
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
        
        MotionTweenAnimationProperties p = MotionTweenAnimationProperties.of( 
            "x1", 300,
            "y1", 50,
            "x2", 600,
            "y2", 350,
            "velX", 300,
            "velT", 200,
            "velPercentage", 0.5
        );
        
        DoubleFunction<Double> easingFunction = MotionTweenAnimationEasingFunctions.easeInOutBack;
        easingFunctionImage = DrawingUtils.plot( easingFunction, 250, 200, 15, 30, BLACK, BLUE );
        
        motionTweenAnimation = new MotionTweenAnimation<>(
            p,
            proxy,
            MotionTweenAnimationConsumerFactory.<Rectangle>tweenXYEasing(),
            easingFunction
        );
        
    }

    @Override
    public void update() {
        
        double delta = getFrameTime();
        
        imgAnimationFixed.update( delta );
        imgAnimationVariable.update( delta );
        
        drAnimationFixed.update( delta );
        
        motionTweenAnimation.update( delta );
        
    }
    
    @Override
    public void draw() {
        
        drawImage( imgAnimationFixed.getCurrentFrame().image, 25, 60 );
        drawImage( imgAnimationVariable.getCurrentFrame().image, 75, 60 );
        
        imgAnimationFixed.getCurrentFrame().draw( this, 25, 110 );
        imgAnimationVariable.getCurrentFrame().draw( this, 75, 110 );
        
        /*drAnimationFixed.getCurrentFrame().getDrawable().fill( this, GREEN );
        drAnimationFixed.getCurrentFrame().getDrawable().draw( this, BLACK );*/
        
        drAnimationFixed.getCurrentFrame().fill( this, GREEN );
        drAnimationFixed.getCurrentFrame().draw( this, BLACK );
        
        motionTweenAnimation.getComponent().fill( this, BLUE );
        drawImage( easingFunctionImage, 25, 220, WHITE );
        
        drawFPS( 20, 20 );
        
        
    }
    
    /**
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new ScratchPad();
    }
    
}
