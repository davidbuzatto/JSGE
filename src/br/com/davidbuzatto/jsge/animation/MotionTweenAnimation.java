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
package br.com.davidbuzatto.jsge.animation;

import br.com.davidbuzatto.jsge.animation.tween.MotionTweenConsumer;
import br.com.davidbuzatto.jsge.animation.proxy.ComponentProxy;

/**
 * Uma animação de interpolação de movimento.
 * 
 * @param <ComponentType> O tipo do componente que passará pela interpolação.
 * @author Prof. Dr. David Buzatto
 */
public class MotionTweenAnimation<ComponentType> {
    
    private ComponentProxy<ComponentType> componentProxy;
    private MotionTweenConsumer<ComponentType> updateFunction;
    
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double startAngle;
    private double endAngle;
    private double scale;
    private double radius;
    private double velX;
    private double velY;
    private double velAngle;
    private double velScale;
    private double velRadius;
    
    public MotionTweenAnimation( 
            ComponentProxy<ComponentType> proxy, 
            MotionTweenConsumer updateFunction, 
            double x1, 
            double y1, 
            double x2, 
            double y2,
            double velX,
            double velY ) {
        this( proxy, updateFunction, x1, y1, x2, y2, 0, 0, 0, 0, velX, velY, 0, 0, 0 );
    }

    public MotionTweenAnimation( 
            ComponentProxy<ComponentType> proxy, 
            MotionTweenConsumer<ComponentType> updateFunction, 
            double x1, double y1, double x2, double y2, 
            double startAngle, double endAngle, 
            double scale, double radius, 
            double velX, double velY, 
            double velAngle, double velScale, 
            double velRadius ) {
        this.componentProxy = proxy;
        this.updateFunction = updateFunction;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.scale = scale;
        this.radius = radius;
        this.velX = velX;
        this.velY = velY;
        this.velAngle = velAngle;
        this.velScale = velScale;
        this.velRadius = velRadius;
    }
    
    public void update( double delta ) {
        updateFunction.accept( delta, componentProxy, x1, y1, x2, y2, startAngle, endAngle, scale, radius, velX, velY, velAngle, velScale, velRadius );
    }
    
    public ComponentType getComponent() {
        return componentProxy.getComponent();
    }
    
}
