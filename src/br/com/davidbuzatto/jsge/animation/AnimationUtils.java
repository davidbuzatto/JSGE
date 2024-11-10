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
package br.com.davidbuzatto.jsge.animation;

import br.com.davidbuzatto.jsge.animation.frame.ImageAnimationFrame;
import br.com.davidbuzatto.jsge.image.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface com métodos utilitários para animações.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface AnimationUtils {
    
    /**
     * Cria uma lista de ImageAnimationFrame a partir de uma lista de imagens.
     * 
     * @param images Uma lista de imagens.
     * @return Uma lista de ImageAnimationFrame
     */
    public static List<ImageAnimationFrame> newImageAnimationFrameList( List<Image> images ) {
        
        List<ImageAnimationFrame> frames = new ArrayList<>();
        
        for ( Image image : images ) {
            frames.add( new ImageAnimationFrame( image ) );
        }
        
        return frames;
        
    }
    
}
