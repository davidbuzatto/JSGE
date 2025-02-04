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
package br.com.davidbuzatto.jsge;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.math.Vector2;

/**
 * Classe de apresentação.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class JSGEWelcome extends EngineFrame {
    
    private Image logo;
    private Vector2 logoPos;
    
    public JSGEWelcome() {
        super( 700, 700, String.format( "JSGE - %s", CoreUtils.getVersion() ), 60, true );
    }
    
    @Override
    public void create() {
        
        logo = DrawingUtils.createLogo();
        setWindowIcon( logo );
        
        logoPos = new Vector2(
                getScreenWidth() / 2 - logo.getWidth() / 2, 
                getScreenHeight() / 2 - logo.getHeight() / 2 - 10
        );
        
        setDefaultFontSize( 20 );
        
    }
    
    @Override
    public void update( double delta ) {
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        drawImage( logo, logoPos.x, logoPos.y );
        
        drawText( 
            CoreUtils.getVersion(),
            logoPos.x + logo.getWidth() - measureText( CoreUtils.getVersion() ), 
            logoPos.y + logo.getHeight() + 10,
            BLACK
        );
        
    }
    
    /**
     * Executa.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new JSGEWelcome();
    }
    
}
