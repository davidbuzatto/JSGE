/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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

import br.com.davidbuzatto.jsge.animation.AnimationExecutionState;

/**
 * Holds the state of a tween animation, consisting of the execution state
 * and the execution percentage.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TweenAnimationStateContainer {

    /** The execution state of the animation. */
    public AnimationExecutionState state;

    /** The execution percentage of the animation. Ranges from 0 to 1. */
    public double percentage;

    /** The time elapsed from the start of the animation to the current moment. */
    public double executionTime;

    /**
     * Creates a new animation state container.
     *
     * @param initialState Initial state.
     */
    public TweenAnimationStateContainer( AnimationExecutionState initialState ) {
        this.state = initialState;
        this.percentage = 0.0;
        this.executionTime = 0.0;
    }
    
}
