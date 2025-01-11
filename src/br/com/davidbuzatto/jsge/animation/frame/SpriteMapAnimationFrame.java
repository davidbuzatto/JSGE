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
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import java.awt.Color;

/**
 * Um quadro de animação baseado em um mapa de sprite. Usa como base um quadro
 * do tipo ImageAnimationFrame. A imagem do mapa de sprite será associada
 * ao atributo baseImage do ImageAnimationFrame.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SpriteMapAnimationFrame extends ImageAnimationFrame {
    
    /**
     * O retângulo fonte do recorte.
     */
    public final Rectangle source;
    
    /**
     * Cria um quadro de animação de imagem.
     * 
     * @param spriteMapImage Imagem do mapa de sprite.
     * @param x Coordenada x do início do recorte do mapa de sprite.
     * @param y Coordenada y do início do recorte do mapa de sprite.
     * @param width Largura do recorte do mapa de sprite.
     * @param height Altura do recorte do mapa de sprite.
     */
    public SpriteMapAnimationFrame( Image spriteMapImage, double x, double y, double width, double height ) {
        super( spriteMapImage );
        this.source = new Rectangle( x, y, width, height );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y ) {
        engine.drawImage( baseImage, source, x, y );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double rotation ) {
        engine.drawImage( baseImage, source, x, y, rotation );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation ) {
        engine.drawImage( baseImage, source, x, y, originX, originY, rotation );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, Color bgColor ) {
        engine.drawImage( baseImage, source, x, y, bgColor );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, source, x, y, rotation, bgColor );
    }
    
    @Override
    public void draw( EngineFrame engine, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        engine.drawImage( baseImage, source, x, y, originX, originY, rotation, bgColor );
    }
    
}
