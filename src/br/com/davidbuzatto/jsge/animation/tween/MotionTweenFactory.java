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

/**
 * Uma fábrica de interpoladores de movimento.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MotionTweenFactory {
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenX() {
        return ( 
                double delta, 
                ComponentProxy<ComponentType> componentProxy, 
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
                double velRadius ) -> {
            componentProxy.setX( componentProxy.getX() + delta * velX );
        };
    }
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenY() {
        return ( 
                double delta, 
                ComponentProxy<ComponentType> componentProxy, 
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
                double velRadius ) -> {
            componentProxy.setY( componentProxy.getY() + delta * velX );
        };
    }
    
    public static <ComponentType> MotionTweenConsumer<ComponentType> tweenXY() {
        return ( 
                double delta, 
                ComponentProxy<ComponentType> componentProxy, 
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
                double velRadius ) -> {
            componentProxy.setX( componentProxy.getX() + delta * velX );
            componentProxy.setY( componentProxy.getY() + delta * velY );
        };
    }
    
}
