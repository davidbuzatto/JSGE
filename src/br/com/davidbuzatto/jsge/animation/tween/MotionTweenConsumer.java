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
 * Interface funcional para os consumidores dos interpoladores de movimento.
 * 
 * @param <ComponentType> Tipo do componente.
 * @author Prof. Dr. David Buzatto
 */
@FunctionalInterface
public interface MotionTweenConsumer<ComponentType> {
    
    public void accept( 
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
            double velPercentage );
    
}
