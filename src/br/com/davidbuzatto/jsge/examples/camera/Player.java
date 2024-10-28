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
package br.com.davidbuzatto.jsge.examples.camera;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Modelo de um jogador para o exemplo de uso da câmera.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Player {
    
    /**
     * Posição do jogador.
     */
    public Vector2 pos;
    private Vector2 dim;
    private Vector2 vel;
    private double speed;
    private Color color;

    /**
     * Cria o jogador do exemplo da câmera.
     * 
     * @param pos Posição.
     * @param dim Dimensão.
     * @param speed Velocidade.
     * @param color Cor.
     */
    public Player( Vector2 pos, Vector2 dim, double speed, Color color ) {
        this.pos = pos;
        this.dim = dim;
        this.vel = new Vector2();
        this.speed = speed;
        this.color = color;
    }
    
    /**
     * Atualiza o jogador.
     * 
     * @param delta Variação do tempo.
     * @param boundary Limites.
     * @param engine EngineFrame.
     */
    public void update( double delta, Rectangle boundary, EngineFrame engine ) {
        
        if ( engine.isKeyDown(EngineFrame.KEY_LEFT ) ) {
            vel.x = -speed;
        } else if ( engine.isKeyDown(EngineFrame.KEY_RIGHT ) ) {
            vel.x = speed;
        } else {
            vel.x = 0;
        }
        
        if ( engine.isKeyDown(EngineFrame.KEY_UP ) ) {
            vel.y = -speed;
        } else if ( engine.isKeyDown(EngineFrame.KEY_DOWN ) ) {
            vel.y = speed;
        } else {
            vel.y = 0;
        }
        
        pos.x += vel.x * delta;
        pos.y += vel.y * delta;
        
        if ( pos.x - dim.x / 2 <= boundary.x ) {
            pos.x = boundary.x + dim.x / 2;
        } else if ( pos.x + dim.x / 2 >= boundary.x + boundary.width ) {
            pos.x = boundary.x + boundary.width - dim.x / 2;
        }
        
        if ( pos.y - dim.y / 2 <= boundary.y ) {
            pos.y = boundary.y + dim.y / 2;
        } else if ( pos.y + dim.y / 2 >= boundary.y + boundary.height ) {
            pos.y = boundary.y + boundary.height - dim.y / 2;
        }
        
    }
    
    /**
     * Desenha o jogador.
     * 
     * @param engine EngineFrame.
     */
    public void draw( EngineFrame engine ) {
        engine.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, color );
        engine.drawRectangle(pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, EngineFrame.BLACK );
    }
    
}
