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
package br.com.davidbuzatto.jsge.examples.ball;

import br.com.davidbuzatto.jsge.core.Engine;
import static br.com.davidbuzatto.jsge.core.Engine.BLUE;
import br.com.davidbuzatto.jsge.geom.Vector2;

/**
 * Exemplo de simulação da bolinha.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BouncingBallExample extends Engine {

    public static final double GRAVITY = 50;
    private Ball ball;
    
    public BouncingBallExample() {
        super( 800, 450, "Bouncing Ball", 60, true );
    }
    
    @Override
    
    public void create() {
        ball = new Ball(
                new Vector2( getScreenWidth() / 2, getScreenHeight() / 2 ),
                new Vector2( 200, 200 ),
                50,
                0.99,
                0.9,
                BLUE
        );
    }

    @Override
    public void update() {
        ball.update( getFrameTime(), this );
    }
    
    @Override
    public void draw() {
        ball.draw( this );
    }
    
    public static void main( String[] args ) {
        new BouncingBallExample();
    }
    
}
