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
public class MotionTweenFactory {
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenXEasing() {
        return ( 
            double delta, 
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer,
            double x1, 
            double y1, 
            double x2, 
            double y2,
            double startAngle,
            double endAngle,
            double scale,
            double radius,
            double velX,
            double velY,
            double velAngle,
            double velScale,
            double velRadius,
            double velPercentage ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( x1 );
                componentProxy.setY( y1 );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
                if ( easingFunction != null ) {
                    stateContainer.percentage += velPercentage * delta;
                    componentProxy.setX( x1 + ( x2 - x1 ) * easingFunction.apply( stateContainer.percentage ) );
                    if ( stateContainer.percentage >= 1.0 ) {
                        componentProxy.setX( x2 );
                        stateContainer.percentage = 1.0;
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
                } else {
                    componentProxy.setX( componentProxy.getX() + delta * velX );
                    if ( componentProxy.getX() >= x2 ) {
                        componentProxy.setX( x2 );
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenYEasing() {
        return ( 
            double delta, 
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer,
            double x1, 
            double y1, 
            double x2, 
            double y2,
            double startAngle,
            double endAngle,
            double scale,
            double radius,
            double velX,
            double velY,
            double velAngle,
            double velScale,
            double velRadius,
            double velPercentage ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( x1 );
                componentProxy.setY( y1 );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
                if ( easingFunction != null ) {
                    stateContainer.percentage += velPercentage * delta;
                    componentProxy.setY( y1 + ( y2 - y1 ) * easingFunction.apply( stateContainer.percentage ) );
                    if ( stateContainer.percentage >= 1.0 ) {
                        componentProxy.setY( y2 );
                        stateContainer.percentage = 1.0;
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
                } else {
                    componentProxy.setY( componentProxy.getY() + delta * velY );
                    if ( componentProxy.getY() >= y2 ) {
                        componentProxy.setY( y2 );
                        stateContainer.state = MotionTweenAnimationState.FINISHED;
                    }
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenXYEasing() {
        return ( 
            double delta, 
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer,
            double x1, 
            double y1, 
            double x2, 
            double y2,
            double startAngle,
            double endAngle,
            double scale,
            double radius,
            double velX,
            double velY,
            double velAngle,
            double velScale,
            double velRadius,
            double velPercentage ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( x1 );
                componentProxy.setY( y1 );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
                boolean stop = false;
                
                if ( easingFunction != null ) {
                    stateContainer.percentage += velPercentage * delta;
                    componentProxy.setX( x1 + ( x2 - x1 ) * easingFunction.apply( stateContainer.percentage ) );
                    componentProxy.setY( y1 + ( y2 - y1 ) * easingFunction.apply( stateContainer.percentage ) );
                    if ( stateContainer.percentage >= 1.0 ) {
                        componentProxy.setX( x2 );
                        componentProxy.setY( y2 );
                        stateContainer.percentage = 1.0;
                        stop = true;
                    }
                } else {
                    componentProxy.setX( componentProxy.getX() + delta * velX );
                    componentProxy.setY( componentProxy.getY() + delta * velY );
                    if ( componentProxy.getX() >= x2 ) {
                        componentProxy.setX( x2 );
                        stop = true;
                    }
                    if ( componentProxy.getY() >= y2 ) {
                        componentProxy.setY( y2 );
                        stop = true;
                    }
                }
                
                if ( stop ) {
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
                
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenXSimple() {
        return ( 
            double delta, 
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer,
            double x1, 
            double y1, 
            double x2, 
            double y2,
            double startAngle,
            double endAngle,
            double scale,
            double radius,
            double velX,
            double velY,
            double velAngle,
            double velScale,
            double velRadius,
            double velPercentage ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( x1 );
                componentProxy.setY( y1 );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                componentProxy.setX( componentProxy.getX() + delta * velX );
                if ( componentProxy.getX() >= x2 ) {
                    componentProxy.setX( x2 );
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenYSimple() {
        return ( 
            double delta, 
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer,
            double x1, 
            double y1, 
            double x2, 
            double y2,
            double startAngle,
            double endAngle,
            double scale,
            double radius,
            double velX,
            double velY,
            double velAngle,
            double velScale,
            double velRadius,
            double velPercentage ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( x1 );
                componentProxy.setY( y1 );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                componentProxy.setY( componentProxy.getY() + delta * velY );
                if ( componentProxy.getY() >= y2 ) {
                    componentProxy.setY( y2 );
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
            }
            
        };
    }
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenXYSimple() {
        return ( 
            double delta, 
            ComponentProxy<ComponentType> componentProxy, 
            DoubleFunction<Double> easingFunction,
            MotionTweenAnimationStateContainer stateContainer,
            double x1, 
            double y1, 
            double x2, 
            double y2,
            double startAngle,
            double endAngle,
            double scale,
            double radius,
            double velX,
            double velY,
            double velAngle,
            double velScale,
            double velRadius,
            double velPercentage ) -> {
            
            if ( stateContainer.state == MotionTweenAnimationState.INITIALIZED ) {
                stateContainer.state = MotionTweenAnimationState.RUNNING;
                componentProxy.setX( x1 );
                componentProxy.setY( y1 );
            }
            
            if ( stateContainer.state == MotionTweenAnimationState.RUNNING ) {
                
                componentProxy.setX( componentProxy.getX() + delta * velX );
                componentProxy.setY( componentProxy.getY() + delta * velY );
                boolean stop = false;
                
                if ( componentProxy.getX() >= x2 ) {
                    componentProxy.setX( x2 );
                    stop = true;
                }
                
                if ( componentProxy.getY() >= y2 ) {
                    componentProxy.setY( y2 );
                    stop = true;
                }
                
                if ( stop ) {
                    stateContainer.state = MotionTweenAnimationState.FINISHED;
                }
                
            }
            
        };
    }
    
}
