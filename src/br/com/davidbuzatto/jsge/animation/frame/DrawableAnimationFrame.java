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

import br.com.davidbuzatto.jsge.core.Drawable;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.awt.Paint;

/**
 * Um quadro de animação baseado em um objeto desenhável.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawableAnimationFrame implements AnimationFrame {
    
    /**
     * O objeto desenhável do quadro de animação.
     */
    public final Drawable drawable;
    
    /**
     * Cria um quadro de animação de um objeto desenhável.
     * 
     * @param drawable Objeto desenhável.
     */
    public DrawableAnimationFrame( Drawable drawable ) {
        this.drawable = drawable;
    }
    
    /**
     * Desenha o objeto desenhável do quadro de animação.
     * 
     * @param engine A engine.
     * @param paint Paint para o desenho.
     */
    public void draw( EngineFrame engine, Paint paint ) {
        drawable.draw( engine, paint );
    }
    
    /**
     * Pinta o objeto desenhável do quadro de animação.
     * 
     * @param engine A engine.
     * @param paint Paint para o desenho.
     */
    public void fill( EngineFrame engine, Paint paint ) {
        drawable.fill( engine, paint );
    }
    
}
