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
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiToggleButton;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiButtonGroup;
import br.com.davidbuzatto.jsge.core.engine.imgui.GuiRadioButton;
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
    private GuiRadioButton radioBtn1;
    private GuiRadioButton radioBtn2;
    private GuiRadioButton radioBtn3;
    private GuiButtonGroup buttonGroupRadio;
    private GuiToggleButton toggleBtn;
    private GuiToggleButton toggleBtn1;
    private GuiToggleButton toggleBtn2;
    private GuiToggleButton toggleBtn3;
    private GuiButtonGroup buttonGroupToggle;
    
    private GuiCheckBox checkEnabled;
    private GuiCheckBox checkVisible;
    private GuiCheckBox checkDrawBounds;
    
    private int btnPressCount;
    private int labelBtnPressCount;
    
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
        labelBtn = new GuiLabelButton( 10, 90, 100, 30, "Label (Button)", this );
        check = new GuiCheckBox( 10, 130, 100, 20, "Check Box", this );
        
        buttonGroupRadio = new GuiButtonGroup();
        radioBtn1 = new GuiRadioButton( 10, 170, 80, 20, "Radio 1", this );
        radioBtn1.setSelected( true );
        radioBtn1.setButtonGroup( buttonGroupRadio );
        radioBtn2 = new GuiRadioButton( 100, 170, 80, 20, "Radio 2", this );
        radioBtn2.setButtonGroup( buttonGroupRadio );
        radioBtn3 = new GuiRadioButton( 190, 170, 80, 20, "Radio 3", this );
        radioBtn3.setButtonGroup( buttonGroupRadio );
        
        toggleBtn = new GuiToggleButton( 10, 210, 200, 30, "Toggle Button", this );
        buttonGroupToggle = new GuiButtonGroup();
        toggleBtn1 = new GuiToggleButton( 10, 250, 80, 30, "Option 1", this );
        toggleBtn1.setSelected( true );
        toggleBtn1.setButtonGroup( buttonGroupToggle );
        toggleBtn2 = new GuiToggleButton( 90, 250, 80, 30, "Option 2", this );
        toggleBtn2.setButtonGroup( buttonGroupToggle );
        toggleBtn3 = new GuiToggleButton( 170, 250, 80, 30, "Option 3", this );
        toggleBtn3.setButtonGroup( buttonGroupToggle );
        
        components = new ArrayList<>();
        components.add( label );
        components.add( btn );
        components.add( labelBtn );
        components.add( check );
        components.add( radioBtn1 );
        components.add( radioBtn2 );
        components.add( radioBtn3 );
        components.add( toggleBtn );
        components.add( toggleBtn1 );
        components.add( toggleBtn2 );
        components.add( toggleBtn3 );
        
        checkEnabled = new GuiCheckBox( 400, 10, 20, 20, "Enabled", this );
        checkEnabled.setSelected( true );
        checkVisible = new GuiCheckBox( 400, 40, 20, 20, "Visible", this );
        checkVisible.setSelected( true );
        checkDrawBounds = new GuiCheckBox( 400, 70, 20, 20, "Draw bounds", this );
        
    }
    
    @Override
    public void update( double delta ) {
        
        for ( GuiComponent c : components ) {
            c.update( delta );
        }
        
        if ( btn.isPressed() ) {
            btnPressCount++;
        }
        
        if ( labelBtn.isPressed() ) {
            labelBtnPressCount++;
        }
        
        checkEnabled.update( delta );
        checkVisible.update( delta );
        checkDrawBounds.update( delta );
        
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
        
        if ( checkDrawBounds.isPressed() ) {
            for ( GuiComponent c : components ) {
                c.setDrawBounds( checkDrawBounds.isSelected() );
                checkEnabled.setDrawBounds( checkDrawBounds.isSelected() );
                checkVisible.setDrawBounds( checkDrawBounds.isSelected() );
                checkDrawBounds.setDrawBounds( checkDrawBounds.isSelected() );
            }
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        for ( GuiComponent c : components ) {
            c.draw();
        }
        
        drawText( "press count: " + btnPressCount, btn.getBounds().x + btn.getBounds().width + 5, btn.getBounds().y + btn.getBounds().height / 2 - 3, 12, LIGHTGRAY );
        drawText( "press count: " + labelBtnPressCount, labelBtn.getBounds().x + labelBtn.getBounds().width + 5, labelBtn.getBounds().y + labelBtn.getBounds().height / 2 - 3, 12, LIGHTGRAY );
        
        checkEnabled.draw();
        checkVisible.draw();
        checkDrawBounds.draw();
        
    }
    
    /**
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new IMGUITests();
    }
    
}
