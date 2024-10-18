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

import br.com.davidbuzatto.jsge.core.Engine;
import br.com.davidbuzatto.jsge.geom.Point;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Vector2;
import br.com.davidbuzatto.jsge.utils.CollisionUtils;
import br.com.davidbuzatto.jsge.utils.ColorUtils;
import br.com.davidbuzatto.jsge.utils.MathUtils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Exemplo de geração de partículas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ParticlesExample extends Engine {
    
    public static final double GRAVITY = 50;
    private List<Particle> particles;
    private Rectangle limits;
    
    public ParticlesExample() {
        super( 800, 450, "Particles", 60, true );
    }
    
    @Override
    public void create() {
        particles = new CopyOnWriteArrayList<>();
        limits = new Rectangle( 0, 0, getScreenWidth(), getScreenHeight() ) ;
    }
    
    @Override
    public void update() {
        
        double delta = getFrameTime();
        Point mousePos = getMousePositionPoint();
        
        if ( isMouseButtonDown( MOUSE_BUTTON_LEFT ) && CollisionUtils.checkCollisionPointRectangle( mousePos, limits ) ) {
            for ( int i = 0; i < 20; i++ ) {
                Particle p = new Particle(
                    new Vector2( mousePos.x, mousePos.y ),
                    new Vector2( MathUtils.getRandomValue( -200, 200 ), MathUtils.getRandomValue( -200, 200 ) ),
                    MathUtils.getRandomValue( 2, 6 ),
                    0.99,
                    0.8,
                    ColorUtils.colorFromHSV( MathUtils.getRandomValue( 0, 30 ), 1, 1 )
                );
                particles.add( p );
            }
        }

        for ( Particle p : particles ) {
            p.update( delta, limits );
        }
        
    }
    
    @Override
    public void draw() {

        clearBackground( BLACK );
        setFontStyle( FONT_BOLD );
        
        drawText( "Particles: " + particles.size(), 10, 10, 20, WHITE );

        for ( Particle p : particles ) {
            p.draw( this );
        }

    }

    public static void main( String[] args ) {
        new ParticlesExample();
    }

}