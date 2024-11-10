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

import br.com.davidbuzatto.jsge.animation.AnimationExecutionState;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationStateContainer;
import br.com.davidbuzatto.jsge.animation.tween.easing.EasingTweenAnimationUpdateFunction;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import java.util.function.DoubleFunction;

/**
 * Uma fábrica de funções de atualização com suavização para as animações
 * interpoladas suavizadas do exemplo de animações.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface EasingUpdateFunctionsFactory {
    
    /**
     * Fabrica uma função de interpolação em x com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> EasingTweenAnimationUpdateFunction<ComponentType> tweenX() {
        return ( 
            double delta, 
            double deltaP,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "x", p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "x", p.getDouble( "x2" ) );
                    sc.percentage = 1.0;
                    sc.state = AnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação em y com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> EasingTweenAnimationUpdateFunction<ComponentType> tweenY() {
        return ( 
            double delta, 
            double deltaP,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "y", p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "y", p.getDouble( "y2" ) );
                    sc.percentage = 1.0;
                    sc.state = AnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação em x e y com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> EasingTweenAnimationUpdateFunction<ComponentType> tweenXY() {
        return ( 
            double delta, 
            double deltaP,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "x", p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * ef.apply( sc.percentage ) );
                cm.set( "y", p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "x", p.getDouble( "x2" ) );
                    cm.set( "y", p.getDouble( "y2" ) );
                    sc.percentage = 1.0;
                    sc.state = AnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação para raio com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> EasingTweenAnimationUpdateFunction<ComponentType> tweenRadius() {
        return ( 
            double delta, 
            double deltaP,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "radius", p.getDouble( "radius1" ) + ( p.getDouble( "radius2" ) - p.getDouble( "radius1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "radius", p.getDouble( "radius2" ) );
                    sc.percentage = 1.0;
                    sc.state = AnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação para transparência com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> EasingTweenAnimationUpdateFunction<ComponentType> tweenAlpha() {
        return ( 
            double delta, 
            double deltaP,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
                
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "alpha", (int) ( p.getInt( "alpha1" ) + ( p.getInt( "alpha2" ) - p.getInt( "alpha1" ) ) * ef.apply( sc.percentage ) ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "alpha", p.getInt( "alpha2" ) );
                    sc.percentage = 1.0;
                    sc.state = AnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação para rotação com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> EasingTweenAnimationUpdateFunction<ComponentType> tweenRotation() {
        return ( 
            double delta, 
            double deltaP,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "angle", p.getDouble( "angle1" ) + ( p.getDouble( "angle2" ) - p.getDouble( "angle1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "angle", p.getDouble( "angle2" ) );
                    sc.percentage = 1.0;
                    sc.state = AnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
}
