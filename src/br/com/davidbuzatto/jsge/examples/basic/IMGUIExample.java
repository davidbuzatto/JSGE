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
package br.com.davidbuzatto.jsge.examples.basic;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiButtonGroup;
import br.com.davidbuzatto.jsge.imgui.GuiCheckBox;
import br.com.davidbuzatto.jsge.imgui.GuiColorPicker;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiConfirmDialog;
import br.com.davidbuzatto.jsge.imgui.GuiDropdownList;
import br.com.davidbuzatto.jsge.imgui.GuiGroup;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.imgui.GuiLabelButton;
import br.com.davidbuzatto.jsge.imgui.GuiLine;
import br.com.davidbuzatto.jsge.imgui.GuiList;
import br.com.davidbuzatto.jsge.imgui.GuiMessageDialog;
import br.com.davidbuzatto.jsge.imgui.GuiPanel;
import br.com.davidbuzatto.jsge.imgui.GuiProgressBar;
import br.com.davidbuzatto.jsge.imgui.GuiRadioButton;
import br.com.davidbuzatto.jsge.imgui.GuiSlider;
import br.com.davidbuzatto.jsge.imgui.GuiSpinner;
import br.com.davidbuzatto.jsge.imgui.GuiTextField;
import br.com.davidbuzatto.jsge.imgui.GuiToggleButton;
import br.com.davidbuzatto.jsge.imgui.GuiWindow;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Exemplo de uso dos componentes IMGUI (Immediate Mode Graphical User Interface).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class IMGUIExample extends EngineFrame {

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
    private GuiProgressBar progressBar;
    private GuiSpinner spinner;
    private GuiSlider horizontalSlider;
    private GuiSlider verticalSlider;
    private GuiTextField textField;
    private GuiDropdownList dropdownList;
    private GuiList list;
    private GuiColorPicker colorPicker;
    
    private GuiLine horizontalLine;
    private GuiLine horizontalLineUntitled;
    private GuiLine verticalLine;
    private GuiLine verticalLineUntitled;
    private GuiGroup group;
    private GuiGroup groupUntitled;
    private GuiPanel panel;
    private GuiPanel panelUntitled;
    private GuiWindow window;
    private GuiWindow windowUntitled;
    
    private GuiButton buttonShowMessageDialog;
    private GuiButton buttonShowInputDialog;
    private GuiButton buttonShowConfirmDialog;
    
    private GuiMessageDialog messageDialog;
    private GuiInputDialog inputDialog;
    private GuiConfirmDialog confirmDialog;
    
    private GuiComponent draggedComponent;
    private Vector2 previousMousePos;
    
    private GuiCheckBox checkEnabled;
    private GuiCheckBox checkVisible;
    private GuiCheckBox checkDrawBounds;
    
    private int buttonPressCount;
    private int labelButtonPressCount;
    
    private double progressCount;
    private double progressTime;
    
    private String messageDialogStatus;
    private String inputDialogStatus;
    private String confirmDialogStatus;
    
    /**
     * Cria o exemplo.
     */
    public IMGUIExample() {
        super( 905, 840, "IMGUI", 60, true );
    }
    
    @Override
    public void create() {
        
        useAsDependencyForIMGUI();
        
        int x = 20;
        int y = 40;
        int vSpacing = 50;
        
        label = new GuiLabel( x, y, 40, 30, "Label" );
        button = new GuiButton( x, y += vSpacing, 100, 30, "Button" );
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
        
        toggleButton = new GuiToggleButton( x, y += vSpacing, 120, 30, "Toggle Button" );
        buttonGroupToggle = new GuiButtonGroup();
        toggleButton1 = new GuiToggleButton( x, y += vSpacing, 80, 30, "Option 1" );
        toggleButton1.setSelected( true );
        toggleButton1.setButtonGroup( buttonGroupToggle );
        toggleButton2 = new GuiToggleButton( x + 80, y, 80, 30, "Option 2" );
        toggleButton2.setButtonGroup( buttonGroupToggle );
        toggleButton3 = new GuiToggleButton( x + 160, y, 80, 30, "Option 3" );
        toggleButton3.setButtonGroup( buttonGroupToggle );
        
        progressBar = new GuiProgressBar( x, y += vSpacing + 5, 260, 20, 0, 0, 50 );
        spinner = new GuiSpinner( x, y += vSpacing - 5, 100, 30, 0, 0, 10 );
        horizontalSlider = new GuiSlider( x, ( y += vSpacing ) + 25, 220, 30, 25, 1, 50 );
        verticalSlider = new GuiSlider( x + 230, y, 30, 80, 25, 1, 50, GuiSlider.VERTICAL );
        textField = new GuiTextField( x, y += vSpacing * 2, 260, 30, "" );
        
        List<String> listItems = new ArrayList<>();
        listItems.add( "one" );
        listItems.add( "two" );
        listItems.add( "three" );
        listItems.add( "four" );
        listItems.add( "five" );
        listItems.add( "six" );
        dropdownList = new GuiDropdownList( x, y += vSpacing, 240, 30, listItems );
        list = new GuiList( x, y += vSpacing, 240, 130, listItems );

        progressTime = 0.05;
        
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
        components.add( progressBar );
        components.add( spinner );
        components.add( horizontalSlider );
        components.add( verticalSlider );
        components.add( textField );
        components.add( list );
        components.add( dropdownList );
        
        x = 450;
        y = 55;
        vSpacing = 120;
        
        horizontalLine = new GuiLine( x, y + 10, 130, 30, "Line" );
        horizontalLineUntitled = new GuiLine( x + 170, y +10, 130, 30, "" );
        verticalLine = new GuiLine( x + 130, y - 20, 30, 90, "Line", GuiLine.VERTICAL );
        verticalLineUntitled = new GuiLine( x + 300, y - 20, 30, 90, "", GuiLine.VERTICAL );
        group = new GuiGroup( x, y += vSpacing - 30, 150, 70, "Group" );
        groupUntitled = new GuiGroup( x + 170, y, 150, 70, "" );
        panel = new GuiPanel( x, y += vSpacing - 25, 150, 80, "Panel" );
        panelUntitled = new GuiPanel( x + 170, y, 150, 80, "" );
        window = new GuiWindow( x, y += vSpacing - 20, 150, 80, "Window" );
        windowUntitled = new GuiWindow( x + 170, y, 150, 80, "" );
        
        components.add( horizontalLine );
        components.add( horizontalLineUntitled );
        components.add( verticalLine );
        components.add( verticalLineUntitled );
        components.add( group );
        components.add( groupUntitled );
        components.add( panel );
        components.add( panelUntitled );
        components.add( window );
        components.add( windowUntitled );
        
        x = 450;
        y = 490;
        vSpacing = 50;
        buttonShowMessageDialog = new GuiButton( x, y, 150, 30, "Show Message Dialog" );
        buttonShowInputDialog = new GuiButton( x, y += vSpacing, 150, 30, "Show Input Dialog" );
        buttonShowConfirmDialog = new GuiButton( x, y += vSpacing, 150, 30, "Show Confirm Dialog" );
        messageDialog = new GuiMessageDialog( "Message Dialog", "This is a message!", true );
        inputDialog = new GuiInputDialog( "Input Dialog", "Provide some data:", true );
        confirmDialog = new GuiConfirmDialog( "Confirm Dialog", "Hello!\nHow are you?", "fine", "happy", "hungry", true );
        
        messageDialogStatus = "";
        inputDialogStatus = "";
        confirmDialogStatus = "";
        
        x = 465;
        y = 695;
        colorPicker = new GuiColorPicker( x, y, 100, 100, EngineFrame.LIME );
        components.add( colorPicker );
        
        x = 790;
        y = 30;
        vSpacing = 30;
        checkEnabled = new GuiCheckBox( x, y, 100, 20, "Enabled" );
        checkEnabled.setSelected( true );
        checkVisible = new GuiCheckBox( x, y += vSpacing, 100, 20, "Visible" );
        checkVisible.setSelected( true );
        checkDrawBounds = new GuiCheckBox( x, y += vSpacing, 100, 20, "Draw bounds" );
        
    }

    @Override
    public void update( double delta ) {
        
        Vector2 mousePos = getMousePositionPoint();
        
        for ( GuiComponent c : components ) {
            if ( dropdownList.isDropdownListVisible()  ) {
                if ( !c.equals( list ) ) {
                    c.update( delta );
                }
            } else {
                c.update( delta );
            }
        }
        
        if ( button.isMousePressed() ) {
            buttonPressCount++;
        }
        
        if ( labelButton.isMousePressed() ) {
            labelButtonPressCount++;
        }
        
        progressCount += delta;
        if ( progressCount > progressTime ) {
            if ( progressBar.isEnabled() ) {
                progressBar.setValue( progressBar.getValue() + 0.1 );
            }
            progressCount = 0;
        }
        
        if ( panel.isTitleBarPressed() ) {
            draggedComponent = panel;
        }
        
        if ( panelUntitled.isTitleBarPressed() ) {
            draggedComponent = panelUntitled;
        }
        
        if ( window.isCloseButtonPressed() ) {
            window.setVisible( false );
        }
        
        if ( window.isTitleBarPressed() ) {
            draggedComponent = window;
        }
        
        if ( windowUntitled.isCloseButtonPressed() ) {
            windowUntitled.setVisible( false );
        }
        
        if ( windowUntitled.isTitleBarPressed() ) {
            draggedComponent = windowUntitled;
        }
        
        if ( isMouseButtonDown( MOUSE_BUTTON_LEFT ) ) {
            if ( draggedComponent != null ) {
                draggedComponent.move( mousePos.x - previousMousePos.x, mousePos.y - previousMousePos.y );
            }
        } else if ( isMouseButtonUp( MOUSE_BUTTON_LEFT ) ) {
            draggedComponent = null;
        }
        
        buttonShowMessageDialog.update( delta );
        buttonShowInputDialog.update( delta );
        buttonShowConfirmDialog.update( delta );
        messageDialog.update( delta );
        inputDialog.update( delta );
        confirmDialog.update( delta );
        
        if ( buttonShowMessageDialog.isMousePressed() ) {
            messageDialog.show();
        }
        
        if ( buttonShowInputDialog.isMousePressed() ) {
            inputDialog.show();
        }
        
        if ( buttonShowConfirmDialog.isMousePressed() ) {
            confirmDialog.show();
        }
        
        if ( messageDialog.isCloseButtonPressed() ) {
            messageDialogStatus = "closed";
            messageDialog.hide();
        }
        
        if ( messageDialog.isOkButtonPressed() ) {
            messageDialogStatus = "ok";
            messageDialog.hide();
        }
        
        if ( messageDialog.isTitleBarPressed() ) {
            draggedComponent = messageDialog;
        }
        
        if ( inputDialog.isCloseButtonPressed() ) {
            inputDialogStatus = "closed";
            inputDialog.hide();
        }
        
        if ( inputDialog.isOkButtonPressed() ) {
            inputDialogStatus = inputDialog.getValue();
            inputDialog.hide();
        }
        
        if ( inputDialog.isEnterKeyPressed() ) {
            inputDialogStatus = inputDialog.getValue();
            inputDialog.hide();
        }
        
        if ( inputDialog.isCancelButtonPressed() ) {
            inputDialogStatus = "canceled";
            inputDialog.hide();
        }
        
        if ( inputDialog.isTitleBarPressed() ) {
            draggedComponent = inputDialog;
        }
        
        if ( confirmDialog.isCloseButtonPressed() ) {
            confirmDialogStatus = "closed";
            confirmDialog.hide();
        }
        
        String pressedButton = confirmDialog.getPressedButton();
        if ( pressedButton != null ) {
            confirmDialogStatus = String.format(  "\"%s\" pressed", pressedButton );
            confirmDialog.hide();
        }
        
        if ( confirmDialog.isTitleBarPressed() ) {
            draggedComponent = confirmDialog;
        }
        
        checkEnabled.update( delta );
        checkVisible.update( delta );
        checkDrawBounds.update( delta );
        
        if ( checkEnabled.isMousePressed() ) {
            for ( GuiComponent c : components ) {
                c.setEnabled( checkEnabled.isSelected() );
            }
        }
        
        if ( checkVisible.isMousePressed() ) {
            for ( GuiComponent c : components ) {
                c.setVisible( checkVisible.isSelected() );
            }
        }
        
        if ( checkDrawBounds.isMousePressed() ) {
            for ( GuiComponent c : components ) {
                c.setDrawBounds( checkDrawBounds.isSelected() );
                checkEnabled.setDrawBounds( checkDrawBounds.isSelected() );
                checkVisible.setDrawBounds( checkDrawBounds.isSelected() );
                checkDrawBounds.setDrawBounds( checkDrawBounds.isSelected() );
            }
        }
        
        previousMousePos = mousePos;
        
        /*int x = 0;
        int y = 0;
        if ( isKeyPressed( KEY_RIGHT ) ) {
            x = 10;
        } else if ( isKeyPressed( KEY_LEFT ) ) {
            x = -10;
        } else if ( isKeyPressed( KEY_UP ) ) {
            y = -10;
        } else if ( isKeyPressed( KEY_DOWN ) ) {
            y = 10;
        }
        
        if ( x != 0 || y != 0 ) {
            for ( GuiComponent c : components ) {
                c.move( x, y );
            }
        }*/
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        int hSep = 280;
        int dataMargin = hSep + 25;
        drawGrid( 9, 10, 30, 420, 50, hSep, LIGHTGRAY );
        drawGrid( 1, 10, 480, 420, 100, hSep, LIGHTGRAY );
        drawGrid( 2, 10, 580, 420, 50, hSep, LIGHTGRAY );
        drawGrid( 1, 10, 680, 420, 150, hSep, LIGHTGRAY );
        drawGrid( 4, 440, 30, 340, 100, 170, LIGHTGRAY );
        drawGrid( 3, 440, 480, 340, 50, 170, LIGHTGRAY );
        drawGrid( 1, 440, 680, 340, 150, 170, LIGHTGRAY );
        
        drawText( "Controls", 10, 10, 20, GRAY );
        drawText( "Containers/Separators", 440, 10, 20, GRAY );
        drawText( "Dialogs", 440, 460, 20, GRAY );
        drawText( "Color Picker", 440, 660, 20, GRAY );
        
        for ( GuiComponent c : components ) {
            c.draw();
        }
        
        drawText( "press count: " + buttonPressCount, dataMargin, button.getBounds().y + button.getBounds().height / 2 - 3, 12, GRAY );
        drawText( "press count: " + labelButtonPressCount, dataMargin, labelButton.getBounds().y + labelButton.getBounds().height / 2 - 3, 12, GRAY );
        drawText( checkBox.isSelected() ? "selected" : "unselected", dataMargin, checkBox.getBounds().y + checkBox.getBounds().height / 2 - 3, 12, GRAY );
        
        int selectedRadio = 0;
        if ( radioButton1.isSelected() ) {
            selectedRadio = 1;
        } else if ( radioButton2.isSelected() ) {
            selectedRadio = 2;
        } else if ( radioButton3.isSelected() ) {
            selectedRadio = 3;
        }
        drawText( "radio " + selectedRadio, dataMargin, radioButton1.getBounds().y + radioButton1.getBounds().height / 2 - 3, 12, GRAY );
        
        drawText( toggleButton.isSelected() ? "selected" : "unselected", dataMargin, toggleButton.getBounds().y + toggleButton.getBounds().height / 2 - 3, 12, GRAY );
        
        int selectedOtion = 0;
        if ( toggleButton1.isSelected() ) {
            selectedOtion = 1;
        } else if ( toggleButton2.isSelected() ) {
            selectedOtion = 2;
        } else if ( toggleButton3.isSelected() ) {
            selectedOtion = 3;
        }
        drawText( "option " + selectedOtion, dataMargin, toggleButton1.getBounds().y + toggleButton1.getBounds().height / 2 - 3, 12, GRAY );
        
        drawText( String.format( "%.0f%% (%.2f)", progressBar.getPercentage() * 100, progressBar.getValue() ), dataMargin, progressBar.getBounds().y + progressBar.getBounds().height / 2 - 3, 12, GRAY );
        drawText( String.format( "value: %d", spinner.getValue() ), dataMargin, spinner.getBounds().y + spinner.getBounds().height / 2 - 3, 12, GRAY );
        drawText( String.format( "value (h): %.2f", horizontalSlider.getValue() ), dataMargin, horizontalSlider.getBounds().y + horizontalSlider.getBounds().height / 2 - 25, 12, GRAY );
        drawText( String.format( "value (v): %.2f", verticalSlider.getValue() ), dataMargin, verticalSlider.getBounds().y + verticalSlider.getBounds().height / 2 + 20, 12, GRAY );
        drawText( String.format( "size: %d", textField.getValue().length() ), dataMargin, textField.getBounds().y + textField.getBounds().height / 2 - 3, 12, GRAY );
        drawText( String.format( "selected: %s", dropdownList.getSelectedItemText() ), dataMargin, dropdownList.getBounds().y + dropdownList.getBounds().height / 2 - 3, 12, GRAY );
        drawText( String.format( "selected: %s", list.getSelectedItemText() ), dataMargin, list.getBounds().y + list.getBounds().height / 2 - 3, 12, GRAY );
        
        buttonShowMessageDialog.draw();
        buttonShowInputDialog.draw();
        buttonShowConfirmDialog.draw();
        
        drawText( messageDialogStatus, 620, buttonShowMessageDialog.getBounds().y + buttonShowMessageDialog.getBounds().height / 2 - 3, 12, GRAY );
        drawText( inputDialogStatus, 620, buttonShowInputDialog.getBounds().y + buttonShowInputDialog.getBounds().height / 2 - 3, 12, GRAY );
        drawText( confirmDialogStatus, 620, buttonShowConfirmDialog.getBounds().y + buttonShowConfirmDialog.getBounds().height / 2 - 3, 12, GRAY );
        
        messageDialog.draw();
        inputDialog.draw();
        confirmDialog.draw();
        
        drawColoredRectangle( 645, colorPicker.getBounds().y + colorPicker.getBounds().height / 2 - 40, colorPicker.getColor() );
        
        checkEnabled.draw();
        checkVisible.draw();
        checkDrawBounds.draw();
        
    }
    
    private void drawGrid( int lines, int x, int y, int width, int height, int hSep, Color color ) {
        
        for ( int i = 0; i <= lines; i++ ) {
            drawLine( x, y + height * i, x + width, y + height * i, color );
        }
        
        drawLine( x, y, x, y + height * lines, color );
        drawLine( x + hSep, y, x + hSep, y + height * lines, color );
        drawLine( x + width, y, x + width, y + height * lines, color );
        
    }
    
    private void drawColoredRectangle( double x, double y, Color color ) {
        
        int w = 100;
        int retSize = 5;
        
        for ( int i = 0; i < w; i += retSize ) {
            for ( int j = 0; j < w; j += retSize ) {
                fillRectangle( 
                        x + j,
                        y + i,
                        retSize,
                        retSize,
                        i % 2 == 0 ?
                                j % 2 == 0 ? EngineFrame.WHITE : EngineFrame.GRAY
                                :
                                j % 2 == 0 ? EngineFrame.GRAY : EngineFrame.WHITE
                );
            }
        }
        
        fillRectangle( x, y, 100, 100, colorPicker.getColor() );
        drawRectangle( x, y, 100, 100, GRAY );
        
    }
    
    /**
     * Executa o exemplo.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new IMGUIExample();
    }
    
}
