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

/**
 * Contém o estado de uma animação de interpolação de movimento que
 * consiste no estado de execução e na porcentagem da execução.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MotionTweenAnimationStateContainer {
    
    /** O estado de execução da animação. */
    public MotionTweenAnimationExecutionState state;
    
    /** A porcentagem de execução da animação. Varia de 0 a 1.*/
    public double percentage;
    
    /**
     * Cria um novo container do estado da animação.
     * @param initialState 
     */
    public MotionTweenAnimationStateContainer( MotionTweenAnimationExecutionState initialState ) {
        this.state = initialState;
        this.percentage = 0.0;
    }
    
}
