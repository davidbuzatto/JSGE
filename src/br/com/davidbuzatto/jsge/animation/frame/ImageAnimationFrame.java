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
package br.com.davidbuzatto.jsge.animation.frame;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.image.Image;
import java.awt.Color;

/**
 * Um quadro de animação baseado em imagem.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ImageAnimationFrame extends AnimationFrame {
    
    /**
     * A imagem do quadro de animação.
     */
    public final Image image;
    
    /**
     * Cria um quadro de animação de imagem.
     * 
     * @param image Imagem do quadro.
     */
    public ImageAnimationFrame( Image image ) {
        this.image = image;
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     */
    public void draw( EngineFrame engine, double x, double y ) {
        engine.drawImage( image, x, y );
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @bgColor Cor de fundo.
     */
    public void draw( EngineFrame engine, double x, double y, Color bgColor ) {
        engine.drawImage( image, x, y, bgColor );
    }
    
}
