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
import br.com.davidbuzatto.jsge.animation.tween.TweenAnimationComponentMapper;
import br.com.davidbuzatto.jsge.animation.tween.timing.TimingTweenAnimationUpdateFunction;

/**
 * Uma fábrica de funções de atualização para as animações interpoladas do
 * exemplo de animações.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface TimingFunctionsFactory {
    
    /**
     * Fabrica uma função de interpolação em x.
     * 
     * @param <ComponentType> Tipo do componente.
     * @return A função de interpolação.
     */
    public static <ComponentType> TimingTweenAnimationUpdateFunction<ComponentType> tweenTime() {
        return ( 
            double delta, 
            double total,
            TweenAnimationProperties p,
            TweenAnimationComponentMapper<ComponentType> cm, 
            TweenAnimationStateContainer sc ) -> {
            
            if ( sc.state == AnimationExecutionState.INITIALIZED ) {
                sc.state = AnimationExecutionState.RUNNING;
                cm.set( "x", p.getDouble( "x" ) );
                cm.set( "y", p.getDouble( "y" ) );
                cm.set( "radius", p.getDouble( "radius" ) );
                cm.set( "angle", p.getDouble( "angle" ) );
            }
            
            if ( sc.state == AnimationExecutionState.RUNNING ) {
                sc.executionTime += delta;
                cm.set( "x", (Double) cm.get( "x" ) + p.getDouble( "velX" ) * delta );
                cm.set( "y", p.getDouble( "y" ) + 50 * Math.sin( Math.toRadians( (Double) cm.get( "angle" ) ) ) );
                cm.set( "angle", (Double) cm.get( "angle" ) + p.getDouble( "velAngle" ) * delta );
                cm.set( "radius", (Double) cm.get( "radius" ) + p.getDouble( "velRadius" ) * delta );
                if ( sc.executionTime >= total ) {
                    sc.state = AnimationExecutionState.FINISHED;
                    sc.executionTime = total;
                }
                
            }
            
        };
    }
    
}
