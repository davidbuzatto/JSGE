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

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.CollisionUtils;
import java.awt.Color;

/**
 * Representação de uma bola com propriedades físicas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Ball {
    
    private Vector2 pos;
    private Vector2 prevPos;
    private Vector2 vel;
    private double radius;
    private double friction;
    private double alasticity;
    private boolean dragging;
    private Color color;
    
    private double xOffset;
    private double yOffset;

    /**
     * Cria uma bolinha.
     * 
     * @param pos posição.
     * @param vel verlocidade.
     * @param radius raio.
     * @param friction atrito.
     * @param alasticity elasticidade.
     * @param color cor.
     */
    public Ball( Vector2 pos, Vector2 vel, double radius, double friction, double alasticity, Color color ) {
        this.pos = pos;
        this.prevPos = new Vector2();
        this.vel = vel;
        this.radius = radius;
        this.friction = friction;
        this.alasticity = alasticity;
        this.color = color;
    }

    /**
     * Atualiza a bolinha.
     * 
     * @param delta variação do tempo.
     * @param engine engine.
     */
    void update( double delta, EngineFrame engine ) {

        if ( engine.isMouseButtonPressed( engine.MOUSE_BUTTON_LEFT ) ) {
            if ( CollisionUtils.checkCollisionPointCircle( engine.getMousePositionPoint(), pos, radius ) ) {
                dragging = true;
                xOffset = pos.x - engine.getMouseX();
                yOffset = pos.y - engine.getMouseY();
            }
        } else if ( engine.isMouseButtonReleased( engine.MOUSE_BUTTON_LEFT ) ) {
            dragging = false;
        }

        if ( !dragging ) {

            pos.x += vel.x * delta;
            pos.y += vel.y * delta;

            if ( pos.x - radius <= 0 ) {
                pos.x = radius;
                vel.x = -vel.x * alasticity;
            } else if ( pos.x + radius >= engine.getScreenWidth() ) {
                pos.x = engine.getScreenWidth() - radius;
                vel.x = -vel.x * alasticity;
            }

            if ( pos.y - radius <= 0 ) {
                pos.y = radius;
                vel.y = -vel.y * alasticity;
            } else if ( pos.y + radius >= engine.getScreenHeight() ) {
                pos.y = engine.getScreenHeight() - radius;
                vel.y = -vel.y * alasticity;
            }

            vel.x = vel.x * friction;
            vel.y = vel.y * friction + BouncingBallExample.GRAVITY;

        } else {
            pos.x = engine.getMouseX() + xOffset;
            pos.y = engine.getMouseY() + yOffset;
            vel.x = ( pos.x - prevPos.x ) / delta;
            vel.y = ( pos.y - prevPos.y ) / delta;
            prevPos.x = pos.x;
            prevPos.y = pos.y;
        }

    }
    
    /**
     * Desenha a bolinha.
     * 
     * @param engine engine.
     */
    void draw( EngineFrame engine ) {
        engine.fillCircle( pos, radius, color );
    }
        
}
