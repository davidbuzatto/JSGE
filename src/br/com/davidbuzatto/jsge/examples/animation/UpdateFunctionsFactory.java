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

import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationExecutionState;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationProperties;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationStateContainer;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationUpdateFunction;
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import java.util.function.DoubleFunction;

/**
 * Uma fábrica de funções de atualização para as animações interpoladas do
 * exemplo de animações.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface UpdateFunctionsFactory {
    
    /**
     * Fabrica uma função de interpolação em x com suavização.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenXEasing() {
        return ( 
            double delta, 
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "x", p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "x", p.getDouble( "x2" ) );
                    sc.percentage = 1.0;
                    sc.state = TweenAnimationExecutionState.FINISHED;
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
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenYEasing() {
        return ( 
            double delta, 
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "y", p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "y", p.getDouble( "y2" ) );
                    sc.percentage = 1.0;
                    sc.state = TweenAnimationExecutionState.FINISHED;
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
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenXYEasing() {
        return ( 
            double delta, 
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "x", p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * ef.apply( sc.percentage ) );
                cm.set( "y", p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "x", p.getDouble( "x2" ) );
                    cm.set( "y", p.getDouble( "y2" ) );
                    sc.percentage = 1.0;
                    sc.state = TweenAnimationExecutionState.FINISHED;
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
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenRadiusEasing() {
        return ( 
            double delta, 
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "radius", p.getDouble( "radius1" ) + ( p.getDouble( "radius2" ) - p.getDouble( "radius1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "radius", p.getDouble( "radius2" ) );
                    sc.percentage = 1.0;
                    sc.state = TweenAnimationExecutionState.FINISHED;
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
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenAlphaEasing() {
        return ( 
            double delta, 
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
                
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "alpha", (int) ( p.getInt( "alpha1" ) + ( p.getInt( "alpha2" ) - p.getInt( "alpha1" ) ) * ef.apply( sc.percentage ) ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "alpha", p.getInt( "alpha2" ) );
                    sc.percentage = 1.0;
                    sc.state = TweenAnimationExecutionState.FINISHED;
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
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenRotationEasing() {
        return ( 
            double delta, 
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                
                sc.percentage += deltaP * delta;
                cm.set( "rotation", p.getDouble( "angle1" ) + ( p.getDouble( "angle2" ) - p.getDouble( "angle1" ) ) * ef.apply( sc.percentage ) );
                
                if ( sc.percentage >= 1.0 ) {
                    cm.set( "rotation", p.getDouble( "angle2" ) );
                    sc.percentage = 1.0;
                    sc.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    /**
     * Fabrica uma função de interpolação em x.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenX() {
        return ( 
            double delta, 
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                cm.set( "x", (Double) cm.get( "x" ) + delta * p.getDouble( "velX" ) );
                if ( (Double) cm.get( "x" ) >= p.getDouble( "x2" ) ) {
                    cm.set( "x", p.getDouble( "x2" ) );
                    sc.state = TweenAnimationExecutionState.FINISHED;
                }
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
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                cm.set( "y", (Double) cm.get( "y" ) + delta * p.getDouble( "velY" ) );
                if ( (Double) cm.get( "y" ) >= p.getDouble( "y2" ) ) {
                    cm.set( "y", p.getDouble( "y2" ) );
                    sc.state = TweenAnimationExecutionState.FINISHED;
                }
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
            double deltaP,
            double totalT,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            DoubleFunction<Double> ef,
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == TweenAnimationExecutionState.INITIALIZED ) {
                sc.state = TweenAnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x1" ) );
                cm.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( sc.state == TweenAnimationExecutionState.RUNNING ) {
                
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
                    sc.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
}
