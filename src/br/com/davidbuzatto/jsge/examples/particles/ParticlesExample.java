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
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * Exemplo de geração de partículas.
 * Várias partes do código dos componentes não estão encapsuladas de propósito.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ParticlesExample extends EngineFrame {
    
    /**
     * Constante para a gravidade da simulação.
     */
    public static final double GRAVITY = 50;
    
    private ParticleEmitter peMoveSin;
    private ParticleEmitter peMouseDown;
    private ParticleEmitter peStaticRight;
    private ParticleEmitter peStaticTop;
    private List<ParticleEmitter> emitters;

    private int newObstaclePos;
    private int obstacleQuantity;
    private int maxObstacles;
    private Obstacle[] obstacles;
    
    private double timeToNextObstacle = 0.1;
    private double nextObstacleCounter = 0.0;
    private boolean showInfo = true;
    
    /**
     * Cria o exemplo.
     */
    public ParticlesExample() {
        super( 800, 450, "Particles", 60, true );
    }
    
    @Override
    public void create() {
        
        peMoveSin = new ParticleEmitter( 
            new Vector2( 40.0, 40.0 ),
            new Vector2( 150.0, 100.0 ),
            0.0,
            200.0,
            200.0,
            0.0,
            false,
            1000
        );

        peMouseDown = new ParticleEmitter(
            new Vector2(),
            new Vector2(),
            0.0,
            0.0,
            200.0,
            0.0,
            false,
            1000
        );

        peStaticRight = new ParticleEmitter( 
            new Vector2( 40.0, getScreenHeight() / 2 ),
            new Vector2(),
            90.0,
            0.0,
            200.0,
            10.0,
            true,
            1000
        );

        peStaticTop = new ParticleEmitter(
            new Vector2( getScreenWidth() * 0.75, getScreenHeight() - 40 ),
            new Vector2(),
            180.0,
            0.0,
            200.0,
            10.0,
            true,
            1000
        );
        
        emitters = new ArrayList<>();
        emitters.add( peMoveSin );
        emitters.add( peMouseDown );
        emitters.add( peStaticRight );
        emitters.add( peStaticTop );

        newObstaclePos = 0;
        obstacleQuantity = 0;
        maxObstacles = 400;
        obstacles = new Obstacle[400];
    
    }
    
    @Override
    public void update() {
        
        double delta = getFrameTime();
        Vector2 mousePos = getMousePositionPoint();

        boolean d1 = peStaticRight.resolveParticleEmitterMouseOperations( this );
        boolean d2 = peStaticTop.resolveParticleEmitterMouseOperations( this );

        peMoveSin.emitParticleColorIntervalQuantity( 
            0, 150,
            50, 50,
            true, false,
            2, 6,
            180.0, 240.0,
            5
        );

        if ( !d1 && !d2 && isMouseButtonDown( MOUSE_BUTTON_LEFT ) ) {
            peMouseDown.emitParticlePolarPositionColorIntervalQuantity( 
                mousePos,
                100, 200,
                0, 200, true,
                2, 6,
                0.0, 60.0,
                5
            );
        }

        peStaticRight.emitParticlePolarColorIntervalQuantity( 
            300, 500,
            0, 20, true,
            2, 6,
            75.0, 165.0, 
            5
        );

        peStaticTop.emitParticlePolarColorIntervalQuantity( 
            400, 1000,
            0, 8, true,
            1, 3,
            270.0, 330.0, 
            5
        );

        peMoveSin.updateMoveSin( delta, this );
        peMouseDown.updateStatic( delta );
        peStaticRight.updateStatic( delta );
        peStaticTop.updateStatic( delta );

        if ( isMouseButtonDown( MOUSE_BUTTON_RIGHT ) ) {
            createObstacle( delta, mousePos );
        }

        if ( isKeyPressed( KEY_F1 ) ) {
            showInfo = !showInfo;
        }

        if ( isKeyPressed( KEY_F2 ) ) {
            resetObstacles();
        }

        peMoveSin.resolveParticlesObstaclesCollision( obstacles, obstacleQuantity );
        peMouseDown.resolveParticlesObstaclesCollision( obstacles, obstacleQuantity );
        peStaticRight.resolveParticlesObstaclesCollision( obstacles, obstacleQuantity );
        peStaticTop.resolveParticlesObstaclesCollision( obstacles, obstacleQuantity );
        
    }
    
    @Override
    public void draw() {

        clearBackground( BLACK );
        setFontStyle( FONT_BOLD );

        peMoveSin.draw( this );
        peMouseDown.draw( this );
        peStaticRight.draw( this );
        peStaticTop.draw( this );

        for ( int i = 0; i < obstacleQuantity; i++ ) {
            obstacles[i].draw( this );
        }

        if ( showInfo ) {
            drawFPS( 20, 20 );
            int y = 20;
            drawText( String.format( "particles (moving): %d", peMoveSin.particleQuantity ), 20, y += 20, 20, WHITE );
            drawText( String.format( "particles (mouse): %d", peMouseDown.particleQuantity ), 20, y += 20, 20, WHITE );
            drawText( String.format( "particles (static left): %d", peStaticRight.particleQuantity ), 20, y += 20, 20, WHITE );
            drawText( String.format( "particles (static right): %d", peStaticTop.particleQuantity ), 20, y += 20, 20, WHITE );
            drawText( String.format( "obstacles: %d", obstacleQuantity ), 20, (y += 20), 20, WHITE );
            drawText( "<F1>: show/hide this info", 20, (y += 20), 20, WHITE );
            drawText( "<F2>: reset obstacles", 20, (y += 20), 20, WHITE );
        }

    }
    
    private void createObstacle( double delta, Vector2 pos ) {

        nextObstacleCounter += delta;

        if ( nextObstacleCounter >= timeToNextObstacle ) {

            nextObstacleCounter = 0;

            int k = newObstaclePos % maxObstacles;

            pos.x -= 10.0;
            pos.y -= 10.0;

            obstacles[k] = new Obstacle( 
                new Rectangle( pos.x, pos.y, 20, 20 ),
                RAYWHITE
            );

            newObstaclePos++;

            if ( obstacleQuantity < maxObstacles ) {
                obstacleQuantity++;
            }

        }

    }
    
    private void resetObstacles() {
        newObstaclePos = 0;
        obstacleQuantity = 0;
    }

    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new ParticlesExample();
    }

}