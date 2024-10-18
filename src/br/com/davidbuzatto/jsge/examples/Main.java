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
package br.com.davidbuzatto.jsge.examples;

import br.com.davidbuzatto.jsge.core.Engine;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Point;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.geom.Vector2;
import br.com.davidbuzatto.jsge.utils.CollisionUtils;
import br.com.davidbuzatto.jsge.utils.ColorUtils;
import br.com.davidbuzatto.jsge.utils.MathUtils;
import br.com.davidbuzatto.jsge.utils.Utils;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Exemplos de utilização da engine.
 * 
 * Todas as classes necessárias estão implementadas como classes internas para
 * que o exemplo seja auto-contido.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Main extends Engine {
    
    private class Bolinha {

        Vector2 pos;
        Vector2 posAnt;
        Vector2 vel;
        Color cor;
        double raio;
        double atrito;
        double elasticidade;
        boolean arrastando;

        void desenhar() {
            fillCircle( pos, raio, cor );
        }

        void atualizar( double delta, int width, int height ) {

            if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
                if ( CollisionUtils.checkCollisionPointCircle( getMousePositionPoint(), pos, raio ) ) {
                    arrastando = true;
                    xOffset = pos.x - getMouseX();
                    yOffset = pos.y - getMouseY();
                }
            } else if ( isMouseButtonReleased( MOUSE_BUTTON_LEFT ) ) {
                arrastando = false;
            }
    
            if ( !arrastando ) {
    
                pos.x += vel.x * delta;
                pos.y += vel.y * delta;
    
                if ( pos.x - raio <= 0 ) {
                    pos.x = raio;
                    vel.x = -vel.x * elasticidade;
                } else if ( pos.x + raio >= width ) {
                    pos.x = width - raio;
                    vel.x = -vel.x * elasticidade;
                }
    
                if ( pos.y - raio <= 0 ) {
                    pos.y = raio;
                    vel.y = -vel.y * elasticidade;
                } else if ( pos.y + raio >= height ) {
                    pos.y = height - raio;
                    vel.y = -vel.y * elasticidade;
                }
    
                vel.x = vel.x * atrito;
                vel.y = vel.y * atrito + GRAVIDADE;
    
            } else {
                pos.x = getMouseX() + xOffset;
                pos.y = getMouseY() + yOffset;
                vel.x = ( pos.x - posAnt.x ) / delta;
                vel.y = ( pos.y - posAnt.y ) / delta;
                posAnt.x = pos.x;
                posAnt.y = pos.y;
            }

        }

    }
    
    private static final double GRAVIDADE = 50;
    private Point mousePos;
    
    // bolinha
    private double xOffset;
    private double yOffset;
    Bolinha bolinha;
    
    
    // imagens
    private Image img;
    private Color cr = new Color( 255, 0, 0, 100 );
    private Color cg = new Color( 0, 255, 0, 100 );
    private Color cb = new Color( 0, 0, 255, 100 );
    
    
    public Main() {
        super( 800, 450, "JSGE - v" + Utils.getVersion(), 60, true );
        setExtendedState( MAXIMIZED_BOTH );
    }
    
    /**
     * Processa a entrada inicial fornecida pelo usuário e cria
     * e/ou inicializa os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void create() {
        
        // bolinha
        bolinha = new Bolinha();
        bolinha.pos = new Vector2( 400, 225 );
        bolinha.posAnt = new Vector2();
        bolinha.vel = new Vector2( 200, 200 );
        bolinha.raio = 30;
        bolinha.atrito = 0.99;
        bolinha.elasticidade = 0.9;
        bolinha.cor = BLUE;
        
        
        // imagens
        img = loadImage( "resources/images/duke.png" );
        
    }

    /**
     * Atualiza os objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void update() {
        
        double delta = getFrameTime();
        mousePos = getMousePositionPoint();        
        
        // bolinha
        bolinha.atualizar( delta, 800, 450 );
        
        
    }

    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     */
    @Override
    public void draw() {

        clearBackground( WHITE );
        setFontStyle( Font.BOLD );
        
        // bolinha
        bolinha.desenhar();
        drawRectangle( 0, 0, 800, 450, BLACK );
        
        // imagens
        drawImage( img, 1700, 10, cg );
        drawImage( img, 1700, 10, 45, cb );
        drawImage( img, 1700, 10, 10, 10, 90, cr );
        
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), 1700, 410, cg );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), 1700, 410, 45, cb );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), 1700, 410, 10, 10, 90 );
        
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 1700, 610, 150, 150 ), cg );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 1700, 610, 150, 150 ), 45, cb );
        drawImage( img, new Rectangle( 0, 0, 100, 100 ), new Rectangle( 1700, 610, 150, 150 ), 10, 10, 90 );
        
        drawFPS( 10, 20 );

    }

    public static void main( String[] args ) {
        new Main();
    }

}