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
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiCheckBox;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiLabelButton;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiToogleButton;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiToogleButtonGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de teste da immediate mode gui.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class IMGUITests extends EngineFrame {
    
    private List<GuiComponent> components;
    
    private GuiLabel label;
    private GuiButton btn;
    private GuiLabelButton labelBtn;
    private GuiCheckBox check;
    private GuiToogleButton toogleBtn;
    private GuiToogleButton toogleBtn1;
    private GuiToogleButton toogleBtn2;
    private GuiToogleButton toogleBtn3;
    private GuiToogleButtonGroup buttonGroup;
    
    private GuiCheckBox checkEnabled;
    private GuiCheckBox checkVisible;
    
    /**
     * Cria o teste.
     */
    public IMGUITests() {
        super( 800, 800, "IMGUI", 60, true );
    }
    
    @Override
    public void create() {
        
        label = new GuiLabel( 10, 10, 200, 30, "Label", this );
        btn = new GuiButton( 10, 50, 200, 30, "Button", this );
        labelBtn = new GuiLabelButton( 10, 90, 200, 30, "Label (Button)", this );
        check = new GuiCheckBox( 10, 130, 200, 30, "Check Box", this );
        toogleBtn = new GuiToogleButton( 10, 170, 200, 30, "Toggle Button", this );
        
        buttonGroup = new GuiToogleButtonGroup();
        toogleBtn1 = new GuiToogleButton( 10, 210, 80, 30, "Option 1", this );
        toogleBtn1.setSelected( true );
        toogleBtn1.setButtonGroup( buttonGroup );
        toogleBtn2 = new GuiToogleButton( 90, 210, 80, 30, "Option 2", this );
        toogleBtn2.setButtonGroup( buttonGroup );
        toogleBtn3 = new GuiToogleButton( 170, 210, 80, 30, "Option 3", this );
        toogleBtn3.setButtonGroup( buttonGroup );
        
        components = new ArrayList<>();
        components.add( label );
        components.add( btn );
        components.add( labelBtn );
        components.add( check );
        components.add( toogleBtn );
        components.add( toogleBtn1 );
        components.add( toogleBtn2 );
        components.add( toogleBtn3 );
        
        checkEnabled = new GuiCheckBox( 600, 10, 30, 30, "Enabled", this );
        checkEnabled.setSelected( true );
        checkVisible = new GuiCheckBox( 700, 10, 30, 30, "Visible", this );
        checkVisible.setSelected( true );
        
    }
    
    @Override
    public void update( double delta ) {
        
        for ( GuiComponent c : components ) {
            c.update( delta );
        }
        
        checkEnabled.update( delta );
        checkVisible.update( delta );
        
        if ( checkEnabled.isPressed() ) {
            for ( GuiComponent c : components ) {
                c.setEnabled( checkEnabled.isSelected() );
            }
        }
        
        if ( checkVisible.isPressed() ) {
            for ( GuiComponent c : components ) {
                c.setVisible( checkVisible.isSelected() );
            }
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        for ( GuiComponent c : components ) {
            c.draw();
        }
        
        checkEnabled.draw();
        checkVisible.draw();
        
    }
    
    /**
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new IMGUITests();
    }
    
}
