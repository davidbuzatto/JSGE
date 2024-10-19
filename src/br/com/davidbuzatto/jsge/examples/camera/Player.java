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

import br.com.davidbuzatto.jsge.core.Engine;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Vector2;
import java.awt.Color;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Player {
    
    private Vector2 pos;
    private Vector2 dim;
    private Vector2 vel;
    private double speed;
    private Color color;

    public Player( Vector2 pos, Vector2 dim, double speed, Color color ) {
        this.pos = pos;
        this.dim = dim;
        this.vel = new Vector2();
        this.speed = speed;
        this.color = color;
    }
    
    public void update( double delta, Rectangle boundary, Engine engine ) {
        
        if ( engine.isKeyDown( Engine.KEY_LEFT ) ) {
            vel.x = -speed;
        } else if ( engine.isKeyDown( Engine.KEY_RIGHT ) ) {
            vel.x = speed;
        } else {
            vel.x = 0;
        }
        
        if ( engine.isKeyDown( Engine.KEY_UP ) ) {
            vel.y = -speed;
        } else if ( engine.isKeyDown( Engine.KEY_DOWN ) ) {
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
    
    public void draw( Engine engine ) {
        engine.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, color );
        engine.drawRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, Engine.BLACK );
    }

    public Vector2 getPos() {
        return pos;
    }
    
}