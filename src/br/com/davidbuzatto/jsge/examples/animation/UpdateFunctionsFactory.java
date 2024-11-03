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
public class UpdateFunctionsFactory {
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenXEasing() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentMapper.set( "x", p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentMapper.set( "x", p.getDouble( "x2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenYEasing() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentMapper.set( "y", p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentMapper.set( "y", p.getDouble( "y2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenXYEasing() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentMapper.set( "x", p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                componentMapper.set( "y", p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentMapper.set( "x", p.getDouble( "x2" ) );
                    componentMapper.set( "y", p.getDouble( "y2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenRadiusEasing() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentMapper.set( "radius", p.getDouble( "radius1" ) + ( p.getDouble( "radius2" ) - p.getDouble( "radius1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentMapper.set( "radius", p.getDouble( "radius2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenAlphaEasing() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
                
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentMapper.set( "alpha", (int) ( p.getInt( "alpha1" ) + ( p.getInt( "alpha2" ) - p.getInt( "alpha1" ) ) * easingFunction.apply( stateContainer.percentage ) ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentMapper.set( "alpha", p.getInt( "alpha2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenRotationEasing() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentMapper.set( "rotation", p.getDouble( "angle1" ) + ( p.getDouble( "angle2" ) - p.getDouble( "angle1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentMapper.set( "rotation", p.getDouble( "angle2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenX() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                componentMapper.set( "x", (Double) componentMapper.get( "x" ) + delta * p.getDouble( "velX" ) );
                if ( (Double) componentMapper.get( "x" ) >= p.getDouble( "x2" ) ) {
                    componentMapper.set( "x", p.getDouble( "x2" ) );
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenY() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                componentMapper.set( "y", (Double) componentMapper.get( "y" ) + delta * p.getDouble( "velY" ) );
                if ( (Double) componentMapper.get( "y" ) >= p.getDouble( "y2" ) ) {
                    componentMapper.set( "y", p.getDouble( "y2" ) );
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> TweenAnimationUpdateFunction<ComponentType> tweenXY() {
        return ( 
            double delta, 
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> componentMapper, 
            DoubleFunction<Double> easingFunction,
            TweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == TweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = TweenAnimationExecutionState.RUNNING;
                componentMapper.set( "x", p.getDouble( "x1" ) );
                componentMapper.set( "y", p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == TweenAnimationExecutionState.RUNNING ) {
                
                componentMapper.set( "x", (Double) componentMapper.get( "x" ) + delta * p.getDouble( "velX" ) );
                componentMapper.set( "y", (Double) componentMapper.get( "y" ) + delta * p.getDouble( "velY" ) );
                boolean stop = false;
                
                if ( (Double) componentMapper.get( "x" ) >= p.getDouble( "x2" ) ) {
                    componentMapper.set( "x", p.getDouble( "x2" ) );
                    stop = true;
                }
                
                if ( (Double) componentMapper.get( "y" ) >= p.getDouble( "y2" ) ) {
                    componentMapper.set( "y", p.getDouble( "y2" ) );
                    stop = true;
                }
                
                if ( stop ) {
                    stateContainer.state = TweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
}
