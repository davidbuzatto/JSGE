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
 * Um componente para gerenciamento das caixas de alternância e botões de radio.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiButtonGroup {
    
    private Set<GuiToggleButton> toggleButtons;
    
    public GuiButtonGroup() {
        toggleButtons = new HashSet<>();
    }
    
    public void addToggleButton( GuiToggleButton toggleButton ) {
        toggleButtons.add( toggleButton );
    }
    
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
