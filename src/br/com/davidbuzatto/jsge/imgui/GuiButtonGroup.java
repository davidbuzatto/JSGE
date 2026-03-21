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
package br.com.davidbuzatto.jsge.imgui;

import java.util.HashSet;
import java.util.Set;

/**
 * A component for managing the selection logic of toggle buttons and radio buttons.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiButtonGroup {
    
    private final Set<GuiToggleButton> toggleButtons;
    
    /**
     * Creates a button group.
     */
    public GuiButtonGroup() {
        toggleButtons = new HashSet<>();
    }
    
    /**
     * Adds a toggle button or radio button to this group.
     * @param toggleButton
     */
    public void addToggleButton( GuiToggleButton toggleButton ) {
        toggleButtons.add( toggleButton );
    }
    
    /**
     * Toggles the selection to the button passed as parameter.
     *
     * @param target The button that should be selected.
     */
    public void toggle( GuiToggleButton target ) {
        
        GuiToggleButton selected = null;
        
        for ( GuiToggleButton btn : toggleButtons ) {
            if ( btn.isSelected() ) {
                selected = btn;
                break;
            }
        }
        
        if ( selected == null ) {
            target.selected = true;
        } else if ( selected != target ) {
            for ( GuiToggleButton btn : toggleButtons ) {
                btn.selected = false;
            }
            selected.selected = false;
            target.selected = true;
        }
        
    }
    
}
