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
package br.com.davidbuzatto.jsge.tests;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiButton;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiLabelButton;

/**
 * Classe da immediate mode gui.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class IMGUITests extends EngineFrame {
    
    private GuiLabel label;
    private GuiButton btn;
    private GuiLabelButton labelBtn;
    
    /**
     * Cria o teste.
     */
    public IMGUITests() {
        super( 800, 450, "IMGUI", 60, true );
    }
    
    @Override
    public void create() {
        label = new GuiLabel( 10, 10, 200, 30, "Label", this );
        btn = new GuiButton( 10, 50, 200, 30, "Botão", this );
        labelBtn = new GuiLabelButton( 10, 90, 200, 30, "Label (Botão)", this );
    }
    
    @Override
    public void update( double delta ) {
        btn.update( delta );
        labelBtn.update( delta );
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        label.draw();
        btn.draw();
        labelBtn.draw();
        
        if ( btn.isPressed() ) {
            System.out.println( "clicou no botao!" );
        }
        
        if ( labelBtn.isPressed() ) {
            System.out.println( "clicou no label (botao)!" );
        }
        
    }
    
    /**
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new IMGUITests();
    }
    
}
