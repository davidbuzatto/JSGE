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
 * Uma fábrica de interpoladores de movimento.
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
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
                if ( easingFunction != null ) {
                    stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                    componentProxy.setX( p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                    if ( stateContainer.percentage >= 1.0 ) {
                        componentProxy.setX( p.getDouble( "x2" ) );
                        stateContainer.percentage = 1.0;
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
                } else {
                    componentProxy.setX( componentProxy.getX() + delta * p.getDouble( "velX" ) );
                    if ( componentProxy.getX() >= p.getDouble( "x2" ) ) {
                        componentProxy.setX( p.getDouble( "x2" ) );
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
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
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
                if ( easingFunction != null ) {
                    stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                    componentProxy.setY( p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                    if ( stateContainer.percentage >= 1.0 ) {
                        componentProxy.setY( p.getDouble( "y2" ) );
                        stateContainer.percentage = 1.0;
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
                } else {
                    componentProxy.setY( componentProxy.getY() + delta * p.getDouble( "velY" ) );
                    if ( componentProxy.getY() >= p.getDouble( "y2" ) ) {
                        componentProxy.setY( p.getDouble( "y2" ) );
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
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
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
                boolean stop = false;
                
                if ( easingFunction != null ) {
                    stateContainer.percentage += p.getDouble( "velPercentage" ) * delta;
                    componentProxy.setX( p.getDouble( "x1" ) + ( p.getDouble( "x2" ) - p.getDouble( "x1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                    componentProxy.setY( p.getDouble( "y1" ) + ( p.getDouble( "y2" ) - p.getDouble( "y1" ) ) * easingFunction.apply( stateContainer.percentage ) );
                    if ( stateContainer.percentage >= 1.0 ) {
                        componentProxy.setX( p.getDouble( "x2" ) );
                        componentProxy.setY( p.getDouble( "y2" ) );
                        stateContainer.percentage = 1.0;
                        stop = true;
                    }
                } else {
                    componentProxy.setX( componentProxy.getX() + delta * p.getDouble( "velX" ) );
                    componentProxy.setY( componentProxy.getY() + delta * p.getDouble( "velY" ) );
                    if ( componentProxy.getX() >= p.getDouble( "x2" ) ) {
                        componentProxy.setX( p.getDouble( "x2" ) );
                        stop = true;
                    }
                    if ( componentProxy.getY() >= p.getDouble( "y2" ) ) {
                        componentProxy.setY( p.getDouble( "y2" ) );
                        stop = true;
                    }
                }
                
                if ( stop ) {
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenXSimple() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                componentProxy.setX( componentProxy.getX() + delta * p.getDouble( "velX" ) );
                if ( componentProxy.getX() >= p.getDouble( "x2" ) ) {
                    componentProxy.setX( p.getDouble( "x2" ) );
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenYSimple() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                componentProxy.setY( componentProxy.getY() + delta * p.getDouble( "velY" ) );
                if ( componentProxy.getY() >= p.getDouble( "y2" ) ) {
                    componentProxy.setY( p.getDouble( "y2" ) );
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenAnimationConsumer<ComponentType> tweenXYSimple() {
        return ( 
            double delta, 
            MotionTweenAnimationProperties p,
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( p.getDouble( "x1" ) );
                componentProxy.setY( p.getDouble( "y1" ) );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
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
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
                
            }
            
        };
    }
    
}
