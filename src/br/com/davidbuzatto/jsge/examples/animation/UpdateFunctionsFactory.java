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
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationStateContainer;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationUpdateFunction;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;

/**
 * Uma fábrica de funções de atualização para as animações interpoladas do
 * exemplo de animações.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface UpdateFunctionsFactory {
    
    /**
     * Fabrica uma função de interpolação em x.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenX() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                cm.set( "x", (Double) cm.get( "x" ) + p.getDouble( "velX" ) * delta );
                if ( (Double) cm.get( "x" ) >= p.getDouble( "x2" ) ) {
                    cm.set( "x", p.getDouble( "x2" ) );
                    sc.state = AnimationExecutionState.FINISHED;
                }
                sc.percentage = ( (Double) cm.get( "x" ) - p.getDouble( "x1" ) ) / ( p.getDouble( "x2" ) - p.getDouble( "x1" ) );
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação em y.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenY() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                cm.set( "y", (Double) cm.get( "y" ) + delta * p.getDouble( "velY" ) );
                if ( (Double) cm.get( "y" ) >= p.getDouble( "y2" ) ) {
                    cm.set( "y", p.getDouble( "y2" ) );
                    sc.state = AnimationExecutionState.FINISHED;
                }
                sc.percentage = ( (Double) cm.get( "y" ) - p.getDouble( "y1" ) ) / ( p.getDouble( "y2" ) - p.getDouble( "y1" ) );
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação em x e y.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenXY() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                
                cm.set( "x", (Double) cm.get( "x" ) + delta * p.getDouble( "velX" ) );
                cm.set( "y", (Double) cm.get( "y" ) + delta * p.getDouble( "velY" ) );
                boolean stop = false;
                
                if ( (Double) cm.get( "x" ) >= p.getDouble( "x2" ) ) {
                    cm.set( "x", p.getDouble( "x2" ) );
                    stop = true;
                }
                
                if ( (Double) cm.get( "y" ) >= p.getDouble( "y2" ) ) {
                    cm.set( "y", p.getDouble( "y2" ) );
                    stop = true;
                }
                
                if ( stop ) {
                    sc.state = AnimationExecutionState.FINISHED;
                }
                
                double sum = ( (Double) cm.get( "x" ) - p.getDouble( "x1" ) ) / ( p.getDouble( "x2" ) - p.getDouble( "x1" ) );
                sum += ( (Double) cm.get( "y" ) - p.getDouble( "y1" ) ) / ( p.getDouble( "y2" ) - p.getDouble( "y1" ) );
                sc.percentage = sum / 2.0;
                
            }
            
        };
    }
    
    /* Fabrica uma função de interpolação para raio.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenRadius() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
                cm.set( "radius", p.getDouble( "radius1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                cm.set( "radius", (Double) cm.get( "radius" ) + p.getDouble( "velRadius" ) * delta );
                if ( (Double) cm.get( "radius" ) >= p.getDouble( "radius2" ) ) {
                    cm.set( "radius", p.getDouble( "radius2" ) );
                    sc.state = AnimationExecutionState.FINISHED;
                }
                sc.percentage = ( (Double) cm.get( "radius" ) - p.getDouble( "radius1" ) ) / ( p.getDouble( "radius2" ) - p.getDouble( "radius1" ) );
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação para transparência.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenAlpha() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
                cm.set( "alpha", p.getInt( "alpha1" ) );
                
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                cm.set( "alpha", (int) ( (Integer) cm.get( "alpha" ) + p.getDouble( "velAlpha" ) * delta ) );
                if ( (Integer) cm.get( "alpha" ) >= p.getInt( "alpha2" ) ) {
                    cm.set( "alpha", p.getInt( "alpha2" ) );
                    sc.state = AnimationExecutionState.FINISHED;
                }
                sc.percentage = ( (Integer) cm.get( "alpha" ) - p.getDouble( "alpha1" ) ) / ( p.getDouble( "alpha2" ) - p.getDouble( "alpha1" ) );
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação para rotação com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenRotation() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
                cm.set( "angle", p.getDouble( "angle1" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                cm.set( "angle", (Double) cm.get( "angle" ) + p.getDouble( "velAngle" ) * delta );
                if ( (Double) cm.get( "angle" ) >= p.getDouble( "angle2" ) ) {
                    cm.set( "angle", p.getDouble( "angle2" ) );
                    sc.state = AnimationExecutionState.FINISHED;
                }
                sc.percentage = ( (Double) cm.get( "angle" ) - p.getDouble( "angle1" ) ) / ( p.getDouble( "angle2" ) - p.getDouble( "angle1" ) );
            }
            
        };
    }
    
}
