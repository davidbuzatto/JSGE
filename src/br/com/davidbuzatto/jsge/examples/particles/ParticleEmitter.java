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
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.CollisionUtils;
import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.Color;

/**
 * Um emissor de partículas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ParticleEmitter {
    
    public Vector2 pos;
    public Vector2 vel;
    
    public double launchAngle;
    public double posAngle;
    public double posAngleVel;
    public double hueAngle;
    public double hueAngleVel;
    
    public double radius;
    public boolean dragging;
    public boolean draggable;
    public boolean mouseOver;
    
    public int newParticlePos;
    public int particleQuantity;
    public Particle[] particles;
    
    private double xOffset;
    private double yOffset;
    
    private static final double PE_RANDOM_MULTIPLIER = 1000.0;
    
    public ParticleEmitter( Vector2 pos, Vector2 vel, double launchAngle, double posAngleVel, double hueAngleVel, double radius, boolean draggable, int maxParticles ) {
        this.pos = pos;
        this.vel = vel;
        this.launchAngle = launchAngle;
        this.posAngle = 0.0;
        this.posAngleVel = posAngleVel;
        this.hueAngle = 0.0;
        this.hueAngleVel = hueAngleVel;
        this.radius = radius;
        this.draggable = draggable;
        this.newParticlePos = 0;
        this.particleQuantity = 0;
        this.particles = new Particle[maxParticles];
    }
    
    public void draw( EngineFrame engine ) {

        if ( draggable && mouseOver ) {
            engine.fillCircle(pos, radius, ColorUtils.fade(EngineFrame.RAYWHITE, 0.5 ) );    
            engine.drawCircle(pos, radius, EngineFrame.RAYWHITE );
        }

        for ( int i = 0; i < particleQuantity; i++ ) {
            particles[i].draw( engine );
        }

    }

    public void updateMoveSin( double delta, EngineFrame engine ) {

        pos.x += vel.x * delta;
        pos.y += vel.y * Math.sin( Math.toRadians( posAngle ) ) * delta;

        posAngle += posAngleVel * delta;
        if ( posAngle > 360.0 ) {
            posAngle = 0.0;
        }

        updateHueAngleBouncing( delta );

        if ( pos.x < 40.0f ) {
            vel.x *= -1.0f;
        } else if ( pos.x >= engine.getScreenWidth() - 40.0f ) {
            vel.x *= -1.0f;
        }

        for ( int i = 0; i < particleQuantity; i++ ) {
            particles[i].update( delta );
        }

    }

    public void updateStatic( double delta ) {

        updateHueAngleBouncing( delta );

        for ( int i = 0; i < particleQuantity; i++ ) {
            particles[i].update( delta );
        }

    }

    public void updateHueAngleBouncing( double delta ) {

        hueAngle += hueAngleVel * delta;
        if ( hueAngle < 0.0f ) {
            hueAngle = 0.0f;
            hueAngleVel *= -1.0f;
        }
        if ( hueAngle > 360.0f ) {
            hueAngle = 360.0f;
            hueAngleVel *= -1.0f;
        }

    }

    public void emitParticle( Vector2 pos, Vector2 vel, double radius, Color color ) {

        int k = newParticlePos % particles.length;

        particles[k] = new Particle( 
            new Vector2( pos.x, pos.y ), 
            vel,
            radius,
            0.99,
            0.9,
            color
        );

        newParticlePos++;

        if ( particleQuantity < particles.length ) {
            particleQuantity++;
        }

    }

    public void emitParticleColorInterval( Vector2 vel, double minRadius, double maxRadius, double startHue, double endHue ) {
        emitParticlePositionColorInterval( pos, vel, minRadius, maxRadius, startHue, endHue );
    }

    public void emitParticlePositionColorInterval( Vector2 pos, Vector2 vel, double minRadius, double maxRadius, double startHue, double endHue ) {
        emitParticle( 
            pos, 
            vel, 
            MathUtils.getRandomValue( (int) (minRadius * PE_RANDOM_MULTIPLIER), (int) (maxRadius * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER,
            ColorUtils.colorFromHSV( 
                MathUtils.lerp( startHue, endHue, hueAngle / 360.0f ), 
                1.0f, 
                1.0f
            )
        );
    }

    public boolean isMouseOverParticleEmitter( Vector2 pePos, double peRadius, Vector2 mousePos ) {

        double c1 = mousePos.x - pePos.x;
        double c2 = mousePos.y - pePos.y;

        return c1 * c1 + c2 * c2 <= peRadius * peRadius;

    }

    public void emitParticleColorIntervalQuantity( 
        double minVelX, double maxVelX, 
        double minVelY, double maxVelY, 
        boolean randomSignX, boolean randomSignY,
        double minRadius, double maxRadius, 
        double startHue, double endHue, 
        int quantity ) {

        for ( int i = 0; i < quantity; i++ ) {
            emitParticleColorInterval( 
                createVel( 
                    MathUtils.getRandomValue( (int) (minVelX * PE_RANDOM_MULTIPLIER), (int) (maxVelX * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER, 
                    MathUtils.getRandomValue( (int) (minVelY * PE_RANDOM_MULTIPLIER), (int) (maxVelY * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER, 
                    randomSignX, randomSignY
                ),
                minRadius, maxRadius,
                startHue, endHue
            );
        }

    }

    public void emitParticlePositionColorIntervalQuantity( 
        Vector2 pos,
        double minVelX, double maxVelX, 
        double minVelY, double maxVelY, 
        boolean randomSignX, boolean randomSignY,
        double minRadius, double maxRadius, 
        double startHue, double endHue, 
        int quantity ) {

        for ( int i = 0; i < quantity; i++ ) {
            emitParticlePositionColorInterval( 
                pos,
                createVel( 
                    MathUtils.getRandomValue( (int) (minVelX * PE_RANDOM_MULTIPLIER), (int) (maxVelX * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER, 
                    MathUtils.getRandomValue( (int) (minVelY * PE_RANDOM_MULTIPLIER), (int) (maxVelY * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER, 
                    randomSignX, randomSignY
                ),
                minRadius, maxRadius,
                startHue, endHue
            );
        }

    }

    public void emitParticlePolarColorIntervalQuantity( 
        double minVel, double maxVel, 
        double minLaunchAngle, double maxLaunchAngle,
        boolean randomSignLaunchAnble,
        double minRadius, double maxRadius, 
        double startHue, double endHue, 
        int quantity ) {

        for ( int i = 0; i < quantity; i++ ) {

            double rVel = MathUtils.getRandomValue( (int) (minVel * PE_RANDOM_MULTIPLIER), (int) (maxVel * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER;
            double launAngleOffset = MathUtils.getRandomValue( (int) (minLaunchAngle * PE_RANDOM_MULTIPLIER), (int) (maxLaunchAngle * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER;

            if ( randomSignLaunchAnble ) {
                if ( MathUtils.getRandomValue( 0, 1 ) == 0 ) {
                    launAngleOffset *= -1.0f;
                }
            }

            emitParticleColorInterval( 
                createVel( 
                    rVel * Math.sin( Math.toRadians( launchAngle + launAngleOffset ) ), 
                    rVel * Math.cos( Math.toRadians( launchAngle + launAngleOffset ) ), 
                    false, false
                ),
                minRadius, maxRadius,
                startHue, endHue
            );
        }

    }

    public void emitParticlePolarPositionColorIntervalQuantity( 
        Vector2 pos,
        double minVel, double maxVel, 
        double minLaunchAngle, double maxLaunchAngle,
        boolean randomSignLaunchAnble,
        double minRadius, double maxRadius, 
        double startHue, double endHue, 
        int quantity ) {

        for ( int i = 0; i < quantity; i++ ) {

            double rVel = MathUtils.getRandomValue( (int) (minVel * PE_RANDOM_MULTIPLIER), (int) (maxVel * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER;
            double launAngleOffset = MathUtils.getRandomValue( (int) (minLaunchAngle * PE_RANDOM_MULTIPLIER), (int) (maxLaunchAngle * PE_RANDOM_MULTIPLIER) ) / PE_RANDOM_MULTIPLIER;

            if ( randomSignLaunchAnble ) {
                if ( MathUtils.getRandomValue( 0, 1 ) == 0 ) {
                    launAngleOffset *= -1.0f;
                }
            }

            emitParticlePositionColorInterval( 
                pos,
                createVel( 
                    rVel * Math.sin( Math.toRadians( launchAngle + launAngleOffset ) ), 
                    rVel * Math.cos( Math.toRadians( launchAngle + launAngleOffset ) ), 
                    false, false
                ),
                minRadius, maxRadius,
                startHue, endHue
            );
        }

    }
    
    private Vector2 createVel( double velX, double velY, boolean randomSignX, boolean randomSignY ) {

        double multX = randomSignX ? ( MathUtils.getRandomValue( 0, 1 ) == 0 ? 1.0 : -1.0 ) : 1.0;
        double multY = randomSignY ? ( MathUtils.getRandomValue( 0, 1 ) == 0 ? 1.0 : -1.0 ) : 1.0;

        return new Vector2(
            velX * multX,
            velY * multY
        );

    }
    
    public boolean resolveParticleEmitterMouseOperations( EngineFrame engine ) {

        Vector2 mousePos = engine.getMousePositionPoint();

        if ( draggable && isMouseOverParticleEmitter( pos, radius, mousePos ) ) {
            mouseOver = true;
            if ( engine.isMouseButtonPressed(EngineFrame.MOUSE_BUTTON_LEFT ) ) {
                dragging = true;
                xOffset = pos.x - mousePos.x;
                yOffset = pos.y - mousePos.y;
            }
        } else {
            mouseOver = false;
        }

        if ( engine.isMouseButtonReleased(EngineFrame.MOUSE_BUTTON_LEFT ) ) {
            dragging = false;
        }

        if ( dragging ) {
            mouseOver = true;
            mousePos.x += xOffset;
            mousePos.y += yOffset;
            pos.x = mousePos.x;
            pos.y = mousePos.y;
        }

        if ( mouseOver ) {
            double m = engine.getMouseWheelMove() * 2;
            launchAngle += m;
        }

        return dragging;

    }
    
    public void resolveParticlesObstaclesCollision( Obstacle[] obstacles, int obstacleQuantity ) {        

        for ( int i = 0; i < particleQuantity; i++ ) {

            Particle p = particles[i];

            for ( int j = 0; j < obstacleQuantity; j++ ) {
                Obstacle o = obstacles[j];
                if ( CollisionUtils.checkCollisionCircleRectangle( p.pos, p.radius, o.topCP ) ) {
                    p.vel.y = -200.f;
                    p.vel.y *= p.elasticity;
                } else if ( CollisionUtils.checkCollisionCircleRectangle( p.pos, p.radius, o.bottomCP ) ) {
                    p.pos.y = o.rect.y + o.rect.height + p.radius;
                    p.vel.y *= p.elasticity;
                } else if ( CollisionUtils.checkCollisionCircleRectangle( p.pos, p.radius, o.leftCP ) ) {
                    p.pos.x = o.rect.x - p.radius;
                    p.vel.x = -Math.abs( p.vel.x );
                    p.vel.x *= p.elasticity;
                } else if ( CollisionUtils.checkCollisionCircleRectangle( p.pos, p.radius, o.rightCP ) ) {
                    p.pos.x = o.rect.x + o.rect.width + p.radius;
                    p.vel.x = Math.abs( p.vel.x );
                    p.vel.x *= p.elasticity;
                }
            }

        }

    }
    
}
