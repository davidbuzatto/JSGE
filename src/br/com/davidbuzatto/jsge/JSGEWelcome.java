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

/**
 * Classe de apresentação.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class JSGEWelcome extends EngineFrame {
    
    private Image logo;
    private double timeCounter;
    private double timeToClose;
    
    public JSGEWelcome() {
        super( 512, 512, String.format( "JSGE - %s", CoreUtils.getVersion() ), 60, true, false, false, true, false, false );
    }
    
    @Override
    public void create() {
        
        logo = DrawingUtils.createLogo();
        setWindowIcon( logo );
        
        setDefaultFontSize( 20 );
        timeToClose = 10;
        
    }
    
    @Override
    public void update( double delta ) {
        
        timeCounter += delta;
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) || 
             isMouseButtonPressed( MOUSE_BUTTON_MIDDLE ) || 
             isMouseButtonPressed( MOUSE_BUTTON_RIGHT ) ||
             getKeyPressed() != KEY_NULL ||
             timeCounter > timeToClose ) {
            System.exit( 0 );
        }
        
    }
    
    @Override
    public void draw() {
        
        drawImage( logo, 0, 0 );
        
        drawText( 
            CoreUtils.getVersion(),
            logo.getWidth() - measureText( CoreUtils.getVersion() ) - 48, 
            logo.getHeight() - 60,
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
