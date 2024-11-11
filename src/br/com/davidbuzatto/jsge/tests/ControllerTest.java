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
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 * Classe de testes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ControllerTest extends EngineFrame {
    
    private Controller[] controllers;
    private List<Controller> gamepads;
    
    /**
     * Cria o teste.
     */
    public ControllerTest() {
        super( 800, 450, "Scratch Pad", 60, true );
    }
    
    @Override
    public void create() {
        
        gamepads = new ArrayList<>();
        
        // dispositivos de entrada disponíveis
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        for ( Controller c : controllers ) {
            if ( c.getType() == Controller.Type.GAMEPAD ) {
                gamepads.add( c );
            }
        }
        
    }
    
    @Override
    public void update( double delta ) {
        
        Controller firstGamepad = gamepads.get( 0 );
        
        if ( firstGamepad != null ) {
            
            // busca
            firstGamepad.poll();
            
            // obtém a fila de eventos do dispositivo
            EventQueue queue = firstGamepad.getEventQueue();
            
            // cria um evento para ser populado
            Event event = new Event();
            
            // consome todos os eventos
            while ( queue.getNextEvent( event ) ) {
                
                Component comp = event.getComponent();
                
                // 
                System.out.println( firstGamepad.getName() );
                System.out.println( comp );
                System.out.println( comp.getIdentifier().getName() + " " + event.getValue() );
                
            }
            
        }

    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        drawFPS( 10, 10 );
        
    }
    
    /**
     * Executa o teste.
     * @param args Argumentos.
     */
    public static void main( String[] args ) {
        new ControllerTest();
    }
    
}
