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
    private GuiButton button;
    private GuiLabelButton labelButton;
    private GuiCheckBox checkBox;
    private GuiRadioButton radioButton1;
    private GuiRadioButton radioButton2;
    private GuiRadioButton radioButton3;
    private GuiButtonGroup buttonGroupRadio;
    private GuiToggleButton toggleButton;
    private GuiToggleButton toggleButton1;
    private GuiToggleButton toggleButton2;
    private GuiToggleButton toggleButton3;
    private GuiButtonGroup buttonGroupToggle;
    
    private GuiCheckBox checkEnabled;
    private GuiCheckBox checkVisible;
    private GuiCheckBox checkDrawBounds;
    
    private int buttonPressCount;
    private int labelButtonPressCount;
    
    /**
     * Cria o teste.
     */
    public IMGUITests() {
        super( 800, 800, "IMGUI", 60, true );
    }
    
    @Override
    public void create() {
        
        useAsDependency();
        
        int x = 20;
        int y = 20;
        int vSpacing = 50;
        
        label = new GuiLabel( x, y, 80, 30, "Label" );
        button = new GuiButton( x, y += vSpacing, 200, 30, "Button" );
        labelButton = new GuiLabelButton( x, y += vSpacing, 90, 30, "Label Button" );
        checkBox = new GuiCheckBox( x, y += vSpacing, 100, 30, "Check Box" );
        
        buttonGroupRadio = new GuiButtonGroup();
        radioButton1 = new GuiRadioButton( x, y += vSpacing, 80, 30, "Radio 1" );
        radioButton1.setSelected( true );
        radioButton1.setButtonGroup( buttonGroupRadio );
        radioButton2 = new GuiRadioButton( x + 90, y, 80, 30, "Radio 2" );
        radioButton2.setButtonGroup( buttonGroupRadio );
        radioButton3 = new GuiRadioButton( x + 180, y, 80, 30, "Radio 3" );
        radioButton3.setButtonGroup( buttonGroupRadio );
        
        toggleButton = new GuiToggleButton( x, y += vSpacing, 200, 30, "Toggle Button" );
        buttonGroupToggle = new GuiButtonGroup();
        toggleButton1 = new GuiToggleButton( x, y += vSpacing, 80, 30, "Option 1" );
        toggleButton1.setSelected( true );
        toggleButton1.setButtonGroup( buttonGroupToggle );
        toggleButton2 = new GuiToggleButton( x + 80, y, 80, 30, "Option 2" );
        toggleButton2.setButtonGroup( buttonGroupToggle );
        toggleButton3 = new GuiToggleButton( x + 160, y, 80, 30, "Option 3" );
        toggleButton3.setButtonGroup( buttonGroupToggle );
        
        components = new ArrayList<>();
        components.add( label );
        components.add( button );
        components.add( labelButton );
        components.add( checkBox );
        components.add( radioButton1 );
        components.add( radioButton2 );
        components.add( radioButton3 );
        components.add( toggleButton );
        components.add( toggleButton1 );
        components.add( toggleButton2 );
        components.add( toggleButton3 );
        
        int xControllers = 520;
        checkEnabled = new GuiCheckBox( xControllers, 10, 100, 20, "Enabled" );
        checkEnabled.setSelected( true );
        checkVisible = new GuiCheckBox( xControllers, 40, 100, 20, "Visible" );
        checkVisible.setSelected( true );
        checkDrawBounds = new GuiCheckBox( xControllers, 70, 100, 20, "Draw bounds" );
        
    }
    
    @Override
    public void update( double delta ) {
        
        for ( GuiComponent c : components ) {
            c.update( delta );
        }
        
        if ( button.isPressed() ) {
            buttonPressCount++;
        }
        
        if ( labelButton.isPressed() ) {
            labelButtonPressCount++;
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
        
        int hSep = 280;
        int dataMargin = hSep + 25;
        drawGrid( 10, 10, 10, 500, 50, hSep );
        
        for ( GuiComponent c : components ) {
            c.draw();
        }
        
        drawText( "press count: " + buttonPressCount, dataMargin, button.getBounds().y + button.getBounds().height / 2 - 3, 12, DARKGRAY );
        drawText( "press count: " + labelButtonPressCount, dataMargin, labelButton.getBounds().y + labelButton.getBounds().height / 2 - 3, 12, DARKGRAY );
        drawText( checkBox.isSelected() ? "selected" : "unselected", dataMargin, checkBox.getBounds().y + checkBox.getBounds().height / 2 - 3, 12, DARKGRAY );
        
        int selectedRadio = 0;
        if ( radioButton1.isSelected() ) {
            selectedRadio = 1;
        } else if ( radioButton2.isSelected() ) {
            selectedRadio = 2;
        } else if ( radioButton3.isSelected() ) {
            selectedRadio = 3;
        }
        drawText( "radio " + selectedRadio, dataMargin, radioButton1.getBounds().y + radioButton1.getBounds().height / 2 - 3, 12, DARKGRAY );
        
        drawText( toggleButton.isSelected() ? "selected" : "unselected", dataMargin, toggleButton.getBounds().y + toggleButton.getBounds().height / 2 - 3, 12, DARKGRAY );
        
        int selectedOtion = 0;
        if ( toggleButton1.isSelected() ) {
            selectedOtion = 1;
        } else if ( toggleButton2.isSelected() ) {
            selectedOtion = 2;
        } else if ( toggleButton3.isSelected() ) {
            selectedOtion = 3;
        }
        drawText( "option " + selectedOtion, dataMargin, toggleButton1.getBounds().y + toggleButton1.getBounds().height / 2 - 3, 12, DARKGRAY );
        
        checkEnabled.draw();
        checkVisible.draw();
        checkDrawBounds.draw();
        
    }
    
    private void drawGrid( int lines, int x, int y, int width, int height, int hSep ) {
        
        for ( int i = 0; i <= lines; i++ ) {
            drawLine( x, y + height * i, x + width, y + height * i, BLACK );
        }
        
        drawLine( x, y, x, y + height * lines, BLACK );
        drawLine( x + hSep, y, x + hSep, y + height * lines, BLACK );
        drawLine( x + width, y, x + width, y + height * lines, BLACK );
        
    }
    
    /**
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new IMGUITests();
    }
    
}
