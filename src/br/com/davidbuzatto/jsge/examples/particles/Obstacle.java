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
import java.awt.Color;

/**
 * Classe que representa um obstáculo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Obstacle {
    
    /** retângulo */
    public Rectangle rect;
    
    /** sonda de colisão de cima */
    public Rectangle topCP;
    
    /** sonda de colisão de baixo */
    public Rectangle bottomCP;
    
    /** sonda de colisão da esquerda */
    public Rectangle leftCP;
    
    /** sonda de colisão de direita */
    public Rectangle rightCP;
    
    /** cor */
    public Color color;
    
    /**
     * Cria um obstáculo.
     * 
     * @param rect Reângulo.
     * @param color Cor.
     */
    public Obstacle( Rectangle rect, Color color ) {
        
        this.rect = rect;
        this.color = color;
        
        double marginP = 0.1;
        double thicknessP = 0.3;
        
        topCP = new Rectangle(
            rect.x + rect.width * marginP,
            rect.y,
            rect.width * ( 1.0 - marginP * 2 ),
            rect.height * thicknessP
        );

        bottomCP = new Rectangle(
            rect.x + rect.width * marginP,
            rect.y + rect.height - rect.height * thicknessP,
            rect.width * ( 1.0 - marginP * 2 ),
            rect.height * thicknessP
        );

        leftCP = new Rectangle(
            rect.x,
            rect.y + rect.height * marginP,
            rect.width * thicknessP,
            rect.height * ( 1.0 - marginP * 2 )
        );

        rightCP = new Rectangle(
            rect.x + rect.width - rect.width * thicknessP,
            rect.y + rect.height * marginP,
            rect.width * thicknessP,
            rect.height * ( 1.0 - marginP * 2 )
        );
        
    }
    
    /**
     * Desenha o obstáculo.
     * 
     * @param engine A engine.
     */
    public void draw( EngineFrame engine ) {
        engine.fillRectangle( rect, color );
    }
    
}
