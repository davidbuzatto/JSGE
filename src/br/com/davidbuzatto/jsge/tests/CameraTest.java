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
package br.com.davidbuzatto.jsge.tests;

import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.Engine;
import br.com.davidbuzatto.jsge.geom.Point;
import br.com.davidbuzatto.jsge.geom.Vector2;
import br.com.davidbuzatto.jsge.utils.MathUtils;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class CameraTest extends Engine {

    public CameraTest() {
        super( 800, 450, "Test", 60, true );
    }
    
    private class Player {
        
        double x;
        double y;
        double width;
        double height;
        Vector2 vel;
        double speed;

        public Player( double x, double y, double width, double height, Vector2 vel, double speed ) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.vel = vel;
            this.speed = speed;
        }
        
        void update( double delta ) {
            
            if ( isKeyDown( KEY_LEFT ) ) {
                vel.x = -speed;
            } else if ( isKeyDown( KEY_RIGHT ) ) {
                vel.x = speed;
            } else {
                vel.x = 0;
            }
            
            if ( isKeyDown( KEY_UP ) ) {
                vel.y = -speed;
            } else if ( isKeyDown( KEY_DOWN ) ) {
                vel.y = speed;
            } else {
                vel.y = 0;
            }
            
            x += vel.x * delta;
            y += vel.y * delta;
            
        }
        
        void draw() {
            fillRectangle( x - width / 2, y - height / 2, width, height, BLUE );
        }
        
    }
    
    private Player player;
    private Camera2D camera;
    
    
    @Override
    public void create() {
        player = new Player( 
                getScreenWidth() / 2, getScreenHeight() / 2, 50, 50,
                new Vector2(),
                200
        );
        camera = new Camera2D();
    }

    @Override
    public void update() {
        
        double delta = getFrameTime();
        player.update( delta );
        
        camera.target.x = player.x;
        camera.target.y = player.y;
        camera.offset.x = getScreenWidth() / 2;
        camera.offset.y = getScreenHeight() / 2;
        camera.zoom = 1.0;
        camera.rotation = 0;
        
    }
    
    @Override
    public void draw() {
        
        fillRectangle( 10, 10, 10, 10, BLACK );
        
        beginMode2D( camera );
        
        fillRectangle( 10, 10, 100, 100, RED );
        fillCircle( getScreenWidth() - 60, 60, 50, GREEN );
        fillRing( 60, getScreenHeight() - 60, 20, 50, 0, 270, ORANGE );
        fillPolygon( getScreenWidth() - 60, getScreenHeight() - 60, 5, 50, 0, VIOLET );
        
        player.draw();
        
        Point pWorld = MathUtils.getScreenToWorld2D( new Vector2( 200, 200 ), camera );
        fillCircle( pWorld.x, pWorld.y, 10, LIME );
        drawText( "Screen to world", pWorld.x, pWorld.y, 10, BLACK );
        
        endMode2D();
        
        Point pScreen = MathUtils.getWorldToScreen2D( new Vector2( player.x, player.y ), camera );
        fillCircle( pScreen.x, pScreen.y, 10, ORANGE );
        drawText( "World to screen", pScreen.x, pScreen.y, 10, BLACK );
        
        fillRectangle( 10, getScreenHeight() - 20, 10, 10, BLACK );
        
    }
    
    public static void main( String[] args ) {
        new CameraTest();
    }
    
}
