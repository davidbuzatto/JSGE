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

import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.Color;

/**
 * Exemplo de uso da câmera.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CameraExample extends EngineFrame {

    private Rectangle worldBoundary;
    private Rectangle playerBoundary;
    private Player player;
    private Camera2D camera;
    
    /**
     * Cria o exemplo.
     */
    public CameraExample() {
        super( 800, 600, "Camera", 60, true );
    }
    
    @Override
    public void create() {
        
        worldBoundary = new Rectangle( 0, 0, 1200, 720 );
        playerBoundary = new Rectangle( 30, 30, worldBoundary.width - 60, worldBoundary.height - 60 );
        
        player = new Player( 
                new Vector2( worldBoundary.width / 2, worldBoundary.height / 2 ),
                new Vector2( 50, 50 ),
                400,
                BLUE
        );
        
        camera = new Camera2D();
        
    }

    @Override
    public void update() {
        
        player.update( getFrameTime(), playerBoundary, this );
        
        if ( isKeyDown( KEY_DELETE ) ) {
            camera.rotation--;
        } else if ( isKeyDown( KEY_PAGE_DOWN ) ) {
            camera.rotation++;
        }
        
        if ( isKeyDown( KEY_KP_ADD ) || isKeyDown( KEY_EQUAL ) ) {
            camera.zoom += 0.01;
        } else if ( isKeyDown( KEY_KP_SUBTRACT ) || isKeyDown( KEY_MINUS ) ) {
            camera.zoom -= 0.01;
            if ( camera.zoom < 0.1 ) {
                camera.zoom = 0.1;
            }
        }
        
        if ( isKeyPressed( KEY_R ) ) {
            camera.rotation = 0;
            camera.zoom = 1;
            player.pos.x = worldBoundary.width / 2;
            player.pos.y = worldBoundary.height / 2;
        }
        
        updateCamera();
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        setFontSize( 16 );
        setFontStyle( FONT_BOLD );
        
        beginMode2D( camera );
        
        worldBoundary.fill( this, ORANGE );
        playerBoundary.fill( this, WHITE );
        drawGrid();
        player.draw( this );
        
        endMode2D();
        
        drawInfo();
        
    }
    
    private void drawGrid() {
        
        int step = 30;
        
        int xStart = (int) playerBoundary.x;
        int xEnd = (int) ( playerBoundary.x + playerBoundary.width );
        int yStart = (int) playerBoundary.y;
        int yEnd = (int) ( playerBoundary.y + playerBoundary.height );
        
        int lines = ( yEnd - yStart ) / step;
        int columns = ( xEnd - xStart ) / step;
        
        Color c;
        
        for ( int i = 0; i < lines; i++ ) {
            for ( int j = 0; j < columns; j++ ) {
                if ( i % 2 == 0 ) {
                    if ( j % 2 == 0 ) {
                        c = GRAY;
                    } else {
                        c = LIGHTGRAY;
                    }
                } else {
                    if ( j % 2 == 0 ) {
                        c = LIGHTGRAY;
                    } else {
                        c = GRAY;
                    }
                }
                fillRectangle( 
                        xStart + j * step, 
                        yStart + i * step, 
                        step, step, 
                        ColorUtils.colorTint( 
                                c, 
                                ColorUtils.colorFromHSV( 
                                        45 + i / (double) lines * 210, 1, 1
                                )
                        )
                );
            }
        }
        
    }
    
    private void drawInfo() {
        
        fillRoundRectangle( 5, 5, 340, 200, 10, ColorUtils.colorAlpha( RAYWHITE, 0.5 ) );
        drawRoundRectangle( 5, 5, 340, 200, 10, BLACK );
        
        drawFPS( 20, 20 );
        drawText( "<R> to reset", 210, 20, BLACK );
        
        int y = 40;
        int step = 18;
        
        Vector2 playerScreen = camera.getWorldToScreen( player.pos.x, player.pos.y );
        drawText( "Player: ", 20, y, BLACK );
        drawText( String.format( " World: (%.2f, %.2f)", player.pos.x, player.pos.y ), 30, y += step, BLACK );
        drawText( String.format( "Screen: (%.2f, %.2f)", playerScreen.x, playerScreen.y ), 30, y += step, BLACK );
        
        y += step;
        
        drawText( "Camera: ", 20, y += step, BLACK );
        drawText( String.format( "  Target: (%.2f, %.2f)", camera.target.x, camera.target.y ), 30, y += step, BLACK );
        drawText( String.format( "  Offset: (%.2f, %.2f)", camera.offset.x, camera.offset.y ), 30, y += step, BLACK );
        drawText( String.format( "Rotation: %.2f (DEL/PG-Down)", camera.rotation ), 30, y += step, BLACK );
        drawText( String.format( "    Zoom: %.2f (+/-)", camera.zoom ), 30, y += step, BLACK );
        
    }
    
    private void updateCamera() {
        
        if ( player.pos.x <= getScreenWidth() / 2 ) {
            camera.target.x = getScreenWidth() / 2;
        } else if ( player.pos.x >= worldBoundary.width - getScreenWidth() / 2 ) {
            camera.target.x = worldBoundary.width - getScreenWidth() / 2 ;
        } else {
            camera.target.x = player.pos.x;
        }
        
        if ( player.pos.y <= getScreenHeight() / 2 ) {
            camera.target.y = getScreenHeight()/ 2;
        } else if ( player.pos.y >= worldBoundary.height - getScreenHeight()/ 2 ) {
            camera.target.y = worldBoundary.height - getScreenHeight()/ 2 ;
        } else {
            camera.target.y = player.pos.y;
        }
        
        camera.offset.x = getScreenWidth() / 2;
        camera.offset.y = getScreenHeight() / 2;
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new CameraExample();
    }
    
}
