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
package br.com.davidbuzatto.jsge.core.engine.imgui;

import java.util.HashSet;
import java.util.Set;

/**
 * Um componente para gerenciamento das caixas de alternância.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiToogleButtonGroup {
    
    private Set<GuiToogleButton> toogleButtons;
    
    public GuiToogleButtonGroup() {
        toogleButtons = new HashSet<>();
    }
    
    public void addToogleButton( GuiToogleButton toogleButton ) {
        toogleButtons.add( toogleButton );
    }
    
    public void toogle( GuiToogleButton target ) {
        
        GuiToogleButton selected = null;
        
        for ( GuiToogleButton btn : toogleButtons ) {
            if ( btn.isSelected() ) {
                selected = btn;
                break;
            }
        }
        
        if ( selected == null ) {
            target.selected = true;
        } else if ( selected != target ) {
            for ( GuiToogleButton btn : toogleButtons ) {
                btn.selected = false;
            }
            selected.selected = false;
            target.selected = true;
        }
        
    }
    
}
