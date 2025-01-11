/*
 * Copyright (C) 2025 Prof. Dr. David Buzatto
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
public class ImageAnimationFrame implements AnimationFrame {
    
    /**
     * A imagem do quadro de animação.
     */
    public final Image baseImage;
    
    /**
     * Cria um quadro de animação de imagem.
     * 
     * @param baseImage Imagem do quadro.
     */
    public ImageAnimationFrame( Image baseImage ) {
        this.baseImage = baseImage;
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     */
    public void draw( EngineFrame engine, double x, double y ) {
        engine.drawImage( baseImage, x, y );
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void draw( EngineFrame engine, double x, double y, double rotation ) {
        engine.drawImage( baseImage, x, y, rotation );
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation ) {
        engine.drawImage( baseImage, x, y, originX, originY, rotation );
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param bgColor Cor de fundo.
     */
    public void draw( EngineFrame engine, double x, double y, Color bgColor ) {
        engine.drawImage( baseImage, x, y, bgColor );
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Cor de fundo.
     */
    public void draw( EngineFrame engine, double x, double y, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, x, y, rotation, bgColor );
    }
    
    /**
     * Desenha o quadro de animação.
     * 
     * @param engine A engine.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Cor de fundo.
     */
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, x, y, originX, originY, rotation, bgColor );
    }
    
}
