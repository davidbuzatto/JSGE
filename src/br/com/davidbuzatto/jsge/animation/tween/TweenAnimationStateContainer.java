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

import br.com.davidbuzatto.jsge.animation.AnimationExecutionState;

/**
 * Contém o estado de uma animação de interpolada que consiste no estado de
 * execução e na porcentagem da execução.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TweenAnimationStateContainer {
    
    /** O estado de execução da animação. */
    public AnimationExecutionState state;
    
    /** A porcentagem de execução da animação. Varia de 0 a 1. */
    public double percentage;
    
    /** O tempo que passou do início da animação até o momento atual. */
    public double executionTime;
    
    /**
     * Cria um novo container do estado da animação.
     * 
     * @param initialState Estado inicial.
     */
    public TweenAnimationStateContainer( AnimationExecutionState initialState ) {
        this.state = initialState;
        this.percentage = 0.0;
        this.executionTime = 0.0;
    }
    
}
