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
package br.com.davidbuzatto.jsge.examples.particles;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Classe que representa uma partícula.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Particle {

    public Vector2 pos;
    public Vector2 vel;
    public double radius;
    public double friction;
    public double elasticity;
    public Color color;
    
    private static final double MAX_FALL_SPEED = 500;

    /**
     * Cria uma partícula do exemplo.
     * @param pos Posição.
     * @param vel Velocidade.
     * @param radius Raio.
     * @param friction Atrito.
     * @param elasticity Elasticidade.
     * @param color Cor.
     */
    public Particle( Vector2 pos, Vector2 vel, double radius, double friction, double elasticity, Color color ) {
        this.pos = pos;
        this.vel = vel;
        this.radius = radius;
        this.friction = friction;
        this.elasticity = elasticity;
        this.color = color;
    }
    
    /**
     * Atualiza uma partícula.
     * 
     * @param delta Variação do tempo.
     */
    void update( double delta ) {

        pos.x += vel.x * delta;
        pos.y += vel.y * delta;

        vel.x = vel.x * friction;
        vel.y = vel.y * friction + ParticlesExample.GRAVITY;
        
        if ( vel.y > MAX_FALL_SPEED ) {
            vel.y = MAX_FALL_SPEED;
        }

    }
    
    /**
     * Desenha uma partícula.
     * 
     * @param engine EngineFrame.
     */
    void draw( EngineFrame engine ) {
        engine.fillCircle( pos, radius, color );
    }

}
