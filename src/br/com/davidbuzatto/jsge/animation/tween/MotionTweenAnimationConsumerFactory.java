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
package br.com.davidbuzatto.jsge.animation.tween;

import br.com.davidbuzatto.jsge.animation.proxy.ComponentProxy;
import java.util.function.DoubleFunction;

/**
 * Uma fábrica de funções de atualização para as animações com interpolação de movimento.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MotionTweenAnimationConsumerFactory {
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenXEasing() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentProxy.setX( p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentProxy.setX( p.getDouble( "x2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenYEasing() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentProxy.setY( p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentProxy.setY( p.getDouble( "y2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenXYEasing() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentProxy.setX( p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                componentProxy.setY( p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentProxy.setX( p.getDouble( "x2" ) );
                    componentProxy.setY( p.getDouble( "y2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenRadiusEasing() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentProxy.setRadius( p.getDouble( "radius1" ) + ( p.getDouble( "radius2" ) - p.getDouble( "radius1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentProxy.setRadius( p.getDouble( "radius2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenAlphaEasing() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
                
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentProxy.setAlpha( (int) ( p.getInt( "alpha1" ) + ( p.getInt( "alpha2" ) - p.getInt( "alpha1" ) ) * easingFunction.apply( stateContainer.percentage ) ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentProxy.setAlpha( p.getInt( "alpha2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenRotationEasing() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                
                stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                componentProxy.setRotation( p.getDouble( "angle1" ) + ( p.getDouble( "angle2" ) - p.getDouble( "angle1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                
                if ( stateContainer.percentage >= 1.0 ) {
                    componentProxy.setRotation( p.getDouble( "angle2" ) );
                    stateContainer.percentage = 1.0;
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenX() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                componentProxy.setX( componentProxy.getX() + delta * p.getDouble( "velX" ) );
                if ( componentProxy.getX() >= p.getDouble( "x2" ) ) {
                    componentProxy.setX( p.getDouble( "x2" ) );
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenY() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                componentProxy.setY( componentProxy.getY() + delta * p.getDouble( "velY" ) );
                if ( componentProxy.getY() >= p.getDouble( "y2" ) ) {
                    componentProxy.setY( p.getDouble( "y2" ) );
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenXY() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationExecutionState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationExecutionState.RUNNING ) {
                
                componentProxy.setX( componentProxy.getX() + delta * p.getDouble( "velX" ) );
                componentProxy.setY( componentProxy.getY() + delta * p.getDouble( "velY" ) );
                boolean stop = false;
                
                if ( componentProxy.getX() >= p.getDouble( "x2" ) ) {
                    componentProxy.setX( p.getDouble( "x2" ) );
                    stop = true;
                }
                
                if ( componentProxy.getY() >= p.getDouble( "y2" ) ) {
                    componentProxy.setY( p.getDouble( "y2" ) );
                    stop = true;
                }
                
                if ( stop ) {
                    stateContainer.state = MotionTweenAnimationExecutionState.FINISHED;
                }
                
            }
            
        };
    }
    
}
